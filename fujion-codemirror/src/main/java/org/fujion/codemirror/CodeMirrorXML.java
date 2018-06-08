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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.Option;

@Component(tag = "codemirror_xml", widgetModule = "fujion-codemirror-xml", widgetClass = "CodeMirrorXML", parentTag = "*", description = "XML Extensions CodeMirror JavaScript editor.")
public class CodeMirrorXML extends CodeMirrorBase<CodeMirrorXML.XMLOptions> {

    protected static class XMLOptions extends CodeMirrorBase.CodeMirrorOptions {

        public XMLOptions() {
            super("xml");
        }

        @Option
        private boolean autoCloseTags = true;
        
        @Option(value = "matchTags.bothTags")
        private boolean matchTags = true;
        
        @Option(value = "extraKeys.${value}", convertUsing = "'toMatchingTag'")
        private final String jumpShortcut = "Alt-J";

        @Option(value = "hintOptions")
        private SchemaInfo schemaInfo;
    }

    public static class Tag extends Options {

        @Option
        private final Map<String, String[]> attrs = new TreeMap<>();
        
        @Option
        private final Set<String> children = new TreeSet<>();
        
        public Tag addAttribute(String name, String... values) {
            attrs.put(name, values);
            return this;
        }
        
        public Tag addChildren(String... children) {
            for (String child : children) {
                this.children.add(child);
            }
            return this;
        }
        
        public Tag clear() {
            attrs.clear();
            children.clear();
            return this;
        }
        
        public Tag copy(Tag tag) {
            clear();
            attrs.putAll(tag.attrs);
            children.addAll(tag.children);
            return this;
        }
    }
    
    public static class SchemaInfo extends Options {
        
        @Option
        private final Map<String, Tag> schemaInfo = new TreeMap<>();
        
        private final Tag root = new Tag();
        
        @Option("schemaInfo.!top")
        private final Set<String> top = root.children;
        
        @Option("schemaInfo.!attrs")
        private final Map<String, String[]> attrs = root.attrs;
        
        public Tag addTag(String name) {
            if (name == null) {
                return root;
            } else {
                return addTag(name, new Tag());
            }
        }
        
        public Tag addTag(String name, Tag tag) {
            if (name == null) {
                root.copy(tag);
                return root;
            } else {
                schemaInfo.put(name, tag);
                return tag;
            }
        }
        
        public void clear() {
            root.clear();
            schemaInfo.clear();
        }

    }
    
    public CodeMirrorXML() {
        super(new XMLOptions());
    }
    
    @PropertyGetter(value = "autoCloseTags", bindable = false, description = "If true, automatically generate closing XML tags.")
    public boolean getAutoCloseTags() {
        return options.autoCloseTags;
    }
    
    @PropertySetter(value = "autoCloseTags", bindable = false, defaultValue = "true", description = "If true, automatically generate closing XML tags.")
    public void setAutoCloseTags(boolean autoCloseTags) {
        options.autoCloseTags = autoCloseTags;
        optionsUpdated();
    }
    
    @PropertyGetter(value = "matchTags", bindable = false, description = "If true, highlight matching tag.")
    public boolean getMatchTags() {
        return options.matchTags;
    }
    
    @PropertySetter(value = "matchTags", bindable = false, defaultValue = "true", description = "If true, highlight matching tag.")
    public void setMatchTags(boolean matchTags) {
        options.matchTags = matchTags;
        optionsUpdated();
    }

    public void setSchemaInfo(SchemaInfo schemaInfo) {
        options.schemaInfo = schemaInfo;
        optionsUpdated();
    }
}
