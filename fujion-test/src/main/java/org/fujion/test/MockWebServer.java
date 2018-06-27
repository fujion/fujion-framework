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
package org.fujion.test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.catalina.startup.Tomcat;
import org.springframework.util.SocketUtils;

/**
 * An instance of embedded Tomcat as a mock web server.
 */
public class MockWebServer {

    private Tomcat server;
    
    private final int port;

    /**
     * Create an instance of an embedded Tomcat server, using a random port.
     *
     * @throws Exception Unspecified exception.
     */
    public MockWebServer() throws Exception {
        this(SocketUtils.findAvailableTcpPort());
    }
    
    /**
     * Create an instance of an embedded Tomcat server, using a specified port.
     *
     * @param port Main server port.
     * @throws Exception Unspecified exception.
     */
    public MockWebServer(int port) throws Exception {
        this.port = port;
        server = new Tomcat();
        server.setBaseDir(getTargetPath("tomcat-" + port));
        server.setPort(this.port);
        server.addWebapp("", getTargetPath("classes"));
    }

    private String getTargetPath(String path) {
        return new File("./target/" + path + "/").getAbsolutePath();
    }
    
    /**
     * Start the Tomcat server.
     *
     * @throws Exception Unspecified exception.
     */
    public void start() throws Exception {
        server.start();
    }

    /**
     * Stop the Tomcat server.
     *
     * @throws Exception Unspecified exception.
     */
    public void stop() throws Exception {
        server.stop();
        server = null;
    }
    
    /**
     * Return the server port.
     *
     * @return The server port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Request a resource from the server.
     *
     * @param resourcePath Relative path of the resource.
     * @return The server response.
     * @throws Exception Unspecified exception.
     */
    public InputStream request(String resourcePath) throws Exception {
        URL url = new URL("http://localhost:" + port + "/" + resourcePath);
        return url.openConnection().getInputStream();
    }

}
