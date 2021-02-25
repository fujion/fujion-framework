/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

/**
 * Options controlling a search request.
 */
public class SearchOptions extends Options {

    /**
     * The string or regular expression to search for.
     */
    @Option
    public String needle;

    /**
     * Whether to search backwards from where cursor currently is. Defaults to false.
     */
    @Option
    public Boolean backwards;

    /**
     * Whether to wrap the search back to the beginning when it hits the end. Defaults to false.
     */
    @Option
    public Boolean wrap;

    /**
     * Whether the search ought to be case-sensitive. Defaults to false.
     */
    @Option
    public Boolean caseSensitive;

    /**
     * Whether the search matches only on whole words. Defaults to false.
     */
    @Option
    public Boolean wholeWord;

    /**
     * The Range to search within. The default is the entire document.
     */
    @Option
    public Range range;

    /**
     * Whether the search is a regular expression or not. Defaults to false.
     */
    @Option
    public Boolean regExp;

    /**
     * The starting Range or cursor position to begin the search.
     */
    @Option
    public Range start;

    /**
     * Whether or not to include the current line in the search. Default to false.
     */
    @Option
    public Boolean skipCurrent;

}
