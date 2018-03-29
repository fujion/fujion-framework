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
        IIconLibrary library = IconLibraryRegistry.getInstance().get("materialdesign-action");
        assertNotNull(library);
        String url = library.getIconPath("ic_account_balance_white.png", "18x18");
        assertTrue(
            IconUtil.matcher.match("webjars/material-design-icons/*/action/1x_web/ic_account_balance_white_18dp.png", url));
        List<String> matches = library.getMatching("ic_account_balance_*.png", "48x48");
        assertEquals(4, matches.size());
    }

}
