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

/**
 * Represents a vertex within a graph.
 */
public class MXVertex extends MXCell {

    private MXGeometry geometry;

    protected MXVertex(MXGraph graph, int id, String value, int x, int y, int width, int height, String style,
                       boolean relative) {
        super(graph, id, value, style);
        this.geometry = new MXGeometry(x, y, width, height, relative);
    }
    
    @Override
    protected void doInsert() {
        getGraph().invoke("insertVertex", null, null, this, getValue(), geometry.x, geometry.y, geometry.width,
            geometry.height, getStyle(), geometry.relative);
    }
    
    /**
     * Returns the geometry for the vertex.
     * 
     * @return The geometry for the vertex.
     */
    public MXGeometry getGeometry() {
        return geometry;
    }
    
    /**
     * Sets the geometry for the vertex.
     * 
     * @param geometry The geometry for the vertex.
     */
    public void setGeometry(MXGeometry geometry) {
        this.geometry = geometry;
        updateCellState("setGeometry", geometry);
    }
}
