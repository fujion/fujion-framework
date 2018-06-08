'use strict';

define('fujion-codemirror-xml', [
	'fujion-core', 
	'fujion-codemirror', 
	'codemirror/lib/codemirror', 
	'codemirror/mode/xml/xml',
    'codemirror/addon/fold/xml-fold',
    'codemirror/addon/edit/closetag',
    'codemirror/addon/edit/matchtags',
    'codemirror/addon/hint/xml-hint'], 
	
	function(fujion, Widget, CodeMirror) { 
	
	/**
	 * CodeMirror XML Extensions
	 */
	Widget.CodeMirrorXML = Widget.CodeMirrorBase.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/
		
		init: function() {
			this._super();
			this._options.mode = 'xml';
			_.merge(this._options.extraKeys, {
		        "'<'": this.completeAfter.bind(this),
		        "'/'": this.completeIfAfterLt.bind(this),
		        "' '": this.completeIfInTag.bind(this),
		        "'='": this.completeIfInTag.bind(this),
		        'Ctrl-Space': 'autocomplete'
			});
		},
		
		/*------------------------------ Other ------------------------------*/
		
		completeAfter: function(cm, pred) {
			var cur = cm.getCursor();
	          
			if (!pred || pred()) {
				setTimeout(function() {
					if (!cm.state.completionActive) {
						cm.showHint({completeSingle: false});
					}
				}, 100);
			}
			return CodeMirror.Pass;
		},

		completeIfAfterLt: function(cm) {
			return this.completeAfter(cm, function() {
				var cur = cm.getCursor();
				return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == '<';
			});
		},

		completeIfInTag: function(cm) {
			return this.completeAfter(cm, function() {
				var tok = cm.getTokenAt(cm.getCursor());
				
	            if (tok.type == 'string' && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) {
	            	return false;
	            }
	            
	            var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
	            return inner.tagName;
			});
		},
		
		_format: function(cm, from, to) {
			for (var i = from.line; i <= to.line; i++) {
				var r1 = {line: i, ch: 0},
					r2 = {line: i, ch: 999999},
					s = cm.doc.getRange(r1, r2).trim(),
					l = s.length;
				
				s = s.replace('>', '>\n').trim();
				l = s.length - l;
				cm.doc.replaceRange(s, r1, r2);
				to.line += l;
		    }
			
			this._super();
		}
		
	});

	return Widget;
});