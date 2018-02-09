'use strict';

define('fujion-barcode', [
	'fujion-core', 
	'fujion-widget', 
	'JsBarcode', 
	'fujion-barcode-css'], 
	
	function(fujion, Widget, JsBarcode) { 
	
	/**
	 * Bar code widget
	 */
	Widget.BarCode = Widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({format: 'CODE128', flat: false, displayValue: false});
		},
		
		destroy: function() {
			this._super();
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			var value = this.getState('value');
			
			_.isNil(value) ? null :  this.sub$('cnt').JsBarcode(value, {
				format: this.getState('format'),
				background: this.widget$.css('background-color'),
				lineColor: this.widget$.css('color'),
				font: this.widget$.css('font-family'),
				fontSize: this.widget$.css('font-size'),
				textAlign: this.widget$.css('text-align'),
				textPosition: this.widget$.css('vertical-align'),
				displayValue: this.getState('displayValue'),
				flat: this.getState('flat')
			});
		},
		
		render$: function() {
			return $(this.resolveEL('<div><canvas id="${id}-cnt"/></div>'));
		},
		
		/*------------------------------ State ------------------------------*/
		
		displayValue: function(v) {
			this.rerender();
		},
		
		flat: function(v) {
			this.rerender();
		},
		
		format: function(v) {
			this.rerender();
		},
		
		value: function(v) {
			this.rerender();
		}
		
	});

	return fujion.widget;
});