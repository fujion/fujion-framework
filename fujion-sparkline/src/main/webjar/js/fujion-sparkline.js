'use strict';

define('fujion-sparkline', [
	'fujion-core', 
	'fujion-widget',
	'jquery-sparkline'], (fujion, Widget) => {
	
	/******************************************************************************************************************
	 * A sparkline widget
	 ******************************************************************************************************************/ 
	Widget.Sparkline = Widget.UIWidget.extend({
	
		/*------------------------------ Other ------------------------------*/
		
		clear: function() {
			this.widget$.empty();
		},
		
		run: function(data, op1, op2) {
			const options = _.assign({}, op1, op2);
			const w = this.widget$.width();
			const h = this.widget$.height();
			options.width = w ? w + 'px' : 'auto';
			options.height = h ? h + 'px' : 'auto';
			this.widget$.sparkline(data, options);
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<div>/');
		}
		
	});

	
	return fujion.widget;
});


