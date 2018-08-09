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
package org.fujion.spring;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.fujion.common.Localizer;
import org.fujion.common.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Adds support for "classpath*:" syntax to Spring's resource bundle message source. Note that
 * although the IOC container will replace the default resource loader with that of the application
 * context, this does not occur early enough during container initialization to allow it to search
 * the WEB-INF folder, which requires an awareness of the servlet context. Therefore, we need to
 * manually replace the default resource loader with the application context during startup. This is
 * typically done in the web context initializer code.
 */
public class ClasspathMessageSource extends ReloadableResourceBundleMessageSource {
    
    private static final Logger log = Logger.create(ClasspathMessageSource.class);
    
    private static final String PROPERTIES_SUFFIX = ".properties";
    
    private static final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    
    private static final ClasspathMessageSource instance = new ClasspathMessageSource();

    public static ClasspathMessageSource getInstance() {
        return instance;
    }

    /**
     * The message source will search for "messages*.properties" files first in the WEB-INF folder,
     * then anywhere within the classpath. This means that entries in the former will take
     * precedence over similar entries found in the classpath. Thus, entries placed in any
     * "message*.properties" files under the WEB-INF folder will override entries declared
     * elsewhere.
     */
    private ClasspathMessageSource() {
        super();
        setBasenames("WEB-INF/messages", "classpath*:/**/messages");
        setDefaultEncoding(StandardCharsets.UTF_8.name());
        Localizer.registerMessageSource((id, locale, args) -> {
            return getMessage(id, args, locale);
        });
    }

    /**
     * Intercept the refreshProperties call to handle "classpath*:" syntax.
     *
     * @see org.springframework.context.support.ReloadableResourceBundleMessageSource#refreshProperties(java.lang.String,
     *      org.springframework.context.support.ReloadableResourceBundleMessageSource.PropertiesHolder)
     */
    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        if (filename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
            return refreshClassPathProperties(filename, propHolder);
        } else {
            return super.refreshProperties(filename, propHolder);
        }
    }
    
    /**
     * Handle classpath syntax.
     *
     * @param filename "classpath*:"-prefixed filename.
     * @param propHolder The properties holder.
     * @return The new properties holder.
     */
    private PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = -1;
        
        try {
            Resource[] resources = resolver.getResources(filename + PROPERTIES_SUFFIX);
            
            for (Resource resource : resources) {
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
                properties.putAll(holder.getProperties());
                lastModified = Math.min(lastModified, resource.lastModified());
            }
        } catch (Exception e) {
            log.warn(() -> "Error reading message source: " + filename);
        }
        
        return new PropertiesHolder(properties, lastModified);
    }
    
}
