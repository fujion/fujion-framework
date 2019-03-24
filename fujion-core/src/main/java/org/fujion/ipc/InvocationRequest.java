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
package org.fujion.ipc;

/**
 * A single invocation request that will be sent to a target.
 */
public class InvocationRequest {
    
    private final String methodName;
    
    private final Object[] args;
    
    /**
     * Create a help request.
     * 
     * @param methodName Name of the method to invoke on a target.
     * @param args Arguments to be passed to the method.
     */
    public InvocationRequest(String methodName, Object... args) {
        this.methodName = methodName;
        this.args = args;
    }
    
    /**
     * Returns the method name.
     * 
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }
    
    /**
     * Returns method arguments.
     * 
     * @return Method arguments.
     */
    public Object[] getArgs() {
        return args;
    }
}
