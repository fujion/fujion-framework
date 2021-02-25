/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
package org.fujion.ace;

import org.fujion.ancillary.IResponseCallback;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseInputComponent;

import java.util.List;
import java.util.Map;

/**
 * Base class for Ace JavaScript editor components.
 */
@Component(tag = "ace", widgetModule = "fujion-ace", widgetClass = "AceEditor", parentTag = "*", description = "Fujion wrapper for Ace JavaScript editor.")
public class AceEditor extends BaseInputComponent<String> {

    private boolean readonly;

    private String mode;

    private boolean showGutter = true;

    private String theme;

    private boolean showCursor = true;

    private int padding = 0;

    private boolean animatedScroll;

    private boolean showPrintMargin;

    private boolean showInvisibles;

    /**
     * Scrolls the editor across both x- and y-axes.
     *
     * @param deltaX The x value to scroll by.
     * @param deltaY The y value to scroll by.
     */
    public void scrollBy(int deltaX, int deltaY) {
        invoke("scrollBy", deltaX, deltaY);
    }

    /**
     * Gracefully scrolls from the top of the editor to the row indicated.
     *
     * @param row The row to scroll to.
     */
    public void scrollToRow(int row) {
        invoke("scrollToRow", row);
    }

    /**
     * Scrolls the editor across the x-axis to the pixel indicated.
     *
     * @param xPosition The position to scroll to.
     */
    public void scrollToX(int xPosition) {
        invoke("scrollToX", xPosition);
    }

    /**
     * Scrolls the editor across the y-axis to the pixel indicated.
     *
     * @param yPosition The position to scroll to.
     */
    public void scrollToY(int yPosition) {
        invoke("scrollToY", yPosition);
    }

    /**
     * Performs a search using the specified options, returning the result (a Range) asynchronously.
     *
     * @param options The search options.
     * @param callback The callback to receive the results.
     */
    public void find(SearchOptions options, IResponseCallback<Range> callback) {
        invoke("find", callback, options);
    }

    /**
     * Performs a search using the specified options, returning the results (a list of Ranges) asynchronously.
     *
     * @param options The search options.
     * @param callback The callback to receive the results.
     */
    public void findAll(SearchOptions options, IResponseCallback<List<Range>> callback) {
        invoke("findAll", callback, options);
    }

    /**
     * Returns the animatedScroll parameter.
     *
     * @return True if animated scroll is enabled.
     */
    @PropertyGetter(value = "animatedScroll", description = "True if animated scrolling is enabled.")
    public boolean getAnimatedScroll() {
        return animatedScroll;
    }

    /**
     * Sets the animatedScroll parameter.
     *
     * @param animatedScroll  True if animated scroll is enabled.
     */
    @PropertySetter(value = "animatedScroll", defaultValue = "false", description = "True if animated scrolling is enabled.")
    public void setAnimatedScroll(boolean animatedScroll) {
        propertyChange("animatedScroll", this.animatedScroll, this.animatedScroll = animatedScroll, true);
    }

    /**
     * Returns true if the editor is set to read-only.
     *
     * @return True if read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if read-only.")
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * Set the read-only state of the editor.
     *
     * @param readonly The read-only state.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }

    /**
     * Returns the mode parameter.
     *
     * @return The mode parameter.
     */
    @PropertyGetter(value = "mode", description = "The syntax highlight mode.")
    protected String getMode() {
        return mode;
    }

    /**
     * Sets the mode parameter.
     *
     * @param mode The mode parameter.
     */
    @PropertySetter(value = "mode", description = "The syntax highlight mode.")
    protected void setMode(String mode) {
        propertyChange("mode", this.mode, this.mode = trimify(mode), true);
    }

    /**
     * Returns the padding parameter.
     *
     * @return The padding parameter.
     */
    @PropertyGetter(value = "padding", description = "The padding in pixels.")
    public int getPadding() {
        return padding;
    }

    /**
     * Sets the padding parameter.
     *
     * @param padding  The padding parameter.
     */
    @PropertySetter(value = "padding", defaultValue = "0", description = "The padding in pixels.")
    public void setPadding(int padding) {
        propertyChange("padding", this.padding, this.padding = padding, true);
    }

    /**
     * Returns the showCursor parameter.
     *
     * @return The showCursor parameter.
     */
    @PropertyGetter(value = "showCursor", description = "Whether or not to show the cursor icon.")
    public boolean getShowCursor() {
        return showCursor;
    }

    /**
     * Sets the showCursor parameter.
     *
     * @param showCursor If true, show the cursor icon.
     */
    @PropertySetter(value = "showCursor", defaultValue = "true", description = "Whether or not to show the cursor icon.")
    public void setShowCursor(boolean showCursor) {
        propertyChange("showCursor", this.showCursor, this.showCursor = showCursor, true);
    }

    /**
     * Returns the showGutter parameter.
     *
     * @return The showGutter parameter.
     */
    @PropertyGetter(value = "showGutter", description = "Controls display of left gutter.")
    public boolean getShowGutter() {
        return showGutter;
    }

    /**
     * Sets the showGutter parameter.
     *
     * @param showGutter The lineNumbers parameter.
     */
    @PropertySetter(value = "showGutter", defaultValue = "true", description = "Controls display of left gutter.")
    public void setShowGutter(boolean showGutter) {
        propertyChange("showGutter", this.showGutter, this.showGutter = showGutter, true);
    }

    /**
     * Returns the showInvisibles parameter.
     *
     * @return The showInvisibles parameter.
     */
    @PropertyGetter(value = "showInvisibles", description = "Controls display of invisible characters.")
    public boolean getShowInvisibles() {
        return showInvisibles;
    }

    /**
     * Sets the showInvisibles parameter.
     *
     * @param showInvisibles  The showInvisibles parameter.
     */
    @PropertySetter(value = "showInvisibles", defaultValue = "false", description = "Controls display of invisible characters.")
    public void setShowInvisibles(boolean showInvisibles) {
        propertyChange("showInvisibles", this.showInvisibles, this.showInvisibles = showInvisibles, true);
    }

    /**
     * Returns the showPrintMargin parameter.
     *
     * @return The showPrintMargin parameter.
     */
    @PropertyGetter(value = "showPrintMargin", description = "Controls display of print margin column.")
    public boolean getShowPrintMargin() {
        return showPrintMargin;
    }

    /**
     * Returns the showPrintMargin parameter.
     *
     * @param showPrintMargin  The showPrintMargin parameter.
     */
    @PropertySetter(value = "showPrintMargin", defaultValue = "false", description = "Controls display of print margin column.")
    public void setShowPrintMargin(boolean showPrintMargin) {
        propertyChange("showPrintMargin", this.showPrintMargin, this.showPrintMargin = showPrintMargin, true);
    }

    /**
     * Returns the theme used by the editor.
     *
     * @return The theme used by the editor.
     */
    @PropertyGetter(value = "theme", description = "Theme used by the editor.")
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme used by the editor.
     *
     * @param theme  The theme used by the editor.
     */
    @PropertySetter(value = "theme", description = "Theme used by the editor.")
    public void setTheme(String theme) {
        propertyChange("theme", this.theme, this.theme = theme, true);
    }

    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("wclazz", "fujion_ace");
    }

    @Override
    protected String _toValue(String value) {
        return value;
    }

    @Override
    protected String _toString(String value) {
        return value;
    }

}
