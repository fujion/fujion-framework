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
package org.fujion.ancillary;

import org.fujion.annotation.Option;

/**
 * Options for print operation.
 */
public class PrintOptions extends Options {
    
    /**
     * Adds custom HTML after the selected content. May be any valid jquery selector or HTML text.
     */
    @Option
    public String append;
    
    /**
     * Prepends a doctype to the printed document frame.
     * <p>
     * Default: "&lt;!doctype html&gt;"
     */
    @Option
    public String doctype;
    
    /**
     * Whether or not the styles from the parent document should be included.
     * <p>
     * Default: true
     */
    @Option
    public Boolean globalStyles;
    
    /**
     * Whether to print from an iframe instead of a pop-up window.
     * <p>
     * Default: true
     */
    @Option("iframe")
    public Boolean iframe$boolean;
    
    /**
     * Selector of an existing iframe to use for printing.
     */
    @Option("iframe")
    public String iframe$selector;
    
    /**
     * If true, copy user-updated form input values onto the printed markup (this is done by
     * manually iterating over each form element).
     * <p>
     * Default: true
     */
    @Option
    public Boolean manuallyCopyFormValues;
    
    /**
     * Whether or not link tags with media='print' should be included; Overridden by the
     * globalStyles option.
     * <p>
     * Default: false
     */
    @Option
    public Boolean mediaPrint;
    
    /**
     * A selector for the items that are to be excluded from printing.
     * <p>
     * Default: ".no-print"
     */
    @Option
    public String noPrintSelector;

    /**
     * Adds custom HTML before the selected content. May be any valid jquery selector or HTML text.
     */
    @Option
    public String prepend;

    /**
     * Selector for the root element to print. Defaults to the widget's anchor element.
     */
    @Option
    public String selector;

    /**
     * URL of an external stylesheet to be included.
     */
    @Option
    public String stylesheet;

    /**
     * The maximum amount of time, in milliseconds, to wait for the content, etc. to load before
     * printing the element from the new window/iframe created, as a fallback if the load event for
     * the new window/iframe has not fired yet.
     * <p>
     * Default: 750
     */
    @Option
    public Integer timeout;

    /**
     * Text to use for print title.
     * <p>
     * Default: the page title
     */
    @Option
    public String title;
    
}
