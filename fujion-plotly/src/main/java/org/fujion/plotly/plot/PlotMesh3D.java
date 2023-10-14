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
package org.fujion.plotly.plot;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;

/**
 * Options for 3D mesh plot.
 */
public class PlotMesh3D extends PlotOptions {
    
    /**
     * Specifies one of the three axes.
     */
    public enum AxisSelectorEnum {
        
        X, Y, Z;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Determines how the mesh surface triangles are derived from the set of vertices (points)
     * represented by the "x", "y" and "z" arrays, if the "i", "j", "k" arrays are not supplied. For
     * general use of "mesh3d" it is preferred that "i", "j", "k" are supplied. If "-1", Delaunay
     * triangulation is used, which is mainly suitable if the mesh is a single, more or less layer
     * surface that is perpendicular to "delaunayaxis". In case the "delaunayaxis" intersects the
     * mesh surface at more than one point it will result triangles that are very long in the
     * dimension of "delaunayaxis". If "&gt;0", the alpha-shape algorithm is used. In this case, the
     * positive "alphahull" value signals the use of the alpha-shape algorithm, _and_ its value acts
     * as the parameter for the mesh fitting. If "0", the convex-hull algorithm is used. It is
     * suitable for convex bodies or if the intention is to enclose the "x", "y" and "z" point set
     * into a convex hull.
     * <p>
     * Default: -1
     */
    @Option
    public Double alphahull;

    /**
     * Has an effect only if "color" is set to a numerical array. Determines whether the colorscale
     * is a default palette ("autocolorscale: true") or the palette determined by "colorscale". In
     * case "colorscale" is unspecified or "autocolorscale" is true, the default palette will be
     * chosen according to whether numbers in the "color" array are all positive, all negative or
     * mixed.
     */
    @Option
    public Boolean autocolorscale;

    /**
     * Has an effect only if "color" is set to a numerical array and "cmin", "cmax" are set by the
     * user. In this case, it controls whether the range of colors in "colorscale" is mapped to the
     * range of values in the "color" array ("cauto: true"), or the "cmin"/"cmax" values ("cauto:
     * false").
     * <p>
     * Default: false (when "cmin", "cmax" are set by the user)
     */
    @Option
    public Boolean cauto;
    
    /**
     * Has an effect only if "color" is set to a numerical array. The upper bound of the color
     * domain. Value should be associated to the "color" array index, and if set, "cmin" must be set
     * as well.
     */
    @Option
    public Integer cmax;

    /**
     * Has an effect only if "color" is set to a numerical array. The lower bound of the color
     * domain. Value should be associated to the "color" array index, and if set, "cmax" must be set
     * as well.
     */
    @Option
    public Integer cmin;
    
    /**
     * Alternate form. See {@link #colorscale$array}.
     */
    @Option("color")
    public int[] color$array;

    /**
     * The color of the whole mesh.
     */
    @Option("color")
    public String color$string;
    
    /**
     * Options for color bar display.
     */
    @Option
    public final ColorbarOptions colorbar = new ColorbarOptions();

    /**
     * The colorscale and only has an effect if "color" is set to a numerical array. The colorscale
     * must be an array containing arrays mapping a normalized value to an rgb, rgba, hex, hsl, hsv,
     * or named color string. At minimum, a mapping for the lowest (0) and highest (1) values are
     * required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To control the bounds of
     * the colorscale in color space, use "cmin" and "cmax".
     */
    @Option("colorscale")
    public Object[] colorscale$array;
    
    /**
     * Alternatively, "colorscale" may be a palette name string of the following list: Greys,
     * YlGnBu, Greens, YlOrRd, Bluered, RdBu, Reds, Blues, Picnic, Rainbow, Portland, Jet, Hot,
     * Blackbody, Earth, Electric, Viridis, Cividis
     */
    @Option("colorscale")
    public String colorscale$string;
    
    /**
     * The color of the contour lines.
     * <p>
     * Default: "#444"
     */
    @Option("contour.color")
    public String contour_color;
    
    /**
     * Determines whether or not dynamic contours are shown on hover.
     */
    @Option("contour.show")
    public Boolean contour_show;

    /**
     * The width of the contour lines.
     * <p>
     * Default: 2
     */
    @Option("contour.width")
    public Integer contour_width;

    /**
     * The Delaunay axis, which is the axis that is perpendicular to the surface of the Delaunay
     * triangulation. It has an effect if "i", "j", "k" are not provided and "alphahull" is set to
     * indicate Delaunay triangulation.
     * <p>
     * Default: Z
     */
    @Option
    public AxisSelectorEnum delaunayaxis;

    /**
     * The color of each face Overrides "color" and "vertexcolor".
     */
    @Option
    public String[] facecolor;
    
    /**
     * Determines whether or not normal smoothing is applied to the meshes, creating meshes with an
     * angular, low-poly look via flat reflections.
     */
    @Option
    public Boolean flatshading;

    /**
     * A vector of vertex indices, i.e. integer values between 0 and the length of the vertex
     * vectors, representing the "first" vertex of a triangle. For example, "{i[m], j[m], k[m]}"
     * together represent face m (triangle m) in the mesh, where "i[m] = n" points to the triplet
     * "{x[n], y[n], z[n]}" in the vertex arrays. Therefore, each element in "i" represents a point
     * in space, which is the first vertex of a triangle.
     */
    @Option
    public int[] i;
    
    /**
     * The vertex intensity values, used for plotting fields on meshes.
     */
    @Option
    public double[] intensity;
    
    /**
     * A vector of vertex indices, i.e. integer values between 0 and the length of the vertex
     * vectors, representing the "second" vertex of a triangle. For example, "{i[m], j[m], k[m]}"
     * together represent face m (triangle m) in the mesh, where "j[m] = n" points to the triplet
     * "{x[n], y[n], z[n]}" in the vertex arrays. Therefore, each element in "j" represents a point
     * in space, which is the second vertex of a triangle.
     */
    @Option
    public int[] j;
    
    /**
     * A vector of vertex indices, i.e. integer values between 0 and the length of the vertex
     * vectors, representing the "third" vertex of a triangle. For example, "{i[m], j[m], k[m]}"
     * together represent face m (triangle m) in the mesh, where "k[m] = n" points to the triplet
     * "{x[n], y[n], z[n]}" in the vertex arrays. Therefore, each element in "k" represents a point
     * in space, which is the third vertex of a triangle.
     */
    @Option
    public int[] k;
    
    /**
     * Options for lighting effect.
     */
    @Option
    public final LightingOptions lighting = new LightingOptions();
    
    /**
     * Numeric vector, representing the X coordinate for each vertex.
     * <p>
     * Default: 100000
     */
    @Option("lightposition.x")
    public Double lightposition_x;
    
    /**
     * Numeric vector, representing the Y coordinate for each vertex.
     * <p>
     * Default: 100000
     */
    @Option("lightposition.y")
    public Double lightposition_y;

    /**
     * Numeric vector, representing the Z coordinate for each vertex.
     * <p>
     * Default: 0
     */
    @Option("lightposition.z")
    public Double lightposition_z;

    /**
     * Has an effect only if "color" is set to a numerical array. Reverses the color mapping if true
     * ("cmin" will correspond to the last color in the array and "cmax" will correspond to the
     * first color).
     */
    @Option
    public Boolean reversescale;

    /**
     * Reference between this trace's 3D coordinate system and a 3D scene. If "scene" (the default
     * value), the (x,y,z) coordinates refer to "layout.scene". If "scene2", the (x,y,z) coordinates
     * refer to "layout.scene2", and so on.
     */
    @Option
    public String scene;

    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showscale;

    /**
     * The text elements associated with the vertices. If trace "hoverinfo" contains a "text" flag
     * and "hovertext" is not set, these elements will be seen in the hover labels.
     */
    @Option("text")
    public String[] text$array;

    /**
     * The text elements associated with the vertices to the same value. If trace "hoverinfo"
     * contains a "text" flag and "hovertext" is not set, these elements will be seen in the hover
     * labels.
     */
    @Option("text")
    public String text$string;

    /**
     * The color of each vertex. Overrides "color".
     */
    @Option
    public String[] vertexcolor;

    /**
     * The X coordinates of the vertices. The nth element of vectors "x", "y" and "z" jointly
     * represent the X, Y and Z coordinates of the nth vertex.
     */
    @Option
    public double[] x;

    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum xcalendar;
    
    /**
     * The Y coordinates of the vertices. The nth element of vectors "x", "y" and "z" jointly
     * represent the X, Y and Z coordinates of the nth vertex.
     */
    @Option
    public double[] y;

    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum ycalendar;

    /**
     * The Z coordinates of the vertices. The nth element of vectors "x", "y" and "z" jointly
     * represent the X, Y and Z coordinates of the nth vertex.
     */
    @Option
    public double[] z;

    /**
     * The calendar system to use with "z" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum zcalendar;
}
