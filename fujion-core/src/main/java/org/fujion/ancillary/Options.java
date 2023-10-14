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
package org.fujion.ancillary;

import org.fujion.annotation.OptionScanner;

/**
 * Base class for subclasses that contain {@link org.fujion.annotation.Option @Option} annotations
 * from which an OptionMap may be produced.
 */
public abstract class Options implements IOptionMapTransform {
    
    /**
     * Convert to an OptionMap instance by processing {@link org.fujion.annotation.Option @Option}
     * annotations.
     */
    @Override
    public OptionMap toMap() {
        OptionMap map = new OptionMap();
        OptionScanner.scan(this, map);
        return map;
    }
}
