'use strict';

define('fujion-multimedia', ['fujion-core'], (fujion) => {

	/**
	 * Base multimedia widget.
	 */
	fujion.widget.MultimediaWidget = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		handleVolumeChange: function() {
			const w$ = this.widget$;
			const self = this;
			updateState('volume');
			updateState('muted');

			function updateState(prop) {
				const val = w$ ? w$.prop(prop) : undefined;
				return val === undefined ? null : self.updateState(prop, val);
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({volume: 0.5, muted: false});
		},

		/*------------------------------ Other ------------------------------*/

		fade: function(from, to, duration) {
			const inc = (to - from) / (duration / 100);
			this.stop();
			this.updateState('volume', from, true);
			this.play();
			let nosync = true;
			this._interval = setInterval(() => {
				from += inc;
				duration -= 100;

				if (duration <= 0) {
					this._stopFade();
					from = to;
					nosync = false;
				}

				this.updateState('volume', from, nosync);
			}, 100);
		},

		seek: function(v) {
			this.prop('currentTime', v);
		},

		pause: function() {
			this.widget$ ? this.widget$.get(0).pause() : null;
		},

		play: function() {
			this.widget$ ? this.widget$.get(0).play() : null;
		},

		stop: function() {
			this._stopFade();
			this.widget$ ? this.widget$.get(0).load() : null;
		},

		_stopFade: function() {
			if (this._fade) {
				clearInterval(this._fade);
				this._fade = null;
			}
		},

		/*------------------------------ Rendering ------------------------------*/

		addSources: function(w$) {
			let src = this.getState('src');

			if (src) {
				src = src.split(',');

				for (let i = 0; i < src.length; i++) {
					w$.append('<source src="' + src[i].trim() + '"></source>');
				}
			}

			return w$;
		},

		afterRender: function() {
			this._super();
			this.widget$.on('volumechange', event => this.handleVolumeChange(event))
		},

		/*------------------------------ State ------------------------------*/

		s_autoplay: function(v) {
			this.prop('autoplay', v);
		},

		s_controls: function(v) {
			this.prop('controls', v);
		},

		s_muted: function(v) {
			this.prop('muted', v);
		},

		s_rate: function(v) {
			this.prop('playbackRate', v);
		},

		s_src: function(v) {
			this.rerender();
		},

		s_volume: function(v) {
			this.prop('volume', v);
		}
	});

	fujion.widget.Audio = fujion.widget.MultimediaWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._super();
		},

		init: function() {
			this._super();
		},

		/*------------------------------ Other ------------------------------*/


		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return this.addSources($('<audio></audio>'));
		},

		/*------------------------------ State ------------------------------*/

	});

	fujion.widget.Video = fujion.widget.MultimediaWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._super();
		},

		init: function() {
			this._super();
		},

		/*------------------------------ Other ------------------------------*/


		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return this.addSources($('<video></video>'));
		},

		/*------------------------------ State ------------------------------*/

	});

	return fujion.widget;
});
