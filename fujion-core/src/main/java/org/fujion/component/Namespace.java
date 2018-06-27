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
package org.fujion.component;

import java.util.Map;

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;

/**
 * This is an extension of a Span component that implements its own namespace.
 */
@Component(tag = "namespace", widgetClass = "Span", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"), description = "An extension of a Span component that implements its own namespace.")
public class Namespace extends Span implements INamespace {

    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("wclazz", "fujion_namespace");
    }
    
}
