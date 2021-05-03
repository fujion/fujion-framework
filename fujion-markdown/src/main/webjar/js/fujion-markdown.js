'use strict';

define('fujion-markdown', [
	'fujion-core', 
	'fujion-widget',
	'showdown',
	'fujion-markdown-css'], (fujion, Widget, showdown) => {

	/******************************************************************************************************************
	 * Widget wrapping Markdown content
	 ******************************************************************************************************************/

	fujion.widget.Markdown = fujion.widget.UIWidget.extend( {

		_converter: new showdown.Converter({
			strikethrough: true,
			tables: true,
			tasklists: true,
			openLinksInNewWindow: true,
			emoji: true,
			parseImgDimensions: true,
			backslashEscapesHTMLTags: true,
			simplifiedAutoLink: true,
			disableForced4SpacesIndentedSublists: true
		}),

		/*------------------------------ Other ------------------------------*/

		_content: function(markdown) {
			this.widget$.empty();
			markdown ? this.widget$.html(this._converter.makeHtml(markdown)) : null;
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			return $('<span>');
		},

		/*------------------------------ State ------------------------------*/

		s_content: function(v) {
			this._content(v);
			this.setState('src', null);
		},

		s_src: function(v) {
			this.widget$.empty();
			this.setState('content', null);

			if (v) {
				$.ajax(v, {
					dataType: 'text',
					success: this._content.bind(this)
				});
			}
		}

	});

	return fujion.widget;
});


