'use strict';

define('fujion-carousel', ['fujion-core', 'fujion-widget', 'fujion-carousel-css'], fujion => {
	
	/******************************************************************************************************************
	 * Carousel widget (wrapper for Bootstrap's Carousel widget).
	 ******************************************************************************************************************/ 
	
	fujion.widget.Carousel = fujion.widget.UIWidget.extend({
		
		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		onAddChild: function(child) {
			const index = child.getIndex();

			const dom =
				'<button type="button" data-bs-target="#${id}" data-bs-slide-to="' + index + '">' +
				'</button>'
		},

		onRemoveChild: function(child) {

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
			this.indicators$ = this.sub$('ind');
			this._updateIndicators();
			this.widget$.on('slid.bs.carousel', event => this.handleSlide(event));
		},

		_updateIndicators: function() {
			const itemCount = this._children.length;
			const showIndicators = this.getState('indicators') && itemCount > 0;
			this.indicators$.toggleClass('d-none', !showIndicators);
		},

		render$: function() {
			const dom =
				'<div>'
				+	'<div id="${id}-ind" class="carousel-indicators"></div>'
				+ 	'<div id="${id}-inner" class="carousel-inner"></div>'
				+	'<button class="carousel-control-prev" type="button" role="button" data-bs-target="#${id}" data-bs-slide="prev">'
				+		'<span class="carousel-control-prev-icon" aria-hidden="true"></span>'
				+		'<span class="visually-hidden">Previous</span>'
				+	'</button>'
				+	'<button class="carousel-control-next" type="button" role="button" data-bs-target="#${id}" data-bs-slide="next">'
				+		'<span class="carousel-control-next-icon" aria-hidden="true"></span>'
				+		'<span class="visually-hidden">Next</span>'
				+	'</button>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_indicators: function(v) {
			this.rerender();
		},

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
