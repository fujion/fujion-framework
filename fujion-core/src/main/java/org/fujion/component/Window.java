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
package org.fujion.component;

import java.util.function.BooleanSupplier;

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.client.ExecutionContext;
import org.fujion.event.Event;
import org.fujion.event.IEventListener;
import org.springframework.util.Assert;

/**
 * A window component with a title bar and maximize/minimize/close buttons. May be used in modal,
 * popup, or inline modes.
 */
@Component(
        tag = "window",
        widgetClass = "Window",
        content = ContentHandling.AS_CHILD,
        parentTag = "*",
        childTag = @ChildTag("*"),
        description = "A window component with a title bar and maximize/minimize/close buttons.")
public class Window extends BaseUIComponent implements INamespace {

    /**
     * Possible display modes for a window.
     */
    public enum Mode {
        /**
         * The window floats and a modal mask prevents interaction with other components.
         */
        MODAL,
        /**
         * The window is inline (embedded).
         */
        INLINE,
        /**
         * The window floats, but interaction with other components is allowed.
         */
        POPUP
    }

    /**
     * Size state for a window. Default is NORMAL.
     */
    public enum Size {
        /**
         * Sized and positioned according to corresponding property settings.
         */
        NORMAL,
        /**
         * Expanded to fill the view port.
         */
        MAXIMIZED,
        /**
         * Only the title bar is visible; moved to the bottom of the view port.
         */
        MINIMIZED
    }

    /**
     * Action upon window closure. Default is DESTROY.
     */
    public enum CloseAction {
        /**
         * Detach the window.
         */
        DETACH,
        /**
         * Destroy the window.
         */
        DESTROY,
        /**
         * Hide the window.
         */
        HIDE
    }

    /**
     * Placement of window in view port when first displayed. Default is CENTER.
     */
    public enum Position {
        CENTER, LEFT_CENTER, LEFT_TOP, LEFT_BOTTOM, RIGHT_CENTER, RIGHT_TOP, RIGHT_BOTTOM, CENTER_TOP, CENTER_BOTTOM
    }

    private static final IEventListener dummyListener = (event) -> {};

    private String title;

    private String image;

    private Position position = Position.CENTER;

    private boolean closable;

    private boolean movable = true;

    private boolean sizable;

    private boolean maximizable;

    private boolean minimizable;

    private Size size = Size.NORMAL;

    private BooleanSupplier onCanClose;

    private CloseAction closeAction = CloseAction.DESTROY;

    private Mode mode;

    private IEventListener closeListener;

    public Window() {
        super();
        setMode(Mode.INLINE);
        addClass("flavor:alert-primary");
    }

    /**
     * Returns the title text.
     *
     * @return The title text.
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title text.
     *
     * @param title The title text.
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }

    /**
     * Returns the URL of the image to be displayed on the left side of the title bar.
     *
     * @return The URL of the image to be displayed on the left side of the title bar.
     */
    @PropertyGetter(value = "image", description = "The URL of the image to be displayed on the left side of the title bar.")
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL of the image to be displayed on the left side of the title bar.
     *
     * @param image The URL of the image to be displayed on the left side of the title bar.
     */
    @PropertySetter(value = "image", description = "The URL of the image to be displayed on the left side of the title bar.")
    public void setImage(String image) {
        propertyChange("image", this.image, this.image = nullify(image), true);
    }

    /**
     * Returns true if the window is closable. A window that is closable has an icon that, when
     * clicked, triggers a close event.
     *
     * @return True if the window is closable.
     */
    @PropertyGetter(value = "closable", description = "True if the window is closable. A window that is closable has an icon that, when "
            + "clicked, triggers a close event.")
    public boolean isClosable() {
        return closable;
    }

    /**
     * Set to true to make the window closable. A window that is closable has an icon that, when
     * clicked, triggers a close event.
     *
     * @param closable If true, the window is closable.
     * @see #onCanClose
     */
    @PropertySetter(value = "closable", defaultValue = "false", description = "True if the window is closable. A window that is closable has an icon that, when "
            + "clicked, triggers a close event.")
    public void setClosable(boolean closable) {
        propertyChange("closable", this.closable, this.closable = closable, true);
    }

    /**
     * Returns the window's sizable property. A window that is sizable has borders that may be
     * dragged to change its dimensions. If the {@link #setMode(Mode) mode} is {@link Mode#INLINE
     * INLINE}, this property has no effect.
     *
     * @return The window's sizable property.
     */
    @PropertyGetter(value = "sizable", description = "The window's sizable property. A window that is sizable has borders that may be "
            + "dragged to change its dimensions.")
    public boolean isSizable() {
        return sizable;
    }

    /**
     * Sets the window's sizable property. A window that is sizable has borders that may be dragged
     * to change its dimensions. If the {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE},
     * this property has no effect.
     *
     * @param sizable The window's sizable property.
     */
    @PropertySetter(value = "sizable", defaultValue = "false", description = "The window's sizable property. A window that is sizable has borders that may be "
            + "dragged to change its dimensions.")
    public void setSizable(boolean sizable) {
        propertyChange("sizable", this.sizable, this.sizable = sizable, true);
    }

    /**
     * Returns the {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     * {@link Mode#POPUP popup} window.
     *
     * @return The {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     *         {@link Mode#POPUP popup} window.
     */
    @PropertyGetter(value = "position", description = "The placement of a newly opened modal or popup window.")
    public Position getPosition() {
        return position;
    }
    
    /**
     * Sets the {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     * {@link Mode#POPUP popup} window.
     *
     * @param position The {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     *            {@link Mode#POPUP popup} window.
     */
    @PropertySetter(value = "position", defaultValue = "center", description = "The placement of a newly opened modal or popup window.")
    public void setPosition(Position position) {
        propertyChange("position", this.position, this.position = position, true);
    }
    
    /**
     * Returns whether the window may be moved to a new position by dragging its title bar. If the
     * {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE}, this property has no effect.
     *
     * @return If true, the window may be moved to a new position by dragging its title bar.
     */
    @PropertyGetter(value = "movable", description = "If true, the window may be moved to a new position by dragging its title bar.")
    public boolean isMovable() {
        return movable;
    }

    /**
     * Sets whether the window may be moved to a new position by dragging its title bar. If the
     * {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE}, this property has no effect.
     *
     * @param movable If true, the window may be moved to a new position by dragging its title bar.
     */
    @PropertySetter(value = "movable", defaultValue = "true", description = "If true, the window may be moved to a new position by dragging its title bar.")
    public void setMovable(boolean movable) {
        propertyChange("movable", this.movable, this.movable = movable, true);
    }

    /**
     * Returns true if the window may be maximized. A window that is maximizable has an icon that,
     * when clicked, causes a {@link Mode#MODAL modal} or {@link Mode#POPUP popup} window to be
     * resized to fill the view port. Clicking the icon again restores the window to its original
     * size and position.
     *
     * @return True if the window is maximizable.
     */
    @PropertyGetter(value = "maximizable", description = "True if the window may be maximized.")
    public boolean isMaximizable() {
        return maximizable;
    }

    /**
     * Sets whether the window may be maximized. A window that is maximizable has an icon that, when
     * clicked, causes a {@link Mode#MODAL modal} or {@link Mode#POPUP popup} window to be resized
     * to fill the view port. Clicking the icon again restores the window to its original size and
     * position.
     *
     * @param maximizable True if the window is maximizable.
     */
    @PropertySetter(value = "maximizable", defaultValue = "false", description = "True if the window may be maximized.")
    public void setMaximizable(boolean maximizable) {
        propertyChange("maximizable", this.maximizable, this.maximizable = maximizable, true);
    }

    /**
     * Returns true if the window may be minimized. A window that is minimizable has an icon that,
     * when clicked, causes a window's body to be hidden. If the window's mode is {@link Mode#MODAL
     * modal} or {@link Mode#POPUP popup}, it will also be resized to a smaller dimension and placed
     * at the lower left corner of the view port. Clicking the icon again restores the window to its
     * original size and position.
     *
     * @return True if the window is minimizable.
     */
    @PropertyGetter(value = "minimizable", description = "True if the window may be minimized.")
    public boolean isMinimizable() {
        return minimizable;
    }

    /**
     * Sets whether the window may be minimized. A window that is minimizable has an icon that, when
     * clicked, causes a window's body to be hidden. If the window's mode is {@link Mode#MODAL
     * modal} or {@link Mode#POPUP popup}, it will also be resized to a smaller dimension and placed
     * at the lower left corner of the view port. Clicking the icon again restores the window to its
     * original size and position.
     *
     * @param minimizable True if the window is minimizable.
     */
    @PropertySetter(value = "minimizable", defaultValue = "false", description = "True if the window may be minimized.")
    public void setMinimizable(boolean minimizable) {
        propertyChange("minimizable", this.minimizable, this.minimizable = minimizable, true);
    }

    /**
     * Returns the display {@link Mode mode} of the window.
     *
     * @return The display {@link Mode mode} of the window.
     */
    @PropertyGetter(value = "mode", description = "The display mode of the window.")
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the display {@link Mode mode} of the window.
     *
     * @param mode The display {@link Mode mode} of the window.
     */
    @PropertySetter(value = "mode", defaultValue = "inline", description = "The display mode of the window.")
    public void setMode(Mode mode) {
        propertyChange("mode", this.mode, this.mode = defaultify(mode, Mode.INLINE), true);
    }

    /**
     * Returns the {@link Size sizing} mode of the window.
     *
     * @return The {@link Size sizing} mode of the window.
     */
    @PropertyGetter(value = "size", description = "The sizing mode of the window.")
    public Size getSize() {
        return size;
    }

    /**
     * Sets the {@link Size sizing} mode of the window.
     *
     * @param size The {@link Size sizing} mode of the window.
     */
    @PropertySetter(value = "size", defaultValue = "normal", description = "The sizing mode of the window.")
    public void setSize(Size size) {
        propertyChange("size", this.size, this.size = defaultify(size, Size.NORMAL), true);
    }

    /**
     * Returns the {@link CloseAction action} to be taken when the window is closed.
     *
     * @return The {@link CloseAction action} to be taken when the window is closed.
     */
    @PropertyGetter(value = "closeAction", description = "The action to be taken when the window is closed.")
    public CloseAction getCloseAction() {
        return closeAction;
    }

    /**
     * Sets the {@link CloseAction action} to be taken when the window is closed.
     *
     * @param closeAction The {@link CloseAction action} to be taken when the window is closed.
     */
    @PropertySetter(value = "closeAction", defaultValue = "destroy", description = "The action to be taken when the window is closed.")
    public void setCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction == null ? CloseAction.DESTROY : closeAction;
    }

    /**
     * Request the window to be closed. Window closure may be prevented if the onCanClose logic
     * returns false.
     *
     * @return True if the window was closed.
     */
    public boolean close() {
        if (canClose()) {
            switch (closeAction) {
                case DESTROY:
                    destroy();
                    break;

                case DETACH:
                    detach();
                    break;

                case HIDE:
                    setVisible(false);
                    break;
            }

            if (closeListener != null) {
                try {
                    closeListener.onEvent(new Event("close", this));
                } finally {
                    closeListener = null;
                }
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * Returns the functional interface that determines whether window closure is permitted.
     *
     * @return The functional interface that determines whether window closure is permitted.
     */
    public BooleanSupplier getOnCanClose() {
        return onCanClose;
    }

    /**
     * Sets whether window closure is permitted using a simple Boolean value. This is a shortcut for
     * calling {@link #setOnCanClose(BooleanSupplier)} with a functional interface that returns a
     * fixed Boolean value.
     *
     * @param canClose If true, the tab may be closed.
     */
    public void setOnCanClose(boolean canClose) {
        setOnCanClose(() -> canClose);
    }
    
    /**
     * Sets the functional interface that will determine if window closure is permitted.
     *
     * @param onCanClose The functional interface that will determine if window closure is
     *            permitted.
     */
    public void setOnCanClose(BooleanSupplier onCanClose) {
        this.onCanClose = onCanClose;
    }

    /**
     * Invokes the {@link #getOnCanClose canClose} logic and returns the result.
     *
     * @return The result of invoking the {@link #getOnCanClose canClose} logic.
     */
    public boolean canClose() {
        return onCanClose == null || onCanClose.getAsBoolean();
    }

    /**
     * Opens the window modally.
     */
    public void modal() {
        modal(null);
    }

    /**
     * Opens the window modally.
     *
     * @param closeListener The event listener to be invoked upon window closure.
     */
    public void modal(IEventListener closeListener) {
        doShow(Mode.MODAL, closeListener);
    }

    /**
     * Opens the window as a popup.
     */
    public void popup() {
        popup(null);
    }

    /**
     * Opens the window as a popup.
     *
     * @param closeListener The event listener to be invoked upon window closure.
     */
    public void popup(IEventListener closeListener) {
        doShow(Mode.POPUP, closeListener);
    }

    private void doShow(Mode mode, IEventListener closeListener) {
        Assert.state(this.closeListener == null, () -> "Window is already open");

        if (getParent() == null) {
            setParent(ExecutionContext.getPage());
        }

        this.closeListener = closeListener == null ? dummyListener : closeListener;
        setMode(mode);
        setVisible(true);
        fireEvent("open");
    }

    /**
     * Handles close events from the client.
     */
    @EventHandler(value = "close", syncToClient = false, mode = "init")
    private void _close() {
        close();
    }

}
