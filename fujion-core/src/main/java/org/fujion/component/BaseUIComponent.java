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

import org.fujion.ancillary.CssClasses;
import org.fujion.ancillary.CssStyles;
import org.fujion.ancillary.IDisable;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.event.KeyCode;

/**
 * The base class from which all UI components derive.
 */
public abstract class BaseUIComponent extends BaseComponent implements IDisable {

    private final CssStyles styles = new CssStyles();

    private final CssClasses classes = new CssClasses();

    private String height;

    private String width;

    private String flex;

    private String hint;

    private String balloon;

    private boolean disabled;

    private boolean visible = true;

    private int tabindex;

    private String css;

    private String dragid;

    private String dropid;

    private Popup context;

    private Popup popup;

    private String keycapture;

    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     */
    public void addMask() {
        addMask(null);
    }

    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     *
     * @param label Text to display in the center of the mask.
     */
    public void addMask(String label) {
        invoke("addMask", label);
    }

    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     *
     * @param label Text to display in the center of the mask.
     * @param popup Popup to display when right-clicking on the mask.
     */
    public void addMask(String label, Popup popup) {
        invoke("addMask", label, popup);
    }

    /**
     * Removes the mask, if present.
     */
    public void removeMask() {
        invoke("removeMask");
    }

    /**
     * Returns the styles set for this component.
     *
     * @return The styles set for this component.
     */
    @PropertyGetter(value = "style", description = "The active CSS styles for this component.")
    public String getStyles() {
        return styles.toString();
    }

    /**
     * Sets the styles for this component, replacing any existing styles.
     *
     * @param styles Styles to set, represented as a string in the same format as the HTML style
     *            attribute.
     */
    @PropertySetter(value = "style", description = "The active CSS styles for this component.")
    public void setStyles(String styles) {
        String oldStyles = this.styles.toString();
        this.styles.parse(styles, true);
        _syncStyles(oldStyles);
    }

    private String _syncStyle(String name, String dflt) {
        String current = styles.get(name);

        if (current != null) {
            return current;
        }

        styles.put(name, dflt);
        return dflt;
    }

    /**
     * Synchronizes style properties with stored styles and with the client.
     *
     * @param oldStyles Previous styles value.
     */
    protected void _syncStyles(String oldStyles) {
        height = _syncStyle("height", height);
        width = _syncStyle("width", width);
        flex = _syncStyle("flex", flex);
        propertyChange("style", oldStyles, styles.toString(), true);
    }

    /**
     * Returns the value of the named style.
     *
     * @param name The style name (e.g., "color").
     * @return The value of the style, or null if no value set for the style.
     */
    public String getStyle(String name) {
        return styles.get(name);
    }

    /**
     * Adds a style to existing styles. If that style already exists, it will be overwritten.
     *
     * @param name The style name (e.g., "color").
     * @param value The style value.
     * @return The previous value of the style, or null if none.
     */
    public String addStyle(String name, String value) {
        String oldStyles = this.styles.toString();
        String oldValue = styles.put(name, value);
        _syncStyles(oldStyles);
        return oldValue;
    }

    /**
     * Adds styles specified as a string.
     *
     * @param styles Styles to add, represented as a string in the same format as the HTML style
     *            attribute.
     */
    public void addStyles(String styles) {
        String oldStyles = this.styles.toString();
        this.styles.parse(styles, false);
        _syncStyles(oldStyles);
    }

    /**
     * Removes a style.
     *
     * @param name The style name (e.g., "color").
     * @return The value of the removed style, or null if none.
     */
    public String removeStyle(String name) {
        return addStyle(name, null);
    }

    /**
     * Returns the CSS classes set for this component.
     *
     * @return Space-delimited list of CSS classes.
     */
    @PropertyGetter(value = "class", description = "Space-delimited list of CSS classes.")
    public String getClasses() {
        return classes.toString();
    }

    /**
     * Sets the CSS classes for this component, replacing any existing classes.
     *
     * @param classes Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    public void setClasses(String classes) {
        String oldClasses = classes.toString();
        this.classes.parse(classes);
        _syncClasses(oldClasses);
    }

    /**
     * Synchronize class settings with the client.
     *
     * @param oldClasses Previous value for classes.
     */
    protected void _syncClasses(String oldClasses) {
        propertyChange("clazz", oldClasses, classes.toString(true), true);
    }

    /**
     * Adds one or more classes to existing classes.
     *
     * @param value Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    @PropertySetter(value = "class", description = "Space-delimited list of CSS classes.")
    public void addClass(String value) {
        String oldClasses = classes.toString();

        if (classes.add(value)) {
            _syncClasses(oldClasses);
        }
    }

    /**
     * Removes one or more classes from existing classes.
     *
     * @param value Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    public void removeClass(String value) {
        String oldClasses = classes.toString();

        if (classes.remove(value)) {
            _syncClasses(oldClasses);
        }
    }

    /**
     * Toggles the presence of two mutually exclusive classes based on a condition. Extended syntax
     * is supported for each of the class specifiers (see {@link org.fujion.ancillary.CssClasses})
     *
     * @param yesValue Classes to be added if the condition is true, or removed if false.
     * @param noValue Classes to be added if the condition is false, or removed if true.
     * @param condition The condition value.
     */
    public void toggleClass(String yesValue, String noValue, boolean condition) {
        String oldClasses = classes.toString();
        
        if (classes.toggle(yesValue, noValue, condition)) {
            _syncClasses(oldClasses);
        }
    }

    /**
     * Hides the component - a shortcut for {@link #setVisible setVisible(false)}.
     */
    public void hide() {
        setVisible(false);
    }

    /**
     * Shows the component - a shortcut for {@link #setVisible setVisible(true)}.
     */
    public void show() {
        setVisible(true);
    }

    /**
     * Returns the height. This is synchronized with the height style setting.
     *
     * @return The height.
     */
    @PropertyGetter(value = "height", description = "The component's height.")
    public String getHeight() {
        return height;
    }

    /**
     * Sets the height. This is synchronized with the height style setting.
     *
     * @param height The height.
     */
    @PropertySetter(value = "height", description = "The component's height.")
    public void setHeight(String height) {
        height = trimify(height);

        if (!areEqual(height, this.height)) {
            this.height = height;
            addStyle("height", height);
        }
    }

    /**
     * Returns the width. This is synchronized with the width style setting.
     *
     * @return The width.
     */
    @PropertyGetter(value = "width", description = "The component's width.")
    public String getWidth() {
        return width;
    }

    /**
     * Sets the width. This is synchronized with the width style setting.
     *
     * @param width The width.
     */
    @PropertySetter(value = "width", description = "The component's width.")
    public void setWidth(String width) {
        width = trimify(width);

        if (!areEqual(width, this.width)) {
            this.width = width;
            addStyle("width", width);
        }
    }

    /**
     * Returns the flex setting. This is synchronized with the flex style setting.
     *
     * @return The flex setting.
     */
    @PropertyGetter(value = "flex", description = "The component's flex style setting.")
    public String getFlex() {
        return flex;
    }

    /**
     * Sets the flex setting. This is synchronized with the flex style setting.
     *
     * @param flex The flex setting.
     */
    @PropertySetter(value = "flex", description = "The component's flex style setting.")
    public void setFlex(String flex) {
        flex = trimify(flex);

        if (!areEqual(flex, this.flex)) {
            this.flex = flex;
            addStyle("flex", flex);
        }
    }

    /**
     * Sets/removes the input focus to/from this component. If the component cannot receive the
     * input focus, the request is ignored.
     *
     * @param focus If true, the component receives the input focus if possible. If false and the
     *            component currently has the input focus, the focus is relinquished.
     */
    @PropertySetter(value = "focus", defaultValue = "false", defer = true, description = "Sets or removes focus for this component.")
    public void setFocus(boolean focus) {
        invoke("focus", focus);
    }

    /**
     * Sets the input focus to this component. If the component cannot receive the input focus, the
     * request is ignored. This is a shortcut for <code>setFocus(true)</code>.
     */
    public void focus() {
        setFocus(true);
    }

    /**
     * Returns the CSS specifier for this component. This is similar to an embedded style sheet, but
     * is associated with this component and is added to or removed from the client as this
     * component is added or removed. Moreover, EL expressions can be used in the selector to
     * reference this component or one of its subcomponents by their id. For example,
     *
     * <pre>
     *      ##{id}&gt;.inline{font-style:italic; background: white}
     * </pre>
     *
     * @return The CSS specifier.
     */
    @PropertyGetter(value = "css", description = "The CSS specifier for this component.")
    public String getCss() {
        return css;
    }

    /**
     * Sets the CSS specifier for this component. This is similar to an embedded style sheet, but is
     * associated with this component and is added to or removed from the client as this component
     * is added or removed. Moreover, EL expressions can be used in the selector to reference this
     * component or one of its subcomponents by their id. For example,
     *
     * <pre>
     *      ##{id}&gt;.inline{font-style:italic; background: white}
     * </pre>
     *
     * @param css The CSS specifier.
     */
    @PropertySetter(value = "css", description = "The CSS specifier for this component.")
    public void setCss(String css) {
        propertyChange("css", this.css, this.css = nullify(css), true);
    }

    /**
     * Causes the component to report its current dimensions and relative position via a
     * {@link org.fujion.event.ResizeEvent resize event}.
     */
    public void reportSize() {
        invoke("reportSize");
    }

    /**
     * Returns the popup text to be displayed when hovering over this component.
     *
     * @return The hint text.
     */
    @PropertyGetter(value = "hint", description = "The popup text to be displayed when hovering over this component.")
    public String getHint() {
        return hint;
    }

    /**
     * Sets the popup text to be displayed when hovering over this component.
     *
     * @param hint The hint text.
     */
    @PropertySetter(value = "hint", description = "The popup text to be displayed when hovering over this component.")
    public void setHint(String hint) {
        propertyChange("hint", this.hint, this.hint = nullify(hint), true);
    }

    /**
     * Returns the balloon text to be displayed adjacent to the component.
     *
     * @return The balloon text.
     */
    @PropertyGetter(value = "balloon", description = "The balloon text to be displayed adjacent to the component.")
    public String getBalloon() {
        return balloon;
    }

    /**
     * Sets the balloon text to be displayed adjacent to the component.
     *
     * @param balloon The balloon text.
     */
    @PropertySetter(value = "balloon", description = "The balloon text to be displayed adjacent to the component.")
    public void setBalloon(String balloon) {
        propertyChange("balloon", this.balloon, this.balloon = nullify(balloon), true);
    }

    @Override
    @PropertyGetter(value = "disabled", description = "True if the component is disabled.")
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    @PropertySetter(value = "disabled", defaultValue = "false", description = "True if the component is disabled.")
    public void setDisabled(boolean disabled) {
        propertyChange("disabled", this.disabled, this.disabled = disabled, true);
    }

    /**
     * Returns the visibility state of the component.
     *
     * @return The visibility state of the component.
     */
    @PropertyGetter(value = "visible", description = "The visibility state of the component.")
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility state of the component.
     *
     * @param visible The visibility state of the component.
     */
    @PropertySetter(value = "visible", defaultValue = "true", description = "The visibility state of the component.")
    public void setVisible(boolean visible) {
        propertyChange("visible", this.visible, this.visible = visible, true);
    }

    /**
     * Returns the tab index for the component.
     *
     * @return The tab index.
     */
    @PropertyGetter(value = "tabindex", description = "The tab index for the component.")
    public int getTabindex() {
        return tabindex;
    }

    /**
     * Sets the tab index for the component.
     *
     * @param tabindex The tab index.
     */
    @PropertySetter(value = "tabindex", defaultValue = "0", description = "The tab index for the component.")
    public void setTabindex(int tabindex) {
        propertyChange("tabindex", this.tabindex, this.tabindex = tabindex < 0 ? 0 : tabindex, true);
    }

    /**
     * Returns a space-delimited list of drag id's associated with this component. A non-empty value
     * enables dragging of the component. A dragged component may be dropped upon a target component
     * whose drop id(s) matches at least one of the dragged component's drag id's. A drag id value
     * of "*" will match any drop id.
     *
     * @return The drag id(s).
     */
    @PropertyGetter(value = "dragid", description = "A space-delimited list of drag id's associated with this component.")
    public String getDragid() {
        return dragid;
    }

    /**
     * Sets the drag id(s) to be associated with this component.
     *
     * @param dragid A space-delimited list of drag id's to be associated with this component. A
     *            non-empty value enables dragging of the component. A dragged component may be
     *            dropped upon a target component whose drop id(s) matches at least one of the drag
     *            id's. A drag id value of "*" will match any drop id.
     */
    @PropertySetter(value = "dragid", description = "A space-delimited list of drag id's associated with this component.")
    public void setDragid(String dragid) {
        propertyChange("dragid", this.dragid, this.dragid = trimify(dragid), true);
    }

    /**
     * Returns a space-delimited list of drop id's associated with this component. A non-empty value
     * enables this component to act as a drop target. A dragged component may be dropped upon a
     * target component whose drop id(s) matches at least one of the dragged component's drag id's.
     * A drop id value of "*" will match any drag id.
     *
     * @return The drag id(s).
     */
    @PropertyGetter(value = "dropid", description = "A space-delimited list of drop id's associated with this component.")
    public String getDropid() {
        return dropid;
    }

    /**
     * Sets the drag id(s) to be associated with this component.
     *
     * @param dropid A space-delimited list of drop id's to be associated with this component. A
     *            non-empty value enables this component to act as a drop target. A dragged
     *            component may be dropped upon a target component whose drop id(s) matches at least
     *            one of the dragged component's drag id's. A drop id value of "*" will match any
     *            drag id.
     */
    @PropertySetter(value = "dropid", description = "A space-delimited list of drop id's associated with this component.")
    public void setDropid(String dropid) {
        propertyChange("dropid", this.dropid, this.dropid = trimify(dropid), true);
    }

    /**
     * Returns the popup component that will appear when right-clicking on this component.
     *
     * @return The popup component that will appear when right-clicking on this component.
     */
    @PropertyGetter(value = "context", description = "The popup component that will appear when right-clicking on this component.")
    public Popup getContext() {
        return context;
    }

    /**
     * Sets the popup component that will appear when right-clicking on this component.
     *
     * @param context The popup component that will appear when right-clicking on this component.
     */
    @PropertySetter(value = "context", defer = true, description = "The popup component that will appear when right-clicking on this component.")
    public void setContext(Popup context) {
        if (context != this.context) {
            swapTrackedComponents(context, this.context);
            propertyChange("context", this.context, this.context = context, true);
        }
    }

    /**
     * Returns the popup component that will appear when hovering over this component.
     *
     * @return The popup component that will appear when hovering over this component.
     */
    @PropertyGetter(value = "popup", description = "The popup component that will appear when hovering over this component.")
    public Popup getPopup() {
        return popup;
    }

    /**
     * Sets the popup component that will appear when hovering over this component.
     *
     * @param popup The popup component that will appear when hovering over this component.
     */
    @PropertySetter(value = "popup", defer = true, description = "The popup component that will appear when hovering over this component.")
    public void setPopup(Popup popup) {
        if (popup != this.popup) {
            swapTrackedComponents(popup, this.popup);
            propertyChange("popup", this.popup, this.popup = popup, true);
        }
    }

    /**
     * If the child being removed is the popup, set the popup to null.
     *
     * @see org.fujion.component.BaseComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);

        if (child == popup) {
            setPopup(null);
        }
    }

    /**
     * Returns the list of key codes to be captured. When a key in this list is typed, a
     * {@value org.fujion.event.KeycaptureEvent#TYPE} event will be sent to this component.
     *
     * @return List of key codes to be captured (see
     *         {@link org.fujion.event.KeyCode#normalizeKeyCapture} for formatting details).
     */
    @PropertyGetter(value = "keycapture", description = "The list of key codes to be captured.")
    public String getKeycapture() {
        return keycapture;
    }

    /**
     * Sets the list of key codes to be captured. When a key in this list is typed, a
     * {@value org.fujion.event.KeycaptureEvent#TYPE} event will be sent to this component.
     *
     * @param keycapture List of key codes to be captured (see
     *            {@link org.fujion.event.KeyCode#normalizeKeyCapture} for formatting details).
     */
    @PropertySetter(value = "keycapture", description = "The list of key codes to be captured.")
    public void setKeycapture(String keycapture) {
        if (propertyChange("keycapture", this.keycapture, this.keycapture = nullify(keycapture), false)) {
            sync("keycapture", KeyCode.normalizeKeyCapture(this.keycapture));
        }
    }

    /**
     * Ensures that this component is visible within the view port by scrolling if necessary.
     */
    public void scrollIntoView() {
        invoke("scrollIntoView");
    }

    /**
     * Returns the first visible child, if any.
     *
     * @param recurse If true, all descendant levels are also searched using a breadth first
     *            strategy.
     * @return The first visible child encountered, or null if not found.
     */
    public BaseUIComponent getFirstVisibleChild(boolean recurse) {
        return getFirstVisibleChild(BaseUIComponent.class, recurse);
    }

    /**
     * Returns the first visible child of a given class, if any.
     *
     * @param <T> The child class.
     * @param clazz The child class to consider.
     * @param recurse If true, all descendant levels are also searched using a breadth first
     *            strategy.
     * @return The first visible child encountered, or null if not found.
     */
    public <T extends BaseUIComponent> T getFirstVisibleChild(Class<T> clazz, boolean recurse) {
        for (T child : getChildren(clazz)) {
            if (child.isVisible()) {
                return child;
            }
        }

        if (recurse) {
            for (T child : getChildren(clazz)) {
                T comp = child.getFirstVisibleChild(clazz, recurse);

                if (comp != null) {
                    return comp;
                }
            }
        }

        return null;
    }

    /**
     * Remove popup reference when it is destroyed.
     *
     * @see org.fujion.component.BaseComponent#onDestroyTracked(org.fujion.component.BaseComponent)
     */
    @Override
    protected void onDestroyTracked(BaseComponent comp) {
        if (comp == popup) {
            setPopup(null);
        }
        
        if (comp == context) {
            setContext(null);
        }

        super.onDestroyTracked(comp);
    }
}
