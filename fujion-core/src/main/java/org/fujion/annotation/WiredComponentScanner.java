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
package org.fujion.annotation;

import org.fujion.component.BaseComponent;

/**
 * Scans an object's class and superclasses for fields marked for wiring. Only fields that extend
 * BaseComponent are eligible for wiring.
 */
public class WiredComponentScanner extends AbstractFieldScanner<Object, WiredComponent> {

    private static final WiredComponentScanner instance = new WiredComponentScanner();
    
    private WiredComponentScanner() {
        super(Object.class, WiredComponent.class);
    }

    /**
     * Wire an object instance using the root component to resolve component names.
     *
     * @param object The object whose fields are to be scanned.
     * @param root The root component used to resolve component names.
     */
    public static void wire(Object object, BaseComponent root) {
        instance.scan(object, (annotation, field) -> {
            OnFailure onFailure = annotation.onFailure();

            if (!BaseComponent.class.isAssignableFrom(field.getType())) {
                onFailure.doAction("Field \"%s\" is not a component type", field.getName());
                return true;
            }

            try {
                if (!annotation.overwrite() && field.get(object) != null) {
                    onFailure.doAction("Field \"%s\" is already assigned a value", field.getName());
                    return true;
                }

                String name = annotation.value();
                name = name.isEmpty() ? field.getName() : name;
                BaseComponent component = root.findByName(name);

                if (component == null) {
                    onFailure.doAction("No component matching name \"%s\"", name);
                    return true;
                }

                field.set(object, component);
            } catch (Exception e) {
                onFailure.doAction(e);
            }

            return true;
        });
    }

}
