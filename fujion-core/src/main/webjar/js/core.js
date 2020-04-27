'use strict';

define('fujion-core', ['jquery', 'jquery-ui', 'lodash'], () => {
	let fujion;

	return fujion = {

		/*------------------------------ Initialization ------------------------------*/

		// Global options for Fujion client.

		globalOptions: {},

		/**
		 * Initializer for bootstrapping process.
		 */
		init: function (options) {
			window.onerror = fujion.fatal;
			window.onbeforeunload = event => fujion._canClose ? undefined : event.returnValue = true;
			window.onunload = () => fujion.ws.setKeepalive(0);
			window.fujion = fujion;

			this.debug = options.debug;
			this.pid = options.pid;
			this.baseurl = options.baseurl;
			this._canClose = true;
			this.body$ = $('body');
			this.head$ = $('head');
			this.root$ = $('#fujion_root');
			this.log._init(options);
			this.action._init();
			this.jquery._init();
			this.event._init();
			this.widget._init();
			this.ws._init(options);
		},

		/*------------------------------ Request Processing ------------------------------*/

		action: {
			transforms: {
				'id': value => fujion.widget.find(value),
				'js': value => {
					let fnc;
					eval('fnc=' + value);
					return fnc;
				}
			},

			_init: function () {
			this.queue = [];
			this.processing = false;
		},
			
		processQueue: function() {
			if (this.processing) {
				return;
			}

			let action;
			this.processing = true;

			try {
				while (action = this.queue.shift()) {
					const result = this._processAction(action);

					if (result && _.isFunction(result.then)) {
						return result.then(
							result => {
								this._doCallback(action, result);
								this.processing = false;
								this.processQueue();
							}, error => {
								this.processing = false;
								fujion.fatal(error);
							});
					} else {
						this._doCallback(action, result);
					}
				}
			} catch (error) {
				this.processing = false;
				fujion.fatal(error);
			}
			
			this.processing = false;
		},
		
		queueAction: function(action) {
			if (_.isArray(action)) {
				Array.prototype.push.apply(this.queue, action);
			} else {
				this.queue.push(action);
			}
			
			this.processQueue();
		},
		
		registerTransform: function(datatype, transform) {
			if (fujion.action.transforms[datatype]) {
				throw new Error('A transform for datatype "' + datatype + '" has already been registered.');
			}
			
			fujion.action.transforms[datatype] = transform;
		},
		
		_doCallback: function(action, result) {
			if (action.cbk) {
				fujion.widget._page.trigger('callback', {handle: action.cbk, data: result});
			}			
		},
		
		_processAction: function(action) {
			fujion.log.debug('Processing: ', action);

			let tgt = action.tgt;

			if (tgt) {
				if (tgt.startsWith('@')) {
					return fujion.import(tgt.substring(1), module => _invokeAction(module, action))
				}

				const i = tgt.indexOf('-');
				const sub = i === -1 ? null : tgt.substring(i + 1);
				tgt = fujion.widget.find(i > 0 ? tgt.substring(0, i) : tgt);

				if (!tgt) {
					throw new Error('No target matching ' + action.tgt);
				}

				tgt = sub ? tgt.sub$(sub) : tgt;
			}
			
			return _invokeAction(tgt, action);
			
			function _invokeAction(tgt, action) {
				if (!action.fcn) {
					return;
				}

				const fcn = fujion.resolveReference(tgt, action.fcn);

				if (!fcn) {
					throw new Error('Unknown action (' + action.tgt + ').' + action.fcn);
				}

				return _.isFunction(fcn.ref) ? fcn.ref.apply(fcn.base, _processArgs(action.arg)) : fcn.ref;
			}
		
			function _processArgs(args) {
				args = args || [];
				
				if (!_.isArray(args)) {
					args = [args];
				}

				return _.forOwn(args, _transform);
			}
			
			function _transform(value, key, object) {
				if (_.isNil(value)) {
					object[key] = null;
				} else if (value.__fujion__) {
					object[key] = _customTransform(value.__fujion__);
				} else if (_.isObject(value)) {
					_.forOwn(value, _transform);
				}
			}
			
			function _customTransform(value) {
				const tx = fujion.action.transforms[value.tp];

				if (!tx) {
					throw new Error('Unknown argument type: ' + value.tp);
				}

				return _.isNil(value.vl) ? null : tx(value.vl);
			}
		}
	},

	/*------------------------------ JQuery Extensions ------------------------------*/
	
	jquery: {
		_init: function() {
			for (const fn in this) {
				if (fn != '_init') {
					$.fn[fn] = this[fn];
				}
			}
		},
		
		fujion$widget: function() {
			let ele$ = this;
			let wgt;

			while (!wgt && ele$.length) {
				wgt = ele$.data('fujion_widget');
				ele$ = wgt ? null : ele$.parent();
			}

			return wgt;
		},
		
		/**
		 * Notifies the caller (via move events) when the source element moves.  
		 * Note this uses background polling, so should be used sparingly.
		 */
		fujion$track: function(src$, untrack) {
			const data = src$._fujion$track || {};
			const lst = data.lst || [];

			this.each(function () {
				const i = lst.indexOf(this);

				if (i === -1) {
					untrack ? null : lst.push(this);
				} else {
					untrack ? lst.splice(i, 1) : null;
				}
			});

			if (!lst.length) {
				_untrack();
			} else if (!data.poll) {
				src$._fujion$track = data;
				data.lst = lst;
				data.pos = src$.offset();
				data.poll = setInterval(_poll, 500);
			}
			
			return this;
			
			function _untrack() {
				data.poll ? clearInterval(data.poll) : null;
				delete src$._fujion$track;
			}
			
			function _poll() {
				const lastpos = data.pos;
				const currpos = src$.offset();

				if (!currpos) {
					_untrack();
				} else if (lastpos.left !== currpos.left || lastpos.top !== currpos.top) {
					lastpos.left = currpos.left;
					lastpos.top = currpos.top;

					const event = $.Event('move', {
						relatedTarget: src$,
						position: currpos,
						pageX: currpos.left,
						pageY: currpos.top
					});
					$(data.lst).trigger(event);
				}
			}
		},
		
		fujion$attr: function(name, value) {
			return _.isNil(value) || value === '' ? this.removeAttr(name): this.attr(name, value);
		},
		
		fujion$prop: function(name, value) {
			return _.isNil(value) || value === '' ? this.removeProp(name): this.prop(name, value);
		},
		
		fujion$show: function(visible) {
			return visible ? this.show() : this.hide();
		},
		
		fujion$on: function(name, handler) {
			return this.off(name).on(name, handler);
		},
		
		fujion$removeSubclass: function(base, sub) {
			if (_.isArray(sub)) {
				sub = sub.join(' ');
			}

			sub = sub.split(' ');

			for (const i in sub) {
				this.removeClass(base + sub[i]);
			}

			return this;
		},
		
		fujion$swapClasses: function(trueClass, falseClass, value) {
			this.removeClass(value ? falseClass : trueClass);
			this.addClass(value ? trueClass : falseClass);
			return this;
		},
		
		fujion$mask: function(zindex) {
			this.css('position', 'relative');
			
			return $('<div class="fujion-mask"></div>')
				.css('z-index', zindex)
				.prependTo(this);
		},
		
		fujion$zindex: function(dflt) {
			let ele = this[0];
			let zindex = null;

			while (ele && !_.isFinite(zindex)) {
				zindex = +getComputedStyle(ele).zIndex;
				ele = ele.parentElement;
			}

			return _.isFinite(zindex) ? zindex : dflt || 0;
		}
	},
	
	/*------------------------------ WebSocket Support ------------------------------*/
	
	ws: {
		_init: function(options) {
			this.socket = new WebSocket(options.wsurl);
			this.socket.onerror = _onerror.bind(this);
			this.socket.onmessage = _onmessage.bind(this);
			this.socket.onopen = _onopen.bind(this);
			this.socket.binaryType = 'blob';
			this.lastSend = 0;
			this.lastReceive = 0;
			this.setKeepalive(options.keepalive);
		
			function _onopen() {
				const data = {
					requestURL: window.location.href,
					baseURL: options.baseurl,
					viewportHeight: $(window).height(),
					viewportWidth: $(window).width(),
					timezoneOffset: new Date().getTimezoneOffset()
				};

				fujion.flatten(screen, data, 'screen', 1);
				fujion.flatten(navigator, data, 'browser', 1);
				this.sendData('init', data);
			}
		
			function _onmessage(message) {
				this.lastReceive = Date.now();
				const action = JSON.parse(message.data);
				fujion.log.debug('Received: ', action);
				fujion.action.queueAction(action);
			}
			
			function _onerror(event) {
				fujion.log.error(event);
			}
		
			function _onkeepalive() {
				const elapsed = Date.now() - this.lastSend;

				if (elapsed >= this.keepalive) {
					this.ping('keepalive');
				}
			}
		},
		
		setKeepalive: function(keepalive) {
			if (this.onkeepalive) {
				clearInterval(this.onkeepalive);
				delete this.onkeepalive;
			}
			
			if (keepalive > 0) {
				this.keepalive = keepalive;
				this.onkeepalive = setInterval(_onkeepalive.bind(this), keepalive / 2);
			} else {
				delete this.keepalive;
			}
		},
		
		isConnected: function() {
			return this.socket && this.socket.readyState === WebSocket.OPEN;
		},
		
		ping: function(data) {
			this.sendData('ping', data);
		},
		
		sendData: function(type, data, nolog) {
			if (!this.isConnected()) {
				this.setKeepalive(0);
				fujion.debug ? null : $('html').empty();

				return setTimeout(() => {
					throw new Error('Communication with the server has been interrupted.');
				}, 1);
			}

			const pkt = {type: type, pid: fujion.pid, data: data};

			if (data && data.blob) {
				const blob = data.blob;
				delete data.blob;
				this.socket.send(new Blob([JSON.stringify(pkt, _replacer), '\n', blob]));
			} else {
				this.socket.send(JSON.stringify(pkt, _replacer));
			}

			this.lastSend = Date.now();
				
			if (!nolog) {
				fujion.log.debug('Sent: ', pkt);
			}
			
			function _replacer(key, value) {
				if (!_.isObject(value)) {
					return value;
				}
				
				if (value.jquery) {
					return value.attr('id');
				}

				if (fujion.widget.isWidget(value) || _.isElement(value)) {
					return value.id;
				}
				
				if (value !== window && !_.isFunction(value)) {
					return value;
				}
			}
		}
	},
	
	/*------------------------------ Event Support ------------------------------*/
		
	event: {
		postProcessors: {},
		
		eventId: 0,
		
		_init: function() {
		    fujion.body$.on('contextmenu', function(event) {
		    		fujion.debug ? null : event.preventDefault();
		    });
		    
		    fujion.body$.on('keydown', function(event) {
		    	if (event.keyCode === 8) {
					let tp = event.srcElement || event.target;
					tp = tp.tagName.toLowerCase();

					if (tp !== 'input' && tp !== 'textarea')
						event.preventDefault();
				}
		    });
			
			this.registerPostProcessor('change', _ppChangeInput);
			this.registerPostProcessor('input', _ppChangeInput);
			
			function _ppChangeInput(event, data) {
				data.value = _.defaultTo(data.value, event.target.value);
			}
			
		},
		
		_postprocess: function(event, data) {
			const fn = this.postProcessors[event.type];
			fn ? fn.apply(this, arguments) : null;
		},
		
		forward: function(event, type, target) {
			const target$ = fujion.$(target || event.target);
			event.type = type || event.type;
			target$ ? target$.triggerHandler(event) : null;
		},
		
		preventDefault: function(event) {
			event.preventDefault ? event.preventDefault(): null;
		},
		
		stop: function(event, immediate) {
			immediate ? fujion.event.stopImmediatePropagation(event) : fujion.event.stopPropagation(event);
			fujion.event.preventDefault(event);
		},
		
		stopImmediatePropagation: function(event) {
			event.stopImmediatePropagation ? event.stopImmediatePropagation() : fujion.event.stopPropagation(event);
		},
		
		stopPropagation: function(event) {
			event.stopPropagation ? event.stopPropagation() : null;
		},
		
		removeOn: function(type) {
			return type.startsWith('on') ? _.lowerFirst(type.substring(2)) : type;
		},
		
		addOn: function(type) {
			return type.startsWith('on') ? type : ('on' + _.upperFirst(type));
		},
		
		registerPostProcessor: function(type, fn) {
			this.postProcessors[type] = fn;
		},

		toKeyCapture: function(event) {
			let value = '';
			value += event.ctrlKey ? '^' : '';
			value += event.altKey ? '@' : '';
			value += event.metaKey ? '~' : '';
			value += event.shiftKey ? '$' : '';
			value += "#" + event.keyCode;
			return value;
		},
		
		/**
		 * Event handler for constraining input.
		 */
		constrainInput: function(constraint, keyPressEvent) {
			const key = String.fromCharCode(keyPressEvent.which);
			const test = constraint.test ? constraint.test(key) : constraint(key);
			test ? null : fujion.event.stop(keyPressEvent);
		},
		
		sendToServer: function(event, params) {
			const orig = event.originalEvent || event;

			if (orig.fujion_nosend || event.fujion_nosend) {
				return;
			}

			event.id = ++fujion.event.eventId;
			const pkt = _.assignIn({}, event, params || {});
			delete pkt.originalEvent;
			orig.fujion_nosend = true;
			fujion.event._postprocess(event, pkt);
			fujion.ws.sendData('event', pkt);
		}
	},
	
	/*------------------------------ Widget Support ------------------------------*/
	
	widget: {
		_registry: {},
		
		_addon: {},
		
		_init: function() {
		    $.widget.bridge('uitooltip', $.ui.tooltip);
		    $.widget.bridge('uibutton', $.ui.button);
		},
		
		/**
		 * Register an add-on widget.
		 * 
		 * @param {String} moduleName The module name.
		 * @param {String} className The name of the widget class.
		 * @param {object} classObject The widget class.
		 * @return {object} The module object.
		 */
		addon: function(moduleName, className, classObject) {
			const addon = fujion.widget._addon;
			addon[moduleName] = addon[moduleName] || {};
			addon[moduleName][className] = classObject;
			return addon[moduleName];
		},

		/**
		 * Create a widget instance
		 *
		 * @param {Widget} parent The parent widget
		 * @param {object} props Immutable widget properties.
		 * @param {object} [state] Initial state values.
		 * @return {Widget} The newly created widget.
		 */
		create: function (parent, props, state) {
			let pkg;
			props.id = props.id || fujion.uniqueId();
			props.wmodule = props.wmodule || 'fujion-widget';
			return fujion.import(props.wmodule, _create);

			function _create(pkg) {
				const clazz = pkg[props.wclass];

				if (!clazz) {
					throw new Error('Unrecognized widget class: ' + props.wmodule + '.' + props.wclass);
				}

				return fujion.widget.register(props.id, new clazz(parent, props, state));
			}
		},
		
		find: function(id) {
			return _.isNil(id) ? null : this._registry[id];
		},
		
		isWidget: function(object) {
			return object && object.constructor && object.constructor.name === 'Widget';
		},
		
		register: function(id, wgt) {
			this._registry[id] = wgt;
			return wgt;
		},
		
		unregister: function(id) {
			delete this._registry[id];
		}
		
	},
	
	/*------------------------------ Logging Support ------------------------------*/
	
	log: {
		level: {},
		
		_init: function(options) {
			this.level = options.logging;
		},

		debug: function() {
			fujion.log._log('debug', 'log', arguments);
		},
		
		error: function() {
			fujion.log._log('error', 'error', arguments);
		},
		
		fatal: function() {
			fujion.log._log('fatal', 'error', arguments);
		},
		
		info: function() {
			fujion.log._log('info', 'info', arguments);
		},
		
		trace: function() {
			fujion.log._log('trace', 'log', arguments);
		},
		
		warn: function() {
			fujion.log._log('warn', 'warn', arguments);
		},
		
		/**
		 * Send log message to console and/or to server.
		 */
		_log: function(level, con, args) {
			let setting = fujion.log.level[level];

			if (setting & 2) {
				if (fujion.ws.isConnected()) {
					fujion.ws.sendData('log', {level: level, message: args}, true);
				} else {
					setting = 1;
				}
			}

			if (setting & 1) {
				console[con].apply(this, _.concat([level + ':'], args));
			}
		}
	},
	
	/*------------------------------ Utility Functions ------------------------------*/
	
	/**
	 * Returns the jquery object associated with the object.
	 */
	$: function(object) {
		return _.isNil(object) ? null 
			: object.jquery ? object 
			: fujion.widget.isWidget(object) ? object.widget$ 
			: $(object);
	},
	
	/**
	 * Returns the widget associated with the object.
	 */
	wgt: function(object) {
		return _.isNil(object) ? null 
			: fujion.widget.isWidget(object) ? object 
			: this.$(object).fujion$widget();
	},	
	
	/**
	 * Returns the id associated with the object.
	 */
	id: function(object) {
		return _.isNil(object) ? null 
			: object.jquery ? object.attr('id') 
			: object.id;
	},
	
	/**
	 * Returns the DOM element associated with the object.
	 */
	ele: function(object) {
		return _.isNil(object) ? null 
			: _.isElement(object) ? object 
			: fujion.$(object).get(0);
	},
	
	/**
	 * Swaps positions of the two elements within the DOM.
	 */
	swap: function(ele1, ele2) {
		ele1 = fujion.ele(ele1);
		ele2 = fujion.ele(ele2);
		const parent1 = ele1.parentNode;
		const next1 = ele1.nextSibling;
		const parent2 = ele2.parentNode;
		const next2 = ele2.nextSibling;
		parent1.insertBefore(ele2, next1);
		parent2.insertBefore(ele1, next2);
	},
	
	/**
	 * Handler for fatal exceptions.
	 */
	fatal: function(error) {
		if (!(error instanceof Error)) {
			error = new Error(error);
		}

		const err = error.toString();
		const message = error.stack || err;
		alert('Fatal error:\n\n' + (message.startsWith(err) ? message : err + '\n' + message));
	},
	
	/**
	 * Display an alert dialog.
	 */
	alert: function(message, title, flavor) {
		flavor = flavor || 'primary';

		const props = {
			wclass: 'Alert'
		};
		const state = {
			clazz: 'alert-' + flavor,
			title: title || 'Alert',
			text: message
		};

		return fujion.widget.create(null, props, state);
	},
	
	/**
	 * Submits a form.
	 */
	submit: function(form) {
		fujion.$(form).submit();
	},
	
	/**
	 * Performs an eval operation in the fujion context.
	 */
	eval: function(value) {
		return eval(value);
	},
	
	/**
	 * Redirects to target url or creates new window if name is specified.
	 */
	redirect: function(target, name) {
		if (name) {
			window.open(target, name);
		} else {
			fujion._canClose = true;
			$(location).attr('href', target);
		}
	},
	
	/**
	 * Return a unique identifier.
	 */
	uniqueId: function() {
		return _.uniqueId('_fujion__');
	},
	
	/**
	 * Resolve a hierarchical reference relative to the specified base.
	 */
	resolveReference: function(base, path) {
		let i = 0;
		path = path === null ? [] : path.split('.');
		
		if (!base) {
			base = eval(path[i++]);
		}
		
		let ref = base;
		
		while (i < path.length && ref) {
			base = ref;
			const name = path[i++];
			ref = ref[name];
		}
		
		return ref ? {base: base, ref: ref} : null;
	},
	
	/**
	 * Resolve embedded Expression Language references.
	 */
	resolveEL: function(base, text, pfx) {
		let i = 0;
		base = base || this;
		pfx = (pfx || '$').charAt(0) + '{';
		
		while ((i = text.indexOf(pfx, i)) >= 0) {
			let j = text.indexOf('}', i + 2);
			j = j == -1 ? text.length : j;
			const exp = text.substring(i + 2, j);
			let ref = exp.length === 0 ? null : this.resolveReference(base, exp);
			ref = ref === null ? '' 
				: _.isFunction(ref.ref) ? ref.ref.apply(base)
				: _.isObject(ref.ref) ? ''
				: ref.ref;
			text = text.substring(0, i) + ref + text.substring(j + 1);
			i += exp.length - ref.length;
		}
		
		return text;
	},
	
	/**
	 * Escape reserved characters in text. 
	 */
	escapeHtml: function(text) {
		const div = document.createElement('div');
		div.appendChild(document.createTextNode(text));
		return div.innerHTML;
	},
	
	/**
	 * Replaces all occurrences of a substring.
	 * 
	 * @param {string} src The string to modify.
	 * @param {string} sub The substring to replace.
	 * @param {string} repl The replacement string.
	 * @return {string} The modified string.
	 */
	replaceAll: function(src, sub, repl) {
		return src.split(sub).join(repl);
	},
	
	/**
	 * Combine sources into a single object
	 * 
	 * @param {object[]} sources Array of source objects.
	 * @param {function} filter Optional filter that returns true for each element
	 * 		to be copied.
	 * @return {object} An object that is the result of the merging of all source
	 * 		objects.
	 */
	combine: function(sources, filter) {
		const target = {};
		
		for (let i = 0; i < sources.length; i++) {
			if (sources[i]) {
				_.forOwn(sources[i], function(value, key, object) {
					if (!filter || filter.apply(object, arguments)) {
						target[key] = value;
					}
				});
			}
		}
		
		return target;
	},
	
	/**
	 * Recursively copies members from source into destination in flattened form.
	 * 
	 * @param {object} source Source object to copy.
	 * @param {object} dest Destination of copy operation.
	 * @param {String} [prefix] Prefix to be prepended to key when writing to destination.
	 * @param {int} [maxdepth] Maximum recursion depth.  Defaults to no recursion.
	 */
	flatten: function(source, dest, prefix, maxdepth) {
		maxdepth = maxdepth ? maxdepth : 0;
		
		_.forIn(source, function(value, key) {
			if (!_.isNil(value) && !_.isFunction(value)) {
				key = _.isArray(source) ? '[' + key + ']' : key;
				key = prefix ? (prefix + _.upperFirst(key)) : key;
				
				if (_.isObject(value)) {
					maxdepth <= 0 ? null : fujion.flatten(value, dest, key, maxdepth - 1);
				} else {
					dest[key] = value;
				}
			}
		});
	},
	
	/**
	 * Clone a DOM element to the specified depth.
	 * 
	 * @param {Node|jquery|Widget} element A DOM element, jquery object, or widget.
	 * @param {int} [depth=0] Depth of clone operation (0 means original element only
	 * 		< 0 means infinite depth).
	 * @param {Node} [parent] The parent for the cloned element.
	 * @return {Node} The cloned element.
	 */
	clone: function(element, depth, parent) {
		element = fujion.ele(element);
		const clone = element.cloneNode(false);
		clone.id ? clone.removeAttribute('id') : null;
		depth = depth || 0;
		
		if (parent) {
			parent.appendChild(clone);
		}
		
		if (depth--) {
			const childNodes = element.childNodes;
			
			for (let i = 0, j = childNodes.length; i < j; i++) {
				fujion.clone(childNodes.item(i), depth, clone);
			}
		}
		
		return clone;
	},
	
	/**
	 * Converts an input string (or array of strings) to a map consisting of
	 * the split values as keys and "true" as values.
	 * 
	 * @param {string|string[]} value The string or string array value.
	 * @param {string} [dlm] The delimiter used for split operation (required if value is a string).
	 * @return {object} A map with split values as keys.
	 */
	stringToSet: function(value, dlm) {
		if (!_.isArray(value)) {
			value = value.split(dlm);
		}
		
		const result = {};
		
		_.forEach(value, function(entry) {
			if (entry) {
				result[entry] = true;
			}
		});
		
		return result;
	},
	
	/**
	 * Converts a set to a delimited string.
	 * 
	 * @param {object} set Set whose values are to be concatenated.
	 * @param {string} dlm Delimiter for separating values.
	 * @return {string} The concatenated list of set values.
	 */
	setToString: function(set, dlm) {
		return _.keys(set).join(dlm);
	},
	
	/**
	 * Removes a set of values from another set.
	 * 
	 * @param {object} set Set whose values are to be removed.
	 * @param {object} values Set with values to be removed.
	 */
	removeFromSet: function(set, values) {
		_.forOwn(values, function(x, key) {
			delete set[key];
		});
		
		return set;
	},
	
	/**
	 * Inserts an element into the array at the specified position.
	 * If the element is already in the array, it is moved to the
	 * specified position.
	 * 
	 * @param {array} array The array to be modified.
	 * @param {object} element The element to be inserted.
	 * @param {number} position The position within the array where the
	 * 		element will be inserted or moved.
	 */
	insertIntoArray: function(array, element, position) {
		const i = array.indexOf(element);
		let changed = false;
	
		if (i >= 0 && i === position) {
			return;
		}
		
		if (i >= 0) {
			array.splice(i, 1);
			changed = true;
		}
		
		if (position >= 0) {
			array.splice(position, 0, element);
			changed = true;
		}
		
		return changed;
	},
	
	import: function(pkgname, callback) {
		const path = System.resolve(pkgname);
		const pkg = System.get(path);
		return pkg ? _callback(pkg) : System.import(path).then(pkg => _callback(pkg));

		function _callback(pkg) {
			pkg = pkg.__useDefault ? pkg.default : pkg;
			return callback ? callback(pkg) : pkg;
		}
	},
	
	saveToFile: function(content, mimetype, filename) {
		mimetype = !mimetype || navigator.userAgent.match(/Version\/[\d\.]+.*Safari/) ? 'application/octet-stream' : mimetype;
		this.import('file-saver', fileSaver => {
			const blob = new Blob([content], {type: mimetype});
			fileSaver.saveAs(blob, filename);
		});
	},
	
	tagIsSupported: function(tag) {
		return !(document.createElement(tag) instanceof HTMLUnknownElement);
	}	
		
}});  // end module definition
