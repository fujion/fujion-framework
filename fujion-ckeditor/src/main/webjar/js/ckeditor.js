'use strict';

window.CKEDITOR_BASEPATH = fujion.baseurl + 'webjars/webjar-ckeditor-full/';

define('fujion-ckeditor', [
	'fujion-core', 
	'fujion-widget',
	'jshashes',
	'ckeditor-full',
	'fujion-ckeditor-css'], function(fujion, Widget, Hashes) { 
	
	/******************************************************************************************************************
	 * A CKEditor widget
	 ******************************************************************************************************************/ 
	Widget.CKEditor = Widget.UIWidget.extend({
	
		MD5: new Hashes.MD5,
		
		/*------------------------------ Events ------------------------------*/
		
		blurHandler: function() {
			if (this._changed && this.changed()) {
				this._changed = false;
				this.trigger('change', {value: this._editor.getData()});
			}
		},
		
		changeHandler: function() {
			this._changed = true;
			this.getState('synced') ? this.blurHandler() : null;
		},
		
		instanceReadyHandler: function() {
			this._ready = true;
			this.syncState('readonly', 'value');
			this.getState('sizable') ? null : this.resizeHandler();
			
			if (this._focus) {
				delete this._focus;
				var editor = this._editor;
				setTimeout(function() {
					editor.focus();
				}, 0);
			}
		},
		
		resizeHandler: function() {
			this._ready ? this._editor.resize(this.widget$.width(), this.widget$.height()) : null;
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		destroy: function() {
			this.destroyEditor();
			this._super();
		},
		
		destroyEditor: function() {
			if (this._editor) {
				this._editor.destroy();
				delete this._editor;
			}
		},
		
		init: function() {
			this._super();
			this.initState({sizable: true});
			this.forwardToServer('change');
		},
		
		/*------------------------------ Other ------------------------------*/
		
		changed: function() {
			var hash = this.MD5.hex(this._editor.getData());
			
			if (!this._hash || this._hash !== hash) {
				this._hash = hash;
				this._changed = true;
			} else {
				this._changed = false;
			}
			
			return this._changed;
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this._editor.once('instanceReady', this.instanceReadyHandler.bind(this));
			this._editor.on('blur', this.blurHandler.bind(this));
			this._editor.on('change', this.changeHandler.bind(this));
			var iframe = this.widget$.find('iframe.fujion-resizer')[0];
			
			if (iframe) {
				$(iframe.contentWindow).on('resize', this.resizeHandler.bind(this));
			}
		},
		
		beforeRender: function() {
			this._super();
			delete this._ready;
			this.destroyEditor();
			this._editor = CKEDITOR.replace(this.widget$.find('textarea')[0], {
				baseHref: fujion.baseurl,
				resize_enabled: this.getState('sizable')
			});
		},
		
		render$: function() {
			var dom = this.getState('sizable') ? '<div><textarea/></div>' : '<div><textarea/><iframe class="fujion-resizer"/></div>';
			return $(dom);
		},
		
		/*------------------------------ State ------------------------------*/
		
		focus: function(v) {
			!this._ready ? this._focus = v : v ? this._editor.focus() : this._super();
		},
		
		readonly: function(v) {
			this._ready ? this._editor.setReadOnly(v) : null;
		},
		
		sizable: function(v) {
			this.rerender();
		},
		
		synced: function(v) {			
		},
		
		value: function(v) {
			if (this._ready) {
				delete this._hash;
				this._editor.setData(v);
				this.changed();
			}
		}
	});

	
	return fujion.widget;
});


