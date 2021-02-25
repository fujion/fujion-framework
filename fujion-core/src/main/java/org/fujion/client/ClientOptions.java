/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.client;

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.springframework.beans.factory.annotation.Value;

/**
 * Global settings for the client. These are passed to the client during page initialization.
 */
public class ClientOptions extends Options {
    
    private static final ClientOptions instance = new ClientOptions();
    
    @Option
    @Value("${org.fujion.client.popupDelay}")
    private int popupDelay;

    @Option
    @Value("${org.fujion.client.popupDuration}")
    private int popupDuration;

    @Option
    @Value("${org.fujion.client.hintDelay}")
    private int hintDelay;

    @Option
    @Value("${org.fujion.client.hintDuration}")
    private int hintDuration;

    @Option
    @Value("${org.fujion.client.hintTransition}")
    private int hintTransition;

    @Option
    @Value("${org.fujion.client.hintEffect}")
    private String hintEffect;

    @Option
    @Value("${org.fujion.client.hintTrack}")
    private String hintTrack;

    @Option
    private final OptionMap custom = new OptionMap();

    public static ClientOptions getInstance() {
        return instance;
    }

    /**
     * Set a custom global setting.  Custom settings will appear under <code>globalOptions.custom</code>
     * on the client.  Only pages created subsequent to the change will receive the updated value.
     *
     * @param name Name of setting.
     * @param value Setting value.
     */
    public static void setCustomSetting(String name, Object value) {
        instance.custom.put(name, value);
    }

    private ClientOptions() {
    }

}
