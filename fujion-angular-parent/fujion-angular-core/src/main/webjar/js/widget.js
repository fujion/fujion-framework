'use strict';

define('fujion-angular-widget', ['fujion-core', 'fujion-widget', '@angular/core', 'fujion-angular-bootstrap', 'core-js-shim', '@angular/common', '@angular/platform-browser', '@angular/platform-browser-dynamic', 'zone.js', 'rxjs'],
	(fujion, wgt, core, bootstrap) => {

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
				const invk = this._ngInvoke.shift();
				this.ngInvoke(invk.functionName, invk.args);
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			const src = this.getState('src');
			const id = "#" + this.id;

			if (src) {
				fujion.import(src, module => {
					this._appContext = new bootstrap.AppContext(module, id);
					this._appContext.bootstrap().then(() => this.ngFlush());
				});
			}
		},
		
		beforeRender: function() {
			this._destroy();
			this._super();
		},
		
		render$: function() {
			return $('<div></div>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_src: function(v) {
			this.rerender();
		}
	
	})};
});
