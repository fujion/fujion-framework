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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.springframework.util.Assert;

/**
 * A component for uploading files to the server.
 */
@Component(tag = "upload", widgetModule = "fujion-upload", widgetClass = "Upload", parentTag = "*", description = "A component for uploading files to the server.")
public class Upload extends BaseUIComponent {

    private boolean multiple;

    private boolean progress;

    private String accept;

    private int maxsize = 1024 * 1024 * 100;

    public Upload() {
        super();
    }

    /**
     * Returns true if multiple file uploads are allowed.
     *
     * @return True if multiple file uploads are allowed.
     */
    @PropertyGetter(value = "multiple", description = "True if multiple file uploads are allowed.")
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * Set to true if multiple file uploads are allowed.
     *
     * @param multiple True if multiple file uploads are to be allowed.
     */
    @PropertySetter(value = "multiple", defaultValue = "false", description = "True if multiple file uploads are allowed.")
    public void setMultiple(boolean multiple) {
        propertyChange("multiple", this.multiple, this.multiple = multiple, true);
    }

    /**
     * Returns the specifier reflecting the type of files the server expects.
     *
     * @return Specifier for acceptable file types.
     */
    @PropertyGetter(value = "accept", description = "Specifier for acceptable file types.")
    public String getAccept() {
        return accept;
    }

    /**
     * Sets the specifier reflecting the type of files the server expects.
     *
     * @param accept Specifier for acceptable file types. Valid values include:
     *            <ul>
     *            <li>A file extension prefixed with a period.</li>
     *            <li>audio/* = Any audio file.</li>
     *            <li>video/* = Any video file.</li>
     *            <li>image/* = Any image file.</li>
     *            <li>Any valid IANA media type.</li>
     *            </ul>
     */
    @PropertySetter(value = "accept", description = "Specifier for acceptable file types.")
    public void setAccept(String accept) {
        propertyChange("accept", this.accept, this.accept = nullify(accept), true);
    }

    /**
     * Returns the maximum allowable file size, in bytes. Any attempt to upload a file larger than
     * this size will produce an exception.
     *
     * @return The maximum allowable file size, in bytes.
     */
    @PropertyGetter(value = "maxsize", description = "The maximum allowable file size, in bytes.")
    public int getMaxsize() {
        return maxsize;
    }

    /**
     * Sets the maximum allowable file size, in bytes.
     *
     * @param maxsize The maximum allowable file size, in bytes. Any attempt to upload a file larger
     *            than this size will produce an exception.
     */
    @PropertySetter(value = "maxsize", defaultValue = "104857600", description = "The maximum allowable file size, in bytes.")
    public void setMaxsize(int maxsize) {
        Assert.isTrue(maxsize >= 0, "maxsize must be >= 0");
        propertyChange("_maxsize", this.maxsize, this.maxsize = maxsize, true);
    }

    /**
     * If true, the uploader will fire UploadEvent events to report progress.
     *
     * @see org.fujion.event.UploadEvent
     * @return Returns true if UploadEvent events will be fired.
     */
    @PropertyGetter(value = "progress", description = "If true, the uploader will fire UploadEvent events to report progress.")
    public boolean getProgress() {
        return progress;
    }

    /**
     * Set to true to receive upload progress events.
     *
     * @see org.fujion.event.UploadEvent
     * @param progress True if UploadEvent events are to be fired.
     */
    @PropertySetter(value = "progress", defaultValue = "false", description = "If true, the uploader will fire UploadEvent events to report progress.")
    public void setProgress(boolean progress) {
        propertyChange("_progress", this.progress, this.progress = progress, true);
    }

    /**
     * Abort all file uploads in progress.
     */
    public void abortAll() {
        invokeIfAttached("abortAll");
    }

    /**
     * Abort an upload for a specific file.
     *
     * @param filename File whose upload is to be aborted.
     */
    public void abort(String filename) {
        invokeIfAttached("abort", filename);
    }

    /**
     * Bind uploader to another component. A click event on that component will then trigger an
     * upload.
     *
     * @param comp Component to bind.
     */
    public void bind(BaseUIComponent comp) {
        invoke("bind", comp);
    }

    /**
     * Unbind a previously bound component.
     *
     * @param comp Component to unbind.
     */
    public void unbind(BaseUIComponent comp) {
        invoke("unbind", comp);
    }

    /**
     * Resets to baseline state.
     */
    public void clear() {
        invoke("clear");
    }
}
