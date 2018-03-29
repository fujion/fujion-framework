/* #%L
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
package org.fujion.icon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.webjar.WebJar;
import org.fujion.webjar.WebJarLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

/**
 * An abstract base implementation for webjar-packaged icon libraries.
 */
public abstract class IconLibraryBase implements IIconLibrary, ApplicationContextAware {
    
    private static final Log log = LogFactory.getLog(IconLibraryBase.class);
    
    private final String defaultExtension;

    private final WebJar webjar;
    
    private final String[] dimensions;
    
    private ResourcePatternResolver resolver;

    /**
     * Create icon library definition.
     *
     * @param webjarName The webjar name.
     * @param defaultExtension The default file extension (e.g., "png").
     * @param dimensions The supported dimensions.
     */
    protected IconLibraryBase(String webjarName, String defaultExtension, String... dimensions) {
        webjar = WebJarLocator.getInstance().getWebjar(webjarName);
        Assert.notNull(webjar, "Cannot find webjar named \"" + webjarName + "\" during icon library initialization.");
        this.dimensions = dimensions;
        this.defaultExtension = defaultExtension.startsWith(".") ? defaultExtension : "." + defaultExtension;
    }
    
    @Override
    public String getId() {
        return webjar.getName();
    }
    
    @Override
    public String getIconPath(String iconName, String dimensions) {
        return webjar.getRootPath() + webjar.getVersion() + "/" + formatPath(iconName, dimensions);
    }
    
    @Override
    public List<String> getMatching(String iconName, String dims) {
        dims = resolveDims(dims);
        return dims == null ? Collections.emptyList() : getMatching(formatPath(iconName, dims));
    }

    protected List<String> getMatching(String path) {
        List<String> matches = new ArrayList<>();
        
        try {
            path = webjar.getAbsolutePath() + path;
            
            for (Resource resource : resolver.getResources(path)) {
                String p = resource.getURL().getPath();
                int i = p.lastIndexOf("jar!/META-INF/resources/webjars/");
                matches.add(p.substring(i + 24));
            }
        } catch (IOException e) {
            log.error("Error enumerating icons.", e);
        }
        
        return matches;
    }

    @Override
    public String[] supportedDimensions() {
        return dimensions;
    }
    
    protected String getDefaultExtension() {
        return defaultExtension;
    }
    
    protected String resolveDims(String dims) {
        dims = StringUtils.isEmpty(dims) ? "*" : dims.toLowerCase();

        if (ArrayUtils.contains(dimensions, dims)) {
            return dims;
        }

        if (!dims.contains("x")) {
            dims += "x" + dims;
        }

        if (!ArrayUtils.isEmpty(dimensions)) {
            for (String dim : dimensions) {
                if (IconUtil.matcher.match(dims, dim)) {
                    return dim;
                }
            }
        }

        return null;
    }
    
    protected String expandName(String name) {
        return name.contains(".") ? name : name + defaultExtension;
    }
    
    private String formatPath(String name, String dims) {
        dims = resolveDims(dims);
        return dims == null ? null : doFormatPath(name, dims);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        resolver = applicationContext;
    }

    protected abstract String doFormatPath(String name, String dims);
    
}
