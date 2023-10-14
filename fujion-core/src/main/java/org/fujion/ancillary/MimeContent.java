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
package org.fujion.ancillary;

import java.util.Base64;

/**
 * Helper class for packaging embedded binary or textual data.
 */
public class MimeContent {

    private static class MimeData {

        private byte[] rawData;

        private String encodedData;

        byte[] getRawData() {
            if (rawData == null && encodedData != null) {
                rawData = Base64.getDecoder().decode(encodedData);
            }

            return rawData;
        }

        void setRawData(byte[] rawData) {
            this.rawData = rawData;
            this.encodedData = null;
        }

        String getEncodedData() {
            if (encodedData == null && rawData != null) {
                encodedData = Base64.getEncoder().encodeToString(rawData);
            }

            return encodedData;
        }

        void setEncodedData(String encodedData) {
            this.encodedData = encodedData;
            this.rawData = null;
        }

        void clear() {
            this.encodedData = null;
            this.rawData = null;
        }

        boolean isEmpty() {
            return encodedData == null && rawData == null;
        }

    }

    private final MimeData data = new MimeData();

    private String mimeType;

    private String url;

    /**
     * @param mimeType The MIME type.
     * @param rawData  The raw data.
     */
    public MimeContent(
            String mimeType,
            byte[] rawData) {
        this.mimeType = mimeType;
        this.data.setRawData(rawData);
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
        return url != null ? url : (mimeType == null || data.isEmpty()) ? null
                : "data:" + mimeType + ";base64," + data.getEncodedData();
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
            data.clear();
        }
    }

    /**
     * Returns the raw data.
     *
     * @return The raw data.
     */
    public byte[] getData() {
        return data.getRawData();
    }

    /**
     * Sets the raw data.
     *
     * @param rawData The raw data.
     */
    public void setData(byte[] rawData) {
        data.setRawData(rawData);

        if (rawData != null) {
            url = null;
        }
    }

    /**
     * Returns the raw data in base 64 encoded form.
     *
     * @return The raw data in base 64 encoded form.
     */
    public String getEncodedData() {
        return data.getEncodedData();
    }

    /**
     * Sets the raw data from base 64 encoded data.
     *
     * @param encodedData The base 64 encoded data.
     */
    public void setEncodedData(String encodedData) {
        data.setEncodedData(encodedData);

        if (encodedData != null) {
            url = null;
        }
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
