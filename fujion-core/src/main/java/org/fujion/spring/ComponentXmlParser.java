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
package org.fujion.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * Provides namespace support for scanning for Fujion component annotations. For example,
 *
 * <pre>
 * {@code
 * <beans xmlns="http://www.springframework.org/schema/beans"
 *    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *    xmlns:ffc="http://www.fujion.org/schema/component"
 *    xsi:schemaLocation="
 *        http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans.xsd
 *        http://www.fujion.org/schema/component
 *        http://www.fujion.org/schema/component-extensions.xsd">
 *
 *    <!-- Scan by package -->
 *    <ffc:component-scan package="org.acme.foo" />
 *    <!-- Scan by class -->
 *    <ffc:component-scan class="org.acme.foo.Bar" />
 * </beans>
 * }
 * </pre>
 */
public class ComponentXmlParser extends AbstractXmlParser {

    @Override
    protected void setTargetObject(BeanDefinitionBuilder builder) {
        builder.addPropertyReference("targetObject", "fujion_ComponentScanner");
    }
}
