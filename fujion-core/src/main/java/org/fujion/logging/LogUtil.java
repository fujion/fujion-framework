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
package org.fujion.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Convenience methods for initializing logging settings on the client. These are used during
 * application startup. Changing these settings after application startup will only affect new
 * sessions.
 * <p>
 * Client logging settings are specified in an external property file. A logging {@link LogTarget
 * target} can be specified for each logging {@link LogLevel level}. For example, the client can be
 * configured to log {@link LogLevel#FATAL fatal} and {@link LogLevel#ERROR error} level messages at
 * the server, {@link LogLevel#DEBUG debug} at both the client and the server, and all others only
 * at the client.
 */
public class LogUtil {
    
    /**
     * Supported log levels.
     */
    public static enum LogLevel {
        UNKNOWN, DEBUG, ERROR, FATAL, INFO, TRACE, WARN
    }
    
    /**
     * Client logging targets.
     */
    public static enum LogTarget {
        /**
         * No logging should occur.
         */
        NONE,
        /**
         * Logs on the client console only.
         */
        CLIENT,
        /**
         * Logs using the server's logging framework.
         */
        SERVER,
        /**
         * Logs on both client and server.
         */
        BOTH;
    }
    
    private static Map<LogLevel, LogTarget> settings = new HashMap<>();
    
    private static volatile String clientSettings;
    
    /**
     * Initializes client logging settings. This is called during application startup to inject
     * logging settings specified in an external property file.
     *
     * @param settings Map of logging settings.
     */
    public static void initSettings(Map<String, String> settings) {
        for (Entry<String, String> entry : settings.entrySet()) {
            setTarget(toLevel(entry.getKey()), toTarget(entry.getValue()));
        }
    }
    
    /**
     * Returns the client logging target for the specified logging level.
     *
     * @param level The logging level.
     * @return The client logging target.
     */
    public static LogTarget getTarget(LogLevel level) {
        LogTarget target = settings.get(level);
        return target == null ? LogTarget.NONE : target;
    }
    
    /**
     * Sets the client logging target for a given logging level.
     *
     * @param level The logging level.
     * @param target The logging target.
     * @return The previous logging target, if any.
     */
    public static LogTarget setTarget(LogLevel level, LogTarget target) {
        synchronized (settings) {
            clientSettings = null;
            
            if (target == null || target == LogTarget.NONE) {
                return settings.remove(level);
            }
            
            return settings.put(level, target);
        }
    }
    
    /**
     * Returns a JSON string that will be used by the client to initialize logging settings.
     *
     * @return A JSON string that will be used by the client to initialize logging settings.
     */
    public static String getSettingsForClient() {
        return clientSettings == null ? initSettingsForClient() : clientSettings;
    }
    
    private static String initSettingsForClient() {
        synchronized (settings) {
            if (clientSettings == null) {
                StringBuilder sb = new StringBuilder("{");
                String delim = "";
                
                for (Entry<LogLevel, LogTarget> entry : settings.entrySet()) {
                    if (entry.getValue() != null && entry.getValue() != LogTarget.NONE) {
                        sb.append(delim).append(entry.getKey().name().toLowerCase()).append(":")
                                .append(entry.getValue().ordinal());
                        delim = ",";
                    }
                }
                
                clientSettings = sb.append("}").toString();
            }
        }
        
        return clientSettings;
    }
    
    /**
     * Returns the {@link LogLevel logging level} from its text equivalent.
     *
     * @param level Text to convert.
     * @return The {@link LogLevel logging level}.
     */
    public static LogLevel toLevel(String level) {
        try {
            return LogLevel.valueOf(level.toUpperCase());
        } catch (Exception e) {
            return LogLevel.UNKNOWN;
        }
    }
    
    /**
     * Returns the {@link LogTarget logging target} from its text equivalent.
     *
     * @param target Text to convert.
     * @return The {@link LogTarget logging target}.
     */
    public static LogTarget toTarget(String target) {
        try {
            return LogTarget.valueOf(target.toUpperCase());
        } catch (Exception e) {
            return LogTarget.NONE;
        }
    }
    
    private LogUtil() {
    }
}
