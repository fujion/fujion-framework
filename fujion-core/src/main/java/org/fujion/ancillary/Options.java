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
package org.fujion.ancillary;

import org.fujion.ancillary.OptionMap.IOptionMapConverter;
import org.fujion.annotation.OptionScanner;

/**
 * Base class for options.
 */
public abstract class Options implements IOptionMapConverter {

    /**
     * @see org.fujion.ancillary.OptionMap.IOptionMapConverter#toMap()
     */
    @Override
    public OptionMap toMap() {
        OptionMap map = new OptionMap();
        OptionScanner.scan(this, map);
        return map;
    }
    
}
