/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.sparkline.Sparkline;

/**
 * Sample controller to demonstrate simple sparkline graph.
 */
public class SparklineController extends BaseChartController {
    
    @WiredComponent
    private Sparkline sparkline;
    
    @Override
    public void afterInitialized(BaseComponent root) {
        sparkline.setData(SparklineController.NEW_YORK);
        sparkline.run();
    }

}
