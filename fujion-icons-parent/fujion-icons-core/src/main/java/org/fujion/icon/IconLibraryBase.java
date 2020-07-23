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
package org.fujion.icon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.fujion.common.Logger;
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

    private static final Logger log = Logger.create(IconLibraryBase.class);

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
        webjar = WebJarLocator.getInstance().getWebJar(webjarName);
        Assert.notNull(webjar, () -> "Cannot find webjar named \"" + webjarName + "\" during icon library initialization");
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
        List<String> matches = new ArrayList<>();

        try {
            for (String dim : resolveDims(dims)) {
                String path = webjar.getAbsolutePath() + "**/" + formatPath(iconName, dim);

                for (Resource resource : resolver.getResources(path)) {
                    if (resource.exists()) {
                        String p = resource.getURL().getPath();
                        int i = p.lastIndexOf("jar!/META-INF/resources/webjars/");
                        matches.add(p.substring(i + 24));
                    }
                }
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

    protected String getDefaultDimension() {
        return ArrayUtils.isEmpty(dimensions) ? "" : dimensions[0];
    }

    /**
     * Returns a list of matching dimensions.
     *
     * @param dims Input dimensions (wildcards allowed).
     * @return List of matching dimensions (never null);
     */
    protected List<String> resolveDims(String dims) {
        dims = StringUtils.isEmpty(dims) ? getDefaultDimension() : dims.toLowerCase();

        if (ArrayUtils.contains(dimensions, dims)) {
            return Collections.singletonList(dims);
        }
        
        if (!dims.contains("x")) {
            dims += "x" + dims;
        }
        
        List<String> results = new ArrayList<>();

        if (!ArrayUtils.isEmpty(dimensions)) {
            for (String dim : dimensions) {
                if (IconUtil.matcher.match(dims, dim)) {
                    results.add(dim);
                }
            }
        }
        
        return results;
    }

    protected String expandName(String name) {
        return name.contains(".") ? name : name + defaultExtension;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        resolver = applicationContext;
    }
    
    private String formatPath(String iconName, String dimensions) {
        return doFormatPath(iconName, StringUtils.isEmpty(dimensions) ? getDefaultDimension() : dimensions);
    }
    
    protected abstract String doFormatPath(String iconName, String dimensions);

}
