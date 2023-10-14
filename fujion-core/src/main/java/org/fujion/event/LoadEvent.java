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
package org.fujion.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.component.BaseComponent;

/**
 * A document load event.
 */
@EventType(LoadEvent.TYPE)
public class LoadEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "load";

    @EventParameter(onFailure = OnFailure.IGNORE)
    private String src;

    public LoadEvent() {
        super(TYPE);
    }

    public LoadEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }

    /**
     * Returns the URL of the loaded document. If cross-domain security constraints prevent access,
     * the URL will be null.
     *
     * @return The URL of the loaded document.
     */
    public String getSrc() {
        return src;
    }

}
