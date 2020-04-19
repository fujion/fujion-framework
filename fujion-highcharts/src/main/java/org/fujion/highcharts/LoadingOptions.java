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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * The loading options control the appearance of the loading screen that covers the plot area on
 * chart operations. This screen only appears after an explicit call to chart.showLoading(). It is a
 * utility for developers to communicate to the end user that something is going on, for example
 * while retrieving new data via an XHR connection. The "Loading..." text itself is not part of this
 * configuration object, but part of the lang object.
 */
public class LoadingOptions extends Options {
    
    /**
     * The duration in milliseconds of the fade out effect. Defaults to 100.
     */
    @Option
    public Integer hideDuration;
    
    /**
     * CSS styles for the loading label span. Defaults to:
     *
     * <pre>
     *     fontWeight: 'bold'
     *     position: 'relative'
     *     top: '45%'
     * </pre>
     */
    @Option
    public final StyleOptions labelStyle = new StyleOptions();
    
    /**
     * The duration in milliseconds of the fade in effect. Defaults to 100.
     */
    @Option
    public Integer showDuration;
    
    /**
     * CSS styles for the loading screen that covers the plot area. Defaults to:
     *
     * <pre>
     *     position: 'absolute',
     *     backgroundColor: 'ffffff',
     *     opacity: 0.5,
     *     textAlign: 'center'
     * </pre>
     */
    @Option
    public final StyleOptions style = new StyleOptions();
}
