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
package org.fujion.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.fujion.annotation.ComponentDefinition;
import org.fujion.annotation.ComponentDefinition.Cardinality;
import org.fujion.taglib.TagLibrary;
import org.springframework.util.Assert;

/**
 * A single page element, roughly equivalent to a single tag in a Fujion server page.
 */
public class PageElement {

    private final PageElement parent;

    private final ComponentDefinition definition;

    private Map<String, String> attributes;

    private Map<String, TagLibrary> tagLibraries;

    private List<PageElement> children;

    private Map<String, Integer> childCounts;

    /*package*/ PageElement(ComponentDefinition definition, PageElement parent) {
        this.definition = definition;
        this.parent = parent;

        if (parent != null) {
            parent.addChild(this);
        }
    }

    private void addChild(PageElement child) {
        if (children == null) {
            children = new ArrayList<>();
            childCounts = new HashMap<>();
        }

        if (getDefinition() == null) {
            children.add(child);
            return;
        }

        String childTag = child.getDefinition().getTag();
        Integer count = childCounts.getOrDefault(childTag, 0);
        getDefinition().validateChild(child.getDefinition(), () -> count);
        children.add(child);
        childCounts.put(childTag, count + 1);
    }

    /**
     * Sets the value of a named attribute.
     *
     * @param name The attribute name.
     * @param value The new value.
     */
    /*package*/ void setAttribute(String name, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        attributes.put(name, value);
    }

    /**
     * Validates that the current state of the element possesses the minimum set of required
     * elements.
     */
    protected void validate() {
        if (children == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        Map<String, Cardinality> map = new HashMap<>(getDefinition().getChildTags());
        map.remove("*");

        for (Entry<String, Integer> child : childCounts.entrySet()) {
            int count = child.getValue();
            String tag = child.getKey();
            Cardinality cardinality = getDefinition().getCardinality(tag);
            map.remove(tag);

            if (!cardinality.isValid(count)) {
                build(sb, "The number of occurrences (%d) for tag '<%s>' falls outside the range of %d - %d.", count, tag,
                    cardinality.getMinimum(), cardinality.getMaximum());
            }
        }

        for (Entry<String, Cardinality> child : map.entrySet()) {
            if (child.getValue().getMinimum() > 0) {
                build(sb, "A required child tag '<%s>' is missing.", child.getKey());
            }
        }

        Assert.state(sb.length() == 0, () -> sb.toString());
    }

    private void build(StringBuilder sb, String format, Object... args) {
        sb.append(sb.length() == 0 ? "" : "\n").append(String.format(format, args));
    }

    /**
     * Returns the component definition for this page element.
     *
     * @return The component definition for this page element.
     */
    public ComponentDefinition getDefinition() {
        return definition;
    }

    /**
     * Returns the parent of this page element.
     *
     * @return The parent of this page element.
     */
    public PageElement getParent() {
        return parent;
    }

    /**
     * Returns a list of this page element's children.
     *
     * @return A list of this page element's children, never null.
     */
    public List<PageElement> getChildren() {
        return children == null ? Collections.emptyList() : Collections.unmodifiableList(children);
    }

    /**
     * Returns a copy of the attribute map.
     *
     * @return A copy of the attribute map, possibly null.
     */
    public Map<String, String> getAttributes() {
        return attributes == null ? null : new HashMap<>(attributes);
    }

    /**
     * Returns true if this is the root page element.
     *
     * @return True if this is the root page element.
     */
    public boolean isRoot() {
        return parent == null;
    }
    
    /**
     * Registers a tag library to this page element.
     *
     * @param prefix The tag library prefix.
     * @param tagLibrary The tag library.
     */
    public void addTagLibrary(String prefix, TagLibrary tagLibrary) {
        tagLibraries = tagLibraries == null ? new HashMap<>() : tagLibraries;
        tagLibraries.put(prefix, tagLibrary);
    }

    /**
     * Returns a tag library registered to this page element or one of its ancestors.
     *
     * @param prefix The tag library prefix.
     * @return The tag library, possibly null.
     */
    public TagLibrary getTagLibrary(String prefix) {
        TagLibrary lib = tagLibraries == null ? null : tagLibraries.get(prefix);
        return lib != null ? lib : parent == null ? null : parent.getTagLibrary(prefix);
    }
}
