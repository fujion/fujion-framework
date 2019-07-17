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
package org.fujion.ancillary;

import org.fujion.common.MiscUtil;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores a method invocation to be executed at a later time. Supports currying of arguments.
 *
 * @param <T> Return type of the method invocation.
 */
public class DeferredInvocation<T> {
    
    private final Object instance;
    
    private final Method method;
    
    private final List<Object> curriedArgs = new ArrayList<>();

    /**
     * Create a deferred execution.
     *
     * @param instance Instance that is the target of the execution.
     * @param method The method to be executed.
     * @param curriedArgs Initial argument list.
     */
    public DeferredInvocation(Object instance, Method method, Object... curriedArgs) {
        this.instance = instance;
        this.method = method;
        addArgs(curriedArgs);
        method.setAccessible(true);
    }
    
    /**
     * Add additional curried arguments
     *
     * @param args Arguments to append to the curried argument list.
     * @exception IllegalArgumentException If maximum argument count was exceeded.
     */
    public void addArgs(Object... args) {
        if (args != null) {
            Assert.isTrue(curriedArgs.size() + args.length <= method.getParameterCount(),
                    () -> "Method parameter count was exceeded");
            curriedArgs.addAll(Arrays.asList(args));
        }
    }

    /**
     * Invoke the deferred method.
     *
     * @param args Arguments to pass to the method. These will be appended to any curried arguments.
     * @return Value returned by the method.
     */
    @SuppressWarnings("unchecked")
    public T invoke(Object... args) {
        try {
            List<Object> arguments = new ArrayList<>(curriedArgs);
            arguments.addAll(Arrays.asList(args));
            return (T) ConvertUtil.invokeMethod(instance, method, arguments.toArray());
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
}
