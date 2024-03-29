/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.*;
import org.fujion.component.Rows.Selectable;
import org.fujion.model.IComponentRenderer;
import org.fujion.model.ListModel;

import java.util.Comparator;

/**
 * Grid demonstration.
 */
public class GridsController extends BaseController {

    private static class RowModelObject implements Comparable<RowModelObject> {

        final String label;

        final int value;

        final int sequence;

        RowModelObject(int sequence) {
            this.sequence = sequence;
            this.label = RandomStringUtils.random(10, true, true);
            this.value = RandomUtils.nextInt();
        }

        @Override
        public int compareTo(RowModelObject o) {
            return sequence - o.sequence;
        }

    }

    private final IComponentRenderer<Row, RowModelObject> renderer = model -> {
        Row row = new Row();
        row.addChild(new Cell(model.label));
        row.addChild(new Cell(Integer.toString(model.value)));
        return row;
    };

    private final Comparator<RowModelObject> comp1 = (o1, o2) -> o1.label.compareToIgnoreCase(o2.label);

    private final Comparator<RowModelObject> comp2 = Comparator.comparingInt(o -> o.value);

    @WiredComponent
    private Grid grid;

    @WiredComponent
    private Rows rows;

    @WiredComponent
    private Column col1;

    @WiredComponent
    private Column col2;

    @WiredComponent
    private Radiogroup rgSelectable;

    @WiredComponent
    private Radiogroup rgSizable;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        col1.setSortComparator(comp1);
        col2.setSortComparator(comp2);
        ListModel<RowModelObject> model = new ListModel<>();

        for (int i = 1; i < 101; i++) {
            model.add(new RowModelObject(i));
        }

        rows.setModel(model);
        rows.setRenderer(renderer);
        col1.sort();
    }

    @EventHandler(value = "change", target = "@rgSelectable")
    private void rgSelectableChangeHandler() {
        Radiobutton rb = rgSelectable.getSelected();

        if (rb != null) {
            grid.getRows().setSelectable(Selectable.valueOf(rb.getLabel()));
        }
    }

    @EventHandler(value = "change", target = "@rgSizable")
    private void rgSizableChangeHandler() {
        Radiobutton rb = rgSizable.getSelected();

        if (rb != null) {
            boolean sizable = rb.getLabel().equals("YES");
            grid.setAutoSize(!sizable);
            col1.setSizable(sizable);
            col2.setSizable(sizable);
        }
    }
}
