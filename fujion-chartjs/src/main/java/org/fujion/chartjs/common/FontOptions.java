package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Font options.
 */
public class FontOptions extends Options {

    /**
     * Default font family for all text, follows CSS font-family options.
     * <p>
     * Default: 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif
     */
    @Option
    public String family;

    /**
     * Height of an individual line of text.  Follows CSS format for line-height.
     */
    @Option
    public Integer lineHeight$number;

    /**
     * Height of an individual line of text.  Follows CSS format for line-height.
     */
    @Option
    public String lineHeight$string;

    /**
     * Default font size (in px) for text. Does not apply to radialLinear scale point labels.
     * <p>
     * Default: 12
     */
    @Option
    public Integer size;

    /**
     * Default font style. Does not apply to tooltip title or footer. Does not apply to chart title. Follows CSS
     * font-style options (i.e. normal, italic, oblique, initial, inherit).
     * <p>
     * Default: normal
     */
    @Option
    public String style;

    /**
     * Default font weight (boldness).
     */
    @Option
    public String weight;

}
