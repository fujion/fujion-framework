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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for 3D presentation.
 */
public class ThreeDOptions extends Options {

    /**
     * One of the two rotation angles for the chart. Defaults to 0.
     */
    @Option
    public Double alpha;

    /**
     * Set it to "auto" to automatically move the labels to the best edge. Defaults to null.
     */
    @Option
    public String axisLabelPosition;
    
    /**
     * One of the two rotation angles for the chart. Defaults to 0.
     */
    @Option
    public Double beta;

    /**
     * The total depth of the chart. Defaults to 100.
     */
    @Option
    public Double depth;

    /**
     * Whether to render the chart using the 3D functionality. Defaults to false.
     */
    @Option
    public Boolean enabled;

    /**
     * Whether the 3d box should automatically adjust to the chart plot area. Defaults to true.
     */
    @Option
    public Boolean fitToPlot;

    /**
     * Defines the back panel of the frame around 3D charts.
     */
    @Option("frame.back")
    public final ThreeDFrameOptions frame_back = new ThreeDFrameOptions();
    
    /**
     * The bottom of the frame around a 3D chart.
     */
    @Option("frame.bottom")
    public final ThreeDFrameOptions frame_bottom = new ThreeDFrameOptions();
    
    /**
     * The front of the frame around a 3D chart.
     */
    @Option("frame.front")
    public final ThreeDFrameOptions frame_front = new ThreeDFrameOptions();

    /**
     * The left side of the frame around a 3D chart.
     */
    @Option("frame.left")
    public final ThreeDFrameOptions frame_left = new ThreeDFrameOptions();

    /**
     * The right side of the frame around a 3D chart.
     */
    @Option("frame.right")
    public final ThreeDFrameOptions frame_right = new ThreeDFrameOptions();

    /**
     * The side for the frame around a 3D chart.
     */
    @Option("frame.side")
    public final ThreeDFrameOptions frame_side = new ThreeDFrameOptions();
    
    /**
     * The top of the frame around a 3D chart.
     */
    @Option("frame.top")
    public final ThreeDFrameOptions frame_top = new ThreeDFrameOptions();

    /**
     * Whether the frames are visible. Defaults to default.
     */
    @Option("frame.visible")
    public Boolean frame_visible;

    /**
     * Defines the distance the viewer is standing in front of the chart, this setting is important
     * to calculate the perspective effect in column and scatter charts. It is not used for 3D pie
     * charts. Defaults to 100.
     */
    @Option
    public Double viewDistance;
    
}
