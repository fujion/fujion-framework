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
package org.fujion.client;

import org.fujion.ancillary.IResponseCallback;
import org.fujion.component.BaseComponent;
import org.fujion.component.BaseUIComponent;

/**
 * Static convenience methods for client-side operations.
 */
public class ClientUtil {

    /**
     * Invoke a function on the client.
     *
     * @param function Name of the function to invoke.
     * @param callback Optional callback for invocation result.
     * @param args Arguments to pass to the function.
     */
    public static void invoke(String function, IResponseCallback<?> callback, Object... args) {
        ExecutionContext.getPage().invoke(null, function, callback, args);
    }

    /**
     * Invoke a function on the client.
     *
     * @param function Name of the function to invoke.
     * @param args Arguments to pass to the function.
     */
    public static void invoke(String function, Object... args) {
        invoke(function, null, args);
    }

    /**
     * Perform a client invocation on the active page.
     *
     * @param invocation The client invocation.
     */
    public static void invoke(ClientInvocation invocation) {
        ExecutionContext.getPage().invoke(invocation);
    }

    /**
     * Redirects the client.
     *
     * @param target URL of the redirect target.
     */
    public static void redirect(String target) {
        redirect(target, null);
    }

    /**
     * Redirects the client.
     *
     * @param target URL of the redirect target.
     * @param window Name of the window that will be redirected. A null value redirects the current
     *            window.
     */
    public static void redirect(String target, String window) {
        invoke("fujion.redirect", target, window);
    }

    /**
     * Evaluates a JavaScript expression on the client.  This is invoked as the body of an
     * anonymous function that is bound to the context of the global fujion object.
     *
     * @param expression A valid JavaScript expression.
     */
    public static void eval(String expression) {
        invoke("fujion.eval", expression);
    }

    /**
     * Evaluates a JavaScript expression on the client, returning the result asynchronously.
     * This is invoked as the body of an anonymous function that is bound to the context of
     * the global fujion object, so the result must be explicitly returned.  For example,
     * {@code return 10*20; } would return the value 200.
     *
     * @param expression A valid JavaScript expression.
     * @param callback Callback for invocation result.
     */
    public static void eval(String expression, IResponseCallback<?> callback) {
        invoke("fujion.eval", callback, expression);
    }

    /**
     * Submits a form.
     *
     * @param form Root component of the form.
     */
    public static void submit(BaseComponent form) {
        invoke("fujion.submit", form);
    }

    /**
     * Creates a busy message covering the specified target. A busy message consists of a mask the
     * covers and prevents interaction with the target component and a message centered within the
     * mask.
     *
     * @param target The target of the busy message.
     * @param message The message to be displayed. If null, any existing message is removed.
     */
    public static void busy(BaseUIComponent target, String message) {
        if (message == null || message.isEmpty()) {
            target.removeMask();
        } else {
            target.addMask(message);
        }
    }

    /**
     * Saves content as a file on the client machine.
     *
     * @param content Content to save.
     * @param mimeType The MIME type of the content.
     * @param fileName The name of the file to be created.
     */
    public static void saveToFile(String content, String mimeType, String fileName) {
        invoke("fujion.saveToFile", content, mimeType, fileName);
    }

    private ClientUtil() {
    }
}
