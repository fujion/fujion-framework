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

import org.fujion.ancillary.ILabeled;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * The base class for components that have an associated label.
 *
 * @param <P> The type of label positioning that is supported.
 */
public abstract class BaseLabeledComponent<P extends BaseLabeledComponent.ILabelPosition> extends BaseUIComponent implements ILabeled {
    
    /**
     * Position specifier for label.
     */
    public interface ILabelPosition {}

    /**
     * Horizontal position specifier.
     */
    public enum LabelPositionHorz implements ILabelPosition {
        RIGHT, LEFT
    }
    
    /**
     * Vertical position specifier.
     */
    public enum LabelPositionVert implements ILabelPosition {
        TOP, BOTTOM
    }
    
    /**
     * Specifier for all label positions.
     */
    public enum LabelPositionAll implements ILabelPosition {
        RIGHT, LEFT, TOP, BOTTOM
    }
    
    /**
     * Specifier for fixed label position.
     */
    public enum LabelPositionNone implements ILabelPosition {}
    
    /**
     * Alignment of label relative to child component(s).
     */
    public enum LabelAlignment {
        START, CENTER, END
    }

    private String label;
    
    private P position;
    
    private LabelAlignment alignment = LabelAlignment.CENTER;

    protected BaseLabeledComponent() {
    }
    
    protected BaseLabeledComponent(String label) {
        setLabel(label);
    }
    
    @Override
    @PropertyGetter(value = "label", description = "The text label.")
    public String getLabel() {
        return label;
    }
    
    @Override
    @PropertySetter(value = "label", description = "The text label.")
    public void setLabel(String label) {
        propertyChange("label", this.label, this.label = nullify(label), true);
    }
    
    /**
     * Returns the alignment of the label. Defaults to 'center'.
     *
     * @return May be one of start, center, end.
     */
    protected LabelAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment of the label.
     *
     * @param alignment May be one of: start, center, end.
     */
    protected void setAlignment(LabelAlignment alignment) {
        propertyChange("alignment", this.alignment, this.alignment = defaultify(alignment, LabelAlignment.CENTER), true);
    }

    /**
     * Returns the label position.
     *
     * @return The label position.
     */
    protected P getPosition() {
        return position;
    }
    
    /**
     * Sets the label position.
     *
     * @param position The label position.
     */
    protected void setPosition(P position) {
        propertyChange("position", this.position, this.position = position, true);
    }
    
}
