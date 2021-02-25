package org.fujion.ace;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class Range extends Options {

    /**
     * The starting row.
     */
    @Option(required = true)
    public Integer startRow;

    /**
     * The starting column.
     */
    @Option(required = true)
    public Integer startColumn;

    /**
     * The ending row.
     */
    @Option(required = true)
    public Integer endRow;

    /**
     * The ending column.
     */
    @Option(required = true)
    public Integer endColumn;

}
