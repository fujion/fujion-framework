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
			this._updateIndicators();
			this.widget$.carousel();
			this.widget$.on('slid.bs.carousel', event => this.handleSlide(event));
		},

		beforeRender: function() {
			this.indicators$ = this.sub$('ind');
		},

		_updateIndicators: function() {
			const indicators = this.indicators$.children();
			const showIndicators = this.getState('indicators') && indicators.length > 0;
			this.indicators$.toggleClass('d-none', !showIndicators);
			indicators.each((index, ele) => ele.setAttribute('data-bs-slide-to', index));
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
			return this.resolveEL$(dom);
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

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

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

		beforeRender: function() {
			const dom = '<button type="button" data-bs-target="#${_parent.id}"></button>'
			const ind$ = this._ancillaries.indicator$ = this.resolveEL$(dom);
			ind$.data('attach', () => this._attachIndicator());
			this._attachIndicator();
		},

		_attachIndicator: function() {
			const ind$ = this._ancillaries.indicator$;
			const indParent$ = this._parent.indicators$;
			indParent$.append(ind$);
			this._parent._updateIndicators();
		},

		render$: function() {
			const dom =
				'<div>' +
				'  <div id="${id}-inner"></div>' +
				'  <div id="${id}-cap" class="h5 carousel-caption"></div>' +
				'</div>';
			return this.resolveEL$(dom);
		},

		/*------------------------------ State ------------------------------*/

		s_caption: function(v) {
			this.sub$('cap').text(v);
		},

		s_selected: function(v) {
			this.toggleClass('active', v);
			this._ancillaries.indicator$.toggleClass('active', v);
		}

	});
		
	return fujion.widget;
});
