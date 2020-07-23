/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.canvas.test;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.fujion.ancillary.IEnumWithValue;
import org.fujion.annotation.PackageScanner;
import org.fujion.canvas.d3.Canvas3D;
import org.junit.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;

/**
 * Canvas component tests.
 */
public class Tests {

    /**
     * Verifies that all members of all enumerations return the expected values.
     *
     * @throws Exception Unexpected exception.
     */
    @Test
    public void testEnumerations() throws Exception {
        int enumCount = 0;
        int memberCount = 0;
        int invalidCount = 0;
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        PackageScanner packageScanner = new PackageScanner(resolver);
        InputStream is = resolver.getResource("classpath:constants.properties").getInputStream();
        Properties props = new Properties();
        props.load(is);

        for (Class<?> clazz : packageScanner.getClasses(Canvas3D.class.getPackage())) {
            if (clazz.isEnum()) {
                enumCount++;

                for (Object e : clazz.getEnumConstants()) {
                    memberCount++;
                    IEnumWithValue ewv = (IEnumWithValue) e;
                    int actual = ewv.value();
                    int expected = NumberUtils.toInt(props.getProperty(e.toString()), Integer.MIN_VALUE);

                    if (actual != expected) {
                        invalidCount++;
                        System.out.print(clazz.getName() + "." + e.toString() + ": ");
                        System.out.println("Actual: " + actual + ", Expected: " + expected);
                    }
                }
            }
        }

        System.out.println("Checked " + memberCount + " members in " + enumCount + " enums.");
        String invmsg = "There were " + invalidCount + " invalid member(s) found.";
        System.out.println(invmsg);
        Assert.isTrue(invalidCount == 0, () -> invmsg);
    }

}
