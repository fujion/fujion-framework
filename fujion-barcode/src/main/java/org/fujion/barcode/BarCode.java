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
package org.fujion.barcode;

import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;
import org.springframework.util.StringUtils;

/**
 * Fujion wrapper for JsBarcode library.
 */
@Component(tag = "barcode", widgetModule = "fujion-barcode", widgetClass = "BarCode", parentTag = "*", description = "Barcode component.")
public class BarCode extends BaseUIComponent {
    
    private static final Function<String, Boolean> PHARMACODE_VALIDATOR = value -> {
        int i = NumberUtils.toInt(value);
        return i >= 3 && i <= 131070;
    };
    
    /**
     * Supported barcode formats.
     */
    public enum Format {
        
        // @formatter:off
        CODABAR("^[A-D][0-9\\+$:\\-/.]*[A-D]$"),
        CODE39("^[0-9 a-z A-Z - . $ / + %]*$"),
        CODE128(),
        CODE128A(),
        CODE128B(),
        CODE128C(),
        EAN2("^\\d{2}$"),
        EAN5("^\\d{5}$"),
        EAN8("^\\d{7}$"),
        EAN13("^\\d{12}$"),
        ITF("^\\d{14}$"),
        ITF14("^\\d{13}$"),
        MSI("^\\d*$"),
        MSI10("^\\d*$"),
        MSI11("^\\d*$"),
        MSI1010("^\\d*$"),
        MSI1110("^\\d*$"),
        PHARMACODE(PHARMACODE_VALIDATOR),
        QR(),
        UPC("^\\d{12}$"),
        UPCE("^\\d{6}$");
        // @formatter:on
        
        private final Function<String, Boolean> validator;
        
        Format() {
            validator = null;
        }
        
        Format(String regex) {
            Pattern pattern = Pattern.compile(regex);
            validator = value -> pattern.matcher(value).matches();
        }
        
        Format(Function<String, Boolean> validator) {
            this.validator = validator;
        }
        
        /**
         * Determine if value is valid for the corresponding barcode format.
         *
         * @param value Value to validate.
         * @return True if valid.
         */
        public boolean validate(String value) {
            return StringUtils.isEmpty(value) || validator == null || validator.apply(value);
        }
        
    }
    
    private Format format = Format.CODE128;
    
    private boolean displayValue;
    
    private String value;
    
    private boolean flat;
    
    public BarCode() {
    }
    
    public BarCode(Format format) {
        setFormat(format);
    }
    
    /**
     * Validate a value, throwing an exception if validation fails.
     *
     * @param value Value to validate.
     */
    private void validateValue(String value) {
        if (!format.validate(value)) {
            throw new IllegalArgumentException(
                String.format("Value \"%s\" is not valid for the format type \"%s\"", value, format.name()));
        }
    }
    
    /**
     * Returns the barcode format.
     *
     * @return The barcode format.
     */
    @PropertyGetter(value = "format", description = "The barcode format.")
    public Format getFormat() {
        return format;
    }
    
    /**
     * Sets the barcode format. Resets the value property to null.
     *
     * @param format The barcode format.
     */
    @PropertySetter(value = "format", defaultValue = "code128", description = "The barcode format.")
    public void setFormat(Format format) {
        format = defaultify(format, Format.CODE128);
        
        if (propertyChange("format", this.format, this.format = format, true)) {
            setValue(null);
        }
    }
    
    /**
     * Returns true if the value is to be displayed next to the barcode.
     *
     * @return True if the value is to be displayed next to the barcode.
     */
    @PropertyGetter(value = "displayValue", description = "If true, display the value in plain text.")
    public boolean getDisplayValue() {
        return displayValue;
    }
    
    /**
     * Set to true if the value is to be displayed next to the barcode.
     *
     * @param displayValue True if the value is to be displayed next to the barcode.
     */
    @PropertySetter(value = "displayValue", description = "If true, display the value in plain text.")
    public void setDisplayValue(boolean displayValue) {
        propertyChange("displayValue", this.displayValue, this.displayValue = displayValue, true);
    }
    
    /**
     * Returns the value to be encoded.
     *
     * @return The value to be encoded.
     */
    @PropertyGetter(value = "value", description = "The value to encode.")
    public String getValue() {
        return value;
    }
    
    /**
     * Sets the value to be encoded. The value is tested for validity for the current barcode
     * format.
     *
     * @param value The value to be encoded.
     * @throws IllegalArgumentException If value fails validation.
     */
    @PropertySetter(value = "value", defer = true, description = "The value to encode.")
    public void setValue(String value) {
        validateValue(value = trimify(value));
        propertyChange("value", this.value, this.value = value, true);
    }
    
    /**
     * Returns whether or not to render guard bars.
     *
     * @return True to suppress rendering of guard bars.
     */
    @PropertyGetter(value = "flat", description = "If true, suppresses rendering of guard bars.")
    public boolean isFlat() {
        return flat;
    }
    
    /**
     * Set to true to suppress rendering of guard bars. For formats that do not use guard bars, this
     * setting has no effect.
     *
     * @param flat True to suppress rendering of guard bars.
     */
    @PropertySetter(value = "flat", description = "If true, suppresses rendering of guard bars.")
    public void setFlat(boolean flat) {
        propertyChange("flat", this.flat, this.flat = flat, true);
    }
    
}
