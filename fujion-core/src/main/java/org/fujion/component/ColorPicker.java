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

import java.awt.Color;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.common.ColorUtil;

/**
 * A component for selecting a color from a palette of colors.
 */
@Component(tag = "colorpicker", widgetModule = "fujion-picker", widgetClass = "Colorpicker", parentTag = "*", childTag = @ChildTag("colorpickeritem"), description = "A component for selecting a color from a palette of colors.")
public class ColorPicker extends BasePickerComponent<Color, ColorPicker.ColorPickeritem> {

    /**
     * A color selection for color picker.
     */
    @Component(tag = "colorpickeritem", widgetModule = "fujion-picker", widgetClass = "Colorpickeritem", parentTag = "colorpicker", description = "A color choice.")
    public static class ColorPickeritem extends BasePickerItem<Color> {

        public ColorPickeritem() {
            super();
        }

        public ColorPickeritem(Color color) {
            super(color);
        }

        @Override
        protected String _toString(Color value) {
            return ColorUtil.toString(value);
        }

        @Override
        protected Color _toValue(String text) {
            return ColorUtil.toColor(text);
        }

    }

    public ColorPicker() {
        super(ColorPickeritem.class);
    }

}
