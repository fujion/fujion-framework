/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.canvas.d3;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Methods specifying how primitives are rendered.
 */
public enum DrawMode implements IEnumWithValue {
    
    /**
     * Draws a straight line to the next vertex, and connects the last vertex back to the first.
     */
    LINE_LOOP(2),
    /**
     * Draws a straight line to the next vertex.
     */
    LINE_STRIP(3),
    /**
     * Draws a line between a pair of vertices.
     */
    LINES(1),
    /**
     * Draws a single dot.
     */
    POINTS(0),
    /**
     * A series of connected triangles sharing the first vertex in fanlike fashion. The first three
     * vertices form the first triangle and the second triangle is formed from the next vertex, one
     * of the sides of the first triangle, and the first vertex.
     */
    TRIANGLE_FAN(6),
    /**
     * A series of connected triangles in strip fashion. The first three vertices form the first
     * triangle and the second triangle is formed from the next vertex and one of the sides of the
     * first triangle.
     */
    TRIANGLE_STRIP(5),
    /**
     * Draws a triangle for a group of three vertices.
     */
    TRIANGLES(4);
    
    private int value;
    
    DrawMode(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
