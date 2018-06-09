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
package org.fujion.codemirror;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.Option;

/**
 * CodeMirror editor configured for XML support.
 */
@Component(tag = "codemirror_xml", widgetModule = "fujion-codemirror-xml", widgetClass = "CodeMirrorXML", parentTag = "*", description = "XML Extensions CodeMirror JavaScript editor.")
public class CodeMirrorXML extends CodeMirrorBase<CodeMirrorXML.XMLOptions> {
    
    protected static class XMLOptions extends CodeMirrorOptions {
        
        public XMLOptions() {
            super("xml");
        }
        
        @Option
        boolean autoCloseTags = true;

        @Option(value = "matchTags.bothTags")
        boolean matchTags = true;

        @Option(value = "extraKeys.${value}", convertUsing = "'toMatchingTag'")
        final String jumpShortcut = "Alt-J";
        
        @Option(value = "hintOptions")
        SchemaInfo schemaInfo;
    }
    
    /**
     * Represents a single XML tag with its attributes and children.
     */
    public static class Tag extends Options {
        
        @Option
        private final Map<String, String[]> attrs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        @Option
        private final Set<String> children = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        /**
         * Add an attribute with optional value constraints.
         *
         * @param name The attribute name.
         * @param values Optional list of value constraints.
         * @return The original tag (for chaining).
         */
        public Tag addAttribute(String name, String... values) {
            Arrays.sort(values);
            attrs.put(name, values);
            return this;
        }

        /**
         * Add zero or more children.
         *
         * @param children List of children for this tag.
         * @return The original tag (for chaining).
         */
        public Tag addChildren(String... children) {
            for (String child : children) {
                this.children.add(child);
            }
            return this;
        }

        /**
         * Remove all attributes and children.
         *
         * @return The original tag (for chaining).
         */
        public Tag clear() {
            attrs.clear();
            children.clear();
            return this;
        }

        /**
         * Copy the attributes and children of one tag to this one, removing any existing values.
         *
         * @param tag The tag to copy from.
         * @return The original tag (for chaining).
         */
        public Tag copy(Tag tag) {
            clear();
            attrs.putAll(tag.attrs);
            children.addAll(tag.children);
            return this;
        }
    }

    /**
     * Represents the collection of tags that comprise a schema. This resolves to the format
     * expected by the CodeMirror XML add-on.
     */
    public static class SchemaInfo extends Options {

        @Option
        private final Map<String, Tag> schemaInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        private final Tag root = new Tag();

        @Option("schemaInfo.!top")
        private final Set<String> top = root.children;

        @Option("schemaInfo.!attrs")
        private final Map<String, String[]> attrs = root.attrs;

        /**
         * Add a tag if one does not already exist.
         *
         * @param tagName The tag name.
         * @return The tag corresponding to the specified name.
         */
        public Tag addTag(String tagName) {
            Tag tag = getTag(tagName);
            return tag != null ? tag : addTag(tagName, new Tag());
        }

        public Tag addTag(String tagName, Tag tag) {
            Tag atag = getTag(tagName);
            
            if (atag == null) {
                schemaInfo.put(tagName, tag);
            } else {
                tag = atag.copy(tag);
            }

            return tag;
        }
        
        /**
         * Returns the tag associated with the specified name.
         *
         * @param tagName The tag name. A null tag name returns the root tag.
         * @return The associated tag, possibly null.
         */
        public Tag getTag(String tagName) {
            return tagName == null ? root : schemaInfo.get(tagName);
        }

        /**
         * Clears all schema information.
         */
        public void clear() {
            root.clear();
            schemaInfo.clear();
        }
        
    }

    public CodeMirrorXML() {
        super(new XMLOptions());
    }

    /**
     * Returns the autoCloseTags setting. If true, the editor will automatically generate closing
     * tags.
     *
     * @return The autoCloseTags setting.
     */
    @PropertyGetter(value = "autoCloseTags", bindable = false, description = "If true, automatically generate closing XML tags.")
    public boolean getAutoCloseTags() {
        return options.autoCloseTags;
    }

    /**
     * Sets the autoCloseTags setting. If true, the editor will automatically generate closing tags.
     *
     * @param autoCloseTags The autoCloseTags setting.
     */
    @PropertySetter(value = "autoCloseTags", bindable = false, defaultValue = "true", description = "If true, automatically generate closing XML tags.")
    public void setAutoCloseTags(boolean autoCloseTags) {
        if (propertyChange("autoCloseTags", options.autoCloseTags, options.autoCloseTags = autoCloseTags, false)) {
            refreshOptions();
        }
    }

    /**
     * Returns the matchTags setting. If true, the editor will highlight matching tags.
     *
     * @return The matchTags setting.
     */
    @PropertyGetter(value = "matchTags", bindable = false, description = "If true, highlight matching tag.")
    public boolean getMatchTags() {
        return options.matchTags;
    }

    /**
     * Sets the matchTags setting. If true, the editor will highlight matching tags.
     *
     * @param matchTags The matchTags setting.
     */
    @PropertySetter(value = "matchTags", bindable = false, defaultValue = "true", description = "If true, highlight matching tag.")
    public void setMatchTags(boolean matchTags) {
        if (propertyChange("matchTags", options.matchTags, options.matchTags = matchTags, false)) {
            refreshOptions();
        }
    }
    
    /**
     * Sets schema information.
     *
     * @param schemaInfo The schema information.
     */
    public void setSchemaInfo(SchemaInfo schemaInfo) {
        options.schemaInfo = schemaInfo;
        refreshOptions();
    }
}
