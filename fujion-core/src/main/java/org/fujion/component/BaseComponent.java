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

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.fujion.ancillary.*;
import org.fujion.ancillary.IComposite.CompositePosition;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.EventHandlerScanner;
import org.fujion.annotation.WiredComponentScanner;
import org.fujion.client.ClientInvocation;
import org.fujion.client.ClientInvocationQueue;
import org.fujion.client.ClientUtil;
import org.fujion.client.ExecutionContext;
import org.fujion.common.*;
import org.fujion.component.BaseScriptComponent.ExecutionMode;
import org.fujion.convert.ConversionService;
import org.fujion.event.*;
import org.fujion.model.IBinding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * The abstract base class for all components.
 */
public abstract class BaseComponent implements IElementIdentifier, IAttributeMap<String, Object>, IPropertyChangeObservable {

    /**
     * Reference to a subcomponent. A subcomponent typically does not have an explicit
     * implementation on the server, but does have a corresponding HTML element on the client. This
     * class exists to allow client invocations to be directed to that element.
     */
    public static class SubComponent implements IElementIdentifier {

        private final BaseComponent component;

        private final String subId;

        private SubComponent(
                BaseComponent component,
                String subId) {
            this.component = component;
            this.subId = subId;
        }

        @Override
        public String getId() {
            return component.getId() + "-" + subId;
        }

    }

    /**
     * Manages child components. The component list should only be manipulated through this wrapper,
     * never directly.
     */
    private static class ChildList implements List<BaseComponent> {

        private final LinkedList<BaseComponent> delegate = new LinkedList<>();

        private final BaseComponent parent;

        private int modCount;

        private ChildList(BaseComponent parent) {
            this.parent = parent;
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        @Override
        public Iterator<BaseComponent> iterator() {
            return new Iterator<>() {

                int next = 0;

                boolean fetched = false;

                int expected = modCount;

                @Override
                public boolean hasNext() {
                    checkModCount();
                    return next < delegate.size();
                }

                @Override
                public BaseComponent next() {
                    checkModCount();
                    fetched = true;
                    return delegate.get(next++);
                }

                @Override
                public void remove() {
                    checkModCount();
                    Assert.state(fetched, "No element to remove");
                    fetched = false;
                    ChildList.this.remove(--next);
                    expected = modCount;
                }

                private void checkModCount() {
                    if (modCount != expected) {
                        throw new ConcurrentModificationException();
                    }
                }
            };
        }

        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return delegate.toArray(a);
        }

        @Override
        public boolean remove(Object o) {
            remove((BaseComponent) o, false, false);
            return true;
        }

        protected void remove(
                BaseComponent child,
                boolean noSync,
                boolean destroy) {
            if (child instanceof IComposite) {
                BaseComponent root = ((IComposite) child).getCompositeRoot();
                BaseComponent parent = root == null ? null : root.getParent();

                if (parent != null) {
                    parent.children.remove(root, noSync, destroy);
                }

                child.parent = null;
                modCount++;
            } else {
                int index = indexOf(child);
                ComponentException.assertTrue(index != -1, parent, "Child does not belong to this parent");
                parent.beforeRemoveChild(child);
                parent.nameIndex.remove(child);
                child.parent = null;
                delegate.remove(child);
                modCount++;

                if (!noSync) {
                    parent.invokeIfAttached("removeChild", child, destroy);
                }

                child.dead |= destroy;
                parent.afterRemoveChild(child);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends BaseComponent> c) {
            return addAll(delegate.size(), c);
        }

        @Override
        public boolean addAll(
                int index,
                Collection<? extends BaseComponent> c) {
            for (BaseComponent child : c) {
                add(index++, child);
            }

            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            for (Object child : c) {
                remove(child);
            }

            return true;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            List<BaseComponent> remove = new ArrayList<>(delegate);
            remove.removeAll(c);
            return removeAll(remove);
        }

        @Override
        public void clear() {
            clear(false);
        }

        public void clear(boolean destroy) {
            while (!isEmpty()) {
                remove(get(0), false, destroy);
            }
        }

        @Override
        public BaseComponent get(int index) {
            return delegate.get(index);
        }

        @Override
        public BaseComponent set(
                int index,
                BaseComponent child) {
            BaseComponent old = get(index);
            old.detach();
            add(index, child);
            return old;
        }

        @Override
        public boolean add(BaseComponent child) {
            add(-1, child);
            return true;
        }

        @Override
        public void add(
                int index,
                BaseComponent child) {
            modCount++;

            if (child instanceof IComposite) {
                parent.addComposite((IComposite) child);
                child.parent = parent;
                return;
            }

            boolean noSync = child.getPage() == null && index < 0;
            child.validate();
            BaseComponent oldParent = child.getParent();

            if (oldParent != parent) {
                child.validateParent(parent);
                parent.validateChild(child);
                parent.nameIndex.validate(child);
            }

            child.validatePage(parent.page);

            if (oldParent == parent) {
                int i = child.getIndex();

                if (i == index) {
                    return;
                }

                if (index > i) {
                    index--;
                }
            } else {
                child.beforeSetParent(parent);
                parent.beforeAddChild(child);
            }

            if (oldParent != null) {
                oldParent.children.remove(child, true, false);
            }

            if (index < 0) {
                delegate.add(child);
            } else {
                delegate.add(index, child);
            }

            child.parent = parent;

            if (parent.page != null) {
                child._attach(parent.page);
            }

            parent.nameIndex.add(child);

            if (!noSync) {
                parent.invokeIfAttached("addChild", child, index);
            }

            if (oldParent != parent) {
                parent.afterAddChild(child);
                child.afterSetParent(parent);
            }
        }

        @Override
        public BaseComponent remove(int index) {
            BaseComponent old = get(index);
            remove(old);
            return old;
        }

        @Override
        public int indexOf(Object o) {
            return delegate.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return delegate.lastIndexOf(o);
        }

        @Override
        public ListIterator<BaseComponent> listIterator() {
            return IteratorUtils.toListIterator(iterator());
        }

        @Override
        public ListIterator<BaseComponent> listIterator(int index) {
            ListIterator<BaseComponent> iter = listIterator();

            while (iter.nextIndex() < index) {
                iter.next();
            }

            return iter;
        }

        @Override
        public List<BaseComponent> subList(
                int fromIndex,
                int toIndex) {
            throw new UnsupportedOperationException();
        }

        /**
         * Swap positions of two children.
         *
         * @param index1 Index of first child.
         * @param index2 Index of second child.
         */
        public void swap(
                int index1,
                int index2) {
            if (index1 != index2) {
                BaseComponent child1 = delegate.get(index1);
                BaseComponent child2 = delegate.get(index2);
                delegate.set(index1, child2);
                delegate.set(index2, child1);
                parent.invokeIfAttached("swapChildren", index1, index2);
                modCount++;
            }
        }

    }

    /**
     * Used to wrap a component that is referenced by another component for purposes other than
     * parent-child relationships. This wrapper serves two functions. First, it tracks the lifecycle
     * of the referenced component, removing the reference when the component is destroyed. Second,
     * when a component reference is passed to the synchronizer, this wrapper ensures that it is
     * connected to the active page.
     *
     * @param <T> The type of the reference component.
     */
    public class ComponentReference<T extends BaseComponent> implements IElementIdentifier, IEventListener {

        private final Consumer<BaseComponent> onDestroy;

        private T component;

        public ComponentReference() {
            this(null, null);
        }

        public ComponentReference(T component) {
            this(component, null);
        }

        public ComponentReference(Consumer<BaseComponent> onDestroy) {
            this(null, onDestroy);
        }

        public ComponentReference(
                T component,
                Consumer<BaseComponent> onDestroy) {
            this.onDestroy = onDestroy;
            setReference(component);
        }

        /**
         * Returns the referenced component.
         *
         * @return The referenced component (possibly null).
         */
        public T getReference() {
            return component;
        }

        /**
         * Sets the referenced component, removing any previous reference.
         *
         * @param component The referenced component.
         * @return True if the referenced component changed.
         */
        public boolean setReference(T component) {
            if (component != this.component) {
                if (component != null) {
                    component.validatePage(BaseComponent.this.page);
                    component.addEventListener("destroy", this);
                }

                removeReference();
                this.component = component;
                return true;
            }

            return false;
        }

        /**
         * Returns the id of the referenced component, or null if there is no referenced component.
         */
        @Override
        public String getId() {
            return component == null ? null : component.getId();
        }

        /**
         * Prior to performing the transform, ensure that the referenced component is attached to
         * the active page.
         */
        @Override
        public Object transformForClient() {
            if (component == null) {
                return null;
            }

            if (!component.isDead() && component.getPage() == null) {
                Page page = BaseComponent.this.page;
                component._attach(page != null ? page : ExecutionContext.getPage());
            }

            return IElementIdentifier.super.transformForClient();
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof ComponentReference) && ((ComponentReference<?>) o).component == component;
        }

        @Override
        protected void finalize() {
            removeReference();
        }

        /**
         * Handle the destroy event.
         *
         * @param event The destroy event.
         */
        @Override
        public void onEvent(Event event) {
            if (component == event.getTarget()) {
                removeReference();

                if (onDestroy != null) {
                    onDestroy.accept(event.getTarget());
                }
            }
        }

        /**
         * Remove the component reference.
         */
        private void removeReference() {
            if (component != null) {
                component.removeEventListener("destroy", this);
                component = null;
            }
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
         * @throws ComponentException Thrown if a name collision is detected.
         */
        public void validate(BaseComponent component) {
            _validate(component, getNameRoot());
        }

        private void _validate(
                BaseComponent component,
                BaseComponent root) {
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

        private void _validate(
                String name,
                BaseComponent root,
                BaseComponent component) {
            if (name != null) {
                BaseComponent cmp = _find(name, root);
                ComponentException.assertTrue(cmp == null || cmp == component,
                        "Name \"%s\"already exists in enclosing namespace", name);
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

        private BaseComponent _find(
                String name,
                BaseComponent root) {
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

        private void _findAll(
                BaseComponent root,
                Map<String, BaseComponent> results) {
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

    private static final String ATTR_CONTROLLER = "controller";

    private static final Pattern nameValidator = Pattern.compile("^[a-zA-Z$][a-zA-Z_$0-9]*$");

    private final ChildList children = new ChildList(this);

    private final Map<String, Object> attributes = new HashMap<>();

    private final EventListeners eventListeners = new EventListeners();

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final ComponentDefinition componentDefinition;

    private final NameIndex nameIndex = new NameIndex();

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

    private int initializing;

    private List<Object> controllers;

    /**
     * Validates that a component still exists (i.e., is not dead).
     *
     * @param comp Component to validate.
     * @throws ComponentException Thrown upon validation failure.
     */
    protected static void validate(BaseComponent comp) {
        ComponentException.assertTrue(comp == null || !comp.isDead(), "Component no longer exists: %s", comp);
    }

    /**
     * Returns true if the specified name is valid. A valid name starts with an alphabetic character
     * followed by any number of alphanumeric or underscore characters.
     *
     * @param name Name to validate.
     * @return True if name is valid.
     */
    public static boolean validateName(String name) {
        return nameValidator.matcher(name).matches();
    }

    /**
     * Create a component. Event handler annotations are processed at this time.
     */
    protected BaseComponent() {
        componentDefinition = ComponentRegistry.getInstance().get(getClass());
        namespace = this instanceof INamespace;
        EventHandlerScanner.wire(this, this, "init");
    }

    /**
     * Executes code in an initialization context.  When an initialization context is active,
     * certain behaviors may change.  For example, property synchronization to the client is
     * suppressed.  Initialization contexts are nestable, so an initializer may invoke another
     * initializer.
     *
     * @param initializer The initialization code.
     */
    protected void initialize(Runnable initializer) {
        try {
            initializing++;
            initializer.run();
        } finally {
            initializing--;
        }
    }

    /**
     * Returns true if an initialization context is active.
     *
     * @return True if an initialization context is active.
     */
    protected boolean isInitializing() {
        return initializing != 0;
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
    @PropertyGetter(value = "name", bindable = false, description = "The name associated with this component instance (must be unique within the enclosing namespace).")
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with this instance. Names must be unique within the enclosing
     * namespace.
     *
     * @param name The component's name.
     */
    @PropertySetter(value = "name", bindable = false, description = "The name associated with this component instance (must be unique within the enclosing namespace).")
    public void setName(String name) {
        if (!areEqual(name = nullify(name), this.name)) {
            _validateName(name);
            nameIndex.remove(this);
            propertyChange("name", this.name, this.name = name, true);
            nameIndex.add(this);
        }
    }

    private void _validateName(String name) {
        if (name != null) {
            ComponentException.assertTrue(validateName(name), this, "Component name \"%s\" is not valid", name);
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
        Assert.isNull(this.id, () -> "Unique id cannot be modified");
        this.id = id;
    }

    /**
     * Removes, but does not destroy, this component from its parent.
     */
    public void detach() {
        setParent(null);
    }

    /**
     * Detach all children under this component.
     */
    public void detachChildren() {
        children.clear();
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
            parent.children.remove(this, false, true);
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
    protected void finalize() throws Throwable {
        super.finalize();

        if (id != null) {
            destroy();
        }
    }

    /**
     * Destroy all children under this component.
     */
    public void destroyChildren() {
        children.clear(true);
    }

    /**
     * Set the disable state for all immediate children.  Affects only children implementing the IDisable interface.
     *
     * @param disable The disable state.
     */
    public void disableChildren(boolean disable) {
        disableChildren(disable, false);
    }

    /**
     * Set the disable state for all immediate children, optionally recursing through descendants.
     * Affects only children implementing the IDisable interface.
     *
     * @param disable The disable state.
     * @param recurse If true recurse through all descendants.
     */
    public void disableChildren(
            boolean disable,
            boolean recurse) {
        for (BaseComponent child : getChildren()) {
            if (child instanceof IDisable) {
                ((IDisable) child).setDisabled(disable);
            }

            if (recurse) {
                child.disableChildren(disable, true);
            }
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
     * @throws ComponentException Thrown if validation fails.
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
     * Sets the component's parent.
     *
     * @param parent The new parent.
     * @throws ComponentException Thrown if the new parent is not a valid parent for this
     *                            component.
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
     * Validates that a component would a valid parent for this component.
     *
     * @param parent Component to validate.
     * @throws ComponentException Thrown if the new parent is not a valid parent for this
     *                            component.
     */
    protected void validateParent(BaseComponent parent) {
        if (parent == null) {
            return;
        }

        componentDefinition.validateParent(parent.componentDefinition);
        ComponentException.assertTrue(!isAncestor(parent),
                "Not a valid parent because it is the same as or an descendant of this component");
    }

    /**
     * Returns the attribute map for this component.
     *
     * @return The attribute map.
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Returns the value of the named attribute, cast to the specified type.
     *
     * @param <T>  The attribute value's expected type.
     * @param name The attribute name.
     * @param dflt The default attribute value. This will be returned under one of the following
     *             conditions:
     *             <ul>
     *             <li>The named attribute does not exist</li>
     *             <li>The named attribute value is null</li>
     *             <li>The named attribute value cannot be cast to the specified type</li>
     *             </ul>
     * @return The value of the named attribute, or the default value.
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(
            String name,
            T dflt) {
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
     * @param <T>  The return type.
     * @param name The attribute name.
     * @param type The return type for the attribute value.
     * @return The value of the named attribute, or null if the existing value cannot be converted
     * to the specified type.
     */
    public <T> T getAttribute(
            String name,
            Class<T> type) {
        try {
            return ConversionService.getInstance().convert(attributes.get(name), type, this);
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
     * @param name  The attribute name.
     * @param value The new value.
     * @return The previous value of the named attribute, if any.
     */
    @PropertySetter("attr:")
    private Object _setAttribute(
            String name,
            Object value) {
        return setAttribute(name, value);
    }

    /**
     * Validates that the specified component is currently a child of this component.
     *
     * @param child The component to check.
     * @throws ComponentException Thrown if the component fails validation.
     */
    protected void validateIsChild(BaseComponent child) {
        ComponentException.assertTrue(child == null || child.getParent() == this, child,
                "Child does not belong to this parent");
    }

    /**
     * Validates that the specified component may be added as a child.
     *
     * @param child The component to check.
     * @throws ComponentException Thrown if the component fails validation.
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
        children.add(-1, child);
    }

    /**
     * Adds a child to the child list at the specified position.
     *
     * @param child Child to add.
     * @param index The position in the child list where the new child will be inserted.
     */
    public void addChild(
            BaseComponent child,
            int index) {
        children.add(index, child);
    }

    /**
     * Adds a child to the child list immediately before the reference child.
     *
     * @param child  Child to add.
     * @param before The reference child.
     */
    public void addChild(
            BaseComponent child,
            BaseComponent before) {
        if (before == null) {
            children.add(-1, child);
        } else {
            ComponentException.assertTrue(before.getParent() == this, this,
                    "Reference component does not belong to this parent");
            int i = children.indexOf(before);
            children.add(i, child);
        }
    }

    /**
     * Adds a list of children.
     *
     * @param children List of children to add.
     */
    public void addChildren(Collection<? extends BaseComponent> children) {
        this.children.addAll(children);
    }

    /**
     * Removes a child from this parent.
     *
     * @param child Child to remove.
     */
    public void removeChild(BaseComponent child) {
        children.remove(child);
    }

    /**
     * Swap the position of two children.
     *
     * @param index1 Index of the first child.
     * @param index2 Index of the second child.
     */
    public void swapChildren(
            int index1,
            int index2) {
        children.swap(index1, index2);
    }

    /**
     * Adds a composite component into the component tree rooted at this component.
     *
     * @param composite Composite to add.
     */
    public void addComposite(IComposite composite) {
        BaseComponent root = composite.getCompositeRoot();
        Assert.notNull(root, () -> "A snippet must specify a root");
        String anchorName = composite.getCompositeAnchor();
        CompositePosition position = composite.getCompositePosition();
        Assert.notNull(position, () -> "A snippet must specify a position");
        BaseComponent anchor = anchorName == null ? this : findByName(anchorName);
        Assert.notNull(anchor, () -> "Could not locate any anchor named " + anchorName);
        BaseComponent parent = position == CompositePosition.FIRST || position == CompositePosition.LAST ? anchor
                : anchor.getParent();
        Assert.notNull(parent, () -> "Anchor must have a parent for position value of " + position);
        root.detach();
        int index = anchor.getIndex();

        switch (position) {
            case FIRST:
                parent.addChild(root, 0);
                break;

            case LAST:
                parent.addChild(root, -1);
                break;

            case PARENT:
                anchor.detach();
                parent.addChild(root, index);
                anchor.setParent(root);
                break;

            case REPLACE:
                anchor.destroy();
                parent.addChild(root, index);
                break;

            case BEFORE:
                parent.addChild(root, index);
                break;

            case AFTER:
                parent.addChild(root, index + 1);
                break;
        }
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
        return children;
    }

    /**
     * Returns an iterable of children of the specified type.
     *
     * @param <T>  The desired type.
     * @param type The desired type.
     * @return An iterable of children of the specified type. Never null.
     */
    public final <T extends BaseComponent> Iterable<T> getChildren(Class<T> type) {
        return CollectionUtil.iterableForType(getChildren(), type);
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
        return !children.isEmpty();
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
     * Return the first child of the requested type.
     *
     * @param <T>  The type of child sought.
     * @param type The type of child sought.
     * @return The requested child, or null if none exist of the requested type.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseComponent> T getFirstChild(Class<T> type) {
        for (BaseComponent child : children) {
            if (type.isInstance(child)) {
                return (T) child;
            }
        }

        return null;
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
     * @param <T>  The type of ancestor sought.
     * @param type The type of ancestor sought.
     * @return The ancestor component of the requested type, or null if none found.
     */
    public <T extends BaseComponent> T getAncestor(Class<T> type) {
        return getAncestor(type, false);
    }

    /**
     * Return first ancestor that is of the requested type.
     *
     * @param <T>         Expected return type.
     * @param type        The type of ancestor sought.
     * @param includeSelf If true, include this component in the search.
     * @return The ancestor component of the requested type, or null if none found.
     */
    @SuppressWarnings("unchecked")
    public <T> T getAncestor(
            Class<T> type,
            boolean includeSelf) {
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
     * @return Index of this child within its parent. If the component has no parent, returns -1.
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
     * Return the next sibling of the requested type.
     *
     * @param <T>  The type of sibling sought.
     * @param type The type of sibling sought.
     * @return The requested sibling, or null if none exist of the requested type.
     */
    public <T extends BaseComponent> T getNextSibling(Class<T> type) {
        return getRelativeSibling(type, false);
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
     * Return the previous sibling of the requested type.
     *
     * @param <T>  The type of sibling sought.
     * @param type The type of sibling sought.
     * @return The requested sibling, or null if none exist of the requested type.
     */
    public <T extends BaseComponent> T getPreviousSibling(Class<T> type) {
        return getRelativeSibling(type, true);
    }

    /**
     * Returns the sibling of this component at the specified offset.
     *
     * @param offset Offset from this component. For example, 2 would mean the second sibling
     *               following this component.
     * @return The requested sibling, or null if none exists at the requested offset.
     */
    private BaseComponent getRelativeSibling(int offset) {
        int i = getIndex();
        i = i == -1 ? -1 : i + offset;
        return i < 0 || i >= getParent().getChildCount() ? null : getParent().getChildAt(i);
    }

    /**
     * Return the next/previous sibling of the requested type.
     *
     * @param <T>      The type of sibling sought.
     * @param type     The type of sibling sought.
     * @param previous If true, search for a previous sibling. Otherwise, search for next.
     * @return The requested sibling, or null if none exist of the requested type.
     */
    @SuppressWarnings("unchecked")
    private <T> T getRelativeSibling(
            Class<T> type,
            boolean previous) {
        BaseComponent sib = this;
        int offset = previous ? -1 : 1;

        while ((sib = sib.getRelativeSibling(offset)) != null) {
            if (type.isInstance(sib)) {
                return (T) sib;
            }
        }

        return null;
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
            ComponentException.assertTrue(!(this instanceof INamespace), this,
                    "You may not disable namespace support for a component that implements INamespace");
            ComponentException.assertTrue(parent == null && !hasChildren(), this,
                    "You may not modify the namespace property if a component has a parent or any children.");
            this.namespace = namespace;
        }
    }

    /**
     * Returns the page to which this component belongs.
     *
     * @return The owning page (possibly null).
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
     * @throws ComponentException If fails validation.
     */
    protected void validatePage(Page page) {
        ComponentException.assertTrue(page == this.page || this.page == null, this,
                "Component cannot be assigned to a different page");
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
    protected void sync(
            String state,
            Object value) {
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
     * @param args     Arguments for the function.
     */
    public void invoke(
            String function,
            Object... args) {
        invoke(function, null, args);
    }

    /**
     * Invoke a widget function on the client.
     *
     * @param function The name of the function.
     * @param callback Optional callback for invocation result.
     * @param args     Arguments for the function.
     */
    public void invoke(
            String function,
            IResponseCallback<?> callback,
            Object... args) {
        if (!dead) {
            invoke(this, function, callback, args);
        }
    }

    /**
     * Invoke a widget function on the client only if attached to a page.
     *
     * @param function The name of the function.
     * @param args     Arguments for the function.
     */
    public void invokeIfAttached(
            String function,
            Object... args) {
        if (page != null) {
            invoke(function, null, args);
        }
    }

    /**
     * Process a client invocation on behalf of this component. If the component is not yet attached
     * to a page, the invocation will be queued.
     *
     * @param invocation The client invocation to process.
     */
    public void invoke(ClientInvocation invocation) {
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
     * @param id       The id of the widget or sub-widget.
     * @param function The name of the function.
     * @param callback Optional callback for invocation result.
     * @param args     Arguments for the function.
     */
    public void invoke(
            IElementIdentifier id,
            String function,
            IResponseCallback<?> callback,
            Object... args) {
        invoke(new ClientInvocation(id, function, callback, args));
    }

    /**
     * Invoke a widget or sub-widget function on the client.
     *
     * @param id       The id of the widget or sub-widget.
     * @param function The name of the function.
     * @param args     Arguments for the function.
     */
    public void invoke(
            IElementIdentifier id,
            String function,
            Object... args) {
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

        String[] pcs = name.replace('.', '/').split("/");
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
     * @param <T>  The expected return type.
     * @param name Component name or path.
     * @param type Expected return type.
     * @return The component sought, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseComponent> T findByName(
            String name,
            Class<T> type) {
        return (T) findByName(name);
    }

    /**
     * Find the first child matching the specified criteria.
     *
     * @param criteria The logic that returns true if a match.
     * @return The matching child, or null if not found.
     */
    public BaseComponent findChild(Predicate<BaseComponent> criteria) {
        for (BaseComponent child : children) {
            if (criteria.test(child)) {
                return child;
            }
        }

        return null;
    }

    /**
     * Find the first child containing the specified data object.
     *
     * @param data The data object to find.
     * @return The child with the data object, or null if not found.
     */
    public BaseComponent findChildByData(Object data) {
        return findChild(child -> Objects.equals(data, child.getData()));
    }

    /**
     * Find the first child whose label matches the specified value. This will only examine children
     * that implement the ILabeled interface.
     *
     * @param label The label to find.
     * @return The first child whose label matches, or null if none found.
     */
    public BaseComponent findChildByLabel(String label) {
        return findChildByLabel(label, true);
    }

    /**
     * Find the first child whose label matches the specified value. This will only examine children
     * that implement the ILabeled interface.
     *
     * @param label         The label to find.
     * @param caseSensitive If true, comparison is case-sensitive.
     * @return The first child whose label matches, or null if none found.
     */
    public BaseComponent findChildByLabel(String label, boolean caseSensitive) {
        return findChild(child -> child instanceof ILabeled
                && StrUtil.areEqual(label, ((ILabeled) child).getLabel(), caseSensitive));
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
     *                 entry is of the form <code>event=target</code> where <code>event</code> is the
     *                 name of the event to forward and <code>target</code> is the forwarding target. The
     *                 target is the name of the forwarded event optionally prefixed by a resolvable
     *                 component name or path. In the absence of such a prefix, the target is the
     *                 component itself. For example, <code>click=window.listbox.change</code> would
     *                 forward this component's <code>click</code> event as a <code>change</code> event
     *                 targeting the component that is the result of resolving the
     *                 <code>window.listbox</code> path.
     */
    @PropertySetter(value = "forward", bindable = false, defer = true, description = "Sets one or more event forwarding directives.")
    private void setForward(String forwards) {
        forwards = trimify(forwards);

        if (forwards != null) {
            for (String forward : forwards.split(" ")) {
                if (!forward.isEmpty()) {
                    int i = forward.indexOf("=");
                    Assert.isTrue(i > 0, "Illegal forward directive:  %s", forward);
                    String original = forward.substring(0, i);
                    forward = forward.substring(i + 1);
                    i = forward.lastIndexOf(".");
                    String name = i == -1 ? null : forward.substring(0, i);
                    forward = forward.substring(i + 1);
                    BaseComponent target = name == null ? this : findByName(name);
                    ComponentException.assertTrue(target != null, this, "No component named \"%s\" found", name);
                    ComponentException.assertTrue(!forward.isEmpty(), this, "No forward event specified");
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
     * @param target    The target for the forwarded event.
     */
    public void addEventForward(
            String eventType,
            BaseComponent target) {
        addEventForward(eventType, target, null);
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component, optionally with a different event type.
     *
     * @param eventType   The event type to forward.
     * @param target      The target for the forwarded event.
     * @param forwardType The type of the forwarded event. If null, the original event type is used.
     */
    public void addEventForward(
            String eventType,
            BaseComponent target,
            String forwardType) {
        addEventListener(eventType, createForwardListener(eventType, target, forwardType));
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component.
     *
     * @param eventClass The event type to forward.
     * @param target     The target for the forwarded event.
     */
    public void addEventForward(
            Class<? extends Event> eventClass,
            BaseComponent target) {
        addEventForward(eventClass, target, null);
    }

    /**
     * Adds an event forward. An event forward forwards an event of the specified type received by
     * this component to another component, optionally with a different event type.
     *
     * @param eventClass  The event type to forward.
     * @param target      The target for the forwarded event.
     * @param forwardType The type of the forwarded event. If null, the original event type is used.
     */
    public void addEventForward(
            Class<? extends Event> eventClass,
            BaseComponent target,
            String forwardType) {
        addEventForward(getEventType(eventClass), target, forwardType);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventType The source event type.
     * @param target    The forwarded event target.
     */
    public void removeEventForward(
            String eventType,
            BaseComponent target) {
        removeEventForward(eventType, target, null);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventType   The source event type.
     * @param target      The forwarded event target.
     * @param forwardType The forwarded event type. If null, the source event type is used.
     */
    public void removeEventForward(
            String eventType,
            BaseComponent target,
            String forwardType) {
        removeEventListener(eventType, createForwardListener(eventType, target, forwardType));
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventClass The source event type.
     * @param target     The forwarded event target.
     */
    public void removeEventForward(
            Class<? extends Event> eventClass,
            BaseComponent target) {
        removeEventForward(eventClass, target, null);
    }

    /**
     * Removes an event forward, if one exists.
     *
     * @param eventClass  The source event type.
     * @param target      The forwarded event target.
     * @param forwardType The forwarded event type. If null, the source event type is used.
     */
    public void removeEventForward(
            Class<? extends Event> eventClass,
            BaseComponent target,
            String forwardType) {
        removeEventForward(getEventType(eventClass), target, forwardType);
    }

    private ForwardListener createForwardListener(
            String eventType,
            BaseComponent target,
            String forwardType) {
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
     * @param eventType     The type of event to listen for.
     * @param eventListener The listener for the event.
     */
    public void addEventListener(
            String eventType,
            IEventListener eventListener) {
        updateEventListener(eventType, eventListener, true, true);
    }

    /**
     * Adds an event listener.
     *
     * @param eventClass    The type of event to listen for.
     * @param eventListener The listener for the event.
     */
    public void addEventListener(
            Class<? extends Event> eventClass,
            IEventListener eventListener) {
        updateEventListener(eventClass, eventListener, true, true);
    }

    /**
     * Adds an event listener.
     *
     * @param eventType     The type of event to listen for.
     * @param eventListener The listener for the event.
     * @param syncToClient  If true, notify the client that a listener has been added. The client
     *                      will normally not send events to the server unless it knows that a listener
     *                      exists.
     */
    public void addEventListener(
            String eventType,
            IEventListener eventListener,
            boolean syncToClient) {
        updateEventListener(eventType, eventListener, true, syncToClient);
    }

    /**
     * Adds an event listener.
     *
     * @param eventClass    The type of event to listen for.
     * @param eventListener The listener for the event.
     * @param syncToClient  If true, notify the client that a listener has been added. The client
     *                      will normally not send events to the server unless it knows that a listener
     *                      exists.
     */
    public void addEventListener(
            Class<? extends Event> eventClass,
            IEventListener eventListener,
            boolean syncToClient) {
        updateEventListener(eventClass, eventListener, true, syncToClient);
    }

    /**
     * Removes an event listener.
     *
     * @param eventType     The type of event listened for.
     * @param eventListener The listener for the event.
     */
    public void removeEventListener(
            String eventType,
            IEventListener eventListener) {
        updateEventListener(eventType, eventListener, false, true);
    }

    /**
     * Removes an event listener.
     *
     * @param eventClass    The type of event listened for.
     * @param eventListener The listener for the event.
     */
    public void removeEventListener(
            Class<? extends Event> eventClass,
            IEventListener eventListener) {
        updateEventListener(eventClass, eventListener, false, true);
    }

    /**
     * Removes an event listener.
     *
     * @param eventType     The type of event listened for.
     * @param eventListener The listener for the event.
     * @param syncToClient  If true, notify the client that a listener has been added. The client
     *                      will normally not send events to the server unless it knows that a listener
     *                      exists.
     */
    public void removeEventListener(
            String eventType,
            IEventListener eventListener,
            boolean syncToClient) {
        updateEventListener(eventType, eventListener, false, syncToClient);
    }

    /**
     * Removes an event listener.
     *
     * @param eventClass    The type of event listened for.
     * @param eventListener The listener for the event.
     * @param syncToClient  If true, notify the client that a listener has been added. The client
     *                      will normally not send events to the server unless it knows that a listener
     *                      exists.
     */
    public void removeEventListener(
            Class<? extends Event> eventClass,
            IEventListener eventListener,
            boolean syncToClient) {
        updateEventListener(eventClass, eventListener, false, syncToClient);
    }

    private void updateEventListener(
            Class<? extends Event> eventClass,
            IEventListener eventListener,
            boolean register,
            boolean syncToClient) {
        updateEventListener(getEventType(eventClass), eventListener, register, syncToClient);

    }

    private void updateEventListener(
            String eventTypes,
            IEventListener eventListener,
            boolean register,
            boolean syncToClient) {
        for (String eventType : eventTypes.split(" ")) {
            eventType = EventUtil.stripOn(eventType);
            boolean before = eventListeners.hasListeners(eventType);

            if (register) {
                eventListeners.add(eventType, eventListener);
            } else {
                eventListeners.remove(eventType, eventListener);
            }

            if (!isInitializing() && syncToClient && before != eventListeners.hasListeners(eventType)) {
                syncEventListeners(eventType, before);
            }
        }
    }

    private void syncEventListeners(
            String eventType,
            boolean remove) {
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
        Assert.notNull(eventType, "Not a concrete event type: %s", eventClass);
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
     * @param data      The event payload.
     */
    public void fireEventToClient(
            String eventType,
            Object data) {
        fireEventToClient(eventType, this, data);
    }

    /**
     * Send an event to the client.
     *
     * @param eventType The event type.
     * @param target    The event target.
     * @param payload   The event payload.
     */
    public void fireEventToClient(
            String eventType,
            IElementIdentifier target,
            Object payload) {
        OptionMap event = new OptionMap();
        event.put("type", eventType);
        event.put("payload", payload);
        event.put("target", target);
        invoke("trigger", event, null, true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Setter for on* event handlers.
     *
     * @param eventName The event name.
     * @param value     Either a script component, a script string, or an event listener.
     */
    @PropertySetter("on:")
    private void setOnHandler(
            String eventName,
            Object value) {
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
            variables.put("controller", getLastController(true));
            variables.put("event", event);
            script.execute(variables);
        });
    }

    /**
     * Send an event to all the ancestors of this component. Event propagation stops if any
     * recipient invokes the <code>stopPropagation</code> method on the event.
     *
     * @param event       Event to send.
     * @param includeThis If true, include this component in the recipient chain.
     */
    public void notifyAncestors(
            Event event,
            boolean includeThis) {
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
     * @param event       Event to send.
     * @param includeThis If true, include this component in the recipient chain.
     */
    public void notifyDescendants(
            Event event,
            boolean includeThis) {
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
     *                   <ul>
     *                   <li>self - Controller is the component itself.</li>
     *                   <li>&lt;String&gt; - Name of the class from which a controller instance will be
     *                   created.</li>
     *                   <li>&lt;Class&gt; - The class from which a controller instance will be
     *                   created.</li>
     *                   <li>&lt;Collection&gt; - Each element of the collection will be wired.</li>
     *                   <li>All other - The controller instance to be wired.</li>
     *                   </ul>
     */
    @PropertySetter(value = "controller", bindable = false, defer = true, description = "Controller to be wired to this component.")
    public void wireController(Object controller) {
        wireController("", controller);
    }

    /**
     * Wires a controller's annotated components and event handlers, in that order, using this
     * component to resolve name references.
     *
     * @param mode       The wiring mode.
     * @param controller The controller to wire. The following values are recognized:
     *                   <ul>
     *                   <li>self - Controller is the component itself.</li>
     *                   <li>&lt;String&gt; - Name of the class from which a controller instance will be
     *                   created.</li>
     *                   <li>&lt;Class&gt; - The class from which a controller instance will be
     *                   created.</li>
     *                   <li>&lt;Collection&gt; - Each element of the collection will be wired.</li>
     *                   <li>All other - The controller instance to be wired.</li>
     *                   </ul>
     */
    @PropertySetter(value = "controller:", bindable = false, defer = true, description = "Controller to be wired to this component.")
    public void wireController(
            String mode,
            Object controller) {
        ComponentException.assertTrue(controller != null, this, "Controller is null or could not be resolved");

        if (controller instanceof Collection) {
            for (Object ctlr : (Collection<?>) controller) {
                wireController(mode, ctlr);
            }

            return;
        }

        if (controller instanceof String) {
            try {
                controller = "self".equals(controller) ? this : Class.forName((String) controller);
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        if (controller instanceof Class) {
            controller = MiscUtil.newInstance((Class<?>) controller);
        }

        setAttribute(ATTR_CONTROLLER, controller);

        if (controller instanceof IAutoWired) {
            ((IAutoWired) controller).beforeInitialized(this);
        }

        WiredComponentScanner.wire(controller, this, mode);
        EventHandlerScanner.wire(controller, this, mode);
        controllers = controllers == null ? new ArrayList<>() : controllers;
        controllers.add(controller);
        setAttribute(ATTR_CONTROLLER + "_" + controllers.size(), controller);

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
    public Object getLastController() {
        return controllers == null ? null : controllers.get(controllers.size() - 1);
    }

    /**
     * Returns a reference to the last controller wired to this component.
     *
     * @param recurse If true, recurse up component tree until a controller is found.
     * @return The last controller wired to this component, or null if none.
     */
    public Object getLastController(boolean recurse) {
        BaseComponent cmp = this;
        Object controller = null;

        while (controller == null && cmp != null) {
            controller = cmp.getLastController();
            cmp = recurse ? cmp.getParent() : null;
        }

        return controller;
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
     * @param <T>   The argument class.
     * @param value The input value.
     * @param deflt The default value.
     * @return Original value or the default if the input value was null.
     */
    protected <T> T defaultify(
            T value,
            T deflt) {
        return value == null ? deflt : value;
    }

    /**
     * Returns true if two objects are equal, allowing for null values.
     *
     * @param obj1 First object to compare.
     * @param obj2 Second object to compare.
     * @return True if the objects are equal.
     */
    protected boolean areEqual(
            Object obj1,
            Object obj2) {
        return Objects.equals(obj1, obj2);
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
     * Sets the data object to be associated with the component.
     *
     * @param data The data object.
     */
    @PropertySetter(value = "data", description = "Data object to associate with this component.")
    public void setData(Object data) {
        propertyChange("data", this.data, this.data = data, false);
    }

    /**
     * Returns the data object associated with the component if it is of the specified type;
     * otherwise returns null.
     *
     * @param <T>  Expected return type.
     * @param type The expected type.
     * @return The data object if it is of the expected type; null otherwise.
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> type) {
        return type.isInstance(data) ? (T) data : null;
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
    @EventHandler(value = "statechange", syncToClient = false, mode = "init")
    private void _onStateChange(StatechangeEvent event) {
        String state = event.getState();

        try {
            Field field = FieldUtils.getField(this.getClass(), state, true);
            Object oldValue = field.get(this);
            Object newValue = ConversionService.getInstance().convert(event.getValue(), field.getType(), this);
            field.set(this, newValue);
            propertyChange(state, oldValue, newValue, false);
        } catch (Exception e) {
            throw new ComponentException(e, this, "Error updating state \"%s\"", state);
        }
    }

    /**
     * Handle changes to published properties. If the old and new values are the same, no action is
     * taken. Otherwise, the client is notified of the new value (if syncToClient is true) and a
     * {@link java.beans.PropertyChangeEvent} is fired.
     *
     * @param propertyName The property name.
     * @param oldValue     The old value.
     * @param newValue     The new value.
     * @param syncToClient If true, notify client of change.
     * @return True if property value changed.
     */
    protected boolean propertyChange(
            String propertyName,
            Object oldValue,
            Object newValue,
            boolean syncToClient) {
        if (areEqual(oldValue, newValue)) {
            return false;
        }

        if (!isInitializing() && syncToClient) {
            sync(propertyName, newValue);
        }

        propertyName = StringUtils.removeStart(propertyName, "_");

        if (propertyChangeSupport.hasListeners(propertyName)) {
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }

        return true;
    }

    /**
     * Handle changes to published properties that are component references. If the old and new
     * values are the same, no action is taken. Otherwise, the client is notified of the new value
     * (if syncToClient is true) and a {@link java.beans.PropertyChangeEvent} is fired.
     *
     * @param <T>          The type of the referenced component.
     * @param propertyName The property name.
     * @param reference    The property reference object.
     * @param newValue     The new value.
     * @param syncToClient If true, notify client of change.
     * @return True if property value changed.
     */
    protected <T extends BaseComponent> boolean propertyChange(
            String propertyName,
            ComponentReference<T> reference,
            T newValue,
            boolean syncToClient) {
        T oldValue = reference.getReference();

        if (reference.setReference(newValue)) {
            propertyChange(propertyName, oldValue, newValue, false);

            if (!isInitializing() && syncToClient) {
                sync(propertyName, reference);
            }

            return true;
        }

        return false;
    }

    /**
     * Convenience method for programmatically adding a binding.
     *
     * @param propertyName Name of property in this component to be bound.
     * @param binding      The binding to apply.
     */
    public void bind(
            String propertyName,
            IBinding binding) {
        getDefinition().setProperty(this, propertyName, binding);
    }

    /**
     * Asynchronously loads the specified JavaScript module.
     *
     * @param module The module name or path.
     */
    public void loadModule(String module) {
        loadModule(module, null);
    }

    /**
     * Asynchronously loads the specified JavaScript module.
     *
     * @param module   The module name or path.
     * @param callback Optional callback to invoke upon completion.
     */
    public void loadModule(
            String module,
            IResponseCallback<?> callback) {
        ClientUtil.invoke(new ClientInvocation(module, "", callback));
    }

    /**
     * Returns basic information about this component for display purposes.
     */
    @Override
    public String toString() {
        return String.format("%s, id: %s, name: %s", getClass().getName(), id, name);
    }

}
