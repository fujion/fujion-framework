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

import org.apache.commons.lang.BooleanUtils;
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
        Boolean htmlMode;
        
        @Option
        Boolean matchClosing;
        
        @Option
        Boolean alignCDATA;
        
        @Option
        Boolean autoCloseTags = true;
        
        @Option(value = "matchTags.bothTags")
        Boolean matchTags = true;
        
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
    
    private static final String MATCHING_TAG_COMMAND = "toMatchingTag";
    
    private boolean autoComplete = true;
    
    private String jumpShortcut;
    
    public CodeMirrorXML() {
        super(new XMLOptions());
        setJumpShortcut("Alt-J");
    }
    
    /**
     * Returns the autoCloseTags setting. If true, the editor will automatically generate closing
     * tags.
     *
     * @return The autoCloseTags setting.
     */
    @PropertyGetter(value = "autoCloseTags", bindable = false, description = "If true, automatically generate closing XML tags.")
    public Boolean getAutoCloseTags() {
        return options.autoCloseTags;
    }
    
    /**
     * Sets the autoCloseTags setting. If true, the editor will automatically generate closing tags.
     *
     * @param autoCloseTags The autoCloseTags setting.
     */
    @PropertySetter(value = "autoCloseTags", bindable = false, defaultValue = "true", description = "If true, automatically generate closing XML tags.")
    public void setAutoCloseTags(Boolean autoCloseTags) {
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
     * Returns the autoComplete setting. If true, the editor will present context-sensitive,
     * auto-completion options after certain keystrokes. If false, auto-completion must be invoked
     * manually.
     *
     * @return The autoComplete setting.
     */
    @PropertyGetter(value = "autoComplete", bindable = false, description = "If true, autocompletion mode is enabled.")
    public boolean getAutoComplete() {
        return autoComplete;
    }
    
    /**
     * Sets the autoComplete setting. If true, the editor will present context-sensitive,
     * auto-completion options after certain keystrokes. If false, auto-completion must be invoked
     * manually.
     *
     * @param autoComplete The autoComplete setting.
     */
    @PropertySetter(value = "autoComplete", bindable = false, defaultValue = "true", description = "If true, autocompletion mode is enabled.")
    public void setAutoComplete(boolean autoComplete) {
        propertyChange("autoComplete", this.autoComplete, this.autoComplete = autoComplete, true);
    }
    
    /**
     * Returns the HTML mode setting. If true, attributes do not have to be quoted, and some
     * elements (such as &lt;br&gt;) do not require a closing tag.
     *
     * @return The HTML mode setting.
     */
    @PropertyGetter(value = "htmlMode", bindable = false, description = "If true, sets the mode to parse HTML instead of XML. This means attributes do not have to be quoted, and some elements (such as br) do not require a closing tag.")
    public boolean isHtmlMode() {
        return BooleanUtils.isTrue(options.htmlMode);
    }
    
    /**
     * Sets the HTML mode setting. If true, attributes do not have to be quoted, and some elements
     * (such as &lt;br&gt;) do not require a closing tag.
     *
     * @param htmlMode The HTML mode setting.
     */
    @PropertySetter(value = "htmlMode", bindable = false, defaultValue = "false", description = "If true, sets the mode to parse HTML instead of XML. This means attributes do not have to be quoted, and some elements (such as br) do not require a closing tag.")
    public void setHtmlMode(Boolean htmlMode) {
        if (propertyChange("htmlMode", options.htmlMode, options.htmlMode = htmlMode ? htmlMode : null, false)) {
            refreshOptions();
        }
    }
    
    /**
     * Returns the matchClosing setting which controls whether the mode checks that close tags match
     * the corresponding opening tag, and highlights mismatches as errors.
     *
     * @return The matchClosing setting.
     */
    @PropertyGetter(value = "matchClosing", bindable = false, description = "Controls whether the mode checks that close tags match the corresponding opening tag, and highlights mismatches as errors.")
    public boolean getMatchClosing() {
        return !BooleanUtils.isFalse(options.matchClosing);
    }
    
    /**
     * Sets the matchClosing setting which controls whether the mode checks that close tags match
     * the corresponding opening tag, and highlights mismatches as errors.
     *
     * @param matchClosing The matchClosing setting.
     */
    @PropertySetter(value = "matchClosing", bindable = false, defaultValue = "true", description = "Controls whether the mode checks that close tags match the corresponding opening tag, and highlights mismatches as errors. ")
    public void setMatchClosing(boolean matchClosing) {
        if (propertyChange("matchClosing", options.matchClosing, options.matchClosing = matchClosing ? null : matchClosing,
                false)) {
            refreshOptions();
        }
    }
    
    /**
     * Returns the alignCDATA setting. When true, the opening tag of CDATA blocks to not be
     * indented.
     *
     * @return The alignCDATA setting.
     */
    @PropertyGetter(value = "alignCDATA", bindable = false, description = "Setting this to true will force the opening tag of CDATA blocks to not be indented.")
    public boolean getAlignCDATA() {
        return BooleanUtils.isTrue(options.alignCDATA);
    }
    
    /**
     * Sets the alignCDATA setting. When true, the opening tag of CDATA blocks to not be indented.
     *
     * @param alignCDATA The alignCDATA setting.
     */
    @PropertySetter(value = "alignCDATA", bindable = false, defaultValue = "false", description = "Setting this to true will force the opening tag of CDATA blocks to not be indented.")
    public void setAlignCDATA(boolean alignCDATA) {
        if (propertyChange("alignCDATA", options.alignCDATA, options.alignCDATA = alignCDATA ? alignCDATA : null, false)) {
            refreshOptions();
        }
    }
    
    /**
     * Returns the key combination that will jump to the tag matching the one currently under the
     * cursor. Setting to null will disable this feature.
     *
     * @return Key combination to jump to matching tag.
     */
    @PropertyGetter(value = "jumpShortcut", bindable = false, description = "Key combination to jump to matching tag.")
    public String getJumpShortcut() {
        return jumpShortcut;
    }
    
    /**
     * Sets the key combination that will jump to the tag matching the one currently under the
     * cursor. Setting to null will disable this feature.
     *
     * @param jumpShortcut Key combination to jump to matching tag.
     */
    @PropertySetter(value = "jumpShortcut", bindable = false, defaultValue = "Alt-J", description = "Key combination to jump to matching tag.")
    public void setJumpShortcut(String jumpShortcut) {
        String oldValue = this.jumpShortcut;
        XMLOptions options = this.getOptions();
        
        if (propertyChange("jumpShortcut", this.jumpShortcut, this.jumpShortcut = trimify(jumpShortcut), false)) {
            if (oldValue != null && MATCHING_TAG_COMMAND.equals(options.getKeyBinding(oldValue))) {
                options.removeKeyBinding(oldValue);
            }
            
            if (jumpShortcut != null) {
                options.addKeyBinding(jumpShortcut, MATCHING_TAG_COMMAND);
            }

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
