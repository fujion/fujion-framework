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
package org.fujion.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.fujion.page.PageDefinitionCache;

import com.udojava.jmx.wrapper.JMXBean;
import com.udojava.jmx.wrapper.JMXBeanOperation;
import com.udojava.jmx.wrapper.JMXBeanOperation.IMPACT_TYPES;
import com.udojava.jmx.wrapper.JMXBeanWrapper;

/**
 * JMX-based management console for Fujion.
 */
@JMXBean(resourceBundleName = "org.fujion.common.MessageBundle", descriptionKey = "org.fujion.console.description")
public class Console {

    private final PageDefinitionCache cache = PageDefinitionCache.getInstance();

    private Console(boolean enabled) {
        try {
            if (enabled) {
                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                ObjectName name = new ObjectName("org.fujion:type=Console");
                mbs.registerMBean(new JMXBeanWrapper(this), name);
            }
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    @JMXBeanOperation(nameKey = "org.fujion.console.clearFSPCache.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.clearFSPCache.description")
    public String clearFSPCache() {
        int size = cache.size();
        cache.clear();
        return StrUtil.getLabel("org.fujion.console.clearFSPCache.result", size);
    }

    @JMXBeanOperation(nameKey = "org.fujion.console.refreshFSPCache.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.refreshFSPCache.description")
    public String refreshFSPCache() {
        int size = cache.size();
        cache.refresh();
        return StrUtil.getLabel("org.fujion.console.refreshFSPCache.result", size);
    }

}
