/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A simple button component.
 */
@Component(tag = "button", widgetClass = "Button", parentTag = "*", description = "A simple button component.")
public class Button extends BaseLabeledImageComponent<BaseLabeledComponent.LabelPositionAll> {

    public Button() {
        this(null);
    }

    public Button(String label) {
        this(label, "flavor:btn-info size:btn-sm");
    }

    protected Button(String label, String clazz) {
        super(label);
        addClass(clazz);
        initialize(() -> setPosition(LabelPositionAll.BOTTOM));
    }

    /**
     * Returns the position of the label relative to the image. Defaults to 'left'.
     *
     * @return May be one of: left, right, top, bottom.
     */
    @Override
    @PropertyGetter(value = "position", description = "The position of the label relative to the image.")
    public LabelPositionAll getPosition() {
        return super.getPosition();
    }

    /**
     * Sets the position of the label relative to the image.
     *
     * @param position May be one of: left, right, top, bottom.
     */
    @Override
    @PropertySetter(value = "position", defaultValue = "bottom", description = "The position of the label relative to the image.")
    public void setPosition(LabelPositionAll position) {
        super.setPosition(position);
    }

}
