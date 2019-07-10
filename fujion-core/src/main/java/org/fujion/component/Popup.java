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
package org.fujion.component;

import java.util.HashMap;
import java.util.Map;

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;

/**
 * A container component that be displayed at a selected location.
 */
@Component(
        tag = "popup",
        widgetClass = "Popup",
        content = ContentHandling.AS_CHILD,
        parentTag = "*",
        childTag = @ChildTag("*"),
        description = "A container component that be displayed at a selected location.")
public class Popup extends BaseComponent implements INamespace {
    
    /**
     * Opens the popup over the reference component.
     *
     * @param reference The reference component.
     */
    public void open(BaseComponent reference) {
        open(reference, null, null);
    }
    
    /**
     * Opens the popup relative to the reference component.
     *
     * @param reference The reference component.
     * @param my Reference position on the popup.
     * @param at Reference position on the reference component.
     */
    public void open(BaseComponent reference, String my, String at) {
        Map<String, Object> map = new HashMap<>();
        map.put("of", reference);
        map.put("at", at);
        map.put("my", my);
        invoke("open", map, true);
    }

    /**
     * Closes the popup.
     */
    public void close() {
        invoke("close", true);
    }
}
