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
		
		/*------------------------------ Rendering ------------------------------*/

		beforeRender: function() {
			this._super();
			const mode = this.getState('mode');
			this._editor = ace.edit(
				this.widget$.children()[0], {
					mode: mode ? 'ace/mode/' + mode : null
				});
			this._editor.on('blur', this.blurHandler.bind(this));
			this._editor.on('change', this.changeHandler.bind(this));
		},
		
		render$: function() {
			return $('<div><textarea></textarea></div>');
		},
		
		/*------------------------------ State ------------------------------*/

		s_lineNumbers: function(v) {
			this._editor.renderer.setShowGutter(v);
		},

		s_mode: function(v) {
			this.rerender();
		},

		s_readonly: function(v) {
			this._editor.setReadOnly(v);
		},

		s_value: function(v) {
			this._editor.setValue(v);
		}
		
	});
	
	return fujion.widget;
});
