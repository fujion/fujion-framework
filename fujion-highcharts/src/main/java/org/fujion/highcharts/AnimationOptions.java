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

import org.fujion.ancillary.Options;

/**
 * Options for animations.
 * <p>
 * Enable or disable the initial animation when a series is displayed. The animation can also be set
 * as a configuration object. Please note that this option only applies to the initial animation of
 * the series itself. For other animations, see chart.animation and the animation parameter under
 * the API methods.
 */
public class AnimationOptions extends Options {

    /**
     * The duration of the animation in milliseconds.
     */
    public Integer duration;

    /**
     * A string reference to an easing function set on the Math object.
     */
    public String easing;
}
