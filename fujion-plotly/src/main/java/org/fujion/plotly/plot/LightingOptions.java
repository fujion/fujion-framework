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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for lighting effects.
 */
public class LightingOptions extends Options {
    
    /**
     * Ambient light increases overall color visibility but can wash out the image.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.8
     */
    @Option
    public Double ambient;
    
    /**
     * Represents the extent that incident rays are reflected in a range of angles. *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.8
     */
    @Option
    public Double diffuse;
    
    /**
     * Epsilon for face normals calculation avoids math issues arising from degenerate geometry. *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1e-06
     */
    @Option
    public Double facenormalsepsilon;
    
    /**
     * Represents the reflectance as a dependency of the viewing angle; e.g. paper is reflective
     * when viewing it from the edge of the paper (almost 90 degrees), causing shine. *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.2
     */
    @Option
    public Double fresnel;
    
    /**
     * Alters specular reflection; the rougher the surface, the wider and less contrasty the shine.
     * *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.5
     */
    @Option
    public Double roughness;
    
    /**
     * Represents the level that incident rays are reflected in a single direction, causing shine. *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.05
     */
    @Option
    public Double specular;
    
    /**
     * Epsilon for vertex normals calculation avoids math issues arising from degenerate geometry. *
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1e-12
     */
    @Option
    public Double vertexnormalsepsilon;
}
