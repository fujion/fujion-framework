'use strict';

define('fujion-widget', [
	'fujion-core',
	'bootstrap',
	'jquery-ui',
	'jquery-scrollTo',
	'jquery-print',
	'balloon-css',
	'jquery-ui-css',
	'bootstrap-css',
	'fontawesome-css',
	'fujion-widget-css'], fujion => {
	/* Widget support.  In the documentation, when we refer to 'widget' we mean an instance of the Widget
	 * class.  When we refer to 'widget$' (following the convention that a variable name ending in '$'
	 * is always a jquery object), we mean the jquery object managed by the widget.
	 */

	fujion.widget._fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

	fujion.widget._domTemplates = {
			badge: '<span id="${id}-badge" class="badge badge-pill"></span>',
			checkable: '<span id="${id}-chk" class="fa"></span>',
			closable: '<span id="${id}-cls" class="fa fa-remove"></span>',
			image: '<img id="${id}-img" src="${_state.image}">',
			label: '<span id="${id}-lbl" class="fujion-labeled-label"></span>',
			labelfor: '<label id="${id}-lbl" for="${id}-inp" class="fujion-labeled-label"></label>',
			sortOrder: '<span id="${id}-dir" class="fa"></span>'
	};

	fujion.widget._zmodal = 999;

	fujion.widget._popup = {};

	/******************************************************************************************************************
	 * Base class providing simulated inheritance.
	 ******************************************************************************************************************/

	fujion.widget.Widget = function(){};

	/**
	 * Simulates inheritance by copying methods and properties to
	 * the designated subclass prototype.
	 *
	 * @param {object} subclass The subclass.
	 */
	fujion.widget.Widget.extend = function(subclass) {
		const _super = this.prototype;
		const prototype = new this();
		const log = fujion.log.warn;
		subclass = subclass || {};

		for (const name in subclass) {
			if (!name.endsWith('_')) {
				const subvalue = subclass[name];
				const supervalue = _super[name];
				const subfn = _.isFunction(subvalue);
				const superfn = _.isFunction(supervalue);
				const hasSuper = subfn && fujion.widget._fnTest.test(subvalue);

				if (subfn) {
					if (!hasSuper) {
						superfn ? log('_super method not called for', name) : null;
						prototype[name] = subvalue;
					} else {
						!superfn ? log('_super method call without ancestor method for', name) : null;
						prototype[name] =
							(function (sp, fn) {
		    			    			return function() {
		    			    				const tmp = this._super;
		    			    				const args = arguments;
		    			    				const self = this;
		    			    				let ret;
		    			    				this._super = function() {return sp ? sp.apply(self, arguments.length ? arguments : args) : undefined};
									try {
										ret = fn.apply(this, arguments);
									} finally {
										this._super = tmp;
									}
									return ret;
								};
							})(superfn ? supervalue : null, subvalue);
					}
				} else {
					superfn ? log('Member declaration hides ancestor method', name) : null;
					prototype[name] = subvalue;
				}
			}
		}

		function Widget() {
			if (arguments.length && this._init) {
				this._init.apply(this, arguments);
			}
		}

		Widget.prototype = prototype;
		Widget.prototype.constructor = Widget;
		Widget.extend = this.extend;
		return Widget;
	};

	/******************************************************************************************************************
	 * Base class for all widget implementations.
	 ******************************************************************************************************************/

	fujion.widget.BaseWidget = fujion.widget.Widget.extend({

		/*------------------------------ Containment ------------------------------*/

		addChild: function(child, index) {
			if (!this.isContainer()) {
				throw new Error('Not a container widget: ' + this.wclass)
			}

			child = fujion.wgt(child);

			if (!child) {
				throw new Error('Child is not a valid widget.');
			}

			child._parent ? child._parent.removeChild(child) : null;
			index = _.isNil(index) || index >= this._children.length ? -1 : index;
			child._parent = this;
			child._attach(index);
			index < 0 ? this._children.push(child) : this._children.splice(index, 0, child);
			this.onAddChild(child);
			child.onParentChange(this);
		},

		anchor$: function() {
			return this.widget$;
		},

		/**
		 * Detaches the associated widget$ and its ancillaries.
		 */
		detach: function() {
			this.widget$ ? this.widget$.detach() : null;
			this._detachAncillaries();
		},

		/**
		 * Returns the ancestor of the desired widget class, or null if not found.
		 *
		 * @param {ctor | string} wclass The class name or class constructor for the ancestor.
		 * 		If the class name is specified, it may be prefixed with a module name, separated by
		 * 		a period.  If no module name is included, the module name will default to the
		 * 		widget's module name.
		 * @param {boolean} [includeSelf] If true, include this widget in the search.
		 * @return {Widget} The ancestor of the desired type, or null if none found.
		 */
		getAncestor: function(wclass, includeSelf) {
			let wgt = includeSelf ? this : this._parent;
			const byctor = _.isFunction(wclass);
			let wmodule = null;

			if (!byctor) {
				const pcs = wclass ? wclass.split('.', 2) : [];
				wmodule = pcs.length === 2 ? pcs[0] : this.wmodule;
				wclass = pcs.length === 2 ? pcs[1] : wclass;
			}

			while (wgt && (byctor ? wgt.constructor !== wclass : wgt.wclass !== wclass || wgt.wmodule !== wmodule)) {
				wgt = wgt._parent;
			}

			return wgt || null;
		},

		/**
		 * @return The count of children belonging to this parent.
		 */
		getChildCount: function() {
			return this._children ? this._children.length : 0;
		},

		/**
		 * Returns the index position of the specified child widget within its parent widget.
		 *
		 * @param {Widget} child The child widget.
		 * @return {number} The index of the child widget, or -1 if not found.
		 */
		getChildIndex: function(child) {
			return this._children ? this._children.indexOf(child) : -1;
		},

		/**
		 * Returns the level of a widget relative to its owning page.  If
		 * the widget is not attached to a page, returns -1.
		 *
		 * @param {Object} [wgt] If specified, the widget associated with
		 * 		the parameter is considered.  Otherwise, this widget is used.
		 */
		getLevel: function(wgt) {
			let level = 0;
			wgt = wgt ? fujion.wgt(wgt) : this;

			while (wgt && wgt !== fujion.widget._page) {
				level++;
				wgt = wgt._parent;
			}

			return wgt ? level : -1;
		},

		/**
		 * Invokes a callback on each child widget.
		 *
		 * @param {callback} callback A callback function following the _.forEach convention.
		 */
		forEachChild: function(callback) {
			if (this._children) {
				_.forEach(this._children, callback.bind(this));
			}
		},

		/**
		 * Returns the hosting page.
		 *
		 * @return {Page} The hosting page.
		 */
		getPage: function() {
			return fujion.widget._page;
		},

		/**
		 * Returns true if this widget may contain other widgets.
		 *
		 * @return {boolean} True if widget is a container.
		 */
		isContainer: function() {
			return this._children;
		},

		onAddChild: function(child) {
			// Does nothing by default
		},

		onRemoveChild: function(child, destroyed, anchor$) {
			// Does nothing by default
		},

		onParentChange: function(parent) {
			// Does nothing by default
		},

		removeChild: function(child, destroy) {
			const currentIndex = this.getChildIndex(child);

			if (currentIndex >= 0) {
				const anchor$ = child.widget$.parent();
				this._children.splice(currentIndex, 1);
				child._parent = null;
				destroy ? child.destroy() : child.detach();
				this.onRemoveChild(child, destroy, anchor$);
				child.onParentChange(null);
				return true;
			}
		},

		swapChildren: function(index1, index2) {
			const child1 = this._children[index1];
			const child2 = this._children[index2];
			this._children[index1] = child2;
			this._children[index2] = child1;
			fujion.swap(child1.widget$, child2.widget$);
		},

		/**
		 * Attaches a widget to the DOM at the specified position.
		 *
		 * @param {number} [index] Position of the widget relative to its siblings.
		 */
		_attach: function(index) {
			const ref = _.isNil(index) || index < 0 ? null : this._parent._children[index];
			this._attachWidgetAt(this.widget$, this._parent.anchor$(this.widget$), fujion.$(ref));
			this._attachAncillaries();
		},

		_attachAncillaries: function() {
			_.forOwn(this._ancillaries, ancillary => {
				const ancillary$ = fujion.$(ancillary);
				const attach = ancillary$.data('attach') || _attach;
				attach(ancillary$);
			});

			function _attach(ancillary$) {
				const oldparent$ = ancillary$.data('oldparent');
				oldparent$ ? oldparent$.append(ancillary$) : null;
			}
		},

		/**
		 * Attaches a widget to the DOM at the specified reference point.
		 *
		 * @param {jquery} widget$ The widget to attach.
		 * @param {jquery} parent$ The widget to become the parent (if nil, must specify <code>ref$</code>).
		 * @param {jquery} ref$ The reference point for insertion (if nil, must specify <code>parent$</code>).
		 */
		_attachWidgetAt: function(widget$, parent$, ref$) {
			if (ref$) {
				ref$.before(widget$);
			} else {
				parent$.append(widget$);
			}
		},

		_detachAncillaries: function(destroy) {
			_.forOwn(this._ancillaries, (ancillary, key, map) => {
				const ancillary$ = fujion.$(ancillary);
				const wgt = fujion.widget.isWidget(ancillary) ? ancillary : null;

				if (destroy) {
					delete map[key];
					wgt ? wgt.destroy() : ancillary$.remove();
				} else {
					ancillary$.data('oldparent', ancillary$.parent());
					ancillary$.detach();
				}
			});
		},

		/*------------------------------ Events ------------------------------*/

		/**
		 * Establish forwarding of an event from its source to this widget.
		 *
		 * @param {jquery} source$ The source of the event.
		 * @param {string} sourceEvent The name of the source event.
		 * @param {string} [forwardEvent] The name of the forwarded event
		 * 	(defaults to same as sourceEvent).
		 */
		forward: function(source$, sourceEvent, forwardEvent) {
			source$.on(sourceEvent, event => {
				const forward = $.extend({}, event);
				forward.type = forwardEvent || sourceEvent;
				this.widget$.triggerHandler(forward);
			})
		},

		/**
		 * Turns on/off server forwarding of one or more event types.
		 *
		 * @param {string} eventTypes Space-delimited list of event types.
		 * @param {boolean} [noforward] If false, forwarding is enabled; if true, disabled.
		 */
		forwardToServer: function(eventTypes, noforward) {
			const fwd = this.getState('forwarding');
			const types = fujion.stringToSet(eventTypes, ' ');
			noforward ? fujion.removeFromSet(fwd, types) : _.assign(fwd, types);

			if (this.widget$) {
				this.widget$[noforward ? 'off' : 'on'](eventTypes, fujion.event.sendToServer);
			}
		},

		/**
		 * Notify server of a state change.
		 *
		 * @param {string} state Name of the state.
		 * @param {*} value New value of the state.
		 */
		stateChanged: function(state, value) {
			this.trigger('statechange', {state: state, value: value});
		},

		/**
		 * Trigger an event on the widget.
		 *
		 * @param {string || Event} event The event to be triggered.
		 * @param {object} [params] Additional params to be included.
		 * @param {boolean} [noSend] If true, don't send to server.
		 */
		trigger: function(event, params, noSend) {
			event = _.isString(event) ? $.Event(event) : event;

			if (noSend) {
				event.fujion_nosend = true;
			}

			this.widget$ ? this.widget$.triggerHandler(event, params) : null;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		/**
		 * Removes this widget from its parent widget and destroys the associated widget$ and its ancillaries.
		 */
		destroy: function() {
			if (this._parent) {
				this._parent.removeChild(this, true);
			} else {
				this._untrackRender();
				this._detachAncillaries(true);
				this.widget$ ? this.widget$.remove() : null;
				this.widget$ = null;
				fujion.widget.unregister(this.id);
			}
		},

		init: function() {
			// Override to perform additional initializations
		},

		_init: function(parent, props, state) {
			_.assign(this, props);
			this._parent = parent;
			this._children = this.cntr ? [] : null;
			this._ancillaries = {};
			this.widget$ = null;
			this.wclazz = this.wclazz || 'fujion_' + this.wclass.toLowerCase();
			this._rendering = false;
			this._state = {};
			this._state.forwarding = {statechange: true};
			this.initState(state, true);
			this.init();
			this.rerender();
		},

		/*------------------------------ Other ------------------------------*/

		/**
		 * Creates a translucent mask over this widget.
		 */
		addMask: function(label, popup) {
			this.removeMask();
			const zindex = this.widget$.fujion$zindex() + 1;
			this._mask$ = this.widget$.fujion$mask(zindex).append('<span>').css('display', 'flex');
			const span$ = this._mask$.children().first();
			span$.text(label).css('display', label ? '' : 'none');
			popup ? this.contextMenu(this._mask$, popup) : null;
		},

		/**
		 * Convenience method for setting an attribute value.
		 *
		 * @param {string} attr The attribute name.
		 * @param value The new attribute value.
		 * @param {jquery} tgt$ The jquery object to receive the new attribute value.  If not
		 * 	specified, this widget's widget$ object will be used.
		 */
		attr: function(attr, value, tgt$) {
			tgt$ = tgt$ || this.widget$;
			tgt$ ? tgt$.fujion$attr(attr, value) : null;
		},

		/**
		 * Locates the named widget within the namespace of this widget.
		 *
		 * @param {string} name The name of the widget sought.
		 * @return {Widget} The widget sought, or nil result if not found.
		 */
		findByName: function(name) {
			return _find(this.getNamespace());

			function _find(wgt) {
				if (wgt && wgt.getState('name') !== name) {
					wgt.forEachChild(child => {
						const wgt2 = _find(child);

						if (wgt2) {
							wgt = wgt2;
							return false;
						}
					});
				}

				return wgt;
			}
		},

		/**
		 * Returns the index position of this child widget within its parent widget.
		 *
		 * @return {number} The index of this child widget, or -1 if it has no parent widget.
		 */
		getIndex: function() {
			return this._parent ? this._parent.getChildIndex(this) : -1;
		},

		/**
		 * Returns the widget representing the enclosing namespace for this widget.
		 *
		 * @return {Widget} The widget representing the enclosing namespace.
		 */
		getNamespace: function() {
			let wgt = this.nmsp ? this._parent : this;
			let last = wgt;

			while (wgt && !wgt.nmsp) {
				last = wgt;
				wgt = wgt._parent;
			}

			return last;
		},

		/**
		 * Convenience method for setting a property value.
		 *
		 * @param {string} prop The property name.
		 * @param value The new property value.
		 * @param {jquery} tgt$ The jquery object to receive the new property value.  If not
		 * 	specified, this widget's widget$ object will be used.
		 */
		prop: function(prop, value, tgt$) {
			tgt$ = tgt$ || this.widget$;
			tgt$ ? tgt$.fujion$prop(prop, value) : null;
		},

		/**
		 * Removes any existing mask;
		 */
		removeMask: function() {
			if (this._mask$) {
				this._mask$.remove();
				delete this._mask$;
			}
		},

		/**
		 * Convenience method for resolving embedded EL references.
		 *
		 * @param {string} v Value containing EL references.
		 * @param {string} [pfx] Optional EL prefix (defaults to '$').
		 * @return {string} Input value with EL references resolved.
		 */
		resolveEL: function(v, pfx) {
			return fujion.resolveEL(this, v, pfx);
		},

		/**
		 * Returns the identifier for the specified subcomponent.
		 *
		 * @param {string} sub The subcomponent name.
		 * @return {string} The subcomponent's identifier.
		 */
		subId: function(sub) {
			return this.id + '-' + sub;
		},

		/**
		 * Returns the subcomponent for the specified identifier.
		 *
		 * @param {string} sub The subcomponent name.
		 * @return {jquery} The subcomponent (never null).
		 */
		sub$: function(sub) {
			const id = '#' + this.subId(sub);
			sub = this.widget$ ? this.widget$.find(id) : null;
			return sub && sub.length > 0 ? sub : $(id);
		},

		/*------------------------------ Rendering ------------------------------*/

		/**
		 * Called after rendering is complete, including attachment to DOM.
		 * Override to apply event handlers, etc.
		 */
		afterRender: _.noop,

		/**
		 * Called after the widget's DOM elements are constructed, but before
		 * applying state values or attaching to DOM.
		 */
		beforeRender: _.noop,

		rerender: function() {
			if (this._rendering) {
				return;
			}

			try {
				this._rendering = true;
				this._untrackRender();

				if (this.isContainer()) {
					this.forEachChild(child => child.detach());
				}

				const old$ = this.widget$;

				this.widget$ = this.render$()
					.data('fujion_widget', this)
					.data('fujion_wclass', this.wclass)
					.first()
					.attr('id', this.id);

				this.beforeRender();
				this.syncState();

				if (old$) {
					old$.replaceWith(this.widget$);
				} else if (this._parent) {
					this._parent.addChild(this);
				}

				if (this.isContainer()) {
					this.forEachChild(function(child){child._attach(-1);});
				}
			} finally {
				this._rendering = false;
			}

			this.afterRender();
			this.trackrender ? this._trackRender() : null;
		},

		/**
		 * Returns the jquery object representing the rendered DOM for
		 * this widget.
		 */
		render$: function() {
			throw new Error('No rendering logic supplied for ' + this.wclass);
		},

		_trackRender: function() {
			const self = this;
			this._untrackRender();

			if (!_hasRendered()) {
				if (window.IntersectionObserver) {
					this._renderobserver = new IntersectionObserver(_hasRendered, {root: fujion.widget._page.widget$[0]});
					this._renderobserver.observe(this.widget$[0]);
				} else {
					const intrvl = setInterval(_hasRendered, 1000);
					this._renderobserver = {
						disconnect: function () {
							clearInterval(intrvl);
						}
					};
				}
			}

			function _hasRendered() {
				if (self.widget$.is(':visible')) {
					self._untrackRender();
					self.trigger('render');
					return true;
				}
			}
		},

		_untrackRender: function() {
			if (this._renderobserver) {
				this._renderobserver.disconnect();
				delete this._renderobserver;
			}
		},

		/**
		 * Returns the DOM template(s) associated with the specified key(s).
		 *
		 * @param {string...} keys One or more keys.  A key may be prefixed with
		 * 		a state name followed by a colon to indicate that the template
		 *      should be included only if the state has a truthy value.  If no
		 *      state name precedes the colon, the key name is used (i.e.,
		 *      ":xxx" is a shortcut for "xxx:xxx").
		 * @return {string} A concatenation of the DOM templates associated
		 * 		with the specified keys.
		 */
		getDOMTemplate: function() {
			let result = '';

			for (let i = 0; i < arguments.length; i++) {
				let key = arguments[i];
				const j = key.indexOf(':');

				if (j >= 0) {
					const state = key.substring(0, j);
					key = key.substring(j + 1);

					if (!this.getState(state.length ? state : key)) {
						continue;
					}
				}

				const tmpl = fujion.widget._domTemplates[key];

				if (!_.isNil(tmpl)) {
					result += tmpl;
				}
			}

			return result;
		},

		/*------------------------------ State ------------------------------*/

		/**
		 * Invoke a state's setter function.
		 *
		 * @param {string} key The name of the state.
		 * @param {*} old The previous value of the state.
		 */
		applyState: function(key, old) {
			if (!key.startsWith('_')) {
				const fn = this['s_' + key];
				const value = this._state[key];

				if (!fn || !_.isFunction(fn)) {
					throw new Error('Unrecognized state for ' + this.wclass + ': ' + key);
				}

				fn.call(this, value, old);
			}
		},

		/**
		 * Returns the current state for the specified key.
		 *
		 * @param {string} key The key for the requested state.
		 * @return {*} The value of the requested state.
		 */
		getState: function(key) {
			return this._state[key];
		},

		/**
		 * Returns true if there is a state associated with the specified key.
		 *
		 * @param {string} key The key for the state of interest.
		 * @return {boolean} True if a state is present for the key.
		 */
		hasState: function(key) {
			const value = this._state[key];
			return !_.isNil(value) && value !== '';
		},

		/**
		 * Initializes the widget state to the specified values.  If overwrite is
		 * false, the supplied values will not overwrite existing values in the
		 * current state.  Otherwise, any existing values in the current state
		 * will be replaced by those supplied.
		 *
		 * @param {object} state A map of name-value pairs.
		 * @param {boolean} [overwrite] If true, existing state values will be
		 * 		overwritten.
		 */
		initState: function(state, overwrite) {
			if (state) {
				return overwrite ? _.assign(this._state, state) : _.defaults(this._state, state);
			}
		},

		/**
		 * Updates the saved state for the specified function and arguments.
		 *
		 * @param {string} key The name of the setter function.
		 * @param {*} value The value of the last setter invocation.
		 * @return {boolean} True if the state changed.
		 */
		setState: function(key, value) {
			const oldValue = this._state[key];

			if (!_.isEqual(value, oldValue)) {
				delete this._state[key];
				this._state[key] = value;
				return true;
			}
		},

		/**
		 * Calls setters for specified or all saved states.  If parameters are specified,
		 * they are assumed to be named states to synchronize.  Otherwise, all states
		 * are synchronized.
		 */
		syncState: function() {
			const states = arguments.length ? _.pick(this._state, arguments) : this._state;
			_.forOwn(states, (value, key) => this.applyState(key));
		},

		/**
		 * Updates the value for the specified state, and invokes its setter
		 * function if the value changed from the previous value or if rendering
		 * is active.
		 *
		 * @param {string} key The name of the state.
		 * @param {*} value The new value for the state.
		 * @param {boolean} [fromServer] If true, do not sync state back to server.
		 * @return {boolean} True if the state value changed.
		 */
		updateState: function(key, value, fromServer) {
			const old = this._state[key];
			const changed = this.setState(key, value);

			if (changed || this._rendering) {
				this.applyState(key, old);

				if (changed && !fromServer) {
					this.stateChanged(key, value);
				}
			}

			return changed;
		},

		/**
		 * Add / remove text content to / from widget.
		 *
		 * @param {string} v Text content to add (or nil to remove).
		 */
		s_content: function(v) {
			let span$ = this.sub$('content');

			if (!v) {
				span$.remove();
			} else {
				if (span$.length === 0) {
					const dom = this.resolveEL('<span id="${id}-content"></span>');
					span$ = $(dom).appendTo(this.widget$);
				}

				span$.text(v);
			}
		},

		/**
		 * Restore all server forwards.
		 */
		s_forwarding: function(v) {
			this.widget$.on(fujion.setToString(v, ' '), fujion.event.sendToServer);
		},

		/**
		 * Assign name associated with the widget.
		 *
		 * @param {string} v Name value.
		 */
		s_name: function(v) {
			this.attr('data-fujion-name', v);
		}

	});

	/******************************************************************************************************************
	 * Null widget.  Used as a NOP placeholder.
	 ******************************************************************************************************************/

	fujion.widget.NullWidget = fujion.widget.BaseWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		/**
		 * Returns a NOP placeholder.
		 */
		render$: function() {
			return $();
		}

	});

	/******************************************************************************************************************
	 * Non-UI widget base class
	 ******************************************************************************************************************/

	fujion.widget.MetaWidget = fujion.widget.BaseWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		/**
		 * Extend renderReal$ to also render the real widget.  The widget$ returned
		 * by render$ is really just a NOP placeholder.
		 */
		render$: function() {
			this._detachAncillaries(true);
			this.real$ = this.renderReal$();

			if (this.real$) {
				this.real$
					.data('fujion_widget', this)
					.appendTo(this.realAnchor$)
					.attr('id', this.subId('real'));
				this._ancillaries.real$ = this.real$;
			}

			return $('<!-- ' + this.id + ' -->');
		},

		/**
		 * Return rendering for the real widget.
		 */
		renderReal$: function() {
			return null;
		}

	});

	/******************************************************************************************************************
	 * UI widget base class
	 ******************************************************************************************************************/

	fujion.widget.UIWidget = fujion.widget.BaseWidget.extend({

		/*------------------------------ Events ------------------------------*/

		reportSize : function() {
			const self = this;
			requestAnimationFrame ? requestAnimationFrame(_reportSize) : setTimeout(_reportSize, 100);

			function _reportSize() {
				const p = self.widget$.position();

				self.trigger('resize', {
					left: p.left,
					top: p.top,
					width: self.widget$.outerWidth(),
					height: self.widget$.outerHeight()
				});
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({_clazz: this.wclazz, clazz: '', visible: true});
		},

		/*------------------------------ Other ------------------------------*/

		contextMenu: function(ele$, popup) {
			ele$.off('contextmenu.fujion');
			popup ? ele$.on('contextmenu.fujion', _showContextPopup) : null;

			function _showContextPopup(event) {
				const wgt = fujion.wgt(popup);

				if (!wgt) {
					ele$.off('contextmenu.fujion');
				} else {
					wgt.open({
						my: 'left top',
						at: 'right bottom',
						of: event
					});
				}

				return false;
			}
		},

		hoverPopup: function(ele$, popup) {
			let start, stop, started, last;
			_reset();
			popup ? ele$.on('mouseenter.fujion', _init) : null;
			popup ? ele$.on('mousemove.fujion', _update) : null;
			popup ? ele$.on('mouseleave.fujion', _stop) : null;

			function _reset() {
				ele$.off('mouseenter.fujion');
				ele$.off('mousemove.fujion');
				ele$.off('mouseleave.fujion');
				_clearTimers();
			}

			function _clearTimers() {
				start ? clearTimeout(start) : null;
				stop ? clearTimeout(stop) : null;
				start = stop = last = null;
				started = false;
			}

			function _popup() {
				const wgt = fujion.wgt(popup);
				return wgt ? wgt : _reset();
			}

			function _init(event) {
				if (_popup()) {
					_clearTimers();
					last = event;
					start = setTimeout(_start, fujion.globalOptions.popupDelay);
					stop = setTimeout(_stop, fujion.globalOptions.popupDelay + fujion.globalOptions.popupDuration);
				}

				return false;
			}

			function _start() {
				start = null;
				started = true;
				_show(last);
			}

			function _show(event, wgt) {
				wgt = wgt || _popup();

				if (wgt) {
					event.pageX += 10;
					wgt.open({
						my: 'left top',
						of: event
					});
				}
			}

			function _update(event) {
				if (started) {
					const wgt = _popup();

					if (wgt) {
						wgt.isOpen() ? _show(event, wgt) : _stop(event, wgt);
					}
				} else {
					last = event;
				}

				return false;
			}

			function _stop(event, wgt) {
				_clearTimers();
				wgt = wgt || _popup();
				wgt ? wgt.close() : null;
				return false;
			}
		},

		focus: function(focus) {
			const sel = ':input:enabled:visible:not([readonly])';
			const tgt = this.widget$.is(sel) ? this.widget$.get(0) : this.widget$.find(sel).get(0);

			if (tgt) {
				focus = typeof focus === 'boolean' ? focus : true;
				setTimeout(() => focus ? tgt.focus() : tgt.blur(), 10);
			}
		},

		input$: function() {
			const input$ = this.sub$('inp');
			return input$.length ? input$ : this.widget$;
		},

		print: function(options) {
			const ele$ = options && options.selector ? $(options.selector) : this.anchor$();
			ele$.print(options);
		},

		/**
		 * Replace one class with another.
		 *
		 * @param {string} class1 The class to remove (may be nil).
		 * @param {string} class2 The class to add (may be nil).
		 * @param {boolean} [flip] If true, class1 and class2 meanings are flipped.
		 * @return {boolean} True if a class was added or removed.
		 */
		replaceClass: function(class1, class2, flip) {
			let result = class1 && this.toggleClass(class1, flip);
			result |= class2 && this.toggleClass(class2, !flip);
			return result;
		},

		scrollIntoView: function() {
			const sp$ = this.widget$.scrollParent();
			sp$ ? sp$.scrollTo(this.widget$) : null;
		},

		subclazz: function(sub, wclazz) {
			return sub ? (wclazz || this.wclazz) + '-' + sub.toLowerCase() : null;
		},

		/**
		 * Use this method to add or remove fixed classes.  Fixed classes will always be added
		 * to the element regardless of the classes specified via the class property.
		 *
		 * @param {string} cls The classes to toggle.
		 * @param {boolean} [add] If true, add the class; if false, remove it; if missing, toggle it.
		 * @return {boolean} True if any class was added or removed.
		 */
		toggleClass: function(cls, add) {
			const _clazz = this.getState('_clazz').split(' ');
			const w$ = this.widget$;
			let changed = false;
			cls = cls.split(' ');
			_.forEach(cls, _toggle);

			if (changed) {
				this.setState('_clazz', _clazz.join(' '));
			}

			return changed;

			function _toggle(cls) {
				const i = _clazz.indexOf(cls);
				const exists = i !== -1;
				const remove = _.isNil(add) ? exists : !add;

				if (exists === remove) {
					w$ ? w$.toggleClass(cls, !remove) : null;
					remove ? _clazz.splice(i, 1) : _clazz.push(cls);
					changed = true;
				}
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		getDragHelper: function() {
			return fujion.clone(this.widget$, this.isContainer() ? 0 : -1);
		},

		/*------------------------------ State ------------------------------*/

		s_badge: function(v) {
			this.sub$('badge').text(v).fujion$swapClasses('badge-pos', 'badge-neg', v > 0).fujion$show(v);
		},

		s_balloon: function(v) {
			if (v) {
				this.widget$.attr('aria-label', v)
					.attr('data-balloon-pos', 'right')
					.attr('data-balloon-visible', true)
					.attr('data-balloon-length', 'fit');
			} else {
				this.widget$.removeAttr('aria-label data-balloon-pos data-balloon-visible data-balloon-length');
			}
		},

		s_clazz: function(v) {
			const clazz = this.getState('_clazz') + (v ? ' ' + v : '');
			this.attr('class', clazz);
		},

		s_context: function(v) {
			this.contextMenu(this.widget$, v);
		},

		s_css: function(v) {
			let inline$ = this._ancillaries.inline$;

			if (v) {
				if (!inline$) {
					this._ancillaries.inline$ = inline$ =
						$('<style>').appendTo(fujion.head$).attr('id', this.subId('inline'));
				}
				inline$.text(fujion.qualifyCSS(v, '#' + this.id));
			} else if (inline$) {
				inline$.remove();
				delete this._ancillaries.inline$;
			}
		},

		s_disabled: function(v) {
			this.attr('disabled', v, this.input$());
			this.attr('disabled', v, this.sub$('btn'));
		},

		s_dragid: function(v) {
			const self = this;
			const active = !!this.widget$.draggable('instance');
			const newactive = !_.isNil(v);
			this._dragids = newactive ? fujion.stringToSet(v, ' ') : null;

			if (newactive !== active) {
				if (active) {
					this.widget$.draggable('destroy');
				} else {
					this.widget$.draggable({
						cancel: null,
						helper: _helper,
						start: _start,
						stop: _stop,
						appendTo: fujion.root$,
						iframeFix: true
					});
				}
			}

			function _helper() {
				const ele = self.getDragHelper();
				ele.className += ' fujion-dragging';
				return ele;
			}

			function _start(event, ui) {
				self._dragging = true;
				self.widget$.draggable('option', 'cursorAt', {
				    left: Math.floor(ui.helper.width() / 2),
				    top: Math.floor(ui.helper.height() / 2)
				});
			}

			function _stop() {
				self._dragging = false;
			}
		},

		s_dropid: function(v) {
			const self = this;
			const active = !!this.widget$.droppable('instance');
			const newactive = !_.isNil(v);
			this._dropids = newactive ? fujion.stringToSet(v, ' ') : null;

			if (newactive !== active) {
				if (active) {
					this.widget$.droppable('destroy');
				} else {
					this.widget$.droppable({
						accept: _canDrop,
						tolerance: 'pointer',
						classes: {
							'ui-droppable-hover': 'fujion-droppable'
						}
					});
				}
			}

			function _canDrop(draggable$) {
				const wgt = fujion.wgt(draggable$);
				const dragids = wgt ? wgt._dragids : null;
				const dropids = self._dropids;
				let result = false;

				if (dragids && dropids) {
					if (dragids['*'] || dropids['*']) {
						return true;
					}

					_.forOwn(dropids, (x, dropid) => {
						if (dragids[dropid]) {
							result = true;
							return false;
						}
					});
				}

				return result;
			}

		},

		s_hint: function(v) {
			const self = this;
			const active = this.widget$.uitooltip('instance');
			const opt = fujion.globalOptions;
			this.attr('data-fujion-hint', !!v);
			_cancelClose();

			if (v && !active) {
				this.widget$.uitooltip({
					content: _tt,
					items: '[data-fujion-hint]',
					track: opt.hintTrack,
					open: _autoClose,
					close: _cancelClose,
					show: {
						effect: opt.hintEffect,
						delay: opt.hintDelay,
						duration: opt.hintTransition
					}
				}).on('click.hint', _clickClose);
            } else if (!v && active) {
		        this.widget$.uitooltip('destroy').off('click.hint');
            }

			function _tt() {
		        return fujion._disableHints ? null : self.getState('hint');
            }

		    function _autoClose(event, ui) {
				self._hintTimeout = setTimeout(() => {
					delete self._hintTimeout;
					$(ui.tooltip).hide();
				}, opt.hintDuration)
			}

		    function _cancelClose() {
		    	if (self._hintTimeout) {
		    		clearTimeout(self._hintTimeout);
		    		delete self._hintTimeout;
				}
			}

		    function _clickClose() {
		    	self.widget$.uitooltip('close');
			}
		},

		s_keycapture: function(v) {
			const self = this;

			if (v) {
				this._keycapture = v.split(' ');
				this.widget$.on('keydown.fujion', _keyevent);
			} else {
				this._keycapture = null;
				this.widget$.off('keydown.fujion');
			}

			function _keyevent(event) {
				const val = fujion.event.toKeyCapture(event);

				if (self._keycapture.indexOf(val) >= 0) {
					fujion.event.stop(event);
					event.type = 'keycapture';
					fujion.event.sendToServer(event);
				}
			}
		},

		s_popup: function(v) {
			this.hoverPopup(this.widget$, v);
		},

		s_style: function(v) {
			this.attr('style', v);
		},

		s_tabindex: function(v) {
			this.attr('tabindex', v < 0 ? null : v, this.input$());
		},

		s_visible: function(v) {
			this.toggleClass('d-none', !v);
		}

	});

	/******************************************************************************************************************
	 * Connector widget.
	 ******************************************************************************************************************/

	fujion.widget.Connector = fujion.widget.BaseWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		onRemoveChild: function(child, destroyed, anchor$) {
			this._parent ? this._parent.removeChild(this, true) : null;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			if (this._children.length) {
				this._children[0].destroy();
			}

			this._super();
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $(this.resolveEL(this._dom));
		}

	});

	fujion.widget.Connector.create = function(dom, child) {
		const wgt = fujion.widget.create(null, {wclass: 'Connector', id: child.subId('cnc'), cntr: true, _dom: dom});
		wgt.addChild(child);
		return wgt;
	};

	/******************************************************************************************************************
	 * Base class for widgets wrapping input elements
	 ******************************************************************************************************************/

	fujion.widget.InputWidget = fujion.widget.UIWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({readonly: false});
		},

		/*------------------------------ Other ------------------------------*/

		selectAll: function() {
			this.selectRange(0, 99999);
		},

		selectRange: function(start, end) {
			try {
				this.input$().get(0).setSelectionRange(start, end);
			} catch (e) {
			}
		},

		/*------------------------------ State ------------------------------*/

		s_maxlength: function(v) {
			this.attr('maxlength', v, this.input$());
		},

		s_maxvalue: function(v) {
			this.attr('max', v, this.input$());
		},

		s_minvalue: function(v) {
			this.attr('min', v, this.input$());
		},

		s_pattern: function(v) {
			this.attr('pattern', v, this.input$());
		},

		s_placeholder: function(v) {
			this.attr('placeholder', v, this.input$());
		},

		s_readonly: function(v) {
			this.attr('readonly', v, this.input$());
		},

		s_required: function(v) {
			this.attr('required', v, this.input$());
		},

		s_value: function(v) {
			this.input$().val(v);
		}

	});

	/******************************************************************************************************************
	 * Base class for widgets the allow text entry.
	 ******************************************************************************************************************/

	fujion.widget.InputboxWidget = fujion.widget.InputWidget.extend({

		/*------------------------------ Events ------------------------------*/

		fireChanged: function() {
			this._changed = false;
			this.trigger('change', {value: this._value()});
		},

		handleBlur: function(event) {
			const msg = event.target.validationMessage;
			fujion.wgt(event.target).updateState('balloon', msg ? msg : null);

			if (this._changed && !msg) {
				this.fireChanged();
			}
		},

		handleInput: function(event) {
			const ele = this.input$().get(0);
			const value = ele.value;

			if (value.length && this.validate && !this.validate(value)) {
				event ? fujion.event.stop(event) : null;
				const cpos = ele.selectionStart - 1;
				ele.value = this.getState('value');
				ele.selectionStart = cpos;
				ele.selectionEnd = cpos;
				return;
			}

			this.setState('value', value);

			if (this._synchronized) {
				this.fireChanged();
			} else {
				this._changed = true;
			}
		},

		handleKeyup: function(event) {
			if (event.keyCode === 13) {
				this.trigger('enter');
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._synchronized = false;
			this._changed = false;
			this.forwardToServer('change');
			this.toggleClass('fujion_inputbox', true);
		},

		/*------------------------------ Other ------------------------------*/

		clear: function() {
			this.input$().val('');
		},

		_value: function() {
			return this.input$().get(0).value;
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			const input$ = this.input$();
			input$.on('input propertychange', this.handleInput.bind(this));
			input$.on('blur', this.handleBlur.bind(this));
			input$.on('keyup', this.handleKeyup.bind(this));
			this._constraint ? input$.on('keypress', fujion.event.constrainInput.bind(this, this._constraint)) : null;
			this._step ? input$.attr('step', this._step) : null;
		},

		render$: function() {
			return $(this.resolveEL('<span><input id="${id}-inp" type="${_type}"></span>'));
		},

		/*------------------------------ State ------------------------------*/

		s_synced: function(v) {
			this._synchronized = v;
		}

	});

	/******************************************************************************************************************
	 * An integer input box widget
	 ******************************************************************************************************************/

	fujion.widget.NumberboxWidget = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleSpinStart: function(up, event) {
			if (!this.getState('disabled')) {
				this._spinning = true;
				this._spin(up, 1000);
				this.input$().focus();
			}

			return false;
		},

		handleSpinStop: function(event) {
			clearTimeout(this._spinning);
			delete this._spinning;
			return false;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._type = 'text';
			this._constraint = /[\d+-]/;
			this._partial = /^[+-]?$/;
			this._super();
			this.initState({step: 0});
		},

		/*------------------------------ Other ------------------------------*/

		validate: function(value) {
			const partial = this._partial.test(value);
			value = partial ? 0 : _.toNumber(value);
			return partial || (!_.isNaN(value) && this.validateRange(value));
		},

		validateRange: function(value) {
			const min = _.defaultTo(this.getState('minvalue'), this._min);
			const max = _.defaultTo(this.getState('maxvalue'), this._max);
			value = value === undefined ? +this.input$().val() : +value;
			return value >= min && value <= max;
		},

		_spin: function(up, intrvl) {
			if (this._spinning) {
				const step = +this.getState('step');
				const val = +this._value() + (up ? step : -step);
				this.input$().val(val);
				this.handleInput();
				this._spinning = setTimeout(this._spin.bind(this, up, 100), intrvl);
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.find('.fa-chevron-up')
				.on('mousedown', this.handleSpinStart.bind(this, true))
				.on('mouseup', this.handleSpinStop.bind(this));
			this.widget$.find('.fa-chevron-down')
				.on('mousedown', this.handleSpinStart.bind(this, false))
				.on('mouseup', this.handleSpinStop.bind(this));
		},

		render$: function() {
			const dom = '<span>'
				+ '<input id="${id}-inp" type="${_type}">'
				+ '<span id="${id}-spn">'
				+ '<span class="fa fa-chevron-up"></span>'
				+ '<span class="fa fa-chevron-down"></span>'
				+ '</span>'
				+ '</span>';
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_minvalue: function(v) {
			this.attr('min', v, this.input$());
		},

		s_maxvalue: function(v) {
			this.attr('max', v, this.input$());
		},

		s_step: function(v) {
			const spn$ = this.sub$('spn');
			v ? spn$.show() : spn$.hide();
		}
	});

	/******************************************************************************************************************
	 * Main page widget
	 ******************************************************************************************************************/

	fujion.widget.Page = fujion.widget.BaseWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			if (this._parent) {
				throw new Error('Page may not have a parent.')
			}

			fujion.widget._page = this;
			this._super();
			this.initState({closable: true});
		},

		afterInitialize: function() {
			fujion.root$.css('visibility', 'visible');
		},

		beforeInitialize: function(globalOptions) {
			globalOptions ? _.assign(fujion.globalOptions, globalOptions) : null;
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div class="fujion_page">').appendTo(fujion.root$);
		},

		/*------------------------------ State ------------------------------*/

		s_closable: function(v) {
			fujion._canClose = v;
		},

		s_disableHints: function(v) {
			fujion._disableHints = v;
		},

		s_image: function(v) {
			this.sub$('img').remove();

			if (v) {
				const img$ = $(this.resolveEL('<link id="${id}-img" rel="shortcut icon">')).appendTo(fujion.head$);
				img$.attr('href', v);
			}
		},

		s_title: function(v) {
			fujion.head$.find('>title').text(v);
		}

	});

	/******************************************************************************************************************
	 * Style widget
	 ******************************************************************************************************************/

	fujion.widget.Style = fujion.widget.MetaWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		realAnchor$: fujion.head$,

		renderReal$: function() {
			return $(this.getState('src') ? '<link type="text/css" rel="stylesheet">' : '<style>');
		},

		/*------------------------------ State ------------------------------*/

		s_content: function(v) {
			this.rerender();
			this.real$.text(v);
		},

		s_src: function(v) {
			this.rerender();
			this.attr('href', v, this.real$);
		}

	});

	/******************************************************************************************************************
	 * Script widget
	 ******************************************************************************************************************/

	fujion.widget.Script = fujion.widget.BaseWidget.extend({

		/*------------------------------ Other ------------------------------*/

		compile: function(script) {
			this._script = script ? Function('self', 'fujion', 'vars', this.resolveEL(script, '#')).bind(this) : null;
		},

		execute: function(self, vars) {
			if (this._script) {
				this.trigger('scriptExecution', {data: this._script(self, fujion, vars)});
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<script>');
		},

		/*------------------------------ State ------------------------------*/

		s_content: function(v) {
			this.compile(v);
		},

		s_src: function(v) {
			this.compile();

			if (v) {
				$.ajax({
					url: v,
					async: true,
					dataType: 'text'
				}).done(script => this.compile(script));
			}
		}
	});

	/******************************************************************************************************************
	 * A timer widget
	 ******************************************************************************************************************/

	fujion.widget.Timer = fujion.widget.BaseWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		detach: function() {
			this.stop();
			this._super();
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._timer = null;
			this._interval = 0;
			this._repeat = -1;
		},

		destroy: function() {
			this.stop();
			this._super();
		},

		/*------------------------------ Other ------------------------------*/

		start: function() {
			const self = this;
			let count = 0;

			if (!this.timer && this._interval > 0) {
				this.timer = setInterval(_trigger, this._interval);
				return true;
			}

			function _trigger() {
				if (!fujion.ws.isConnected()) {
					self.stop();
					return;
				}

				count++;

				if (self._repeat >= 0 && count > self._repeat) {
					self.updateState('running', false);
				}

				self.trigger('timer', {count: count, running: self.timer !== null});
			}
		},

		stop: function() {
			if (this.timer) {
				clearInterval(this.timer);
				this.timer = null;
				return true;
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<span>');
		},

		/*------------------------------ State ------------------------------*/

		s_interval: function(v) {
			if (v !== this._interval) {
				this._interval = v;

				if (this.stop()) {
					this.start();
				}
			}
		},

		s_repeat: function(v) {
			this._repeat = v;
		},

		s_running: function(v) {
			this[v ? 'start' : 'stop']();
		}

	});

	/******************************************************************************************************************
	 * Widget wrapping text content
	 ******************************************************************************************************************/

	fujion.widget.Content = fujion.widget.UIWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const text = document.createTextNode(this.getState('content'));
			return $(text);
		},

		/*------------------------------ State ------------------------------*/

		s_content: function(v) {
			this.rerender();
		}

	});

	/******************************************************************************************************************
	 * Widget wrapping html content
	 ******************************************************************************************************************/

	fujion.widget.Html = fujion.widget.UIWidget.extend( {

		/*------------------------------ Other ------------------------------*/

		_content: function(html) {
			this.widget$.empty();
			html ? this.widget$.html(html) : null;
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<span>');
		},

		/*------------------------------ State ------------------------------*/

		s_content: function(v) {
			this._content(v);
			this.setState('src', null);
		},

		s_src: function(v) {
			this.widget$.empty();
			this.setState('content', null);
			v ? this.widget$.load(v) : null;
		}

	});

	/******************************************************************************************************************
	 * Widget wrapping a native html element
	 ******************************************************************************************************************/

	fujion.widget.HtmlElement = fujion.widget.UIWidget.extend( {

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({tag: 'span'});
		},

		/*------------------------------ Other ------------------------------*/

		attribute: function(attr, value) {
			this.attr(attr, value);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<' + this.getState('tag') + '>');
		},

		/*------------------------------ State ------------------------------*/

		s_tag: function(v) {
			this.rerender();
		}

	});

	/******************************************************************************************************************
	 * A div widget
	 ******************************************************************************************************************/

	fujion.widget.Div = fujion.widget.UIWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div>');
		}

	});

	/******************************************************************************************************************
	 * A span widget
	 ******************************************************************************************************************/

	fujion.widget.Span = fujion.widget.UIWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<span>');
		}

	});

	/******************************************************************************************************************
	 * A popup widget
	 ******************************************************************************************************************/

	fujion.widget.Popup = fujion.widget.BaseWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.real$;
		},

		/*------------------------------ Events ------------------------------*/

		handleMove: function(event) {
			this._options.of = event.relatedTarget;
			this.real$.position(this._options);
		},

		_trigger: function(which, notself) {
			const relatedTarget = this._related$ ? this._related$.fujion$widget() : null;
			notself ? null : this.trigger($.Event(which, {relatedTarget: relatedTarget}));
			relatedTarget ? relatedTarget.trigger($.Event('popup' + which, {relatedTarget: this})) : null;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			fujion.widget.Popup.registerPopup(this, false);
			this._super();
		},

		/*------------------------------ Other ------------------------------*/

		beforeOpen: function() {
			return true;
		},

		close: function(notself, notothers) {
			if (this.isOpen()) {
				fujion.widget.Popup.registerPopup(this, false);
				this.real$.hide().fujion$track(this._related$, true);
				this._trigger('close', notself);
				this._related$ = null;
				this._options = null;
			}

			notothers ? null : fujion.widget.Popup.closePopups(this.real$);
		},

		isOpen: function() {
			return fujion.widget._popup[this.id];
		},

		open: function(options, notself) {
			let related$;
			options.collision = options.collision || 'flipfit';
			this._options = options;

			if (options.of.currentTarget) {
				related$ = fujion.$(options.of.currentTarget);
			} else {
				related$ = fujion.$(options.of);
				options.of = related$;
			}

			if (!this.isOpen() || (related$ && !related$.is(this._related$))) {
				fujion.widget.Popup.closePopups(related$);
				this._related$ = related$;

				if (!this.beforeOpen(related$)) {
					return;
				}

				this.real$.css('z-index', this._related$.fujion$zindex() + 1);
				this.real$.fujion$track(this._related$);
				this.real$.show().position(options);
				fujion.widget.Popup.registerPopup(this, true);
				this._trigger('open', notself);
			} else {
				this.real$.show().position(options);
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.real$.on('move', this.handleMove.bind(this));
			this._allowBubble ? null : this.real$.on('click', fujion.event.stopPropagation);
		},

		render$: function() {
			if (!this.real$) {
				this.real$ = this.renderReal$()
					.appendTo(fujion.root$)
					.attr('id', this.subId('real'))
					.attr('data-fujion-popup', true)
					.hide()
					.addClass(this.wclazz);
				this._ancillaries.real$ = this.real$;
			}

			return $('<span>');
		},

		renderReal$: function() {
			return $('<div>')
		}

	});

	fujion.widget.Popup.registerPopup = function(wgt, v) {
		if (!wgt.close) {
			throw new Error('Widget must implement close method.');
		}

		if (v) {
			fujion.widget._popup[wgt.id] = wgt;
		} else {
			delete fujion.widget._popup[wgt.id];
		}
	};

	fujion.widget.Popup.closePopups = function(parent$) {
		const parent = parent$ ? parent$.closest('*[data-fujion-popup]')[0] : null;

		_.forOwn(fujion.widget._popup, popup => {
			if (!parent || $.contains(parent, popup._related$[0])) {
				popup.close(false, true);
			}
		});
	};

	fujion.body$.on('click', function() {
		fujion.widget.Popup.closePopups();
	});

	/******************************************************************************************************************
	 * A toolbar widget
	 ******************************************************************************************************************/

	fujion.widget.Toolbar = fujion.widget.UIWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({alignment: 'START', orientation: 'HORIZONTAL'});
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div></div>');
		},

		/*------------------------------ State ------------------------------*/

		s_alignment: function(v, old) {
			v = this.subclazz(v ? v : 'start');
			old = old ? this.subclazz(old) : null;
			this.replaceClass(old, v);
		},

		s_orientation: function(v, old) {
			v = this.subclazz(v ? v : 'horizontal');
			old = old ? this.subclazz(old) : null;
			this.replaceClass(old, v);
		}

	});

	/******************************************************************************************************************
	 * Base class for widgets with a label
	 ******************************************************************************************************************/

	fujion.widget.LabeledWidget = fujion.widget.UIWidget.extend({

		/*------------------------------ State ------------------------------*/

		s_alignment: function(v) {
			this.toggleClass('fujion-labeled-start', v === 'START');
			this.toggleClass('fujion-labeled-center', v === 'CENTER');
			this.toggleClass('fujion-labeled-end', v === 'END');
		},

		s_label: function(v) {
			const lbl$ = this.sub$('lbl');
			(lbl$.length ? lbl$ : this.widget$).text(v);
		},

		s_position: function(v) {
			this.toggleClass('fujion-labeled-left', v === 'LEFT');
			this.toggleClass('fujion-labeled-right', v === 'RIGHT');
			this.toggleClass('fujion-labeled-top', v === 'TOP');
			this.toggleClass('fujion-labeled-bottom', v === 'BOTTOM');
		}

	});

	/******************************************************************************************************************
	 * Base class for widgets with a label and an image
	 ******************************************************************************************************************/

	fujion.widget.LabeledImageWidget = fujion.widget.LabeledWidget.extend({

		/*------------------------------ State ------------------------------*/

		s_image: function(v) {
			this.rerender();
		}

	});

	/******************************************************************************************************************
	 * A button widget
	 ******************************************************************************************************************/

	fujion.widget.Button = fujion.widget.LabeledImageWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({position: 'BOTTOM'})
			this.toggleClass('btn fujion-labeled', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom = '<button>'
				+ this.getDOMTemplate(':image', 'label')
				+ '</button>';

			return $(this.resolveEL(dom));
		}

	});

	/******************************************************************************************************************
	 * A hyperlink widget
	 ******************************************************************************************************************/

	fujion.widget.Hyperlink = fujion.widget.Button.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom = '<a>'
				+ this.getDOMTemplate(':image', 'label')
				+ '</a>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_href: function(v) {
			this.attr('href', v);
		},

		s_target: function(v) {
			this.attr('target', v);
		}

	});

	/******************************************************************************************************************
	 * A label widget
	 ******************************************************************************************************************/

	fujion.widget.Label = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<label></label>');
		}

	});

	/******************************************************************************************************************
	 * A caption widget
	 ******************************************************************************************************************/

	fujion.widget.Caption = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({position: 'LEFT', alignment: 'CENTER'});
			this.toggleClass('fujion-labeled', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom = '<span>'
				+ '<span id="${id}-inner" class="fujion-labeled-rel"></span>'
				+ this.getDOMTemplate('label')
				+ '</span>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_labelClass: function(v) {
			v = v ? v + ' ' : '';
			this.attr('class', v + 'fujion-labeled-label', this.sub$('lbl'));
		},

		s_labelStyle: function(v) {
			this.attr('style', v, this.sub$('lbl'));
		}

	});

	/******************************************************************************************************************
	 * A page control widget
	 ******************************************************************************************************************/

	fujion.widget.Paging = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleClick: function(event) {
			let page = this.getState('currentPage');
			const max = this.getState('maxPage');

			switch ($(event.currentTarget).data('fujion-pg')) {
				case -2: // Start
					page = 0;
					break;

				case -1: // Previous
					page = page - 1;
					break;

				case 1: // Next
					page = page + 1;
					break;

				case 2: // End
					page = max;
					break;
			}

			if (page >= 0 && page <= max) {
				this.setState('currentPage', page);
				this._update();
				this.trigger('change', {value: page});
			}

			return false;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({currentPage: 0, maxPage: 0, pageSize: 0, nav: {}});
			this.forwardToServer('change');
		},

		/*------------------------------ Other ------------------------------*/

		_update: function() {
			let label = this.getState('label') || '%c / %m';
			const page = this.getState('currentPage');
			const max = this.getState('maxPage');
			label = label.replace('%c', page + 1).replace('%m', max + 1);
			this.sub$('lbl').text(label);
			this.widget$.find('a').each((idx, ele) => {
				const a$ = $(ele);
				const pg = a$.data('fujion-pg');
				const disabled = !pg || (pg < 0 && page < 1) || (pg > 0 && page >= max);
				a$.parent().toggleClass('disabled', disabled);
				this.attr('tabindex', disabled ? -1 : null, a$);
			});
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this.widget$.find('a').on('click.fujion', this.handleClick.bind(this));
		},

		render$: function() {
			const dom =
				'<nav aria-label="pagination">'
				+ '<ul class="pagination">'
				+ '<li class="page-item">'
				+ '<a class="page-link" data-fujion-pg="-2">'
				+ '<span class="fa fa-angle-double-left" aria-hidden="true"></span>'
				+ '<span class="sr-only">${_state.nav.start}</span>'
				+ '</a>'
				+ '</li>'
				+ '<li class="page-item">'
				+ '<a class="page-link" data-fujion-pg="-1">'
				+ '<span class="fa fa-angle-left" aria-hidden="true"></span>'
				+ '<span class="sr-only">${_state.nav.previous}</span>'
				+ '</a>'
				+ '</li>'
				+ '<li class="page-item">'
				+ '<span id="${id}-lbl" class="fujion-labeled-label page-link disabled rounded-circle"></span>'
				+ '</li>'
				+ '<li class="page-item">'
				+ '<a class="page-link" data-fujion-pg="1">'
				+ '<span class="fa fa-angle-right" aria-hidden="true"></span>'
				+ '<span class="sr-only">${_state.nav.next}</span>'
				+ '</a>'
				+ '</li>'
				+ '<li class="page-item">'
				+ '<a class="page-link" data-fujion-pg="2">'
				+ '<span class="fa fa-angle-double-right" aria-hidden="true"></span>'
				+ '<span class="sr-only">${_state.nav.end}</span>'
				+ '</a>'
				+ '</li>'
				+ '</ul>'
				+ '</nav>'
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_currentPage: function(v) {
			this._update();
		},

		s_label: function(v) {
			this._update();
		},

		s_maxPage: function(v) {
			this._update();
		},

		s_nav: function(v) {
			this.rerender();
		},

		s_pageSize: function(v) {
			this._update();
		}

	});

	/******************************************************************************************************************
	 * A cell widget
	 ******************************************************************************************************************/

	fujion.widget.Cell = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom =
				'<div>'
				+ this.getDOMTemplate(':label')
				+ '</div>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_label: function(v, old) {
			if (!!old !== !!v) {
				this.rerender();
			}

			this._super();
		}

	});

	/******************************************************************************************************************
	 * A checkbox widget
	 ******************************************************************************************************************/

	fujion.widget.Checkbox = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.toggleClass('fujion-labeled', true);
		},

		/*------------------------------ Events ------------------------------*/

		handleChange: function(event, params) {
			const target = event.target;
			const checked = target.checked;
			this.setState('checked', checked);
			this._syncChecked(checked);
			target.value = checked;
			fujion.event.sendToServer(event, params);
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('change', this.handleChange.bind(this));
		},

		render$: function() {
			const dom =
				'<div>'
				+ '<input id="${id}-inp" type="checkbox" class="fujion-labeled-rel">'
				+ this.getDOMTemplate('labelfor')
				+ '</div>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_checked: function(v) {
			this.sub$('inp').prop('checked', v);
			this._syncChecked(v);
		},

		/**
		 * Sync checked state with other elements.  Does nothing by default.
		 */
		_syncChecked: _.noop

	});

	/******************************************************************************************************************
	 * A radio button widget
	 ******************************************************************************************************************/

	fujion.widget.Radiobutton = fujion.widget.Checkbox.extend({

		/*------------------------------ Other ------------------------------*/

		/**
		 * Returns the enclosing radio group, or null if there is none.
		 */
		getGroup: function() {
			let wgt = this._parent;

			while (wgt && wgt.wclass !== 'Radiogroup') {
				wgt = wgt._parent;
			}

			return wgt;
		},

		/**
		 * Returns the id of the enclosing radio group.  If there is no enclosing
		 * radio group, returns an empty string.
		 */
		getGroupId: function() {
			const group = this.getGroup();
			return group ? group.id : "";
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom =
				'<div>'
				+ '<input id="${id}-inp" type="radio" name="${getGroupId}" class="fujion-labeled-rel">'
				+ this.getDOMTemplate('labelfor')
				+ '</div>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		/**
		 * Update the checked button associated with the enclosing radio group.
		 */
		_syncChecked: function(checked) {
			const group = this.getGroup();

			if (group) {
				let previous = group.getState('_checked');
				previous = previous ? fujion.widget.find(previous) : null;

				if (checked) {
					if (previous && previous !== this) {
						previous.trigger('change', {value: false});
					}

					group.setState('_checked', this.id);
				} else if (previous === this) {
					group.setState('_checked', null);
				}
			}
		}

	});

	/******************************************************************************************************************
	 * Widget for grouping radio buttons
	 ******************************************************************************************************************/

	fujion.widget.Radiogroup = fujion.widget.Span.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({orientation: 'HORIZONTAL'});
		},

		/*------------------------------ State ------------------------------*/

		s_orientation: function(v) {
			this.toggleClass(this.subclazz(v.toLowerCase()), true);
			this.toggleClass(this.subclazz(v === 'VERTICAL' ? 'horizontal' : 'vertical'), false);
		}
	});

	/******************************************************************************************************************
	 * A menu popup widget
	 ******************************************************************************************************************/

	fujion.widget.Menupopup = fujion.widget.Popup.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._allowBubble = true;
		},

		/*------------------------------ Rendering ------------------------------*/

		renderReal$: function() {
			return $('<ul role="menu" class="dropdown-menu multi-level"></ul>');
		}

	});

	/******************************************************************************************************************
	 * A menu widget
	 ******************************************************************************************************************/

	fujion.widget.Menu = fujion.widget.LabeledImageWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this._ancillaries.popup ? this._ancillaries.popup.anchor$() : this._super();
		},

		onAddChild: function() {
			this._childrenUpdated();
		},

		onRemoveChild: function() {
			this._childrenUpdated();
		},

		/*------------------------------ Events ------------------------------*/

		handleClick: function(event) {
			if (this._children.length) {
				this.toggle();
				this.trigger(this.isOpen() ? 'open' : 'close');
			}
			return false;
		},

		handleOpenClose: function(event) {
			this.setState('_open', event.type === 'open');
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({_open: false});
			this.forwardToServer('open close');
		},

		/*------------------------------ Other ------------------------------*/

		open: function() {
			if (this._ancillaries.popup) {
				this.close();

				this._ancillaries.popup.open({
					my: 'left top',
					at: 'left bottom',
					of: this.widget$
				});

				this.setState('_open', true);
			}
		},

		close: function() {
			if (this._ancillaries.popup) {
				this._ancillaries.popup.close();
				this.setState('_open', false);
			}
		},

		toggle: function() {
			this.isOpen() ? this.close() : this.open();
		},

		isOpen: function() {
			return this._ancillaries.popup && this._ancillaries.popup.isOpen();
		},

		_childrenUpdated: function() {
			this.toggleClass(this.subclazz('nochildren'), !this._children.length);
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('open close', this.handleOpenClose.bind(this));
			this.widget$.on('click', this.handleClick.bind(this));
			this._childrenUpdated();

			if (!this._ancillaries.popup) {
				this._ancillaries.popup = fujion.widget.create(null, {wclass: 'Menupopup'});
				this.widget$.append(this._ancillaries.popup.widget$);
			} else if (this.getState('_open')) {
				this.open();
			}
		},

		render$: function() {
			const dom =
				'<span>'
				+ '<div class="dropdown" style="display: inline-block" role="presentation">'
				+ '<a id="${id}-btn" role="button" aria-haspopup="true" aria-expanded="false" class="dropdown-menu">'
				+ this.getDOMTemplate(':image', 'label')
				+ '<span class="dropdown-toggle"></span>'
				+ '</a>'
				+ '</div>'
				+ '</span>';
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_clazz: function(v) {
			this._super();
			this.attr('class', v, this.sub$('btn'));
		}

	});

	/******************************************************************************************************************
	 * A menu item widget
	 ******************************************************************************************************************/

	fujion.widget.Menuitem = fujion.widget.LabeledImageWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		onAddChild: function() {
			this._childrenUpdated();
		},

		onRemoveChild: function() {
			this._childrenUpdated();
		},

		/*------------------------------ Events ------------------------------*/

		handleCheck: function(event) {
			this.trigger(event);
			return false;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({_submenu: false, checked: false, checkable: false});
		},


		/*------------------------------ Other ------------------------------*/

		_childrenUpdated: function() {
			if (this.setState('_submenu', !!this._children.length)) {
				this.rerender();
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this.sub$('chk').on('click', this.handleCheck.bind(this));
		},

		render$: function() {
			const submenu = this.getState('_submenu');
			const dom = '<li>'
				+ '<a id="${id}-btn" class="dropdown-item">'
				+ this.getDOMTemplate(':image', ':checkable', 'label')
				+ '</a>'
				+ (submenu ? '<ul id="${id}-inner" class="dropdown-menu">' : '')
				+ '</li>';

			this.toggleClass('dropdown', !submenu);
			this.toggleClass('dropdown-submenu', submenu);
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_checkable: function(v) {
			this.rerender();
		},

		s_checked: function(v) {
			this.sub$('chk').fujion$swapClasses('fa-check', 'fa-square-o', v);
		}

	});

	/******************************************************************************************************************
	 * A menu header widget
	 ******************************************************************************************************************/

	fujion.widget.Menuheader = fujion.widget.LabeledImageWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.toggleClass('dropdown-header', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom =
				'<li>'
				+ this.getDOMTemplate(':image', 'label')
				+ '</li>';

			return $(this.resolveEL(dom));
		}

	});

	/******************************************************************************************************************
	 * A menu separator widget
	 ******************************************************************************************************************/

	fujion.widget.Menuseparator = fujion.widget.UIWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.toggleClass('divider', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<li role="separator"></li>');
		}

	});

	/******************************************************************************************************************
	 * A standalone image widget
	 ******************************************************************************************************************/

	fujion.widget.Image = fujion.widget.UIWidget.extend({

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<img>');
		},

		/*------------------------------ State ------------------------------*/

		s_alt: function(v) {
			this.attr('alt', v);
		},

		s_src: function(v) {
			this.attr('src', v);
		}

	});

	/******************************************************************************************************************
	 * A text box widget
	 ******************************************************************************************************************/

	fujion.widget.Textbox = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._type = 'text';
			this._super();
			this._options = null;
		},

		destroy: function() {
			this._removeOptions();
			this._super();
		},

		_removeOptions: function() {
			if (this._options) {
				this._options.destroy();
				this._options = null;
			}
		},

		/*------------------------------ State ------------------------------*/

		s_options: function(v) {
			this._removeOptions();

			if (!v) {
				return;
			}

			const self = this;
            fujion.import('cleave.js', () => applyOptions(v.phone));

			function applyOptions(loadPhoneFormatter) {
				if (loadPhoneFormatter) {
					if (!v.phoneRegionCode) {
						const lang = navigator.language.split('-');
						v.phoneRegionCode = lang[1] || lang[0];
					}

					return fujion.import('cleave.js/addons/cleave-phone.' + v.phoneRegionCode.toLowerCase() + '.js', () => applyOptions())
				}

				self._options = new Cleave(self.input$(), v);
			}
		},

		s_masked: function(v) {
			this.attr('type', v ? 'password' : this._type, this.input$());
		}

	});

	/******************************************************************************************************************
	 * An integer input box widget
	 ******************************************************************************************************************/

	fujion.widget.Integerbox = fujion.widget.NumberboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._max = 2147483647;
			this._min = -2147483648;
		}

	});

	/******************************************************************************************************************
	 * An integer input box widget
	 ******************************************************************************************************************/

	fujion.widget.Longbox = fujion.widget.Integerbox.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._max = 9223372036854775807;
			this._min = -9223372036854775808;
		}

	});

	/******************************************************************************************************************
	 * A double float point input box widget
	 ******************************************************************************************************************/

	fujion.widget.Doublebox = fujion.widget.NumberboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._constraint = /[\d+-.]/;
			this._partial = /^[+-]?[.]?$/;
			this._max = Number.MAX_VALUE;
			this._min = -Number.MAX_VALUE;
		}

	});

	/******************************************************************************************************************
	 * A multi-line text box widget
	 ******************************************************************************************************************/

	fujion.widget.Memobox = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({wrap: 'SOFT', rows: 2, cols: 20});
		},

		/*------------------------------ Other ------------------------------*/

		scrollToBottom: function() {
			const input$ = this.input$();
			input$.scrollTop(input$.get(0).scrollHeight);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $(this.resolveEL('<span><textarea id="${id}-inp"></textarea></span>'));
		},

		/*------------------------------ State ------------------------------*/

		s_autoScroll: function(v) {
			if (v) {
				this.scrollToBottom();
			}
		},

		s_cols: function(v) {
			this.attr('cols', v, this.input$());
		},

		s_rows: function(v) {
			this.attr('rows', v, this.input$());
		},

		s_value: function(v) {
			this._super();

			if (this.getState('autoScroll')) {
				this.scrollToBottom();
			}
		},

		s_wrap: function(v) {
			this.attr('wrap', v ? v.toLowerCase() : 'soft', this.input$());
		}

	});

	/******************************************************************************************************************
	 * A popup box widget
	 ******************************************************************************************************************/

	fujion.widget.Popupbox = fujion.widget.Textbox.extend({

		/*------------------------------ Events ------------------------------*/

		handleClick: function(event) {
			this.getState('disabled') ? null : this.toggle();
			return false;
		},

		/*------------------------------ Other ------------------------------*/

		close: function() {
			const popup = this._popup();
			popup ? popup.close() : null;
		},

		isOpen: function() {
			const popup = this._popup();
			return popup && popup.isOpen();
		},

		open: function() {
			this.close();
			const popup = this._popup();

			if (popup) {
				popup.open({
					my: 'left top',
					at: 'left bottom',
					of: this.widget$
				});
			}
		},

		_popup: function() {
			return fujion.wgt(this.getState('popup'));
		},

		toggle: function() {
			this.isOpen() ? this.close() : this.open();
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.sub$('btn').on('click', this.handleClick.bind(this));
		},

		render$: function() {
			const dom =
				'<span>'
				+ '<input id="${id}-inp" type="text">'
				+ '<span id="${id}-btn" class="fa fa-caret-down"></span>'
				+ '</span>';

			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_popup: function(v) {
			this.close();
		},

		s_readonly: function(v) {
			this._super();
			const inp$ = this.input$();
			inp$.off('click.fujion');
			v ? inp$.on('click.fujion', this.handleClick.bind(this)) : null;

		}

	});

	/******************************************************************************************************************
	 * A date box widget
	 ******************************************************************************************************************/

	fujion.widget.Datebox = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._type = 'date';
			this._super();
		}

	});

	/******************************************************************************************************************
	 * A time box widget
	 ******************************************************************************************************************/

	fujion.widget.Timebox = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._type = 'time';
			this._step = 1;
			this._super();
		}

	});

	/******************************************************************************************************************
	 * A list box widget
	 ******************************************************************************************************************/

	fujion.widget.Listbox = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleChange: function(event) {
			this.forEachChild(child => child.syncSelected());
			event.fujion_nosend = true;
			return false;
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('change', this.handleChange.bind(this));
		},

		render$: function() {
			return $('<select>');
		},

		/*------------------------------ State ------------------------------*/

		s_multiple: function(v) {
			this.attr('multiple', v);
		},

		s_size: function(v) {
			this.attr('size', v);
		}

	});

	/******************************************************************************************************************
	 * A list box item widget
	 ******************************************************************************************************************/

	fujion.widget.Listitem = fujion.widget.LabeledWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleChange: function(event) {
			this.syncSelected(true);
		},

		handleClick: function(event) {
			if (!this._dragging) {
				this.select(true);
				this._parent.handleChange({});
				this._parent.focus();
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({selected: false});
			this.forwardToServer('change');
		},

		/*------------------------------ Other ------------------------------*/

		select: function(select) {
			this.s_selected(select);
		},

		syncSelected: function(noevent) {
			const selected = this.widget$.is(':selected');

			if (this.setState('selected', selected) && !noevent) {
				this.trigger('change', {value: selected});
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('change', this.handleChange.bind(this));
		},

		render$: function() {
			return $('<option role="presentation">');
		},

		/*------------------------------ State ------------------------------*/

		s_dragid: function(v) {
			this._super();
			this.widget$.off('mouseup.fujion');

			if (v) {
				this.widget$.on('mouseup.fujion', this.handleClick.bind(this));
			}
		},

		s_selected: function(v) {
			this.prop('selected', v);
		},

		s_value: function(v) {
			this.attr('value', v);
		}

	});

	/******************************************************************************************************************
	 * A combo box widget
	 ******************************************************************************************************************/

	fujion.widget.Combobox = fujion.widget.InputboxWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Events ------------------------------*/

		handleClick: function(event) {
			if (event.target.tagName === 'LI') {
				return;
			}

			const inp$ = this.input$();

			if (inp$.is(':disabled')) {
				return;
			}

			fujion.event.stop(event);
			this.setOpen(!this.getState('_open'));
		},

		handleMove: function(event) {
			const inp$ = this.input$();
			const position = inp$.autocomplete('option', 'position');
			position.of = inp$;
			this._ul.position(position);
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			const self = this;
			const inp$ = this.input$();
			inp$.autocomplete({
				delay: 50,
				minLength: 0,
				autoFocus: false,
				appendTo: fujion.root$,
				change: _change,
				close: _close,
				open: _open,
				select: _change,
				source: _source,
				classes: {
					'ui-autocomplete': 'fujion_combobox-ddn'
				}
			});

			inp$.data('ui-autocomplete')._renderItem = this.renderItem$.bind(this);
			inp$.data('ui-autocomplete')._renderMenu = this.renderMenu.bind(this);
			this.widget$.on('move', this.handleMove.bind(this));
			this.sub$('btn').on('mousedown', this.handleClick.bind(this));

			function _change(event, ui) {
				const wgt = ui.item ? ui.item.wgt : null;

				if (wgt && wgt.setState('selected', true)) {
					wgt.trigger('change', {value: true});
				}
			}

			function _close(event, ui) {
				self.setState('_open', false);
				self.widget$.fujion$track(self.widget$, true);
			}

			function _open(event, ui) {
				self.setState('_open', true);
				self.widget$.fujion$track(self.widget$);
			}

			function _source(request, response) {
				const term = request.term.toLowerCase();
				const len = term.length;
				const items = [];
				const filter = self.getState('autoFilter');

				self.forEachChild(child => {
					const label = child.getState('label') || '';
					const matched = len > 0 && label.substring(0, len).toLowerCase() === term;

					if (!len || !filter || matched) {
						items.push({
							wgt: child,
							label: label,
							matched: matched
						});
					}
				});

				response(items);
			}

		},

		render$: function() {
			const dom =
				'<span>'
				+ '<input id="${id}-inp" type="text">'
				+ '<span id="${id}-btn" class="fa fa-caret-down"></span>'
				+ '<select id="${id}-inner" class="d-none"></select>'
				+ '</span>';

			return $(this.resolveEL(dom));
		},

		renderItem$: function(ul, item) {
			const wgt = item.wgt;
			const image = wgt.getState('image');
			const item$ = $('<li>')
				.toggleClass(this.subclazz('matched'), item.matched)
				.toggleClass(this.subclazz('selected'), wgt.getState('selected'))
				.appendTo(ul);
			const cnt$ = $('<span>').appendTo(item$);
			image ? $('<img>').attr('src', image).appendTo(cnt$) : null;
			$('<span>').text(item.label).appendTo(cnt$);
			return item$;
		},

		renderMenu: function(ul, items) {
			this._ul = ul;
			ul.css('z-index', fujion.widget._zmodal + 1);
			const ac = this.input$().autocomplete('instance');
			_.forEach(items, item => ac._renderItemData(ul, item));
		},

		/*------------------------------ State ------------------------------*/

		setOpen: function(open) {
			this.setState('_open', open);
			const inp$ = this.input$();

			if (open) {
				inp$.autocomplete('search', inp$.attr('value'));
				inp$.focus();
			} else {
				inp$.autocomplete('close');
			}
		},

		s_readonly: function(v) {
			this._super();
			const mdn = 'mousedown.fujion';
			this.widget$.off(mdn);
			v ? this.widget$.on(mdn, this.handleClick.bind(this)) : null;
		}

	});

	/******************************************************************************************************************
	 * A combo box item widget
	 ******************************************************************************************************************/

	fujion.widget.Comboitem = fujion.widget.LabeledImageWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({selected: false});
			this.forwardToServer('change');
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<option>');
		},

		/*------------------------------ State ------------------------------*/

		s_selected: function(v) {
			this.attr('selected', v);
		},

		s_value: function(v) {
			this.attr('value', v);
		}

	});

	/******************************************************************************************************************
	 * An iframe widget
	 ******************************************************************************************************************/

	fujion.widget.Iframe = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleLoad: function(event) {
			event.src = '';

			try {
				const src = event.target.contentWindow.location.href;
				this.setState('src', src);
				event.src = src;
			} catch (e) {
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.forwardToServer('load');
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this.widget$.on('load', this.handleLoad.bind(this));
		},

		render$: function() {
			return $('<iframe>');
		},

		/*------------------------------ State ------------------------------*/

		s_sandbox: function(v) {
			this.attr('sandbox', v);
		},

		s_src: function(v) {
			this.attr('src', v);
		}

	});

	/******************************************************************************************************************
	 * A group box widget
	 ******************************************************************************************************************/

	fujion.widget.Groupbox = fujion.widget.UIWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom =
				'<div>'
				+ '<fieldset>'
				+ '<legend id="${id}-title"></legend>'
				+ '<span id="${id}-inner"></span>'
				+ '</fieldset>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_title: function(v) {
			this.sub$('title').text(v);
		}

	});

	/******************************************************************************************************************
	 * A progress bar widget
	 ******************************************************************************************************************/

	fujion.widget.Progressbar = fujion.widget.UIWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({maxvalue: 100, value: 0})
		},

		/*------------------------------ Other ------------------------------*/

		_pct: function() {
			const value = this.getState('value');
			const max = this.getState('maxvalue');
			const pct = max <= 0 ? 0 : value / max * 100;
			return pct > 100 ? 100 : pct;
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this._adjust();
		},

		render$: function() {
			return $('<div><div></div><div></div></div>');
		},

		/*------------------------------ State ------------------------------*/

		_adjust: function(v) {
			v = v || this.widget$;
			v.children().last().width(this._pct() + '%');
		},

		s_label: function(v) {
			this.widget$.children().first().text(v);
		},

		s_maxvalue: function(v) {
			this._adjust();
		},

		s_value: function(v) {
			this._adjust();
		}

	});

	/******************************************************************************************************************
	 * A slider widget
	 ******************************************************************************************************************/

	fujion.widget.Slider = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleChange: function(event, ui) {
			const value = ui.value;

			if (this.setState('value', value)) {
				this.trigger('change', {value: value});
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({value: 0, maxvalue: 100, minvalue: 0, step: 1, orientation: 'HORIZONTAL', synced: false});
			this.forwardToServer('change');
		},

		/*------------------------------ Other ------------------------------*/

		_slider: function(opt, value) {
			this.sub$('slider').slider('option', opt, value);
		},

		/*------------------------------ Rendering ------------------------------*/

		beforeRender: function() {
			this._super();
			this.sub$('slider').slider({});
		},

		render$: function() {
			return $(this.resolveEL('<span><div id="${id}-slider"></div></span>'));
		},

		/*------------------------------ State ------------------------------*/

		s_maxvalue: function(v) {
			this._slider('max', v);
		},

		s_minvalue: function(v) {
			this._slider('min', v);
		},

		s_orientation: function(v) {
			this._slider('orientation', v.toLowerCase());
		},

		s_step: function(v) {
			this._slider('step', v);
		},

		s_synced: function(v) {
			this.widget$.off('slidechange slide');
			this.widget$.on(v ? 'slide' : 'slidechange', this.handleChange.bind(this));
		},

		s_value: function(v) {
			this._slider('value', v);
		}

	});


	/******************************************************************************************************************
	 * A slide-down message window.
	 ******************************************************************************************************************/

	fujion.widget.Messagewindow = fujion.widget.UIWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		onAddChild: function(child) {
			this.widget$.show();
			child._slide(true, 'fast', function() {
				child.widget$ ? child.widget$.height('auto') : null;
			});
		},

		onRemoveChild: function(child) {
			!this.getChildCount() ? this.widget$.hide() : null;
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			const dom = '<div>'
				+ '<div id="${id}-inner"></div>'
				+ '</div>';
			return $(this.resolveEL(dom));
		}

	});

	/******************************************************************************************************************
	 * Message pane for slide-down message window.
	 ******************************************************************************************************************/

	fujion.widget.Messagepane = fujion.widget.UIWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._clearTimeout();
			this._super();
		},

		init: function() {
			this._super();
			this.initState({duration: 8000});
			this.toggleClass('alert', true);
			this.forwardToServer('close');
		},

		/*------------------------------ Other ------------------------------*/

		_slide : function(down, duration, complete) {
			const w$ = this.widget$;
			const start = down ? 0 : w$.outerHeight();
			const end = down ? w$.outerHeight() : 0;
			w$.outerHeight(start);
			w$.animate({height: end}, duration || 'slow', complete);
		},

		_clearTimeout: function() {
			if (this._timeout) {
				clearTimeout(this._timeout);
				delete this._timeout;
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this._buttonAdd('remove', 'close');
		},

		render$: function() {
			const dom = '<div>'
				+ '<div>'
				+ '<span id="${id}-title" class="${wclazz}-title"></span>'
				+ '<span id="${id}-icons" class="fujion_titled-icons"></span>'
				+ '</div>'
				+ '<div id="${id}-inner"></div>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},

		_buttonAdd: function(type, forward) {
			const btn$ = $('<span>').addClass('fa fa-' + type).appendTo(this.sub$('icons'));
			this.forward(btn$, 'click', forward);
		},

		/*------------------------------ State ------------------------------*/

		s_actionable: function(v) {
			v ? this._buttonAdd('flash', 'action') : null;
		},

		s_duration: function(v) {
			const self = this;
			this._clearTimeout();

			if (v > 0) {
				this._timeout = setTimeout(_timeout, v);
			}

			function _timeout() {
				delete self._timeout;
				self._slide(false, null, function () {
					self.trigger('close');
				});
			}
		},

		s_title: function(v) {
			this.sub$('title').text(v);
		}

	});

	/******************************************************************************************************************
	 * A window widget
	 ******************************************************************************************************************/

	const modalMask$ = fujion.body$.fujion$mask(fujion.widget._zmodal).hide();

	const modals = [];

	function addModal(w) {
		const zindex = ++fujion.widget._zmodal;
		modals.push({w: w, zindex: zindex, previous: w.widget$.css('z-index')});
		w.widget$.css('z-index', zindex);
		updateMask();
		return zindex;
	}

	function removeModal(w) {
		const i = _.findIndex(modals, v => v.w === w);

		if (i === -1) {
			return;
		}

		w.widget$.css('z-index', modals[i].previous);
		modals.splice(i, 1);
		updateMask();
	}

	function updateMask() {
		const i = _.findLastIndex(modals, v => v.w.widget$.get(0).parentElement && v.w.getState('visible'));

		if (i < 0) {
			modalMask$.hide();
		} else {
			modalMask$.css('z-index', modals[i].zindex).show();
		}
	}

	fujion.widget.Window = fujion.widget.UIWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Events ------------------------------*/

		handleMaximize: function(event) {
			const size = this._buttonState('maximize') ? 'NORMAL' : 'MAXIMIZED';
			this.updateState('size', size);
		},

		handleMinimize: function(event) {
			const size = this._buttonState('minimize') ? 'NORMAL' : 'MINIMIZED';
			this.updateState('size', size);
		},

		onParentChange: function() {
			this._super();
			updateMask();
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({mode: 'INLINE', size: 'NORMAL', position: 'CENTER', movable: true});
			this.toggleClass('fujion_titled card', true);
			this.forwardToServer('close');
		},

		destroy: function() {
			removeModal(this);
			this._super();
		},

		/*------------------------------ Other ------------------------------*/

		_modifyStyles: function(styles) {
			if (styles) {
				const s = this.widget$[0].style;
				_.forOwn(styles, (value, key) => s[key] = value);
			}
		},

		_restoreStyles: function() {
			this._modifyStyles(this.getState('_savedStyles'));
			this.setState('_savedStyles', null);
		},

		_saveStyles: function() {
			if (!this.getState('_savedStyles')) {
				const saved = _.pick(this.widget$[0].style, fujion.widget.Window._saveStyles);
				this.setState('_savedStyles', saved);
			}
		},

		_updateDraggable: function() {
			this.widget$.draggable('instance') ? this.widget$.draggable('destroy') : null;

			if (this.getState('mode') === 'INLINE') {
				this.applyState('dragid');
			} else if (this.getState('movable')) {
				this.widget$.draggable({
					containment: fujion.root$,
					handle: '#' + this.subId('titlebar')});
			}
		},

		_updatePosition: function() {
			this._needsPositioning = this.getState('mode') !== 'INLINE';

			if (this._needsPositioning && this.getState('visible')) {
				this._needsPositioning = false;
				let pos = this.getState('position');

				if (pos) {
					pos = pos.toLowerCase().replace('_', ' ');

					this.widget$.position({
						my: pos,
						at: pos,
						of: fujion.root$
					});
				}
			}
		},

		_updateSizable: function() {
			const canResize = this.getState('sizable')
				&& this.getState('mode') !== 'INLINE'
				&& this.getState('size') === 'NORMAL';
			const active = this.widget$.resizable('instance');

			if (!canResize !== !active) {
				if (canResize) {
					this.widget$.resizable({
						minHeight: 50,
						minWidth: 100,
						handles: 'all'
					});
				} else {
					this.widget$.resizable('destroy');
				}
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('minimize', this.handleMinimize.bind(this));
			this.widget$.on('maximize', this.handleMaximize.bind(this));
		},

		getDragHelper: function() {
			return fujion.clone(this.sub$('titlebar'), -1);
		},

		render$: function() {
			const dom =
				'<div>'
				+ '<div class="card-header">'
				+ '<div id="${id}-titlebar" class="card-title">'
				+ '<img id="${id}-image" class="fujion_window-image">'
				+ '<span id="${id}-title"></span>'
				+ '<span id="${id}-icons" class="fujion_titled-icons"></span>'
				+ '</div>'
				+ '</div>'
				+ '<div id="${id}-inner" class="card-body"></div>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},

		_buttonAdd: function(type, icons, position) {
			const id = this.subId(type);
			let btn = $('#' + id);

			if (btn.length) {
				return btn;
			}

			icons = icons.split(' ');
			btn = $('<span class="fa fa-' + icons[0] + '"></span>')
				.attr('id', id)
				.data('position', position)
				.data('icons', icons)
				.data('state', 0);
			const icons$ = this.sub$('icons');
			icons$.append(btn);
			icons$.children().sort((a, b) => {
				a = $(a);
				b = $(b);
				const x = a.data('position') - b.data('position');

				if (x > 0) {
					a.before(b);
				}

				return x;
			});

			this.forward(btn, 'click', type);
			return btn;
		},

		_buttonRemove: function(type) {
			this.sub$(type).remove();
		},

		_buttonState: function(type, newState) {
			const btn = this.sub$(type);
			const icons = btn.data('icons');
			const oldState = btn.data('state');

			if (!icons) {
				newState = 0;
			} else if (_.isNil(newState)) {
				newState = oldState;
			} else if (newState !== oldState) {
				btn.data('state', newState).removeClass('fa-' + icons[oldState]).addClass('fa-' + icons[newState]);
			}

			return newState;
		},

		/*------------------------------ State ------------------------------*/

		s_closable: function(v) {
			this[v ? '_buttonAdd' : '_buttonRemove']('close', 'close', 9999);
		},

		s_dragid: function(v) {
			if (this.getState('mode') === 'INLINE') {
				this._super();
			}
		},

		s_image: function(v) {
			this.sub$('image').attr('src', v);
		},

		s_maximizable: function(v) {
			this[v ? '_buttonAdd' : '_buttonRemove']('maximize', 'expand compress', 10);
		},

		s_minimizable: function(v) {
			this[v ? '_buttonAdd' : '_buttonRemove']('minimize', 'window-minimize window-maximize', 20);
		},

		s_mode: function(v, oldmode) {
			const self = this;

			if (oldmode === 'INLINE') {
				this._saveStyles();
			}

			v = v || 'INLINE';
			_mode(oldmode, true);
			_mode(v, false);
			this._updateDraggable();
			this._updateSizable();
			this._updatePosition();
			this.widget$.removeAttr('data-fujion-popup');

			if (oldmode === 'MODAL') {
			    removeModal(this);
			    this.widget$.detach();
				const index = this._parent ? this._parent.getChildIndex(this) : -1;

                if (index >= 0) {
                    this._attach(index + 1);
                }
            }

			if (v === 'MODAL') {
				this.widget$
                    .attr('data-fujion-popup', true)
                    .detach()
                    .appendTo(fujion.body$);
				addModal(this);
			} else {
				this.widget$.css('z-index', v === 'POPUP' ? ++fujion.widget._zmodal : null);
			}

			if (v === 'INLINE') {
				this._restoreStyles();
			}

			function _mode(mode, remove) {
				mode ? self.toggleClass(self.subclazz(mode), !remove) : null;
			}
		},

		s_movable: function(v) {
			this._updateDraggable();
		},

		s_position: function(v) {
			this._updatePosition();
		},

		s_sizable: function(v) {
			this._updateSizable();
		},

		s_size: function(v)	 {
			const inline = 'INLINE' === this.getState('mode');
			this._updateSizable();

			switch (v) {
				case 'NORMAL':
					this._buttonState('minimize', 0);
					this._buttonState('maximize', 0);
					this.sub$('inner').hide();
					this._restoreStyles();
					this.sub$('inner').show();
					break;

				case 'MAXIMIZED':
					this._buttonState('minimize', 0);
					this._buttonState('maximize', 1);

					if (!inline) {
						this._saveStyles();
						this._modifyStyles({
							left: 0,
							right: 0,
							top: 0,
							bottom: 0,
							height: null,
							width: null,
							'max-width': null,
							'max-height': null
						});
					}

					this.sub$('inner').show();
					break;

				case 'MINIMIZED':
					this._saveStyles();
					this.sub$('inner').hide();
					this._buttonState('minimize', 1);
					this._buttonState('maximize', 0);
					const tbheight = this.widget$.children().first().css('height');

					if (inline) {
						this._modifyStyles({
							height: tbheight
						});
					} else {
						this._modifyStyles({
							left: 0,
							right: 'auto',
							top: null,
							bottom: 0,
							height: tbheight,
							width: null,
							'min-width': null,
							'min-height': null
						});
					}

					break;
			}

		},

		s_title: function(v) {
			this.sub$('title').text(v);
		},

		s_visible: function(v) {
			this._super();
			v ? this._updatePosition() : null;
			updateMask();
		}

	});

	fujion.widget.Window._saveStyles = ['left', 'right', 'top', 'bottom', 'height', 'width',
		'min-height', 'max-height', 'min-width', 'max-width'];

	/******************************************************************************************************************
	 * A widget for displaying alerts (client side only)
	 ******************************************************************************************************************/

	fujion.widget.Alert = fujion.widget.Window.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this.wclazz = 'fujion_window';
			this._super();
			this.initState({mode: 'MODAL', closable: true, sizable: true}, true);
			this.toggleClass('fujion_alert', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.find('.fa-close').on('click', this.destroy.bind(this));
		},

		beforeRender: function() {
			this._super();
			this.widget$.appendTo(fujion.body$);
		},

		/*------------------------------ State ------------------------------*/

		s_text: function(v) {
			this.widget$.find('.card-body').text(v);
		}

	});

	return fujion.widget;
});
