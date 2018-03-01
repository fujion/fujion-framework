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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * Layout options for layers.
 */
public class LayerOptions extends Options {

    /**
     * Source types.
     */
    public enum SourceTypeEnum {
        GEOJSON, VECTOR;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Layer types.
     */
    public enum TypeEnum {
        CIRCLE, FILL, LINE, SYMBOL;
    }
    
    /**
     * Determines if the layer will be inserted before the layer with the specified ID. If omitted
     * or set to '', the layer will be inserted above every existing layer.
     */
    public String below;
    
    /**
     * The circle radius. Has an effect only when "type" is set to CIRCLE.
     * <p>
     * Default: 15
     */
    public Integer circle_radius;

    /**
     * The primary layer color. If "type" is CIRCLE, color corresponds to the circle color. If
     * "type" is LINE, color corresponds to the line color. If "type" is FILL, color corresponds to
     * the fill color If "type" is SYMBOL, color corresponds to the icon color.
     * <p>
     * Default: "#444"
     */
    public String color;
    
    /**
     * The fill outline color. Has an effect only when "type" is set to FILL.
     * <p>
     * Default: "#444"
     */
    public String fill_outlinecolor;
    
    /**
     * The line width. Has an effect only when "type" is set to LINE.
     * <p>
     * Default: 2
     */
    public Integer line_width;

    /**
     * The opacity of the layer.
     * <p>
     * Constraints: &ge;0 and &le;1
     */
    public Double opacity;
    
    /**
     * The source data for this layer. Source can be either a URL, a geojson object (with
     * "sourcetype" set to "geojson") or an array of tile URLS (with "sourcetype" set to "vector").
     */
    public String source;
    
    /**
     * Specifies the layer to use from a vector tile source. Required for "vector" source type that
     * supports multiple layers.
     */
    public String sourcelayer;
    
    /**
     * The source type for this layer.
     */
    public SourceTypeEnum sourcetype;
    
    /**
     * Symbol options. Has an effect only when "type" is set to SYMBOL.
     */
    public final SymbolOptions symbol = new SymbolOptions();

    /**
     * The layer type.
     * <p>
     * Default: CIRCLE
     */
    public TypeEnum type;
}
