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
import org.fujion.component.BaseComponent;
import org.fujion.convert.ConversionService;

/**
 * A change event.
 */
@EventType(ChangeEvent.TYPE)
public class ChangeEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "change";

    @EventParameter
    private Object value;

    public ChangeEvent() {
        super(TYPE);
    }

    public ChangeEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }

    public ChangeEvent(BaseComponent target, Object data, Object value) {
        super(TYPE, target, data);
        this.value = value;
    }

    public ChangeEvent(BaseComponent target, BaseComponent relatedTarget, Object data, Object value) {
        super(TYPE, target, relatedTarget, data);
        this.value = value;
    }

    /**
     * Returns the changed value.
     *
     * @return The changed value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the changed value, coerced to the specified type.
     *
     * @param <T> The desired return type.
     * @param type The desired return type.
     * @return The changed value, coerced to the specified type.
     */
    public <T> T getValue(Class<T> type) {
        return ConversionService.getInstance().convert(value, type);
    }
}
