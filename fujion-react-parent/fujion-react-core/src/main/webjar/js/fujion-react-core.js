'use strict';

define('fujion-react-core', ['fujion-core', 'fujion-widget', 'react', 'react-dom'],
	(fujion, wgt, React, ReactDOM) => {

	return {
		
	ReactWidget: fujion.widget.UIWidget.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		destroy: function() {
			this._destroy();
			this._super();
		},
		
		init: function() {
			this._super();
			this._rxInvoke = [];
		},
		
		_destroy: function () {
			//this.widget$ ? ReactDOM.unmountComponentAtNode(this.widget$[0]) : null;
			this._rxRoot ? this._rxRoot.unmount() : null;
			this._rxComponent = null;
			this._rxRoot = null;
		},
		
		/*------------------------------ Other ------------------------------*/
		
		isLoaded: function() {
			return !!this._rxComponent;
		},
		
		loaded: function(component) {
			this._rxComponent = component;
			
			while (this._rxInvoke.length) {
				const invk = this._rxInvoke.shift();
				this.rxInvoke(invk.functionName, invk.args);
			}
		},
		
		rxInvoke: function(functionName, args) {
			if (this.isLoaded()) {
				return this._rxComponent[functionName].apply(this._rxComponent, args);
			} else {
				this._rxInvoke.push({functionName: functionName, args: args});
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/
		
		afterRender: function() {
			this._super();
			const src = this.getState('src');
			
			if (src) {
				fujion.import(src, module => {
					const self = this;
					const wrapper = React.createElement(ComponentWrapper);
					this._rxRoot = ReactDOM.createRoot(this.widget$[0]);
					this._rxRoot.render(wrapper);

					function ComponentWrapper() {
						React.useEffect(() => self.loaded(self));
						return React.createElement(module.ReactComponent);
					}
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
