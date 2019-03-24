/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
package org.fujion.plotly;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Chart-wide settings.
 */
public class ChartOptions extends Options {

    public enum DoubleClickInteractionEnum {
        AUTOSIZE, FALSE, RESET, RESET_AUTOSIZE;
        
        @Override
        public String toString() {
            return name().toLowerCase().replace("_", "+");
        }
    }

    public enum ModeBarDisplayEnum {
        FALSE, HOVER, TRUE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Do autosize once regardless of layout.autosize (use default width or height values
     * otherwise).
     * <p>
     * Default: false
     */
    @Option
    public Boolean autosizable;
    
    /**
     * Add the plotly logo on the end of the mode bar.
     * <p>
     * Default: true
     */
    @Option
    public Boolean displaylogo;

    /**
     * Display the mode bar.
     * <p>
     * Default: HOVER
     */
    @Option
    public ModeBarDisplayEnum displayModeBar;
    
    /**
     * The double click interaction.
     * <p>
     * Default: RESET_AUTOSIZE
     */
    @Option
    public DoubleClickInteractionEnum doubleClick;
    
    /**
     * If true, we can edit titles, move annotations, etc - sets all pieces of "edits" unless a
     * separate "edits" item overrides individual parts.
     * <p>
     * Default: false
     */
    @Option
    public Boolean editable;
    
    /**
     * Edit options on individual elements.
     */
    @Option
    public final EditOptions edits = new EditOptions();
    
    /**
     * If we do autosize, do we fill the container or the screen?
     * <p>
     * Default: false
     */
    @Option
    public Boolean fillFrame;
    
    /**
     * If we do autosize, set the frame margins in percents of plot size.
     * <p>
     * Defaults: 0
     */
    @Option
    public Integer frameMargins;
    
    /**
     * Text appearing in the sendData link.
     * <p>
     * Default: "Edit chart"
     */
    @Option
    public String linkText;
    
    /**
     * Which localization to use. Should be a string like "en" or "en-US".
     * <p>
     * Default: "en-US"
     */
    @Option
    public String locale;
    
    /**
     * Mapbox access token (required to plot mapbox trace types) If using an Mapbox Atlas server,
     * set this option to "", so that plotly.js won't attempt to authenticate to the @Option public
     * Mapbox server.
     */
    @Option
    public String mapboxAccessToken;
    
    /**
     * Fully custom mode bar buttons as nested array, where the outer arrays represents button
     * groups, and the inner arrays have buttons config objects or names of default buttons.
     * <p>
     * Default: false
     */
    @Option
    public Boolean modeBarButtons;
    
    /**
     * Remove mode bar button by name.
     */
    @Option
    public String[] modeBarButtonsToRemove;
    
    /**
     * Increase the pixel ratio for Gl plot images.
     * <p>
     * Default: 2
     */
    @Option
    public Integer plotGlPixelRatio;
    
    /**
     * The length of the undo/redo queue.
     * <p>
     * Default: 0
     */
    @Option
    public Integer queueLength;
    
    /**
     * Mouse wheel or two-finger scroll zooms the plot.
     * <p>
     * Default: false
     */
    @Option
    public Boolean scrollZoom;
    
    /**
     * If we show a link, does it contain data or just link to a plotly file?
     * <p>
     * Default: true
     */
    @Option
    public Boolean sendData;
    
    /**
     * Background setting function "transparent" (the default) sets the background
     * "layout.paper_color" "opaque" blends bg color with white ensuring an opaque background or any
     * other custom function of gd
     */
    @Option
    public String setBackground;
    
    /*
     * add mode bar button using config objects
     * (see ./components/modebar/buttons.js for list of arguments)
    modeBarButtonsToAdd: [],
     */
    
    /**
     * Enable axis pan/zoom drag handles.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showAxisDragHandles;
    
    /**
     * Enable direct range entry at the pan/zoom drag points (drag handles must be enabled above).
     * <p>
     * Default: true
     */
    @Option
    public Boolean showAxisRangeEntryBoxes;
    
    /**
     * Link to open this plot in plotly.
     * <p>
     * Default: false
     */
    @Option
    public Boolean showLink;
    
    /**
     * False or function adding source(s) to linkText.
     */
    @Option
    public Boolean showSources;
    
    /**
     * New users see some hints about interactivity.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showTips;
    
    /**
     * Set to true for no interactivity (e.g., for export or image generation).
     * <p>
     * Default: false
     */
    @Option
    public Boolean staticPlot;
    
    /*
     * Turn all console logging on or off (errors will be thrown)
     * This should ONLY be set via Plotly.setPlotConfig
     * 0: no logs
     * 1: warnings and errors, but not informational messages
     * 2: verbose logs
    logging: 1,
     */
    
    /*
     * Set global transform to be applied to all traces with no
     * specification needed
    globalTransforms: [],
    */

    /**
     * URL to topojson files used in geo charts.
     * <p>
     * Default: "https://cdn.plot.ly/"
     */
    @Option
    public String topojsonURL;
    
    /*
     * Localization definitions
     * Locales can be provided either here (specific to one chart) or globally
     * by registering them as modules.
     * Should be an object of objects {locale: {dictionary: {...}, format: {...}}}
     * {
     *     da: {
     *         dictionary: {"Reset axes": "Nulstil aksler", ...},
     *         format: {months: [...], shortMonths: [...]}
     *     },
     *     ...
     * }
     * All parts are optional. When looking for translation or format fields, we
     * look first for an exact match in a config locale, then in a registered
     * module. If those fail, we strip off any regionalization ("en-US" -> "en")
     * and try each (config, registry) again. The final fallback for translation
     * is untranslated (which is US English) and for formats is the base English
     * (the only consequence being the last fallback date format %x is DD/MM/YYYY
     * instead of MM/DD/YYYY). Currently "grouping" and "currency" are ignored
     * for our automatic number formatting, but can be used in custom formats.
    locales: {}
     */

}
