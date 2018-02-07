/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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

/**
 * Options for vector plot.
 * <p>
 * A vector plot is a type of Cartesian chart where each point has an X and Y position, a length and
 * a direction. Vectors are drawn as arrows.
 */
public class PlotVector extends PlotOptions {
    
    /**
     * What part of the vector it should be rotated around. Can be one of start, center and end.
     * When start, the vectors will start from the given [x, y] position, and when end the vectors
     * will end in the [x, y] position. Defaults to center.
     */
    public AlignGeneral rotation;

    /**
     * Maximum length of the arrows in the vector plot. The individual arrow length is computed
     * between 0 and this value. Defaults to 20.
     */
    public Integer vectorLength;
}
