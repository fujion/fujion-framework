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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.Treenode.TreenodeIterator;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

import java.util.Iterator;

/**
 * A component acting as a root for a hierarchical collection of nodes.
 */
@Component(
        tag = "treeview",
        widgetModule = "fujion-treeview",
        widgetClass = "Treeview",
        parentTag = "*",
        childTag = @ChildTag("treenode"),
        description = "A component acting as a root for a hierarchical collection of nodes.")
public class Treeview extends BaseUIComponent implements Iterable<Treenode>, ISupportsModel<Treenode> {

    private boolean showRoot;

    private boolean showLines = true;

    private boolean showToggles = true;

    private Treenode selectedNode;

    private final ModelAndView<Treenode, Object> modelAndView = new ModelAndView<>(this);

    /**
     * Returns true if the tree root should be visible. Otherwise, the visible tree starts with the
     * immediate children of the root.
     *
     * @return True if the tree root should be visible.
     */
    @PropertyGetter(value = "showRoot", description = "True if the tree root should be visible.")
    public boolean getShowRoot() {
        return showRoot;
    }

    /**
     * Set to true if the tree root should be visible. Otherwise, the visible tree starts with the
     * immediate children of the root.
     *
     * @param showRoot True if the tree root should be visible.
     */
    @PropertySetter(value = "showRoot", defaultValue = "false", description = "True if the tree root should be visible.")
    public void setShowRoot(boolean showRoot) {
        propertyChange("showRoot", this.showRoot, this.showRoot = showRoot, true);
    }

    /**
     * Returns true if lines connecting parent nodes to their children should be displayed.
     *
     * @return True if lines connecting parent nodes to their children should be displayed.
     */
    @PropertyGetter(value = "showLines", description = "True if lines connecting parent nodes to their children should be displayed.")
    public boolean getShowLines() {
        return showLines;
    }

    /**
     * Set to true to display lines connecting parent nodes to their children.
     *
     * @param showLines If true, lines connecting parent nodes to their children will be displayed.
     */
    @PropertySetter(value = "showLines", defaultValue = "true", description = "True if lines connecting parent nodes to their children should be displayed.")
    public void setShowLines(boolean showLines) {
        propertyChange("showLines", this.showLines, this.showLines = showLines, true);
    }

    /**
     * Returns true if expander icons should be displayed for each parent node with children.
     * Clicking an expander icon toggles between displaying (expanding) and hiding (collapsing) a
     * node's children.
     *
     * @return True if expander icons should be displayed.
     */
    @PropertyGetter(value = "showToggles", description = "True if expander icons should be displayed.")
    public boolean getShowToggles() {
        return showToggles;
    }

    /**
     * Set to true to display expander icons for each parent node with children. Clicking an
     * expander icon toggles between displaying (expanding) and hiding (collapsing) a node's
     * children.
     *
     * @param showToggles True if expander icons should be displayed.
     */
    @PropertySetter(value = "showToggles", defaultValue = "true", description = "True if expander icons should be displayed.")
    public void setShowToggles(boolean showToggles) {
        propertyChange("showToggles", this.showToggles, this.showToggles = showToggles, true);
    }

    /**
     * Collapses all nodes.
     */
    public void collapseAll() {
        expandOrCollapse(this, true);
    }

    /**
     * Expands all nodes.
     */
    public void expandAll() {
        expandOrCollapse(this, false);
    }

    private void expandOrCollapse(BaseComponent parent, boolean collapse) {
        for (BaseComponent child : parent.getChildren()) {
            ((Treenode) child).setCollapsed(collapse);
            expandOrCollapse(child, collapse);
        }
    }

    /**
     * Returns the currently selected node.
     *
     * @return The currently selected node.
     */
    public Treenode getSelectedNode() {
        return selectedNode;
    }

    /**
     * Sets the selected node.
     *
     * @param selectedNode The node to select.
     */
    public void setSelectedNode(Treenode selectedNode) {
        if (selectedNode == this.selectedNode) {
            return;
        }

        if (this.selectedNode != null) {
            this.selectedNode._setSelected(false, true, false);
        }

        this.selectedNode = selectedNode;

        if (selectedNode != null) {
            selectedNode._setSelected(true, true, false);
        }
    }

    /**
     * Clears the selection if the removed node or one of its descendants is the selected node.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child.isAncestor(selectedNode)) {
            selectedNode = null;
        }
    }

    /**
     * Sets the selection if the added node or one of its descendants is flagged as selected.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        Treenode selnode = findSelected((Treenode) child, null);

        if (selnode != null) {
            setSelectedNode(selnode);
        }
    }

    private Treenode findSelected(Treenode node, Treenode selnode) {
        if (node.isSelected()) {
            if (selnode != null) {
                selnode._setSelected(false, true, false);
            }

            selnode = node;
        }

        for (BaseComponent child : node.getChildren()) {
            selnode = findSelected((Treenode) child, selnode);
        }

        return selnode;
    }

    /**
     * Returns an iterator for all the nodes in this tree.
     *
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Treenode> iterator() {
        return new TreenodeIterator(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        modelAndView.destroy();
    }

    @Override
    public IModelAndView<Treenode, ?> getModelAndView() {
        return modelAndView;
    }

}
