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
package org.fujion.servlet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.fujion.common.MiscUtil;
import org.fujion.component.Page;
import org.fujion.core.RequestUtil;
import org.fujion.core.WebUtil;
import org.fujion.logging.LogUtil;
import org.fujion.webjar.WebJarLocator;
import org.fujion.websocket.WebSocketConfiguration;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.ResourceTransformerSupport;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Replaces the top-level Fujion resource with the bootstrapper template that is responsible for
 * loading and initializing that resource at the client.
 */
public class FujionResourceTransformer extends ResourceTransformerSupport {

    /**
     * Exposes the fully resolved boostrapper template as a resource to be delivered to the client.
     */
    private static class BootstrapperResource extends AbstractFileResolvingResource {

        private final Resource resource;
        
        private final boolean compress;

        private final StringBuffer content = new StringBuffer();

        BootstrapperResource(Resource resource, boolean compress) {
            this.resource = resource;
            this.compress = compress;
        }

        public void addContent(String data) {
            if (compress) {
                content.append(data.trim());
            } else {
                content.append(data).append('\n');
            }
        }

        @Override
        public long contentLength() {
            return content.length();
        }

        @Override
        public String getFilename() {
            return resource.getFilename();
        }

        @Override
        public URL getURL() throws IOException {
            return resource.getURL();
        }

        @Override
        public String getDescription() {
            return resource.getDescription();
        }

        @Override
        public InputStream getInputStream() {
            return IOUtils.toInputStream(content.toString(), StandardCharsets.UTF_8);
        }

    }

    private final List<String> bootstrapperTemplate;

    public FujionResourceTransformer() {
        try {
            bootstrapperTemplate = IOUtils.readLines(getClass().getResourceAsStream("/org/fujion/bootstrapper.htm"),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * If the resource is a Fujion server page (i.e., has a file extension of ".fsp"), replace it
     * with an html resource derived from the bootstrapping template.
     */
    @Override
    public Resource transform(HttpServletRequest request, Resource resource,
                              ResourceTransformerChain chain) throws IOException {
        String filename = resource == null ? null : resource.getFilename();
        
        if (filename == null || !filename.endsWith(".fsp")) {
            return chain.transform(request, resource);
        }

        request.getSession(true);
        BootstrapperResource bootstrapperResource = new BootstrapperResource(resource, !WebUtil.isDebugEnabled());
        Map<String, Object> map = new HashMap<>();
        Page page = Page._create(resource.getURL().toString());
        String baseUrl = RequestUtil.getBaseURL(request);
        String wsUrl = "ws" + baseUrl.substring(4) + "ws";
        String webJarInit = WebJarLocator.getInstance().getWebJarInit();
        map.put("pid", page.getId());
        map.put("baseUrl", baseUrl);
        map.put("wsUrl", wsUrl);
        map.put("webjarInit", webJarInit);
        map.put("debug", WebUtil.isDebugEnabled());
        map.put("logging", LogUtil.getSettingsForClient());
        map.put("keepalive", WebSocketConfiguration.getKeepaliveInterval());
        StrSubstitutor sub = new StrSubstitutor(map);

        for (String line : bootstrapperTemplate) {
            bootstrapperResource.addContent(sub.replace(line));
        }

        return chain.transform(request, bootstrapperResource);
    }

}
