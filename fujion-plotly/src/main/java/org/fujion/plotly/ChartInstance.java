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
package org.fujion.plotly;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.layout.LayoutOptions;
import org.fujion.plotly.plot.PlotOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for all configuration data for this chart instance.
 */
public class ChartInstance extends Options {
    
    @Option
    protected final List<PlotOptions> data = new ArrayList<>();
    
    @Option
    protected final ChartOptions config = new ChartOptions();
    
    @Option
    protected final LayoutOptions layout = new LayoutOptions();
    
}
