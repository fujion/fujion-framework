/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.fujion.ancillary.IComposite;
import org.fujion.page.PageUtil;

/**
 * Base for creating components that are composites of other Fujion components.
 */
public abstract class BaseCompositeComponent extends BaseComponent implements IComposite {

    private BaseComponent root;

    private String anchor;

    private CompositePosition position = CompositePosition.LAST;

    public BaseCompositeComponent() {
    }

    public BaseCompositeComponent(String src, String anchor, CompositePosition position) {
        setCompositeSource(src);
        setCompositeAnchor(anchor);
        setCompositePosition(position);
    }
    
    @Override
    public BaseComponent getCompositeRoot() {
        return root;
    }
    
    /**
     * Sets the URL of the source FSP for this composite.
     *
     * @param src The URL of the source FSP for this composite.
     */
    protected void setCompositeSource(String src) {
        src = trimify(src);
        root = src == null ? null : PageUtil.createPage(src, null).get(0);
    }

    @Override
    public String getCompositeAnchor() {
        return anchor;
    }
    
    /**
     * Sets the name of the anchor component within the parent namespace.
     *
     * @param anchor The name of the anchor component within the parent namespace.
     */
    protected void setCompositeAnchor(String anchor) {
        this.anchor = trimify(anchor);
    }

    @Override
    public CompositePosition getCompositePosition() {
        return position;
    }
    
    /**
     * Sets the insertion point of the composite relative to its anchor.
     *
     * @param position The insertion point of the composite relative to its anchor.
     */
    protected void setCompositePosition(CompositePosition position) {
        this.position = position == null ? CompositePosition.LAST : position;
    }
    
    @Override
    public void addChild(BaseComponent child, int index) {
        root.addChild(child, index);
    }
}
