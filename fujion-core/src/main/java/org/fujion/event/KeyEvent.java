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
package org.fujion.event;

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * Base class for key-related events.
 */
public abstract class KeyEvent extends Event {
    
    @EventParameter
    private int keyCode;
    
    @EventParameter
    private char charCode;
    
    @EventParameter
    private boolean shiftKey;
    
    @EventParameter
    private boolean ctrlKey;
    
    @EventParameter
    private boolean altKey;
    
    @EventParameter
    private boolean metaKey;
    
    protected KeyEvent(String type) {
        super(type);
    }
    
    protected KeyEvent(String type, BaseComponent target, Object data) {
        super(type, target, data);
    }
    
    /**
     * Returns true if the Alt key was depressed.
     *
     * @return True if the Alt key was depressed.
     */
    public boolean isAltKey() {
        return altKey;
    }
    
    /**
     * Returns true if the Control key was depressed.
     *
     * @return True if the Control key was depressed.
     */
    public boolean isCtrlKey() {
        return ctrlKey;
    }
    
    /**
     * Returns true if the Shift key was depressed.
     *
     * @return True if the Shift key was depressed.
     */
    public boolean isShiftKey() {
        return shiftKey;
    }
    
    /**
     * Returns true if the Meta key was depressed.
     *
     * @return True if the Meta key was depressed.
     */
    public boolean isMetaKey() {
        return metaKey;
    }
    
    /**
     * Returns the key code of the depressed key(s).
     *
     * @return The key code of the depressed key(s).
     */
    public KeyCode getKeyCode() {
        return KeyCode.fromCode(keyCode);
    }
    
    /**
     * Returns the character code of the depressed key(s).
     *
     * @return The character code of the depressed key(s).
     */
    public char getCharCode() {
        return charCode;
    }
    
    /**
     * Returns the key capture representation of the typed key.
     *
     * @return The key capture representation of the typed key.
     */
    public String getKeycapture() {
        StringBuilder sb = new StringBuilder();
        
        if (isCtrlKey()) {
            sb.append('^');
        }
        
        if (isAltKey()) {
            sb.append('@');
        }
        
        if (isMetaKey()) {
            sb.append('~');
        }
        
        if (isShiftKey()) {
            sb.append('$');
        }
        
        sb.append("#").append(keyCode);
        return sb.toString();
    }
    
}
