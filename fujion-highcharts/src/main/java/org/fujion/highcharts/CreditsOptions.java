/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
 * Highchart by default puts a credits label in the lower right corner of the chart. This can be
 * changed using these options.
 */
public class CreditsOptions extends Options {

    /**
     * Whether to show the credits text. Defaults to false.
     */
    @Option
    public Boolean enabled = false;

    /**
     * The URL for the credits label. Defaults to "http://www.highcharts.com".
     */
    @Option
    public String href;

    /**
     * Position configuration for the credits label.
     *
     * @see PositionOptions
     */
    @Option
    public final PositionOptions position = new PositionOptions();

    /**
     * CSS styles for the credits label. Defaults to:
     *
     * <pre>
     *     cursor: 'pointer'
     *     color: '#909090'
     *     fontSize: '10px'
     * </pre>
     */
    @Option
    public final StyleOptions style = new StyleOptions();

    /**
     * The text for the credits label. Defaults to "Highcharts.com".
     */
    @Option
    public String text;
}
