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
package org.fujion.annotation;

import java.lang.reflect.Method;

/**
 * Utility class to facilitate scanning methods for annotations. Scans superclasses and interfaces,
 * presenting each method for processing by a message processor.
 */
public class MethodScanner {

    public interface IMethodProcessor {
        
        void process(Method method);
    }

    /**
     * Scans a class for method annotations, processing each.
     *
     * @param clazz The class to be scanned.
     * @param processor The method processor.
     */
    public static void scan(Class<?> clazz, IMethodProcessor processor) {
        if (clazz == null || clazz == Object.class) {
            return;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            if (!method.isSynthetic() && !method.isBridge()) {
                processor.process(method);
            }
        }

        for (Class<?> intf : clazz.getInterfaces()) {
            scan(intf, processor);
        }

        scan(clazz.getSuperclass(), processor);
    }

}
