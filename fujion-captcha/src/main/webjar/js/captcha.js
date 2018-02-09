'use strict';

define('fujion-captcha', [
	'fujion-core', 
	'fujion-widget', 
	'recaptcha', 
	'fujion-captcha-css'], 
	
	function(fujion, Widget) { 
	
	/**
	 * CAPTCHA widget
	 */
	Widget.Captcha = Widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({theme: 'LIGHT', size: 'NORMAL', type: 'IMAGE'});
		},
		
		destroy: function() {
			this._super();
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			
			if (window.recaptcha_defer) {
				window.recaptcha_defer[this.id] = this;
				return;
			}
			
			var key = this.getState('siteKey');
			
			_.isNil(key) ? null :  grecaptcha.render(this.widget$[0], {
				theme: this.getState('theme').toLowerCase(),
				size: this.getState('size').toLowerCase(),
				type: this.getState('type').toLowerCase(),
				sitekey: key
			});
		},
		
		render$: function() {
			return $('<span/>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		siteKey: function(v) {
			this.rerender();
		},
		
		theme: function(v) {
			this.rerender();
		},
		
		size: function(v) {
			this.rerender();
		},
		
		type: function(v) {
			this.rerender();
		}
		
	});

	return fujion.widget;
});

window.recaptcha_defer = {};

window.recaptcha_onload = function() {
	var defer = window.recaptcha_defer;
	window.recaptcha_defer = null;
	
	_.forOwn(defer, function(widget) {
		widget.rerender();
	});
	
}

