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
package org.fujion.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Configurator for the web socket connection.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    
    private static long keepaliveInterval;

    private WebSocketHandler fujion_WebSocketHandler;

    /**
     * Returns the keep-alive interval, in milliseconds. The client will transmit a ping packet when
     * no transmission has occurred within this interval. A value of &lt;= 0 disables this feature.
     *
     * @return The keep-alive interval.
     */
    public static long getKeepaliveInterval() {
        return keepaliveInterval;
    }

    @Autowired
    public WebSocketConfiguration(WebSocketHandler fujion_WebSocketHandler) {
        this.fujion_WebSocketHandler = fujion_WebSocketHandler;
    }

    /**
     * Register the web socket handler and add a handshake interceptor to copy attributes from the
     * http session to the web socket.
     *
     * @see org.springframework.web.socket.config.annotation.WebSocketConfigurer#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(fujion_WebSocketHandler, "/ws/**").addInterceptors(new HttpSessionHandshakeInterceptor());
    }
    
    /**
     * Sets the keep-alive interval, in milliseconds. The client will transmit a ping packet when no
     * transmission has occurred within this interval.
     *
     * @param value The keep-alive interval, in milliseconds. A value of <= 0 disables this feature.
     */
    @Value("${org.fujion.websocket.keepaliveInterval}")
    private void setKeepaliveInterval(long value) {
        keepaliveInterval = value;
    }
}
