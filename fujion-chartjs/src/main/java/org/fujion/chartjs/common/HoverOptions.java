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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;

/**
 * Hover options.
 */
public class HoverOptions extends Options {

    /**
     * Determines which directions are used in calculating distances.
     */
    public enum AxisEnum {
        X, XY, Y;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Duration in milliseconds it takes to animate hover style changes.
     * <p>
     * Default: 400
     */
    public Integer animationDuration;

    /**
     * Determines which directions are used in calculating distances.
     * <p>
     * Default: X for index mode and XY in dataset and nearest modes.
     */
    public AxisEnum axis;

    /**
     * If true, the hover mode only applies when the mouse position intersects an item on the chart.
     * <p>
     * Default: true
     */
    public Boolean intersect;
    
    /**
     * Determines which elements appear in the tooltip.
     * <p>
     * Default: NEAREST
     */
    public HoverModeEnum mode;

}
