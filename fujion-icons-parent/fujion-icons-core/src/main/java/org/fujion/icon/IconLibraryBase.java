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
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.webjar.WebJar;
import org.fujion.webjar.WebJarLocator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

/**
 * A base implementation for webjar-packaged icon libraries.
 */
public class IconLibraryBase implements IIconLibrary {

    private static final Log log = LogFactory.getLog(IconLibraryBase.class);

    private final String[] dimensions;

    private final String pathPattern;

    private final WebJar webjar;

    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    
    /**
     * Create icon library definition using the default path pattern.
     *
     * @param id The webjar name.
     * @param dimensions The supported dimensions (separate multiple entries with commas).
     */
    protected IconLibraryBase(String id, String dimensions) {
        this(id, dimensions, "%dim%/%name%/");
    }

    /**
     * Create icon library definition.
     *
     * @param id The webjar name.
     * @param dimensions The supported dimensions (separate multiple entries with commas).
     * @param pathPattern The pattern for locating an icon within the library. Use %dim% to
     *            reference the dimension and %name% the icon name. For example:
     *            <p>
     *            %dim%/%name%/
     */
    protected IconLibraryBase(String id, String dimensions, String pathPattern) {
        webjar = WebJarLocator.getInstance().getWebjar(id);
        Assert.notNull(webjar, "Cannot find webjar named \"" + id + "\" during icon library initialization.");
        this.dimensions = StringUtils.split(dimensions, ',');
        this.pathPattern = pathPattern;
    }

    @Override
    public String getId() {
        return webjar.getName();
    }

    @Override
    public String getIconUrl(String iconName, String dimensions) {
        return webjar.getRootPath() + webjar.getVersion() + "/" + formatURL(iconName, dimensions);
    }

    @Override
    public List<String> getMatching(String iconName, String dimensions) {
        List<String> urls = new ArrayList<>();
        dimensions = resolveDims(dimensions);
        
        if (dimensions != null) {
            try {
                String root = webjar.getAbsolutePath();

                for (Resource resource : resolver.getResources(root + formatURL(iconName, dimensions))) {
                    String path = resource.getURL().getPath();
                    int i = path.indexOf("jar!/META-INF/resources/webjars/");
                    urls.add(path.substring(i + 24));
                }
            } catch (IOException e) {
                log.error("Error enumerating icons.", e);
            }
        }

        return urls;
    }

    @Override
    public String[] supportedDimensions() {
        return dimensions;
    }

    private String resolveDims(String dims) {
        if (StringUtils.isEmpty(dims)) {
            dims = "*";
        } else if (ArrayUtils.contains(dimensions, dims)) {
            return dims;
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

    protected String formatURL(String iconName, String dims) {
        dims = resolveDims(dims);
        return dims == null ? "????" : pathPattern.replace("%dim%", dims).replaceAll("%name%", iconName).replace("//", "/");
    }

}
