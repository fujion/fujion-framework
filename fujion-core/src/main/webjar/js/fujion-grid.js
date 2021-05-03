'use strict';

define('fujion-grid', ['fujion-core', 'fujion-widget', 'fujion-grid-css'], fujion => {
	/******************************************************************************************************************
	 * Grid widget
	 ******************************************************************************************************************/
	
	fujion.widget.Grid = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({autoSize: true})
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<table><caption></caption></table>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_autoSize: function(v) {
			this.toggleClass('fujion_grid-fixed', !v);
		},
		
		s_title: function(v) {
			this.widget$.children('caption').text(v);
		}
	
	});
	
	/******************************************************************************************************************
	 * Grid columns widget
	 ******************************************************************************************************************/
	
	fujion.widget.Columns = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		anchor$: function() {
			return this.sub$('inner');
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $(this.resolveEL('<thead><tr id="${id}-inner"></tr></thead>'));
		}
		
	});
	
	/******************************************************************************************************************
	 * Grid column widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Column = fujion.widget.LabeledImageWidget.extend({		

		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.forwardToServer('sort');
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this.forward(this.sub$('dir'), 'click', 'sort');
		},
		
		render$: function() {
			const dom = '<th>'
				+ this.getDOMTemplate(':image', 'label', ':sortOrder') 
				+ '</th>';
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_label: function(v) {
			this.sub$('lbl').text(v);
		},
		
		s_sizable: function(v) {
			const active = !!this.widget$.resizable('instance');
			const newactive = !_.isNil(v);
			
			if (active !== newactive) {
				newactive ? this.widget$.resizable({handles: 'e'}) : this.widget$.resizable('destroy');
			}
		}, 
		
		s_sortOrder: function(v, old) {
			if (!v !== !old) {
				this.rerender();
			}
			
			if (v) {
				this.sub$('dir')
					.toggleClass('fa-chevron-up', v === 'ASCENDING')
					.toggleClass('fa-chevron-down', v === 'DESCENDING')
					.toggleClass('fa-sort', v === 'UNSORTED' || v === 'NATIVE');
			}
		}
	
	});
	
	/******************************************************************************************************************
	 * Grid rows widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Rows = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({selectable: 'NO'});
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<tbody>');
		},
	
		/*------------------------------ State ------------------------------*/
		
		s_selectable: function(v) {
			const self = this;
			const active = !!this.widget$.selectable('instance');
			
			_selectable(v !== 'NO');
			
			function _selectable(selectable) {
				if (selectable && !active) {
					self.widget$.selectable({
						appendTo: '#fujion_root',
						filter: 'tr',
			            cancel: 'input,textarea,button,select,option,.fa',
						selected: _select,
						unselected: _unselect
					})
				} else if (!selectable && active){
					self.widget$.selectable('destroy');
				}
			}

			function _select(event, ui) {
				_doSelect(ui.selected, true);
			}
			
			function _unselect(event, ui) {
				_doSelect(ui.unselected, false);
			}
			
			function _doSelect(target, selected) {
				const w = fujion.wgt(target);
				
				if (w.updateState('selected', selected, true)) {
					w.trigger('change', {value: selected});
				}
			}
		}
	
	});
	
	/******************************************************************************************************************
	 * Grid row widget
	 ******************************************************************************************************************/
	
	fujion.widget.Row = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/
		
		addChild: function(child, index) {
			if (child.wclass !== 'Rowcell') {
				child = fujion.widget.Connector.create('<td>', child);
			}
			
			this._super(child, index);
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({selected: false});
			this.forwardToServer('change');
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<tr>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_selected: function(v) {
			this.toggleClass('ui-selected', v);
		}
		
	});

	/******************************************************************************************************************
	 * Grid row cell widget
	 ******************************************************************************************************************/
	
	fujion.widget.Rowcell = fujion.widget.LabeledWidget.extend({
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			const dom =
				'<td>'
			  + this.getDOMTemplate(':label')
			  + '</td>';
			
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_colspan: function(v) {
			this.attr('colspan', v);
		},
		
		
		s_label: function(v, old) {
			if (!!old !== !!v) {
				this.rerender();
			}
			
			this._super();
		},
		
		s_rowspan: function(v) {
			this.attr('rowspan', v);
		}
		
	});

	return fujion.widget;
});
