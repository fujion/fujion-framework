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
package org.fujion.ace;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

import java.util.Map;

public class Range extends Options {

    /**
     * The starting row.
     */
    @Option(value="start.row")
    protected final int startRow;

    /**
     * The starting column.
     */
    @Option(value="start.column")
    protected final int startColumn;

    /**
     * The ending row.
     */
    @Option(value="end.row")
    protected final int endRow;

    /**
     * The ending column.
     */
    @Option(value="end.column")
    protected final int endColumn;

    public Range(int startRow, int startColumn, int endRow, int endColumn) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.endRow = endRow;
        this.endColumn = endColumn;
    }

    protected Range(Map<String, Map<String, Integer>> map) {
        Map<String, Integer> start = map.get("start");
        this.startRow = start.get("row");
        this.startColumn = start.get("column");
        Map<String, Integer> end = map.get("end");
        this.endRow = end.get("row");
        this.endColumn = end.get("column");
    }
}
