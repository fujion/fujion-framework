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
package org.fujion.mxgraph;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Represents the dimensions of a vertex.
 */
public class MXGeometry extends Options {

    @Option
    public final int x;

    @Option
    public final int y;

    @Option
    public final int height;

    @Option
    public final int width;

    @Option
    public final boolean relative;

    public MXGeometry(int x, int y, int width, int height) {
        this(x, y, width, height, false);
    }
    
    public MXGeometry(Map<String, Object> map) {
        this(MapUtils.getInteger(map, "x"), MapUtils.getInteger(map, "y"), MapUtils.getInteger(map, "width"),
            MapUtils.getInteger(map, "height"), MapUtils.getBoolean(map, "relative", false));
    }

    public MXGeometry(int x, int y, int width, int height, boolean relative) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.relative = relative;
    }
}
