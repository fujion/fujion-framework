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
package org.fujion.common;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Class that permits access to message sources via a resource bundle. This is a very minimal
 * implementation since message sources are not capable of enumerating their keys.
 */
public class MessageBundle extends ResourceBundle {

    @Override
    protected Object handleGetObject(String key) {
        return Localizer.getMessage(key, getLocale());
    }

    @Override
    public Enumeration<String> getKeys() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean containsKey(String key) {
        return handleGetObject(key) != null;
    }
}
