'use strict';

define('fujion-plotly', ['fujion-core', 'fujion-widget', 'chart.js'], function(fujion, wgt, Chart) { 
	
	/**
	 * Wrapper for chart.js
	 */
	fujion.widget.ChartJS = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._reset();
			this._super();
		},
		
		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/

		_reset : function() {
			if (this._chart) {
				this._chart.destroy()
				delete this._chart;
			}
		},
		
		_run : function(params) {
			this._reset();
			this._chart = new Chart(this.widget$.find('canvas')[0], params);
		},
		
		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div><canvas/></div>')
		}
	});

	return fujion.widget;
});