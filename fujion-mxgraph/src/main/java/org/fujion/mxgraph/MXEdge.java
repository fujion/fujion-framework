/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.w3c.dom.Element;

/**
 * Represents an edge within a graph.
 */
public class MXEdge extends MXCell<MXEdge> {

    private final MXVertex source;
    
    private final MXVertex target;
    
    /**
     * Creates an edge within the specified graph.
     *
     * @param graph The containing graph.
     * @param value The label value.
     * @param style The cell style(s).
     * @param source The source vertex.
     * @param target The target vertex.
     */
    protected MXEdge(MXGraph graph, String value, String style, MXVertex source, MXVertex target) {
        super(graph, value, style);
        this.source = source;
        this.target = target;
    }

    protected MXEdge(MXGraph graph, Element node) {
        super(graph, node);
        this.source = graph.getVertex(node.getAttribute("source"));
        this.target = graph.getVertex(node.getAttribute("target"));
    }

    @Override
    protected void doInsert() {
        getGraph().invoke("insertEdge", null, null, this, getValue(), source, target, getStyle());
    }

    /**
     * Returns the source vertex.
     *
     * @return The source vertex.
     */
    public MXVertex getSource() {
        return source;
    }

    /**
     * Returns the target vertex.
     *
     * @return The target vertex.
     */
    public MXVertex getTarget() {
        return target;
    }
}
