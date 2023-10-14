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
package org.fujion.chartjs.axis;

import org.fujion.annotation.Option;

/**
 * Options for category axes.
 */
public class CategoryAxisOptions extends CartesianAxisOptions {

    /**
     * An array of labels to display.
     */
    @Option
    public String[] labels;

    /**
     * The minimum item to display as index of label.
     */
    @Option
    public Integer min$number;

    /**
     * The minimum item to display as value of label.
     */
    @Option
    public String min$string;

    /**
     * The maximum item to display as index of label.
     */
    @Option
    public Integer max$number;

    /**
     * The maximum item to display as value of label.
     */
    @Option
    public String max$string;

    /**
     * Tick options.
     */
    @Option
    public final CategoryTickOptions ticks = new CategoryTickOptions();

}
