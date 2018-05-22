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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.ComponentRegistry;
import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.IAutoWired;
import org.fujion.ancillary.IComposite;
import org.fujion.ancillary.IComposite.CompositePosition;
import org.fujion.ancillary.IElementIdentifier;
import org.fujion.ancillary.ILabeled;
import org.fujion.ancillary.INamespace;
import org.fujion.ancillary.IResponseCallback;
import org.fujion.ancillary.OptionMap;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.EventHandlerScanner;
import org.fujion.annotation.WiredComponentScanner;
import org.fujion.client.ClientInvocation;
import org.fujion.client.ClientInvocationQueue;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseScriptComponent.ExecutionMode;
import org.fujion.event.Event;
import org.fujion.event.EventListeners;
import org.fujion.event.EventUtil;
import org.fujion.event.ForwardListener;
import org.fujion.event.IEventListener;
import org.fujion.event.PropertychangeEvent;
import org.fujion.event.StatechangeEvent;
import org.fujion.model.IBinding;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageUtil;
import org.springframework.util.Assert;

/**
 * The abstract base class for all components.
 */
public abstract class BaseComponent implements IElementIdentifier {

    private static final String ATTR_CONTROLLER = "controller";
    
    /**
     * Reference to a subcomponent. A subcomponent typically does not have an explicit
     * implementation on the server, but does have a corresponding HTML element on the client. This
     * class exists to allow client invocations to be directed to that element.
     */
    public static class SubComponent implements IElementIdentifier {

        private final BaseComponent component;

        private final String subId;

        private SubComponent(BaseComponent component, String subId) {
            this.component = component;
            this.subId = subId;
        }

        @Override
        public String getId() {
            return component.getId() + "-" + subId;
        }
    }

    /**
     * An index of child component names maintained by a parent component.
     */
    private class NameIndex {

        private Map<String, BaseComponent> names;

        /**
         * Add a component's name (if any).
         *
         * @param component Component whose name is to be added.
         */
        public void add(BaseComponent component) {
            String name = component.getName();

            if (name != null) {
                names = names == null ? new HashMap<>() : names;
                names.put(name, component);
            }
        }

        /**
         * Remove a component's name (if any).
         *
         * @param component Component whose name is to be removed.
         */
        public void remove(BaseComponent component) {
            String name = component.getName();

            if (name != null && names != null) {
                names.remove(name);
            }
        }

        private BaseComponent _get(String name) {
            return names == null ? null : names.get(name);
        }

        /**
         * Validate that a component's name does not conflict with an existing name.
         *
         * @param component Component to be validated.
         * @exception ComponentException Thrown if a name collision is detected.
         */
        public void validate(BaseComponent component) {
            _validate(component, getNameRoot());
        }

        private void _validate(BaseComponent component, BaseComponent root) {
            _validate(component.getName(), root, component);

            if (!(component.isNamespace())) {
                for (BaseComponent child : component.children) {
                    _validate(child, root);
                }
            }
        }

        private void validate(String name) {
            _validate(name, getNameRoot(), null);
        }

        private void _validate(String name, BaseComponent root, BaseComponent component) {
            if (name != null) {
                BaseComponent cmp = _find(name, root);

                if (cmp != null && cmp != component) {
                    throw new ComponentException("Name \"" + name + "\"already exists in enclosing namespace");
                }
            }
        }

        private BaseComponent getNameRoot() {
            BaseComponent root = getNamespace();
            return root == null ? getRoot() : root;
        }

        /**
         * Returns a component from the index given its name.
         *
         * @param name Component name
         * @return The corresponding component, or null if none found.
         */
        public BaseComponent find(String name) {
            return _find(name, getNameRoot());
        }

        private BaseComponent _find(String name, BaseComponent root) {
            BaseComponent component = root.nameIndex._get(name);

            if (component != null) {
                return component;
            }

            for (BaseComponent child : root.children) {
                if (!(child.isNamespace())) {
                    component = _find(name, child);

                    if (component != null) {
                        break;
                    }
                }
            }

            return component;
        }
        
        /**
         * Returns a map of all named components in this namespace.
         *
         * @return A map of all named components in this namespace.
         */
        public Map<String, BaseComponent> findAll() {
            Map<String, BaseComponent> results = new HashMap<>();
            _findAll(getNameRoot(), results);
            return results;
        }
        
        private void _findAll(BaseComponent root, Map<String, BaseComponent> results) {
            if (root.nameIndex.names != null) {
                results.putAll(root.nameIndex.names);
            }
            
            for (BaseComponent child : root.children) {
                if (!(child.isNamespace())) {
                    _findAll(child, results);
                }
            }
        }
    }
    
    private static final Pattern nameValidator = Pattern.compile("^[a-zA-Z$][a-zA-Z_$0-9]*$");

    // Listener for tracked components.
    private final IEventListener trackedComponentListener = (event) -> {
        BaseComponent comp = event.getTarget();

        if (comp != null && comp.isDead()) {
            untrackComponent(comp);
            onDestroyTracked(comp);
        }
    };

    private String name;

    private String id;
    
    private boolean dead;

    private Page page;

    private BaseComponent parent;

    private Object data;

    private String content;

    private boolean contentSynced = true;

    private OptionMap inits;

    private ClientInvocationQueue invocationQueue;
    
    private boolean namespace;

    private List<Object> controllers;

    private final List<BaseComponent> children = new LinkedList<>();

    private final Map<String, Object> attributes = new HashMap<>();

    private final EventListeners eventListeners = new EventListeners();

    private final ComponentDefinition componentDefinition;

    private final NameIndex nameIndex = new NameIndex();

    /**
     * Validates that a component still exists (i.e., is not dead).
     *
     * @param comp Component to validate.
     * @exception ComponentException Thrown upon validation failure.
     */
    protected static void validate(BaseComponent comp) {
        if (comp != null && comp.isDead()) {
            throw new ComponentException("Component no longer exists: %s", comp.getId());
        }
    }

    /**
     * Create a component. Event handler annotations are processed at this time.
     */
    protected BaseComponent() {
        componentDefinition = ComponentRegistry.getInstance().get(getClass());
        namespace = this instanceof INamespace;
        EventHandlerScanner.wire(this, this);
    }

    /**
     * Return the component's definition.
     *
     * @return The component's definition.
     */
    public ComponentDefinition getDefinition() {
        return componentDefinition;
    }

    /**
     * Returns the name associated with this instance. Names must be unique within the enclosing
     * namespace.
     *
     * @return The component's name.
     */
    @PropertyGetter(value = "name", description = "The name associated with this component instance (must be unique within the enclosing namespace).")
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with this instance. Names must be unique within the enclosing
     * namespace.
     *
     * @param name The component's name.
     */
    @PropertySetter(value = "name", description = "The name associated with this component instance (must be unique within the enclosing namespace).")
    public void setName(String name) {
        if (!areEqual(name = nullify(name), this.name)) {
            validateName(name);
            nameIndex.remove(this);
            propertyChange("name", this.name, this.name = name, true);
            nameIndex.add(this);
        }
    }

    private void validateName(String name) {
        if (name != null) {
            if (!nameValidator.matcher(name).matches()) {
                throw new ComponentException(this, "Component name is not valid: " + name);
            }

            nameIndex.validate(name);
        }
    }

    /**
     * Returns the unique id of the client widget corresponding to this component.
     *
     * @see org.fujion.ancillary.IElementIdentifier#getId()
     */
    @Override
    @PropertyGetter(value = "id", description = "The unique id of the client widget corresponding to this component.")
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of the client widget. Once set, the id is immutable.
     *
     * @param id The id of the client widget.
     */
    /*package*/ void _setId(String id) {
        Assert.isNull(this.id, "Unique id cannot be modified.");
        this.id = id;
    }

    /**
     * Removes, but does not destroy, this component from its parent.
     */
    public void detach() {
        setParent(null);
    }

    /**
     * Destroys this component.
     */
    public void destroy() {
        if (dead) {
            return;
        }

        onDestroy();

        if (page != null) {
            page.registerComponent(this, false);
        }

        destroyChildren();

        if (parent != null) {
            parent._removeChild(this, false, true);
        } else {
            invokeIfAttached("destroy");
        }

        dead = true;
        fireEvent(new Event("destroy", this));
        eventListeners.removeAll();
    }

    /**
     * Destroy a component and all its children upon finalization.
     *
     * @see java.lang.Object#finalize()
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();

        if (id != null) {
            destroy();
        }
    }

    /**
     * Destroy all children under this
     */
    public void destroyChildren() {
        while (!children.isEmpty()) {
            children.get(0).destroy();
        }
    }

    /**
     * Override to perform any special cleanup operations when this component is destroyed.
     */
    protected void onDestroy() {
    }

    /**
     * Returns true if the component is dead (meaning its corresponding widget has been destroyed).
     * Any operation on a dead component that would cause a client invocation will fail.
     *
     * @return True if the component is dead.
     */
    public boolean isDead() {
        return dead;
    }
    
    /**
     * Returns true if the component has been rendered on the browser. A component is considered
     * rendered if it belongs to a rendered page.
     *
     * @return True if the component has been rendered on the browser.
     */
    public boolean isRendered() {
        return page != null && page.getId() != null;
    }

    /**
     * Validates that this component is not dead.
     *
     * @exception ComponentException Thrown if validation fails.
     */
    protected void validate() {
        validate(this);
    }

    /**
     * Returns this component's parent, if any.
     *
     * @return The parent, or null if there is no parent.
     */
    public BaseComponent getParent() {
        return parent;
    }

    /**
     * Validates that a component would a valid parent for this component.
     *
     * @param parent Component to validate.
     * @exception ComponentException Thrown if the new parent is not a valid parent for this
     *                component.
     */
    protected void validateParent(BaseComponent parent) {
        if (parent == null) {
            return;
        }

        componentDefinition.validateParent(parent.componentDefinition);

        if (isAncestor(parent)) {
            throw new ComponentException("Not a valid parent because it is the same as or an descendant of this component");
        }
    }

    /**
     * Sets the component's parent.
     *
     * @param parent The new parent.
     * @exception ComponentException Thrown if the new parent is not a valid parent for this
     *                component.
     */
    public void setParent(BaseComponent parent) {
        if (parent != this.parent) {
            if (parent == null) {
                this.parent.removeChild(this);
            } else {
                parent.addChild(this);
            }
        }
    }

    /**
     * Returns the attribute map for this component.
     *
     * @return The attribute map.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Returns the value of the named attribute.
     *
     * @param name The attribute name.
     * @return The named attribute's value (or null if not found).
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Returns the value of the named attribute, cast to the specified type.
     *
     * @param <T> The attribute value's expected type.
     * @param name The attribute name.
     * @param dflt The default attribute value. This will be returned under one of the following
     *            conditions:
     *            <ul>
     *            <li>The named attribute does not exist</li>
     *            <li>The named attribute value is null</li>
     *            <li>The named attribute value cannot be cast to the specified type</li>
     *            </ul>
     * @return The value of the named attribute, or the default value.
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name, T dflt) {
        try {
            Object value = attributes.get(name);
            return value == null ? dflt : dflt == null ? (T) value : (T) dflt.getClass().cast(value);
        } catch (Exception e) {
            return dflt;
        }
    }

    /**
     * Returns the value of the named attribute, converted to the specified type.
     *
     * @param <T> The return type.
     * @param name The attribute name.
     * @param type The return type for the attribute value.
     * @return The value of the named attribute, or null if the existing value cannot be converted
     *         to the specified type.
     */
    public <T> T getAttribute(String name, Class<T> type) {
        try {
            return ConvertUtil.convert(attributes.get(name), type, this);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Finds the named attribute, returning its value. If the named attribute does not exist or has
     * a null value, the parent chain will be searched until a match is found.
     *
     * @param name The attribute name.
     * @return The attribute value, or null if not found.
     */
    public Object findAttribute(String name) {
        Object value = null;
        BaseComponent cmp = this;

        while (cmp != null && (value = cmp.attributes.get(name)) == null) {
            cmp = cmp.getParent();
        }

        return value;
    }

    /**
     * Sets the value of a named attribute.
     *
     * @param name The attribute name.
     * @param value The new value.
     * @return The previous value of the named attribute, if any.
     */
    @PropertySetter("attr:")
    public Object setAttribute(String name, Object value) {
        return attributes.put(name, value);
    }

    /**
     * Removes the named attribute if it exists.
     *
     * @param name The attribute name.
     * @return The value of the removed attribute.
     */
    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    /**
     * Returns true if the named attribute exists.
     *
     * @param name The attribute name.
     * @return True if the named attribute exists.
     */
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    /**
     * Validates that the specified component is currently a child of this component.
     *
     * @param child The component to check.
     * @exception ComponentException Thrown if the component fails validation.
     */
    protected void validateIsChild(BaseComponent child) {
        if (child != null && child.getParent() != this) {
            throw new ComponentException("Child does not belong to this parent");
        }
    }

    /**
     * Validates that the specified component may be added as a child.
     *
     * @param child The component to check.
     * @exception ComponentException Thrown if the component fails validation.
     */
    protected void validateChild(BaseComponent child) {
        componentDefinition.validateChild(child.componentDefinition, () -> getChildCount(child.getClass()));
    }

    /**
     * Adds a child to the end of the child list.
     *
     * @param child Child to add.
     */
    public void addChild(BaseComponent child) {
        addChild(child, -1);
    }

    /**
     * Adds a child to the child list at the specified position.
     *
     * @param child Child to add.
     * @param index The position in the child list where the new child will be inserted.
     */
    public void addChild(BaseComponent child, int index) {
        if (child instanceof IComposite) {
            addSnippet((IComposite) child);
            return;
        }

        boolean noSync = child.getPage() == null && index < 0;
        child.validate();
        BaseComponent oldParent = child.getParent();

        if (oldParent != this) {
            child.validateParent(this);
            validateChild(child);
            nameIndex.validate(child);
        }

        child.validatePage(page);

        if (oldParent == this) {
            int i = child.getIndex();

            if (i == index) {
                return;
            }

            if (index > i) {
                index--;
            }
        } else {
            child.beforeSetParent(this);
            beforeAddChild(child);
        }

        if (oldParent != null) {
            oldParent._removeChild(child, true, false);
        }

        if (index < 0) {
            children.add(child);
        } else {
            children.add(index, child);
        }

        child.parent = this;

        if (page != null) {
            child._attach(page);
        }

        nameIndex.add(child);

        if (!noSync) {
            invokeIfAttached("addChild", child, index);
        }

        if (oldParent != this) {
            afterAddChild(child);
            child.afterSetParent(this);
        }
    }

    /**
     * Adds a child to the child list immediately before the reference child.
     *
     * @param child Child to add.
     * @param before The reference child.
     */
    public void addChild(BaseComponent child, BaseComponent before) {
        if (before == null) {
            addChild(child);
            return;
        }

        if (before.getParent() != this) {
            throw new ComponentException(this, "Before component does not belong to this parent");
        }

        int i = children.indexOf(before);
        addChild(child, i);
    }

    /**
     * Adds a list of children.
     *
     * @param children List of children to add.
     */
    public void addChildren(Collection<? extends BaseComponent> children) {
        for (BaseComponent child : children) {
            addChild(child);
        }
    }

    /**
     * Removes a child from this parent.
     *
     * @param child Child to remove.
     */
    public void removeChild(BaseComponent child) {
        _removeChild(child, false, false);
    }

    /**
     * Removes a child from this parent.
     *
     * @param child Child to remove.
     * @param noSync If true, don't sync change with client.
     * @param destroy If true, destroy the child once it is removed.
     */
    /*package*/ void _removeChild(BaseComponent child, boolean noSync, boolean destroy) {
        int index = children.indexOf(child);

        if (index == -1) {
            throw new ComponentException(this, "Child does not belong to this parent");
        }

        beforeRemoveChild(child);
        nameIndex.remove(child);
        child.parent = null;
        children.remove(child);

        if (!noSync) {
            invokeIfAttached("removeChild", child, destroy);
        }

        child.dead |= destroy;
        afterRemoveChild(child);
    }

    /**
     * Swap the position of two children.
     *
     * @param index1 Index of the first child.
     * @param index2 Index of the second child.
     */
    public void swapChildren(int index1, int index2) {
        BaseComponent child1 = children.get(index1);
        BaseComponent child2 = children.get(index2);
        children.set(index1, child2);
        children.set(index2, child1);
        invokeIfAttached("swapChildren", index1, index2);
    }

    /**
     * Adds a snippet into the component tree rooted at this component.
     *
     * @param snippet Snippet to add.
     */
    public void addSnippet(IComposite snippet) {
        String src = snippet.getCompositeSource();
        Assert.notNull(src, "A snippet must specify a source");
        String anchorName = snippet.getCompositeAnchor();
        CompositePosition position = snippet.getCompositePosition();
        Assert.notNull(position, "A snippet must specify a position");
        BaseComponent anchor = anchorName == null ? this : findByName(anchorName);
        Assert.notNull(anchor, "Could not locate any anchor named " + anchorName);
        PageDefinition def = PageUtil.getPageDefinition(src);
        BaseComponent parent = position == CompositePosition.FIRST || position == CompositePosition.LAST ? anchor
                : anchor.getParent();
        Assert.notNull(parent, "Anchor must have a parent for position value of " + position);
        int index = anchor.getIndex();

        switch (position) {
            case FIRST:
                addSnippetToParent(parent, 0, def);
                break;

            case LAST:
                addSnippetToParent(parent, -1, def);
                break;
                
            case PARENT:
                anchor.detach();
                BaseComponent newParent = addSnippetToParent(parent, index, def).get(0);
                anchor.setParent(newParent);
                break;
                
            case REPLACE:
                anchor.destroy();
                addSnippetToParent(parent, index, def);
                break;
                
            case BEFORE:
                addSnippetToParent(parent, index, def);
                break;
                
            case AFTER:
                addSnippetToParent(parent, index + 1, def);
                break;
        }
    }

    private List<BaseComponent> addSnippetToParent(BaseComponent parent, int index, PageDefinition def) {
        List<BaseComponent> children = def.materialize(null);

        for (BaseComponent child : children) {
            parent.addChild(child, index);
            index = index < 0 ? index : index + 1;
        }

        return children;
    }

    /**
     * Called before a new parent is set.
     *
     * @param newParent The new parent.
     */
    protected void beforeSetParent(BaseComponent newParent) {
    }

    /**
     * Called after a new parent is set.
     *
     * @param oldParent The old parent.
     */
    protected void afterSetParent(BaseComponent oldParent) {
    }

    /**
     * Called before a new child is added.
     *
     * @param child The new child.
     */
    protected void beforeAddChild(BaseComponent child) {
    }

    /**
     * Called after a new child is added.
     *
     * @param child The new child.
     */
    protected void afterAddChild(BaseComponent child) {
    }

    /**
     * Called before a child is removed.
     *
     * @param child The child to be removed.
     */
    protected void beforeRemoveChild(BaseComponent child) {
    }

    /**
     * Called after a child is removed.
     *
     * @param child The removed child.
     */
    protected void afterRemoveChild(BaseComponent child) {
    }

    /**
     * Returns an immutable list of existing children.
     *
     * @return List of existing children.
     */
    public final List<BaseComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Returns the list of existing children. Never directly modify the returned list.
     *
     * @return List of existing children.
     */
    protected final List<BaseComponent> _getChildren() {
        return children;
    }

    /**
     * Returns an iterable of children of the specified type.
     *
     * @param <T> The desired type.
     * @param type The desired type.
     * @return An iterable of children of the specified type. Never null.
     */
    public final <T extends BaseComponent> Iterable<T> getChildren(Class<T> type) {
        return MiscUtil.iterableForType(getChildren(), type);
    }

    /**
     * Returns the number of children.
     *
     * @return The number of children.
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * Returns true if the component has any children.
     *
     * @return True if the component has any children.
     */
    public boolean hasChildren() {
        return getChildCount() > 0;
    }
    
    /**
     * Returns the count of children of a specified type.
     *
     * @param type The desired type.
     * @return Count of children of the specified type.
     */
    public int getChildCount(Class<? extends BaseComponent> type) {
        int count = 0;

        for (BaseComponent child : children) {
            if (type.isInstance(child)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns true if this component may contain children.
     *
     * @return True if this component may contain children.
     */
    public boolean isContainer() {
        return componentDefinition.childrenAllowed();
    }

    /**
     * Return the first child of the requested type.
     *
     * @param <T> The type of child sought.
     * @param type The type of child sought.
     * @return The requested child, or null if none exist of the requested type.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseComponent> T getChild(Class<T> type) {
        for (BaseComponent child : children) {
            if (type.isInstance(child)) {
                return (T) child;
            }
        }

        return null;
    }

    /**
     * Returns the child at the specified index. If the index is out of bounds, returns null.
     *
     * @param index The index of the child sought.
     * @return Child at the specified index or null if the index is out of bounds.
     */
    public BaseComponent getChildAt(int index) {
        return index < 0 || index >= getChildCount() ? null : children.get(index);
    }

    /**
     * Returns the first child of this component.
     *
     * @return The first child, or null if no children.
     */
    public BaseComponent getFirstChild() {
        return getChildAt(0);
    }

    /**
     * Returns the last child of this component.
     *
     * @return The last child, or null if no children.
     */
    public BaseComponent getLastChild() {
        return getChildAt(getChildCount() - 1);
    }

    /**
     * Return the root component of this component's hierarchy.
     *
     * @return The root component of the hierarchy to which this component belongs.
     */
    public BaseComponent getRoot() {
        BaseComponent root = this;

        while (root.getParent() != null) {
            root = root.getParent();
        }

        return root;
    }

    /**
     * Return first ancestor that is of the requested type.
     *
     * @param <T> The type of ancestor sought.
     * @param type The type of ancestor sought.
     * @return The ancestor component of the requested type, or null if none found.
     */
    public <T extends BaseComponent> T getAncestor(Class<T> type) {
        return getAncestor(type, false);
    }

    /**
     * Return first ancestor that is of the requested type.
     *
     * @param <T> Expected return type.
     * @param type The type of ancestor sought.
     * @param includeSelf If true, include this component in the search.
     * @return The ancestor component of the requested type, or null if none found.
     */
    @SuppressWarnings("unchecked")
    public <T> T getAncestor(Class<T> type, boolean includeSelf) {
        BaseComponent cmp = includeSelf ? this : this.getParent();

        while (cmp != null) {
            if (type.isInstance(cmp)) {
                break;
            } else {
                cmp = cmp.getParent();
            }
        }

        return (T) cmp;
    }

    /**
     * Returns true if this component is the same as or an ancestor of the specified component.
     *
     * @param comp Component to test.
     * @return True if this component is the same as or an ancestor of the specified component.
     */
    public boolean isAncestor(BaseComponent comp) {
        while (comp != null && comp != this) {
            comp = comp.getParent();
        }

        return comp != null;
    }

    /**
     * Returns the index of this child within its parent.
     *
     * @return Index of this child within its parent. If the component has not parent, returns -1.
     */
    public int getIndex() {
        return getParent() == null ? -1 : getParent().children.indexOf(this);
    }

    /**
     * Moves this child to the specified index within its parent.
     *
     * @param index New index for this child.
     */
    public void setIndex(int index) {
        getParent().addChild(this, index);
    }

    /**
     * Return the next sibling for this component.
     *
     * @return The requested sibling, or null if not found.
     */
    public BaseComponent getNextSibling() {
        return getRelativeSibling(1);
    }

    /**
     * Return the previous sibling for this component.
     *
     * @return The requested sibling, or null if not found.
     */
    public BaseComponent getPreviousSibling() {
        return getRelativeSibling(-1);
    }

    /**
     * Returns the sibling of this component at the specified offset.
     *
     * @param offset Offset from this component. For example, 2 would mean the second sibling
     *            following this component.
     * @return The requested sibling, or null if none exists at the requested offset.
     */
    private BaseComponent getRelativeSibling(int offset) {
        int i = getIndex();
        i = i == -1 ? -1 : i + offset;
        return i < 0 || i >= getParent().getChildCount() ? null : getParent().children.get(i);
    }

    /**
     * Returns the namespace to which this component belongs. May be null.
     *
     * @return The namespace to which this component belongs.
     */
    public BaseComponent getNamespace() {
        BaseComponent comp = this;
        
        while (comp != null) {
            if (comp.isNamespace()) {
                return comp;
            }
            
            comp = comp.getParent();
        }
        
        return null;
    }

    /**
     * Returns true if this component is a namespace boundary.
     *
     * @return True if this component is a namespace boundary.
     */
    @PropertyGetter(value = "namespace", bindable = false, description = "When true, this component acts as a namespace boundary.")
    public boolean isNamespace() {
        return namespace;
    }
    
    /**
     * When set to true, this component defines a namespace boundary. This may not be changed once a
     * parent or children are added or for a component that implements the INamespace interface.
     *
     * @param namespace True to make component a namespace boundary.
     */
    @PropertySetter(value = "namespace", bindable = false, description = "When true, this component acts as a namespace boundary.")
    public void setNamespace(boolean namespace) {
        if (this.namespace != namespace) {
            if (this instanceof INamespace) {
                throw new ComponentException(this,
                        "You may not disable namespace support for a component that implements INamespace");
            }
            
            if (this.parent != null || this.getChildCount() > 0) {
                throw new ComponentException(this,
                        "You may not modify the namespace property if a component has a parent or any children.");
            }
            
            this.namespace = namespace;
        }
    }

    /**
     * Returns the page to which this component belongs.
     *
     * @return The owning page (may be null).
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets the page property for this component and its children.
     *
     * @param page The owning page.
     */
    private void _setPage(Page page) {
        validatePage(page);
        this.page = page;
        page.registerComponent(this, true);
        OptionMap props = new OptionMap();
        _initProps(props);
        page.getSynchronizer().createWidget(parent, props, inits);
        inits = null;

        for (BaseComponent child : children) {
            child._setPage(page);
        }
    }

    /**
     * Called when this component is first attached to a page.
     *
     * @param page The attached page.
     */
    protected void onAttach(Page page) {
    }

    /**
     * Validates that the specified page can be an owner of this component.
     *
     * @param page The page to be tested.
     * @exception ComponentException If fails validation.
     */
    protected void validatePage(Page page) {
        if (page != this.page && this.page != null) {
            throw new ComponentException(this, "Component cannot be assigned to a different page");
        }
    }

    /**
     * Attach this component and its children to their owning page.
     *
     * @param page Page to receive this component.
     */
    protected void _attach(Page page) {
        if (page != null && this.page != page) {
            _setPage(page);
            _flushQueue();
        }
    }

    /**
     * Flushes this component's invocation queue.
     */
    private void _flushQueue() {
        if (invocationQueue != null) {
            page.getSynchronizer().processQueue(invocationQueue);
            invocationQueue = null;
        }

        for (BaseComponent child : children) {
            child._flushQueue();
        }

        onAttach(page);
        fireEvent("attach");
    }

    /**
     * Initialize properties to be passed to widget factory. Override to add additional properties.
     *
     * @param props Properties for widget factory.
     */
    protected void _initProps(Map<String, Object> props) {
        props.put("id", id);
        props.put("wclass", componentDefinition.getWidgetClass());
        props.put("wmodule", componentDefinition.getWidgetModule());
        props.put("cntr", isContainer());
        props.put("nmsp", isNamespace() ? true : null);
    }

    /**
     * Synchronize a state value to the client.
     *
     * @param state The state name.
     * @param value The state value.
     */
    protected void sync(String state, Object value) {
        if (!dead) {
            if (getPage() == null) {
                if (inits == null) {
                    inits = new OptionMap();
                }

                inits.put(state, value);
            } else {
                page.getSynchronizer().invokeClient(this, "updateState", state, value, true);
            }
        }
    }

    /**
     * Invoke a widget function on the client.
     *
     * @param function The name of the function.
     * @param args Arguments for the function.
     */
    public void invoke(String function, Object... args) {
        invoke(function, null, args);
    }

    /**
     * Invoke a widget function on the client.
     *
     * @param function The name of the function.
     * @param callback Optional callback for invocation result.
     * @param args Arguments for the function.
     */
    public void invoke(String function, IResponseCallback<?> callback, Object... args) {
        if (!dead) {
            invoke(this, function, callback, args);
        }
    }

    /**
     * Invoke a widget function on the client only if attached to a page.
     *
     * @param function The name of the function.
     * @param args Arguments for the function.
     */
    public void invokeIfAttached(String function, Object... args) {
        if (page != null) {
            invoke(function, null, args);
        }
    }

    /**
     * Invoke a widget or sub-widget function on the client.
     *
     * @param id The id of the widget or sub-widget.
     * @param function The name of the function.
     * @param callback Optional callback for invocation result.
     * @param args Arguments for the function.
     */
    public void invoke(IElementIdentifier id, String function, IResponseCallback<?> callback, Object... args) {
        ClientInvocation invocation = new ClientInvocation(id, function, callback, args);

        if (page == null) {
            if (invocationQueue == null) {
                invocationQueue = new ClientInvocationQueue();
            }

            invocationQueue.queue(invocation);
        } else {
            page.getSynchronizer().sendToClient(invocation);
        }
    }

    /**
     * Invoke a widget or sub-widget function on the client.
     *
     * @param id The id of the widget or sub-widget.
     * @param function The name of the function.
     * @param args Arguments for the function.
     */
    public void invoke(IElementIdentifier id, String function, Object... args) {
        invoke(id, function, null, args);
    }

    /**
     * Looks up a component by its name within the namespace occupied by this component.
     *
     * @param name Component name or "/"-delimited path. "^" in path means parent namespace.
     * @return The component sought, or null if not found.
     */
    public BaseComponent findByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String[] pcs = name.replace('.', '/').split("\\/");
        BaseComponent cmp = this;
        int i = 0;

        while (i < pcs.length && cmp != null) {
            String pc = pcs[i++];

            if (pc.isEmpty()) {
                continue;
            }
            
            if ("^".equals(pc)) {
                cmp = cmp.getNamespace();

                if (i != pcs.length) {
                    cmp = cmp == null ? null : cmp.getParent();
                    cmp = cmp == null ? null : cmp.getNamespace();
                }
            } else {
                cmp = cmp.nameIndex.find(pc);
            }
        }

        return cmp;
    }
    
    /**
     * Returns a map of all named components in this namespace.
     *
     * @return A map of all named components in this namespace.
     */
    public Map<String, BaseComponent> findAllNamed() {
        return nameIndex.findAll();
    }

    /**
     * Looks up a component of the specified type by its name within the namespace occupied by this
     * component.
     *
     * @param <T> The expected return type.
     * @param name Component name or path.
     * @param type Expected return type.
     * @return The component sought, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseComponent> T findByName(String name, Class<T> type) {
        return (T) findByName(name);
    }

    /**
     * Find the first child containing the specified data object.
     *
     * @param data The data object to find.
     * @return The child with the data object, or null if not found.
     */
    public BaseComponent findChildByData(Object data) {
        for (BaseComponent child : children) {
            if (ObjectUtils.equals(data, child.getData())) {
                return child;
            }
        }

        return null;
    }

    /**
     * Find the first child whose label matches the specified value. This will only examine children
     * that implement the ILabeled interface.
     *
     * @param label The label to find.
     * @return The first child whose label matches, or null if none found.
     */
    public BaseComponent findChildByLabel(String label) {
        for (BaseComponent comp : children) {
            if (comp instanceof ILabeled && label.equals(((ILabeled) comp).getLabel())) {
                return comp;
            }
        }

        return null;
    }

    /**
     * Returns a subcomponent identifier.
     *
     * @param subId The sub identifier.
     * @return A subcomponent object.
     */
    public SubComponent sub(String subId) {
        return new SubComponent(this, subId);
    }

    /**
     * Causes one or more events to be forwarded. Multiple entries must be separated by a space.
     *
     * @param forwards One or more forwarding directives, multiple entries separated by spaces. Each
     *            entry is of the form <code>event=target</code> where <code>event</code> is the
     *            name of the event to forward and <code>target</code> is the forwarding target. The
     *            target is the name of the forwarded event optionally prefixed by a resolvable
     *            component name or path. In the absence of such a prefix, the target is the
     *            component itself. For example, <code>click=window.listbox.change</code> would
     *            forward this component's <code>click</code> event as a <code>change</code> event
     *            targeting the component that is the result of resolving the
     *            <code>window.listbox</code> path.
     */
    @PropertySetter(value = "forward", bindable = false, defer = true, description = "Sets one or more event forwarding directives.")
    private void setForward(String forwards) {
        forwards = trimify(forwards);

        if (forwards != null) {
            for (String forward : forwards.split("\\ ")) {
                if (!forward.isEmpty()) {
                    int i = forward.indexOf("=");

                    if (i <= 0) {
                        throw new IllegalArgumentException("Illegal forward directive:  " + forward);
                    }

                    String original = forward.substring(0, i);
                    forward = forward.substring(i + 1);
                    i = forward.lastIndexOf(".");
                    String name = i == -1 ? null : forward.substring(0, i);
                    forward = forward.substring(i + 1);
                    BaseComponent target = name == null ? this : findByName(name);

                    if (target == null) {
                        throw new ComponentException(this, "No component named \"%s\" found", name);
                    }

                    if (forward.isEmpty()) {
                        throw new IllegalArgumentException("No forward event specified");
                    }

                    addEventForward(original, target, forward);
                }
            }
        }
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component.
     *
     * @param eventType The event type to forward.
     * @param target The target for the forwarded event.
     */
    public void addEventForward(String eventType, BaseComponent target) {
        addEventForward(eventType, target, null);
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component, optionally with a different event type.
     *
     * @param eventType The event type to forward.
     * @param target The target for the forwarded event.
     * @param forwardType The type of the forwarded event. If null, the original event type is used.
     */
    public void addEventForward(String eventType, BaseComponent target, String forwardType) {
        addEventListener(eventType, createForwardListener(eventType, target, forwardType));
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component.
     *
     * @param eventClass The event type to forward.
     * @param target The target for the forwarded event.
     */
    public void addEventForward(Class<? extends Event> eventClass, BaseComponent target) {
        addEventForward(eventClass, target, null);
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component, optionally with a different event type.
     *
     * @param eventClass The event type to forward.
     * @param target The target for the forwarded event.
     * @param forwardType The type of the forwarded event. If null, the original event type is used.
     */
    public void addEventForward(Class<? extends Event> eventClass, BaseComponent target, String forwardType) {
        addEventForward(getEventType(eventClass), target, forwardType);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventType The source event type.
     * @param target The forwarded event target.
     */
    public void removeEventForward(String eventType, BaseComponent target) {
        removeEventForward(eventType, target, null);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventType The source event type.
     * @param target The forwarded event target.
     * @param forwardType The forwarded event type. If null, the source event type is used.
     */
    public void removeEventForward(String eventType, BaseComponent target, String forwardType) {
        removeEventListener(eventType, createForwardListener(eventType, target, forwardType));
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventClass The source event type.
     * @param target The forwarded event target.
     */
    public void removeEventForward(Class<? extends Event> eventClass, BaseComponent target) {
        removeEventForward(eventClass, target, null);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventClass The source event type.
     * @param target The forwarded event target.
     * @param forwardType The forwarded event type. If null, the source event type is used.
     */
    public void removeEventForward(Class<? extends Event> eventClass, BaseComponent target, String forwardType) {
        removeEventForward(getEventType(eventClass), target, forwardType);
    }

    private ForwardListener createForwardListener(String eventType, BaseComponent target, String forwardType) {
        return new ForwardListener(forwardType == null ? eventType : forwardType, target == null ? this : target);
    }

    /**
     * Returns true if this component has any listeners registered for the specified event type.
     *
     * @param eventType The event type.
     * @return True if any registered listeners exist.
     */
    public boolean hasEventListener(String eventType) {
        return eventListeners.hasListeners(eventType);
    }

    /**
     * Returns true if this component has any listeners registered for the specified event type.
     *
     * @param eventClass The event type.
     * @return True if any registered listeners exist.
     */
    public boolean hasEventListener(Class<? extends Event> eventClass) {
        return hasEventListener(getEventType(eventClass));
    }

    /**
     * Adds an event listener.
     *
     * @param eventType The type of event to listen for.
     * @param eventListener The listener for the event.
     */
    public void addEventListener(String eventType, IEventListener eventListener) {
        updateEventListener(eventType, eventListener, true, true);
    }

    /**
     * Adds an event listener.
     *
     * @param eventClass The type of event to listen for.
     * @param eventListener The listener for the event.
     */
    public void addEventListener(Class<? extends Event> eventClass, IEventListener eventListener) {
        updateEventListener(eventClass, eventListener, true, true);
    }

    /**
     * Adds an event listener.
     *
     * @param eventType The type of event to listen for.
     * @param eventListener The listener for the event.
     * @param syncToClient If true, notify the client that a listener has been added. The client
     *            will normally not send events to the server unless it knows that a listener
     *            exists.
     */
    public void addEventListener(String eventType, IEventListener eventListener, boolean syncToClient) {
        updateEventListener(eventType, eventListener, true, syncToClient);
    }

    /**
     * Adds an event listener.
     *
     * @param eventClass The type of event to listen for.
     * @param eventListener The listener for the event.
     * @param syncToClient If true, notify the client that a listener has been added. The client
     *            will normally not send events to the server unless it knows that a listener
     *            exists.
     */
    public void addEventListener(Class<? extends Event> eventClass, IEventListener eventListener, boolean syncToClient) {
        updateEventListener(eventClass, eventListener, true, syncToClient);
    }

    /**
     * Removes an event listener.
     *
     * @param eventType The type of event listened for.
     * @param eventListener The listener for the event.
     */
    public void removeEventListener(String eventType, IEventListener eventListener) {
        updateEventListener(eventType, eventListener, false, true);
    }

    /**
     * Removes an event listener.
     *
     * @param eventClass The type of event listened for.
     * @param eventListener The listener for the event.
     */
    public void removeEventListener(Class<? extends Event> eventClass, IEventListener eventListener) {
        updateEventListener(eventClass, eventListener, false, true);
    }

    /**
     * Removes an event listener.
     *
     * @param eventType The type of event listened for.
     * @param eventListener The listener for the event.
     * @param syncToClient If true, notify the client that a listener has been added. The client
     *            will normally not send events to the server unless it knows that a listener
     *            exists.
     */
    public void removeEventListener(String eventType, IEventListener eventListener, boolean syncToClient) {
        updateEventListener(eventType, eventListener, false, syncToClient);
    }

    /**
     * Removes an event listener.
     *
     * @param eventClass The type of event listened for.
     * @param eventListener The listener for the event.
     * @param syncToClient If true, notify the client that a listener has been added. The client
     *            will normally not send events to the server unless it knows that a listener
     *            exists.
     */
    public void removeEventListener(Class<? extends Event> eventClass, IEventListener eventListener, boolean syncToClient) {
        updateEventListener(eventClass, eventListener, false, syncToClient);
    }

    private void updateEventListener(Class<? extends Event> eventClass, IEventListener eventListener, boolean register,
                                     boolean syncToClient) {
        updateEventListener(getEventType(eventClass), eventListener, register, syncToClient);

    }

    private void updateEventListener(String eventTypes, IEventListener eventListener, boolean register,
                                     boolean syncToClient) {
        for (String eventType : eventTypes.split("\\ ")) {
            eventType = EventUtil.stripOn(eventType);
            boolean before = eventListeners.hasListeners(eventType);

            if (register) {
                eventListeners.add(eventType, eventListener);
            } else {
                eventListeners.remove(eventType, eventListener);
            }

            if (syncToClient && before != eventListeners.hasListeners(eventType)) {
                syncEventListeners(eventType, before);
            }
        }
    }

    private void syncEventListeners(String eventType, boolean remove) {
        invoke("forwardToServer", eventType, remove);
    }

    /**
     * Returns the event type given its implementation class, throwing an exception if not a
     * concrete class.
     *
     * @param eventClass The event class.
     * @return The event type (never null).
     */
    private String getEventType(Class<? extends Event> eventClass) {
        String eventType = EventUtil.getEventType(eventClass);

        if (eventType == null) {
            throw new IllegalArgumentException("Not a concrete event type: " + eventClass);
        }

        return eventType;
    }

    /**
     * Send an event to this component's registered event listeners.
     *
     * @param eventType Type of event to send.
     */
    public void fireEvent(String eventType) {
        fireEvent(EventUtil.toEvent(eventType, this, null));
    }

    /**
     * Send an event to this component's registered event listeners.
     *
     * @param event Event to send.
     */
    public void fireEvent(Event event) {
        eventListeners.invoke(event);
    }

    /**
     * Send an event to the client.
     *
     * @param eventType The event type.
     * @param data The event payload.
     */
    public void fireEventToClient(String eventType, Object data) {
        fireEventToClient(eventType, this, data);
    }
    
    /**
     * Send an event to the client.
     *
     * @param eventType The event type.
     * @param target The event target.
     * @param data The event payload.
     */
    public void fireEventToClient(String eventType, IElementIdentifier target, Object data) {
        OptionMap event = new OptionMap();
        event.put("type", eventType);
        event.put("data", data);
        event.put("target", target);
        invoke("trigger", event, null, true);
    }

    /**
     * Setter for on* event handlers.
     *
     * @param eventName The event name.
     * @param value Either a script component, a script string, or an event listener.
     */
    @PropertySetter("on:")
    private void setOnHandler(String eventName, Object value) {
        BaseScriptComponent script;

        if (value instanceof IEventListener) {
            addEventListener(eventName, (IEventListener) value);
            return;
        }
        
        if (value instanceof BaseScriptComponent) {
            script = (BaseScriptComponent) value;
        } else if (value instanceof String) {
            script = new ServerScript("groovy", value.toString());
            script.setMode(ExecutionMode.MANUAL);
            script.setSelf(this);
        } else {
            throw new ComponentException(this, "Illegal type (%s) for event handler \"%s\"", value.getClass(), eventName);
        }

        addEventListener(eventName, (event) -> {
            if (script.getPage() == null) {
                script.setParent(getPage());
            } else {
                script.validatePage(getPage());
            }

            Map<String, Object> variables = new HashMap<>();
            variables.put(script.getSelfName(), this);
            variables.put("controller", findAttribute(ATTR_CONTROLLER));
            variables.put("event", event);
            script.execute(variables);
        });
    }
    
    /**
     * Send an event to all the ancestors of this component. Event propagation stops if any
     * recipient invokes the <code>stopPropagation</code> method on the event.
     *
     * @param event Event to send.
     * @param includeThis If true, include this component in the recipient chain.
     */
    public void notifyAncestors(Event event, boolean includeThis) {
        BaseComponent next = includeThis ? this : getParent();

        while (next != null && !event.isStopped()) {
            next.fireEvent(event);
            next = next.getParent();
        }
    }

    /**
     * Send an event to all the descendants of this component using a depth-first traversal. Event
     * propagation stops if any recipient invokes the <code>stopPropagation</code> method on the
     * event.
     *
     * @param event Event to send.
     * @param includeThis If true, include this component in the recipient chain.
     */
    public void notifyDescendants(Event event, boolean includeThis) {
        for (BaseComponent child : children) {
            child.notifyDescendants(event, true);
        }

        if (includeThis && !event.isStopped()) {
            fireEvent(event);
        }
    }

    /**
     * Wires a controller's annotated components and event handlers, in that order, using this
     * component to resolve name references.
     *
     * @param controller The controller to wire. The following values are recognized:
     *            <ul>
     *            <li>self - Controller is the component itself.</li>
     *            <li>&lt;String&gt; - Name of the class from which a controller instance will be
     *            created.</li>
     *            <li>&lt;Class&gt; - The class from which a controller instance will be
     *            created.</li>
     *            <li>All other - The controller instance to be wired.</li>
     *            </ul>
     */
    @PropertySetter(value = "controller", bindable = false, defer = true, description = "Controller to be wired to this component.")
    public void wireController(Object controller) {
        if (controller == null) {
            throw new ComponentException("Controller is null or could not be resolved");
        }

        if (controller instanceof String) {
            try {
                controller = "self".equals(controller) ? this : Class.forName((String) controller);
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
        
        if (controller instanceof Class) {
            try {
                controller = ((Class<?>) controller).newInstance();
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        setAttribute(ATTR_CONTROLLER, controller);
        
        if (controller instanceof IAutoWired) {
            ((IAutoWired) controller).beforeInitialized(this);
        }

        WiredComponentScanner.wire(controller, this);
        EventHandlerScanner.wire(controller, this);
        controllers = controllers == null ? new ArrayList<>() : controllers;
        controllers.add(controller);
        
        if (controller instanceof IAutoWired) {
            ((IAutoWired) controller).afterInitialized(this);
        }
    }
    
    /**
     * Returns an immutable list of controllers wired to this component.
     *
     * @return List of controllers wired to this component (never null).
     */
    public List<Object> getControllers() {
        return controllers == null ? Collections.emptyList() : Collections.unmodifiableList(controllers);
    }

    /**
     * Returns a reference to the last controller wired to this component.
     *
     * @return The last controller wired to this component, or null if none.
     */
    public Object getController() {
        return controllers == null ? null : controllers.get(controllers.size() - 1);
    }

    /**
     * Override to cause a UI component to be brought to the forefront of the UI.
     */
    public void bringToFront() {
        if (getParent() != null) {
            getParent().bringToFront();
        }
    }

    /**
     * Converts empty string to null.
     *
     * @param value String value.
     * @return Original value or null if empty string.
     */
    protected String nullify(String value) {
        return value == null || value.isEmpty() ? null : value;
    }

    /**
     * Trims whitespace from a string and nullifies it.
     *
     * @param value String value.
     * @return Trimmed and nullified value.
     */
    protected String trimify(String value) {
        return value == null ? null : nullify(value.trim());
    }

    /**
     * Returns the input value if it is not null, or the default value otherwise.
     *
     * @param <T> The argument class.
     * @param value The input value.
     * @param deflt The default value.
     * @return Original value or the default if the input value was null.
     */
    protected <T> T defaultify(T value, T deflt) {
        return value == null ? deflt : value;
    }

    /**
     * Returns true if two objects are equal, allowing for null values.
     *
     * @param obj1 First object to compare.
     * @param obj2 Second object to compare.
     * @return True if the objects are equal.
     */
    protected boolean areEqual(Object obj1, Object obj2) {
        return ObjectUtils.equals(obj1, obj2);
    }

    /**
     * Returns the data object associated with the component.
     *
     * @return The data object; may be null.
     */
    @PropertyGetter(value = "data", description = "Data object to associate with this component.")
    public Object getData() {
        return data;
    }

    /**
     * Returns the data object associated with the component if it is of the specified type;
     * otherwise returns null.
     *
     * @param <T> Expected return type.
     * @param type The expected type.
     * @return The data object if it is of the expected type; null otherwise.
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> type) {
        return type.isInstance(data) ? (T) data : null;
    }

    /**
     * Sets the data object to be associated with the component.
     *
     * @param data The data object.
     */
    @PropertySetter(value = "data", description = "Data object to associate with this component.")
    public void setData(Object data) {
        propertyChange("data", this.data, this.data = data, false);
    }

    /**
     * Returns the text content associated with this component, if any.
     *
     * @return The text content.
     */
    @PropertyGetter("#text")
    protected String getContent() {
        return content;
    }

    /**
     * Sets the text content associated with this component.
     *
     * @param content The text content.
     */
    @PropertySetter("#text")
    protected void setContent(String content) {
        propertyChange("content", this.content, this.content = nullify(content), contentSynced);
    }

    /**
     * Returns true if the content property is to be synced to the client.
     *
     * @return True if the content property is to be synced to the client.
     */
    protected boolean isContentSynced() {
        return contentSynced;
    }
    
    /**
     * Set to true if the content property is to be synced to the client.
     *
     * @param contentSynced If true, the content property is synced to the client.
     */
    protected void setContentSynced(boolean contentSynced) {
        this.contentSynced = contentSynced;
    }
    
    /**
     * Handle state change events from the client. These events cause the field whose name matches
     * the state name to be directly updated with the new value. This is the principal mechanism by
     * which the client communicates simple state changes to the server. It should NOT be used on
     * generically typed fields.
     *
     * @param event The state change event.
     */
    @EventHandler(value = "statechange", syncToClient = false)
    private void _onStateChange(StatechangeEvent event) {
        String state = event.getState();

        try {
            Field field = FieldUtils.getField(this.getClass(), state, true);
            Object oldValue = field.get(this);
            Object newValue = ConvertUtil.convert(event.getValue(), field.getType(), this);
            field.set(this, newValue);
            propertyChange(state, oldValue, newValue, false);
        } catch (Exception e) {
            throw new ComponentException(e, "Error updating state: " + state);
        }
    }

    /**
     * Handle changes to published properties. If the old and new values are the same, no action is
     * taken. Otherwise, the client is notified of the new value (if syncToClient is true) and a
     * {@link PropertychangeEvent} is fired.
     *
     * @param propertyName The property name.
     * @param oldValue The old value.
     * @param newValue The new value.
     * @param syncToClient If true, notify client of change.
     * @return True if property value changed.
     */
    protected boolean propertyChange(String propertyName, Object oldValue, Object newValue, boolean syncToClient) {
        if (areEqual(oldValue, newValue)) {
            return false;
        }

        if (syncToClient) {
            sync(propertyName, newValue);
        }
        
        if (this.hasEventListener(PropertychangeEvent.TYPE)) {
            fireEvent(new PropertychangeEvent(this, propertyName, oldValue, newValue));
        }

        return true;
    }
    
    /**
     * Convenience method for programmatically adding a binding.
     *
     * @param propertyName Name of property in this component to be bound.
     * @param binding The binding to apply.
     */
    public void bind(String propertyName, IBinding binding) {
        getDefinition().setProperty(this, propertyName, binding);
    }
    
    /**
     * Monitors a component for destroy events, invoking #onTrackedDestroy when detected.
     *
     * @param comp Component to track.
     */
    protected void trackComponent(BaseComponent comp) {
        if (comp != null) {
            comp.validate();
            comp.addEventListener("destroy", trackedComponentListener);
        }
    }

    /**
     * Removes tracking for a component.
     *
     * @param comp Component to untrack.
     */
    protected void untrackComponent(BaseComponent comp) {
        if (comp != null) {
            comp.removeEventListener("destroy", trackedComponentListener);
        }
    }
    
    /**
     * Swap tracking from one component to another. This is a convenience method for calling
     * {@link #trackComponent} and {@link #untrackComponent} in succession.
     *
     * @param track Component to track.
     * @param untrack Component to untrack.
     */
    protected void swapTrackedComponents(BaseComponent track, BaseComponent untrack) {
        trackComponent(track);
        untrackComponent(untrack);
    }

    /**
     * Invoked when a tracked component is destroyed. Override to provide special handling.
     *
     * @param comp The tracked component.
     */
    protected void onDestroyTracked(BaseComponent comp) {
    }

    /**
     * Returns basic information about this component for display purposes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //@formatter:off
        sb.append(getClass().getName())
        .append(", ")
        .append("id: ")
        .append(id)
        .append(", ")
        .append("name: ")
        .append(name);
        //@formatter:on

        return sb.toString();
    }
}
