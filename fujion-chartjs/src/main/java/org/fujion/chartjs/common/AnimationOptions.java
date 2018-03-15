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

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.common.StrUtil;

/**
 * Options for animations.
 */
public class AnimationOptions extends Options {
    
    public enum EasingEnum {
        EASE_IN_BACK, EASE_IN_BOUNCE, EASE_IN_CIRC, EASE_IN_CUBIC, EASE_IN_ELASTIC, EASE_IN_EXPO, EASE_IN_OUT_BACK, EASE_IN_OUT_BOUNCE, EASE_IN_OUT_CIRC, EASE_IN_OUT_CUBIC, EASE_IN_OUT_ELASTIC, EASE_IN_OUT_EXPO, EASE_IN_OUT_QUAD, EASE_IN_OUT_QUART, EASE_IN_OUT_QUINT, EASE_IN_OUT_SINE, EASE_IN_QUAD, EASE_IN_QUART, EASE_IN_QUINT, EASE_IN_SINE, EASE_OUT_BACK, EASE_OUT_BOUNCE, EASE_OUT_CIRC, EASE_OUT_CUBIC, EASE_OUT_ELASTIC, EASE_OUT_EXPO, EASE_OUT_QUAD, EASE_OUT_QUART, EASE_OUT_QUINT, EASE_OUT_SINE, LINEAR;
        
        @Override
        public String toString() {
            return StrUtil.toCamelCaseLower(name());
        }
    }

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
