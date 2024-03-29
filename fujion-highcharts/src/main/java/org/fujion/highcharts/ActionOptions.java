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
package org.fujion.highcharts;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Actions that may be associated with a menu item, for example.
 */
public class ActionOptions extends Options {
    
    /**
     * Label for action (e.g., menu item label).
     */
    @Option
    public String text;
    
    /**
     * If internationalization is required, the key to a language string.
     */
    @Option
    public String textKey;
    
    /**
     * Callback function to handle click event.
     */
    @Option(convertTo = JavaScript.class)
    public String onclick;
}
