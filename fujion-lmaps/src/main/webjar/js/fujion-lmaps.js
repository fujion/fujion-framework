'use strict';

define('fujion-lmaps', ['fujion-core', 'fujion-widget', 'leaflet', 'leaflet-css'], (fujion, wgt, Leaflet) => {
	
	/**
	 * Wrapper for leaflet.js
	 */
	fujion.widget.LeafletMap = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		_eventsToForward: ['resize', 'load', 'click', 'zoomstart', 'movestart',
			'zoom', 'move', 'zoomend', 'moveend'],

		triggerMapEvent: function(event) {
			const evt = _.assign({}, event, {
				type: 'lmap_' + event.type,
				target: undefined,
				originalEvent: undefined,
				sourceTarget: undefined,
				propagatedFrom: undefined
			});
			this.trigger(evt);
		},

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
			if (this._map) {
				this._map.remove();
				delete this._map;
			}
		},
		
		run : function(options) {
			this.reset();
			this._map = Leaflet.map(this.widget$[0], options);
			_.forEach(this._eventsToForward, eventName => this._map.on(eventName, e => this.triggerMapEvent(e)));
			Leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(this._map);

		},
		
		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<div></div>')
		}
	});

	return fujion.widget;
});
