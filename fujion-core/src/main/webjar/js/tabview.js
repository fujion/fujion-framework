'use strict';

define('fujion-tabview', ['fujion-core', 'fujion-widget', 'fujion-tabview-css'], function(fujion) { 
	
	/******************************************************************************************************************
	 * A tab box widget
	 ******************************************************************************************************************/
	
	fujion.widget.Tabview = fujion.widget.UIWidget.extend({
				
		/*------------------------------ Containment ------------------------------*/
		
		anchor$: function() {
			return this.sub$('tabs');
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({tabPosition: 'TOP'});
		},		
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			var dom = 
				  '<div>'
				+   '<ul id="${id}-tabs" class="fujion_tabview-tabs"/>'
				+   '<div id ="${id}-panes" class="fujion_tabview-panes"/>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/
		
		tabPosition: function(v, old) {
			v = 'fujion_tabview-' + (v ? v.toLowerCase() : 'top');
			old = old ? 'fujion_tabview-' + old : null;
			this.replaceClass(old, v);
		}
		
	});
	
	/******************************************************************************************************************
	 * A tab widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Tab = fujion.widget.LabeledImageWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		anchor$: function() {
			return this._ancillaries.pane$;
		},
		
		/*------------------------------ Events ------------------------------*/
		
		handleSelect: function(event) {
			this.trigger('change', {value: true});
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.forwardToServer('change close');
		},
				
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this.sub$('tab').on('click', this.handleSelect.bind(this));
		},
				
		render$: function() {
			var dom = 
				  '<li role="presentation">'
				+   '<a id="${id}-tab">'
				+ this.getDOMTemplate(':image', 'badge', 'label', ':closable')
				+   '</a>'
				+ '</li>',
				self = this;
				
			if (!this._ancillaries.pane$) {
				var pane = '<div id="${id}-pane" class="fujion_tab-pane hidden"/>',
					pane$ = $(this.resolveEL(pane));
				this._ancillaries.pane$ = pane$;
				pane$.data('attach', _attachPane);
				pane$.data('fujion_widget', this);
				_attachPane();
			}
			
			return $(this.resolveEL(dom));
			
			function _attachPane() {
				self._ancillaries.pane$.appendTo(self._parent.sub$('panes'));
			}
		},
		
		/*------------------------------ State ------------------------------*/		
		
		closable: function(v) {
			this.rerender();
			this.toggleClass('fujion_tab-closable', v);
			
			if (v) {
				this.forward(this.sub$('cls'), 'click', 'close');
			}
		},
		
		context: function(v) {
			this._super();
			this.contextMenu(this._ancillaries.pane$, v);
		},
		
		popup: function(v) {
			this._super();
			this.hoverPopup(this._ancillaries.pane$, v);
		},
		
		selected: function(v) {
			this.toggleClass('fujion_tab-selected', v);
			this.sub$('pane').toggleClass('hidden', !v);
			this.widget$.children().blur();
		}
		
	});
	
	return fujion.widget;
});