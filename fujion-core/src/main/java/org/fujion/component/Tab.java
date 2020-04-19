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
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;

import java.util.function.BooleanSupplier;

/**
 * A single tab in a tab view.
 */
@Component(
        tag = "tab",
        widgetModule = "fujion-tabview",
        widgetClass = "Tab",
        content = ContentHandling.AS_CHILD,
        parentTag = "tabview",
        childTag = @ChildTag("*"),
        description = "A single tab in a tab view.")
public class Tab extends BaseLabeledImageComponent<BaseLabeledComponent.LabelPositionNone> {

    private boolean closable;

    private boolean selected;

    private BooleanSupplier onCanClose;

    private int badgeCounter;

    public Tab() {
        super();
    }

    public Tab(String label) {
        super(label);
    }

    /**
     * Returns true if the tab is closable. A closable tab has an icon that, when clicked, removes
     * and destroys the tab.
     *
     * @return True if the tab is closable.
     */
    @PropertyGetter(value = "closable", description = "True if the tab is closable. A closable tab has an icon that, when clicked, removes "
            + "and destroys the tab.")
    public boolean isClosable() {
        return closable;
    }

    /**
     * Set to true to make the tab closable. A tab that is closable has an icon that, when clicked,
     * triggers a close event.
     *
     * @param closable If true, the tab is closable.
     * @see #onCanClose
     */
    @PropertySetter(value = "closable", defaultValue = "false", description = "True if the tab is closable. A closable tab has an icon that, when clicked, removes "
            + "and destroys the tab.")
    public void setClosable(boolean closable) {
        propertyChange("closable", this.closable, this.closable = closable, true);
    }

    /**
     * Returns the selection state of the tab.
     *
     * @return The selection state of the tab.
     */
    @PropertyGetter(value = "selected", description = "The selection state of the tab.")
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selection state of the tab.
     *
     * @param selected The selection state of the tab.
     */
    @PropertySetter(value = "selected", defaultValue = "false", description = "The selection state of the tab.")
    public void setSelected(boolean selected) {
        _setSelected(selected, true);
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), true);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }

    /**
     * Handles close events from the client.
     *
     * @param event A close event.
     */
    @EventHandler(value = "close", syncToClient = false, mode = "init")
    private void _onClose(Event event) {
        close();
    }

    /**
     * Sets the tab's selected status.
     *
     * @param selected The new selected status.
     * @param notifyParent If true, notify the parent tab view of the status change.
     */
    protected void _setSelected(boolean selected, boolean notifyParent) {
        if (propertyChange("selected", this.selected, this.selected = selected, true)) {
            if (notifyParent && getParent() != null) {
                getTabview().setSelectedTab(selected ? this : null);
            }
        }
    }

    /**
     * Request the tab to be closed. Tab closure may be prevented if the onCanClose logic returns
     * false.
     *
     * @return True if the tab was closed.
     */
    public boolean close() {
        if (canClose()) {
            destroy();
            return true;
        }

        return false;
    }

    /**
     * Invokes the {@link #getOnCanClose canClose} logic and returns the result.
     *
     * @return The result of invoking the {@link #getOnCanClose canClose} logic.
     */
    public boolean canClose() {
        return onCanClose == null || onCanClose.getAsBoolean();
    }

    public Tabview getTabview() {
        return (Tabview) getParent();
    }

    /**
     * Returns the functional interface that determines whether tab closure is permitted.
     *
     * @return The functional interface that determines whether tab closure is permitted.
     */
    public BooleanSupplier getOnCanClose() {
        return onCanClose;
    }

    /**
     * Sets whether tab closure is permitted using a simple Boolean value. This is a shortcut for
     * calling {@link #setOnCanClose(BooleanSupplier)} with a functional interface that returns a
     * fixed Boolean value.
     *
     * @param canClose If true, the tab may be closed.
     */
    public void setOnCanClose(boolean canClose) {
        setOnCanClose(() -> canClose);
    }

    /**
     * Sets the functional interface that will determine if tab closure is permitted.
     *
     * @param onCanClose The functional interface that will determine if tab closure is permitted.
     */
    public void setOnCanClose(BooleanSupplier onCanClose) {
        this.onCanClose = onCanClose;
    }

    @Override
    public void bringToFront() {
        setSelected(true);
        super.bringToFront();
    }

    /**
     * Handles badge update events from the client.
     *
     * @param event A badge update event.
     */
    @EventHandler(value = "badge", mode = "init")
    private void _onBadge(Event event) {
        int delta = (Integer) event.getData();

        if (delta != 0) {
            badgeCounter += delta;
            sync("badge", badgeCounter);
        }
    }
}
