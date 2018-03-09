/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.ancillary;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.fujion.client.IClientTransform;

/**
 * Represents a snippet of JavaScript code.
 */
public class JavaScriptSnippet implements IClientTransform {

    private final String snippet;
    
    public JavaScriptSnippet(String snippet) {
        snippet = StringUtils.trimToNull(snippet);
        this.snippet = snippet == null ? null : snippet.startsWith("function") ? snippet : "function() {" + snippet + "}";
    }
    
    @Override
    public String toString() {
        return snippet;
    }

    @Override
    public Object transformForClient() {
        return snippet == null ? null : Collections.singletonMap("__fujion_js__", snippet);
    }
}
