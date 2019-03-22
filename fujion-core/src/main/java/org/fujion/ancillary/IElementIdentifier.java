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

import org.fujion.client.CustomDatatype;
import org.fujion.client.IClientTransform;
import org.fujion.common.Logger;

/**
 * Interface for classes that assign a unique identifier to an HTML element.
 */
public interface IElementIdentifier extends IClientTransform {
    
    Logger log = Logger.create(IElementIdentifier.class);
    
    /**
     * Returns the unique identifier of the corresponding HTML element.
     *
     * @return HTML element unique identifier.
     */
    String getId();
    
    @Override
    default Object transformForClient() {
        String id = getId();
        
        if (id == null) {
            log.error(() -> "Component is not attached to a page: " + this);
        }
        
        return id == null ? null : new CustomDatatype("id", id);
    }
    
}
