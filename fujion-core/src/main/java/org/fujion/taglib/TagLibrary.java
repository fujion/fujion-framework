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
package org.fujion.taglib;

import org.fujion.common.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single tag library. We only expose function definitions since custom tags are not
 * supported.
 */
public class TagLibrary {
    
    private final Map<String, TagLibraryFunction> functions = new HashMap<>();
    
    private final String uri;
    
    public TagLibrary(String uri) {
        this.uri = uri;
    }
    
    public String getUri() {
        return uri;
    }
    
    /**
     * Create and add a function definition based on the input parameters.
     *
     * @param functionName The function name.
     * @param className The name of the implementing class.
     * @param methodSignature The signature of the target method.
     */
    public void addFunction(String functionName, String className, String methodSignature) {
        TagLibraryFunction newFunction = new TagLibraryFunction(className, methodSignature);
        TagLibraryFunction oldFunction = functions.get(functionName);
        
        if (oldFunction != null) {
            Assert.isTrue(oldFunction.equals(newFunction), () -> "Duplicate tag library function name: " + functionName);
        } else {
            functions.put(functionName, newFunction);
        }
    }
    
    /**
     * Returns a function definition given its name.
     *
     * @param functionName The function name.
     * @return The function definition, or null if not found.
     */
    public TagLibraryFunction getFunction(String functionName) {
        return functions.get(functionName);
    }
}
