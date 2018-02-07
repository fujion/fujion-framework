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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

/**
 * Options for the exporting module.
 */
public class ExportingOptions extends Options {

    /**
     * Options for the export button.
     */
    public final ButtonOptions buttons_exportButton = new ButtonOptions();

    /**
     * Options for the print button.
     */
    public final ButtonOptions buttons_printButton = new ButtonOptions();

    /**
     * Whether to enable the exporting module. Defaults to true.
     */
    public Boolean enabled;

    /**
     * Function to call if the offline-exporting module fails to export a chart on the client side,
     * and fallbackToExportServer is disabled. If left undefined, an exception is thrown instead.
     * Defaults to undefined.
     */
    @JavaScript
    public String error;

    /**
     * Whether or not to fall back to the export server if the offline-exporting module is unable to
     * export the chart on the client side. Defaults to true.
     */
    public Boolean fallbackToExportServer;
    
    /**
     * The filename, without extension, to use for the exported chart. Defaults to "chart".
     */
    public String filename;

    /**
     * When printing the chart from the menu item in the burger menu, if the on-screen chart exceeds
     * this width, it is resized. After printing or cancelled, it is restored. The default width
     * makes the chart fit into typical paper format. Note that this does not affect the chart when
     * printing the web page as a whole. Defaults to 780.
     */
    public Integer printMaxWidth;
    
    /**
     * Defines the scale or zoom factor for the exported image compared to the on-screen display.
     * While for instance a 600px wide chart may look good on a website, it will look bad in print.
     * The default scale of 2 makes this chart export to a 1200px PNG or JPG. Defaults to 2.
     */
    public Integer scale;

    /**
     * Export-data module required. Show a HTML table below the chart with the chart's current data.
     * Defaults to false.
     */
    public Boolean showTable;
    
    /**
     * The height of the original chart when exported, unless an explicit chart.height is set. The
     * height exported raster image is then multiplied by scale. Defaults to undefined.
     */
    public Integer sourceHeight;
    
    /**
     * The width of the original chart when exported, unless an explicit chart.width is set. The
     * width exported raster image is then multiplied by scale. Defaults to undefined.
     */
    public Integer sourceWidth;

    /**
     * Export-data module required. Caption for the data table. Same as chart title by default. Set
     * to empty string to disable. Defaults to undefined.
     */
    public String tableCaption;

    /**
     * Default MIME type for exporting if chart.exportChart() is called without specifying a type
     * option. Possible values are image/png, image/jpeg, application/pdf and image/svg+xml.
     * Defaults to "image/png".
     */
    public ExportType type;

    /**
     * The URL for the server module converting the SVG string to an image format. By default this
     * points to Highchart's free web service. Defaults to https://export.highcharts.com/.
     */
    public String url;

    /**
     * Export-data module required. Use multi level headers in data table. If
     * csv.columnHeaderFormatter is defined, it has to return objects in order for multi level
     * headers to work. Defaults to true.
     */
    public Boolean useMultiLevelHeaders;
    
    /**
     * Export-data module required. If using multi level table headers, use rowspans for headers
     * that have only one level. Defaults to true.
     */
    public Boolean useRowspanHeaders;

    /**
     * The pixel width of charts exported to PNG or JPG. The default pixel width is a function of
     * the chart.width or exporting.sourceWidth and the exporting.scale.
     */
    public Integer width;

}
