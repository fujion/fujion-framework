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
 * Represents an edge within a graph.
 */
public class MXEdge extends MXCell<MXEdge> {

    private final MXVertex source;
    
    private final MXVertex target;
    
    /**
     * Creates an edge within the specified graph.
     *
     * @param graph The containing graph.
     * @param id The unique cell id.
     * @param value The label value.
     * @param style The cell style(s).
     * @param source The source vertex.
     * @param target The target vertex.
     */
    protected MXEdge(MXGraph graph, int id, String value, String style, MXVertex source, MXVertex target) {
        super(graph, id, value, style);
        this.source = source;
        this.target = target;
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
