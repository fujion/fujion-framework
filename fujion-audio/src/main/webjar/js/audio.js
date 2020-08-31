'use strict';

define('fujion-audio', ['fujion-core', 'fujion-widget', 'howler'], (fujion, wgt, howl) => {
	
	/**
	 * Wrapper for howler.js
	 */
	fujion.widget.Audio = fujion.widget.BaseWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleEvent: function(event, id, error) {
			this.trigger(event, {id: id, error: error || null});
		},

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._super();
			this.unload();
		},
		
		init: function() {
			this._super();
			this.initState({volume: 50, html5: true});
			this._howler = null;
			this._restart = false;
			this._options = {
				onload: id => this.handleEvent('load', id),
				onloaderror: (id, error) => this.handleEvent('loaderror', id, error),
				onplayerror: (id, error) => this.handleEvent('playerror', id, error),
				onplay: id => this.handleEvent('play', id),
				onend: id => this.handleEvent('end', id),
				onpause: id => this.handleEvent('pause', id),
				onstop: id => this.handleEvent('stop', id),
				onmute: id => this.handleEvent('mute', id),
				onvolume: id => this.handleEvent('volume', id),
				onrate: id => this.handleEvent('rate', id),
				onseek: id => this.handleEvent('seek', id),
				onfade: id => this.handleEvent('fade', id),
				onunlock: id => this.handleEvent('unlock', id)
			};
		},
		
		/*------------------------------ Other ------------------------------*/

		fade: function(from, to, duration) {
			this._howler ? this._howler.fade(from / 100, to / 100, duration) : null;
		},

		newInstance: function() {
			this.unload();
			this._howler = new howl.Howl(this._options);
		},

		pause: function() {
			this._howler ? this._howler.pause() : null;
		},

		play: function() {
			this._restart || !this._howler ? this.newInstance() : null;
			this._restart = false;
			this._howler.play();
		},

		seek: function(position) {
			this._howler ? this._howler.seek(position / 1000) : null;
		},

		stop: function() {
			this._howler ? this._howler.stop() : null;
		},

		unload: function() {
			if (this._howler) {
				this._howler.unload();
				this._howler = null;
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<span class="d-none"></span>');
		},

		/*------------------------------ State ------------------------------*/

		s_html5: function(v) {
			this._options.html5 = v;
			this._restart = true;
		},

		s_loop: function(v) {
			this._options.loop = v;
			this._howler ? this._howler.loop(v) : null;
		},

		s_mute: function(v) {
			this._options.mute = v;
			this._howler ? this._howler.mute(v) : null;
		},

		s_preload: function(v) {
			this._options.preload = v;
			this._restart = true;
		},

		s_rate: function(v) {
			v ? this._options.rate = v : delete this._options.rate;
			this._howler ? this._howler.rate(v) : null;
		},

		s_src: function(v) {
			v ? this._options.src = v : delete this._options.src;
			this._restart = true;
		},

		s_volume: function(v) {
			v = v / 100;
			this._options.volume = v;
			this._howler ? this._howler.volume(v) : null;
		}
	});

	return fujion.widget;
});
