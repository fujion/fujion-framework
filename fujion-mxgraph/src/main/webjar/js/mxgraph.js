'use strict';

define('fujion-mxgraph', ['fujion-core', 'fujion-widget', 'mxgraph', 'fujion-mxgraph-css'], function(fujion, wgt, mx) { 
	
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

		addCellOverlay: function(cell, overlay) {
			cell = this._getCell(cell);
			this._graph.addCellOverlay(cell, overlay);
		},
		
		autoSizeCell: function(cell, recurse) {
			cell = this._getCell(cell);
			this._graph.autoSizeCell(cell, recurse);
		},
		
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
			const node = new mx.mxCodec().encode(this._graph.getModel());
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

			const cell = this._graph.getModel().getCell(id);

			if (!cell) {
				throw new Error('No cell with an id of "' + id + '" was found.');
			}

			return cell;
		},
		
		_mxInvoke: function(self, functionName, args) {
			const tgt = self[functionName];

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
			return $('<div></div>')
		},
		
		/*------------------------------ State ------------------------------*/

		s_allowDanglingEdges: function(v) {
			this._graph.setAllowDanglingEdges(v);
		},
		
		s_content: function(v) {
			this.clear();
			const doc = mx.mxUtils.parseXml(v);
			const ele = doc.documentElement;
			const dec = new mxCodec(ele.ownerDocument);
			dec.decode(ele, this._graph.getModel());
		},
		
		s_disconnectOnMove: function(v) {
			this._graph.setDisconnectOnMove(v);
		},
		
		s_gridEnabled: function(v) {
			this._graph.setGridEnabled(v);
		},
		
		s_gridSize: function(v) {
			this._graph.setGridSize(v);
		},
		
		s_panning: function(v) {
			this._graph.setPanning(v);
		},
		
		s_portsEnabled: function(v) {
			this._graph.setPortsEnabled(v);
		},
		
		s_readonly: function(v) {
			this._graph.setEnabled(!v);
		},
		
		s_tooltips: function(v) {
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
			this._editor.setToolbarContainer(this.sub$('tbar')[0]);
			this._editor.setGraphContainer(this.sub$('cnt')[0]);
			this._editor.setStatusContainer(this.sub$('sbar')[0]);
			this._editor.postDiagram = this._saveDiagram;
			this._editor.urlPost = 'graph.xml';
		},
		
		_mxdestroy: function() {
			this._editor ? this._editor.destroy() : null;
			delete this._editor;
			delete this._graph;
		},
		
		/*------------------------------ Other ------------------------------*/

		addAction: function(action, fnc) {
			this._editor.addAction(action, fnc);
		},
		
		addToolbarItem: function(label, action, image) {
			if (label === '-') {
				this._editor.toolbar.addSeparator(image);
			} else {
				this._editor.toolbar.addItem(label, image, action);
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
			const dom = '<div>'
				+ '<div id="${id}-tbar"></div>'
				+ '<div id="${id}-cnt"></div>'
				+ '<label id="${id}-sbar"></label>'
				+ '</div>';
			return $(this.resolveEL(dom));
		},

		/*------------------------------ State ------------------------------*/

		s_status: function(v) {
			this._editor.setStatus(v);
		}
		
	});
	
	return fujion.widget;
});
