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
package org.fujion.component;

import java.util.List;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.event.IEventListener;

/**
 * A component supporting a window that slides down from the top center of the viewport and can
 * display multiple messages (see {@link MessagePane}).
 */
@Component(tag = "messagewindow", widgetClass = "Messagewindow", parentTag = "page", childTag = @ChildTag("messagepane"), description = "A window that slides down from the top center of the viewport")
public class MessageWindow extends BaseUIComponent {

    /**
     * Clears all messages.
     */
    public void clearMessages() {
        destroyChildren();
    }

    /**
     * Clears messages that belong to the specified category.
     *
     * @param category Messages belonging to this category will be cleared.
     */
    public void clearMessages(String category) {
        List<BaseComponent> children = getChildren();

        for (int i = children.size() - 1; i >= 0; i--) {
            MessagePane pane = (MessagePane) children.get(i);

            if (areEqual(category, pane.getCategory())) {
                pane.destroy();
            }
        }
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message) {
        return showMessage(message, null);
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display.
     * @param caption Caption for message.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message, String caption) {
        return showMessage(message, caption, null);
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display
     * @param caption Caption for message.
     * @param clazz CSS classes to apply.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message, String caption, String clazz) {
        return showMessage(message, caption, clazz, 8000);
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display
     * @param caption Caption for message.
     * @param clazz CSS classes to apply.
     * @param duration Message duration in milliseconds.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message, String caption, String clazz, int duration) {
        return showMessage(message, caption, clazz, duration, null, null);
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display
     * @param caption Caption for message.
     * @param clazz CSS classes to apply.
     * @param duration Message duration in milliseconds.
     * @param category Tag to classify message.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message, String caption, String clazz, Integer duration, String category) {
        return showMessage(message, caption, clazz, duration, category, null);
    }

    /**
     * Convenience method for displaying a simple text message.
     *
     * @param message Message to display
     * @param title Caption for message.
     * @param clazz CSS classes to apply.
     * @param duration Message duration in milliseconds.
     * @param category Tag to classify message.
     * @param actionListener Optional listener for action invocation.
     * @return The newly created message pane.
     */
    public MessagePane showMessage(String message, String title, String clazz, int duration, String category,
                                   IEventListener actionListener) {
        MessagePane pane = new MessagePane(title, category, duration, actionListener != null);
        pane.addClass(clazz);
        pane.addChild(new Cell(message));

        if (actionListener != null) {
            pane.addEventListener("action", actionListener);
        }

        addChild(pane);
        return pane;
    }

}
