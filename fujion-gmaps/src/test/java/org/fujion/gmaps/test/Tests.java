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
package org.fujion.gmaps.test;

import static org.junit.Assert.assertEquals;

import org.fujion.gmaps.LatLng;
import org.junit.Test;

public class Tests {
    
    @Test
    public void testLatLng() {
        LatLng ref = new LatLng(39.7684, -86.1581);
        assertEquals(ref, ref);
        LatLng test = LatLng.parse("39.7684,-86.1581");
        assertEquals(ref, test);
        test = LatLng.parse("39.7684 N, 86.1581 W");
        assertEquals(ref, test);
        test = LatLng.parse("[39.7684° N, 86.1581° W]");
        assertEquals(ref, test);
        test = LatLng.parse("[-39.7684° S, -86.1581° E]");
        assertEquals(ref, test);
        test = LatLng.parse("[-39.7684° S, +86.1581° W]");
        assertEquals(ref, test);
        test = LatLng.parse(ref.toString());
        assertEquals(ref, test);
    }
    
}
