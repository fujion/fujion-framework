/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
 * Layout options for camera.
 */
public class CameraOptions extends Options {

    /**
     * The x component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    @Option
    public Integer center_x;

    /**
     * The y component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    @Option
    public Integer center_y;

    /**
     * The z component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    @Option
    public Integer center_z;
    
    /**
     * The horizontal domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_x;

    /**
     * The vertical domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_y;

    /**
     * The x component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    @Option
    public Double eye_x;

    /**
     * The y component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    @Option
    public Double eye_y;

    /**
     * The z component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    @Option
    public Double eye_z;

    /**
     * The x component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 0
     */
    @Option
    public Integer up_x;

    /**
     * The y component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 0
     */
    @Option
    public Integer up_y;

    /**
     * The z component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 1
     */
    @Option
    public Integer up_z;

}
