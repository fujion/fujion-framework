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
package org.fujion.icon;

import java.util.List;

/**
 * Interface for plug-in icon libraries.
 */
public interface IIconLibrary {
    
    /**
     * Returns the unique identifier for the library.
     *
     * @return The unique identifier.
     */
    String getId();
    
    /**
     * Returns the relative path for the requested icon.
     *
     * @param iconName The icon file name.
     * @param dimensions The desired dimensions (for example, 16x16). May be null for default
     *            dimensions.
     * @return The relative path referencing the requested icon.
     */
    String getIconPath(String iconName, String dimensions);
    
    /**
     * Returns a list of relative paths for icons that match the criteria. Criteria may include
     * wildcard characters. For example, <code>getMatching("weather*", "*x16")</code>
     *
     * @param iconName Name of icon to match.
     * @param dimensions Desired dimensions to match. May be null for default dimensions.
     * @return List of matching paths.
     */
    List<String> getMatching(String iconName, String dimensions);
    
    /**
     * Returns an array of dimensions supported by this library.
     *
     * @return Array of supported dimensions.
     */
    String[] supportedDimensions();
}
