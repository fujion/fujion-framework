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
import org.fujion.chartjs.common.TickOptions;
import org.fujion.chartjs.enums.TickSourceEnum;

/**
 * Tick options for time plots.
 */
public class TimeTickOptions extends TickOptions {

    /**
     * How ticks are generated.
     * <p>
     * Default: AUTO
     */
    @Option
    public TickSourceEnum source;
}
