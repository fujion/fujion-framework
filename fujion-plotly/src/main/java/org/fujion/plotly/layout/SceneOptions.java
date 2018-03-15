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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for scenes.
 */
public class SceneOptions extends Options {
    
    /**
     * Aspect mode settings.
     */
    public enum AspectModeEnum {
        /**
         * The scene's axes are drawn using the results of "data" except when one axis is more than
         * four times the size of the two others, where in that case the results of "cube" are used.
         */
        AUTO,
        /**
         * The scene's axes are drawn as a cube, regardless of the axes' ranges.
         */
        CUBE,
        /**
         * The scene's axes are drawn in proportion with the axes' ranges.
         */
        DATA,
        /**
         * The scene's axes are drawn in proportion with the input of "aspectratio" (the default
         * behavior if "aspectratio" is provided).
         */
        MANUAL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Drag mode settings.
     */
    public enum DragModeEnum {
        FALSE, ORBIT, PAN, TURNTABLE, ZOOM;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Hover mode settings.
     */
    public enum HoverModeEnum {
        CLOSEST, FALSE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Annotation options.
     */
    @Option
    public final AnnotationOptions annotations = new AnnotationOptions();
    
    /**
     * The aspect mode.
     * <p>
     * Default: AUTO
     */
    @Option
    public AspectModeEnum aspectmode;
    
    /**
     * The aspect ratio in the x dimension.
     * <p>
     * Constraints: &ge;0
     */
    @Option
    public Double aspectratio_x;
    
    /**
     * The aspect ratio in the y dimension.
     * <p>
     * Constraints: &ge;0
     */
    @Option
    public Double aspectratio_y;
    
    /**
     * The aspect ratio in the z dimension.
     * <p>
     * Constraints: &ge;0
     */
    @Option
    public Double aspectratio_z;
    
    /**
     * The background color.
     * <p>
     * Default: "rgba(0,0,0,0)"
     */
    @Option
    public String bgcolor;
    
    /**
     * Options for camera.
     */
    @Option
    public final CameraOptions camera = new CameraOptions();
    
    /**
     * The horizontal domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_x;

    /**
     * The vertical domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_y;

    /**
     * Drag mode setting;
     * <p>
     * Default: TURNTABLE
     */
    @Option
    public DragModeEnum dragmode;

    /**
     * Hover mode setting.
     * <p>
     * Default: CLOSEST
     */
    @Option
    public HoverModeEnum hovermode;

    /**
     * Options for x axis.
     */
    @Option
    public final XYZAxisOptions xaxis = new XYZAxisOptions();

    /**
     * Options for y axis.
     */
    @Option
    public final XYZAxisOptions yaxis = new XYZAxisOptions();
    
    /**
     * Options for z axis.
     */
    @Option
    public final XYZAxisOptions zaxis = new XYZAxisOptions();
    
}
