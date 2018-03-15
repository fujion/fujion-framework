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
package org.fujion.highcharts;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for configuring accessibility for the chart. Requires the accessibility module to be
 * loaded.
 */
public class AccessibilityOptions extends Options {
    
    public static class KeyboardNavigationOptions extends Options {

        /**
         * Enable keyboard navigation for the chart. Defaults to true.
         */
        @Option
        public Boolean enabled;

        /**
         * Options for the focus border drawn around elements while navigating through them.
         */
        @Option
        public final FocusBorderOptions focusBorder = new FocusBorderOptions();

        /**
         * Set the keyboard navigation mode for the chart. Can be "normal" or "serialize". In normal
         * mode, left/right arrow keys move between points in a series, while up/down arrow keys
         * move between series. Up/down navigation acts intelligently to figure out which series
         * makes sense to move to from any given point. In "serialize" mode, points are instead
         * navigated as a single list. Left/right behaves as in "normal" mode. Up/down arrow keys
         * will behave like left/right. This is useful for unifying navigation behavior with/without
         * screen readers enabled. Defaults to normal.
         */
        @Option
        public String mode;
        
        /**
         * Skip null points when navigating through points with the keyboard. Defaults to true.
         */
        @Option
        public Boolean skipNullPoints;
    }

    public static class FocusBorderOptions extends Options {
        
        /**
         * Enable/disable focus border for chart. Defaults to true.
         */
        @Option
        public Boolean enabled;
        
        /**
         * Hide the browser's default focus indicator. Defaults to true.
         */
        @Option
        public Boolean hideBrowserFocusOutline;

        /**
         * Focus border margin around the elements. Defaults to 2.
         */
        @Option
        public Integer margin;
        
        /**
         * Border radius of the focus border. Defaults to 3.
         */
        @Option("style.borderRadius")
        public Integer style_borderRadius;

        /**
         * Color of the focus border. Defaults to #000000.
         */
        @Option("style.color")
        public String style_color;
        
        /**
         * Line width of the focus border. Defaults to 2.
         */
        @Option("style.lineWidth")
        public Integer style_lineWidth;
    }
    
    /**
     * Whether or not to add series descriptions to charts with a single series. Defaults to false.
     */
    @Option
    public Boolean describeSingleSeries;
    
    /**
     * Enable accessibility features for the chart. Defaults to true.
     */
    @Option
    public Boolean enabled;
    
    /**
     * Options for keyboard navigation.
     */
    @Option
    public final KeyboardNavigationOptions keyboardNavigatioon = new KeyboardNavigationOptions();
    
    /**
     * Function to run upon clicking the "View as Data Table" link in the screen reader region. By
     * default Highcharts will insert and set focus to a data table representation of the chart.
     * Defaults to undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String onTableAnchorClick;
    
    /**
     * Date format to use for points on date/time axes when describing them to screen reader users.
     * Defaults to the same format as in tooltip. Defaults to undefined.
     */
    @Option
    public String pointDateFormat;
    
    /**
     * Formatter function to determine the date/time format used with points on datetime axes when
     * describing them to screen reader users. Receives one argument, point, referring to the point
     * to describe. Should return a date format string compatible with dateFormat. Defaults to
     * undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String pointDateFormatter;

    /**
     * Formatter function to use instead of the default for point descriptions. Receives one
     * argument, point, referring to the point to describe. Should return a String with the
     * description of the point for a screen reader user. Defaults to undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String pointDescriptionFormatter;
    
    /**
     * When a series contains more points than this, we no longer expose information about
     * individual points to screen readers.
     */
    @Option
    public Integer pointDescriptionThreshold;
    
    /**
     * A formatter function to create the HTML contents of the hidden screen reader information
     * region. Receives one argument, chart, referring to the chart object. Should return a String
     * with the HTML content of the region. The link to view the chart as a data table will be added
     * automatically after the custom HTML content. Defaults to undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String screenReaderSectionFormatter;
    
    /**
     * Formatter function to use instead of the default for series descriptions. Receives one
     * argument, series, referring to the series to describe. Should return a String with the
     * description of the series for a screen reader user. Defaults to undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String seriesDescriptionFormatter;
}
