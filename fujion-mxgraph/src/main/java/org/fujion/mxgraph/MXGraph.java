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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.fujion.ancillary.IResponseCallback;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

/**
 * Fujion wrapper for mxGraph component.
 */
@Component(tag = "mxgraph", widgetModule = "fujion-mxgraph", widgetClass = "MXGraph", parentTag = "*", description = "Fujion wrapper for mxGraph component.")
public class MXGraph extends BaseUIComponent {
    
    private int nextId;
    
    private boolean readonly;
    
    private boolean tooltips = true;

    private boolean panning = true;

    private boolean allowDanglingEdges;

    private boolean disconnectOnMove;

    private final int gridSize = 10;
    
    private final boolean gridEnabled = true;
    
    private final boolean portsEnabled = true;
    
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
        MXVertex vertex = new MXVertex(this, value, x, y, width, height, style, relative);
        vertices.put(vertex.getId(), vertex);
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
        MXEdge edge = new MXEdge(this, value, style, source, target);
        edges.put(edge.getId(), edge);
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
        vertices.clear();
        edges.clear();
        invoke("clear");
    }

    /**
     * Returns the current graph as an XML-formatted string.
     *
     * @param pretty If true, return in pretty format.
     * @param cb The callback to receive the result.
     */
    public void getXML(boolean pretty, IResponseCallback<String> cb) {
        invoke("getGraphXML", cb, pretty);
    }
    
    /**
     * Creates a new graph from an XML string.
     *
     * @param xml The XML string.
     */
    public void setXML(String xml) {
        invoke("setGraphXML", xml);
    }

    /**
     * Returns true if the graph is read-only.
     *
     * @return True if the graph is read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if the graph is read-only.")
    public boolean isReadonly() {
        return readonly;
    }
    
    /**
     * Sets the read-only state of the graph.
     *
     * @param readonly If true, the graph may not be changed by the user.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if the graph is read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }
    
    /**
     * Returns true if tooltips are enabled.
     *
     * @return True if tooltips are enabled.
     */
    @PropertyGetter(value = "tooltips", description = "True if tooltips are enabled.")
    public boolean getTooltips() {
        return tooltips;
    }
    
    /**
     * Set to true to enable tooltips.
     *
     * @param tooltips If true, tooltips are enabled.
     */
    @PropertySetter(value = "tooltips", defaultValue = "true", description = "True if tooltips are enabled.")
    public void setTooltips(boolean tooltips) {
        propertyChange("tooltips", this.tooltips, this.tooltips = tooltips, true);
    }
    
    /**
     * Returns true if panning is enabled.
     *
     * @return True if panning is enabled.
     */
    @PropertyGetter(value = "panning", description = "True if panning is enabled.")
    public boolean getPanning() {
        return panning;
    }
    
    /**
     * Set to true to enable panning.
     *
     * @param panning If true, panning is enabled.
     */
    @PropertySetter(value = "panning", defaultValue = "true", description = "True if panning is enabled.")
    public void setPanning(boolean panning) {
        propertyChange("panning", this.panning, this.panning = panning, true);
    }
    
    /**
     * Returns true if dangling edges are allowed.
     *
     * @return True if dangling edges are allowed.
     */
    @PropertyGetter(value = "allowDanglingEdges", description = "True if dangling edges are allowed.")
    public boolean getAllowDanglingEdges() {
        return allowDanglingEdges;
    }
    
    /**
     * Set to true to enable dangling edges.
     *
     * @param allowDanglingEdges If true, dangling edges are enabled.
     */
    @PropertySetter(value = "allowDanglingEdges", defaultValue = "false", description = "True if dangling edges are allowed.")
    public void setAllowDanglingEdges(boolean allowDanglingEdges) {
        this.allowDanglingEdges = allowDanglingEdges;
    }
    
    /**
     * Returns true if disconnect on move is allowed.
     *
     * @return True if disconnect on move is allowed.
     */
    @PropertyGetter(value = "disconnectOnMove", description = "True if disconnect on move is allowed.")
    public boolean getDisconnectOnMove() {
        return disconnectOnMove;
    }
    
    /**
     * Set to true to enable disconnect on move.
     *
     * @param disconnectOnMove If true, disconnect on move is enabled.
     */
    @PropertySetter(value = "disconnectOnMove", defaultValue = "false", description = "True if disconnect on move is allowed.")
    public void setDisconnectOnMove(boolean disconnectOnMove) {
        this.disconnectOnMove = disconnectOnMove;
    }
    
}
