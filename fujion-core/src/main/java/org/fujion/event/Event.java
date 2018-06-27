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

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.client.ExecutionContext;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;

/**
 * This is the base class for all events.
 */
public class Event {
    
    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private String type;
    
    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private int id;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private BaseComponent target;
    
    @EventParameter(value = "target", onFailure = OnFailure.IGNORE)
    private String targetId;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private BaseComponent currentTarget;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private BaseComponent relatedTarget;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private Object data;
    
    @EventParameter
    private Page page;
    
    private boolean stopPropagation;
    
    public Event() {
    }
    
    /**
     * Create an event of the specified type with no target and no data.
     *
     * @param type The event type.
     */
    public Event(String type) {
        this(type, null, null);
    }
    
    /**
     * Create an event of the specified type and target with no data.
     *
     * @param type The event type.
     * @param target The target of the event.
     */
    public Event(String type, BaseComponent target) {
        this(type, target, null);
    }
    
    /**
     * Create an event of the specified type, target, and data.
     *
     * @param type The event type.
     * @param target The target of the event.
     * @param data Arbitrary data to associate with the event.
     */
    public Event(String type, BaseComponent target, Object data) {
        this(type, target, null, data);
    }
    
    /**
     * Create an event of the specified type, target, and related target with no data.
     *
     * @param type The event type.
     * @param target The target of the event.
     * @param relatedTarget The related target that participated in the event.
     */
    public Event(String type, BaseComponent target, BaseComponent relatedTarget) {
        this(type, target, relatedTarget, null);
    }
    
    /**
     * Create an event of the specified type, target, related target, and data.
     *
     * @param type The event type.
     * @param target The target of the event.
     * @param relatedTarget The related target that participated in the event.
     * @param data Arbitrary data to associate with the event.
     */
    public Event(String type, BaseComponent target, BaseComponent relatedTarget, Object data) {
        this.type = type;
        this.target = target;
        this.targetId = target == null ? null : target.getId();
        this.data = data;
        this.currentTarget = target;
        this.relatedTarget = relatedTarget;
        this.page = target != null ? target.getPage() : null;
        this.page = this.page == null ? ExecutionContext.getPage() : this.page;
    }
    
    /**
     * Copy constructor.
     *
     * @param source Source event to copy.
     */
    public Event(Event source) {
        this(source.type, source);
    }
    
    /**
     * Copy constructor, but with explicit type.
     *
     * @param type Type for new event.
     * @param source Source event to copy.
     */
    public Event(String type, Event source) {
        this.type = type;
        this.target = source.target;
        this.targetId = source.targetId;
        this.data = source.data;
        this.currentTarget = source.currentTarget;
        this.relatedTarget = source.relatedTarget;
        this.page = source.page;
    }
    
    /**
     * Returns the page associated with the event.
     *
     * @return The page associated with the event.
     */
    public Page getPage() {
        return page;
    }
    
    /**
     * Returns the type of event.
     *
     * @return The type of event.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Returns the event's target (the component on which the event occurred).
     *
     * @return The event's target.
     */
    public BaseComponent getTarget() {
        return target != null ? target : currentTarget;
    }
    
    /**
     * Returns the event's current target (the component that handled the event).
     *
     * @return The event's current target.
     */
    public BaseComponent getCurrentTarget() {
        return currentTarget != null ? currentTarget : target;
    }
    
    /**
     * Returns the event's related target, if any. A related target is a secondary component that
     * had some role in or relationship to the event. For example, for a <code>mouseenter</code>
     * event, the target is the component entered; the related target is the component exited.
     *
     * @return The event's related target
     */
    public BaseComponent getRelatedTarget() {
        return relatedTarget;
    }
    
    /**
     * Returns the id of the DOM element that was the actual target of the event. This may be the
     * same as the target's component id, but could also be a sub-element of the target.
     *
     * @return The target id.
     */
    public String getTargetId() {
        return targetId;
    }
    
    /**
     * Returns arbitrary data associated with the event, if any.
     *
     * @return Arbitrary data associated with the event
     */
    public Object getData() {
        return data;
    }
    
    /**
     * Flags the event to prevent further propagation in the event handler chain.
     */
    public void stopPropagation() {
        stopPropagation = true;
    }
    
    /**
     * Returns true if propagation has been stopped.
     *
     * @return True if propagation has been stopped.
     */
    public boolean isStopped() {
        return stopPropagation;
    }
    
    /**
     * Returns the client-assigned id for the event.
     *
     * @return The client-assigned id. Will be 0 if the event did not originate from the client.
     */
    public int getId() {
        return id;
    }
}
