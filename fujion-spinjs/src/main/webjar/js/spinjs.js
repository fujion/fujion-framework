'use strict';

define('fujion-spinjs', [
	'fujion-core', 
	'fujion-widget',
	'spin-js'], function(fujion, Widget, Spinner) { 
	
	/******************************************************************************************************************
	 * A spin.js widget
	 ******************************************************************************************************************/ 
	Widget.SpinJS = Widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/
		
		destroy: function() {
			this.stop();
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/
		
		start: function(options) {
			this.stop();
			this.setState('options', options);
			this.rerender();
		},
		
		stop: function() {
			if (this._spinner) {
				this._spinner.stop();
				delete this._spinner;
				this.setState('options', null);
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			var options = this.getState('options');
			
			if (options) {
				this._spinner = new Spinner(options);
				this._spinner.spin(this.widget$[0]);
			}
		},
		
		render$: function() {
			return $('<span>/');
		}
		
	});

	
	return fujion.widget;
});


