'use strict';

define('fujion-upload', ['fujion-core', 'fujion-widget'], function(fujion) { 
	
	/******************************************************************************************************************
	 * Base class for file uploader
	 ******************************************************************************************************************/ 
	
	fujion.widget.Upload = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/
		
		changeHandler: function(event) {
			const files = event.target.files;
			const wgt = this._requestor;
			const progress = this.getState('progress');
			const self = this;
			delete this._requestor;

			if (files) {
				_.forEach(files, file => {
					if (file.size > this.getState('maxsize')) {
						_fire(-2);
						return;
					}

					const reader = new FileReader();
					this._readers[file.name] = reader;

					reader.onload = () => {
						delete this._readers[file.name];
						const blob = reader.result;
						const size = blob.byteLength;
						_fire(size, blob);
					};

					reader.onabort = () => {
						delete this._readers[file.name];
						_fire(-1);
					};

					if (progress) {
						reader.onprogress = event => {
							if (event.lengthComputable && event.loaded !== event.total) {
								_fire(event.loaded);
							}
						}
					}
					
					reader.readAsArrayBuffer(file);
					
					function _fire(loaded, blob) {
						const state = loaded < 0 ? loaded : reader.readyState;
						const params = {
							file: file.name,
							blob: blob || null,
							state: state,
							loaded: loaded < 0 ? 0 : loaded,
							total: file.size
						};
						wgt ? wgt.trigger('upload', params) : null;
						wgt !== self ? self.trigger('upload', params) : null;
					}
				});
			}
			
			return false;
		},
		
		clickHandler: function(event, wgt) {
			this._requestor = wgt || this;
		},
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this.initState({multiple: false, accept: null, progress: false, maxsize: 1024*1024*100});
			this._readers = {};
		},
		
		/*------------------------------ Other ------------------------------*/
		
		abort: function(file) {
			const reader = this._readers[file];
			reader ? reader.abort() : null;
		},
		
		abortAll: function() {
			_.forOwn(this._readers, reader => reader.abort());
		},
		
		bind: function(wgt) {
			const wgt$ = fujion.$(wgt);
			
			if (wgt$) {
				this.unbind(wgt);
				wgt$.on('click.fujion.upload', () => this.widget$.trigger('click', wgt));
			}
		},
		
		clear: function() {
			this.abortAll();
			this.rerender();
		},
		
		unbind: function(wgt) {
			const wgt$ = fujion.$(wgt);
			wgt$ ? wgt$.off('click.fujion.upload') : null;
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			this.widget$.on('change', this.changeHandler.bind(this));
			this.widget$.on('click', this.clickHandler.bind(this));
		},
		
		render$: function() {
			return $('<input type="file">');
		},
		
		/*------------------------------ State ------------------------------*/

		s_accept: function(v) {
			this.attr('accept', v);
		},
		
		s_maxsize: _.noop,
		
		s_multiple: function(v) {
			this.attr('multiple', v);
		},
		
		s_progress: _.noop
		
	});
		
	return fujion.widget;
});
