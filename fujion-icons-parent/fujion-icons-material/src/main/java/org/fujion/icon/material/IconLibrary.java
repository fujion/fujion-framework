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
package org.fujion.icon.material;

import org.fujion.icon.IconLibraryBase;

/**
 * Icon library wrapper for Simple icon collection.
 */
public class IconLibrary extends IconLibraryBase {
    
    /**
     * Create icon library definition.
     *
     */
    protected IconLibrary() {
        super("webjar-material-design-icons", "svg", "24x24");
    }
    
    @Override
    public String getId() {
        return "material";
    }

    @Override
    protected String doFormatPath(String name, String dims) {
        if (!name.contains("/")) {
            name = "filled/" + name;
        }

        return expandName("dist/" + name);
    }
    
}
