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
package org.fujion.codemirror;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * Fujion wrapper for CodeMirror JavaScript editor.
 */
@Component(
        tag = "codemirror",
        widgetModule = "fujion-codemirror",
        widgetClass = "CodeMirrorBase",
        parentTag = "*",
        description = "Fujion wrapper for CodeMirror JavaScript editor.")
public class CodeMirror extends CodeMirrorBase<CodeMirrorOptions> {
    
    public CodeMirror() {
        super(new CodeMirrorOptions(null));
    }
    
    /**
     * Returns the CodeMirror mode parameter.
     *
     * @return The CodeMirror mode parameter.
     */
    @Override
    @PropertyGetter(value = "mode", description = "The CodeMirror mode parameter.")
    public String getMode() {
        return super.getMode();
    }
    
    /**
     * Sets the CodeMirror mode parameter.
     *
     * @param mode The CodeMirror mode parameter.
     */
    @Override
    @PropertySetter(value = "mode", description = "The CodeMirror mode parameter.")
    public void setMode(String mode) {
        super.setMode(mode);
    }
    
}
