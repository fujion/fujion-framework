/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
import org.fujion.annotation.Option;

/**
 * Options for tree map plot.
 * <p>
 * A tree map displays hierarchical data using nested rectangles. The data can be laid out in
 * varying ways depending on options.
 */
public class PlotTreeMap extends PlotOptions {
    
    /**
     * Options for the button appearing when drilling down in a treemap.
     */
    public static class DrillUpButtonPosition extends Options {
        
        /**
         * Horizontal alignment of the button. Defaults to right.
         */
        @Option
        public AlignHorizontal align;

        /**
         * Vertical alignment of the button. Defaults to top.
         */
        @Option
        public AlignVertical verticalAlign;
        
        /**
         * Horizontal offset of the button. Defaults to -10.
         */
        @Option
        public Integer x;
        
        /**
         * Vertical offset of the button. Defaults to 10.
         */
        @Option
        public Integer y;
    }
    
    /**
     * Which algorithm is used for setting position and dimensions of the points.
     */
    public enum LayoutAlgorithm {
        SliceAndDice, Stripes, Squarified, Strip
    }
    
    /**
     * Direction the layout algorithm will start drawing.
     */
    public enum LayoutStartingDirection {
        horizontal, vertical
    }
    
    /**
     * When enabled the user can click on a point which is a parent and zoom in on its children.
     * Defaults to false.
     */
    @Option
    public Boolean allowDrillToNode;

    /**
     * Enabling this option will make the treemap alternate the drawing direction between vertical
     * and horizontal. The next levels starting direction will always be the opposite of the
     * previous. Defaults to false.
     */
    @Option
    public Boolean alternateStartingDirection;

    /**
     * The color of the border surrounding each tree map item. Defaults to #e6e6e6.
     */
    @Option
    public String borderColor;

    /**
     * Options for the button appearing when drilling down in a treemap.
     */
    @Option("drillUpButton.position")
    public final DrillUpButtonPosition drillUpButton_position = new DrillUpButtonPosition();
    
    /**
     * Whether to ignore hidden points when the layout algorithm runs. If false, hidden points will
     * leave open spaces. Defaults to true.
     */
    @Option
    public Boolean ignoreHiddenPoint;

    /**
     * This option decides if the user can interact with the parent nodes or just the leaf nodes.
     * When this option is undefined, it will be true by default. However when allowDrillToNode is
     * true, then it will be false by default. Defaults to undefined.
     */
    @Option
    public Boolean interactByLeaf;
    
    /**
     * This option decides which algorithm is used for setting position and dimensions of the
     * points. Can be one of sliceAndDice, stripes, squarified or strip. Defaults to sliceAndDice.
     */
    @Option
    public LayoutAlgorithm layoutAlgorithm;

    /**
     * Defines which direction the layout algorithm will start drawing. Defaults to vertical.
     */
    @Option
    public LayoutStartingDirection layoutStartingDirection;
    
    /**
     * Used together with the levels and allowDrillToNode options. When set to false the first level
     * visible when drilling is considered to be level one. Otherwise the level will be the same as
     * the tree structure. Defaults to true.
     */
    @Option
    public Boolean levelIsConstant;

    /**
     * The opacity of a point in treemap. When a point has children, the visibility of the children
     * is determined by the opacity. Defaults to 0.15.
     */
    @Option
    public Double opacity;
    
    /**
     * The sort index of the point inside the treemap level. Defaults to undefined.
     */
    @Option
    public Integer sortIndex;

}
