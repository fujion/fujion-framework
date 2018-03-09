/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.client.IClientTransform;

/**
 * Interface for classes that assign a unique identifier to an HTML element.
 */
public interface IElementIdentifier extends IClientTransform {

    static final Log log = LogFactory.getLog(IElementIdentifier.class);

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
            log.error("Component is not attached to a page: " + this);
        }

        return id == null ? null : Collections.singletonMap("__fujion_id__", id);
    }

}
