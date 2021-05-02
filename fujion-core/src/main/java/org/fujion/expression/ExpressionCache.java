/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.fujion.common.AbstractCache;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Cache for compiled expressions.
 */
public class ExpressionCache extends AbstractCache<String, Expression> {

    private static final ExpressionCache instance = new ExpressionCache();

    public static ExpressionCache getInstance() {
        return instance;
    }

    private final SpelExpressionParser parser = new SpelExpressionParser();
    
    private final ParserContext templateContext = new TemplateParserContext("${", "}");
    
    private ExpressionCache() {
    }

    @Override
    protected Expression fetch(String expression) {
        return parser.parseExpression(expression, templateContext);
    }
    
    /**
     * Returns true if the value contains an EL expression.
     *
     * @param value The value to check.
     * @return True if the value contains an EL expression.
     */
    protected boolean hasExpression(String value) {
        return value != null && value.contains(templateContext.getExpressionPrefix());
    }
}
