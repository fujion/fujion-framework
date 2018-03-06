/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.fujion.ancillary.IElementIdentifier;

/**
 * Represents a function invocation request to be sent to the client.
 */
public class ClientInvocation {
    
    private final String function;
    
    private final IElementIdentifier target;
    
    private final Object[] arguments;
    
    private final String key;
    
    /**
     * Create a client invocation request.
     *
     * @param target The identifier of the widget that implements the function being invoked. If
     *            null is specified, the function name must specify a fully qualified path to a
     *            free-standing function.
     * @param function The name of the function to be invoked. This may be one of the following
     *            formats:
     *            <table style="padding-left:20px" summary="">
     *            <tr>
     *            <td style="text-align:center"><b>[key]^[function name]</b></td>
     *            <td>- The key and the function name are explicitly declared.</td>
     *            </tr>
     *            <tr>
     *            <td style="text-align:center"><b>^[function name]</b></td>
     *            <td>- The key is implied to be the same as the function name.</td>
     *            </tr>
     *            <tr>
     *            <td style="text-align:center"><b>[function name]</b></td>
     *            <td>- The key will default to a unique value.</td>
     *            </tr>
     *            </table>
     * @param arguments Optional arguments to be passed to the function.
     */
    public ClientInvocation(IElementIdentifier target, String function, Object... arguments) {
        this.target = target;
        this.arguments = arguments;
        String[] pcs = function.split("\\^", 2);
        this.function = pcs.length == 1 ? pcs[0] : pcs[1];
        this.key = pcs.length == 1 ? null : pcs[0].isEmpty() ? pcs[1] : pcs[0];
    }
    
    /**
     * Create a client invocation request.
     *
     * @param moduleName The name of the module whose exported function is to be invoked. If null is
     *            specified, the function name must specify a fully qualified path to a
     *            free-standing function.
     * @param function The name of the exported function to be invoked. This may be one of the
     *            following formats:
     *            <table style="padding-left:20px" summary="">
     *            <tr>
     *            <td style="text-align:center"><b>[key]^[function name]</b></td>
     *            <td>- The key and the function name are explicitly declared.</td>
     *            </tr>
     *            <tr>
     *            <td style="text-align:center"><b>^[function name]</b></td>
     *            <td>- The key is implied to be the same as the function name.</td>
     *            </tr>
     *            <tr>
     *            <td style="text-align:center"><b>[function name]</b></td>
     *            <td>- The key will default to a unique value.</td>
     *            </tr>
     *            </table>
     * @param arguments Optional arguments to be passed to the function.
     */
    public ClientInvocation(String moduleName, String function, Object... arguments) {
        this(moduleName == null ? null : () -> "@" + moduleName, function, arguments);
    }
    
    /**
     * Returns the key associated with the client invocation request. This key is used when queuing
     * the request. If a client invocation request with a matching key already exists in the queue,
     * it will be replaced by this one.
     *
     * @return The key of the client invocation request.
     */
    public String getKey() {
        return key == null ? "" + hashCode() : target == null ? key : key + "^" + target.hashCode();
    }
    
    /**
     * Packages the client invocation request as a map for serialization and transport.
     *
     * @return Client invocation request as a map.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("fcn", function);
        data.put("tgt", target == null ? null : target.getId());
        data.put("arg", transform(arguments));
        return data;
    }
    
    /**
     * Transforms a component or subcomponent by replacing it with its selector. This only effects
     * IElementIdentifier implementations. All other source objects are returned unchanged.
     *
     * @param source The source object.
     * @return The original or transformed object.
     */
    @SuppressWarnings("unchecked")
    private Object transform(Object source) {
        if (source == null) {
            return null;
        }

        if (source instanceof IClientTransform) {
            return ((IClientTransform) source).transformForClient();
        }

        if (source.getClass().isArray()) {
            return transformArray((Object[]) source);
        }
        
        if (source instanceof Map) {
            return transformMap((Map<Object, Object>) source);
        }
        
        if (source instanceof Collection) {
            Collection<Object> col = (Collection<Object>) source;
            Object[] ary = new Object[col.size()];
            return transform(col.toArray(ary));
        }
        
        if (source instanceof Date) {
            return ((Date) source).getTime();
        }
        
        return source;
    }
    
    /**
     * Transforms an array of objects.
     *
     * @param source Array of objects.
     * @return Source array after transformation.
     */
    private Object[] transformArray(Object[] source) {
        for (int i = 0; i < source.length; i++) {
            source[i] = transform(source[i]);
        }
        
        return source;
    }
    
    private Object transformMap(Map<Object, Object> source) {
        Map<Object, Object> map = new HashMap<>();
        
        for (Object key : source.keySet()) {
            map.put(key, transform(source.get(key)));
        }
        
        return map;
    }
}
