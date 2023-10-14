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
package org.fujion.common;

import java.util.Locale;

/**
 * Encapsulates a localized text message.
 */
public class LocalizedMessage {

    private final String id;

    private final Locale locale;

    public LocalizedMessage(String id) {
        this(id, null);
    }

    public LocalizedMessage(
            String id,
            Locale locale) {
        this.id = id;
        this.locale = locale;
    }

    private String getMessage(Object... params) {
        return Localizer.getMessage(id, locale == null ? Localizer.getDefaultLocale() : locale, params);
    }

    @Override
    public String toString() {
        return getMessage();
    }

    public String toString(Object... params) {
        return getMessage(params);
    }
}
