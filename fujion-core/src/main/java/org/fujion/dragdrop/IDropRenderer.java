/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.dragdrop;

import org.fujion.component.BaseComponent;

/**
 * Interface to support rendering of dropped items.
 */
public interface IDropRenderer {
    
    /**
     * The drop renderer should fully render the dropped item and return its root component. The
     * caller will attach the returned root component to an appropriate parent.
     * 
     * @param droppedItem The item that was dropped.
     * @return The root component of the rendered view. The implementation may return null,
     *         indicating an inability to render the dropped item for any reason.
     */
    BaseComponent renderDroppedItem(BaseComponent droppedItem);
    
    /**
     * The drop renderer should supply text to be displayed in association with the dropped item.
     * 
     * @param droppedItem The dropped component.
     * @return The display text.
     */
    String getDisplayText(BaseComponent droppedItem);
    
    /**
     * The drop renderer may return a value of false to temporarily disable its participation in
     * drop events.
     * 
     * @return Enabled status.
     */
    boolean isEnabled();
}
