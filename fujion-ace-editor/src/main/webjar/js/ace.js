'use strict';

define('fujion-ace', [
	'fujion-core', 
	'fujion-widget',
	'ace-editor',
	'fujion-ace-css'], (fujion, Widget, ace) => {

	ace.config.set('basePath', 'webjars/webjar-ace/dist');

	/**
	 * Base Wrapper for Ace Editor
	 */
	Widget.AceEditor = Widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/

		blurHandler: function() {
			if (this._changed) {
				this._changed = false;
				this.trigger('change', {value: this._editor.getValue()});
			}
		},

		changeHandler: function() {
			this._changed = true;
			this.getState('synced') ? this.blurHandler() : null;
		},

		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.forwardToServer('change');
		},
		
		destroy: function() {
			this._destroy();
			this._super();
		},
		
		_destroy: function() {
			this._editor ? this._editor.destroy() : null;
			delete this._editor;
		},
		
		/*------------------------------ Other ------------------------------*/
	
		clear: function() {
		    this._editor.setValue('');
		    this._editor.focus();
		},

		find: function(options) {
			const search = new ace.Search(options);
			return search.find(this._editor.session);
		},

		findAll: function(options) {
			const search = new ace.Search(options);
			return search.findAll(this._editor.session);
		},

		scrollBy: function(x, y) {
			this._editor.renderer.scrollBy(x, y);
		},

		scrollToRow: function(row) {
			this._editor.renderer.scrollToRow(row);
		},

		scrollToX: function(x) {
			this._editor.renderer.scrollToX(x);
		},

		scrollToY: function(y) {
			this._editor.renderer.scrollToY(y);
		},

		/*------------------------------ Rendering ------------------------------*/

		beforeRender: function() {
			this._super();
			this._editor = ace.edit(this.widget$.children()[0]);
			this._editor.on('blur', this.blurHandler.bind(this));
			this._editor.on('change', this.changeHandler.bind(this));
		},
		
		render$: function() {
			return $('<div><textarea></textarea></div>');
		},
		
		/*------------------------------ State ------------------------------*/

		s_animatedScroll: function(v) {
			this._editor.renderer.setAnimatedScroll(v);
		},

		s_mode: function(v) {
			this._editor.session.setMode(v ? 'ace/mode/' + v : null);
		},

		s_readonly: function(v) {
			this._editor.setReadOnly(v);
		},

		s_padding: function(v) {
			this._editor.renderer.setPadding(v);
		},

		s_showCursor: function(v) {
			v ? this._editor.renderer.showCursor() : this._editor.renderer.hideCursor();
		},

		s_showGutter: function(v) {
			this._editor.renderer.setShowGutter(v);
		},

		s_showInvisibles: function(v) {
			this._editor.renderer.setShowInvisibles(v);
		},

		s_showPrintMargin: function(v) {
			this._editor.renderer.setShowPrintMargin(v);
		},

		s_theme: function(v) {
			this._editor.renderer.setTheme(v ? 'ace/theme/' + v : null);
		},

		s_value: function(v) {
			this._editor.setValue(v);
		}
		
	});
	
	return fujion.widget;
});
