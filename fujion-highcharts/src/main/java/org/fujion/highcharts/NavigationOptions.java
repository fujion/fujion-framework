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
 * A collection of options for buttons and menus appearing in the exporting module.
 */
public class NavigationOptions extends Options {

    /**
     * A collection of options for buttons appearing in the exporting module.
     */
    @Option
    public final ButtonOptions buttonOptions = new ButtonOptions();

    /**
     * CSS styles for the hover state of the individual items within the popup menu appearing by
     * default when the export icon is clicked. The menu items are rendered in HTML. Defaults to
     *
     * <pre>
     *     background: '#4572A5'
     *     color: '#FFFFFF'
     * </pre>
     */
    @Option
    public final StyleOptions menuItemHoverStyle = new StyleOptions();

    /**
     * CSS styles for the individual items within the popup menu appearing by default when the
     * export icon is clicked. The menu items are rendered in HTML. Defaults to
     *
     * <pre>
     *     padding: '0 5px'
     *     background: none
     *     color: '#303030'
     * </pre>
     */
    @Option
    public final StyleOptions menuItemStyle = new StyleOptions();

    /**
     * CSS styles for the popup menu appearing by default when the export icon is clicked. This menu
     * is rendered in HTML. Defaults to
     *
     * <pre>
     *     border: '1px solid #A0A0A0'
     *     background: '#FFFFFF'
     * </pre>
     */
    @Option
    public final StyleOptions menuStyle = new StyleOptions();
}
