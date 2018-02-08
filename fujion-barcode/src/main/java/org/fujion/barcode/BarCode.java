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
package org.fujion.barcode;

import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;
import org.springframework.util.StringUtils;

/**
 * Fujion wrapper for CodeMirror JavaScript editor.
 */
@Component(tag = "barcode", widgetModule = "fujion-barcode", widgetClass = "BarCode", parentTag = "*", description = "Barcode component.")
public class BarCode extends BaseUIComponent {

    public enum Format {

        CODABAR, CODE39, CODE128, CODE128A, CODE128B, CODE128C, EAN2, EAN5, EAN8, EAN13, ITF, ITF14, MSI, MSI10, MSI11, MSI1010, MSI1110, PHARMACODE, UPC, UPCE;

        public boolean validate(String value) {
            if (StringUtils.isEmpty(value)) {
                return true;
            }

            switch (this) {
                case CODABAR:
                    return Pattern.matches("^[A-D][0-9\\+$:\\-/.]*[A-D]$", value);
                
                case CODE39:
                    return Pattern.matches("^[0-9 a-z A-Z - . $ / + %]*$", value);
                
                case CODE128:
                case CODE128A:
                case CODE128B:
                case CODE128C:
                    return true;

                case EAN2:
                    return Pattern.matches("^\\d{2}$", value);

                case EAN5:
                    return Pattern.matches("^\\d{5}$", value);

                case EAN8:
                    return Pattern.matches("^\\d{7}$", value);

                case EAN13:
                    return Pattern.matches("^\\d{12}$", value);
                
                case ITF:
                    return Pattern.matches("^\\d{14}$", value);
                
                case ITF14:
                    return Pattern.matches("^\\d{13}$", value);
                
                case MSI:
                case MSI10:
                case MSI11:
                case MSI1010:
                case MSI1110:
                    return Pattern.matches("^\\d*$", value);
                
                case PHARMACODE:
                    int i = NumberUtils.toInt(value);
                    return i >= 3 && i <= 131070;
                
                case UPC:
                    return Pattern.matches("^\\d{12}$", value);
                
                case UPCE:
                    return Pattern.matches("^\\d{6}$", value);
                
                default:
                    return false;
            }
        }

    }

    private Format format;

    private boolean displayValue;

    private String value;

    private boolean flat;
    
    public BarCode() {
        this(Format.CODE128);
    }

    public BarCode(Format format) {
        this.format = format;
    }

    private void validateValue(String value) {
        if (!format.validate(value)) {
            throw new IllegalArgumentException(
                    String.format("Value '%s' is not valid for the format type '%s'", value, format.name()));
        }
    }
    
    @PropertyGetter(value = "format", description = "The barcode format.")
    public Format getFormat() {
        return format;
    }
    
    @PropertySetter(value = "format", defaultValue = "code128", description = "The barcode format.")
    public void setFormat(Format format) {
        format = format == null ? Format.CODE128 : format;

        if (propertyChange("format", this.format, this.format = format, true)) {
            setValue(null);
        }
    }
    
    @PropertyGetter(value = "displayValue", description = "If true, display the value in plain text.")
    public boolean getDisplayValue() {
        return displayValue;
    }
    
    @PropertySetter(value = "displayValue", description = "If true, display the value in plain text.")
    public void setDisplayValue(boolean displayValue) {
        propertyChange("displayValue", this.displayValue, this.displayValue = displayValue, true);
    }
    
    @PropertyGetter(value = "value", description = "The value to encode.")
    public String getValue() {
        return value;
    }
    
    @PropertySetter(value = "value", defer = true, description = "The value to encode.")
    public void setValue(String value) {
        validateValue(value = nullify(value));
        propertyChange("value", this.value, this.value = value, true);
    }
    
    @PropertyGetter(value = "flat", description = "Affect EAN-8 and EAN-13 formats only.")
    public boolean isFlat() {
        return flat;
    }
    
    @PropertySetter(value = "flat", description = "Affect EAN-8 and EAN-13 formats only.")
    public void setFlat(boolean flat) {
        propertyChange("flat", this.flat, this.flat = flat, true);
    }
    
}
