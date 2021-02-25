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
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.common.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 * Options for Google Maps loader.  This should be a singleton.
 */
public class LoaderOptions extends Options {

    public enum Channel {
        WEEKLY, QUARTERLY;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static final LoaderOptions instance = new LoaderOptions();
    
    public static LoaderOptions getInstance() {
        return instance;
    }
    
    @Option("key")
    @Value("${org.fujion.gmaps.key:#{null}}")
    private String apiKey;
    
    @Option("version")
    @Value("${org.fujion.gmaps.version:#{null}}")
    private String apiVersion;

    @Option(value = "libraries", convertUsing = "value.split('\\,')")
    @Value("${org.fujion.gmaps.libraries:#{null}}")
    private String libraries;

    @Option("client")
    @Value("${org.fujion.gmaps.client:#{null}}")
    private String client;

    @Option("channel")
    @Value("${org.fujion.gmaps.channel:QUARTERLY}")
    private Channel channel;

    @Option("language")
    @Value("${org.fujion.gmaps.language:#{null}}")
    private String language;

    @Option("region")
    @Value("${org.fujion.gmaps.region:#{null}}")
    private String region;

    @Option("url")
    @Value("${org.fujion.gmaps.url:#{null}}")
    private String url;

    private LoaderOptions() {
    }
    
    public LoaderOptions validate() {
        Assert.isTrue(!StringUtils.isEmpty(apiKey), () -> "An API key must be specified to access Google Maps functionality");
        return this;
    }

}
