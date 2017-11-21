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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.client.ClientRequest;
import org.fujion.client.Synchronizer;
import org.fujion.common.WeakMap;
import org.fujion.core.WebUtil;
import org.fujion.event.Event;
import org.fujion.event.EventQueue;
import org.fujion.page.PageRegistry;
import org.fujion.websocket.Session;

/**
 * This is the root component of a Fujion Server Page.
 */
@Component(tag = "page", widgetClass = "Page", content = ContentHandling.AS_CHILD, childTag = @ChildTag("*"), description = "The root component of a Fujion Server Page.")
public final class Page extends BaseComponent implements INamespace {

    public static final String ID_PREFIX = "_fujion_";

    private static final AtomicInteger uniqueId = new AtomicInteger();

    private Synchronizer synchronizer;

    private Session session;

    private int nextId;

    private final Map<String, BaseComponent> ids = new WeakMap<>();

    private final EventQueue eventQueue = new EventQueue(this);

    private final Map<String, Object> browserInfo = new HashMap<>();

    private Map<String, String> queryParams;

    private String title;

    private String image;
    
    private boolean closable = true;
    
    private final String src;

    /**
     * Creates an uninitialized page. For internal use only.
     *
     * @param src URL of a FSP resource that will be used to populate the page upon initialization.
     * @return The newly created page.
     */
    public static Page _create(String src) {
        return new Page(src);
    }

    /**
     * Performs final initialization of a newly created page. For internal use only.
     *
     * @param page The page to initialize.
     * @param request The initialization request from the client.
     * @param synchronizer The synchronizer that the page will use.
     */
    @SuppressWarnings("unchecked")
    public static void _init(Page page, ClientRequest request, Synchronizer synchronizer) {
        page.synchronizer = synchronizer;
        page.session = request.getSession();
        page.browserInfo.putAll((Map<String, Object>) request.getData());
        page._attach(page);
    }

    public Page() {
        src = null;
    }

    private Page(String src) {
        this._setId(ID_PREFIX + Integer.toHexString(uniqueId.incrementAndGet()));
        this.src = src;
        PageRegistry.registerPage(this);
    }

    /**
     * Returns the synchronizer for this page.
     *
     * @return The page's synchronizer.
     */
    public Synchronizer getSynchronizer() {
        return synchronizer;
    }

    /**
     * Returns the event queue for this page. Events placed on the event queue (typically by posting
     * the event) will be processed at the end of the execution cycle.
     *
     * @return The event queue.
     */
    public EventQueue getEventQueue() {
        return eventQueue;
    }

    /**
     * A Page may not have a parent.
     *
     * @exception ComponentException Always thrown.
     * @see org.fujion.component.BaseComponent#setParent(org.fujion.component.BaseComponent)
     */
    @Override
    public void setParent(BaseComponent parent) {
        throw new ComponentException(this, "Page cannot have a parent");
    }

    /**
     * Returns the requested attribute value from information provided by the client browser.
     *
     * @param key The attribute key.
     * @return The attribute value as a string, possibly null.
     */
    public String getBrowserInfo(String key) {
        Object value = browserInfo.get(key);
        return value == null ? null : value.toString();
    }

    /**
     * Returns the requested attribute value, cast to the specified type, from information provided
     * by the client browser.
     *
     * @param <T> The expected return type.
     * @param key The attribute key.
     * @param type The expected return type.
     * @return The attribute value, possibly null.
     */
    @SuppressWarnings("unchecked")
    public <T> T getBrowserInfo(String key, Class<T> type) {
        return (T) browserInfo.get(key);
    }

    /**
     * Returns an immutable map of information provided by the browser.
     *
     * @return An immutable map of information provided by the browser.
     */
    public Map<String, Object> getBrowserInfo() {
        return Collections.unmodifiableMap(browserInfo);
    }

    /**
     * Returns the named query parameter from the original request URL.
     *
     * @param param Name of query parameter.
     * @return The value of the query parameter, or null if not found.
     */
    public String getQueryParam(String param) {
        return getQueryParams().get(param);
    }

    /**
     * Returns an immutable map containing all query parameters from the original request URL.
     *
     * @return An immutable map containing all query parameters from the original request URL.
     */
    public Map<String, String> getQueryParams() {
        if (queryParams == null) {
            String requestUrl = (String) browserInfo.get("requestURL");
            int i = requestUrl == null ? -1 : requestUrl.indexOf("?");

            if (i >= 0) {
                queryParams = WebUtil.queryStringToMap(requestUrl.substring(i + 1), ",");
            } else {
                queryParams = Collections.emptyMap();
            }
        }

        return Collections.unmodifiableMap(queryParams);
    }

    /**
     * Returns the web socket session dedicated to this page.
     *
     * @return The web socket session dedicated to this page.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Returns the URL of the FSP resource that created this page.
     *
     * @return The URL of the FSP resource that created this page.
     */
    public String getSrc() {
        return src;
    }

    /**
     * Returns the next available component id.
     *
     * @return The next available component id.
     */
    private String nextComponentId() {
        return getId() + "_" + Integer.toHexString(++nextId);
    }

    /**
     * Cleanup page resources.
     *
     * @see org.fujion.component.BaseComponent#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PageRegistry.unregisterPage(this);
        synchronizer.clear();
        eventQueue.clearAll();
        session = null;
    }

    /**
     * Registers/unregisters a component newly attached to this page.
     *
     * @param component Component to register/unregister.
     * @param register If true, register the component. If false, unregister it.
     */
    /*package*/ void registerComponent(BaseComponent component, boolean register) {
        String id = component.getId();

        if (id == null) {
            id = nextComponentId();
            component._setId(id);
        }

        if (register) {
            ids.put(id, component);
        } else {
            ids.remove(id);
        }

        Event event = new Event(register ? "register" : "unregister", this, component);
        fireEvent(event);
    }

    /**
     * Searches for a component attached to this page given its id.
     *
     * @param id Identifier of the component sought.
     * @return The component with the specified id, or null if not found.
     */
    public BaseComponent findById(String id) {
        int i = id.indexOf('-');
        return ids.get(i == -1 ? id : id.substring(0, i));
    }

    /**
     * Returns true if the browser window may be closed without challenge. If false, the browser
     * will present a confirmation dialog before allowing the window to be closed.
     *
     * @return True if the page is closable.
     */
    @PropertyGetter(value = "closable", description = "True if the browser window may be closed without challenge.")
    public boolean isClosable() {
        return closable;
    }

    /**
     * When set to true (the default value), the browser window may be closed without challenge.
     * When set to false, the browser will present a confirmation dialog before allowing the window
     * to be closed.
     *
     * @param closable If true, the page is closable.
     */
    @PropertySetter(value = "closable", defaultValue = "true", description = "True if the browser window may be closed without challenge.")
    public void setClosable(boolean closable) {
        propertyChange("closable", this.closable, this.closable = closable, true);
    }

    /**
     * Returns the page title.
     *
     * @return The page title.
     */
    @PropertyGetter(value = "title", description = "The page title.")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the page title.
     *
     * @param title The page title.
     */
    @PropertySetter(value = "title", description = "The page title.")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }

    /**
     * Returns the URL of the image used for the page icon.
     *
     * @return The URL of the image used for the page icon.
     */
    @PropertyGetter(value = "image", description = "The URL of the image used for the page icon.")
    public String getImage() {
        return image;
    }

    /**
     * Sets the image used for the page icon.
     *
     * @param image The image used for the page icon.
     */
    @PropertySetter(value = "image", description = "The URL of the image used for the page icon.")
    public void setImage(String image) {
        propertyChange("image", this.image, this.image = nullify(image), true);
    }
}
