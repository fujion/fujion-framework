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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.MiscUtil;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

/**
 * A base class for components that allow the selection of an item from a collection of items.
 *
 * @param <T> The type of item within the collection of choices.
 * @param <P> The type of child picker item component.
 */
public abstract class BasePickerComponent<T, P extends BasePickerItem<T>> extends BaseInputboxComponent<T> implements ISupportsModel<P> {
    
    private final ModelAndView<P, Object> modelAndView = new ModelAndView<>(this);
    
    private boolean showText;
    
    private boolean showHints;
    
    private P converter;
    
    protected BasePickerComponent(Class<P> itemClass) {
        try {
            converter = itemClass.newInstance();
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Returns showText property.
     *
     * @return If true, the text representation of the selection is displayed.
     */
    @PropertyGetter(value = "showText", description = "If true, the text representation of the selection is displayed.")
    public boolean getShowText() {
        return showText;
    }
    
    /**
     * Sets showText property.
     *
     * @param showText If true, the text representation of the selection is displayed.
     */
    @PropertySetter(value = "showText", defaultValue = "false", description = "If true, the text representation of the selection is displayed.")
    public void setShowText(boolean showText) {
        propertyChange("showText", this.showText, this.showText = showText, true);
    }
    
    /**
     * Returns the showHints property.
     *
     * @return If true, the text representation of pick list items is displayed when hovering over
     *         them.
     */
    @PropertyGetter(value = "showHints", description = "If true, the text representation of pick list items is displayed when hovering over them.")
    public boolean getShowHints() {
        return showHints;
    }
    
    /**
     * Sets the showHints property.
     *
     * @param showHints If true, the text representation of pick list items is displayed when
     *            hovering over them.
     */
    @PropertySetter(value = "showHints", defaultValue = "false", description = "If true, the text representation of pick list items is displayed when hovering over them.")
    public void setShowHints(boolean showHints) {
        propertyChange("showHints", this.showHints, this.showHints = showHints, true);
    }
    
    @Override
    protected T _toValue(String text) {
        return converter._toValue(text);
    }
    
    @Override
    protected String _toString(T value) {
        return converter._toString(value);
    }
    
    @Override
    public IModelAndView<P, ?> getModelAndView() {
        return modelAndView;
    }
    
}
