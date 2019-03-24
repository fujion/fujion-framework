/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
package org.fujion.icon.iconic;

import org.fujion.common.StrUtil;
import org.fujion.icon.IconLibraryBase;

/**
 * Icon library wrapper for Open Iconic collection.
 */
public class IconLibrary extends IconLibraryBase {

    /**
     * Create icon library definition.
     */
    protected IconLibrary() {
        super("open-iconic", "png", "16x16", "8x8", "24x24", "32x32", "48x48", "64x64");
    }

    @Override
    public String getId() {
        return "iconic";
    }
    
    @Override
    protected String doFormatPath(String name, String dims) {
        name = expandName(name);
        int i = name.lastIndexOf(".");
        String ext = name.substring(i + 1);
        name = name.substring(0, i);
        int dm = -StrUtil.extractInt(dims) / 8;
        String sfx = name.endsWith("*") ? "{x:[^x]}." : ".";
        return ext + "/" + name + (dm == -1 ? sfx : dm + "x.") + ext;
    }

}
