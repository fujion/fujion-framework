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
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A point on a two-dimensional plane.
 */
public class Point extends Options {
    
    /**
     * The x-coordinate.
     */
    @Option
    protected final double x;

    /**
     * The y-coordinate;
     */
    @Option
    protected final double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Get the x-coordinate;
     *
     * @return The x-coordinate;
     */
    public double getX() {
        return x;
    }
    
    /**
     * Get the y-coordinate;
     *
     * @return The y-coordinate;
     */
    public double getY() {
        return y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof Point) {
            Point pt = (Point) o;
            return pt.x == x && pt.y == y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", x, y);
    }
}
