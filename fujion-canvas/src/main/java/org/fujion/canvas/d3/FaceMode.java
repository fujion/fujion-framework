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
 * Specifies front- and/or back-facing polygons.
 */
public enum FaceMode implements IEnumWithValue {
    /**
     * Back-facing polygons.
     */
    BACK(1029),
    /**
     * Front-facing polygons.
     */
    FRONT(1028),
    /**
     * Front- and back-facing polygons.
     */
    FRONT_AND_BACK(1032);

    private int value;

    FaceMode(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
