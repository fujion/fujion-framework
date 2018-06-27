/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
    
    public MimeContent(String mimeType, byte[] data) {
        this.mimeType = mimeType;
        this.data = data;
    }
    
    public String getSrc() {
        return (mimeType == null || data == null) ? null
                : "data:" + mimeType + ";base64," + Base64Utils.encodeToString(data);
    }
    
    public byte[] getData() {
        return data;
    }
    
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
}
