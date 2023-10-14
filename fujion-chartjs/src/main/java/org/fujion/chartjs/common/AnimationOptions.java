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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.EasingEnum;

/**
 * Options for animations.
 */
public class AnimationOptions extends Options {

    /**
     * The number of milliseconds an animation takes.
     * <p>
     * Default: 1000
     */
    @Option
    public Integer duration;

    /**
     * Easing function to use.
     * <p>
     * Default: EASE_OUT_QUART
     */
    @Option
    public EasingEnum easing;

    /**
     * Delay before starting the animations.
     */
    @Option
    public Integer delay;

    /**
     * If set to true, the animations loop endlessly.
     */
    @Option
    public Boolean loop;

    /**
     * Callback called at the end of an animation.
     */
    @Option(convertTo = JavaScript.class)
    public String onComplete;

    /**
     * Callback called on each step of an animation.
     */
    @Option(convertTo = JavaScript.class)
    public String onProgress;
}
