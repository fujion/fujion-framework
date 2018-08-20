'use strict';

define('fujion-mxgraph', ['fujion-core', 'fujion-widget', 'mxgraph'], function(fujion, wgt, mx) { 
	
	/**
	 * Wrapper for mxGraph
	 */
	fujion.widget.MXGraph = fujion.widget.UIWidget.extend({
	
		/*------------------------------ Lifecycle ------------------------------*/

		destroy: function() {
			this._super();
			this._mxdestroy();
		},
		
		init: function() {
			this._super();
		},
		
		_mxinit: function() {
			this._graph = new mx.mxGraph(this.widget$[0]);
		},
		
		_mxdestroy: function() {
			this._graph ? this._graph.destroy() : null;
			delete this._graph;
		},
		
		/*------------------------------ Other ------------------------------*/

		beginUpdate: function() {
			this._graph.getModel().beginUpdate();
		},
		
		clear: function() {
			this._graph.getModel().clear();
		},
		
		endUpdate: function() {
			this._graph.getModel().endUpdate();
		},
		
		getGraphXML: function(pretty) {
			var node = new mx.mxCodec().encode(this._graph.getModel());
			return pretty ? mx.mxUtils.getPrettyXml(node) : mx.mxUtils.getXml(node, '&#xa;');
		},
		
		insertEdge: function(parent, id, value, source, target, style) {
			parent = this._getCell(parent);
			source = this._getCell(source);
			target = this._getCell(target);
			this._graph.insertEdge(parent, id, value, source, target, style);
		},
		
		insertVertex: function(parent, id, value, x, y, width, height, style, relative) {
			parent = this._getCell(parent);
			this._graph.insertVertex(parent, id, value, x, y, width, height, style, relative);
		},
		
		mxInvoke: function(functionName, args) {
			return this._mxInvoke(this._graph, functionName, args);
		},
		
		setCellState: function(cell, method, value) {
			cell = this._getCell(cell);
			this._graph.getModel()[method](cell, value);
		},
		
		_getCell: function(id) {
			if (_.isNil(id)) {
				return null;
			}
			
			var cell = this._graph.getModel().getCell(id);
			
			if (!cell) {
				throw new Error('No cell with an id of "' + id + '" was found.');
			}
			
			return cell;
		},
		
		_mxInvoke: function(self, functionName, args) {
			var tgt = self[functionName];
			
			if (_.isUndefined(tgt)) {
				throw new Error('Unknown mxInvoke target: ' + functionName);
			} else if (_.isFunction(tgt)) {
				return tgt.apply(self, args);
			} else {
				return tgt;
			}
		},
		
		/*------------------------------ Rendering ------------------------------*/

		afterRender: function() {
			this._super();
		},
		
		beforeRender: function() {
			this._super();
			this._mxinit();
		},
		
		render$: function() {
			return $('<div/>')
		},
		
		/*------------------------------ State ------------------------------*/

		allowDanglingEdges: function(v) {
			this._graph.setAllowDanglingEdges(v);
		},
		
		content: function(v) {
			this.clear();
			
			var doc = mx.mxUtils.parseXml(v),
				ele = doc.documentElement,
				dec = new mxCodec(ele.ownerDocument);
			
			dec.decode(ele, this._graph.getModel());
		},
		
		disconnectOnMove: function(v) {
			this._graph.setDisconnectOnMove(v);
		},
		
		panning: function(v) {
			this._graph.setPanning(v);
		},
		
		readonly: function(v) {
			this._graph.setEnabled(!v);
		},
		
		tooltips: function(v) {
			this._graph.setTooltips(v);
		}
	});

	/**
	 * Wrapper for mxEditor
	 */
	fujion.widget.MXEditor = fujion.widget.MXGraph.extend({
		
		/*------------------------------ Lifecycle ------------------------------*/

		_mxinit: function() {
			this._editor = new mx.mxEditor();
			this._graph = this._editor.graph;
			this._toolbar = new mx.mxDefaultToolbar(this.sub$('tbar')[0], this._editor);
			this._editor.setGraphContainer(this.sub$('cnt')[0]);
			this._editor.postDiagram = this._saveDiagram;
			this._editor.urlPost = 'graph.xml';
		},
		
		_mxdestroy: function() {
			this._editor ? this._editor.destroy() : null;
			delete this._editor;
			delete this._graph;
		},
		
		/*------------------------------ Other ------------------------------*/

		addToolbarItem: function(label, action, image) {
			if (label === '-') {
				this._toolbar.addSeparator(image);
			} else {
				this._toolbar.addItem(label, image, action);
			}
		},
		
		execute: function(action, cell) {
			cell = this._getCell(cell);
			cell ? this._graph.setSelectionCells([cell]) : null;
			this._editor.execute(action, cell);
		},
		
		mxInvoke: function(functionName, args) {
			if (this._editor[functionName]) {
				return this._mxInvoke(this._editor, functionName, args);
			} else {
				return this._super();
			}
		},
		
		resetHistory: function() {
			this._editor.resetHistory();
		},
		
		_saveDiagram: function(file, data) {
			fujion.saveToFile(data, 'text/xml', file);
		},

		/*------------------------------ Rendering ------------------------------*/

		render$: function() {
			var dom = '<div><div id="${id}-tbar"/><div id="${id}-cnt"/></div>';
			return $(this.resolveEL(dom));
		}

		
	});
	
	return fujion.widget;
});