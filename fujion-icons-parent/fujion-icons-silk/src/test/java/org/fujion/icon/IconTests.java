/* #%L
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
package org.fujion.icon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fujion.test.MockTest;
import org.junit.Test;

public class IconTests extends MockTest {
    
    @Test
    public void test() {
        IIconLibrary library = IconLibraryRegistry.getInstance().get("silk");
        assertNotNull(library);
        String path = library.getIconPath("arrow_left", "16x16");
        assertTrue(IconUtil.matcher.match("webjars/famfamfam-silk/*/icons/arrow_left.png", path));
        List<String> matches = library.getMatching("arrow*.png", "16x*");
        assertEquals(20, matches.size());
    }
    
}
