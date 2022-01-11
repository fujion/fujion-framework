'use strict';

define('fujion-leaflet', ['fujion-core', 'fujion-widget', 'leaflet', 'leaflet-css'], (fujion, wgt, Leaflet) => {
	
	/**
	 * Wrapper for leaflet.js
	 */
	fujion.widget.Leaflet = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._reset();
			this._super();
		},
		
		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/

		reset : function() {
			if (this._leaflet) {
				this._leaflet.remove();
				delete this._leaflet;
			}
		},
		
		run : function(options) {
			this.reset();
			this._leaflet = Leaflet.map(this.widget$[0], options);
			Leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(this._leaflet);

		},
		
		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div></div>')
		}
	});

	return fujion.widget;
});
