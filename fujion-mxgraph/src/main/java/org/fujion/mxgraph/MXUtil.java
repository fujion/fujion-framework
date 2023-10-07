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
package org.fujion.mxgraph;

import org.fujion.convert.ConversionService;
import org.w3c.dom.Element;

/**
 * Utility methods.
 */
public class MXUtil {

    protected static final String TAG_ROOT = "root";
    
    protected static final String TAG_CELL = "mxCell";
    
    protected static final String TAG_GEOMETRY = "mxGeometry";
    
    public static <T> T get(Element node, String attr, Class<T> type) {
        return get(node, attr, type, null);
    }
    
    public static <T> T get(Element node, String attr, Class<T> type, T deflt) {
        T value = ConversionService.getInstance().getAttributeAs(node, attr, type);
        return value == null ? deflt : value;
    }

    private MXUtil() {
    }
}
