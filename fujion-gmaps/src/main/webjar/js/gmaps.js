'use strict';

define('fujion-gmaps', [
	'fujion-core', 
	'fujion-widget',
	'google-maps',
	'fujion-gmaps-css'], 
	
	function(fujion, Widget, GoogleMapsLoader) { 
	
	GoogleMapsLoader.VERSION = '3.31';
	
	/**
	 * Google Maps widget
	 */
	Widget.GMap = Widget.UIWidget.extend({
	
		/*------------------------------ Events ------------------------------*/
		
		_eventsToForward: ['bounds_changed', 'center_changed', 'click', 'heading_changed', 'idle', 
			'maptypeid_changed', 'tilesloaded', 'tilt_changed', 'zoom_changed'],
		
		bounds_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getBounds());
		},
		
		center_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getCenter());
		},
		
		clickHandler: function(event, data) {
			data.latLng ? this.triggerMapEvent('location', data.latLng) : null;
		},
		
		heading_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getHeading());
		},
		
		maptypeid_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getMapTypeId());
		},
		
		tilt_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getTilt());
		},
		
		zoom_changedHandler: function(event) {
			this.triggerMapEvent(event, this._map.getZoom());
		},
		
		triggerMapEvent: function(event, data) {
			if (!this._rendering) {
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
		
		run: function(options) {
			this._options = options = options || this._options;

			if (this.api && options) {				
				if (options.streetViewOptions) {
					options.streetView = new this.api.StreetViewPanorama(this.widget$[0], options.streetView);
				}
				
				this._map = new this.api.Map(this.widget$[0], options);

				var map = this._map,
					self = this;
				
				_.forEach(this._eventsToForward, function(eventName) {
					var handler = self[eventName + 'Handler'];
					handler = handler ? handler : self.triggerMapEvent;
					map.addListener(eventName, handler.bind(self, eventName));
				});
			}
		},
		
		_load: function() {
			var self = this;
			delete this.api;
			GoogleMapsLoader.KEY = this.getState('siteKey');
			GoogleMapsLoader.VERSION = '3.31';

			GoogleMapsLoader.load(function(module) {
				self.api = module.maps;
				self.rerender();
			});
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
		
		siteKey: function(v) {
			if (v === GoogleMapsLoader.KEY) {
				this.api ? null : this._load();
			}  else {
				GoogleMapsLoader.release(this._load.bind(this))
			}
		},
		
		tilt: function(v) {
			this._options ? this._options.tilt = v : null;
			this._rendering || !this._map ? null : this._map.setTilt(v);
		},
		
		zoom: function(v) {
			this._options ? this._options.zoom = v : null;
			this._rendering || !this._map ? null : this._map.setZoom(v);
		} 
		
	});

	return fujion.widget;
});


