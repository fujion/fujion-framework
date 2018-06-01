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
package org.fujion.common;

import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a wrapper for Commons logger to allow use of lambda expressions.
 */
public class Logger implements Log {
    
    /**
     * Creates a logger for the given class.
     *
     * @param clazz Class to associate with logger.
     * @return A new logger instance.
     */
    public static Logger create(Class<?> clazz) {
        return create(LogFactory.getLog(clazz));
    }
    
    /**
     * Creates a logger for the given name.
     *
     * @param name Name to associate with logger.
     * @return A new logger instance.
     */
    public static Logger create(String name) {
        return create(LogFactory.getLog(name));
    }

    /**
     * Returns a new logger instance wrapping the specified logger.
     *
     * @param log Logger to wrap.
     * @return A new logger instance or, if the original logger was already an instance of Logger,
     *         that instance is returned.
     */
    public static Logger create(Log log) {
        return log instanceof Logger ? (Logger) log : new Logger(log);
    }

    private final Log log;

    private Logger(Log log) {
        this.log = log;
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }
    
    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }
    
    @Override
    public boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }
    
    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }
    
    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }
    
    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }
    
    @Override
    public void debug(Object message) {
        log.debug(message);
    }
    
    public void debug(Supplier<String> message) {
        if (isDebugEnabled()) {
            debug(message.get());
        }
    }

    @Override
    public void debug(Object message, Throwable t) {
        log.debug(message, t);
    }
    
    public void debug(Supplier<String> message, Throwable t) {
        if (isDebugEnabled()) {
            debug(message.get(), t);
        }
    }

    @Override
    public void error(Object message) {
        log.error(message);
    }
    
    public void error(Supplier<String> message) {
        if (isErrorEnabled()) {
            error(message.get());
        }
    }

    @Override
    public void error(Object message, Throwable t) {
        log.error(message, t);
    }
    
    public void error(Supplier<String> message, Throwable t) {
        if (isErrorEnabled()) {
            error(message.get(), t);
        }
    }

    @Override
    public void fatal(Object message) {
        log.fatal(message);
    }
    
    public void fatal(Supplier<String> message) {
        if (isFatalEnabled()) {
            fatal(message.get());
        }
    }

    @Override
    public void fatal(Object message, Throwable t) {
        log.fatal(message, t);
    }
    
    public void fatal(Supplier<String> message, Throwable t) {
        if (isFatalEnabled()) {
            fatal(message.get(), t);
        }
    }

    @Override
    public void info(Object message) {
        log.info(message);
    }
    
    public void info(Supplier<String> message) {
        if (isInfoEnabled()) {
            info(message.get());
        }
    }

    @Override
    public void info(Object message, Throwable t) {
        log.info(message, t);
    }
    
    public void info(Supplier<String> message, Throwable t) {
        if (isInfoEnabled()) {
            info(message.get(), t);
        }
    }

    @Override
    public void trace(Object message) {
        log.trace(message);

    }
    
    public void trace(Supplier<String> message) {
        if (isTraceEnabled()) {
            trace(message.get());
        }
    }

    @Override
    public void trace(Object message, Throwable t) {
        log.trace(message, t);
    }
    
    public void trace(Supplier<String> message, Throwable t) {
        if (isTraceEnabled()) {
            trace(message.get(), t);
        }
    }

    @Override
    public void warn(Object message) {
        log.warn(message);
    }
    
    public void warn(Supplier<String> message) {
        if (isWarnEnabled()) {
            warn(message.get());
        }
    }

    @Override
    public void warn(Object message, Throwable t) {
        log.warn(message, t);
    }

    public void warn(Supplier<String> message, Throwable t) {
        if (isWarnEnabled()) {
            warn(message.get(), t);
        }
    }

}
