/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.testharness;

import org.fujion.annotation.EventHandler;
import org.fujion.event.DropEvent;

/**
 * Drag and drop demonstration
 */
public class DnDController extends BaseController {

    /**
     * Move dragged component to drop target.
     *
     * @param event The drop event.
     */
    @EventHandler(value = "drop", target = { "dropTargetOriginal", "dropTargetA", "dropTargetD_X", "dropTargetX",
            "dropTargetALL", "dropTargetNONE" })
    public void dropHandler(DropEvent event) {
        event.getTarget().addChild(event.getDraggable());
        log("Component dropped.");
    }

}
