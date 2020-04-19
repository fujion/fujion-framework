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
package org.fujion.expression;

import org.fujion.expression.MessageAccessor.MessageContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;

/**
 * An extension of Spring's EL evaluation context that supports plugin accessors, resolvers, and
 * converters..
 */
public class ELEvaluator extends StandardEvaluationContext implements BeanPostProcessor, ApplicationContextAware {
    
    private static final ELEvaluator instance = new ELEvaluator();
    
    private final ExpressionCache cache = ExpressionCache.getInstance();
    
    private final DefaultConversionService conversionService = new DefaultConversionService();
    
    public static ELEvaluator getInstance() {
        return instance;
    }
    
    private ELEvaluator() {
        addPropertyAccessor(new EnvironmentAccessor());
        addPropertyAccessor(new MessageAccessor());
        addPropertyAccessor(new ContextAccessor());
        addMethodResolver(new ELMethodResolver());
        conversionService.addConverter(new MessageAccessor.MessageContextConverter());
        setTypeConverter(new StandardTypeConverter(conversionService));
    }
    
    /**
     * Evaluate an EL expression against the specified root object.
     *
     * @param expression An EL expression.
     * @param root The root object against which to evaluate the expression.
     * @return The result of the evaluation.
     */
    public Object evaluate(String expression, Object root) {
        Object value = cache.hasExpression(expression) ? cache.get(expression).getValue(this, root) : expression;
        return value instanceof MessageContext ? value.toString() : value;
    }
    
    /**
     * Evaluate an EL expression.
     *
     * @param expression An EL expression.
     * @return The result of the evaluation.
     */
    public Object evaluate(String expression) {
        return cache.hasExpression(expression) ? cache.get(expression).getValue(this) : expression;
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    /**
     * Discover and register plugin resolvers, accessors, and converters.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ConstructorResolver) {
            addConstructorResolver((ConstructorResolver) bean);
        }

        if (bean instanceof MethodResolver) {
            addMethodResolver((MethodResolver) bean);
        }

        if (bean instanceof PropertyAccessor) {
            addPropertyAccessor((PropertyAccessor) bean);
        }

        if (bean instanceof Converter) {
            conversionService.addConverter((Converter<?, ?>) bean);
        }

        if (bean instanceof BeanResolver) {
            setBeanResolver((BeanResolver) bean);
        }
        
        return bean;
    }
    
    /**
     * Register the application context as a bean resolver and its environment as the default root
     * object.
     *
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setRootObject(applicationContext.getEnvironment());
        
        setBeanResolver((context, beanName) -> applicationContext.getBean(beanName));
    }
    
}
