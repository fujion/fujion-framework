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
package org.fujion.ancillary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.fujion.annotation.ComponentDefinition;
import org.fujion.common.AbstractRegistry;
import org.fujion.common.MiscUtil;
import org.fujion.common.RegistryMap.DuplicateAction;
import org.fujion.component.BaseComponent;

/**
 * Registry of component definitions indexed by their tag name and implementing class.
 */
public class ComponentRegistry extends AbstractRegistry<String, ComponentDefinition> {

    private static final ComponentRegistry instance = new ComponentRegistry();

    private final Map<Class<? extends BaseComponent>, ComponentDefinition> classToDefinition = new HashMap<>();

    /**
     * Returns the singleton instance of the component registry.
     *
     * @return Component registry instance.
     */
    public static ComponentRegistry getInstance() {
        return instance;
    }

    private ComponentRegistry() {
        super(DuplicateAction.ERROR);
    }

    /**
     * Adds a component definition to the registry.
     *
     * @param item Item to add.
     */
    @Override
    public void register(ComponentDefinition item) {
        super.register(item);
        classToDefinition.put(item.getComponentClass(), item);
    }

    @Override
    protected String getKey(ComponentDefinition item) {
        return item.getTag();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public ComponentDefinition unregisterByKey(String key) {
        return classToDefinition.remove(super.unregisterByKey(key));
    }

    /**
     * Returns a component definition given a component class. If no direct association occurs for
     * the specified class, will check each ancestor class in turn.
     *
     * @param componentClass The component class.
     * @return The associated component definition, or null if none found.
     */
    public ComponentDefinition get(Class<? extends BaseComponent> componentClass) {
        ComponentDefinition def = null;
        Class<?> clazz = componentClass;

        do {
            def = classToDefinition.get(clazz);
        } while (def == null && BaseComponent.class.isAssignableFrom((clazz = clazz.getSuperclass())));

        return def;
    }
    
    /**
     * Returns a list of all component definitions whose tags match the specified pattern.
     *
     * @param pattern A glob pattern for matching tags.
     * @return A list of matching component definitions (never null).
     */
    public Collection<ComponentDefinition> getMatching(String pattern) {
        if ("*".equals(pattern)) {
            return Collections.unmodifiableCollection(map.values());
        }
        
        ComponentDefinition def = get(pattern);
        
        if (def != null) {
            return Collections.singleton(def);
        }
        
        List<ComponentDefinition> matches = new ArrayList<>();
        Pattern regex = MiscUtil.globToRegex(pattern);
        
        for (String tag : map.keySet()) {
            if (regex.matcher(tag).matches()) {
                matches.add(map.get(tag));
            }
        }
        
        return matches;
    }

}
