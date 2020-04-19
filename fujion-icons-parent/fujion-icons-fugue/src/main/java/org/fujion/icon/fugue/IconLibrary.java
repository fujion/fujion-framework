/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.icon.fugue;

import org.apache.commons.io.FilenameUtils;
import org.fujion.icon.IconLibraryBase;

/**
 * Icon library wrapper for Fugue icon collection.
 */
public class IconLibrary extends IconLibraryBase {

    private final boolean shadowless;
    
    /**
     * Create icon library definition.
     *
     * @param shadowless If true, use shadow-free versions of icons.
     */
    protected IconLibrary(boolean shadowless) {
        super("fugue-icons", "png", "16x16", "24x24", "32x32");
        this.shadowless = shadowless;
    }

    @Override
    public String getId() {
        return shadowless ? "fugue-shadowless" : "fugue";
    }
    
    @Override
    protected String doFormatPath(String name, String dims) {
        name = expandName(name);
        String ext = FilenameUtils.getExtension(name);
        String root = "gif".equals(ext) ? "bonus/animated/icons" : "16x16".equals(dims) ? "icons" : "bonus/icons";
        root += shadowless ? "-shadowless" : "";
        root += "16x16".equals(dims) ? "" : "-" + dims.split("x")[0];
        return root + "/" + name;
    }

}
