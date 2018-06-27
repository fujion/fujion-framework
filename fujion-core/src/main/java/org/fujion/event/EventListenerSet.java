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
package org.fujion.event;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Represents a set of event listeners.
 */
public class EventListenerSet extends LinkedHashSet<IEventListener> implements IEventListener {

    private static final long serialVersionUID = 1L;

    /**
     * Notify all listeners of the specified event. Terminates if event propagation is stopped. This
     * operates on a copy of the set of registered listeners, so modifications to the original set
     * will not affect the iteration.
     *
     * @param event An event.
     */
    @Override
    public void onEvent(Event event) {
        for (IEventListener listener : new ArrayList<>(this)) {
            if (event.isStopped()) {
                break;
            }
            
            listener.onEvent(event);
        }
    }

}
