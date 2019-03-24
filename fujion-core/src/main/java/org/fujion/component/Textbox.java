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
 * A component for entering a single line of text.
 */
@Component(tag = "textbox", widgetClass = "Textbox", parentTag = "*", description = "A component for entering a single line of text.")
public class Textbox extends BaseInputboxComponent<String> {

    private boolean masked;

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
     * Returns true if input is to be obscured by a mask.
     *
     * @return True if input is to be obscured by a mask.
     */
    @PropertyGetter(value = "masked", description = "True if input is to be obscured by a mask.")
    public boolean isMasked() {
        return masked;
    }

    /**
     * Set to true if input is to be obscured by a mask.
     *
     * @param masked True if input is to be obscured by a mask.
     */
    @PropertySetter(value = "masked", defaultValue = "false", description = "True if input is to be obscured by a mask.")
    public void setMasked(boolean masked) {
        propertyChange("masked", this.masked, this.masked = masked, true);
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
