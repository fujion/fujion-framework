/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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

import java.lang.annotation.Annotation;

import org.fujion.common.MiscUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Abstract base class for scanning class-level annotations.
 *
 * @param <T> Type of target class.
 * @param <A> Type of annotation class.
 */
public abstract class AbstractClassScanner<T, A extends Annotation> {

    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private final Class<T> targetClass;

    private final Class<? extends Annotation> annotationClass;

    /**
     * Create class scanner.
     *
     * @param targetClass The class that is the target of the annotation.
     * @param annotationClass The annotation class.
     */
    protected AbstractClassScanner(Class<T> targetClass, Class<? extends Annotation> annotationClass) {
        this.targetClass = targetClass;
        this.annotationClass = annotationClass;
    }

    /**
     * Scan all classes belonging to the specified package.
     *
     * @param pkg A package.
     */
    public void scanPackage(Package pkg) {
        scanPackage(pkg.getName());
    }

    /**
     * Scan all classes belonging to the specified package.
     *
     * @param pkgName A package name. Wild card characters are allowed.
     */
    public void scanPackage(String pkgName) {
        try {
            for (Resource resource : resolver.getResources("classpath*:" + pkgName.replace(".", "/") + "/*.class")) {
                String path = resource.getURL().getPath();
                int i = path.lastIndexOf(".jar!/") + 6;
                i = i > 5 ? i : path.lastIndexOf("/classes/") + 9;
                int j = path.lastIndexOf(".class");
                path = path.substring(i, j).replace("/", ".");
                Class<?> clazz = Class.forName(path);
                scanClass(clazz);
            }
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Creates and registers a component definition for a class by scanning the named class and its
     * superclasses for method annotations.
     *
     * @param className Fully qualified name of the class to scan.
     */
    public void scanClass(String className) {
        try {
            scanClass(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Creates and registers a component definition for a class by scanning the class and its
     * superclasses for method annotations.
     *
     * @param clazz Class to scan.
     */
    @SuppressWarnings("unchecked")
    public void scanClass(Class<?> clazz) {
        for (Class<?> innerClass : clazz.getDeclaredClasses()) {
            scanClass(innerClass);
        }

        if (!clazz.isAnnotationPresent(annotationClass)) {
            return;
        }

        if (!targetClass.isAssignableFrom(clazz)) {
            throw new RuntimeException(
                    annotationClass.getName() + " annotation only valid on " + targetClass.getName() + " subclass");
        }

        doScanClass((Class<T>) clazz);
    }

    /**
     * Scan for and process annotations in the specified class.
     *
     * @param clazz Class to be scanned.
     */
    protected abstract void doScanClass(Class<T> clazz);

}
