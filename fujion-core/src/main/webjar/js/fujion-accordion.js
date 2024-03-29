'use strict';

define('fujion-accordion', ['fujion-core', 'fujion-widget', 'fujion-accordion-css'], (fujion) => {

	/******************************************************************************************************************
	 * Accordion widget
	 ******************************************************************************************************************/

	fujion.widget.Accordion = fujion.widget.UIWidget.extend({

		/*------------------------------ Lifecycle ------------------------------*/

		init: function () {
			this._super();
			this.toggleClass('accordion', true);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function () {
			return $('<div></div>');
		}

	});

	/******************************************************************************************************************
	 * Accordion Item widget
	 ******************************************************************************************************************/

	fujion.widget.Accordionitem = fujion.widget.UIWidget.extend({

		/*------------------------------ Containment ------------------------------*/

		anchor$: function() {
			return this.sub$('inner');
		},

		/*------------------------------ Events ------------------------------*/

		triggerChanged: function (expanded) {
			if (this.setState('expanded', expanded)) {
				this.trigger('change', {value: expanded});
				this._updateStyles(expanded);
			}
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({expanded: false});
			this.toggleClass('accordion-item', true);
			this.forwardToServer('change');
		},

		/*------------------------------ Rendering ------------------------------*/

		afterRender: function () {
			this.sub$('outer')
				.on('show.bs.collapse', () => this.triggerChanged(true))
				.on('hide.bs.collapse', () => this.triggerChanged(false))
				.collapse({
					toggle: false
				});
		},

		render$: function () {
			const dom =
				'<div>'
				+ '<h2 id="${id}-hdr" class="accordion-header">'
				+   '<button id="${id}-btn" class="accordion-button" type="button" '
				+      'data-bs-toggle="collapse" data-bs-target="#${id}-outer" '
				+      'aria-controls="${id}-outer"></button>'
				+ '</h2>'
				+ '<div id="${id}-outer" class="accordion-collapse collapse" aria-labelledby="${id}-hdr">'
				+    '<div id="${id}-inner" class="accordion-body"></div>'
				+ '</div>'
				+'</div>'
			return this.resolveEL$(dom);
		},

		_updateStyles: function (expanded) {
			this.sub$('btn')
				.toggleClass('collapsed bg-light', !expanded)
				.attr('aria-expanded', expanded);
			this.sub$('outer')
				.toggleClass('show', expanded);
		},

		/*------------------------------ State ------------------------------*/

		s_expanded: function (v) {
			this._updateStyles(v);
			this.sub$('outer').collapse(v ? 'show' : 'hide');
		},

		s_title: function(v) {
			this.sub$('btn').text(v);
		}

	});

	return fujion.widget;
});
