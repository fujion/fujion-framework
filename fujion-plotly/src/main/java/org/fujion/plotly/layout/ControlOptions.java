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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for button and slider controls.
 */
public class ControlOptions extends Options {

    /**
     * The arguments values to be passed to the Plotly method set in "method" on trigger event.
     */
    @Option
    public Object[] args;
    
    /**
     * When true, the API method is executed. When false, all other behaviors are the same and
     * command execution is skipped. This may be useful when hooking into, for example, the
     * "plotly_buttonclicked" method and executing the API command manually without losing the
     * benefit of the control automatically binding to the state of the plot through the
     * specification of "method" and "args".
     * <p>
     * Default: true
     */
    @Option
    public Boolean execute;

    /**
     * The text label to appear on the button.
     */
    @Option
    public String label;

    /**
     * The Plotly method to be called on trigger event (click for buttons, change for sliders). If
     * the SKIP method is used, the control API will function as normal but will perform no API
     * calls and will not bind automatically to state updates. This may be used to create a
     * component interface and attach to control events manually via JavaScript.
     * <p>
     * Default: RESTYLE
     */
    @Option
    public MethodEnum method;

    /**
     * The value of the step, used to refer to the step programmatically. Defaults to the label if
     * not provided. Applies to slider control only.
     */
    @Option
    public String value;
}
