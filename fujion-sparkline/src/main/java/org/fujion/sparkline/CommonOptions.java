package org.fujion.sparkline;

import java.util.Map;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class CommonOptions extends Options {
    
    /**
     * The type of sparkline.
     * <p>
     * Default: LINE
     */
    @Option
    protected SparklineType type = SparklineType.LINE;
    
    /**
     * Used by line and discrete charts to specify the color of the line drawn as a CSS values
     * string.
     */
    @Option
    protected String lineColor;
    
    /**
     * The color used to fill the area under the graph as a CSS value.
     */
    @Option
    protected String fillColor;
    
    /**
     * The minimum value to use for the range of Y values of the chart.
     * <p>
     * Default: the minimum value supplied
     */
    @Option
    protected Double chartRangeMin;
    
    /**
     * The maximum value to use for the range of Y values of the chart
     * <p>
     * Default: the maximum value supplied
     */
    @Option
    protected Double chartRangeMax;
    
    /**
     * Set to true to disable all sparkline interactivity.
     * <p>
     * Default: false
     */
    @Option
    public Boolean disableInteraction;
    
    /**
     * Set to true to disable mouseover tooltips.
     * <p>
     * Default: false
     */
    @Option
    public Boolean disableTooltips;
    
    /**
     * Set to true to disable the highlighting of individual values when mousing over a sparkline.
     * <p>
     * Default: false
     */
    @Option
    public Boolean disableHighlight;
    
    /**
     * Controls the amount to lighten or darken a value when moused over. A value of 1.5 will
     * lighten by 50%, 0.5 will darken by 50%.
     * <p>
     * Default: 1.4
     */
    @Option
    public Double highlightLighten;
    
    /**
     * If specified, then values that are moused over will be changed to this color instead of being
     * lightend.
     */
    @Option
    public String highlightColor;
    
    /**
     * Specifies a CSS class name to apply to tooltips to override the default built-in style.
     */
    @Option
    public String tooltipClassname;
    
    /**
     * Specifies how many pixels away from the mouse pointer to render the tooltip on the X axis.
     */
    @Option
    public Integer tooltipOffsetX;
    
    /**
     * Specifies how many pixels away from the mouse pointer to render the tooltip on the Y axis.
     */
    @Option
    public Double tooltipOffsetY;
    
    /**
     * Pass a javascript function to use as a callback to override the HTML used to generate
     * tooltips. The callback will be passed arguments of (sparkline, options, fields).
     */
    @Option(convertTo = JavaScript.class)
    public String tooltipFormatter;
    
    /**
     * If specified then the tooltip uses the string specified by this setting as a title.
     */
    @Option
    public String tooltipChartTitle;
    
    /**
     * A format array to control the format of the tooltips.
     */
    @Option("tooltipFormat")
    public String[] tooltipFormat$array;
    
    /**
     * A format string to control the format of the tooltip.
     */
    @Option("tooltipFormat")
    public String tooltipFormat$string;
    
    /**
     * A string to prepend to each field displayed in a tooltip.
     */
    @Option
    public String tooltipPrefix;
    
    /**
     * A string to append to each field displayed in a tooltip.
     */
    @Option
    public String tooltipSuffix;
    
    /**
     * If true then null values will not have a tooltip displayed.
     */
    @Option
    public String tooltipSkipNull;
    
    /**
     * An range map to map field values to tooltip strings. For example you may want to map -1, 0
     * and 1 to the strings "Lost", "Draw", "Won".
     */
    @Option
    public Map<String, String> tooltipValueLookups;
    
    /**
     * Pass a javascript function to control how numbers are formatted in tooltips. The callback
     * will be passed a number to format and must return a string. Default behavior is to format
     * numbers to western conventions.
     */
    @Option(convertTo = JavaScript.class)
    public String numberFormatter;
    
    /**
     * Character to use for group separator in numbers "1,234" for l10n purposes.
     * <p>
     * Default: ','
     */
    @Option
    public Character numberDigitGroupSep;
    
    /**
     * Character to use for the decimal point in numbers for l10n purposes.
     * <p>
     * Default: '.'
     */
    @Option
    public Character numberDecimalMark;
    
    /**
     * Number of digits between the group separator in numbers for l10n purposes.
     * <p>
     * Default: 3
     */
    @Option
    public Integer numberDigitGroupCount;
    
}
