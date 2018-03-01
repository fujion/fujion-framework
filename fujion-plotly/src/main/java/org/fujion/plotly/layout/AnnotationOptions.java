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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.HoverLabelOptions;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Layout options for annotations.
 */
public class AnnotationOptions extends Options {
    
    /**
     * Determines how the annotation responds to clicks on the plot.
     */
    public enum ClickToShowEnum {
        FALSE, ONOFF, ONOUT;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The horizontal alignment of the "text" within the box. Has an effect only if "text" spans
     * more two or more lines (i.e. "text" contains one or more &lt;br&gt; HTML tags) or if an
     * explicit width is set to override the text width.
     * <p>
     * Default: CENTER
     */
    public HorizontalAlignEnum align;

    /**
     * The color of the annotation arrow.
     */
    public String arrowcolor;

    /**
     * The end annotation arrow head style.
     * <p>
     * Range; &ge;0 and &le;8
     * <p>
     * Default: 1
     */
    public Integer arrowhead;

    /**
     * Any combination of "end", "start" joined with a "+" OR "none".
     * <p>
     * Examples: "end", "start", "end+start", "none".
     * <p>
     * Default: "end"
     */
    public String arrowside;

    /**
     * The size of the end annotation arrow head, relative to "arrowwidth". A value of 1 (default)
     * gives a head about 3x as wide as the line.
     * <p>
     * Constraints: &ge;0.3
     * <p>
     * Default: 1
     */
    public Double arrowsize;
    
    /**
     * The width (in px) of annotation arrow line.
     */
    public Integer arrowwidth;
    
    /**
     * The x component of the arrow tail about the arrow head. If "axref" is "pixel", a positive
     * (negative) component corresponds to an arrow pointing from right to left (left to right). If
     * "axref" is an axis, this is an absolute value on that axis, like "x", NOT a relative value.
     */
    public Double ax$number;
    
    /**
     * The x component of the arrow tail about the arrow head as a categorical string.
     */
    public String ax$string;
    
    /**
     * Indicates in what terms the tail of the annotation (ax,ay) is specified. If "pixel", "ax" is
     * a relative offset in pixels from "x". If set to an x axis id (e.g. "x" or "x2"), "ax" is
     * specified in the same terms as that axis. This is useful for trendline annotations which
     * should continue to indicate the correct trend when zoomed.
     * <p>
     * Constraints: "pixel" | "/^x([2-9]|[1-9][0-9]+)?$/"
     * <p>
     * Default: "pixel"
     */
    public String axref;

    /**
     * the y component of the arrow tail about the arrow head. If "ayref" is "pixel", a positive
     * (negative) component corresponds to an arrow pointing from bottom to top (top to bottom). If
     * "ayref" is an axis, this is an absolute value on that axis, like "y", NOT a relative value.
     */
    public Double ay$number;
    
    /**
     * The y component of the arrow tail about the arrow head as a categorical string.
     */
    public String ay$string;
    
    /**
     * Indicates in what terms the tail of the annotation (ax,ay) is specified. If "pixel", "ay" is
     * a relative offset in pixels from "y". If set to a y axis id (e.g. "y" or "y2"), "ay" is
     * specified in the same terms as that axis. This is useful for trendline annotations which
     * should continue to indicate the correct trend when zoomed.
     * <p>
     * Constraints: "pixel" | "/^x([2-9]|[1-9][0-9]+)?$/"
     * <p>
     * Default: "pixel"
     */
    public String ayref;
    
    /**
     * The background color of the annotation.
     * <p>
     * Default: "rgba(0,0,0,0)"
     */
    public String bgcolor;
    
    /**
     * The color of the border enclosing the annotation "text".
     * <p>
     * Default: "rgba(0,0,0,0)"
     */
    public String bordercolor;

    /**
     * The padding (in px) between the "text" and the enclosing border.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer borderpad;

    /**
     * The width (in px) of the border enclosing the annotation "text".
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer borderwidth;
    
    /**
     * Determines whether the annotation text box captures mouse move and click events, or allows
     * those events to pass through to data points in the plot that may be behind the annotation. By
     * default "captureevents" is false unless "hovertext" is provided. If you use the event
     * "plotly_clickannotation" without "hovertext" you must explicitly enable "captureevents".
     */
    public Boolean captureevents;
    
    /**
     * Determines how the annotation responds to clicks on the plot. If you click a data point that
     * exactly matches the "x" and "y" values of this annotation, and it is hidden (visible: false),
     * it will appear. In ONOFF mode, you must click the same point again to make it disappear, so
     * if you click multiple points, you can show multiple annotations. In ONOUT mode, a click
     * anywhere else in the plot (on another data point or not) will hide this annotation. If you
     * need to show/hide this annotation in response to different "x" or "y" values, you can set
     * "xclick" and/or "yclick". This is useful for example to label the side of a bar. To label
     * markers though, "standoff" is preferred over "xclick" and "yclick".
     */
    public ClickToShowEnum clicktoshow;
    
    /**
     * The annotation text font.
     */
    public final FontOptions font = new FontOptions();

    /**
     * Explicit height for the text box. Taller text will be clipped.
     * <p>
     * Constraints: &ge;1
     * <p>
     * Default: Lets the text set the box height.
     */
    public Integer height;

    /**
     * Options for the hover label.
     */
    public final HoverLabelOptions hoverlabel = new HoverLabelOptions();

    /**
     * Text to appear when hovering over this annotation. If omitted or blank, no hover label will
     * appear.
     */
    public String hovertext;
    
    /**
     * The opacity of the annotation (text + arrow).
     * <p>
     * Constraints: 0 - 1, inclusive
     * <p>
     * Default: 1
     */
    public Double opacity;
    
    /**
     * Determines whether or not the annotation is drawn with an arrow. If true, "text" is placed
     * near the arrow's tail. If false, "text" lines up with the "x" and "y" provided.
     * <p>
     * Default: true
     */
    public Boolean showarrow;

    /**
     * Distance, in pixels, to move the end arrowhead away from the position it is pointing at, for
     * example to point at the edge of a marker independent of zoom. Note that this shortens the
     * arrow from the "ax" / "ay" vector, in contrast to "xshift" / "yshift" which moves everything
     * by this amount.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    public Integer standoff;
    
    /**
     * The start annotation arrow head style.
     * <p>
     * Range; &ge;0 and &le;8
     * <p>
     * Default: 1
     */
    public Integer startarrowhead;

    /**
     * The size of the start annotation arrow head, relative to "arrowwidth". A value of 1 (default)
     * gives a head about 3x as wide as the line.
     * <p>
     * Constraints: &ge;0.3
     * <p>
     * Default: 1
     */
    public Double startarrowsize;

    /**
     * Distance, in pixels, to move the start arrowhead away from the position it is pointing at,
     * for example to point at the edge of a marker independent of zoom. Note that this shortens the
     * arrow from the "ax" / "ay" vector, in contrast to "xshift" / "yshift" which moves everything
     * by this amount.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    public Integer startstandoff;

    /**
     * The text associated with this annotation. Plotly uses a subset of HTML tags to do things like
     * newline (&lt;br&gt; ), bold (&lt;b&gt;&lt;/b&gt;), italics (&lt;i&gt;&lt;/i&gt;), hyperlinks
     * (&lt;a href='...'&gt;&lt;/a&gt;). Tags &lt;em&gt;, &lt;sup&gt;, &lt;sub&gt; &lt;span&gt; are
     * also supported.
     */
    public String text;

    /**
     * The angle at which the "text" is drawn with respect to the horizontal.
     * <p>
     * Default: 0
     */
    public Integer textangle;
    
    /**
     * The vertical alignment of the "text" within the box. Has an effect only if an explicit height
     * is set to override the text height.
     * <p>
     * Default: MIDDLE
     */
    public VerticalAlignEnum valign;
    
    /**
     * Determines whether or not the annotation is visible.
     */
    public Boolean visible;

    /**
     * Explicit width for the text box. Wider text will be clipped. There is no automatic wrapping;
     * use &lt;br&gt; to start a new line.
     * <p>
     * Constraints: &ge;1
     * <p>
     * Default: Lets the text set the box width.
     */
    public Integer width;

    /**
     * The annotation's x position as a number. If the axis "type" is "log", then you must take the
     * log of your desired range. If the axis "type" is "date", it should be date strings, like date
     * data, though Date objects and unix milliseconds will be accepted and converted to strings. If
     * the axis "type" is "category", it should be numbers, using the scale where each category is
     * assigned a serial number from zero in the order it appears.
     */
    public Double x$number;
    
    /**
     * The annotation's x position as a categorical coordinate string. If the axis "type" is "log",
     * then you must take the log of your desired range. If the axis "type" is "date", it should be
     * date strings, like date data, though Date objects and unix milliseconds will be accepted and
     * converted to strings. If the axis "type" is "category", it should be numbers, using the scale
     * where each category is assigned a serial number from zero in the order it appears.
     */
    public String x$string;

    /**
     * The text box's horizontal position anchor This anchor binds the "x" position to the left,
     * center or right of the annotation. For example, if "x" is set to 1, "xref" to "paper" and
     * "xanchor" to RIGHT, then the right-most portion of the annotation lines up with the
     * right-most edge of the plotting area. If AUTO, the anchor is equivalent to CENTER for
     * data-referenced annotations or if there is an arrow, whereas for paper-referenced with no
     * arrow, the anchor picked corresponds to the closest side.
     * <p>
     * Default: AUTO
     */
    public HorizontalAlignEnum xanchor;

    /**
     * Toggle the annotation when clicking a data point whose "x" value is "xclick" rather than the
     * annotation's "x" value.
     */
    public Double xclick$number;
    
    /**
     * Toggle the annotation when clicking a data point whose "x" value is "xclick" rather than the
     * annotation's "x" value.
     */
    public String xclick$string;
    
    /**
     * The annotation's x coordinate axis. If set to an x axis id (e.g. "x" or "x2"), the "x"
     * position refers to an x coordinate If set to "paper", the "x" position refers to the distance
     * from the left side of the plotting area in normalized coordinates where 0 (1) corresponds to
     * the left (right) side.
     * <p>
     * Constraints: "paper" | "/^x([2-9]|[1-9][0-9]+)?$/"
     */
    public String xref;

    /**
     * Shifts the position of the whole annotation and arrow to the right (positive) or left
     * (negative) by this many pixels.
     * <p>
     * Default: 0
     */
    public Integer xshift;
    
    /**
     * The annotation's y position as a number. If the axis "type" is "log", then you must take the
     * log of your desired range. If the axis "type" is "date", it should be date strings, like date
     * data, though Date objects and unix milliseconds will be accepted and converted to strings. If
     * the axis "type" is "category", it should be numbers, using the scale where each category is
     * assigned a serial number from zero in the order it appears.
     */
    public Double y$number;
    
    /**
     * The annotation's y position as a categorical coordinate string. If the axis "type" is "log",
     * then you must take the log of your desired range. If the axis "type" is "date", it should be
     * date strings, like date data, though Date objects and unix milliseconds will be accepted and
     * converted to strings. If the axis "type" is "category", it should be numbers, using the scale
     * where each category is assigned a serial number from zero in the order it appears.
     */
    public String y$string;

    /**
     * The text box's vertical position anchor This anchor binds the "y" position to the top, middle
     * or bottom of the annotation. For example, if "y" is set to 1, "yref" to "paper" and "yanchor"
     * to TOP, then the top-most portion of the annotation lines up with the top-most edge of the
     * plotting area. If AUTO, the anchor is equivalent to MIDDLE for data-referenced annotations or
     * if there is an arrow, whereas for paper-referenced with no arrow, the anchor picked
     * corresponds to the closest side.
     * <p>
     * Default: AUTO
     */
    public VerticalAlignEnum yanchor;
    
    /**
     * Toggle this annotation when clicking a data point whose "y" value is "yclick" rather than the
     * annotation's "y" value.
     */
    public Double yclick$number;
    
    /**
     * Toggle this annotation when clicking a data point whose "y" value is "yclick" rather than the
     * annotation's "y" value.
     */
    public String yclick$string;

    /**
     * The annotation's y coordinate axis. If set to an y axis id (e.g. "y" or "y2"), the "y"
     * position refers to an y coordinate If set to "paper", the "y" position refers to the distance
     * from the bottom of the plotting area in normalized coordinates where 0 (1) corresponds to the
     * bottom (top).
     * <p>
     * Constraints: "paper" | "/^y([2-9]|[1-9][0-9]+)?$/"
     */
    public String yref;

    /**
     * Shifts the position of the whole annotation and arrow up (positive) or down (negative) by
     * this many pixels.
     * <p>
     * Default: 0
     */
    public Integer yshift;
}
