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
package org.fujion.testharness;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.canvas.d2.Canvas2D;
import org.fujion.canvas.d2.CanvasGradient;
import org.fujion.canvas.d2.RenderingContext2D;
import org.fujion.canvas.webgl.ArrayBuffer;
import org.fujion.canvas.webgl.BufferBinding;
import org.fujion.canvas.webgl.BufferBitMask;
import org.fujion.canvas.webgl.BufferUsagePattern;
import org.fujion.canvas.webgl.CanvasWebGL;
import org.fujion.canvas.webgl.DrawMode;
import org.fujion.canvas.webgl.RenderingContextWebGL;
import org.fujion.canvas.webgl.ShaderType;
import org.fujion.canvas.webgl.ValueType;
import org.fujion.canvas.webgl.WebGLBuffer;
import org.fujion.canvas.webgl.WebGLProgram;
import org.fujion.canvas.webgl.WebGLShader;
import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.fujion.component.Button;
import org.fujion.component.Doublebox;
import org.fujion.component.Timer;

/**
 * Sample controller to demonstrate canvas component.
 */
public class CanvasController extends BaseController {

    @WiredComponent
    private Canvas2D canvas2D;
    
    @WiredComponent
    private CanvasWebGL canvasWebGL;
    
    @WiredComponent
    private Doublebox zoomCenterXInput;

    @WiredComponent
    private Doublebox zoomCenterYInput;

    @WiredComponent
    private Doublebox zoomOutput;

    @WiredComponent
    private Doublebox centerOffsetXOutput;
    
    @WiredComponent
    private Doublebox centerOffsetYOutput;
    
    @WiredComponent
    private Timer timer;

    @WiredComponent
    private Button btnRenderWebGL;
    
    private double zoom;
    
    private double zoomCenterX;
    
    private double zoomCenterY;
    
    private double centerOffsetX = 0;
    
    private double centerOffsetY = 0;
    
    private WebGLBuffer vertexPositionBuffer;
    
    private WebGLProgram shaderProgram;
    
    private int aVertexPosition;
    
    private int aPlotPosition;

    private final int viewportWidth = 500;

    private final int viewportHeight = 500;
    
    private RenderingContextWebGL gl;
    
    @EventHandler(value = "click", target = "btnRender2D")
    private void onClick$render2D() {
        RenderingContext2D ctx = canvas2D.newRenderingContext();
        CanvasGradient gradient = ctx.createLinearGradient(0, 0, 100, 200);
        gradient.addColorStop(.25, "red");
        gradient.addColorStop(.75, "yellow");
        gradient.addColorStop(1, "blue");
        ctx.setFillStyle(gradient);
        ctx.fillRect(200, 0, 100, 200);
        ctx.beginPath();
        ctx.moveTo(50, 25);
        ctx.quadraticCurveTo(230, 30, 50, 110);
        ctx.stroke();
        ctx.setFillStyle("blue");
        ctx.fillRect(50, 20, 10, 10);
        ctx.fillRect(50, 100, 10, 10);
        ctx.setFillStyle("red");
        ctx.fillRect(100, 50, 10, 10);
        log("2D rendering completed.");
    }
    
    @EventHandler(value = "click", target = "@btnRenderWebGL")
    private void onClick$renderWebGL() {
        if (timer.isRunning()) {
            timer.stop();
            btnRenderWebGL.addClass("flavor:btn-success");
            log("WebGL rendering sequence stopped.");
        } else {
            timer.setInterval(50);
            resetZoom();
            gl = canvasWebGL.newRenderingContext();
            initShaders();
            initBuffers();
            gl.clearColor(0.0, 0.0, 0.0, 1.0);
            timer.start();
            btnRenderWebGL.addClass("flavor:btn-danger");
            log("WebGL rendering sequence started.");
        }
    }

    @EventHandler(value = "timer", target = "@timer")
    private void onTimer$timer() {
        drawScene();
    }
    
    private void resetZoom() {
        zoom = 1.0;
        zoomCenterX = zoomCenterXInput.getValue();
        zoomCenterY = zoomCenterYInput.getValue();
    }
    
    private WebGLShader getShader(ShaderType type, String id) {
        try (InputStream is = getClass().getResourceAsStream("/" + id + ".webgl");) {
            String src = StrUtil.fromList(IOUtils.readLines(is, StrUtil.UTF8));
            WebGLShader shader = gl.createShader(type);
            gl.shaderSource(shader, src);
            gl.compileShader(shader);
            return shader;
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    private void initShaders() {
        WebGLShader fragmentShader = getShader(ShaderType.FRAGMENT_SHADER, "shader-fs");
        WebGLShader vertexShader = getShader(ShaderType.VERTEX_SHADER, "shader-vs");
        
        shaderProgram = gl.createProgram();
        gl.attachShader(shaderProgram, vertexShader);
        gl.attachShader(shaderProgram, fragmentShader);
        gl.linkProgram(shaderProgram);
        gl.useProgram(shaderProgram);
        
        gl.getAttribLocation(shaderProgram, "aVertexPosition", (value) -> {
            aVertexPosition = value;
            gl.enableVertexAttribArray(aVertexPosition);
        });
        
        gl.getAttribLocation(shaderProgram, "aPlotPosition", (value) -> {
            aPlotPosition = value;
            gl.enableVertexAttribArray(aPlotPosition);
        });
    }
    
    private void initBuffers() {
        vertexPositionBuffer = gl.createBuffer();
        gl.bindBuffer(BufferBinding.ARRAY_BUFFER, vertexPositionBuffer);
        double[] vertices = { 1.0, 1.0, -1.0, 1.0, 1.0, -1.0, -1.0, -1.0 };
        ArrayBuffer ary = new ArrayBuffer(canvasWebGL, vertices);
        gl.bufferData(BufferBinding.ARRAY_BUFFER, ary, BufferUsagePattern.STATIC_DRAW);
        //vertexPositionBuffer.itemSize = 2;
        //vertexPositionBuffer.numItems = 4;
    }
    
    double[][] baseCorners = { { 0.7, 1.2 }, { -2.2, 1.2 }, { 0.7, -1.2 }, { -2.2, -1.2 } };
    
    private void drawScene() {
        gl.viewport(0, 0, viewportWidth, viewportHeight);
        gl.clear(BufferBitMask.COLOR_BUFFER_BIT, BufferBitMask.DEPTH_BUFFER_BIT);
        gl.bindBuffer(BufferBinding.ARRAY_BUFFER, vertexPositionBuffer);
        gl.vertexAttribPointer(aVertexPosition, 2, ValueType.FLOAT, false, 0, 0);
        WebGLBuffer plotPositionBuffer = gl.createBuffer();
        gl.bindBuffer(BufferBinding.ARRAY_BUFFER, plotPositionBuffer);
        double[] corners = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        int idx = 0;
        
        for (double[] cornerIx : baseCorners) {
            double x = cornerIx[0];
            double y = cornerIx[1];
            corners[idx++] = (x / zoom + centerOffsetX);
            corners[idx++] = (y / zoom + centerOffsetY);
        }
        
        ArrayBuffer ary = new ArrayBuffer(canvasWebGL, corners);
        gl.bufferData(BufferBinding.ARRAY_BUFFER, ary, BufferUsagePattern.STATIC_DRAW);
        gl.vertexAttribPointer(aPlotPosition, 2, ValueType.FLOAT, false, 0, 0);
        gl.drawArrays(DrawMode.TRIANGLE_STRIP, 0, 4);
        gl.deleteBuffer(plotPositionBuffer);
        zoom *= 1.02;
        zoomOutput.setValue(zoom);

        if (centerOffsetX != zoomCenterX) {
            centerOffsetX += (zoomCenterX - centerOffsetX) / 20;
        }
        
        this.centerOffsetXOutput.setValue(centerOffsetX);
        
        if (centerOffsetY != zoomCenterY) {
            centerOffsetY += (zoomCenterY - centerOffsetY) / 20;
        }
        
        this.centerOffsetYOutput.setValue(centerOffsetY);
    }
    
}
