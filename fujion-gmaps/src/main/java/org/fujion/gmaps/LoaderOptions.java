/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Options for Google Maps loader.  This should be a singleton.
 */
public class LoaderOptions extends Options {

    public static final LoaderOptions instance = new LoaderOptions();
    
    public static LoaderOptions getInstance() {
        return instance;
    }
    
    @Option("KEY")
    @Value("${org.fujion.gmaps.key:}")
    private String apiKey;
    
    @Option("VERSION")
    @Value("${org.fujion.gmaps.version:3.34}")
    private String apiVersion;

    @Option(value = "LIBRARIES", convertUsing = "value.split('\\,')")
    @Value("${org.fujion.gmaps.libraries:#{null}}")
    private String libraries;

    @Option("CLIENT")
    @Value("${org.fujion.gmaps.client:#{null}}")
    private String client;

    @Option("CHANNEL")
    @Value("${org.fujion.gmaps.channel:#{null}}")
    private String channel;

    @Option("LANGUAGE")
    @Value("${org.fujion.gmaps.language:#{null}}")
    private String language;

    @Option("REGION")
    @Value("${org.fujion.gmaps.region:#{null}}")
    private String region;

    @Option("URL")
    @Value("${org.fujion.gmaps.url:#{null}}")
    private String url;

    private LoaderOptions() {
    }
    
    public LoaderOptions validate() {
        Assert.isTrue(!StringUtils.isEmpty(apiKey), () -> "An API key must be specified to access Google Maps functionality");
        return this;
    }

}
