/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import java.util.Map;

import org.fujion.common.Logger;
import org.fujion.client.ClientRequest;
import org.fujion.websocket.IRequestHandler;

/**
 * Handles a logging request from the client. This is effectively a bridge between the client's
 * logging framework and that of the server.
 */
public class LogRequestHandler implements IRequestHandler {

    private static final Logger log = Logger.create(LogRequestHandler.class);

    @Override
    public void handleRequest(ClientRequest request) {

        @SuppressWarnings("unchecked")
        Map<String, Object> data = request.getData(Map.class);
        String level = (String) data.get("level");
        Object message = data.get("message");

        switch (LogUtil.toLevel(level)) {
            case DEBUG:
                log.debug(message);
                break;

            case ERROR:
                log.error(message);
                break;

            case FATAL:
                log.fatal(message);
                break;

            case INFO:
                log.info(message);
                break;

            case TRACE:
                log.trace(message);
                break;

            case WARN:
                log.warn(message);
                break;

            default:
                log.info(() -> "Unknown logging level: " + level);
                log.info(message);
                break;
        }
    }

    @Override
    public String getRequestType() {
        return "log";
    }

}
