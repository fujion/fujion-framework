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
package org.fujion.icon.elegant;

import org.apache.commons.io.FilenameUtils;
import org.fujion.icon.IconLibraryBase;

/**
 * Icon library wrapper for Elegant icon collection.
 */
public class IconLibrary extends IconLibraryBase {
    
    /**
     * Create icon library definition.
     *
     */
    protected IconLibrary() {
        super("github-com-paroos-elegant-icons", "png", "32x32");
    }
    
    @Override
    public String getId() {
        return "elegant";
    }

    @Override
    protected String doFormatPath(String name, String dims) {
        name = expandName(name);
        String ext = FilenameUtils.getExtension(name).toUpperCase();
        return "images/" + ext + "/" + name;
    }
    
}
