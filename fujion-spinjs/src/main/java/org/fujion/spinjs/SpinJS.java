/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.spinjs;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.Assert;
import org.fujion.common.StrUtil;
import org.fujion.component.BaseUIComponent;

import java.util.Arrays;

/**
 * Fujion wrapper for jquery.sparkline component.
 */
@Component(
        tag = "spinjs",
        widgetModule = "fujion-spinjs",
        widgetClass = "SpinJS",
        parentTag = "*",
        description = "Spin-js component.")
public class SpinJS extends BaseUIComponent {

    private final SpinJSOptions options = new SpinJSOptions();

    private boolean running;
    
    /**
     * Returns true if the animation is running.
     *
     * @return True if the animation is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Starts or stops the animation.
     *
     * @param start If true, start the animation; if false, stop.
     */
    @PropertySetter(value = "start", defer = true, defaultValue = "false", description = "Starts or stops the animation.")
    public void start(boolean start) {
        if (start) {
            start();
        } else {
            stop();
        }
    }

    /**
     * Starts the animation.
     */
    public void start() {
        if (!running) {
            this.invoke("start", options);
            running = true;
        }
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        if (running) {
            this.invoke("stop");
            running = false;
        }
    }

    /**
     * Returns the number of lines to draw.
     * <p>
     * Default: 12
     *
     * @return The number of lines to draw.
     */
    @PropertyGetter(value = "lines", description = "The number of lines to draw.")
    public Integer getLines() {
        return options.lines;
    }

    /**
     * Sets the number of lines to draw.
     * <p>
     * Default: 12
     *
     * @param lines The number of lines to draw.
     */
    @PropertySetter(value = "lines", defaultValue = "12", description = "The number of lines to draw.")
    public void setLines(Integer lines) {
        propertyChange("lines", options.lines, options.lines = lines, false);
    }

    /**
     * Returns the length of each line.
     * <p>
     * Default: 7
     *
     * @return The length of each line.
     */
    @PropertyGetter(value = "linelength", description = "The length of each line.")
    public Integer getLineLength() {
        return options.length;
    }

    /**
     * Sets the length of each line.
     * <p>
     * Default: 7
     *
     * @param length The length of each line.
     */
    @PropertySetter(value = "linelength", defaultValue = "7", description = "The length of each line.")
    public void setLineLength(Integer length) {
        propertyChange("linelength", options.length, options.length = length, false);
    }

    /**
     * Returns the line width.
     * <p>
     * Default: 5
     *
     * @return The line width (thickness).
     */
    @PropertyGetter(value = "linewidth", description = "The width of each line.")
    public Integer getLineWidth() {
        return options.width;
    }

    /**
     * Sets the line width.
     * <p>
     * Default: 5
     *
     * @param width The line width (thickness).
     */
    @PropertySetter(value = "linewidth", defaultValue = "5", description = "The width of each line.")
    public void setLineWidth(Integer width) {
        propertyChange("linewidth", options.width, options.width = width, false);
    }

    /**
     * Returns the radius of the inner circle.
     * <p>
     * Default: 10
     *
     * @return The radius of the inner circle.
     */
    @PropertyGetter(value = "radius", description = "The radius of the inner circle.")
    public Integer getRadius() {
        return options.radius;
    }

    /**
     * Sets the radius of the inner circle.
     * <p>
     * Default: 10
     *
     * @param radius The radius of the inner circle.
     */
    @PropertySetter(value = "radius", defaultValue = "10", description = "The radius of the inner circle.")
    public void setRadius(Integer radius) {
        propertyChange("radius", options.radius, options.radius = radius, false);
    }

    /**
     * Returns the overall size of the spinner.
     * <p>
     * Default: 1
     *
     * @return The overall size of the spinner.
     */
    @PropertyGetter(value = "scale", description = "The overall size of the spinner.")
    public Double getScale() {
        return options.scale;
    }

    /**
     * Sets the overall size of the spinner.
     * <p>
     * Default: 1
     *
     * @param scale The overall size of the spinner.
     */
    @PropertySetter(value = "scale", defaultValue = "1", description = "The overall size of the spinner.")
    public void setScale(Double scale) {
        propertyChange("scale", options.scale, options.scale = scale, false);
    }

    /**
     * Returns the corner roundness.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     *
     * @return The corner roundness.
     */
    @PropertyGetter(value = "corners", description = "The corner roundness.")
    public Double getCorners() {
        return options.corners;
    }

    /**
     * Sets the corner roundness.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     *
     * @param corners The corner roundness.
     */
    @PropertySetter(value = "corners", defaultValue = "1", description = "The corner roundness.")
    public void setCorners(Double corners) {
        Assert.isTrue(corners == null || (corners >= 0.0 && corners <= 1.0),
                () -> "Property \"corners\" must be between 0 and 1, inclusive");
        propertyChange("corners", options.corners, options.corners = corners, false);
    }

    /**
     * Returns the line color(s). Multiple colors are separated by commas.
     * <p>
     * Default: "#000"
     *
     * @return The line color(s).
     */
    @PropertyGetter(value = "linecolor", description = "The line color(s).  Separate multiple colors with commas.")
    public String getLineColor() {
        return _formatColors(options.color$array);
    }

    /**
     * Sets the line color(s). Separate multiple colors with commas.
     * <p>
     * Default: "#000"
     *
     * @param color The line color(s).
     */
    @PropertySetter(value = "linecolor", defaultValue = "#000", description = "The line color(s).  Separate multiple colors with commas.")
    public void setLineColor(String color) {
        String[] colors = color == null ? null : color.replace(" ", "").split(",");
        propertyChange("linecolor", options.color$array, options.color$array = colors, false);
    }

    /**
     * Returns the color(s) for the fade effect. Multiple colors are separated by commas.
     * <p>
     * Default: "transparent"
     *
     * @return The color(s) for the fade effect.
     */
    @PropertyGetter(value = "fadecolor", description = "The color(s) for the fade effect.  Separate multiple colors with commas.")
    public String getFadeColor() {
        return _formatColors(options.fadeColor$array);
    }

    /**
     * Sets the color(s) for the fade effect. Separate multiple colors with commas.
     * <p>
     * Default: "transparent"
     *
     * @param fadeColor The color(s) for the fade effect.
     */
    @PropertySetter(value = "fadecolor", defaultValue = "transparent", description = "The color(s) for the fade effect.  Separate multiple colors with commas.")
    public void setFadeColor(String fadeColor) {
        String[] colors = fadeColor == null ? null : fadeColor.replace(" ", "").split(",");
        propertyChange("fadecolor", options.fadeColor$array, options.fadeColor$array = colors, false);
    }

    private String _formatColors(String[] colors) {
        return colors != null ? StrUtil.fromList(Arrays.asList(colors), ",") : null;
    }
    
    /**
     * Returns the opacity of the lines.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.25
     *
     * @return The opacity of the lines.
     */
    @PropertyGetter(value = "opacity", description = "The opacity of the lines.")
    public Double getOpacity() {
        return options.opacity;
    }

    /**
     * Sets the opacity of the lines.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.25
     *
     * @param opacity The opacity of the lines.
     */
    @PropertySetter(value = "opacity", defaultValue = "0.25", description = "The opacity of the lines.")
    public void setOpacity(Double opacity) {
        Assert.isTrue(opacity == null || (opacity >= 0.0 && opacity <= 1.0),
                () -> "Property \"opacity\" must be between 0 and 1, inclusive");
        propertyChange("opacity", options.opacity, options.opacity = opacity, false);
    }

    /**
     * Returns the rotation offset.
     * <p>
     * Default: 0
     *
     * @return The rotation offset.
     */
    @PropertyGetter(value = "rotate", description = "The rotation offset.")
    public Integer getRotate() {
        return options.rotate;
    }

    /**
     * Sets the rotation offset.
     * <p>
     * Default: 0
     *
     * @param rotate The rotation offset.
     */
    @PropertySetter(value = "rotate", defaultValue = "0", description = "The rotation offset.")
    public void setRotate(Integer rotate) {
        propertyChange("rotate", options.rotate, options.rotate = rotate, false);
    }

    /**
     * Returns true if the spinner rotation is clockwise.
     * <p>
     * Default: true
     *
     * @return True if the spinner rotation is clockwise.
     */
    @PropertyGetter(value = "clockwise", description = "If true, the rotation is clockwise.")
    public Boolean isClockwise() {
        return options.direction == null ? null : options.direction > 0;
    }

    /**
     * Set to true for clockwise rotation.
     * <p>
     * Default: true
     *
     * @param clockwise True for clockwise rotation.
     */
    @PropertySetter(value = "clockwise", defaultValue = "true", description = "If true, the rotation is clockwise.")
    public void setClockwise(Boolean clockwise) {
        Integer direction = clockwise == null ? null : clockwise ? 1 : -1;
        propertyChange("clockwise", options.direction, options.direction = direction, false);
    }

    /**
     * Returns the rounds per second.
     * <p>
     * Default: 1
     *
     * @return Rounds per second.
     */
    @PropertyGetter(value = "speed", description = "Rounds per second.")
    public Integer getSpeed() {
        return options.speed;
    }

    /**
     * Sets the rounds per second.
     * <p>
     * Default: 1
     *
     * @param speed Rounds per second.
     */
    @PropertySetter(value = "speed", defaultValue = "1", description = "Rounds per second.")
    public void setSpeed(Integer speed) {
        propertyChange("speed", options.speed, options.speed = speed, false);
    }

    /**
     * Returns the after-glow percentage.
     * <p>
     * Constraints: &ge;0 and &le;100
     * <p>
     * Default: 100
     *
     * @return The after-glow percentage.
     */
    @PropertyGetter(value = "trail", description = "The after-glow percentage.")
    public Integer getTrail() {
        return options.trail;
    }

    /**
     * Sets the after-glow percentage.
     * <p>
     * Constraints: &ge;0 and &le;100
     * <p>
     * Default: 100
     *
     * @param trail The after-glow percentage.
     */
    @PropertySetter(value = "trail", defaultValue = "100", description = "The after-glow percentage.")
    public void setTrail(Integer trail) {
        Assert.isTrue(trail == null || (trail >= 0.0 && trail <= 100.0),
                () -> "Property \"trail\" must be between 0 and 100, inclusive");
        propertyChange("trail", options.trail, options.trail = trail, false);
    }

    /**
     * Returns the top position relative to parent.
     * <p>
     * Default: "50%"
     *
     * @return Top position relative to parent.
     */
    @PropertyGetter(value = "top", description = "The top position relative to parent.")
    public String getTop() {
        return options.top;
    }

    /**
     * Sets the top position relative to parent.
     * <p>
     * Default: "50%"
     *
     * @param top Top position relative to parent.
     */
    @PropertySetter(value = "top", defaultValue = "50%", description = "The top position relative to parent.")
    public void setTop(String top) {
        propertyChange("top", options.top, options.top = top, false);
    }

    /**
     * Returns the left position relative to parent.
     * <p>
     * Default: "50%"
     *
     * @return Left position relative to parent.
     */
    @PropertyGetter(value = "left", description = "The left position relative to parent.")
    public String getLeft() {
        return options.left;
    }

    /**
     * Sets the left position relative to parent.
     * <p>
     * Default: "50%"
     *
     * @param left Left position relative to parent.
     */
    @PropertySetter(value = "left", defaultValue = "50%", description = "The left position relative to parent.")
    public void setLeft(String left) {
        propertyChange("left", options.left, options.left = left, false);
    }

    /**
     * Returns the box-shadow for the lines.
     * <p>
     * Default: "none"
     *
     * @return Box-shadow for the lines.
     */
    @PropertyGetter(value = "shadow", description = "The box-shadow for the lines.")
    public String getShadow() {
        return options.shadow;
    }

    /**
     * Sets the box-shadow for the lines.
     * <p>
     * Default: "none"
     *
     * @param shadow Box-shadow for the lines.
     */
    @PropertySetter(value = "shadow", defaultValue = "none", description = "The box-shadow for the lines.")
    public void setShadow(String shadow) {
        propertyChange("shadow", options.shadow, options.shadow = shadow, false);
    }

}
