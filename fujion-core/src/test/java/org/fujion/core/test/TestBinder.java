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
package org.fujion.core.test;

import org.fujion.model.GenericBinder;
import org.fujion.model.ObservableModel;

import java.util.Objects;

/**
 * Test binder and model.
 */
public class TestBinder extends GenericBinder<TestBinder.TestModel> {
    
    public static class TestModel extends ObservableModel {
        
        private String color = "green";
        
        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            propertyChanged("color", this.color, this.color = color);
        }

        public void propertyChanged(String propertyName, Object oldValue, Object newValue) {
            if (!Objects.equals(oldValue, newValue)) {
                super.propertyChanged(propertyName, oldValue, newValue);
            }
        }
    }
    
    public TestBinder() {
        super(new TestModel());
    }
}
