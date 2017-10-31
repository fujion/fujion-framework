/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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

import java.util.List;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageParser;
import org.springframework.util.Assert;

/**
 * A component representing a Fujion resource that can be inserted into a template.
 *
 * @see Template
 */
@Component(tag = "snippet", widgetClass = "MetaWidget", parentTag = "template", description = "A Fujion resource that can be inserted into a template.")
public class Snippet extends BaseComponent {

    private enum AnchorPosition {
        BEFORE, // Add snippet as sibling before anchor
        AFTER, // Add snippet as sibling after anchor
        CHILD, // Add snippet as child of anchor
        PARENT, // Add snippet as new parent of anchor
        REPLACE // Replace anchor with snippet.
    }

    private String src;

    private String anchor;

    private AnchorPosition position = AnchorPosition.CHILD;

    public Snippet() {
    }

    /*package*/ void materialize(Template template) {
        Assert.isTrue(src != null && anchor != null, "A snippet requires both a src and an anchor");
        BaseComponent ref = template.findByName(anchor);
        Assert.notNull(ref, "Could not locate anchor for snippet at " + anchor);
        PageDefinition def = PageParser.getInstance().parse(src);
        BaseComponent parent = ref.getParent();
        int index = ref.getIndex();

        switch (position) {
            case CHILD:
                addToParent(ref, -1, def);
                break;

            case PARENT:
                ref.detach();
                BaseComponent newParent = addToParent(parent, index, def).get(0);
                ref.setParent(newParent);
                break;

            case REPLACE:
                ref.destroy();
                addToParent(parent, index, def);
                break;

            case BEFORE:
                addToParent(parent, index, def);
                break;

            case AFTER:
                addToParent(parent, index + 1, def);
                break;
        }
    }

    private List<BaseComponent> addToParent(BaseComponent parent, int index, PageDefinition def) {
        Assert.notNull(parent, "Anchor must have a parent for position value of " + position);
        List<BaseComponent> children = def.materialize(null);

        for (BaseComponent child : children) {
            parent.addChild(child, index);
            index = index < 0 ? index : index + 1;
        }

        return children;
    }

    /**
     * Sets the URL of the source FSP for this snippet.
     *
     * @param src The URL of the source FSP for this snippet.
     */
    @PropertySetter(value = "src", description = "The URL of the source FSP for this snippet.")
    private void setSrc(String src) {
        this.src = trimify(src);
    }

    /**
     * Sets the name of the anchor component within the template.
     *
     * @param anchor The name of the anchor component within the template.
     */
    @PropertySetter(value = "anchor", description = "The name of the anchor component within the template.")
    private void setAnchor(String anchor) {
        this.anchor = trimify(anchor);
    }

    /**
     * Sets the insertion point of the snippet relative to its anchor.
     *
     * @param position The insertion point of the snippet relative to its anchor.
     */
    @PropertySetter(value = "position", defaultValue = "child", description = "The insertion point of the snippet relative to its anchor.")
    private void setPosition(AnchorPosition position) {
        this.position = position == null ? AnchorPosition.CHILD : position;
    }
}
