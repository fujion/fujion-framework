'use strict';

define('fujion-captcha', [
	'fujion-core', 
	'fujion-widget', 
	'dynamic/reCAPTCHA.js', 
	'fujion-captcha-css'], 
	
	function(fujion, Widget) { 
	
	/**
	 * CAPTCHA widget
	 */
	Widget.Captcha = Widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({theme: 'DARK', size: 'NORMAL', type: 'LIGHT'});
		},
		
		destroy: function() {
			this._super();
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
			var key = this.getState('siteKey');
			
			_.isNil(key) ? null :  grecaptcha.render(this.widget$[0], {
				theme: this.getState('theme').toLowerCase(),
				size: this.getState('size').toLowerCase(),
				type: this.getState('type').toLowerCase(),
				sitekey: key
			});
		},
		
		render$: function() {
			return $('<div/>');
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