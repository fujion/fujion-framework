'use strict';

define('fujion-angular-widget', ['fujion-core', 'fujion-widget', '@angular/core', 'fujion-angular-bootstrap', 'core-js/client/shim', '@angular/common', '@angular/platform-browser', '@angular/platform-browser-dynamic', 'zone.js', 'rxjs'],
	function(fujion, wgt, core, bootstrap) { 

	fujion.debug ? null : core.enableProdMode();
	
	return { 
		
	AngularWidget: fujion.widget.UIWidget.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		destroy: function() {
			this._destroy();
			this._super();
		},
		
		init: function() {
			this._super();
			this._ngInvoke = [];
		},
		
		_destroy: function () {
			this._appContext ? this._appContext.destroy() : null;
			this._appContext = null;
		},
		
		/*------------------------------ Other ------------------------------*/
		
		isLoaded: function() {
			return this._appContext && this._appContext.isLoaded();
		},
		
		ngInvoke: function(functionName, args) {
			if (this.isLoaded()) {
				return this._appContext.invoke(functionName, args);
			} else {
				this._ngInvoke.push({functionName: functionName, args: args});
			}
		},
		
		ngFlush: function() {
			while (this._ngInvoke.length) {
				var invk = this._ngInvoke.shift();
				this.ngInvoke(invk.functionName, invk.args);
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			
			var src = this.getState('src'),
				id = "#" + this.id,
				self = this;
			
			if (src) {
				var ngFlush = this.ngFlush.bind(this);
				
				System.import(src).then(function(module) {
					self._appContext = new bootstrap.AppContext(module, id);
					self._appContext.bootstrap().then(ngFlush);
				});
			}
		},
		
		beforeRender: function() {
			this._destroy();
			this._super();
		},
		
		render$: function() {
			return $('<div />');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_src: function(v) {
			this.rerender();
		}
	
	})};
});
