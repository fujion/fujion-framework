/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

import org.fujion.common.Logger;
import org.fujion.webjar.WebJarResourceResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;

import java.util.List;

/**
 * Main servlet configurator.
 */
@EnableWebMvc
@Configuration
public class ServletConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    private static final Logger log = Logger.create(ServletConfiguration.class);

    private final EncodedResourceResolver encodedResourceResolver = new EncodedResourceResolver();

    private final ResourceResolver webjarResourceResolver = new WebJarResourceResolver();

    private final ResourceResolver dynamicResourceResolver = new DynamicResourceResolver();

    private final MinifiedResourceResolver minifiedResourceResolver = new MinifiedResourceResolver("js", "css");

    private final EmptyResourceResolver emptyResourceResolver = new EmptyResourceResolver();

    private final FujionResourceTransformer fujionResourceTransformer = new FujionResourceTransformer();

    private ApplicationContext applicationContext;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("fsp", MediaType.TEXT_HTML);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addResourceHandlers(registry, "/dynamic/**", "/", dynamicResourceResolver);
        addResourceHandlers(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/", webjarResourceResolver);
        addResourceHandlers(registry, "/web/**", "classpath:/web/");
        addResourceHandlers(registry, "/empty/**", "/", emptyResourceResolver);
        addResourceHandlers(registry, "/**", "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FSPInterceptor());
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new ETagExceptionResolver());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        try {
            Resource[] resources = applicationContext.getResources("/index.*");

            if (resources.length > 0) {
                if (resources.length > 1) {
                    log.warn("Multiple home page candidates detected - only one will be selected.");
                }

                String file = "/" + resources[0].getFilename();
                registry.addRedirectViewController("/", file);
                log.info(() -> "Default home page set to: " + file);
            } else {
                log.info("No default home page detected.");
            }
        } catch (Exception e) {
            log.error("Error while attempting to detect default home page.", e);
        }
    }

    private void addResourceHandlers(ResourceHandlerRegistry registry, String pattern, String locations,
                                     ResourceResolver... resolvers) {
        //@formatter:off
        ResourceChainRegistration chain = registry
                .addResourceHandler(pattern)
                .addResourceLocations(locations)
                .resourceChain(false);

        for (ResourceResolver resolver: resolvers) {
            chain.addResolver(resolver);
        }

        chain
                .addResolver(minifiedResourceResolver)
                .addResolver(encodedResourceResolver)
                .addTransformer(fujionResourceTransformer);
        //@formatter:on
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
