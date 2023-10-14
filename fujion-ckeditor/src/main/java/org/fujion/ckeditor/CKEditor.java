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
package org.fujion.ckeditor;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.client.ClientOptions;
import org.fujion.component.BaseInputComponent;

/**
 * Fujion wrapper for CKEditor component.
 */
@Component(
        tag = "ckeditor",
        widgetModule = "fujion-ckeditor",
        widgetClass = "CKEditor",
        parentTag = "*",
        description = "CKEditor component.")
public class CKEditor extends BaseInputComponent<String> {

    public enum CKEditorBundle {
        BASIC,
        FULL,
        STANDARD;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private boolean readonly;

    private boolean sizable = true;

    /**
     * Sets the CKEditor bundle to be loaded.  This will take effect only after page initialization.
     *
     * @param bundle The bundle to load.
     * @return A reference to the ClientOptions instance (not used).
     */
    public static Object setBundle(CKEditorBundle bundle) {
        return ClientOptions.setCustomSetting("org.fujion.ckeditor.bundle", bundle.toString());
    }

    /**
     * Sets the CKEditor bundle to be loaded.  This will take effect only after page initialization.
     *
     * @param bundle The bundle to load.
     * @return A reference to the ClientOptions instance (not used).
     */
    protected static Object setBundleFromString(String bundle) {
        return setBundle(CKEditorBundle.valueOf(bundle.toUpperCase()));
    }

    /**
     * Returns true if the input box is read-only.
     *
     * @return True if the input box is read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if the input box is read-only.")
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * Sets the read-only state of the input box.
     *
     * @param readonly If true, the contents of the input box may not be changed by the user.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if the input box is read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }

    /**
     * Returns true if the editor may be resized manually.
     *
     * @return True if the editor may be resized manually.
     */
    @PropertyGetter(value = "sizable", description = "If true, a sizer grip allows manual resizing of the editor.  If false, the editor size matches the container size.")
    public boolean isSizable() {
        return sizable;
    }

    /**
     * If set to true, the editor displays a sizing grip that can be used for manual resizing. If
     * false, the editor matches the size of its container.
     *
     * @param sizable True if the editor may be resized manually.
     */
    @PropertySetter(value = "sizable", defaultValue = "true", description = "If true, a sizer grip allows manual resizing of the editor.  If false, the editor size matches the container size.")
    public void setSizable(boolean sizable) {
        propertyChange("sizable", this.sizable, this.sizable = sizable, true);
    }
    
    @Override
    protected String _toValue(String value) {
        return value;
    }

    @Override
    protected String _toString(String value) {
        return value;
    }

}
