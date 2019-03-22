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
package org.fujion.icon;

import java.util.List;

import org.springframework.util.AntPathMatcher;

/**
 * Utility class for Icon support
 */
public class IconUtil {
    
    protected static final AntPathMatcher matcher = new AntPathMatcher();
    
    private static class IconParams {
        
        final String name;
        
        final String dimensions;
        
        final String library;
        
        /**
         * Create icon parameters from specifier and defaults.
         *
         * @param icon Icon specifier. May be the name only, or library:name:dimensions.
         * @param defltDimensions Default dimensions.
         * @param defltLibrary Default library.
         */
        IconParams(String icon, String defltDimensions, String defltLibrary) {
            String[] pcs = icon.split("\\:", 3);
            boolean all = pcs.length > 1;
            this.library = getElement(pcs, all ? 0 : -1, defltLibrary);
            this.name = getElement(pcs, all ? 1 : 0, "noname");
            this.dimensions = getElement(pcs, all ? 2 : -1, defltDimensions);
        }
        
        private String getElement(String[] pcs, int index, String deflt) {
            String element = index >= 0 && index < pcs.length ? pcs[index] : "";
            return element.isEmpty() ? deflt : element;
        }
    }
    
    /**
     * <p>
     * Returns the path to the icon resource.
     * </p>
     * <p>
     * For example: getIconPath("help.png") or getIconPath("silk:help:16x16")
     * </p>
     * <p>
     * <i>Note: This method signature is exposed as the <code>getIconPath</code> tag library
     * function.</i>
     * </p>
     *
     * @param icon An icon name or specifier.
     * @return Path to icon resource.
     */
    public static String getIconPath(String icon) {
        return getIconPath(null, icon, null);
    }
    
    /**
     * Returns the path to the icon resource given its name, dimensions, and library name.
     * <p>
     * <i>Note: This method signature is exposed as the <code>getIconPathEx</code> tag library
     * function.</i>
     * </p>
     *
     * @param icon Name of the requested icon (e.g., "help.png").
     * @param library Name of the library containing the icon (e.g., "silk"). Specify null to use
     *            default.
     * @param dimensions Dimensions of the requested icon (e.g., "16x16"). Specify null to use
     *            default.
     * @return The icon path.
     */
    public static String getIconPath(String library, String icon, String dimensions) {
        IconParams params = new IconParams(icon, dimensions, library);
        IIconLibrary lib = IconLibraryRegistry.getInstance().get(params.library);
        return lib == null ? null : lib.getIconPath(params.name, params.dimensions);
    }
    
    /**
     * Returns the paths to matching icon resources given name, dimensions, and library name, any
     * one of which may contain wildcard characters.
     *
     * @param library Library name containing the icon (e.g., "silk"). Specify null to use default.
     * @param icon An icon name or specifier (e.g., "help.png" or "silk:help:16x16").
     * @param dimensions Dimensions of the requested icon (e.g., "16x16"). Specify null to use
     *            default.
     * @return The icon path.
     */
    public static List<String> getMatching(String library, String icon, String dimensions) {
        return IconLibraryRegistry.getInstance().getMatching(library, icon, dimensions);
    }
    
    /**
     * Enforce static class.
     */
    private IconUtil() {
    }
}
