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
package org.fujion.ancillary;

import org.fujion.client.IClientTransform;

/**
 * Interface for classes capable of generating an option map.
 */
public interface IOptionMapTransform extends IClientTransform {

    /**
     * Return object as an option map.
     *
     * @return Option map derived from object instance.
     */
    OptionMap toMap();

    /**
     * @see org.fujion.client.IClientTransform#transformForClient()
     */
    @Override
    default Object transformForClient() {
        return toMap();
    }
}
