/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.axis.CartesianAxisOptions;
import org.fujion.chartjs.axis.RadialAxisOptions;

/**
 * Options for scales.
 */
public class ScaleOptions extends Options {
    
    @Option
    public final List<RadialAxisOptions> rAxes = new ArrayList<>();
    
    @Option
    public final List<CartesianAxisOptions> xAxes = new ArrayList<>();
    
    @Option
    public final List<CartesianAxisOptions> yAxes = new ArrayList<>();
}
