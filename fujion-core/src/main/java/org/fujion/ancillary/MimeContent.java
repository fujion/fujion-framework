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
package org.fujion.ancillary;

import java.util.Base64;

/**
 * Helper class for packaging embedded binary or textual data.
 */
public class MimeContent {

    private byte[] data;

    private String mimeType;

    private String url;

    /**
     * @param mimeType The MIME type.
     * @param data     The raw data.
     */
    public MimeContent(
            String mimeType,
            byte[] data) {
        this.mimeType = mimeType;
        this.data = data;
    }

    /**
     * @param mimeType The MIME type.
     * @param url   The data URL.
     */
    public MimeContent(
            String mimeType,
            String url) {
        this.mimeType = mimeType;
        this.url = url;
    }

    /**
     * Returns the URL or base 64 encoded data suitable for an image src attribute.
     *
     * @return The URL or base 64 encoded data suitable for an image src attribute.
     */
    public String getSrc() {
        return url != null ? url : (mimeType == null || data == null) ? null
                : "data:" + mimeType + ";base64," + getEncodedData();
    }

    /**
     * Returns the URL that references the data's source.
     *
     * @return The URL that references the data's source.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL that references the data's source.
     *
     * @param url The URL that references the data's source.
     */
    public void setUrl(String url) {
        this.url = url;

        if (url != null) {
            data = null;
        }
    }

    /**
     * Returns the raw data.
     *
     * @return The raw data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the raw data.
     *
     * @param data The raw data.
     */
    public void setData(byte[] data) {
        this.data = data;

        if (data != null) {
            url = null;
        }
    }

    /**
     * Returns the raw data in base 64 encoded form.
     *
     * @return The raw data in base 64 encoded form.
     */
    public String getEncodedData() {
        return data == null ? null : Base64.getEncoder().encodeToString(data);
    }

    /**
     * Sets the raw data from base 64 encoded data.
     *
     * @param encodedData The base 64 encoded data.
     */
    public void setEncodedData(String encodedData) {
        setData(encodedData == null ? null : Base64.getDecoder().decode(encodedData));
    }

    /**
     * Returns the MIME type.
     *
     * @return The MIME type.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the MIME type.
     *
     * @param mimeType The MIME type.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}
