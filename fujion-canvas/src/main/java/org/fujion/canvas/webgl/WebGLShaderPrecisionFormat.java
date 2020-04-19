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
package org.fujion.canvas.webgl;

import java.util.Map;

import org.fujion.ancillary.IResponseCallback;
import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.CanvasResource;

public class WebGLShaderPrecisionFormat extends CanvasResource {

    private double rangeMin;

    private double rangeMax;

    private int precision;

    private IResponseCallback<WebGLShaderPrecisionFormat> callback;

    protected WebGLShaderPrecisionFormat(BaseCanvasComponent<?, ?> canvas, ShaderType type, PrecisionType precision,
        IResponseCallback<WebGLShaderPrecisionFormat> callback) {
        super(canvas, true, "initResource", "getShaderPrecisionFormat", type, precision);
    }

    @Override
    protected void callback(Object response) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) response;
        rangeMin = (Double) map.get("rangeMin");
        rangeMax = (Double) map.get("rangeMax");
        precision = (Integer) map.get("precision");
        IResponseCallback.invoke(callback, this);
    }

    /**
     * Returns the base 2 log of the absolute value of the minimum value that can be represented.
     *
     * @return The base 2 log of the absolute value of the minimum value that can be represented.
     */
    public double getRangeMin() {
        return rangeMin;
    }

    /**
     * Returns the base 2 log of the absolute value of the maximum value that can be represented.
     *
     * @return The base 2 log of the absolute value of the maximum value that can be represented.
     */
    public double getRangeMax() {
        return rangeMax;
    }

    /**
     * Returns the number of bits of precision that can be represented. For integer formats this
     * value is always 0.
     *
     * @return The number of bits of precision that can be represented. For integer formats this
     *         value is always 0.
     */
    public int getPrecision() {
        return precision;
    }

}
