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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.springframework.util.Assert;

/**
 * Component for entering multiple lines of text.
 */
@Component(tag = "memobox", widgetClass = "Memobox", parentTag = "*", description = "Component for entering multiple lines of text.")
public class Memobox extends BaseInputboxComponent<String> {
    
    /**
     * Wrap mode for memo box.
     */
    public enum WrapMode {
        /**
         * Text is wrapped (contains newlines) when submitted in a form. When HARD is specified, the
         * cols property must also be specified.
         */
        HARD,
        /**
         * Text is not wrapped when submitted in a form.
         */
        SOFT
    }
    
    private boolean autoScroll;
    
    private WrapMode wrap = WrapMode.SOFT;
    
    private int rows = 2;

    private int cols = 20;
    
    public Memobox() {
        super();
        addStyle("resize", "none");
    }
    
    @Override
    @PropertyGetter(value = "synchronized", description = "A true value means that the client will notify the server "
            + "as the value of the input box changes. A false value means that the client will notify "
            + "server of the new value only when the input element loses focus.")
    public boolean getSynchronized() {
        return super.getSynchronized();
    }
    
    @Override
    @PropertySetter(value = "synchronized", defaultValue = "false", description = "A true value means that the client will notify the server "
            + "as the value of the input box changes. A false value means that the client will notify "
            + "server of the new value only when the input element loses focus.")
    public void setSynchronized(boolean synchronize) {
        super.setSynchronized(synchronize);
    }
    
    /**
     * Returns the auto-scroll setting. If true, the control will ensure that the last line of input
     * is always visible, scrolling if necessary.
     *
     * @return The auto-scroll setting.
     */
    @PropertyGetter(value = "autoScroll", description = "If true, the control will ensure that the last line of input "
            + "is always visible, scrolling if necessary.")
    public boolean isAutoScroll() {
        return autoScroll;
    }
    
    /**
     * Sets the auto-scroll setting. If true, the control will ensure that the last line of input is
     * always visible, scrolling if necessary.
     *
     * @param autoScroll The auto-scroll setting.
     */
    @PropertySetter(value = "autoScroll", defaultValue = "false", description = "If true, the control will ensure that the last line of input "
            + "is always visible, scrolling if necessary.")
    public void setAutoScroll(boolean autoScroll) {
        propertyChange("autoScroll", this.autoScroll, this.autoScroll = autoScroll, true);
    }
    
    /**
     * Returns the wrap mode.
     *
     * @return The wrap mode.
     * @see WrapMode
     */
    @PropertyGetter(value = "wrap", description = "Text wrapping behavior when submitting a form.")
    public WrapMode getWrap() {
        return wrap;
    }

    /**
     * Sets the wrap mode.
     *
     * @param wrap The wrap mode.
     * @see WrapMode
     */
    @PropertySetter(value = "wrap", defaultValue = "soft", description = "Text wrapping behavior when submitting a form.")
    public void setWrap(WrapMode wrap) {
        propertyChange("wrap", this.wrap, this.wrap = defaultify(wrap, WrapMode.SOFT), true);
    }
    
    /**
     * Returns the visible width of the input area in characters. The default is 20 characters. Also
     * affects the line break position when the wrap mode is set to HARD.
     *
     * @return The visible width of the input area in characters.
     */
    @PropertyGetter(value = "cols", description = "The visible width of the input area in characters.")
    public int getCols() {
        return cols;
    }

    /**
     * Sets the visible width of the input area in characters. The default is 20 characters. Also
     * affects the line break position when the wrap mode is set to HARD.
     *
     * @param cols The visible width of the input area in characters.
     */
    @PropertySetter(value = "cols", defaultValue = "20", description = "The visible width of the input area in characters.")
    public void setCols(int cols) {
        Assert.isTrue(cols > 0, "Cols must be greater than zero");
        propertyChange("cols", this.cols, this.cols = cols, true);
    }
    
    /**
     * Returns the visible number of rows in the input area. The default is 2 rows.
     *
     * @return The visible number of rows in the input area.
     */
    @PropertyGetter(value = "rows", description = "The visible number of rows in the input area.")
    public int getRows() {
        return rows;
    }

    /**
     * Sets the visible number of rows in the input area. The default is 2 rows.
     *
     * @param rows The visible number of rows in the input area.
     */
    @PropertySetter(value = "rows", defaultValue = "2", description = "The visible number of rows in the input area.")
    public void setRows(int rows) {
        Assert.isTrue(rows > 0, "Rows must be greater than zero");
        propertyChange("rows", this.rows, this.rows = rows, true);
    }
    
    @Override
    protected String _toValue(String value) {
        return value;
    }
    
    @Override
    protected String _toString(String value) {
        return value;
    }

}
