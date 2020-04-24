'use strict';

define('fujion-plotly', ['fujion-core', 'fujion-widget', 'plotly-js'], function(fujion, wgt, Plotly) { 
	
	/**
	 * Wrapper for Plotly
	 */
	fujion.widget.Plotly = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this.reset();
			this._super();
		},
		
		init: function() {
			this._super();
			this._onresize = this.resize.bind(this);
		},
		
		/*------------------------------ Other ------------------------------*/

		reset: function() {
			if (this._loaded) {
				$(window).off('resize', this._onresize);
				Plotly.purge(this.id);
				this._loaded = false;
			}
		},
		
		resize: function() {
			Plotly.Plots.resize(this.id);
		},
		
		run: function(params) {
			this.reset();
			Plotly.newPlot(this.id, params).then(() => {
				this._loaded = true;
				
				if (params.layout && params.layout.autosize) {
					$(window).on('resize', this._onresize);
				}
			});
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			this.widget$.on('render', this._onresize);
		},
		
		render$: function() {
			return $('<div></div>')
		}
	});

	return fujion.widget;
});
