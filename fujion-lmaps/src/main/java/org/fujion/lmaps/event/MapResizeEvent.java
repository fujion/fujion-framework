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
package org.fujion.lmaps.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.event.Event;
import org.fujion.lmaps.Point;

import java.util.Map;

/**
 * Map resize event
 */
@EventType(MapResizeEvent.TYPE)
public class MapResizeEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "lmap_resize";

    private Point oldSize;

    private Point newSize;

    @EventParameter(value = "oldSize", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _oldSize;

    @EventParameter(value = "newSize", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _newSize;

    public Point getOldSize() {
        return oldSize;
    }

    public Point getNewSize() {
        return newSize;
    }

    @Override
    protected void afterInitialized() {
        super.afterInitialized();
        oldSize = new Point(_oldSize);
        newSize = new Point(_newSize);
    }

}
