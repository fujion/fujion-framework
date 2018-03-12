'use strict';

define('fujion-canvas', ['fujion-core', 'fujion-widget'], function(fujion, wgt) { 
	
	/**
	 * Wrapper for canvas.
	 */
	fujion.widget.Canvas = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			delete this._resources;
			delete this._context;
			this._super();
		},
		
		initArrayBuffer: function(handle, type, sizeOrData) {
			var ary = [],
				data = _.isArray(sizeOrData) ? sizeOrData : null,
				size = data ? data.length : sizeOrData,
				c = this._context;
			
			switch (type) {
				case c.UNSIGNED_BYTE:
					ary = new Uint8Array(size);
					break;
					
				case c.UNSIGNED_SHORT_5_6_5:
				case c.UNSIGNED_SHORT_4_4_4_4:
				case c.UNSIGNED_SHORT_5_5_5_1:
					ary = new Uint16Array(size);
					break;
					
				case c.FLOAT:
					ary = new Float32Array(size);
					break;
			}
			
			if (data) {
				_.forEach(data, function(value, index) {
					ary[index] = data[index];
				});
			}
			
			return this.saveResource(handle, ary);
		},
		
		initResource: function(handle, factory, args) {
			args = _.isArray(args) && arguments.length === 3 ? args : [].slice.call(arguments, 2);
			args = this._resolveResources(args);
			return this.saveResource(handle, this._context[factory].apply(this._context, args));
		},
		
		initContext: function(handle, types, options) {
			this._context ? this.rerender() : null;
			var self = this,
				canvas = this.getCanvas();
			
			_.forEach(types, function(type) {
				self._type = type;
				self._context = self.saveResource(handle, canvas.getContext(type, options));
				return self._context === null;
			});
			
			return this._context ? this._type : this._type = null;
		},
		
		initPath: function(handle) {
			return this.saveResource(handle, new Path2D());
		},
		
		initPattern: function(handle, image, repetition) {
			if (fujion.widget.isWidget(image)) {
				image = image.widget$[0];
			}
			
			return this.saveResource(handle, this._context.createPattern(image, repetition));
		},
		
		/*------------------------------ Other ------------------------------*/

		destroyResource: function(handle) {
			delete this._resources[handle];
		},
		
		saveResource: function(handle, obj) {
			return this._resources[handle] = obj;
		},
		
		getCanvas: function() {
			return this.widget$.find('canvas')[0];
		},
		
		getResource: function(handle) {
			return this._resources[handle];
		},
		
		invokeResource: function(handle, fnc, args) {
			var obj = this.getResource(handle);
			args = _.isArray(args) && arguments.length === 3 ? args : [].slice.call(arguments, 2);
			return obj[fnc].apply(obj, this._resolveResources(args));
		}, 
		
		configResource: function(handle, prop, value) {
			var obj = this.getResource(handle);
			obj[prop] = this._resolveResource(value);
		},
		
		_resolveResource: function(value) {
			return value && value.__fujion_res__ ? this.getResource(value.__fujion_res__) : value;
		},
		
		_resolveResources: function(args) {
			var self = this;
			
			_.forOwn(args, function(value, index) {
				args[index] = self._resolveResource(value);
			});
			
			return args;
		},
		
		/*------------------------------ Rendering ------------------------------*/

		beforeRender: function() {
			this._super();
			delete this._context;
			this._resources = {};
		},
		
		render$: function() {
			return $('<div><canvas/></div>')
		}
	});

	return fujion.widget;
});