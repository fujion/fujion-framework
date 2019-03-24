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
package org.fujion.common;

import java.util.function.Supplier;

import org.apache.commons.logging.Log;

/**
 * Extends Commons Log interface to allow use of lambda expressions.
 */
public interface ILogger extends Log {

    default void debug(Supplier<?> message) {
        if (isDebugEnabled()) {
            debug(message.get());
        }
    }
    
    default void debug(Supplier<?> message, Throwable t) {
        if (isDebugEnabled()) {
            debug(message.get(), t);
        }
    }
    
    default void error(Supplier<?> message) {
        if (isErrorEnabled()) {
            error(message.get());
        }
    }
    
    default void error(Supplier<?> message, Throwable t) {
        if (isErrorEnabled()) {
            error(message.get(), t);
        }
    }
    
    default void fatal(Supplier<?> message) {
        if (isFatalEnabled()) {
            fatal(message.get());
        }
    }
    
    default void fatal(Supplier<?> message, Throwable t) {
        if (isFatalEnabled()) {
            fatal(message.get(), t);
        }
    }
    
    default void info(Supplier<?> message) {
        if (isInfoEnabled()) {
            info(message.get());
        }
    }
    
    default void info(Supplier<?> message, Throwable t) {
        if (isInfoEnabled()) {
            info(message.get(), t);
        }
    }
    
    default void trace(Supplier<?> message) {
        if (isTraceEnabled()) {
            trace(message.get());
        }
    }
    
    default void trace(Supplier<?> message, Throwable t) {
        if (isTraceEnabled()) {
            trace(message.get(), t);
        }
    }
    
    default void warn(Supplier<?> message) {
        if (isWarnEnabled()) {
            warn(message.get());
        }
    }
    
    default void warn(Supplier<?> message, Throwable t) {
        if (isWarnEnabled()) {
            warn(message.get(), t);
        }
    }
    
}
