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
package org.fujion.annotation;

import org.fujion.common.MiscUtil;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * Abstract base class for scanning class-level annotations.
 *
 * @param <T> Type of target class.
 * @param <A> Type of annotation class.
 */
public abstract class AbstractClassScanner<T, A extends Annotation> {

    private final PackageScanner packageScanner = new PackageScanner();

    private final Class<T> targetClass;

    private final Class<? extends Annotation> annotationClass;

    /**
     * Create class scanner.
     *
     * @param targetClass The class that is the target of the annotation.
     * @param annotationClass The annotation class.
     */
    protected AbstractClassScanner(Class<T> targetClass, Class<A> annotationClass) {
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
     * Scan all classes belonging to the matching package(s).
     *
     * @param pkgName A package name. Wild card characters are allowed.
     */
    public void scanPackage(String pkgName) {
        for (Class<?> clazz : packageScanner.getClasses(pkgName)) {
            scanClass(clazz);
        }
    }

    /**
     * Scans a class (and any inner classes) for the presence of the target annotation. For each
     * matching class, calls the abstract <code>doScanClass</code> method.
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
     * Scans a class (and any inner classes) for the presence of the target annotation. For each
     * matching class, calls the abstract <code>doScanClass</code> method.
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

        Assert.isTrue(targetClass.isAssignableFrom(clazz),
            () -> annotationClass.getName() + " annotation only valid on " + targetClass.getName() + " subclass");
        doScanClass((Class<T>) clazz);
    }

    /**
     * Scan for and process annotations in the specified class.
     *
     * @param clazz Class to be scanned.
     */
    protected abstract void doScanClass(Class<T> clazz);

}
