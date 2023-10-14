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
package org.fujion.icon.test;

import org.fujion.icon.IIconLibrary;
import org.fujion.icon.IconLibraryRegistry;
import org.fujion.test.MockTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Helper class for testing icon libraries.
 */
public class IconTestBase extends MockTest {
    
    protected IIconLibrary library;

    /**
     * Sets the icon library being tested.
     *
     * @param name Name of the library.
     */
    protected void setLibrary(String name) {
        library = IconLibraryRegistry.getInstance().get(name);
        assertNotNull(library);
    }

    /**
     * Tests for the presence of a singleton icon.
     *
     * @param name The icon name.
     * @param dims The icon dimensions.
     */
    protected void singleton(String name, String dims) {
        multiple(name, dims, 1);
    }

    /**
     * Tests icon search capability.
     *
     * @param name The icon name.
     * @param dims The icon dimensions.
     * @param count The expected search count.
     */
    protected void multiple(String name, String dims, int count) {
        List<String> matches = library.getMatching(name, dims);
        assertEquals(count, matches.size());
    }
    
}
