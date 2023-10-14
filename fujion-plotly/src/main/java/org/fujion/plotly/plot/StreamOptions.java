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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class StreamOptions extends Options {
    
    /**
     * The maximum number of points to keep on the plots from an incoming stream. If "maxpoints" is
     * set to 50, only the newest 50 points will be displayed on the plot.
     * <p>
     * Default: 500
     */
    @Option
    public Integer maxpoints;
    
    /**
     * The stream id number links a data trace on a plot with a stream. See
     * <a href="https://plot.ly/settings">plotly documentation</a> for more details.
     */
    @Option
    public String token;
}
