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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

public class TooltipCallbackOptions extends Options {
    
    /**
     * Returns text to render after the body section
     */
    @JavaScript
    public String afterBody;
    
    /**
     * Text to render after the footer section.
     */
    @JavaScript
    public String afterFooter;
    
    /**
     * Returns text to render after an individual label.
     */
    @JavaScript
    public String afterLabel;
    
    /**
     * Returns text to render after the title.
     */
    @JavaScript
    public String afterTitle;
    
    /**
     * Returns text to render before the body section.
     */
    @JavaScript
    public String beforeBody;
    
    /**
     * Returns text to render before the footer section.
     */
    @JavaScript
    public String beforeFooter;
    
    /**
     * Returns text to render before an individual label. This will be called for each item in the
     * tooltip.
     */
    @JavaScript
    public String beforeLabel;
    
    /**
     * Returns the text to render before the title.
     */
    @JavaScript
    public String beforeTitle;
    
    /**
     * Returns text to render as the footer of the tooltip.
     */
    @JavaScript
    public String footer;
    
    /**
     * Returns text to render for an individual item in the tooltip.
     */
    @JavaScript
    public String label;
    
    /**
     * Returns the colors to render for the tooltip item.
     */
    @JavaScript
    public String labelColor;
    
    /**
     * Returns the colors for the text of the label for the tooltip item.
     */
    @JavaScript
    public String labelTextColor;
    
    /**
     * Returns text to render as the title of the tooltip.
     */
    @JavaScript
    public String title;
    
}
