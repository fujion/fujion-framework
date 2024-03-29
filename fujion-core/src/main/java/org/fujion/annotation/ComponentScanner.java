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

import org.fujion.ancillary.ComponentRegistry;
import org.fujion.common.Logger;
import org.fujion.component.BaseComponent;

/**
 * Utility class for scanning class and method annotations and building component definitions from
 * them.
 */
public class ComponentScanner extends AbstractClassScanner<BaseComponent, Component> {

    private static final Logger log = Logger.create(ComponentScanner.class);

    private static final ComponentScanner instance = new ComponentScanner();

    /**
     * Returns a singleton instance of the component scanner.
     *
     * @return Singleton instance of the component scanner.
     */
    public static ComponentScanner getInstance() {
        return instance;
    }

    private ComponentScanner() {
        super(BaseComponent.class, Component.class);
    }

    /**
     * Creates and registers a component definition for a class by scanning the class and its
     * superclasses for method annotations.
     *
     * @param clazz Class to scan.
     */
    @Override
    protected void doScanClass(Class<BaseComponent> clazz) {
        log.debug(() -> "Processing @Component annotation for class " + clazz);
        ComponentDefinition def = new ComponentDefinition(clazz);
        MethodScanner.scan(clazz, method -> {
            def._addSetter(method);
            def._addGetter(method);
        });
        MethodScanner.scan(def.getFactoryClass(), def::_addFactoryParameter);
        ComponentRegistry.getInstance().register(def);
    }

}
