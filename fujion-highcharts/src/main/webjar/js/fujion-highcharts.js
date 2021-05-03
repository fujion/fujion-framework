'use strict';

define('fujion-highcharts', ['fujion-core', 'fujion-widget', 'highcharts'], (fujion, wgt, Highcharts) => {
	
	/**
	 * Wrapper for HighCharts
	 */
	fujion.widget.HChart = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Events ------------------------------*/

		handleResize : function() {
			if (!this._resizing && this._chart) {
				const w$ = this.widget$;
				this._chart.setSize(w$.width(), w$.height());
			}
		},
		
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this._chart = null;
			this._resizing = false;
		},
		
		/*------------------------------ Other ------------------------------*/

		export : function(func) {
			if (this._chart) {
				func ? func.call(this._chart) : this._chart.exportChart();
			}
		},
	
		global : function(options) {
			if (options)
				Highcharts.setOptions(options);
		},
		
		print : function(func) {
			if (this._chart)
				func ? func.call(this._chart) : this._chart.print();
		},
	
		redraw : function() {
			if (this._chart)
				this._chart.redraw();
		},
	
		reset : function() {
			if (this._chart) {
				this._chart.destroy();
				this._chart = null;
			}
		},
		
		run : function(options) {
			options.chart.renderTo = this.id;
			this._resizing = true;
			this.reset();
			this._chart = new Highcharts.Chart(options);
			this._resizing = false;
		},
	
		title : function(options) {
			if (this._chart) {
				this._chart.setTitle(options.title, options.subtitle);
			}
		},
	
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('resize', this.handleResize.bind(this));
		},
		
		render$: function() {
			return $('<div></div>')
		}
	});

	return fujion.widget;
});
