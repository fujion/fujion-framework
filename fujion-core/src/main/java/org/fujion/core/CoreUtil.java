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
package org.fujion.core;

import org.apache.commons.lang3.StringUtils;

/**
 * General purpose utility methods.
 */
public class CoreUtil {

    /**
     * Returns the resource classpath for the specified class.
     *
     * @param clazz Class to evaluate
     * @return String representing resource path
     */
    public static String getResourceClassPath(Class<?> clazz) {
        return getResourceClassPath(clazz.getPackage());
    }

    /**
     * Returns the resource classpath for the specified class.
     *
     * @param clazz Class to evaluate
     * @param up    Number of path levels to remove
     * @return String representing resource path
     */
    public static String getResourceClassPath(Class<?> clazz, int up) {
        return getResourceClassPath(clazz.getPackage(), up);
    }

    /**
     * Returns the resource classpath for the specified package.
     *
     * @param pkg Package to evaluate
     * @return String representing resource path
     */
    public static String getResourceClassPath(Package pkg) {
        return getResourceClassPath(pkg.getName());
    }

    /**
     * Returns the resource classpath for the specified package.
     *
     * @param pkg Package to evaluate
     * @param up  Number of path levels to remove
     * @return String representing resource path
     */
    public static String getResourceClassPath(Package pkg, int up) {
        return getResourceClassPath(pkg.getName(), up);
    }

    /**
     * Returns the resource classpath for the package name.
     *
     * @param name Package name
     * @return String representing resource path
     */
    public static String getResourceClassPath(String name) {
        return getResourceClassPath(name, 0);
    }

    /**
     * Returns the resource classpath for the package name.
     *
     * @param name Package name
     * @param up   Number of path levels to remove
     * @return String representing resource path
     */
    public static String getResourceClassPath(String name, int up) {
        String path = StringUtils.removeEnd(name.replace('.', '/'), "/");

        while (up > 0) {
            int i = path.lastIndexOf("/");

            if (i <= 0) {
                break;
            } else {
                path = path.substring(0, i);
                up--;
            }
        }

        return "web/" + path + "/";
    }

    /**
     * Enforce static class.
     */
    private CoreUtil() {
    }
}
