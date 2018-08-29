'use strict';

define('fujion-gmaps', [
	'fujion-core', 
	'fujion-widget',
	'google-maps',
	'fujion-gmaps-css'], 
	
	function(fujion, Widget, GoogleMapsLoader) { 
	
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
			delete this._options;
			this.widget$.empty();
		},
		
		invoke: function(fnc, args) {
			return this._map ? this._map[fnc].apply(this._map, args) : null;
		},
		
		run: function(options, loaderOptions) {
			this._options = options || this._options;
			this._load(this._run.bind(this), loaderOptions);
		},
		
		_load: function(cb, loaderOptions) {
			delete this._map;
			GoogleMapsLoader.KEY ? null : loaderOptions ? _.assign(GoogleMapsLoader, loaderOptions) : null;
			GoogleMapsLoader.KEY ? GoogleMapsLoader.load(cb) : null;
		},
		
		_run: function(google) {
			var options = this._options;
			
			if (options) {				
				if (options.streetViewOptions) {
					options.streetView = new google.maps.StreetViewPanorama(this.widget$[0], options.streetView);
				}
				
				this._map = new google.maps.Map(this.widget$[0], options);

				var map = this._map,
					self = this;
				
				_.forEach(this._eventsToForward, function(eventName) {
					var handler = self[eventName + 'Handler'];
					handler = handler ? handler : self.triggerMapEvent;
					map.addListener(eventName, handler.bind(self, eventName));
				});
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this.run();
		},
		
		render$: function() {
			return $('<div/>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_tilt: function(v) {
			this._options ? this._options.tilt = v : null;
			this._rendering || !this._map ? null : this._map.setTilt(v);
		},
		
		s_zoom: function(v) {
			this._options ? this._options.zoom = v : null;
			this._rendering || !this._map ? null : this._map.setZoom(v);
		} 
		
	});

	return fujion.widget;
});


