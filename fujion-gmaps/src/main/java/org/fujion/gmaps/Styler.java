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
package org.fujion.gmaps;

import org.fujion.ancillary.IOptionMapTransform;
import org.fujion.ancillary.OptionMap;
import org.fujion.common.Assert;

/**
 * Stylers are formatting options that you can apply to map features and elements.
 * 
 * @param <T> The data type of the option value.
 */
public class Styler<T> implements IOptionMapTransform {

    private final OptionMap map = new OptionMap();
    
    private Styler(String type, T value) {
        map.put(type, value);
    }
    
    @Override
    public OptionMap toMap() {
        return map;
    }
    
    /**
     * This option sets the hue while keeping the saturation and lightness specified in the default
     * Google style (or in other style options you define on the map). The resulting color is
     * relative to the style of the base map. If Google makes any changes to the base map style, the
     * changes affect your map's features styled with hue. It's better to use the absolute color
     * styler if you can.
     */
    public static class HueStyler extends Styler<String> {
        
        /**
         * Creates a hue styler with the specified color.
         *
         * @param color An RGB hex string of format #RRGGBB
         */
        public HueStyler(String color) {
            super("hue", color);
        }
    }
    
    /**
     * This option sets the color of the feature.
     */
    public static class ColorStyler extends Styler<String> {
        
        /**
         * Creates a color styler with the specified color.
         *
         * @param color An RGB hex string of format #RRGGBB
         */
        public ColorStyler(String color) {
            super("color", color);
        }
    }
    
    /**
     * This option sets the lightness while keeping the saturation and hue specified in the default
     * Google style (or in other style options you define on the map). The resulting color is
     * relative to the style of the base map. If Google makes any changes to the base map style, the
     * changes affect your map's features styled with lightness. It's better to use the absolute
     * color styler if you can.
     */
    public static class LightnessStyler extends Styler<Double> {

        /**
         * Creates a lightness styler with the specified lightness factor that indicates the
         * percentage change in brightness of the element. Negative values increase darkness (where
         * -100 specifies black) while positive values increase brightness (where +100 specifies
         * white).
         *
         * @param lightness Indicates the percentage change in brightness of the element. Negative
         *            values increase darkness (where -100 specifies black) while positive values
         *            increase brightness (where +100 specifies white).
         *            <p>
         *            Constraints: &ge;-100 and &le;100
         */
        public LightnessStyler(double lightness) {
            super("lightness", lightness);
            Assert.isTrue(lightness >= -100.0 && lightness <= 100.0, () -> "Lightness must be between -100 and 100, inclusive");
        }
    }
    
    /**
     * This option sets the saturation while keeping the hue and lightness specified in the default
     * Google style (or in other style options you define on the map). The resulting color is
     * relative to the style of the base map. If Google makes any changes to the base map style, the
     * changes affect your map's features styled with saturation. It's better to use the absolute
     * color styler if you can.
     */
    public static class SaturationStyler extends Styler<Double> {

        /**
         * Creates a saturation styler with the specified saturation that indicates the percentage
         * change in intensity of the basic color to apply to the element.
         *
         * @param saturation Indicates the percentage change in brightness of the element. Negative
         *            values increase darkness (where -100 specifies black) while positive values
         *            increase brightness (where +100 specifies white).
         *            <p>
         *            Constraints: &ge;-100 and &le;100
         */
        public SaturationStyler(double saturation) {
            super("saturation", saturation);
            Assert.isTrue(saturation >= -100.0 && saturation <= 100.0, () -> "Saturation must be between -100 and 100, inclusive");
        }
    }
    
    /**
     * This option adjusts the lightness relative to the default Google style, using a gamma curve.
     * If Google makes any changes to the base map style, the changes affect your map's features
     * styled with gamma. It's better to use the absolute color styler if you can.
     */
    public static class GammaStyler extends Styler<Double> {

        /**
         * Creates a gamma styler with the specified gamma value that indicates the amount of gamma
         * correction to apply to the element. Gamma corrections modify the lightness of colors in a
         * non-linear fashion, while not affecting white or black values. Gamma correction is
         * typically used to modify the contrast of multiple elements. For example, you can modify
         * the gamma to increase or decrease the contrast between the edges and interiors of
         * elements.
         *
         * @param gamma The gamma value.
         *            <p>
         *            Constraints: &ge;0.01 and &le;10.0
         */
        public GammaStyler(double gamma) {
            super("gamma", gamma);
            Assert.isTrue(gamma >= 0.01 && gamma <= 10.0, () -> "Gamma must be between 0.01 and 10.0, inclusive");
        }
    }

    /**
     * This option simply inverts the default Google style. If Google makes any changes to the base
     * map style, the changes affect your map's features styled with invert_lightness. It's better
     * to use the absolute color styler if you can.
     */
    public static class InvertLightnessStyler extends Styler<Boolean> {
        
        /**
         * Creates a styler for inverting lightness.
         */
        public InvertLightnessStyler() {
            super("invert_lightness", true);
        }
    }

    public enum VisibilityType {
        /**
         * Visibility is on.
         */
        ON,
        /**
         * Visibility is off.
         */
        OFF,
        /**
         * Removes some style features from the affected features; roads, for example, are
         * simplified into thinner lines without outlines, while parks lose their label text but
         * retain the label icon.
         */
        SIMPLIFIED;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * This option indicates whether and how the element appears on the map.
     */
    public static class VisibilityStyler extends Styler<VisibilityType> {
        
        /**
         * Creates a visibility styler with the specified visibility type.
         *
         * @param visibility The visibility type.
         */
        public VisibilityStyler(VisibilityType visibility) {
            super("visibility", visibility);
        }
    }
    
    /**
     * This option sets the weight of the feature, in pixels.
     */
    public static class WeightStyler extends Styler<Integer> {
        
        /**
         * Creates a weight styler.
         *
         * @param weight The weight of the feature, in pixels. Setting the weight to a high value
         *            may result in clipping near tile borders.
         *            <p>
         *            Constraints: &ge;0
         */
        public WeightStyler(int weight) {
            super("weight", weight);
            Assert.isTrue(weight >= 0, () -> "Weight must be greater than or equal to 0");
        }
    }
}
