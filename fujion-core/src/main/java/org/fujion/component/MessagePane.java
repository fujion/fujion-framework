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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;

/**
 * A pane holding a single message in a message window.
 *
 * @see org.fujion.component.MessageWindow
 */
@Component(
        tag = "messagepane",
        widgetClass = "Messagepane",
        content = ContentHandling.AS_CHILD,
        parentTag = "messagewindow",
        childTag = @ChildTag("*"),
        description = "A pane holding a single message in a message window.")
public class MessagePane extends BaseUIComponent {
    
    private String title;
    
    private int duration = 8000;
    
    private String category;
    
    private boolean actionable;
    
    public MessagePane() {
        
    }
    
    /**
     * Create a message pane.
     *
     * @param title Title bar text.
     * @param category Message category.
     * @param duration Duration, in milliseconds, that the message will display.
     * @param actionable True if the message is actionable.
     */
    public MessagePane(String title, String category, int duration, boolean actionable) {
        setTitle(title);
        setCategory(category);
        setDuration(duration);
        setActionable(actionable);
    }
    
    /**
     * Returns the title bar text.
     *
     * @return The title bar text.
     */
    @PropertyGetter(value = "title", description = "The title bar text.")
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title bar text.
     *
     * @param title The title bar text.
     */
    @PropertySetter(value = "title", description = "The title bar text.")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }
    
    /**
     * Returns the duration, in milliseconds, that the message will be displayed. The default is
     * 8000 ms. A value of &lt;=0 means infinite duration.
     *
     * @return The duration, in milliseconds, that the message will be displayed.
     */
    @PropertyGetter(value = "duration", description = "The duration, in milliseconds, that the message will be displayed.")
    public int getDuration() {
        return duration;
    }
    
    /**
     * Sets the duration, in milliseconds, that the message will be displayed. A value of &lt;=0
     * means infinite duration.
     *
     * @param duration The duration, in milliseconds, that the message will be displayed.
     */
    @PropertySetter(value = "duration", defaultValue = "8000", description = "The duration, in milliseconds, that the message will be displayed.")
    public void setDuration(int duration) {
        propertyChange("duration", this.duration, this.duration = duration, true);
    }
    
    /**
     * Returns the category of the message. This allows messages to be cleared based on their
     * category.
     *
     * @return The category of the message.
     */
    @PropertyGetter(value = "category", description = "The category of the message.")
    public String getCategory() {
        return category;
    }
    
    /**
     * Sets the category of the message. This allows messages to be cleared based on their category.
     *
     * @param category The category of the message.
     */
    @PropertySetter(value = "category", description = "The category of the message.")
    public void setCategory(String category) {
        this.category = nullify(category);
    }
    
    /**
     * Returns true if the message is actionable. An actionable message has an action icon that,
     * when clicked, triggers an action event.
     *
     * @return True if the message is actionable.
     */
    @PropertyGetter(value = "actionable", description = "True if the message is actionable.  An actionable message has an action icon that, "
            + "when clicked, triggers an action event.")
    public boolean isActionable() {
        return actionable;
    }
    
    /**
     * Set to true to make the message actionable. An actionable message has an action icon that,
     * when clicked, triggers an action event.
     *
     * @param actionable Set to true to make the message actionable.
     */
    @PropertySetter(value = "actionable", defaultValue = "false", description = "True if the message is actionable.  An actionable message has an action icon that, "
            + "when clicked, triggers an action event.")
    public void setActionable(boolean actionable) {
        propertyChange("actionable", this.actionable, this.actionable = actionable, true);
    }
    
    /**
     * Handles close events from the client.
     */
    @EventHandler(value = "close", syncToClient = false, mode = "init")
    private void _close() {
        destroy();
    }
}
