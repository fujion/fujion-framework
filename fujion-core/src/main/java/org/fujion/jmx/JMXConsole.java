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
package org.fujion.jmx;

import com.udojava.jmx.wrapper.JMXBean;
import com.udojava.jmx.wrapper.JMXBeanWrapper;
import org.fujion.common.MiscUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * JMX-based management console for Fujion.
 */
public class JMXConsole implements DestructionAwareBeanPostProcessor {
    
    public interface IJMXConsolePlugin {

        /**
         * The MBean type value. Must be unique across console plugins.
         *
         * @return The MBean type value.
         */
        String getType();

    }

    private final MBeanServer mbs;
    
    private JMXConsole(boolean enabled) {
        mbs = enabled ? ManagementFactory.getPlatformMBeanServer() : null;
    }
    
    /**
     * Registers a plugin with the console.
     *
     * @param plugin Plugin to register.
     */
    public void registerPlugin(IJMXConsolePlugin plugin) {
        if (mbs != null) {
            try {
                Object mbean = plugin.getClass().isAnnotationPresent(JMXBean.class) ? new JMXBeanWrapper(plugin) : plugin;
                mbs.registerMBean(mbean, getObjectName(plugin));
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
    }

    /**
     * Unregisters a plugin from the console.
     *
     * @param plugin Plugin to unregister.
     */
    public void unregisterPlugin(IJMXConsolePlugin plugin) {
        if (mbs != null) {
            try {
                mbs.unregisterMBean(getObjectName(plugin));
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
    }

    private ObjectName getObjectName(IJMXConsolePlugin plugin) throws MalformedObjectNameException {
        return new ObjectName("org.fujion:type=" + plugin.getType());
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (mbs != null && bean instanceof IJMXConsolePlugin) {
            registerPlugin((IJMXConsolePlugin) bean);
        }

        return bean;
    }
    
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (mbs != null && bean instanceof IJMXConsolePlugin) {
            unregisterPlugin((IJMXConsolePlugin) bean);
        }
    }

}
