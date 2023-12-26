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
package org.fujion.client;

import org.fujion.component.Page;
import org.fujion.convert.ConversionService;
import org.fujion.websocket.Session;

import java.util.Map;

/**
 * Represents a request from the client. At a minimum, a request must have a type that dictates
 * which handler will process the request. Most requests will also have an associated page and a
 * data payload. The format of the data payload is specific to the request type.
 */
public class ClientRequest {

    private final Session session;

    private final String type;

    private final Object data;

    /**
     * Creates a client request object from the request map.
     *
     * @param session The session associated with this request.
     * @param map The request map.
     */
    public ClientRequest(Session session, Map<String, Object> map) {
        this.session = session;
        data = map.get("data");
        type = (String) map.get("type");
    }

    /**
     * The page with which this request is associated.
     *
     * @return The associated page.
     */
    public Page getPage() {
        return session.getPage();
    }

    /**
     * Return the session to which this request belongs.
     *
     * @return The session.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Returns the data payload associated with the request.
     *
     * @return The data payload (may be null).
     */
    public Object getData() {
        return data;
    }

    /**
     * Returns the data payload associated with the request, coercing it to the specified type.
     *
     * @param <T> The data class.
     * @param clazz The class of the payload.
     * @return The data payload (may be null).
     * @exception ClassCastException If the payload is not assignment-compatible with the specified
     *                class.
     */
    public <T> T getData(Class<T> clazz) {
        return clazz.cast(data);
    }

    /**
     * Returns the type of this request.
     *
     * @return The type of the request.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the named parameter from the request data. This assumes that the request data is a
     * map of parameter values. If not, null will always be returned. Performs intelligent
     * conversion of datatypes where possible.
     *
     * @param <T> The expected parameter type.
     * @param name The parameter name.
     * @param type The expected parameter type.
     * @return The parameter value, or null if not found.
     */
    public <T> T getParam(String name, Class<T> type) {
        Object value = data instanceof Map ? getData(Map.class).get(name) : null;
        return convertValue(value, type);
    }

    /**
     * Returns the named event parameter from the request.
     *
     * @param <T> The expected parameter type.
     * @param name The parameter name.
     * @param type The expected parameter type.
     * @param deflt The default value for the parameter.
     * @return The parameter value, or the specified default if not found.
     */
    public <T> T getParam(String name, Class<T> type, T deflt) {
        T value = getParam(name, type);
        return value == null ? convertValue(deflt, type) : value;
    }

    @SuppressWarnings("unchecked")
    private <T> T convertValue(Object value, Class<T> type) {
        try {
            return type == Page.class ? (T) getPage() : ConversionService.getInstance().convert(value, type, getPage());
        } catch (Exception e) {
            return null;
        }
    }
}
