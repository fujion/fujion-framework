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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HoverLabelOptions;
import org.fujion.plotly.common.PolarDirectionEnum;

/**
 * Options for the chart layout. Note that width, height, and margin are not present here. They are
 * taken from the CSS styles applied to the widget container.
 */
public class LayoutOptions extends Options {

    public enum DragModeEnum {
        LASSO, ORBIT, PAN, SELECT, TURNTABLE, ZOOM;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum HoverModeEnum {
        CLOSEST, FALSE, X, Y;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Options for angular axis.
     */
    @Option
    public final LayoutAxisOptions angularaxis = new LayoutAxisOptions();

    /**
     * Options for layout annotations.
     */
    @Option
    public final AnnotationOptions annotations = new AnnotationOptions();
    
    /**
     * Determines whether or not a layout width or height that has been left undefined by the user
     * is initialized on each relayout. Note that, regardless of this attribute, an undefined layout
     * width or height is always initialized on the first call to plot.
     */
    @Option
    public Boolean autosize;
    
    /**
     * The default calendar system to use for interpreting and displaying dates throughout the plot.
     */
    @Option
    public CalendarTypeEnum calendar;

    /**
     * The default trace colors.
     */
    @Option
    public String colorway;

    /**
     * The direction corresponding to positive angles.
     */
    @Option
    public PolarDirectionEnum direction;

    /**
     * Determines the mode of drag interactions. SELECT and LASSO apply only to scatter traces with
     * markers or text. ORBIT and TURNTABLE apply only to 3D scenes.
     * <p>
     * Default: ZOOM
     */
    @Option
    public DragModeEnum dragmode;

    /**
     * The global font. Note that fonts used in traces and other layout components inherit from the
     * global font.
     * <p>
     * Default: "Open Sans, verdana, arial, sans-serif"
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * Layout options for geo.
     */
    @Option
    public final GeoOptions geo = new GeoOptions();

    /**
     * Determines whether or not a text link citing the data source is placed at the bottom-right
     * cored of the figure. Has only an effect only on graphs that have been generated via forked
     * graphs from the plotly service (at https://plot.ly or on-premise).
     */
    @Option
    public Boolean hidesources;
    
    /**
     * The default distance (in pixels) to look for data to add hover labels (-1 means no cutoff, 0
     * means no looking for data).
     * <p>
     * Default: 20
     */
    @Option
    public Integer hoverdistance;
    
    /**
     * Options for hover label. Note: do not use {@link HoverLabelOptions#namelength$array} form.
     */
    @Option
    public final HoverLabelOptions hoverlabel = new HoverLabelOptions();

    /**
     * Determines the mode of hover interactions.
     */
    @Option
    public HoverModeEnum hovermode;
    
    /**
     * Options for images.
     */
    @Option
    public final ImageOptions images = new ImageOptions();

    /**
     * Legend options.
     */
    @Option
    public final LegendOptions legend = new LegendOptions();

    /**
     * Options for mapbox plots.
     */
    @Option
    public final MapboxOptions mapbox = new MapboxOptions();
    
    /**
     * For polar plots only. Rotates the entire polar by the given angle.
     */
    @Option
    public Double orientation;
    
    /**
     * The color of paper where the graph is drawn.
     * <p>
     * Default: "#fff"
     */
    @Option
    public String paper_bgcolor;
    
    /**
     * The color of plotting area in-between x and y axes.
     * <p>
     * Default: "#fff"
     */
    @Option
    public String plot_bgcolor;

    /**
     * Options for polar plots.
     */
    @Option
    public final PolarOptions polar = new PolarOptions();
    
    /**
     * Options for radial axes.
     */
    @Option
    public final LayoutAxisOptions radialaxis = new LayoutAxisOptions();
    
    /**
     * Options for scenes.
     */
    @Option
    public final SceneOptions scene = new SceneOptions();

    /**
     * The decimal and thousand separators. For example, ". " puts a '.' before decimals and a space
     * between thousands. In English locales, dflt is ".," but other locales may alter this default.
     */
    @Option
    public String separators;

    /**
     * Options for shapes.
     */
    @Option
    public final ShapeOptions shapes = new ShapeOptions();

    /**
     * Determines whether or not a legend is drawn.
     */
    @Option
    public Boolean showlegend;

    /**
     * Options for slider controls.
     */
    @Option
    public final SliderOptions sliders = new SliderOptions();

    /**
     * The default distance (in pixels) to look for data to draw spikelines to (-1 means no cutoff,
     * 0 means no looking for data).
     * <p>
     * Default: 20
     */
    @Option
    public Integer spikedistance;
    
    /**
     * Options for ternary plots.
     */
    @Option
    public final TernaryOptions ternary = new TernaryOptions();
    
    /**
     * The chart's title.
     */
    @Option
    public String title;

    /**
     * The title font.
     */
    @Option
    public final FontOptions titlefont = new FontOptions();

    /**
     * Options for update menus.
     */
    @Option
    public final UpdateMenuOptions updatemenus = new UpdateMenuOptions();

    /**
     * Default options for x axis.
     */
    @Option
    public final XYZAxisOptions xaxis = new XYZAxisOptions();

    /**
     * Default options for y axis;
     */
    @Option
    public final XYZAxisOptions yaxis = new XYZAxisOptions();

}
