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
package org.fujion.annotation;

import java.util.ArrayList;
import java.util.List;

import org.fujion.common.MiscUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Scans packages for member classes.
 */
public class PackageScanner {

    private final ResourcePatternResolver resolver;

    public PackageScanner() {
        this(new PathMatchingResourcePatternResolver());
    }

    public PackageScanner(ResourcePatternResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Find all classes belonging to the specified package.
     *
     * @param pkg Package to scan.
     * @return List of member classes (never null).
     */
    public List<Class<?>> getClasses(Package pkg) {
        return getClasses(pkg.getName());
    }
    
    /**
     * Find all classes belonging packages matching the pattern.
     *
     * @param pattern A pattern for matching package names.
     * @return List of member classes (never null).
     */
    public List<Class<?>> getClasses(String pattern) {
        try {
            List<Class<?>> classes = new ArrayList<>();

            for (Resource resource : resolver.getResources("classpath*:" + pattern.replace(".", "/") + "/*.class")) {
                String path = resource.getURL().getPath();
                int i = path.lastIndexOf(".jar!/") + 6;
                i = i > 5 ? i : path.lastIndexOf("/classes/") + 9;
                int j = path.lastIndexOf(".class");
                path = path.substring(i, j).replace("/", ".");
                Class<?> clazz = Class.forName(path);
                classes.add(clazz);
            }

            return classes;
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

}
