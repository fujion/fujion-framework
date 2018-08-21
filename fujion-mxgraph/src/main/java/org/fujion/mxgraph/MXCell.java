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

import org.fujion.client.IClientTransform;
import org.w3c.dom.Element;

/**
 * Abstract base class for edges and vertices.
 *
 * @param <T> Type of subclass.
 */
public abstract class MXCell<T extends MXCell<T>> implements IClientTransform {
    
    private String value;

    private String style;

    private boolean visible = true;

    private boolean collapsed;

    private Object data;

    private boolean inserted;

    private final String id;
    
    private final MXGraph graph;
    
    protected MXCell(MXGraph graph, String value, String style) {
        this.graph = graph;
        this.id = graph.nextId();
        this.value = value;
        this.style = style;
    }
    
    protected MXCell(MXGraph graph, Element node) {
        this.graph = graph;
        this.id = node.getAttribute("id");
        this.value = node.getAttribute("value");
        this.style = node.getAttribute("style");
    }
    
    /**
     * Insert cell into graph.
     */
    protected abstract void doInsert();

    @SuppressWarnings("unchecked")
    public T insert() {
        if (!inserted) {
            doInsert();
            inserted = true;
        }
        
        return (T) this;
    }

    /**
     * Transform to use unique id for client invocations.
     */
    @Override
    public Object transformForClient() {
        return id;
    }
    
    /**
     * Returns the containing graph.
     *
     * @return The containing graph.
     */
    public MXGraph getGraph() {
        return graph;
    }

    /**
     * Returns the id that is unique across all cells within the containing graph.
     *
     * @return The unique id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the label value for the cell.
     *
     * @return The label value for the cell.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Sets the label value for the cell.
     *
     * @param value The label value for the cell.
     */
    public void setValue(String value) {
        updateCellState("setValue", this.value = value);
    }
    
    /**
     * Returns the style settings for the cell.
     *
     * @return The style settings for the cell.
     */
    public String getStyle() {
        return style;
    }
    
    /**
     * Sets the style settings for the cell.
     *
     * @param style The style settings for the cell.
     */
    public void setStyle(String style) {
        updateCellState("setStyle", this.style = style);
    }

    /**
     * Returns the visibility state for the cell.
     *
     * @return The visibility state for the cell.
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Sets the visibility state for the cell.
     *
     * @param visible The visibility state for the cell.
     */
    public void setVisible(boolean visible) {
        updateCellState("setVisible", this.visible = visible);
    }

    /**
     * Returns the collapsed state for the cell.
     *
     * @return The collapsed state for the cell.
     */
    public boolean isCollapsed() {
        return collapsed;
    }
    
    /**
     * Sets the collapsed state for the cell.
     *
     * @param collapsed The collapsed state for the cell.
     */
    public void setCollapsed(boolean collapsed) {
        updateCellState("setCollapsed", this.collapsed = collapsed);
    }

    /**
     * Updates the cell's state on the client.
     *
     * @param method The method on the graph model to set the state.
     * @param value The new state value to set.
     */
    protected void updateCellState(String method, Object value) {
        this.getGraph().invoke("setCellState", this, method, value);
    }
    
    /**
     * Returns an arbitrary object associated with this cell.
     *
     * @return An arbitrary object associated with this cell.
     */
    public Object getData() {
        return data;
    }
    
    /**
     * Sets an arbitrary object associated with this cell.
     *
     * @param data An arbitrary object associated with this cell.
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Adds an overlay to the cell.
     *
     * @param overlay Overlay to add.
     */
    public void addCellOverlay(MXCellOverlay overlay) {
        this.getGraph().invoke("addCellOverlay", this, overlay);
    }

    /**
     * Resizes this cell and all descendants to just fit around the its label and/or children
     */
    public void autoSize() {
        autoSize(true);
    }
    
    /**
     * Resizes this cell to just fit around the its label and/or children
     * 
     * @param recurse If true, all descendants should be auto-sized.
     */
    public void autoSize(boolean recurse) {
        this.getGraph().invoke("autoSizeCell", this, recurse);
    }
    
}
