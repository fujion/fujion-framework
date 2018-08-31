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
package org.fujion.theme.bootswatch;

import org.fujion.common.Logger;
import org.fujion.common.Version;
import org.fujion.common.Version.VersionPart;
import org.fujion.webjar.WebJarLocator;

/**
 * Verifies that a compatible version of Bootstrap is in place.
 */
public class VersionCheck {

    private static final Logger log = Logger.create(VersionCheck.class);

    public VersionCheck(WebJarLocator webjarLocator) {
        Version bsVersion = new Version(webjarLocator.getWebJar("webjar-bootstrap").getVersion());
        String bwVersionStr = webjarLocator.getWebJar("webjar-bootswatch-cerulean").getVersion();
        Version bwVersion = new Version(bwVersionStr);
        
        if (bsVersion.compareTo(bwVersion, VersionPart.MINOR) != 0) {
            throw new RuntimeException("Incompatible Bootswatch theme version " + bwVersionStr);
        } else {
            log.info(() -> String.format("Installed Bootswatch themes version %s.", bwVersionStr));
        }
        
    }
}
