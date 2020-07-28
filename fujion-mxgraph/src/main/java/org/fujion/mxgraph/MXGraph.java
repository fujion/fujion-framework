/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.mxgraph;

import org.fujion.ancillary.IResponseCallback;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.MiscUtil;
import org.fujion.common.XMLUtil;
import org.fujion.component.BaseUIComponent;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Fujion wrapper for mxGraph component.
 */
@Component(
        tag = "mxgraph",
        widgetModule = "fujion-mxgraph",
        widgetClass = "MXGraph",
        parentTag = "*",
        content = ContentHandling.AS_ATTRIBUTE,
        description = "Fujion wrapper for mxGraph component.")
public class MXGraph extends BaseUIComponent {
    
    private int nextId;
    
    private boolean readonly;
    
    private boolean prettyXML;
    
    private boolean tooltips = true;

    private boolean panning = true;

    private boolean allowDanglingEdges;

    private boolean disconnectOnMove;

    private int gridSize = 10;
    
    private boolean gridEnabled = true;
    
    private boolean portsEnabled = true;
    
    private final Map<String, MXEdge> edges = new HashMap<>();

    private final Map<String, MXVertex> vertices = new HashMap<>();

    protected String nextId() {
        return "_fujion_" + ++nextId;
    }

    public Map<String, MXVertex> getVertices() {
        return Collections.unmodifiableMap(vertices);
    }

    public Map<String, MXEdge> getEdges() {
        return Collections.unmodifiableMap(edges);
    }

    public MXVertex getVertex(String id) {
        return vertices.get(id);
    }

    public MXEdge getEdge(String id) {
        return edges.get(id);
    }

    public void beginUpdate() {
        invoke("beginUpdate");
    }
    
    public void endUpdate() {
        invoke("endUpdate");
    }
    
    /**
     * Creates a new vertex using the given coordinates. When adding new vertices from a mouse
     * event, one should take into account the offset of the graph container and the scale and
     * translation of the view in order to find the correct unscaled, untranslated coordinates.
     *
     * @param value The user object.
     * @param x The x coordinate of the vertex.
     * @param y The y coordinate of the vertex.
     * @param width The width of the vertex.
     * @param height The height of the vertex.
     * @param style The vertex's style(s).
     * @param relative Specifies if the geometry is relative.
     * @return The newly created vertex.
     */
    public MXVertex createVertex(String value, int x, int y, int width, int height, String style, boolean relative) {
        return addVertex(new MXVertex(this, value, x, y, width, height, style, relative));
    }
    
    private MXVertex addVertex(MXVertex vertex) {
        String id = vertex.getId();
        
        if (id != null) {
            vertices.put(id, vertex);
        }
        
        return vertex;
    }

    /**
     * Inserts a new vertex using the given coordinates. When adding new vertices from a mouse
     * event, one should take into account the offset of the graph container and the scale and
     * translation of the view in order to find the correct unscaled, untranslated coordinates.
     *
     * @param value The user object.
     * @param x The x coordinate of the vertex.
     * @param y The y coordinate of the vertex.
     * @param width The width of the vertex.
     * @param height The height of the vertex.
     * @param style The vertex's style(s).
     * @param relative Specifies if the geometry is relative.
     * @return The newly created vertex.
     */
    public MXVertex insertVertex(String value, int x, int y, int width, int height, String style, boolean relative) {
        return createVertex(value, x, y, width, height, style, relative).insert();
    }
    
    /**
     * Creates a new edge using the given source and target as the terminals of the new edge.
     *
     * @param value The user object.
     * @param source The source of the edge.
     * @param target The target of the edge.
     * @param style The edge's style(s).
     * @return The newly create edge.
     */
    public MXEdge createEdge(String value, MXVertex source, MXVertex target, String style) {
        return addEdge(new MXEdge(this, value, style, source, target));
    }

    private MXEdge addEdge(MXEdge edge) {
        String id = edge.getId();
        
        if (id != null) {
            edges.put(id, edge);
        }
        
        return edge;
    }

    /**
     * Inserts a new edge using the given source and target as the terminals of the new edge.
     *
     * @param value The user object.
     * @param source The source of the edge.
     * @param target The target of the edge.
     * @param style The edge's style(s).
     * @return The newly create edge.
     */
    public MXEdge insertEdge(String value, MXVertex source, MXVertex target, String style) {
        return createEdge(value, source, target, style).insert();
    }

    /**
     * Directly invoke a method on the graph object.
     *
     * @param functionName Name of function to invoke.
     * @param args Arguments to pass.
     */
    public void mxInvoke(String functionName, Object... args) {
        invoke("mxInvoke", functionName, args);
    }
    
    /**
     * Directly invoke a method on the graph object, returning the result.
     *
     * @param cb Callback to receive result.
     * @param functionName Name of function to invoke.
     * @param args Arguments to pass.
     */
    public void mxInvoke(IResponseCallback<?> cb, String functionName, Object... args) {
        invoke("mxInvoke", cb, functionName, args);
    }
    
    /**
     * Clears the graph.
     */
    public void clear() {
        _clear();
        invoke("clear");
    }

    /**
     * Clear cached cells.
     */
    private void _clear() {
        vertices.clear();
        edges.clear();
    }
    
    /**
     * Synchronizes the XML content and cell caches with the client. This process is asynchronous.
     */
    public void refresh() {
        refresh(null);
    }

    /**
     * Synchronizes the XML content and cell caches with the client. This process is asynchronous.
     *
     * @param cb Optional callback to invoke after refresh completes.
     */
    public void refresh(Runnable cb) {
        invoke("getGraphXML", (String xml) -> {
            _refresh(xml);

            if (cb != null) {
                cb.run();
            }
        }, prettyXML);
        
    }
    
    private void _refresh(String content) {
        try {
            build(XMLUtil.parseXMLFromString(content));
            setContentSynced(false);
            setContent(content);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        } finally {
            setContentSynced(true);
        }
    }
    
    /**
     * Rebuild the cell caches from the graph document.
     *
     * @param doc The graph document.
     */
    private void build(Document doc) {
        _clear();
        Element root = doc.getDocumentElement();
        root = MXUtil.TAG_ROOT.equals(root.getTagName()) ? root
                : (Element) root.getElementsByTagName(MXUtil.TAG_ROOT).item(0);
        Assert.notNull(root, "Cannot find root element");
        build(root);
    }
    
    private void build(Element ele) {
        NodeList children = ele.getChildNodes();
        int count = children.getLength();

        for (int i = 0; i < count; i++) {
            Element child = (Element) children.item(i);

            if (MXUtil.TAG_CELL.equals(child.getTagName())) {
                if (MXUtil.get(child, "vertex", Integer.class, 0) != 0) {
                    addVertex(new MXVertex(this, child));
                } else if (MXUtil.get(child, "edge", Integer.class, 0) != 0) {
                    addEdge(new MXEdge(this, child));
                }
            }

            build(child);
        }
    }
    
    /**
     * Returns the graph as an XML-formatted string.
     */
    @Override
    public String getContent() {
        return super.getContent();
    }
    
    /**
     * Creates a new graph from an XML string.
     *
     * @param content The XML string.
     */
    @Override
    protected void setContent(String content) {
        _clear();
        super.setContent(content);
    }
    
    /**
     * Returns true if the graph is read-only.
     *
     * @return True if the graph is read-only.
     */
    @PropertyGetter(value = "readonly", bindable = false, description = "True if the graph is read-only.")
    public boolean isReadonly() {
        return readonly;
    }
    
    /**
     * Sets the read-only state of the graph.
     *
     * @param readonly If true, the graph may not be changed by the user.
     */
    @PropertySetter(value = "readonly", bindable = false, defaultValue = "false", description = "True if the graph is read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }
    
    /**
     * Returns true if tooltips are enabled.
     *
     * @return True if tooltips are enabled.
     */
    @PropertyGetter(value = "tooltips", bindable = false, description = "True if tooltips are enabled.")
    public boolean getTooltips() {
        return tooltips;
    }
    
    /**
     * Set to true to enable tooltips.
     *
     * @param tooltips If true, tooltips are enabled.
     */
    @PropertySetter(value = "tooltips", bindable = false, defaultValue = "true", description = "True if tooltips are enabled.")
    public void setTooltips(boolean tooltips) {
        propertyChange("tooltips", this.tooltips, this.tooltips = tooltips, true);
    }
    
    /**
     * Returns true if panning is enabled.
     *
     * @return True if panning is enabled.
     */
    @PropertyGetter(value = "panning", bindable = false, description = "True if panning is enabled.")
    public boolean getPanning() {
        return panning;
    }
    
    /**
     * Set to true to enable panning.
     *
     * @param panning If true, panning is enabled.
     */
    @PropertySetter(value = "panning", bindable = false, defaultValue = "true", description = "True if panning is enabled.")
    public void setPanning(boolean panning) {
        propertyChange("panning", this.panning, this.panning = panning, true);
    }
    
    /**
     * Returns true if dangling edges are allowed.
     *
     * @return True if dangling edges are allowed.
     */
    @PropertyGetter(value = "allowDanglingEdges", bindable = false, description = "True if dangling edges are allowed.")
    public boolean getAllowDanglingEdges() {
        return allowDanglingEdges;
    }
    
    /**
     * Set to true to enable dangling edges.
     *
     * @param allowDanglingEdges If true, dangling edges are enabled.
     */
    @PropertySetter(value = "allowDanglingEdges", bindable = false, defaultValue = "false", description = "True if dangling edges are allowed.")
    public void setAllowDanglingEdges(boolean allowDanglingEdges) {
        propertyChange("allowDanglingEdges", this.allowDanglingEdges, this.allowDanglingEdges = allowDanglingEdges, true);
    }
    
    /**
     * Returns true if disconnect on move is allowed.
     *
     * @return True if disconnect on move is allowed.
     */
    @PropertyGetter(value = "disconnectOnMove", bindable = false, description = "True if disconnect on move is allowed.")
    public boolean getDisconnectOnMove() {
        return disconnectOnMove;
    }
    
    /**
     * Set to true to enable disconnect on move.
     *
     * @param disconnectOnMove If true, disconnect on move is enabled.
     */
    @PropertySetter(value = "disconnectOnMove", bindable = false, defaultValue = "false", description = "True if disconnect on move is allowed.")
    public void setDisconnectOnMove(boolean disconnectOnMove) {
        propertyChange("disconnectOnMove", this.disconnectOnMove, this.disconnectOnMove = disconnectOnMove, true);
    }

    /**
     * Returns true if XML to be returned in pretty format.
     *
     * @return True if XML to be returned in pretty format.
     */
    @PropertyGetter(value = "prettyXML", bindable = false, description = "True if XML to be returned in pretty format.")
    public boolean isPrettyXML() {
        return prettyXML;
    }

    /**
     * Set to true if XML to be returned in pretty format.
     *
     * @param prettyXML If true, XML to be returned in pretty format.
     */
    @PropertySetter(value = "prettyXML", bindable = false, defaultValue = "false", description = "True if XML to be returned in pretty format.")
    public void setPrettyXML(boolean prettyXML) {
        propertyChange("prettyXML", this.prettyXML, this.prettyXML = prettyXML, false);
    }
    
    /**
     * Returns the grid size in pixels.
     *
     * @return The grid size in pixels.
     */
    @PropertyGetter(value = "gridSize", bindable = false, description = "The grid size in pixels.")
    public int getGridSize() {
        return gridSize;
    }
    
    /**
     * Sets the grid size in pixels.
     *
     * @param gridSize The grid size in pixels.
     */
    @PropertySetter(value = "gridSize", bindable = false, defaultValue = "10", description = "The grid size in pixels.")
    public void setGridSize(int gridSize) {
        propertyChange("gridSize", this.gridSize, this.gridSize = gridSize, true);
    }
    
    /**
     * Returns true if the grid is enabled.
     *
     * @return True if the grid is enabled.
     */
    @PropertyGetter(value = "gridEnabled", bindable = false, description = "If true, the grid is enabled.")
    public boolean getGridEnabled() {
        return gridEnabled;
    }
    
    /**
     * Set to true to enable the grid.
     *
     * @param gridEnabled True to enable the grid.
     */
    @PropertySetter(value = "gridEnabled", bindable = false, defaultValue = "true", description = "If true, the grid is enabled.")
    public void setGridEnabled(boolean gridEnabled) {
        propertyChange("gridEnabled", this.gridEnabled, this.gridEnabled = gridEnabled, true);
    }
    
    /**
     * Returns true if ports are enabled.
     * 
     * @return True if ports are enabled.
     */
    @PropertyGetter(value = "portsEnabled", bindable = false, description = "If true, ports are enabled.")
    public boolean isPortsEnabled() {
        return portsEnabled;
    }
    
    /**
     * Set to true to enable ports.
     * 
     * @param portsEnabled True to enable ports.
     */
    @PropertySetter(value = "portsEnabled", bindable = false, defaultValue = "true", description = "If true, ports are enabled.")
    public void setPortsEnabled(boolean portsEnabled) {
        propertyChange("portsEnabled", this.portsEnabled, this.portsEnabled = portsEnabled, true);
    }
    
}
