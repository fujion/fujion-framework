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
package org.fujion.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a wrapper for Commons logger to allow use of lambda expressions.
 */
public class Logger implements ILogger {

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

    @Override
    public void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    @Override
    public void error(Object message) {
        log.error(message);
    }

    @Override
    public void error(Object message, Throwable t) {
        log.error(message, t);
    }

    @Override
    public void fatal(Object message) {
        log.fatal(message);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        log.fatal(message, t);
    }

    @Override
    public void info(Object message) {
        log.info(message);
    }

    @Override
    public void info(Object message, Throwable t) {
        log.info(message, t);
    }

    @Override
    public void trace(Object message) {
        log.trace(message);
        
    }

    @Override
    public void trace(Object message, Throwable t) {
        log.trace(message, t);
    }

    @Override
    public void warn(Object message) {
        log.warn(message);
    }

    @Override
    public void warn(Object message, Throwable t) {
        log.warn(message, t);
    }
    
}
