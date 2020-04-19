/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.fujion.annotation.Option;

/**
 * Options for word cloud plot.
 * <p>
 * A word cloud is a visualization of a set of words, where the size and placement of a word is
 * determined by how it is weighted.
 */
public class PlotWordCloud extends PlotOptions {

    /**
     * 3D columns only. The width of the colored edges. Defaults to 1.
     */
    @Option
    public Integer edgeWidth;

    /**
     * This option decides which algorithm is used for placement, and rotation of a word. The choice
     * of algorithm is therefore a crucial part of the resulting layout of the word cloud. It is
     * possible for users to add their own custom placement strategies for use in word cloud.
     * Defaults to center.
     */
    @Option
    public String placementStrategy;

    /**
     * The smallest degree of rotation for a word. Defaults to 0.
     */
    @Option("rotation.from")
    public Integer rotation_from;

    /**
     * The number of possible orientations for a word, within the range of rotation.from and
     * rotation.to. Defaults to 2.
     */
    @Option("rotation.orientations")
    public Integer rotation_orientations;

    /**
     * The largest degree of rotation for a word. Defaults to 90.
     */
    @Option("rotation.to")
    public Integer rotation_to;
    
    /**
     * Spiral used for placing a word after the initial position experienced a collision with either
     * another word or the borders. It is possible for users to add their own custom spiraling
     * algorithms for use in word cloud. Defaults to rectangular.
     */
    @Option
    public String spiral;
}
