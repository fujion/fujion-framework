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
package org.fujion.icon.materialdesign;

import org.fujion.icon.IconLibraryBase;

/**
 * Icon library wrapper for Material Design icon collection.
 */
public class IconLibrary extends IconLibraryBase {
    
    private final String id;
    
    /**
     * Create icon library definition.
     *
     * @param id The icon group.
     */
    protected IconLibrary(String id) {
        super("material-design-icons", "png", "18x18", "24x24", "36x36", "48x48");
        this.id = id;
    }
    
    @Override
    public String getId() {
        return "materialdesign-" + id;
    }
    
    @Override
    protected String doFormatPath(String name, String dims) {
        String sz = dims.split("x")[0];
        int i = name.lastIndexOf(".");
        String real = i == -1 ? name : name.substring(0, i);
        real += "_" + sz + "dp" + (i == -1 ? "" : name.substring(i));
        return id + "/1x_web/" + expandName(real);
    }
    
}
