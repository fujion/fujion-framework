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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Layout options for images.
 */
public class ImageOptions extends Options {

    /**
     * Specifies which dimension of the image to constrain.
     */
    public enum SizingEnum {
        CONTAIN, FILL, STRETCH;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Specifies whether images are drawn below or above traces. When "xref" and "yref" are both set
     * to "paper", image is drawn below the entire plot area.
     * <p>
     * Default: ABOVE
     */
    public LayerEnum layer;
    
    /**
     * The opacity of the image.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     */
    public Double opacity;

    /**
     * The image container size horizontally. The image will be sized based on the "position" value.
     * When "xref" is set to "paper", units are sized relative to the plot width.
     * <p>
     * Default: 0
     */
    public Integer sizex;
    
    /**
     * The image container size vertically. The image will be sized based on the "position" value.
     * When "yref" is set to "paper", units are sized relative to the plot height.
     * <p>
     * Default: 0
     */
    public Integer sizey;

    /**
     * Specifies which dimension of the image to constrain.
     */
    public SizingEnum sizing;
    
    /**
     * Specifies the URL of the image to be used. The URL must be accessible from the domain where
     * the plot code is run, and can be either relative or absolute.
     */
    public String source;
    
    /**
     * Determines whether or not this image is visible.
     * <p>
     * Default: true
     */
    public Boolean visible;
    
    /**
     * The image's x position. When "xref" is set to "paper", units are sized relative to the plot
     * height. See "xref" for more info.
     * <p>
     * Default: 0
     */
    public Double x;

    /**
     * The anchor for the x position.
     * <p>
     * Default: LEFT
     */
    public HorizontalAlignEnum xanchor;

    /**
     * The images's x coordinate axis. If set to a x axis id (e.g. "x" or "x2"), the "x" position
     * refers to an x data coordinate If set to "paper", the "x" position refers to the distance
     * from the left of plot in normalized coordinates where "0" ("1") corresponds to the left
     * (right).
     * <p>
     * Constraints: "paper" | "/^x([2-9]|[1-9][0-9]+)?$/"
     * <p>
     * Default: "paper"
     */
    public String xref;

    /**
     * The image's y position. When "yref" is set to "paper", units are sized relative to the plot
     * width. See "yref" for more info.
     * <p>
     * Default: 0
     */
    public Double y;

    /**
     * The anchor for the y position.
     * <p>
     * Default: TOP
     */
    public VerticalAlignEnum yanchor;

    /**
     * The images's y coordinate axis. If set to a y axis id (e.g. "y" or "y2"), the "y" position
     * refers to a y data coordinate. If set to "paper", the "y" position refers to the distance
     * from the bottom of the plot in normalized coordinates where "0" ("1") corresponds to the
     * bottom (top).
     * <p>
     * Constraints: "paper" | "/^x([2-9]|[1-9][0-9]+)?$/"
     * <p>
     * Default: "paper"
     */
    public String yref;

}
