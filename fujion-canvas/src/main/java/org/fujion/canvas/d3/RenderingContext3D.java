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
package org.fujion.canvas.d3;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.fujion.ancillary.IEnumWithValue;
import org.fujion.ancillary.IResponseCallback;
import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.RenderingContext;
import org.springframework.util.Assert;

public class RenderingContext3D extends RenderingContext {

    public interface IWebGLInfoCallback extends IResponseCallback<WebGLActiveInfo> {};

    protected RenderingContext3D(BaseCanvasComponent<?, ?> canvas) {
        super(canvas, "webgl2", "webgl");
    }

    /**
     * Makes the specified texture active.
     *
     * @param texture Texture to make active.
     */
    public void activeTexture(WebGLTexture texture) {
        invoke("activeTexture", texture);
    }

    /**
     * Attaches either a fragment or vertex WebGLShader to a WebGLProgram.
     *
     * @param program The program.
     * @param shader The shader to attach.
     */
    public void attachShader(WebGLProgram program, WebGLShader shader) {
        invoke("attachShader", program, shader);
        program.getShaders().add(shader);
    }

    /**
     * Binds a given WebGLBuffer to a target.
     *
     * @param target The binding point.
     * @param buffer The buffer to bind.
     */
    public void bindBuffer(BufferBinding target, WebGLBuffer buffer) {
        invoke("bindBuffer", target, buffer);
    }

    /**
     * Binds a given WebGLFrameBuffer to a target.
     *
     * @param target The binding point.
     * @param buffer The buffer to bind.
     */
    public void bindFrameBuffer(FrameBufferBinding target, WebGLFrameBuffer buffer) {
        invoke("bindFrameBuffer", target, buffer);
    }

    /**
     * Binds a given WebGLRenderBuffer to a target.
     *
     * @param target The binding point.
     * @param buffer The buffer to bind.
     */
    public void bindRenderBuffer(RenderBufferBinding target, WebGLRenderBuffer buffer) {
        invoke("bindRenderBuffer", target, buffer);
    }

    /**
     * Binds a given WebGLTexture to a target (binding point).
     *
     * @param target The binding point.
     * @param texture The texture to bind.
     */
    public void bindTexture(TextureType target, WebGLTexture texture) {
        invoke("bindTexture", target, texture);
    }

    /**
     * Sets both the RGB blend equation and alpha blend equation to a single equation. The blend
     * equation determines how a new pixel is combined with a pixel already in the WebGLFramebuffer.
     *
     * @param blendMode Specifies how source and destination colors are combined.
     */
    public void blendEquation(BlendMode blendMode) {
        invoke("blendEquation", blendMode);
    }

    /**
     * Sets the RGB blend equation and alpha blend equation separately. The blend equation
     * determines how a new pixel is combined with a pixel already in the WebGLFramebuffer.
     *
     * @param modeRGB Specifies how the red, green and blue components of source and destination
     *            colors are combined.
     * @param modeAlpha how the alpha component (transparency) of source and destination colors are
     *            combined.
     */
    public void blendEquationSeparate(BlendMode modeRGB, BlendMode modeAlpha) {
        invoke("blendEquationSeparate", modeRGB, modeAlpha);
    }

    /**
     * Defines the function used for blending pixel arithmetic.
     *
     * @param sfactor A multiplier for the source blending factors.
     *            <p>
     *            Default: ONE
     * @param dfactor A multiplier for the destination blending factors.
     *            <p>
     *            Default: ZERO
     */
    public void blendFunc(BlendFactor sfactor, BlendFactor dfactor) {
        invoke("blendFunc", sfactor, dfactor);
    }

    /**
     * Defines the function used for blending pixel arithmetic for RGB and alpha components
     * separately.
     *
     * @param srcRGB Multiplier for the red, green and blue (RGB) source blending factors.
     *            <p>
     *            Default: ONE
     * @param dstRGB Multiplier for the red, green and blue (RGB) destination blending factors.
     *            <p>
     *            Default: ZERO
     * @param srcAlpha Multiplier for the alpha source blending factor.
     *            <p>
     *            Default: ONE
     * @param dstAlpha Multiplier for the alpha destination blending factor.
     *            <p>
     *            Default: ZERO
     */
    public void blendFuncSeparate(BlendFactor srcRGB, BlendFactor dstRGB, BlendFactor srcAlpha, BlendFactor dstAlpha) {
        invoke("blendFuncSeparate", srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    /**
     * Sets the source and destination blending factors.
     *
     * @param red Value for the red component in the range of 0 to 1.
     * @param green Value for the green component in the range of 0 to 1.
     * @param blue Value for the blue component in the range of 0 to 1.
     * @param alpha Value for the alpha component in the range of 0 to 1.
     */
    public void blendTexture(double red, double green, double blue, double alpha) {
        invoke("blendTexture", red, green, blue, alpha);
    }

    /**
     * Initializes and creates the buffer object's data store.
     *
     * @param target The binding point (target).
     * @param size The size of the buffer object's data store.
     * @param usage The usage pattern of the data store.
     */
    public void bufferData(BufferBinding target, int size, BufferUsagePattern usage) {
        invoke("bufferData", target, size, usage);
    }
    
    /**
     * Initializes and creates the buffer object's data store.
     *
     * @param target The binding point (target).
     * @param srcData Data that will be copied into the data store. If null, a data store is still
     *            created, but the content is uninitialized and undefined.
     * @param usage The usage pattern of the data store.
     */
    public void bufferData(BufferBinding target, ArrayBufferBase srcData, BufferUsagePattern usage) {
        invoke("bufferData", target, srcData, usage);
    }
    
    /**
     * Initializes and creates the buffer object's data store.
     *
     * @param target The binding point (target).
     * @param srcData Data that will be copied into the data store. If null, a data store is still
     *            created, but the content is uninitialized and undefined.
     * @param usage The usage pattern of the data store.
     * @param srcOffset The element index offset where to start reading the buffer.
     * @param length ? Defaults to 0.
     */
    public void bufferData(BufferBinding target, ArrayBufferBase srcData, BufferUsagePattern usage, int srcOffset,
                           int length) {
        invoke("bufferData", target, srcData, usage, srcOffset);
    }

    /**
     * Updates a subset of a buffer object's data store.
     *
     * @param target The binding point (target).
     * @param offset Offset in bytes where the data replacement will start.
     * @param srcData Data that will be copied into the data store.
     */
    public void bufferSubData(BufferBinding target, int offset, ArrayBufferBase srcData) {
        invoke("bufferSubData", offset, srcData);
    }

    /**
     * Updates a subset of a buffer object's data store.
     *
     * @param target The binding point (target).
     * @param dstByteOffset Offset in bytes where the data replacement will start.
     * @param srcData Data that will be copied into the data store.
     * @param srcOffset The element index offset where to start reading the buffer.
     * @param length ? Defaults to 0.
     */
    public void bufferSubData(BufferBinding target, int dstByteOffset, ArrayBufferBase srcData, int srcOffset, int length) {
        invoke("bufferSubData", target, dstByteOffset, srcData, srcOffset, length);
    }

    /**
     * Returns the completeness status of the WebGLFramebuffer object.
     *
     * @param target The binding point (target).
     * @param callback Call back to receive result.
     */
    public void checkFramebufferStatus(FrameBufferBinding target, IResponseCallback<FrameBufferStatus> callback) {
        invoke((response) -> {
            IResponseCallback.invoke(callback, IEnumWithValue.fromValue(FrameBufferStatus.class, (Integer) response));
        }, "checkFramebufferStatus", target);
    }
    
    /**
     * Clears buffers to preset values.
     *
     * @param bitmasks Bitwise OR mask that indicates the buffers to be cleared.
     */
    public void clear(BufferBitMask... bitmasks) {
        invoke("clear", BufferBitMask.combine(bitmasks));
    }

    /**
     * Specifies the color values used when clearing color buffers.
     *
     * @param red The red color value used when the color buffers are cleared.
     *            <p>
     *            Default: 0
     * @param green The green color value used when the color buffers are cleared.
     *            <p>
     *            Default: 0
     * @param blue The blue color value used when the color buffers are cleared.
     *            <p>
     *            Default: 0
     * @param alpha The alpha value used when the color buffers are cleared.
     *            <p>
     *            Default: 0
     */
    public void clearColor(double red, double green, double blue, double alpha) {
        invoke("clearColor", red, green, blue, alpha);
    }

    /**
     * Specifies the clear value for the depth buffer.
     *
     * @param depth The depth value used when the depth buffer is cleared.
     *            <p>
     *            Default: 1
     */
    public void clearDepth(double depth) {
        invoke("clearDepth", depth);
    }

    /**
     * Specifies the clear value for the stencil buffer.
     *
     * @param index The index used when the stencil buffer is cleared.
     *            <p>
     *            Default: 0
     */
    public void clearStencil(int index) {
        invoke("clearStencil", index);
    }

    /**
     * Sets which color components to enable or to disable when drawing or rendering to a
     * WebGLFramebuffer.
     *
     * @param red Whether or not the red color component can be written into the frame buffer.
     *            <p>
     *            Default: true
     * @param green Whether or not the green color component can be written into the frame buffer.
     *            <p>
     *            Default: true
     * @param blue Whether or not the blue color component can be written into the frame buffer.
     *            <p>
     *            Default: true
     * @param alpha Whether or not the alpha component can be written into the frame buffer.
     *            <p>
     *            Default: true
     */
    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        invoke("colorMask", red, green, blue, alpha);
    }

    /**
     * Compiles a GLSL shader into binary data so that it can be used by a WebGLProgram.
     *
     * @param shader Shader to compile.
     */
    public void compileShader(WebGLShader shader) {
        invoke("compileShader", shader);
    }

    /**
     * Copies pixels from the current WebGLFramebuffer into a 2D texture image.
     *
     * @param target The binding target of the active texture.
     * @param level The level of detail. Level 0 is the base image level and level n is the nth
     *            mipmap reduction level.
     * @param internalformat The color components in the texture.
     * @param x The x coordinate of the lower left corner where to start copying.
     * @param y The y coordinate of the lower left corner where to start copying.
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public void copyTexImage2D(TextureType2 target, int level, ColorFormat internalformat, int x, int y, int width,
                               int height) {
        invoke("copyTexImage2D", target, level, internalformat, x, y, width, height, 0);
    }

    /**
     * Copies pixels from the current WebGLFramebuffer into an existing 2D texture sub-image.
     *
     * @param target The binding target of the active texture.
     * @param level The level of detail. Level 0 is the base image level and level n is the nth
     *            mipmap reduction level.
     * @param xoffset The horizontal offset within the texture image.
     * @param yoffset The vertical offset within the texture image.
     * @param x The x coordinate of the lower left corner where to start copying.
     * @param y The y coordinate of the lower left corner where to start copying.
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public void copyTexSubImage2D(TextureType2 target, int level, int xoffset, int yoffset, int x, int y, int width,
                                  int height) {
        invoke("copyTexSubImage2D", target, level, xoffset, yoffset, x, y, width, height);
    }

    /**
     * Creates an empty array buffer view of the specified type.
     *
     * @param type The pixel data type.
     * @param size The size of the array.
     * @return An empty array buffer view.
     */
    public ArrayBufferView createArrayBufferView(PixelDataType type, int size) {
        return new ArrayBufferView(getCanvas(), type, size);
    }
    
    /**
     * Creates and initializes a WebGLBuffer instance for storing data such as vertices or colors.
     *
     * @return A WebGLBuffer instance.
     */
    public WebGLBuffer createBuffer() {
        return new WebGLBuffer(getCanvas());
    }

    /**
     * Creates and initializes a WebGLFrameBuffer instance.
     *
     * @return A WebGLFrameBuffer instance.
     */
    public WebGLFrameBuffer createFrameBuffer() {
        return new WebGLFrameBuffer(getCanvas());
    }

    /**
     * Creates and initializes a WebGLProgram instance.
     *
     * @return A WebGLProgram instance.
     */
    public WebGLProgram createProgram() {
        return new WebGLProgram(getCanvas());
    }

    /**
     * Creates and initializes a WebGLRenderBuffer instance.
     *
     * @return A WebGLRenderBuffer instance.
     */
    public WebGLRenderBuffer createRenderBuffer() {
        return new WebGLRenderBuffer(getCanvas());
    }

    /**
     * Creates a WebGLShader instance of the specified type.
     *
     * @param type The shader type.
     * @return A WebGLShader instance.
     */
    public WebGLShader createShader(ShaderType type) {
        return new WebGLShader(getCanvas(), type);
    }

    /**
     * Creates a new texture.
     *
     * @return The new texture.
     */
    public WebGLTexture createTexture() {
        return new WebGLTexture(getCanvas());
    }

    /**
     * Specifies whether or not front- and/or back-facing polygons can be culled.
     *
     * @param mode The culling mode.
     */
    public void cullFace(FaceMode mode) {
        invoke("cullFace", mode);
    }

    /**
     * Deletes a given WebGLBuffer. This method has no effect if the buffer has already been
     * deleted.
     *
     * @param buffer Buffer to delete.
     */
    public void deleteBuffer(WebGLBuffer buffer) {
        invoke("deleteBuffer", buffer);
    }

    /**
     * Deletes a given WebGLFrameBuffer. This method has no effect if the buffer has already been
     * deleted.
     *
     * @param buffer Buffer to delete.
     */
    public void deleteFrameBuffer(WebGLFrameBuffer buffer) {
        invoke("deleteFrameBuffer", buffer);
    }

    /**
     * Deletes a given WebGLProgram. This method has no effect if the program has already been
     * deleted.
     *
     * @param program Program to delete.
     */
    public void deleteProgram(WebGLProgram program) {
        invoke("deleteProgram", program);
    }

    /**
     * Deletes a given WebGLRenderBuffer. This method has no effect if the buffer has already been
     * deleted.
     *
     * @param buffer Buffer to delete.
     */
    public void deleteRenderBuffer(WebGLRenderBuffer buffer) {
        invoke("deleteRenderBuffer", buffer);
    }

    /**
     * Marks a given WebGLShader object for deletion. It will then be deleted whenever the shader is
     * no longer in use. This method has no effect if the shader has already been deleted, and the
     * WebGLShader is automatically marked for deletion when it is destroyed by the garbage
     * collector.
     *
     * @param shader Shader to delete.
     */
    public void deleteShader(WebGLShader shader) {
        invoke("deleteShader", shader);
    }

    /**
     * Deletes a given WebGLTexture. This method has no effect if the texture has already been
     * deleted.
     *
     * @param texture Texture to delete.
     */
    public void deleteTexture(WebGLTexture texture) {
        invoke("deleteTexture", texture);
    }

    /**
     * Specifies a function that compares incoming pixel depth to the current depth buffer value.
     *
     * @param func The depth comparison function, which sets the conditions under which the pixel
     *            will be drawn.
     *            <p>
     *            Default: LESS
     */
    public void depthFunc(DepthFunction func) {
        invoke("depthFunc", func);
    }

    /**
     * Sets whether writing into the depth buffer is enabled or disabled.
     *
     * @param flag If true, writing into the depth buffer is enabled.
     */
    public void depthMask(boolean flag) {
        invoke("depthMask", flag);
    }

    /**
     * Specifies the depth range mapping from normalized device coordinates to window or viewport
     * coordinates.
     *
     * @param zNear Specifies the mapping of the near clipping plane to window or viewport
     *            coordinates. Clamped to the range 0 to 1 and must be less than or equal to zFar.
     *            <p>
     *            Constraints: &ge;0 and &le;1
     *            <p>
     *            Default: 0
     * @param zFar Specifies the mapping of the far clipping plane to window or viewport
     *            coordinates.
     *            <p>
     *            Constraints: &ge;0 and &le;1
     *            <p>
     *            Default: 1
     */
    public void depthRange(double zNear, double zFar) {
        invoke("depthRange", zNear, zFar);
    }

    /**
     * Detaches a previously attached WebGLShader from a WebGLProgram.
     *
     * @param program A program.
     * @param shader The shader to detach.
     */
    public void detachShader(WebGLProgram program, WebGLShader shader) {
        invoke("detachShader", program, shader);
        program.getShaders().remove(shader);
    }

    /**
     * Disable a WebGL capability.
     *
     * @param cap The WebGL capability to disable.
     */
    public void disable(Capability cap) {
        invoke("disable", cap);
    }

    /**
     * Turns off the generic vertex attribute array at a given index position.
     *
     * @param index The index position.
     */
    public void disableVertexAttribArray(int index) {
        invoke("disableVertexAttribArray", index);
    }

    /**
     * Renders primitives from array data.
     *
     * @param mode The drawing mode.
     * @param first The starting index in the array of vector points.
     * @param count The number of indices to be rendered.
     */
    public void drawArrays(DrawMode mode, int first, int count) {
        invoke("drawArrays", mode, first, count);
    }

    /**
     * Renders primitives from array data.
     *
     * @param mode The drawing mode.
     * @param count The number of elements to be rendered.
     * @param type The type of the values in the element array buffer.
     *            <p>
     *            Constraints: one of UNSIGNED_BYTE, UNSIGNED_SHORT, UNSIGNED_INT
     * @param offset The offset in the element array buffer. Must be a valid multiple of the size of
     *            the given type.
     */
    public void drawElements(DrawMode mode, int count, ValueType type, int offset) {
        ValueType.validate(type, ValueType.UNSIGNED_BYTE, ValueType.UNSIGNED_SHORT, ValueType.UNSIGNED_INT);
        invoke("drawElements", count, type, offset);
    }

    /**
     * Enable a WebGL capability.
     *
     * @param cap The WebGL capability to enable.
     */
    public void enable(Capability cap) {
        invoke("enable", cap);
    }

    /**
     * Turns on the generic vertex attribute array at a given index position.
     *
     * @param index The index position.
     */
    public void enableVertexAttribArray(int index) {
        invoke("enableVertexAttribArray", index);
    }

    /**
     * Blocks execution until all previously called commands are finished.
     */
    public void finish() {
        invoke("finish");
    }

    /**
     * Empties different buffer commands, causing all commands to be executed as quickly as
     * possible.
     */
    public void flush() {
        invoke("flush");
    }

    /**
     * Attaches a WebGLRenderbuffer object to a WebGLFramebuffer object.
     *
     * @param target The binding point (target) for the frame buffer.
     * @param attachment The attachment point for the buffer.
     * @param renderbuffertarget The binding point (target) for the render buffer.
     * @param renderbuffer The render buffer to attach.
     */
    public void framebufferRenderbuffer(FrameBufferBinding target, AttachmentPoint attachment,
                                        RenderBufferBinding renderbuffertarget, WebGLRenderBuffer renderbuffer) {
        invoke("framebufferRenderbuffer", target, attachment, renderbuffertarget, renderbuffer);
    }

    /**
     * Attaches a texture to a WebGLFramebuffer.
     *
     * @param target The binding point (target) for the frame buffer.
     * @param attachment The attachment point for the buffer.
     * @param textarget The texture target.
     * @param texture The texture whose image is to be attached.
     */
    public void framebufferTexture2D(FrameBufferBinding target, AttachmentPoint attachment, TextureType2 textarget,
                                     WebGLTexture texture) {
        invoke("framebufferTexture2D", target, attachment, textarget, texture, 0);
    }

    /**
     * Specifies whether polygons are front- or back-facing by setting a winding orientation.
     *
     * @param mode The winding orientation.
     *            <p>
     *            Default: CCW
     */
    public void frontFace(WindingMode mode) {
        invoke("frontFace", mode);
    }

    /**
     * Generates a set of mipmaps for a WebGLTexture object. Mipmaps are used to create distance
     * with objects. A higher-resolution mipmap is used for objects that are closer, and a
     * lower-resolution mipmap is used for objects that are farther away. It starts with the
     * resolution of the texture image and halves the resolution until a 1x1 dimension texture image
     * is created.
     *
     * @param target The binding point (target) of the active texture whose mipmaps will be
     *            generated.
     */
    public void generateMipmap(TextureType target) {
        invoke("generateMipmap", target);
    }

    /**
     * Returns a WebGLActiveInfo object containing size, type, and name of a vertex attribute. It is
     * generally used when querying unknown uniforms either for debugging or generic library
     * creation.
     *
     * @param program A WebGLProgram containing the vertex attribute.
     * @param index The index of the vertex attribute to get. This value is an index 0 to N - 1 as
     *            returned by gl.getProgramParameter(program, gl.ACTIVE_ATTRIBUTES).
     * @param callback Callback to receive result.
     */
    public void getActiveAttrib(WebGLProgram program, int index, IWebGLInfoCallback callback) {
        new WebGLActiveInfo("getActiveAttrib", program, index, callback);
    }

    /**
     * Returns a WebGLActiveInfo object containing size, type, and name of a uniform attribute. It
     * is generally used when querying unknown uniforms either for debugging or generic library
     * creation.
     *
     * @param program A WebGLProgram specifying the WebGL shader program from which to obtain the
     *            uniform variable's information.
     * @param index The index of the uniform attribute to get. This value is an index 0 to N - 1 as
     *            returned by gl.getProgramParameter(program, gl.ACTIVE_UNIFORMS).
     * @param callback Callback to receive result.
     */
    public void getActiveUniform(WebGLProgram program, int index, IWebGLInfoCallback callback) {
        new WebGLActiveInfo("getActiveUniform", program, index, callback);
    }

    /**
     * Returns a list of WebGLShader objects attached to a WebGLProgram.
     *
     * @param program A WebGLProgram object to get attached shaders for.
     * @return A set of WebGLShader objects that are attached to the given WebGLProgram.
     */
    public Set<WebGLShader> getAttachedShaders(WebGLProgram program) {
        return Collections.unmodifiableSet(program.getShaders());
    }
    
    /**
     * Returns the location of an attribute variable in a given WebGLProgram.
     *
     * @param program A WebGLProgram containing the attribute variable.
     * @param name A DOMString specifying the name of the attribute variable whose location to get.
     * @param callback Callback to receive result.
     */
    public void getAttribLocation(WebGLProgram program, String name, IResponseCallback<Integer> callback) {
        invoke(callback, "getAttribLocation", program, name);
    }

    /**
     * Returns information about the buffer.
     *
     * @param target The target buffer object.
     * @param pname Requested parameter. If BUFFER_SIZE, the response will be the size of the buffer
     *            in bytes. If BUFFER_USAGE, the response will be a {@link BufferUsagePattern}.
     * @param callback Callback to receive result.
     */
    public void getBufferParameter(BufferBinding target, BufferParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getBufferParameter", target, pname);
    }

    /**
     * The actual context parameters, or null if the context is lost.
     *
     * @param callback Callback to receive result.
     */
    public void getContextAttributes(IResponseCallback<Map<String, Object>> callback) {
        invoke(callback, "getContextAttributes");
    }
    
    /**
     * Returns error information.
     *
     * @param callback Callback to receive result.
     */
    public void getError(IResponseCallback<ErrorType> callback) {
        invoke((response) -> {
            IResponseCallback.invoke(callback, IEnumWithValue.fromValue(ErrorType.class, (Integer) response));
        }, "getError");
    }

    /**
     * Returns information about a framebuffer's attachment.
     *
     * @param target The binding point (target).
     * @param attachment The attachment point for the texture.
     * @param pname Information to query.
     * @param callback Callback to receive result.
     */
    public void getFramebufferAttachmentParameter(FrameBufferBinding target, AttachmentPoint attachment,
                                                  AttachmentParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getFramebufferAttachmentParameter", target, attachment, pname);
    }
    
    /**
     * returns a value for the passed parameter name.
     *
     * @param pname Parameter value to return.
     * @param callback Callback to receive result.
     */
    public void getParameter(ContextParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getParameter", pname);
    }

    /**
     * Returns the information log for the specified WebGLProgram object. It contains errors that
     * occurred during failed linking or validation of WebGLProgram objects.
     *
     * @param program A WebGLProgram to query.
     * @param callback Callback to receive result.
     */
    public void getProgramInfoLog(WebGLProgram program, IResponseCallback<String> callback) {
        invoke(callback, "getProgramInfoLog", program);
    }

    /**
     * Returns information about the given program.
     *
     * @param program A WebGLProgram to get parameter information from.
     * @param pname The information to query
     * @param callback Callback to receive result.
     */
    public void getProgramParameter(WebGLProgram program, ProgramParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getProgramParameter", program, pname);
    }
    
    /**
     * Returns information about the renderbuffer.
     *
     * @param target The target renderbuffer object.
     * @param pname The information to query.
     * @param callback Callback to receive result.
     */
    public void getRenderbufferParameter(RenderBufferBinding target, RenderBufferParameterType pname,
                                         IResponseCallback<Object> callback) {
        invoke(callback, "getRenderbufferParameter", target, pname);
    }

    /**
     * Returns the information log for the specified WebGLShader object. It contains warnings,
     * debugging and compile information. When a WebGLShader object is initially created, its
     * information log will be a string of length 0.
     *
     * @param shader A WebGLShader to query.
     * @param callback Callback to receive result.
     */
    public void getShaderInfoLog(WebGLShader shader, IResponseCallback<String> callback) {
        invoke(callback, "getShaderInfoLog", shader);
    }
    
    /**
     * Returns information about the given shader.
     *
     * @param shader A WebGLShader to get parameter information from.
     * @param pname The information to query.
     * @param callback Callback to receive result.
     */
    public void getShaderParameter(WebGLShader shader, ShaderParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getShaderParameter", shader, pname);
    }

    /**
     * Returns a new WebGLShaderPrecisionFormat object describing the range and precision for the
     * specified shader numeric format.
     *
     * @param shaderType The shader type.
     * @param precisionType The precision type.
     * @param callback Callback to receive result.
     */
    public void getShaderPrecisionFormat(ShaderType shaderType, PrecisionType precisionType,
                                         IResponseCallback<WebGLShaderPrecisionFormat> callback) {
        new WebGLShaderPrecisionFormat(getCanvas(), shaderType, precisionType, callback);
    }

    /**
     * Returns the source code of a WebGLShader.
     *
     * @param shader A WebGLShader object to get the source code from.
     * @param callback Callback to receive result.
     */
    public void getShaderSource(WebGLShader shader, IResponseCallback<String> callback) {
        invoke(callback, "getShaderSource", shader);
    }
    
    /**
     * Returns a list of all the supported WebGL extensions.
     *
     * @param callback Callback to receive result.
     */
    public void getSupportedExtensions(IResponseCallback<String[]> callback) {
        invoke(callback, "getSupportedExtensions");
    }

    /**
     * Returns information about the given texture.
     *
     * @param target The binding point (target).
     * @param pname The information to query
     * @param callback Callback to receive result.
     */
    public void getTexParameter(TextureType target, TextureParameterType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getTexParameter", target, pname);
    }
    
    /**
     * Returns the value of a uniform variable at a given location. For information on possible
     * return types, see <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/WebGLRenderingContext/getUniform">getUniform</a>.
     *
     * @param program A WebGLProgram containing the uniform attribute.
     * @param location A WebGLUniformLocation object containing the location of the uniform
     *            attribute to get.
     * @param callback Callback to receive result.
     */
    public void getUniform(WebGLProgram program, WebGLUniformLocation location, IResponseCallback<Object> callback) {
        invoke(callback, "getUniform", program, location);
    }

    /**
     * Returns the location of a specific uniform variable which is part of a given WebGLProgram.
     *
     * @param program The WebGLProgram in which to locate the specified uniform variable.
     * @param name the name of the uniform variable whose location is to be returned. The name can't
     *            have any whitespace in it, and you can't use this function to get the location of
     *            any uniforms starting with the reserved string "gl_", since those are internal to
     *            the WebGL layer. The possible values correspond to the uniform names returned by
     *            {@link #getActiveUniform}. Additionally, for uniforms declared as arrays, the
     *            following names are also valid:
     *            <ul>
     *            <li>The uniform name without the [0] suffix. E.g. the location returned for
     *            arrayUniform is equivalent to the one for arrayUniform[0].</li>
     *            <li>The uniform name indexed with an integer. E.g. the location returned for
     *            arrayUniform[2] would point directly to the third entry of the arrayUniform
     *            uniform.</li>
     *            </ul>
     * @return A WebGLUniformLocation value indicating the location of the named variable, if it
     *         exists.
     */
    public WebGLUniformLocation getUniformLocation(WebGLProgram program, String name) {
        return new WebGLUniformLocation(program, name);
    }

    /**
     * Returns information about a vertex attribute at a given position.
     *
     * @param index The index of the vertex attribute.
     * @param pname The information to query
     * @param callback Callback to receive result.
     */
    public void getVertexAttrib(int index, VertexAttributeType pname, IResponseCallback<Object> callback) {
        invoke(callback, "getVertexAttrib", index, pname);
    }
    
    /**
     * Returns the address of a specified vertex attribute.
     *
     * @param index The index of the vertex attribute.
     * @param pname The type of attribute offset.
     * @param callback Callback to receive result.
     */
    public void getVertexAttribOffset(int index, VertexAttributeOffsetType pname, IResponseCallback<Integer> callback) {
        invoke(callback, "getVertexAttribOffset", index, pname);
    }

    /**
     * Specifies hints for certain behaviors. The interpretation of these hints depends on the
     * implementation.
     *
     * @param target Which behavior to be controlled.
     * @param mode The behavior mode.
     */
    public void hint(HintBehavior target, HintMode mode) {
        invoke("hint", target, mode);
    }
    
    /**
     * Returns true if the passed WebGLBuffer is valid and false otherwise.
     *
     * @param buffer A WebGLBuffer to check.
     * @param callback Callback to receive result.
     */
    public void isBuffer(WebGLBuffer buffer, IResponseCallback<Boolean> callback) {
        invoke(callback, "isBuffer", buffer);
    }

    /**
     * Returns whether or not the WebGL context has been lost.
     *
     * @param callback Callback to receive result.
     */
    public void isContextLost(IResponseCallback<Boolean> callback) {
        invoke(callback, "isContextLost");
    }

    /**
     * Returns whether a specific WebGL capability is enabled or not for this context.
     *
     * @param cap Which WebGL capability to test.
     * @param callback Callback to receive result.
     */
    public void isEnabled(Capability cap, IResponseCallback<Boolean> callback) {
        invoke(callback, "isEnabled", cap);
    }

    /**
     * Returns true if the passed WebGLFramebuffer is valid and false otherwise.
     *
     * @param framebuffer A WebGLFramebuffer to check.
     * @param callback Callback to receive result.
     */
    public void isFramebuffer(WebGLFrameBuffer framebuffer, IResponseCallback<Boolean> callback) {
        invoke(callback, "isFramebuffer", framebuffer);
    }

    /**
     * Returns true if the passed WebGLProgram is valid and false otherwise.
     *
     * @param program A WebGLProgram to check.
     * @param callback Callback to receive result.
     */
    public void isProgram(WebGLProgram program, IResponseCallback<Boolean> callback) {
        invoke(callback, "isProgram", program);
    }

    /**
     * Returns true if the passed WebGLRenderBuffer is valid and false otherwise.
     *
     * @param renderbuffer A WebGLRenderBuffer to check.
     * @param callback Callback to receive result.
     */
    public void isRenderbuffer(WebGLRenderBuffer renderbuffer, IResponseCallback<Boolean> callback) {
        invoke(callback, "isRenderbuffer", renderbuffer);
    }

    /**
     * Returns true if the passed WebGLShader is valid and false otherwise.
     *
     * @param shader A WebGLShader to check.
     * @param callback Callback to receive result.
     */
    public void isShader(WebGLShader shader, IResponseCallback<Boolean> callback) {
        invoke(callback, "isShader", shader);
    }

    /**
     * Returns true if the passed WebGLTexture is valid and false otherwise.
     *
     * @param texture A WebGLTexture to check.
     * @param callback Callback to receive result.
     */
    public void isTexture(WebGLTexture texture, IResponseCallback<Boolean> callback) {
        invoke(callback, "isTexture", texture);
    }

    /**
     * Links a given WebGLProgram to the attached vertex and fragment shaders.
     *
     * @param program Program to link.
     */
    public void linkProgram(WebGLProgram program) {
        invoke("linkProgram", program);
    }

    /**
     * Specifies the pixel storage modes.
     *
     * @param pname The parameter to set.
     * @param value The value to set into the parameter.
     */
    public void pixelStorei(PixelStorageParameter pname, Object value) {
        invoke("pixelStorei", pname, value);
    }
    
    /**
     * Specifies the scale factors and units to calculate depth values. The offset is added before
     * the depth test is performed and before the value is written into the depth buffer.
     *
     * @param factor The scale factor for the variable depth offset for each polygon.
     *            <p>
     *            Default: 0
     * @param units The multiplier by which an implementation-specific value is multiplied with to
     *            create a constant depth offset.
     *            <p>
     *            Default: 0
     */
    public void polygonOffset(double factor, double units) {
        invoke("polygonOffset", factor, units);
    }

    /**
     * Reads a block of pixels from a specified rectangle of the current color framebuffer into an
     * ArrayBufferView object.
     *
     * @param x The first horizontal pixel that is read from the lower left corner of a rectangular
     *            block of pixels.
     * @param y The first vertical pixel that is read from the lower left corner of a rectangular
     *            block of pixels.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param format The format of the pixel data.
     * @param pixels An ArrayBufferView object to read data into.
     */
    public void readPixels(int x, int y, int width, int height, PixelFormat format, ArrayBufferView pixels) {
        invoke("readPixels", x, y, width, height, format, pixels.getType(), pixels);
    }

    /**
     * Reads a block of pixels from a specified rectangle of the current color framebuffer into an
     * ArrayBufferView object.
     *
     * @param x The first horizontal pixel that is read from the lower left corner of a rectangular
     *            block of pixels.
     * @param y The first vertical pixel that is read from the lower left corner of a rectangular
     *            block of pixels.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param format The format of the pixel data.
     * @param pixels An ArrayBufferView object to read data into.
     * @param offset The starting array position.
     */
    public void readPixels(int x, int y, int width, int height, PixelFormat format, ArrayBufferView pixels, int offset) {
        invoke("readPixels", x, y, width, height, format, pixels.getType(), pixels, offset);
    }

    /**
     * Creates and initializes a renderbuffer object's data store.
     *
     * @param target The target renderbuffer object.
     * @param internalFormat The internal format of the renderbuffer.
     * @param width The width of the renderbuffer in pixels.
     * @param height The height of the renderbuffer in pixels.
     */
    public void renderbufferStorage(RenderBufferBinding target, RenderBufferInternalFormat internalFormat, int width,
                                    int height) {
        invoke("renderbufferStorage", target, internalFormat, width, height);
    }
    
    /**
     * Specifies multi-sample coverage parameters for anti-aliasing effects.
     *
     * @param value Coverage value.
     *            <p>
     *            Constraints: &ge;0 and &le;1
     *            <p>
     *            Default: 1.0
     * @param invert Whether or not the coverage masks should be inverted.
     *            <p>
     *            Default: false
     */
    public void sampleCoverage(double value, boolean invert) {
        invoke("sampleCoverage", value, invert);
    }

    /**
     * Sets a scissor box, which limits the drawing to a specified rectangle.
     *
     * @param x The horizontal coordinate for the lower left corner of the box.
     *            <p>
     *            Default: 0
     * @param y The vertical coordinate for the lower left corner of the box.
     *            <p>
     *            Default: 0
     * @param width The width of the scissor box.
     *            <p>
     *            Default: width of the canvas
     * @param height The height of the scissor box.
     *            <p>
     *            Default: height of the canvas
     */
    public void scissor(int x, int y, int width, int height) {
        invoke("scissor", x, y, width, height);
    }

    /**
     * Sets the source code of a WebGLShader.
     *
     * @param shader A WebGLShader object in which to set the source code.
     * @param source The GLSL source code to set.
     */
    public void shaderSource(WebGLShader shader, String source) {
        invoke("shaderSource", shader, source);
    }
    
    /**
     * Sets the front and back function and reference value for stencil testing. Stenciling enables
     * and disables drawing on a per-pixel basis. It is typically used in multipass rendering to
     * achieve special effects.
     *
     * @param func The test function.
     *            <p>
     *            Default: ALWAYS
     * @param ref the reference value for the stencil test.
     *            <p>
     *            Constraints: &ge;0 and &le;2n-1, where n is the number of bitplanes in the stencil
     *            buffer.
     *            <p>
     *            Default: 0
     * @param mask A bit-wise mask that is used to AND the reference value and the stored stencil
     *            value when the test is done.
     *            <p>
     *            Default: all 1's
     */
    public void stencilFunc(DepthFunction func, int ref, int mask) {
        invoke("stencilFunc", func, ref, mask);
    }
    
    /**
     * Sets the front and back function and reference value for stencil testing. Stenciling enables
     * and disables drawing on a per-pixel basis. It is typically used in multipass rendering to
     * achieve special effects.
     *
     * @param face Whether the front and/or back stencil state is updated.
     * @param func The test function.
     *            <p>
     *            Default: ALWAYS
     * @param ref the reference value for the stencil test.
     *            <p>
     *            Constraints: &ge;0 and &le;2n-1, where n is the number of bitplanes in the stencil
     *            buffer.
     *            <p>
     *            Default: 0
     * @param mask A bit-wise mask that is used to AND the reference value and the stored stencil
     *            value when the test is done.
     *            <p>
     *            Default: all 1's
     */
    public void stencilFuncSeparate(FaceMode face, DepthFunction func, int ref, int mask) {
        invoke("stencilFuncSeparate", func, ref, mask);
    }
    
    /**
     * Controls enabling and disabling of both the front and back writing of individual bits in the
     * stencil planes. The {@link #stencilMaskSeparate} method can set front and back stencil
     * writemasks to different values.
     *
     * @param mask a bit mask to enable or disable writing of individual bits in the stencil planes.
     *            <p>
     *            Default: all 1's
     */
    public void stencilMask(int mask) {
        invoke("stencilMask", mask);
    }

    /**
     * Controls enabling and disabling of the front and/or back writing of individual bits in the
     * stencil planes. The {@link #stencilMaskSeparate} method can set front and back stencil
     * writemasks to different values.
     *
     * @param face Whether the front and/or back stencil writemask is updated.
     * @param mask a bit mask to enable or disable writing of individual bits in the stencil planes.
     *            <p>
     *            Default: all 1's
     */
    public void stencilMaskSeparate(FaceMode face, int mask) {
        invoke("stencilMaskSeparate", face, mask);
    }

    /**
     * Sets both the front and back-facing stencil test actions.
     *
     * @param fail The function to use when the stencil test fails.
     *            <p>
     *            Default: KEEP
     * @param zfail the function to use when the stencil test passes, but the depth test fails.
     *            <p>
     *            Default: KEEP
     * @param zpass the function to use when both the stencil test and the depth test pass, or when
     *            the stencil test passes and there is no depth buffer or depth testing is disabled.
     *            <p>
     *            Default: KEEP
     */
    public void stencilOp(StencilFunction fail, StencilFunction zfail, StencilFunction zpass) {
        invoke("stencipOp", fail, zfail, zpass);
    }

    /**
     * Sets the front and/or back-facing stencil test actions.
     *
     * @param face The face mode.
     * @param fail The function to use when the stencil test fails.
     *            <p>
     *            Default: KEEP
     * @param zfail the function to use when the stencil test passes, but the depth test fails.
     *            <p>
     *            Default: KEEP
     * @param zpass the function to use when both the stencil test and the depth test pass, or when
     *            the stencil test passes and there is no depth buffer or depth testing is disabled.
     *            <p>
     *            Default: KEEP
     */
    public void stencilOpSeparate(FaceMode face, StencilFunction fail, StencilFunction zfail, StencilFunction zpass) {
        invoke("stencipOpSeparate", face, fail, zfail, zpass);
    }

    /**
     * Specifies a two-dimensional texture image.
     *
     * @param target The binding point (target) of the active texture.
     * @param level The level of detail. Level 0 is the base image level and level n is the nth
     *            mipmap reduction level.
     * @param internalformat The color components in the texture.
     * @param width the width of the texture.
     * @param height the height of the texture.
     * @param format the format of the texel data. In WebGL 1, this must be the same as
     *            internalformat (see above). In WebGL 2, the combinations are listed in <a href=
     *            "https://www.khronos.org/registry/webgl/specs/latest/2.0/#TEXTURE_TYPES_FORMATS_FROM_DOM_ELEMENTS_TABLE">this
     *            table</a>.
     * @param pixels The ArrayViewBuffer.
     */
    public void texImage2D(TextureType2 target, int level, TextureBufferInternalFormat internalformat, int width, int height,
                           TextureBufferInternalFormat format, ArrayBufferView pixels) {
        invoke("texImage2D", target, level, internalformat, width, height, 0, format, pixels.getType(), pixels);
    }

    /**
     * Set texture parameters.
     *
     * @param target The binding point (target).
     * @param pname The texture parameter to set.
     * @param param The value to set into the parameter. See <a href=
     *            "https://developer.mozilla.org/en-US/docs/Web/API/WebGLRenderingContext/texParameter">texParameter</a>
     *            for information on value constraints.
     */
    public void texParameterf(TextureType target, TextureParameterType pname, double param) {
        invoke("texParameterf", target, pname, param);
    }
    
    /**
     * Set texture parameters.
     *
     * @param target The binding point (target).
     * @param pname The texture parameter to set.
     * @param param The value to set into the parameter. See <a href=
     *            "https://developer.mozilla.org/en-US/docs/Web/API/WebGLRenderingContext/texParameter">texParameter</a>
     *            for information on value constraints.
     */
    public void texParameteri(TextureType target, TextureParameterType pname, int param) {
        invoke("texParameteri", target, pname, param);
    }
    
    /**
     * Specifies a sub-rectangle of the current texture.
     *
     * @param target the binding point (target) of the active texture.
     * @param level the level of detail. Level 0 is the base image level and level n is the nth
     *            mipmap reduction level.
     * @param xoffset the horizontal offset within the texture image.
     * @param yoffset the vertical offset within the texture image.
     * @param width the width of the texture.
     * @param height the height of the texture.
     * @param format the format of the texel data.
     * @param pixels The array buffer view.
     */
    public void texSubImage2D(TextureType2 target, int level, int xoffset, int yoffset, int width, int height,
                              TextureFormat format, ArrayBufferView pixels) {
        invoke("texSubImage2D", target, level, xoffset, yoffset, width, height, format, pixels);
    }

    /**
     * Specify values of uniform variables.
     *
     * @param location A WebGLUniformLocation object containing the location of the uniform
     *            attribute to modify.
     * @param values The value(s) to set.
     */
    public void uniform(WebGLUniformLocation location, Double... values) {
        int i = values.length;
        Assert.isTrue(i > 0 && i < 5, "Must specify between 1 and 4 values.");
        invoke(String.format("uniform%dfv", i), new Object[] { values });
    }

    /**
     * Specify values of uniform variables.
     *
     * @param location A WebGLUniformLocation object containing the location of the uniform
     *            attribute to modify.
     * @param values The value(s) to set.
     */
    public void uniform(WebGLUniformLocation location, Integer... values) {
        int i = values.length;
        Assert.isTrue(i > 0 && i < 5, "Must specify between 1 and 4 values.");
        invoke(String.format("uniform%div", i), new Object[] { values });
    }

    /**
     * Specify values of uniform variables.
     *
     * @param location A WebGLUniformLocation object containing the location of the uniform
     *            attribute to modify.
     * @param values The value(s) to set.
     */
    public void uniformMatrix(WebGLUniformLocation location, Double... values) {
        int i = values.length;
        Assert.isTrue(i > 1 && i < 5, "Must specify between 2 and 4 values.");
        invoke(String.format("uniformMatrix%dfv", i), false, values);
    }

    /**
     * Sets the specified WebGLProgram as part of the current rendering state.
     *
     * @param program A WebGLProgram to use.
     */
    public void useProgram(WebGLProgram program) {
        invoke("useProgram", program);
    }
    
    /**
     * Checks if the program is successfully linked and if it can be used in the current WebGL
     * state.
     *
     * @param program The program to check.
     */
    public void validateProgram(WebGLProgram program) {
        invoke("validateProgram", program);
    }

    /**
     * Specify constant values for generic vertex attributes.
     *
     * @param index The position of the vertex attribute to be modified.
     * @param values The vertex attribute value(s).
     */
    public void vertexAttrib(int index, Double... values) {
        int i = values.length;
        Assert.isTrue(i > 0 && i < 5, "Must specify between 1 and 4 values.");
        invoke(String.format("vertexAttrib%dfv", i), new Object[] { values });
    }

    /**
     * Binds the buffer currently bound to ARRAY_BUFFER to a generic vertex attribute of the current
     * vertex buffer object and specifies its layout.
     *
     * @param index The index of the vertex attribute that is to be modified.
     * @param size The number of components per vertex attribute.
     *            <p>
     *            Constraints: 1, 2, 3, or 4
     * @param type The data type of each component in the array.
     *            <p>
     *            Constraints: one of BYTE, SHORT, UNSIGNED_BYTE, UNSIGNED_SHORT, FLOAT, HALF_FLOAT
     * @param normalized Whether integer data values should be normalized into a certain range when
     *            being casted to a float.
     * @param stride The offset in bytes between the beginning of consecutive vertex attributes.
     *            Cannot be larger than 255. If stride is 0, the attribute is assumed to be tightly
     *            packed, that is, the attributes are not interleaved but each attribute is in a
     *            separate block, and the next vertex' attribute follows immediately after the
     *            current vertex.
     * @param offset Offset in bytes of the first component in the vertex attribute array. Must be a
     *            multiple of type.
     */
    public void vertexAttribPointer(int index, int size, ValueType type, boolean normalized, int stride, int offset) {
        ValueType.validate(type, ValueType.BYTE, ValueType.SHORT, ValueType.UNSIGNED_BYTE, ValueType.UNSIGNED_SHORT,
            ValueType.FLOAT, ValueType.HALF_FLOAT);
        invoke("vertexAttribPointer", index, size, type, normalized, stride, offset);
    }

    /**
     * Sets the viewport, which specifies the affine transformation of x and y from normalized
     * device coordinates to window coordinates.
     *
     * @param x The horizontal coordinate for the lower left corner of the viewport origin.
     *            <p>
     *            Default: 0
     * @param y The vertical coordinate for the lower left corner of the viewport origin.
     *            <p>
     *            Default: 0
     * @param width The width of the viewport.
     *            <p>
     *            Default: width of the canvas.
     * @param height The height of the viewport.
     *            <p>
     *            Default: height of the canvas.
     */
    public void viewport(int x, int y, int width, int height) {
        invoke("viewport", x, y, width, height);
    }
}
