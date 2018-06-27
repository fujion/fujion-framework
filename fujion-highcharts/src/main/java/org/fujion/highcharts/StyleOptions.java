/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.OptionMap.IOptionMapConverter;

/**
 * Used to hold style options.
 */
public class StyleOptions implements IOptionMapConverter {
    
    private final OptionMap styles = new OptionMap();
    
    /**
     * Adds the requested style.
     * 
     * @param style A CSS style.
     * @param value Value to set (or null to remove).
     * @return The StyleOptions instance (for chaining).
     */
    public StyleOptions addStyle(String style, String value) {
        styles.put(style, value);
        return this;
    }
    
    /**
     * Returns the underlying map.
     */
    @Override
    public OptionMap toMap() {
        return styles;
    }
    
}
