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
package org.fujion.plotly;

import org.fujion.ancillary.Options;

/**
 * Options for editing various plot elements.
 */
public class EditOptions extends Options {
    
    /**
     * The main anchor of the annotation, which is the text (if no arrow) or the arrow (which drags
     * the whole thing leaving the arrow length & direction unchanged). Defaults to false.
     */
    public Boolean annotationPosition;

    /**
     * Just for annotations with arrows, change the length and direction of the arrow. Defaults to
     * false.
     */
    public Boolean annotationTail;

    /**
     * Defaults to false.
     */
    public Boolean annotationText;

    /**
     * Defaults to false.
     */
    public Boolean axisTitleText;

    /**
     * Defaults to false.
     */
    public Boolean colorbarPosition;

    /**
     * Defaults to false.
     */
    public Boolean colorbarTitleText;

    /**
     * Defaults to false.
     */
    public Boolean legendPosition;

    /**
     * Can edit the trace name fields from the legend Defaults to false.
     */
    public Boolean legendText;

    /**
     * Defaults to false.
     */
    public Boolean shapePosition;

    /**
     * Can edit the global "layout.title".
     */
    public Boolean titleText;
}
