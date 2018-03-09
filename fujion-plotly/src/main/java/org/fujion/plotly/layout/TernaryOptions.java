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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * TODO: Layout options for ternary plots.
 */
public class TernaryOptions extends Options {
    
    /**
     * Options for a axis.
     */
    public final ABCAxisOptions aaxis = new ABCAxisOptions();
    
    /**
     * Options for b axis.
     */
    public final ABCAxisOptions baxis = new ABCAxisOptions();
    
    /**
     * Options for c axis.
     */
    public final ABCAxisOptions caxis = new ABCAxisOptions();
    
    public String bgcolor;
    
    public int[] domain_x;
    
    public int[] domain_y;
    
    public Object sum;
}
