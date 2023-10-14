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
package org.fujion.plotly;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for editing various plot elements.
 */
public class EditOptions extends Options {
    
    /**
     * The main anchor of the annotation, which is the text (if no arrow) or the arrow (which drags
     * the whole thing leaving the arrow length &amp; direction unchanged). Defaults to false.
     */
    @Option
    public Boolean annotationPosition;

    /**
     * Just for annotations with arrows, change the length and direction of the arrow. Defaults to
     * false.
     */
    @Option
    public Boolean annotationTail;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean annotationText;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean axisTitleText;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean colorbarPosition;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean colorbarTitleText;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean legendPosition;

    /**
     * Can edit the trace name fields from the legend Defaults to false.
     */
    @Option
    public Boolean legendText;

    /**
     * Defaults to false.
     */
    @Option
    public Boolean shapePosition;

    /**
     * Can edit the global "layout.title".
     */
    @Option
    public Boolean titleText;
}
