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
package org.fujion.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

import java.io.InputStream;

/**
 * An upload event.
 */
@EventType(UploadEvent.TYPE)
public class UploadEvent extends MouseEvent {
    
    /**
     * Upload stage reported by this event.
     */
    public enum UploadState {
        /**
         * The state is not known.
         */
        UNKNOWN,
        /**
         * The file has exceeded the maximum allowable size.
         */
        MAXSIZE,
        /**
         * The upload request has been aborted.
         */
        ABORTED,
        /**
         * No data has been loaded yet.
         */
        EMPTY,
        /**
         * Data is currently being loaded.
         */
        LOADING,
        /**
         * The upload has completed.
         */
        DONE
    }
    
    /**
     * The event type.
     */
    public static final String TYPE = "upload";
    
    @EventParameter
    private String file;
    
    @EventParameter
    private InputStream blob;
    
    @EventParameter
    private int total;
    
    @EventParameter
    private int loaded;
    
    @EventParameter
    private int state;
    
    public UploadEvent() {
        super(TYPE);
    }
    
    public UploadEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }
    
    /**
     * Returns the name of the uploaded file.
     *
     * @return Name of the uploaded file.
     */
    public String getFile() {
        return file;
    }
    
    /**
     * Returns the file contents as an input stream.
     *
     * @return The file contents as an input stream.
     */
    public InputStream getBlob() {
        return blob;
    }
    
    /**
     * Returns the total number of bytes to be transferred.
     *
     * @return The total number of bytes to be transferred.
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Returns the number of bytes transferred so far.
     *
     * @return The number of bytes transferred so far.
     */
    public int getLoaded() {
        return loaded;
    }
    
    /**
     * Returns the upload {@link UploadState state}.
     *
     * @return The upload {@link UploadState state}.
     */
    public UploadState getState() {
        try {
            return UploadState.values()[state + 3];
        } catch (Exception e) {
            return UploadState.UNKNOWN;
        }
    }
}
