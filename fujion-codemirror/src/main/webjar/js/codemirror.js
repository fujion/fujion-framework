'use strict';

define('fujion-codemirror', [
	'fujion-core', 
	'fujion-widget', 
	'codemirror/lib/codemirror', 
	'fujion-codemirror-css', 
	'codemirror-css',
	'codemirror/addon/hint/show-hint.css',
	'codemirror/addon/display/placeholder',
    'codemirror/addon/hint/show-hint',
	'codemirror/addon/edit/closebrackets',
	'codemirror/addon/edit/matchbrackets',
	'codemirror/addon/fold/brace-fold'], 
	
	function(fujion, Widget, CodeMirror) { 
	
	/**
	 * Base Wrapper for CodeMirror Editor
	 */
	Widget.CodeMirrorBase = Widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
			this.initState({'options': {}});
			this._options = {};
			this._options.extraKeys = {'Alt-F': this.format.bind(this)};
			this.forwardToServer('change');
		},
		
		destroy: function() {
			this._destroy();
			this._super();
		},
		
		_destroy: function() {
			this.widget$ ? this.widget$.empty() : null;
			delete this._cm;
		},
		
		/*------------------------------ Other ------------------------------*/
	
		clear: function() {
		    this._cm.setValue('');
		    this._cm.focus();
		},
		
		format: function() {
			const cm = this._cm;
			const sel = cm.doc.somethingSelected();
			const from = sel ? cm.getCursor('from') : {line: 0, ch: 0};
			const to = sel ? cm.getCursor('to') : {line: cm.lastLine(), ch: 99999999};
		    
    		cm.operation(this._format.bind(this, cm, from, to));
    		from.ch = 0;
    		cm.setSelection(from, from);
    		cm.focus();
		},
		
		setOption: function(name, value) {
			_.isNil(value) ? delete this._options[name] : this._options[name] = value;
			this._cm ? this._cm.setOption(name, value) : null;
		},
		
		_format: function(cm, from, to) {
			for (let i = from.line; i <= to.line; i++) {
				cm.indentLine(i);
		    }
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			setTimeout(() => this._cm ? this._cm.refresh() : null, 1);
		},
		
		beforeRender: function() {
			const options = _.merge({}, this._options, this.getState('options'));
			this._super();
			this._destroy();
			this._cm = CodeMirror(this.widget$[0], options);
			
			this._cm.on('changes', () => {
				const v = this._cm.getValue();
				
				if (this.setState('value', v)) {
					this.trigger('change', {value: v});
				}
			});
			
			this.widget$.find('.CodeMirror textarea').on('change', false);
		},
		
		render$: function() {
			return $('<div></div>');
		},
		
		/*------------------------------ State ------------------------------*/
		
		s_focus: function(v) {
			v ? this._cm.focus() : null;    
		},
		
		s_lineNumbers: function(v) {
			this.setOption('lineNumbers', v);
		},
		
		s_options: function(v) {
			this.rerender();
		},
		
		s_placeholder: function(v) {
			this.setOption('placeholder', v);
		},
		
		s_readonly: function(v) {
			this.setOption('readOnly', v);
		},
		
		s_value: function(v) {
			this._cm.setValue(v || '');
		}
	});
	
	return Widget;
});
