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
package org.fujion.plotly.common;

import org.fujion.ancillary.Options;

/**
 * Projection options.
 */
public class ProjectionOptions extends Options {

    /**
     * The projection opacity.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     */
    public Double opacity;

    /**
     * The scale factor determining the size of the projection marker points.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.6666666666666666
     */
    public Double scale;

    /**
     * Determines whether or not projections are shown along an axis.
     */
    public Boolean show;

}