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
package org.fujion.canvas.webgl;

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.common.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Array buffer derived from an array of numeric values.
 */
public class ArrayBuffer extends ArrayBufferBase {
    
    private static final Map<Class<?>, PixelDataType> classToType = new HashMap<>();
    
    static {
        classToType.put(Short.class, PixelDataType.UNSIGNED_SHORT_5_6_5);
        classToType.put(short.class, PixelDataType.UNSIGNED_SHORT_5_6_5);
        classToType.put(Integer.class, PixelDataType.UNSIGNED_SHORT_5_6_5);
        classToType.put(int.class, PixelDataType.UNSIGNED_SHORT_5_6_5);
        classToType.put(Byte.class, PixelDataType.UNSIGNED_BYTE);
        classToType.put(byte.class, PixelDataType.UNSIGNED_BYTE);
        classToType.put(Float.class, PixelDataType.FLOAT);
        classToType.put(float.class, PixelDataType.FLOAT);
        classToType.put(Double.class, PixelDataType.FLOAT);
        classToType.put(double.class, PixelDataType.FLOAT);
    }

    private static PixelDataType typeFromData(Object data) {
        Assert.notNull(data, () -> "Data may not be null");
        Class<?> clazz = data.getClass().getComponentType();
        PixelDataType type = classToType.get(clazz);
        Assert.notNull(type, () -> "Unsupported numeric data type: " + clazz.getName());
        return type;
    }
    
    private ArrayBuffer(BaseCanvasComponent<?, ?> canvas, Object data) {
        super(canvas, typeFromData(data), 0, data);
    }

    public ArrayBuffer(BaseCanvasComponent<?, ?> canvas, Number[] data) {
        this(canvas, (Object) data);
    }

    public ArrayBuffer(BaseCanvasComponent<?, ?> canvas, byte[] data) {
        this(canvas, (Object) data);
    }

    public ArrayBuffer(BaseCanvasComponent<?, ?> canvas, int[] data) {
        this(canvas, (Object) data);
    }

    public ArrayBuffer(BaseCanvasComponent<?, ?> canvas, float[] data) {
        this(canvas, (Object) data);
    }

    public ArrayBuffer(BaseCanvasComponent<?, ?> canvas, double[] data) {
        this(canvas, (Object) data);
    }

}
