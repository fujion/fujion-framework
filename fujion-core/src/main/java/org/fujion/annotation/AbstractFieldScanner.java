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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.BiFunction;

/**
 * Abstract base class for scanning field-level annotations.
 *
 * @param <T> Instance type to be scanned.
 * @param <A> Type of annotation class.
 */
public abstract class AbstractFieldScanner<T, A extends Annotation> {

    private final Class<T> instanceClass;
    
    private final Class<A> annotationClass;

    /**
     * Create field scanner.
     *
     * @param instanceClass The instance class.
     * @param annotationClass The annotation class.
     */
    protected AbstractFieldScanner(Class<T> instanceClass, Class<A> annotationClass) {
        this.instanceClass = instanceClass;
        this.annotationClass = annotationClass;
    }

    /**
     * Scans all annotated fields of the given object instance.
     *
     * @param instance Object instance to scan.
     * @param processor Field processor.
     */
    public void scan(T instance, BiFunction<A, Field, Boolean> processor) {
        if (instance != null) {
            scan(instance, processor, instance.getClass());
        }
    }

    /**
     * Scan class and superclasses for annotated fields, calling the processor for each annotated
     * field. Superclasses are scanned first.
     *
     * @param instance Object instance to scan.
     * @param processor Field processor.
     * @param clazz The class to be scanned.
     * @return Returns false to stop further scanning.
     */
    private boolean scan(T instance, BiFunction<A, Field, Boolean> processor, Class<?> clazz) {
        if (clazz != Object.class && instanceClass.isAssignableFrom(clazz)) {
            if (!scan(instance, processor, clazz.getSuperclass())) {
                return false;
            }

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                A[] annotations = field.getAnnotationsByType(annotationClass);
                
                for (A annotation : annotations) {
                    if (!processor.apply(annotation, field)) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
}
