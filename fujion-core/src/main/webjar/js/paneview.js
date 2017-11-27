'use strict';

define('fujion-paneview', ['fujion-core', 'fujion-widget', 'fujion-paneview-css'], function(fujion) { 
	
	/******************************************************************************************************************
	 * Pane view widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Paneview = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		onAddChild: function(child) {
			child._updateSplitter();
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({orientation: 'HORIZONTAL'});
		},		
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<div/>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		orientation: function(v, old) {
			old ? this.toggleClass('fujion_paneview-' + old.toLowerCase(), false) : null;
			this.toggleClass('fujion_paneview-' + v.toLowerCase(), true);
			this.forEachChild(function(child) {
				child._updateSplitter();
			});
		}
		
	});
	
	/******************************************************************************************************************
	 * Pane widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Pane = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Events ------------------------------*/
		
		handleResize: function(event) {
			this._resizing ? fujion.event.stop(event, true) : fujion.event.stopPropagation(event);
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({splittable: false});
		},
				
		/*------------------------------ Other ------------------------------*/
		
		_isHorizontal: function() {
			return !this._parent || this._parent.getState('orientation') === 'HORIZONTAL';
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		beforeRender: function() {
			this.widget$.on('resize', this.handleResize.bind(this));
			this._super();
		},
		
		render$: function() {
			var dom = '<div>'
					+    '<span id="${id}-title" class="${wclazz}-title" />'
					+ '</div>';
			return $(this.resolveEL(dom));
		},
		
		_updateSplitter: function() {
			var spl$ = this.widget$,
				active = !!spl$.resizable('instance'),
				splittable = this.getState('splittable');
			
			if (active === !splittable) {
				if (active) {
					spl$.resizable('destroy');
				} else {
					spl$.resizable({
						containment: 'parent',
						handles: this._isHorizontal() ? 'e' : 's',
					    start: _start.bind(this),
					    stop: _stop.bind(this)
					});
				}
			}
			
			function _start(event, ui) {
				this._resizing = true;
				$('iframe').addClass('fujion-disabled');
			}
			
			function _stop(event, ui) {
				$('iframe').removeClass('fujion-disabled');
				this._resizing = false;
				this.reportSize();
			}
		},
		
		/*------------------------------ State ------------------------------*/
		
		splittable: function(v) {
			this._updateSplitter();
		},
		
		title: function(v) {
			this.sub$('title').text(v);
		}
		
	});
		
	return fujion.widget;
});