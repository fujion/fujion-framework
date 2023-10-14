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
package org.fujion.client;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Used to wrap custom datatypes. For each type, there must be a registered custom transform on the
 * client.
 */
public class CustomDatatype extends Options {
    
    @Option("__fujion__.tp")
    private final String type;

    @Option("__fujion__.vl")
    private final Object value;

    public CustomDatatype(String type, Object value) {
        this.type = type;
        this.value = value;
    }

}
