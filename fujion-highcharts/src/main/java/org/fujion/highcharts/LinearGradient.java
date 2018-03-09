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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;

/**
 * A linear gradient that lets us create gradients in plot bands instead of just using solid colors.
 * I only put the properties I needed into this class. The highcharts' javascript class has more
 * properties than this so feel free to add them if you need to. According to:
 * http://www.w3.org/TR/SVG/pservers.html#LinearGradients linear gradients are defined as a line in
 * a coordinate system that has its origin in the top left (0, 0) and with (1, 1) being in the
 * bottom right. In other words, it starts in the top left and as you go to the right, x increases
 * and as you go down, y increases.
 */
public class LinearGradient extends Options {
    
    /**
     * The x1 coordinate of the line that defines the gradient.
     */
    public Double x1;
    
    /**
     * The y1 coordinate of the line that defines the gradient.
     */
    public Double y1;
    
    /**
     * The x2 coordinate of the line that defines the gradient.
     */
    public Double x2;
    
    /**
     * The y2 coordinate of the line that defines the gradient.
     */
    public Double y2;
    
}
