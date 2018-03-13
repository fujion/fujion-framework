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
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.canvas.d2.Canvas2D;
import org.fujion.canvas.d2.CanvasGradient;
import org.fujion.canvas.d2.LineCap;
import org.fujion.canvas.d2.RenderingContext2D;
import org.fujion.canvas.d2.TextAlign;
import org.fujion.canvas.d2.TextBaseline;
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

    /******************* 2D Rendering *****************/

    @WiredComponent
    private Canvas2D canvas2D;

    @WiredComponent
    private Timer timer2D;
    
    @WiredComponent
    private Button btnRender2D;

    private RenderingContext2D d2;

    private double radius = 200.0;
    
    @EventHandler(value = "click", target = "btnRender2D")
    private void onClick$render2D() {
        if (timer2D.isRunning()) {
            timer2D.stop();
            btnRender2D.addClass("flavor:btn-success");
            log("2D rendering sequence stopped.");
        } else {
            init2D();
            timer2D.start();
            btnRender2D.addClass("flavor:btn-danger");
            log("2D rendering sequence started.");
        }
    }

    @EventHandler(value = "timer", target = "@timer2D")
    private void onTimer$timer2D() {
        drawClock();
    }

    private void init2D() {
        d2 = canvas2D.newRenderingContext();
        radius = 200.0;
        d2.translate(radius, radius);
        radius = radius * 0.9;
        d2.setFont(radius * 0.15 + "px arial");
        d2.setTextBaseline(TextBaseline.MIDDLE);
        d2.setTextAlign(TextAlign.CENTER);
        drawClock();
    }
    
    private void drawClock() {
        drawFace();
        drawNumbers();
        drawTime();
    }

    private void drawFace() {
        d2.beginPath();
        d2.setFillStyle("white");
        CanvasGradient grad = d2.createRadialGradient(0, 0, radius * 0.95, 0, 0, radius * 1.05);
        grad.addColorStop(0, "#333");
        grad.addColorStop(0.5, "white");
        grad.addColorStop(1, "#333");
        d2.setStrokeStyle(grad);
        d2.setLineWidth(radius * 0.1);
        d2.arc(0, 0, radius, 0, 2 * Math.PI);
        d2.fill();
        d2.stroke();
        d2.setStrokeStyle("#333");
        d2.beginPath();
        d2.setFillStyle("#333");
        d2.arc(0, 0, radius * 0.1, 0, 2 * Math.PI);
        d2.fill();
    }
    
    private void drawNumbers() {
        for (int num = 1; num < 13; num++) {
            double ang = num * Math.PI / 6;
            d2.rotateRadians(ang);
            d2.translate(0, -radius * 0.85);
            d2.rotateRadians(-ang);
            d2.fillText(Integer.toString(num), 0, 0);
            d2.rotateRadians(ang);
            d2.translate(0, radius * 0.85);
            d2.rotateRadians(-ang);
        }
    }

    private void drawGlass() {
        CanvasGradient grad = d2.createRadialGradient(-0.4 * radius, -0.4 * radius, 0, -0.4 * radius, -0.4 * radius,
            14 * radius / 12);
        grad.addColorStop(0, "rgba(255,255,255,0.8)");
        grad.addColorStop(1, "rgba(255,255,255,0)");
        d2.setFillStyle(grad);
        d2.beginPath();
        d2.arc(0, 0, radius, 0, 2 * Math.PI);
        d2.fill();
    }
    
    private void drawTime() {
        Calendar now = Calendar.getInstance();
        double hour = now.get(Calendar.HOUR);
        double minute = now.get(Calendar.MINUTE);
        double second = now.get(Calendar.SECOND);
        //hour
        hour = hour % 12;
        hour = (hour * Math.PI / 6) + (minute * Math.PI / (6 * 60)) + (second * Math.PI / (360 * 60));
        drawHand(hour, radius * 0.5, radius * 0.07);
        //minute
        minute = (minute * Math.PI / 30) + (second * Math.PI / (30 * 60));
        drawHand(minute, radius * 0.8, radius * 0.07);
        // second
        second = (second * Math.PI / 30);
        drawHand(second, radius * 0.9, radius * 0.02);
    }
    
    private void drawHand(double pos, double length, double width) {
        d2.beginPath();
        d2.setLineWidth(width);
        d2.setLineCap(LineCap.ROUND);
        d2.moveTo(0, 0);
        d2.rotateRadians(pos);
        d2.lineTo(0, -length);
        d2.stroke();
        d2.rotateRadians(-pos);
    }
    
    /******************* WebGL Rendering *****************/

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
    private Timer timerWebGL;
    
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

    @EventHandler(value = "click", target = "@btnRenderWebGL")
    private void onClick$renderWebGL() {
        if (timerWebGL.isRunning()) {
            timerWebGL.stop();
            btnRenderWebGL.addClass("flavor:btn-success");
            log("WebGL rendering sequence stopped.");
        } else {
            initWebGL();
            timerWebGL.start();
            btnRenderWebGL.addClass("flavor:btn-danger");
            log("WebGL rendering sequence started.");
        }
    }
    
    private void initWebGL() {
        resetZoom();
        gl = canvasWebGL.newRenderingContext();
        initShaders();
        initBuffers();
        gl.clearColor(0.0, 0.0, 0.0, 1.0);
    }

    @EventHandler(value = "timer", target = "@timerWebGL")
    private void onTimer$timerWebGL() {
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
