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
package org.fujion.chartjs.plot;

import org.fujion.ancillary.Options;

/**
 * Options common to all plots.
 */
public abstract class PlotOptions extends Options {
    
    /**
     * The fill color under the line.
     */
    public String backgroundColor;

    /**
     * The color of the line.
     */
    public String borderColor;
    
    /**
     * The width of the line in pixels.
     */
    public Integer borderWidth;
    
    /**
     * The plot type.
     */
    protected PlotType type;

}