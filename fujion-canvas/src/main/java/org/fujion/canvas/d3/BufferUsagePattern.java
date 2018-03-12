/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.canvas.d3;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Buffer usage patterns.
 */
public enum BufferUsagePattern implements IEnumWithValue {
    // @formatter:off

    DYNAMIC_COPY(35050),
    DYNAMIC_DRAW(35048),
    DYNAMIC_READ(35049),
    STATIC_COPY(35046),
    STATIC_DRAW(35044),
    STATIC_READ(35045),
    STREAM_COPY(35042),
    STREAM_DRAW(35040),
    STREAM_READ(35041);

    // @formatter:on
    
    private int value;

    BufferUsagePattern(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
