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
package org.fujion.canvas.d2;

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.RenderingContextBase;
import org.fujion.common.Assert;
import org.fujion.component.Image;

public class RenderingContext2D extends RenderingContextBase {
    
    private Object fillStyle;
    
    private String font;
    
    private double globalAlpha = 1.0;
    
    private CompositeOperation globalCompositeOperation = CompositeOperation.SOURCE_OVER;

    private LineCap lineCap = LineCap.BUTT;

    private double lineDashOffset;

    private LineJoin lineJoin = LineJoin.MITER;
    
    private double lineWidth = 1.0;
    
    private double miterLimit = 10.0;
    
    private double shadowBlur;
    
    private String shadowColor;
    
    private double shadowOffsetX;

    private double shadowOffsetY;

    private Object strokeStyle;
    
    private TextAlign textAlign = TextAlign.START;

    private TextBaseline textBaseline = TextBaseline.ALPHABETIC;

    public RenderingContext2D(BaseCanvasComponent<?, ?> canvas) {
        super(canvas, "2d");
    }
    
    private void _setFillStyle(Object fillStyle) {
        config("fillStyle", this.fillStyle = fillStyle);
    }

    private void _setStrokeStyle(Object strokeStyle) {
        config("strokeStyle", this.strokeStyle = strokeStyle);
    }

    /**
     * Adds an arc to the path which is centered at (x, y) position with radius r starting at
     * startAngle and ending at endAngle going in the given direction by anticlockwise.
     *
     * @param x The x coordinate of the arc's center.
     * @param y The y coordinate of the arc's center.
     * @param radius The arc's radius.
     * @param startAngle The angle at which the arc starts, measured clockwise from the positive x
     *            axis and expressed in radians.
     * @param endAngle The angle at which the arc ends, measured clockwise from the positive x axis
     *            and expressed in radians.
     */
    public void arc(double x, double y, double radius, double startAngle, double endAngle) {
        arc(x, y, radius, startAngle, endAngle, false);
    }

    /**
     * Adds an arc to the path which is centered at (x, y) position with radius r starting at
     * startAngle and ending at endAngle going in the given direction by anticlockwise.
     *
     * @param x The x coordinate of the arc's center.
     * @param y The y coordinate of the arc's center.
     * @param radius The arc's radius.
     * @param startAngle The angle at which the arc starts, measured clockwise from the positive x
     *            axis and expressed in radians.
     * @param endAngle The angle at which the arc ends, measured clockwise from the positive x axis
     *            and expressed in radians.
     * @param anticlockwise if true, causes the arc to be drawn counter-clockwise between the two
     *            angles.
     */
    public void arc(double x, double y, double radius, double startAngle, double endAngle, boolean anticlockwise) {
        invoke("arc", x, y, radius, startAngle, endAngle, anticlockwise);
    }

    /**
     * Adds an arc to the path with the given control points and radius. The arc drawn will be a
     * part of a circle, never elliptical. Typical use could be making a rounded corner. One way to
     * think about the arc drawn is to imagine two straight segments, from the starting point
     * (latest point in current path) to the first control point, and then from the first control
     * point to the second control point. These two segments form a sharp corner with the first
     * control point being in the corner. Using arcTo, the corner will instead be an arc with the
     * given radius. The arc is tangential to both segments, which can sometimes produce surprising
     * results, e.g. if the radius given is larger than the distance between the starting point and
     * the first control point. If the radius specified doesn't make the arc meet the starting point
     * (latest point in the current path), the starting point is connected to the arc with a
     * straight line segment.
     *
     * @param x1 x-axis coordinates for the first control point.
     * @param y1 y-axis coordinates for the first control point.
     * @param x2 x-axis coordinates for the second control point.
     * @param y2 y-axis coordinates for the second control point.
     * @param radius The arc's radius.
     */
    public void arcTo(double x1, double y1, double x2, double y2, double radius) {
        invoke("arcTo", x1, y1, x2, y2, radius);
    }

    /**
     * Starts a new path by emptying the list of sub-paths. Call this method when you want to create
     * a new path.
     */
    public void beginPath() {
        invoke("beginPath");
    }
    
    /**
     * Adds a cubic Bézier curve to the path. It requires three points. The first two points are
     * control points and the third one is the end point. The starting point is the last point in
     * the current path, which can be changed using moveTo() before creating the Bézier curve.
     *
     * @param cp1x The x axis of the coordinate for the first control point.
     * @param cp1y The y axis of the coordinate for the first control point.
     * @param cp2x The x axis of the coordinate for the second control point.
     * @param cp2y The y axis of the coordinate for the second control point.
     * @param x The x axis of the coordinate for the end point.
     * @param y The y axis of the coordinate for the end point.
     */
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        invoke("bezierCurveTo", cp1x, cp1y, cp2x, cp2y, x, y);
    }
    
    /**
     * Sets all pixels in the rectangle defined by starting point (x, y) and size (width, height) to
     * transparent black, erasing any previously drawn content.
     *
     * @param x The x axis of the coordinate for the rectangle starting point.
     * @param y The y axis of the coordinate for the rectangle starting point.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void clearRect(double x, double y, double width, double height) {
        invoke("clearRect", x, y, width, height);
    }

    /**
     * Turns the path currently being built into the current clipping path.
     */
    public void clip() {
        clip(FillRule.NONZERO);
    }
    
    /**
     * Turns the path currently being built into the current clipping path.
     *
     * @param fillRule The fill rule.
     */
    public void clip(FillRule fillRule) {
        invoke("clip", fillRule);
    }
    
    /**
     * Causes the point of the pen to move back to the start of the current sub-path. It tries to
     * add a straight line (but does not actually draw it) from the current point to the start. If
     * the shape has already been closed or has only one point, this function does nothing.
     */
    public void closePath() {
        invoke("closePath");
    }
    
    /**
     * Creates a new, blank ImageData object with the specified dimensions. All of the pixels in the
     * new object are transparent black.
     *
     * @param height The image height.
     * @param width The image width.
     */
    public void createImageData(double height, double width) {
        throw new UnsupportedOperationException("createImageData");
    }

    /**
     * Creates a gradient along the line given by the coordinates represented by the parameters.
     *
     * @param x0 The x axis of the coordinate of the start point.
     * @param y0 The y axis of the coordinate of the start point.
     * @param x1 The x axis of the coordinate of the end point.
     * @param y1 The y axis of the coordinate of the end point.
     * @return A linear canvas gradient initialized with the specified line.
     */
    public CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1) {
        return new CanvasGradient(getCanvas(), "createLinearGradient", x0, y0, x1, y1);
    }

    /**
     * Creates a pattern using the specified image. It repeats the source in the directions
     * specified by the repetition argument. This method returns a CanvasPattern.
     *
     * @param image The image to repeat.
     * @param repetition The repetition.
     * @return A canvas pattern.
     */
    public CanvasPattern createPattern(Image image, Repetition repetition) {
        return new CanvasPattern(getCanvas(), image, repetition);
    }
    
    /**
     * Creates a radial gradient given by the coordinates of the two circles represented by the
     * parameters.
     *
     * @param x0 The x axis of the coordinate of the start circle.
     * @param y0 The y axis of the coordinate of the start circle.
     * @param r0 The radius of the start circle.
     * @param x1 The x axis of the coordinate of the end circle.
     * @param y1 The y axis of the coordinate of the end circle.
     * @param r1 The radius of the end circle.
     * @return A radial canvas gradient initialized with the two specified circles.
     */
    public CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1) {
        return new CanvasGradient(getCanvas(), "createRadialGradient", x0, y0, r0, x1, y1, r1);
    }

    /**
     * Fills the current or given path with the current fill style using the non-zero winding rule.
     */
    public void fill() {
        fill(FillRule.NONZERO);
    }
    
    /**
     * Fills the current or given path with the current fill style using the specified winding rule.
     *
     * @param fillRule The fill rule.
     */
    public void fill(FillRule fillRule) {
        invoke("fill", fillRule);
    }

    /**
     * Draws a filled rectangle whose starting point is at the coordinates (x, y) with the specified
     * width and height and whose style is determined by the fillStyle attribute.
     *
     * @param x The x component of the coordinates for the rectangle's starting point.
     * @param y The y component of the coordinates for the rectangle's starting point.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void fillRect(double x, double y, double width, double height) {
        invoke("fillRect", x, y, width, height);
    }

    /**
     * Draws a text string at the specified coordinates, filling the string's characters with the
     * current foreground color. The text is rendered using the font and text layout configuration
     * as defined by the font, textAlign, textBaseline, and direction properties.
     *
     * @param text The text string to render into the context. The text is rendered using the
     *            settings specified by font, textAlign, textBaseline, and direction.
     * @param x The x-coordinate of the point at which to begin drawing the text, in pixels.
     * @param y The y-coordinate of the point at which to begin drawing the text, in pixels.
     */
    public void fillText(String text, double x, double y) {
        invoke("fillText", text, x, y);
    }

    /**
     * Draws a text string at the specified coordinates, filling the string's characters with the
     * current foreground color. The text is rendered using the font and text layout configuration
     * as defined by the font, textAlign, textBaseline, and direction properties.
     *
     * @param text The text string to render into the context. The text is rendered using the
     *            settings specified by font, textAlign, textBaseline, and direction.
     * @param x The x-coordinate of the point at which to begin drawing the text, in pixels.
     * @param y The y-coordinate of the point at which to begin drawing the text, in pixels.
     * @param maxWidth The maximum number of pixels wide the string may be once rendered. If not
     *            specified, there is no limit to the width of the string. However, if this value is
     *            provided, the user agent will adjust the kerning, select a more horizontally
     *            condensed font (if one is available or can be generated without loss of quality),
     *            or scale down to a smaller font size in order to fit the text in the specified
     *            width.
     */
    public void fillText(String text, double x, double y, Integer maxWidth) {
        invoke("fillText", text, x, y, maxWidth);
    }
    
    /**
     * Returns the color or style to use inside shapes.
     *
     * @return The color or style to use inside shapes. May be a color, a CanvasGradient, or a
     *         CanvasPattern.
     */
    public Object getFillStyle() {
        return fillStyle;
    }

    /**
     * Returns the current text style being used when drawing text. This string uses the same syntax
     * as the CSS font specifier. The default font is 10px sans-serif.
     *
     * @return The current text style being used when drawing text.
     */
    public String getFont() {
        return font;
    }

    /**
     * Returns the alpha value that is applied to shapes and images before they are drawn onto the
     * canvas. The value is in the range from 0.0 (fully transparent) to 1.0 (fully opaque).
     *
     * @return The global alpha value.
     */
    public double getGlobalAlpha() {
        return globalAlpha;
    }

    /**
     * Returns the type of compositing operation to apply when drawing new shapes, where type is a
     * string identifying which of the compositing or blending mode operations to use.
     *
     * @return The global compositing operation.
     */
    public CompositeOperation getGlobalCompositeOperation() {
        return globalCompositeOperation;
    }
    
    /**
     * Returns the line cap style.
     *
     * @return The line cap style.
     */
    public LineCap getLineCap() {
        return lineCap;
    }

    /**
     * A float specifying the amount of the offset. Initially 0.0.
     *
     * @return The amount of the offset.
     */
    public double getLineDashOffset() {
        return lineDashOffset;
    }
    
    /**
     * Returns the line join behavior.
     *
     * @return The line join behavior.
     */
    public LineJoin getLineJoin() {
        return lineJoin;
    }
    
    /**
     * Returns the thickness of lines in space units.
     *
     * @return The thickness of lines in space units.
     */
    public double getLineWidth() {
        return lineWidth;
    }

    /**
     * Returns a number specifying the miter limit ratio in space units.
     *
     * @return The miter limit ratio.
     */
    public double getMiterLimit() {
        return miterLimit;
    }

    /**
     * Returns the level of the blurring effect.
     *
     * @return The level of the blurring effect.
     */
    public double getShadowBlur() {
        return shadowBlur;
    }

    /**
     * Returns the shadow color. Defaults to fully-transparent black.
     *
     * @return The shadow color.
     */
    public String getShadowColor() {
        return shadowColor;
    }
    
    /**
     * Returns the distance that the shadow will be offset in horizontal distance.
     * <p>
     * Default: 0
     *
     * @return The distance that the shadow will be offset in horizontal distance.
     */
    public double getShadowOffsetX() {
        return shadowOffsetX;
    }

    /**
     * Returns the distance that the shadow will be offset in vertical distance.
     * <p>
     * Default: 0
     *
     * @return The distance that the shadow will be offset in vertical distance.
     */
    public double getShadowOffsetY() {
        return shadowOffsetY;
    }
    
    /**
     * Returns the color, gradient or style to use for the lines around shapes.
     * <p>
     * Default: "#000" (black).
     *
     * @return The color, gradient or style to use for the lines around shapes.
     */
    public Object getStrokeStyle() {
        return strokeStyle;
    }
    
    /**
     * Returns the current text alignment being used when drawing text.
     *
     * @return The current text alignment being used when drawing text.
     */
    public TextAlign getTextAlign() {
        return textAlign;
    }
    
    /**
     * Returns the current text baseline being used when drawing text.
     *
     * @return The current text baseline being used when drawing text.
     */
    public TextBaseline getTextBaseline() {
        return textBaseline;
    }
    
    /**
     * Connects the last point in the sub-path to the x, y coordinates with a straight line (but
     * does not actually draw it).
     *
     * @param x The x axis of the coordinate for the end of the line.
     * @param y The y axis of the coordinate for the end of the line.
     */
    public void lineTo(double x, double y) {
        invoke("lineTo", x, y);
    }
    
    /**
     * Moves the starting point of a new sub-path to the (x, y) coordinates.
     *
     * @param x The x axis of the point.
     * @param y The y axis of the point.
     */
    public void moveTo(double x, double y) {
        invoke("moveTo", x, y);
    }

    /**
     * @param cpx The x axis of the coordinate for the control point.
     * @param cpy The y axis of the coordinate for the control point.
     * @param x The x axis of the coordinate for the end point.
     * @param y The y axis of the coordinate for the end point.
     */
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        invoke("quadraticCurveTo", cpx, cpy, x, y);
    }

    /**
     * Creates a path for a rectangle at position (x, y) with a size that is determined by width and
     * height. Those four points are connected by straight lines and the sub-path is marked as
     * closed, so that you can fill or stroke this rectangle.
     *
     * @param x The x coordinate for the left side of the rectangle.
     * @param y The y coordinate for the left side of the rectangle.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void rect(double x, double y, double width, double height) {
        invoke("rect", x, y, width, height);
    }
    
    /**
     * Restores the most recently saved canvas state by popping the top entry in the drawing state
     * stack. If there is no saved state, this method does nothing.
     */
    public void restore() {
        invoke("restore");
    }
    
    /**
     * Adds a rotation to the transformation matrix. The angle argument represents a clockwise
     * rotation angle and is expressed in degrees.
     *
     * @param angle The angle to rotate clockwise in radians.
     */
    public void rotateDegrees(double angle) {
        rotateRadians(angle * Math.PI / 180.0);
    }
    
    /**
     * Adds a rotation to the transformation matrix. The angle argument represents a clockwise
     * rotation angle and is expressed in radians.
     *
     * @param angle The angle to rotate clockwise in radians.
     */
    public void rotateRadians(double angle) {
        invoke("rotate", angle);
    }
    
    /**
     * Saves the entire state of the canvas by pushing the current state onto a stack.
     */
    public void save() {
        invoke("save");
    }
    
    /**
     * Adds a scaling transformation to the canvas units by x horizontally and by y vertically.
     * <p>
     * By default, one unit on the canvas is exactly one pixel. If we apply, for instance, a scaling
     * factor of 0.5, the resulting unit would become 0.5 pixels and so shapes would be drawn at
     * half size. In a similar way setting the scaling factor to 2.0 would increase the unit size
     * and one unit now becomes two pixels. This results in shapes being drawn twice as large.
     *
     * @param x Scaling factor in the horizontal direction.
     * @param y Scaling factor in the vertical direction.
     */
    public void scale(double x, double y) {
        invoke("scale", x, y);
    }
    
    /**
     * Sets the fill style using a canvas gradient.
     *
     * @param gradient The canvas gradient to use as the fill style.
     */
    public void setFillStyle(CanvasGradient gradient) {
        _setFillStyle(gradient);
    }

    /**
     * Sets the fill style using a canvas pattern.
     *
     * @param pattern The canvas pattern to use as the fill style.
     */
    public void setFillStyle(CanvasPattern pattern) {
        _setFillStyle(pattern);
    }

    /**
     * Sets the fill style using a color.
     *
     * @param color The color to use as the fill style.
     */
    public void setFillStyle(String color) {
        _setFillStyle(color);
    }

    /**
     * Sets the current text style to be used when drawing text. This string uses the same syntax as
     * the CSS font specifier. The default font is 10px sans-serif.
     *
     * @param font The current text style to be used when drawing text.
     */
    public void setFont(String font) {
        config("font", this.font = font);
    }

    /**
     * Sets the alpha value that is applied to shapes and images before they are drawn onto the
     * canvas. The value is in the range from 0.0 (fully transparent) to 1.0 (fully opaque).
     *
     * @param globalAlpha The global alpha value.
     */
    public void setGlobalAlpha(double globalAlpha) {
        Assert.isTrue(globalAlpha >= 0 && globalAlpha <= 1, () -> "globalAlpha must be between 0 and 1, inclusive");
        config("globalAlpha", this.globalAlpha = globalAlpha);
    }

    /**
     * Sets the type of compositing operation to apply when drawing new shapes, where type is a
     * string identifying which of the compositing or blending mode operations to use.
     *
     * @param globalCompositeOperation The global compositing operation.
     */
    public void setGlobalCompositeOperation(CompositeOperation globalCompositeOperation) {
        config("globalCompositeOperation", this.globalCompositeOperation = globalCompositeOperation);
    }

    /**
     * Sets the line cap style.
     *
     * @param lineCap The line cap style.
     */
    public void setLineCap(LineCap lineCap) {
        config("lineCap", this.lineCap = lineCap);
    }

    /**
     * Sets the line dash pattern used when stroking lines, using an array of values which specify
     * alternating lengths of lines and gaps which describe the pattern.
     *
     * @param segments An array of numbers which specify distances to alternately draw a line and a
     *            gap (in coordinate space units). If the number of elements in the array is odd,
     *            the elements of the array get copied and concatenated. For example, [5, 15, 25]
     *            will become [5, 15, 25, 5, 15, 25]. If the array is empty, the line dash list is
     *            cleared and line strokes return to being solid.
     */
    public void setLineDash(double[] segments) {
        invoke("setLineDash", segments);
    }

    /**
     * A float specifying the amount of the offset. Initially 0.0.
     *
     * @param lineDashOffset The amount of the offset.
     */
    public void setLineDashOffset(double lineDashOffset) {
        config("lineDashOffset", this.lineDashOffset = lineDashOffset);
    }

    /**
     * Sets the line join behavior.
     *
     * @param lineJoin The line join behavior.
     */
    public void setLineJoin(LineJoin lineJoin) {
        config("lineJoin", this.lineJoin = lineJoin);
    }

    /**
     * Sets the thickness of lines in space units.
     *
     * @param lineWidth The thickness of lines in space units.
     */
    public void setLineWidth(double lineWidth) {
        Assert.isTrue(lineWidth > 0, () -> "lineWidth must be greater than zero");
        config("lineWidth", this.lineWidth = lineWidth);
    }
    
    /**
     * Specifies the miter limit ratio in space units.
     *
     * @param miterLimit The miter limit ratio.
     */
    public void setMiterLimit(double miterLimit) {
        Assert.isTrue(miterLimit > 0, () -> "miterLimit must be greater than zero");
        config("miterLimit", this.miterLimit = miterLimit);
    }
    
    /**
     * Sets the level of the blurring effect.
     *
     * @param shadowBlur The level of the blurring effect.
     */
    public void setShadowBlur(double shadowBlur) {
        config("shadowBlur", this.shadowBlur = shadowBlur);
    }
    
    /**
     * Sets the shadow color.
     *
     * @param shadowColor The shadow color.
     */
    public void setShadowColor(String shadowColor) {
        config("shadowColor", this.shadowColor = shadowColor);
    }
    
    /**
     * Sets the distance that the shadow will be offset in horizontal distance.
     *
     * @param shadowOffsetX The distance that the shadow will be offset in horizontal distance.
     */
    public void setShadowOffsetX(double shadowOffsetX) {
        config("shadowOffsetX", this.shadowOffsetX = shadowOffsetX);
    }
    
    /**
     * Sets the distance that the shadow will be offset in vertical distance.
     *
     * @param shadowOffsetY The distance that the shadow will be offset in vertical distance.
     */
    public void setShadowOffsetY(double shadowOffsetY) {
        config("shadowOffsetY", this.shadowOffsetY = shadowOffsetY);
    }
    
    /**
     * Sets the gradient to use for the lines around shapes.
     *
     * @param gradient The color or style to use for the lines around shapes.
     */
    public void setStrokeStyle(CanvasGradient gradient) {
        _setStrokeStyle(gradient);
    }

    /**
     * Sets the pattern to use for the lines around shapes.
     *
     * @param pattern The pattern to use for the lines around shapes.
     */
    public void setStrokeStyle(CanvasPattern pattern) {
        _setStrokeStyle(pattern);
    }

    /**
     * Sets the color to use for the lines around shapes.
     *
     * @param color The color or style to use for the lines around shapes.
     */
    public void setStrokeStyle(String color) {
        _setStrokeStyle(color);
    }

    /**
     * Sets the current text alignment being used when drawing text.
     *
     * @param textAlign The current text alignment being used when drawing text.
     */
    public void setTextAlign(TextAlign textAlign) {
        config("textAlign", this.textAlign = textAlign);
    }

    /**
     * Sets the current text baseline being used when drawing text.
     *
     * @param textBaseline The current text baseline being used when drawing text.
     */
    public void setTextBaseline(TextBaseline textBaseline) {
        config("textBaseline", this.textBaseline = textBaseline);
    }
    
    /**
     * Resets (overrides) the current transformation to the identity matrix and then invokes a
     * transformation described by the arguments of this method.
     *
     * @param a Horizontal scaling.
     * @param b Horizontal skewing.
     * @param c Vertical skewing.
     * @param d Vertical scaling.
     * @param dx Horizontal moving.
     * @param dy Vertical moving.
     */
    public void setTransform(double a, double b, double c, double d, double dx, double dy) {
        invoke("setTransform", a, b, c, d, dx, dy);
    }
    
    /**
     * Strokes the current or given path with the current stroke style using the non-zero winding
     * rule.
     */
    public void stroke() {
        invoke("stroke");
    }

    /**
     * Paints a rectangle which has a starting point at (x, y) and has a w width and an h height
     * onto the canvas, using the current stroke style.
     *
     * @param x The x axis of the coordinate for the rectangle starting point.
     * @param y The y axis of the coordinate for the rectangle starting point.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void strokeRect(double x, double y, double width, double height) {
        invoke("strokeRect", x, y, width, height);
    }

    /**
     * Draws the outlines of the characters of a specified text string at the given (x, y) position.
     * If the optional fourth parameter for a maximum width is provided, the text is scaled to fit
     * that width. See {@link #fillText} method to draw the text with the characters filled with
     * color rather than having just their outlines drawn.
     *
     * @param text The text to draw using the current font, textAlign, textBaseline, and direction
     *            values.
     * @param x The x axis of the coordinate for the text starting point.
     * @param y The y axis of the coordinate for the text starting point.
     */
    public void strokeText(String text, double x, double y) {
        invoke("strokeText", text, x, y);
    }

    /**
     * Draws the outlines of the characters of a specified text string at the given (x, y) position.
     * If the optional fourth parameter for a maximum width is provided, the text is scaled to fit
     * that width. See {@link #fillText} method to draw the text with the characters filled with
     * color rather than having just their outlines drawn.
     *
     * @param text The text to draw using the current font, textAlign, textBaseline, and direction
     *            values.
     * @param x The x axis of the coordinate for the text starting point.
     * @param y The y axis of the coordinate for the text starting point.
     * @param maxWidth The maximum width to draw. When specified, and the string is computed to be
     *            wider than this width, the font is adjusted to use a more horizontally condensed
     *            font (if one is available or if a reasonably readable one can be synthesized by
     *            scaling the current font horizontally) or a smaller font.
     */
    public void strokeText(String text, double x, double y, double maxWidth) {
        invoke("strokeText", text, x, y, maxWidth);
    }

    /**
     * Multiplies the current transformation with the matrix described by the arguments of this
     * method. You are able to scale, rotate, move and skew the context. See also the
     * {@link #setTransform} method which resets the current transform to the identity matrix and
     * then invokes transform().
     *
     * @param a Horizontal scaling.
     * @param b Horizontal skewing.
     * @param c Vertical skewing.
     * @param d Vertical scaling.
     * @param dx Horizontal moving.
     * @param dy Vertical moving.
     */
    public void transform(double a, double b, double c, double d, double dx, double dy) {
        invoke("transform", a, b, c, d, dx, dy);
    }
    
    /**
     * Adds a translation transformation by moving the canvas and its origin x horizontally and y
     * vertically on the grid.
     *
     * @param x Distance to move in the horizontal direction.
     * @param y Distance to move in the vertical direction.
     */
    public void translate(double x, double y) {
        invoke("translate", x, y);
    }
}
