'use strict';

define('fujion-gmaps', [
	'fujion-core', 
	'fujion-widget',
	'fujion-gmaps-css'],
	
	(fujion, Widget) => {

	const CALLBACK_NAME = 'fujion_gmap_callback';
	const API_URL = 'https://maps.googleapis.com/maps/api/js?';
	let loading = false;

	function loadGMap(options, callback) {
		if (loading) {
			return;
		}

		if (!window.google) {
			loading = true;
			options.callback = CALLBACK_NAME;
			const URL = API_URL + $.param(options);
			window[CALLBACK_NAME] = () => {
				loading = false;
				delete window[CALLBACK_NAME];
				callback(window.google);
			};
			$.getScript(URL);
		} else {
			callback(window.google);
		}
	}

	/**
	 * Google Maps widget
	 */
	Widget.GMap = Widget.UIWidget.extend({
	
		/*------------------------------ Events ------------------------------*/
		
		_eventsToForward: ['bounds_changed', 'center_changed', 'click', 'heading_changed', 'idle', 
			'maptypeid_changed', 'tilesloaded', 'tilt_changed', 'zoom_changed'],

		bounds_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getBounds(), true);
		},
		
		center_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getCenter(), true);
		},
		
		clickHandler: function(event, data) {
			this.triggerMapEvent('location', data.latLng, true);
		},
		
		heading_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getHeading(), true);
		},
		
		maptypeid_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getMapTypeId(), true);
		},
		
		tilt_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getTilt(), true);
		},
		
		zoom_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getZoom(), true);
		},
		
		triggerMapEvent: function(event, data, ignoreNil) {
			if (!this._rendering && !(ignoreNil && _.isNil(data))) {
				data = data && data.toJSON ? data.toJSON() : data;
				this.trigger('gmap_' + event, {value: data});
			}
		},

		/*------------------------------ Other ------------------------------*/

		clear: function() {
			delete this._map;
			this.widget$.empty();
		},
		
		invoke: function(fnc, args) {
			const map = this._map;
			return map ? map[fnc].apply(map, args) : null;
		},
		
		run: function(options, loaderOptions) {
			this._options = options;

			if (!this.google) {
				loadGMap(loaderOptions, google => {
					this.google = google;
					this._draw();
				})
			} else {
				this._draw();
			}
		},

		_draw: function() {
			const options = this._options;
			const anchor = this.widget$ && this.widget$[0];

			if (options && this.google && anchor) {

				if (options.streetViewOptions) {
					options.streetView = new this.google.maps.StreetViewPanorama(anchor, options.streetView);
				}
				
				this._map = new this.google.maps.Map(anchor, options);
				_.forEach(this._eventsToForward, eventName => {
					var handler = this[eventName + 'Handler'];
					handler = handler ? handler : this.triggerMapEvent;
					this._map.addListener(eventName, handler.bind(this, eventName));
				});
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this._draw();
		},
		
		render$: function() {
			return $('<div></div>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_tilt: function(v) {
			this._options ? this._options.tilt = v : null;
			const map = this._map;
			this._rendering || !map ? null : map.setTilt(v);
		},
		
		s_zoom: function(v) {
			this._options ? this._options.zoom = v : null;
			const map = this._map;
			this._rendering || !map ? null : map.setZoom(v);
		} 
		
	});

	return fujion.widget;
});


