'use strict';

define('fujion-upload', ['fujion-core', 'fujion-widget'], function(fujion) { 
	
	/******************************************************************************************************************
	 * Base class for file uploader
	 ******************************************************************************************************************/ 
	
	fujion.widget.Upload = fujion.widget.UIWidget.extend({

		/*------------------------------ Events ------------------------------*/
		
		changeHandler: function(event) {
			var files = event.target.files,
				wgt = this._requestor,
				progress = this.getState('progress'),
				self = this;
			
			delete this._requestor;
			
			if (files) {
				_.forEach(files, function(file) {
					if (file.size > self.getState('maxsize')) {
						_fire(-2);
						return;
					}
					
					var reader = new FileReader();
					self._readers[file.name] = reader;
					
					reader.onload = function(event) {
						delete self._readers[file.name];
						var blob = reader.result,
							size = blob.byteLength;
						_fire(size, blob);
					};
					
					reader.onabort = function(event) {
						delete self._readers[file.name];
						_fire(-1);
					};
					
					if (progress) {
						reader.onprogress = function(event) {
							if (event.lengthComputable && event.loaded !== event.total) {
								_fire(event.loaded);
							}
						}
					}
					
					reader.readAsArrayBuffer(file);
					
					function _fire(loaded, blob) {
						var state = loaded < 0 ? loaded : reader.readyState,
							params = {file: file.name, blob: blob || null, state: state, loaded: loaded < 0 ? 0 : loaded, total: file.size};
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
			var reader = this._readers[file];
			reader ? reader.abort() : null;
		},
		
		abortAll: function() {
			_.forOwn(this._readers, function(reader) {
				reader.abort();
			});
		},
		
		bind: function(wgt) {
			var self = this,
				wgt$ = fujion.$(wgt);
			
			if (wgt$) {
				this.unbind(wgt);
				
				wgt$.on('click.fujion.upload', function() {
					self.widget$.trigger('click', wgt);
				});
			}
		},
		
		clear: function() {
			this.abortAll();
			this.rerender();
		},
		
		unbind: function(wgt) {
			var wgt$ = fujion.$(wgt);
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