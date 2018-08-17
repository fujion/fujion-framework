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
        return new MXVertex(this, ++nextId, value, x, y, width, height, style, relative);
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
        MXVertex vertex = createVertex(value, x, y, width, height, style, relative);
        vertex.insert();
        return vertex;
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
        return new MXEdge(this, ++nextId, value, style, source, target);
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
        MXEdge edge = createEdge(value, source, target, style);
        edge.insert();
        return edge;
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

}
