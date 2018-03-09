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

import org.fujion.ancillary.Options;

/**
 * These are the language options supported by Highcharts.
 */
public class LanguageOptions extends Options {
    
    /**
     * The default decimal point used in the Highcharts.numberFormat method unless otherwise
     * specified in the function arguments. Defaults to ".".
     */
    public String decimalPoint;
    
    /**
     * Exporting module only. The text for the JPEG download menu item. Defaults to
     * "Download JPEG image".
     */
    public String downloadJPEG;
    
    /**
     * Exporting module only. The text for the PDF download menu item. Defaults to
     * "Download PDF document".
     */
    public String downloadPDF;
    
    /**
     * Exporting module only. The text for the PNG download menu item. Defaults to
     * "Download PNG image".
     */
    public String downloadPNG;
    
    /**
     * Exporting module only. The text for the SVG download menu item. Defaults to
     * "Download SVG vector image".
     */
    public String downloadSVG;
    
    /**
     * Exporting module only. The tooltip text for the export button. Defaults to
     * "Export to raster or vector image".
     */
    public String exportButtonTitle;
    
    /**
     * The loading text that appears when the chart is set into the loading state following a call
     * to chart.showLoading. Defaults to "Loading...".
     */
    public String loading;
    
    /**
     * An array containing the months names. Corresponds to the %B format in
     * Highcharts.dateFormat(). Defaults to ['January' 'February', 'March', 'April', 'May', 'June',
     * 'July', 'August', 'September', 'October', 'November', 'December'].
     */
    public String[] months;
    
    /**
     * Metric prefixes used to shorten high numbers in axis labels. Defaults to ['k', 'M', 'G', 'T',
     * 'P', 'E'].
     */
    public String[] numericSymbols;
    
    /**
     * Exporting module only. The tooltip text for the print button. Defaults to "Print the chart".
     */
    public String printButtonTitle;
    
    /**
     * The text for the label appearing when a chart is zoomed. Defaults to Reset zoom.
     */
    public String resetZoom;
    
    /**
     * The tooltip title for the label appearing when a chart is zoomed. Defaults to Reset zoom
     * level 1:1.
     */
    public String resetZoomTitle;
    
    /**
     * An array containing the months names in abbreviated form. Corresponds to the %b format in
     * Highcharts.dateFormat(). Defaults to ['Jan' 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
     * 'Sep', 'Oct', 'Nov', 'Dec'].
     */
    public String[] shortMonths;
    
    /**
     * The default thousands separator used in the Highcharts.numberFormat method unless otherwise
     * specified in the function arguments. Defaults to ",".
     */
    public String thousandsSep;
    
    /**
     * An array containing the weekday names. Defaults to ['Sunday', 'Monday', 'Tuesday',
     * 'Wednesday', 'Thursday', 'Friday', 'Saturday']. Defaults to ['Sunday' 'Monday', 'Tuesday',
     * 'Wednesday', 'Thursday', 'Friday', 'Saturday'].
     */
    public String[] weekdays;
    
}
