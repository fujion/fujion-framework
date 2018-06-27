/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
package org.fujion.core;

import java.util.TimeZone;

import org.fujion.client.ExecutionContext;
import org.fujion.common.Localizer;
import org.fujion.component.Page;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Initializes the Localizer class with the locale and time zone resolvers.
 */
public class InitLocalizer {

    /**
     * Initialize the Localizer.
     */
    public static void init() {
        Localizer.setLocaleResolver(() -> {
            return LocaleContextHolder.getLocale();
        });

        Localizer.setTimeZoneResolver(() -> {
            TimeZone tz = null;
            Page page = ExecutionContext.getPage();
            Integer offset = page == null ? null : page.getBrowserInfo("timezoneOffset", Integer.class);

            if (offset != null) {
                String id = "GMT" + (offset < 0 ? "-" : "+") + "%02d:%02d";
                offset = Math.abs(offset);
                id = String.format(id, offset / 60, offset % 60);
                tz = TimeZone.getTimeZone(id);
            }

            return tz == null ? TimeZone.getDefault() : tz;
        });
    }

    private InitLocalizer() {
    }
}
