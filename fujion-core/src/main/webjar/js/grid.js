'use strict';

define('fujion-grid', ['fujion-core', 'fujion-widget', 'fujion-grid-css'], function(fujion) { 
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
			return $('<table><caption/></table>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		autoSize: function(v) {
			this.toggleClass('fujion_grid-fixed', !v);
		},
		
		title: function(v) {
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
			return $(this.resolveEL('<thead><tr id="${id}-inner"/></thead>'));
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
			var dom = '<th>' 
				+ this.getDOMTemplate(':image', 'label', ':sortOrder') 
				+ '</th>';
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/
		
		label: function(v) {
			this.sub$('lbl').text(v);
		},
		
		sizable: function(v) {
			var active = !!this.widget$.resizable('instance'),
				newactive = !_.isNil(v);
			
			if (active != newactive) {
				newactive ? this.widget$.resizable({handles: 'e'}) : this.widget$.resizable('destroy');
			}
		}, 
		
		sortOrder: function(v, old) {
			var self = this;
			
			if (!v !== !old) {
				this.rerender();
			}
			
			if (v) {
				this.sub$('dir')
					.toggleClass('glyphicon-chevron-up', v === 'ASCENDING')
					.toggleClass('glyphicon-chevron-down', v === 'DESCENDING')
					.toggleClass('glyphicon-sort', v === 'UNSORTED' || v === 'NATIVE');
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
		
		selectable: function(v) {
			var self = this,
				active = !!this.widget$.selectable('instance');
			
			_selectable(v !== 'NO');
			
			function _selectable(selectable) {
				if (selectable && !active) {
					self.widget$.selectable({
						appendTo: '#fujion_root',
						filter: 'tr',
			            cancel: 'input,textarea,button,select,option,.glyphicon',
						selected: _select,
						unselected: _unselect
					})
				} else if (!selectable && active){
					self.widget$.selectable('destroy');
				}
			};
			
			function _select(event, ui) {
				_doSelect(ui.selected, true);
			}
			
			function _unselect(event, ui) {
				_doSelect(ui.unselected, false);
			}
			
			function _doSelect(target, selected) {
				var w = fujion.wgt(target);
				
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
		
		selected: function(v) {
			this.toggleClass('ui-selected', v);
		}
		
	});

	/******************************************************************************************************************
	 * Grid row cell widget
	 ******************************************************************************************************************/
	
	fujion.widget.Rowcell = fujion.widget.LabeledWidget.extend({
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			var dom = 
				'<td>'
			  + this.getDOMTemplate(':label')
			  + '</td>';
			
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/
		
		colspan: function(v) {
			this.attr('colspan', v);
		},
		
		
		label: function(v, old) {
			if (!!old !== !!v) {
				this.rerender();
			}
			
			this._super();
		},
		
		rowspan: function(v) {
			this.attr('rowspan', v);
		}
		
	});

	return fujion.widget;
});