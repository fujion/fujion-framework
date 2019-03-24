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
package org.fujion.captcha;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

/**
 * Fujion wrapper for Google reCAPTCHA library.
 */
@Component(tag = "captcha", widgetModule = "fujion-captcha", widgetClass = "Captcha", parentTag = "*", description = "Captcha component.")
public class Captcha extends BaseUIComponent {
    
    public enum CaptchaTheme {
        DARK, LIGHT
    }

    public enum CaptchaType {
        AUDIO, IMAGE
    }

    public enum CaptchaSize {
        COMPACT, NORMAL
    }

    private CaptchaTheme theme = CaptchaTheme.LIGHT;

    private CaptchaType type = CaptchaType.IMAGE;

    private CaptchaSize size = CaptchaSize.NORMAL;

    private String siteKey;

    @PropertyGetter(value = "siteKey", description = "The site key.")
    public String getSiteKey() {
        return siteKey;
    }

    @PropertySetter(value = "siteKey", description = "The site key.")
    public void setSiteKey(String siteKey) {
        propertyChange("siteKey", this.siteKey, this.siteKey = trimify(siteKey), true);
    }

    @PropertyGetter(value = "theme", description = "The CAPTCHA theme.")
    public CaptchaTheme getTheme() {
        return theme;
    }

    @PropertySetter(value = "theme", description = "The CAPTCHA theme.")
    public void setTheme(CaptchaTheme theme) {
        propertyChange("theme", this.theme, this.theme = theme, true);
    }

    @PropertyGetter(value = "type", description = "The CAPTCHA type.")
    public CaptchaType getType() {
        return type;
    }

    @PropertySetter(value = "type", description = "The CAPTCHA type.")
    public void setType(CaptchaType type) {
        propertyChange("type", this.type, this.type = type, true);
    }

    @PropertyGetter(value = "size", description = "The CAPTCHA size.")
    public CaptchaSize getSize() {
        return size;
    }

    @PropertySetter(value = "size", description = "The CAPTCHA size.")
    public void setSize(CaptchaSize size) {
        propertyChange("size", this.size, this.size = size, true);
    }

}
