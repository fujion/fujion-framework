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
		
		anchor$: function() {
			return this.sub$('inner');
		},
		
		onAddChild: function() {
			if (this.getChildCount() === 1) {
				this._updateToggle();
			}
		},
		
		onRemoveChild: function() {
			if (!this.getChildCount()) {
				this._updateToggle();
			}
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
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({collapsed: false});
			this.forwardToServer('change toggle');
		},
				
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this.widget$.on('mouseenter mousemove mouseleave contextmenu', false);
			this.widget$.find('>a').on('click', this.handleSelect.bind(this));
			this.sub$('ctl').on('click', this.handleClick.bind(this));
			this._updateToggle();
		},
		
		getDragHelper: function() {
			return fujion.clone(this.sub$('lbl'), -1);
		},
		
		render$: function() {
			var dom = 
				  '<li>'
				+ ' <span id="${id}-ctl" class="glyphicon"/>'
				+ ' <a>'
				+ this.getDOMTemplate(':image', 'badge', 'label')
				+ ' </a>'
				+ ' <ul id="${id}-inner"/>'
				+ '</li>';
			return $(this.resolveEL(dom));
		},
		
		_updateToggle: function() {
			this.sub$('ctl').toggleClass('fujion_treenode-nochildren', !this.getChildCount());
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_collapsed: function(v) {
			this.sub$('ctl').toggleClass('glyphicon-expand', v)
				.toggleClass('glyphicon-collapse-down', !v);
			this.toggleClass('fujion_treenode-collapsed', v);
		},
		
		s_selected: function(v) {
			this.toggleClass('fujion_treenode-selected', v);
		}

	});
		
	return fujion.widget;
});