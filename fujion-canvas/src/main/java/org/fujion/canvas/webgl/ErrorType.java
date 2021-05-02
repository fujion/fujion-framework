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

import org.fujion.ancillary.IEnumWithValue;

/**
 * Error types.
 */
public enum ErrorType implements IEnumWithValue {
    /**
     * If the WebGL context is lost, this error is returned on the first call to getError.
     * Afterwards and until the context has been restored, it returns NO_ERROR.
     */
    CONTEXT_LOST_WEBGL(37442),
    /**
     * An unacceptable value has been specified for an enumerated argument. The command is ignored
     * and the error flag is set.
     */
    INVALID_ENUM(1280),
    /**
     * The currently bound framebuffer is not framebuffer complete when trying to render to or to
     * read from it.
     */
    INVALID_FRAMEBUFFER_OPERATION(1286),
    /**
     * The specified command is not allowed for the current state. The command is ignored and the
     * error flag is set.
     */
    INVALID_OPERATION(1282),
    /**
     * A numeric argument is out of range. The command is ignored and the error flag is set.
     */
    INVALID_VALUE(1281),
    /**
     * No error has been recorded.
     */
    NO_ERROR(0),
    /**
     * Not enough memory is left to execute the command.
     */
    OUT_OF_MEMORY(1285);

    private final int value;

    ErrorType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
