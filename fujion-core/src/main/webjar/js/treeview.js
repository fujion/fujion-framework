'use strict';

define('fujion-treeview', ['fujion-core', 'fujion-widget', 'fujion-treeview-css'], function(fujion) { 
	
	/******************************************************************************************************************
	 * Tree view widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Treeview = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		anchor$: function() {
			return this.sub$('inner');
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({showRoot: false, showLines: true, showToggles: true});
		},		
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $(this.resolveEL('<div><ul id="${id}-inner"></div>'));
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_showLines: function(v) {
			this.toggleClass('fujion_treeview-nolines', !v);
		},
		
		s_showRoot: function(v) {
			this.toggleClass('fujion_treeview-noroot', !v);
		},
		
		s_showToggles: function(v) {
			this.toggleClass('fujion_treeview-notoggles', !v);
		}
		
	});
	
	/******************************************************************************************************************
	 * Tree node widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Treenode = fujion.widget.LabeledImageWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		anchor$: function(child$) {
			return child$.fujion$widget().wclass === 'Treenode' ? this.sub$('inner') : this.sub$('cnt');
		},
		
		onAddChild: function(child) {
			this._updateCount(child, 1);
		},
		
		onRemoveChild: function(child) {
			this._updateCount(child, -1);
		},
		
		/*------------------------------ Events ------------------------------*/
		
		handleClick: function(event) {
			_toggle(this, !this.getState('collapsed'), event.altKey);
			return false;
			
			function _toggle(node, collapsed, cascade) {
				if (node.updateState('collapsed', collapsed)) {
					node.trigger('toggle', {collapsed: collapsed});
				}
				
				if (cascade) {
					node.forEachChild(function(child) {
						_toggle(child, collapsed, cascade);
					});
				}
			}
		},
		
		handleSelect: function(event) {
			if (this.updateState('selected', true, true)) {
				this.trigger('change', {value: true});
			}
			
			return false;
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({collapsed: false, chnodes: 0});
			this.forwardToServer('change toggle');
		},
				
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this.widget$.on('mouseenter mousemove mouseleave contextmenu', false);
			this.sub$('cnt').on('click', this.handleSelect.bind(this));
			this.sub$('btn').on('click', this.handleClick.bind(this));
			this._updateToggle();
		},
		
		getDragHelper: function() {
			return fujion.clone(this.sub$('lbl'), -1);
		},
		
		render$: function() {
			var dom = 
				  '<li>'
				+   '<span id="${id}-btn" />'
				+   '<span id="${id}-cnt">'
				+     this.getDOMTemplate(':image', 'badge', 'label')
				+   '</span>'
				+   '<ul id="${id}-inner"/>'
				+ '</li>';
			return $(this.resolveEL(dom));
		},
		
		_updateCount(child, inc) {
			if (child.wclass === 'Treenode') {
				var chnodes = this.getState('chnodes'),
					newval = chnodes + inc;
				this.setState('chnodes', newval);
				
				if (!chnodes !== !newval) {
					this._updateToggle();
				}
			}
		},
		
		_updateToggle: function() {
			var chnodes = this.getState('chnodes'),
				collapsed = chnodes && this.getState('collapsed'),
				clazz = !chnodes ? 'fujion_treenode-nochildren' : collapsed ? 'fa fa-caret-square-o-right' : 'fa fa-caret-square-o-down';
			
			this.attr('class', clazz, this.sub$('btn'));
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_chnodes: function(v) {
			this._updateToggle();
		},
		
		s_collapsed: function(v) {
			this.sub$('inner').toggleClass('d-none', v);
			this._updateToggle();
		},
		
		s_selected: function(v) {
			this.toggleClass('fujion_treenode-selected', v);
		}

	});
		
	return fujion.widget;
});