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
package org.fujion.testharness;

import java.util.ArrayList;
import java.util.List;

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.component.Checkbox;
import org.fujion.component.Treenode;
import org.fujion.component.Treeview;
import org.fujion.model.IListModel;
import org.fujion.model.NestedModel;

/**
 * Demonstration of tree view control.
 */
public class TreesController extends BaseController {

    private class TreeModelObject {

        final List<TreeModelObject> children = new ArrayList<>();

        final String label;

        TreeModelObject(String label) {
            this.label = label;
        }

    }

    private class TreeModel extends NestedModel<TreeModelObject> {

        public TreeModel(List<TreeModelObject> children) {
            super(children);
        }

        @Override
        public IListModel<TreeModelObject> getChildren(TreeModelObject parent) {
            return new TreeModel(parent.children);
        }

    }

    @WiredComponent
    private Treeview treeview;

    @WiredComponent
    private Treeview treeview2;

    @WiredComponent
    private Checkbox chkShowRoot;

    @WiredComponent
    private Checkbox chkShowLines;

    @WiredComponent
    private Checkbox chkShowToggles;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        TreeModelObject rootNode = new TreeModelObject(null);
        addChildren(rootNode, 1);
        treeview2.setModel(new TreeModel(rootNode.children));
        treeview2.setRenderer((TreeModelObject model) -> {
            Treenode node = new Treenode();
            node.setLabel(model.label);
            return node;
        });
    }

    private void addChildren(TreeModelObject parent, int level) {
        for (int i = 1; i < 4; i++) {
            String label = parent.label == null ? Integer.toString(i) : parent.label + "." + i;
            TreeModelObject child = new TreeModelObject(label);
            parent.children.add(child);

            if (level < 3) {
                addChildren(child, level + 1);
            }
        }
    }

    @EventHandler(value = "change", target = "@chkShowRoot")
    public void chkShowRootHandler() {
        treeview.setShowRoot(chkShowRoot.isChecked());
    }

    @EventHandler(value = "change", target = "@chkShowLines")
    public void chkShowLinesHandler() {
        treeview.setShowLines(chkShowLines.isChecked());
    }

    @EventHandler(value = "change", target = "@chkShowToggles")
    public void chkShowTogglesHandler() {
        treeview.setShowToggles(chkShowToggles.isChecked());
    }

    @EventHandler(value = "click", target = "btnCollapseAll")
    public void collapseAllHandler() {
        treeview.collapseAll();
    }

    @EventHandler(value = "click", target = "btnExpandAll")
    public void expandAllHandler() {
        treeview.expandAll();
    }

}
