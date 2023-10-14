/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.mxgraph.MXConstants.MXAlign;

/**
 *
 * An image and/or tool tip overlay for a cell.
 *
 */
public class MXCellOverlay extends Options {
    
    /**
     * Holds the image to be used as the icon.
     */
    @Option
    public final MXImage image;
    
    /**
     * Holds the optional string to be used as the tooltip.
     */
    @Option
    public final String tooltip;
    
    /**
     * Holds the horizontal alignment for the overlay. For edges, the overlay always appears in the
     * center of the edge.
     * <p>
     * Default: MXAlign.RIGHT
     */
    @Option
    public final MXAlign align;
    
    /**
     * Holds the vertical alignment for the overlay. For edges, the overlay always appears in the
     * center of the edge.
     * <p>
     * Default: MXAlign.BOTTOM
     */
    @Option
    public final MXAlign verticalAlign;
    
    /**
     * The offset will be scaled according to the current scale.
     */
    public final MXPoint offset;

    /**
     * Holds the cursor for the overlay.
     * <p>
     * Default: "help"
     */
    @Option
    public final String cursor;
    
    /**
     * Defines the overlapping for the overlay, that is, the proportional distance from the origin
     * to the point defined by the alignment.
     * <p>
     * Default: 0.5
     */
    @Option
    public final Float defaultOverlap;

    public MXCellOverlay(MXImage image, String tooltip) {
        this(image, tooltip, null, null);
    }
    
    public MXCellOverlay(MXImage image, String tooltip, MXAlign align, MXAlign verticalAlign) {
        this(image, tooltip, align, verticalAlign, null, null, null);
    }
    
    public MXCellOverlay(MXImage image, String tooltip, MXAlign align, MXAlign verticalAlign, MXPoint offset,
                         String cursor, Float defaultOverlap) {
        this.image = image;
        this.tooltip = tooltip;
        this.align = align;
        this.verticalAlign = verticalAlign;
        this.offset = offset;
        this.cursor = cursor;
        this.defaultOverlap = defaultOverlap;
    }
}
