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

import org.springframework.util.Base64Utils;

/**
 * Helper class for packaging embedded binary or textual data.
 */
public class MimeContent {
    
    private byte[] data;
    
    private String mimeType;

    private String source;

    public MimeContent(String mimeType, byte[] data) {
        this.mimeType = mimeType;
        this.data = data;
    }

    public MimeContent(String mimeType, String source) {
        this.mimeType = mimeType;
        this.source = source;
    }

    public String getSrc() {
        return source != null ? source : (mimeType == null || data == null) ? null
                : "data:" + mimeType + ";base64," + Base64Utils.encodeToString(data);
    }

    public void setSource(String source) {
        this.source = source;

        if (source != null) {
            data = null;
        }
    }

    public byte[] getData() {
        return data;
    }
    
    public void setData(byte[] data) {
        this.data = data;

        if (data != null) {
            source = null;
        }
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
}
