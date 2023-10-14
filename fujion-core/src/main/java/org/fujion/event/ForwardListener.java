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
package org.fujion.event;

import org.fujion.common.Assert;
import org.fujion.component.BaseComponent;

/**
 * Implementation for a listener for a forwarded event.
 */
public class ForwardListener implements IEventListener {
    
    private final String forwardType;
    
    private final BaseComponent target;
    
    public ForwardListener(String forwardType, BaseComponent target) {
        Assert.notNull(this.forwardType = forwardType, () -> "A forward type must be specified");
        Assert.notNull(this.target = target, () -> "A forward target must be specified");
    }
    
    @Override
    public void onEvent(Event event) {
        if (event.getType().equals(forwardType)) {
            if (event.getTarget() != this.target) {
                EventUtil.send(event, target);
            }
        } else {
            Event newEvent = new ForwardedEvent(forwardType, event);
            EventUtil.send(newEvent, target);
        }
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof ForwardListener) {
            ForwardListener fl = (ForwardListener) object;
            return fl.target == target && fl.forwardType.equals(forwardType);
        }
        
        return false;
    }
}
