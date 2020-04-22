'use strict';

define('fujion-carousel', ['fujion-core', 'fujion-widget', 'fujion-carousel-css'], function(fujion) {
	
	/******************************************************************************************************************
	 * Carousel widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Carousel = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Events ------------------------------*/

		handleSlide: function(event) {
			this._children[event.from].triggerChanged(false);
			this._children[event.to].triggerChanged(true);
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({interval: 0, keyboard: true, ride: false, wrap: true});
			this.toggleClass('carousel slide', true);
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this.widget$.carousel();
			this.widget$.on('slid.bs.carousel', this.handleSlide.bind(this));
		},

		render$: function() {
			var dom =
				'<div data-ride="carousel">'
				+	'<ol id="${id}-ind" class="carousel-indicators"></ol>'
				+ 	'<div id="${id}-inner" class="carousel-inner"></div>'
				+	'<a class="carousel-control-prev" href="#${id}" role="button" data-slide="prev">'
				+		'<span class="carousel-control-prev-icon" aria-hidden="true"></span>'
				+		'<span class="sr-only">Previous</span>'
				+	'</a>'
				+	'<a class="carousel-control-next" href="#${id}" role="button" data-slide="prev">'
				+		'<span class="carousel-control-next-icon" aria-hidden="true"></span>'
				+		'<span class="sr-only">Next</span>'
				+	'</a>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},
		
		/*------------------------------ State ------------------------------*/

		s_interval: function(v) {
			this.widget$.carousel({interval: v});
		},

		s_keyboard: function(v) {
			this.widget$.carousel({keyboard: v});
		},

		s_ride: function(v) {
			this.widget$.carousel({ride: v});
		},

		s_wrap: function(v) {
			this.widget$.carousel({wrap: v});
		}

	});
	
	/******************************************************************************************************************
	 * Carousel Item widget
	 ******************************************************************************************************************/ 
	
	fujion.widget.Carouselitem = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Events ------------------------------*/

		triggerChanged: function(selected) {
			this.setState('selected', selected);
			this.trigger('change', {value: selected});
		},

		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.toggleClass('carousel-item', true);
			this.forwardToServer('change')
		},
				
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<div></div>');
		},

		/*------------------------------ State ------------------------------*/

		s_selected: function(v) {
			this.toggleClass('active', v);
		}

	});
		
	return fujion.widget;
});
