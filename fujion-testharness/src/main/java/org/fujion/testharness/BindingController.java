/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
import org.fujion.component.BaseComponent;
import org.fujion.model.GenericBinder;
import org.fujion.model.ObservableModel;

/**
 * Model binding demonstration
 */
public class BindingController extends BaseController {

    public class MyModel extends ObservableModel {
        
        private String color = "green";
        
        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            propertyChanged("color", this.color, this.color = color);
        }

        private void propertyChanged(String propertyName, Object oldValue, Object newValue) {
            propertyChanged(propertyName);
            BindingController.this.log(
                String.format("Model property \"%s\" changed from \"%s\" to \"%s\".", propertyName, oldValue, newValue));
        }
    }

    public static class MyBinder extends GenericBinder<MyModel> {
        
        public MyBinder() {
            super();
        }

    }
    
    private MyBinder binder;

    private MyModel model;
    
    @Override
    public void afterInitialized(BaseComponent comp) {
        super.afterInitialized(comp);
        binder = (MyBinder) comp.getAttribute("binder");
        model = new MyModel();
        binder.setModel(model);
    }

    @EventHandler(value = "click", target = "btnToggle")
    private void btnToggleHandler() {
        model.setColor("green".equals(model.getColor()) ? "red" : "green");
    }
    
    @EventHandler(value = "click", target = "btnReset")
    private void btnResetHandler() {
        model.setColor("green");
    }

}
