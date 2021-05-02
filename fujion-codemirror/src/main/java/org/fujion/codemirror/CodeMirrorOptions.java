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
package org.fujion.codemirror;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Base options common to all CodeMirror components.
 */
public class CodeMirrorOptions extends Options {
    
    public enum InputStyleEnum {
        CONTENT_EDITABLE, TEXT_AREA;

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", "");
        }
    }

    /**
     * When enabled, an extra CSS class will be added to each token, indicating the (inner) mode
     * that produced it, prefixed with "cm-m-". For example, tokens from the XML mode will get the
     * cm-m-xml class.
     * <p>
     * Default: false
     */
    @Option
    public Boolean addModeClass;

    /**
     * When set, only files whose type is in the array can be dropped into the editor. The strings
     * should be MIME types, and will be checked against the type of the File object as reported by
     * the browser.
     */
    @Option
    public String[] allowDropFileTypes;

    /**
     * Can be used to make CodeMirror focus itself on initialization.
     * <p>
     * Default: false
     */
    @Option
    public Boolean autofocus;

    /**
     * When fixedGutter is on, and there is a horizontal scrollbar, by default the gutter will be
     * visible to the left of this scrollbar. If this option is set to true, it will be covered by
     * an element with class CodeMirror-gutter-filler.
     * <p>
     * Default: false
     */
    @Option
    public Boolean coverGutterNextToScrollbar;
    
    /**
     * Half-period in milliseconds used for cursor blinking. By setting this to zero, blinking can
     * be disabled. A negative value hides the cursor entirely.
     * <p>
     * Default: 530
     */
    @Option
    public Integer cursorBlinkRate;
    
    /**
     * Determines the height of the cursor. Default is 1, meaning it spans the whole height of the
     * line. For some fonts (and by some tastes) a smaller height (for example 0.85), which causes
     * the cursor to not reach all the way to the bottom of the line, looks better.
     * <p>
     * Default: 1.0
     */
    @Option
    public Double cursorHeight;
    
    /**
     * How much extra space to always keep above and below the cursor when approaching the top or
     * bottom of the visible view in a scrollable document.
     * <p>
     * Default: 0
     */
    @Option
    public Integer cursorScrollMargin;
    
    /**
     * Controls whether drag-and-drop is enabled.
     * <p>
     * Default: true
     */
    @Option
    public Boolean dragDrop;
    
    /**
     * Configures whether the editor should re-indent the current line when a character is typed
     * that might change its proper indentation (only works if the mode supports indentation).
     * <p>
     * Default: true
     */
    @Option
    public Boolean electricChars;

    /**
     * Can be used to specify extra key bindings for the editor, alongside the ones defined by
     * keyMap.
     */
    @Option
    private final Map<String, Object> extraKeys = new HashMap<>();

    /**
     * At which number to start counting lines.
     * <p>
     * Default: 1
     */
    @Option
    public Integer firstLineNumber;
    
    /**
     * Determines whether the gutter scrolls along with the content horizontally (false) or whether
     * it stays fixed (true) during horizontal scrolling.
     * <p>
     * Default: true
     */
    @Option
    public Boolean fixedGutter;
    
    /**
     * By default, CodeMirror will combine adjacent tokens into a single span if they have the same
     * class. This will result in a simpler DOM tree, and thus perform better. With some kinds of
     * styling (such as rounded corners), this will change the way the document looks. You can set
     * this option to false to disable this behavior.
     * <p>
     * Default: true
     */
    @Option
    public Boolean flattenSpans;
    
    /**
     * The period of inactivity (in milliseconds) that will cause a new history event to be started
     * when typing or deleting.
     * <p>
     * Default: 1250
     */
    @Option
    public Integer historyEventDelay;

    /**
     * How many spaces a block (whatever that means in the edited language) should be indented.
     * <p>
     * Default: 2
     */
    @Option
    public Integer indentUnit;

    /**
     * Whether, when indenting, the first N*tabSize spaces should be replaced by N tabs.
     * <p>
     * Default: false
     */
    @Option
    public Boolean indentWithTabs;
    
    /**
     * Selects the way CodeMirror handles input and focus. The core library defines the "textarea"
     * and "contenteditable" input models. On mobile browsers, the default is "contenteditable". On
     * desktop browsers, the default is "textarea". Support for IME and screen readers is better in
     * the "contenteditable" model. The intention is to make it the default on modern desktop
     * browsers in the future.
     */
    @Option
    public InputStyleEnum inputStyle;

    /**
     * A function used to format line numbers. The function is passed the line number, and should
     * return a string that will be shown in the gutter.
     */
    @Option
    public JavaScript lineNumberFormatter;
    
    /**
     * Whether to show line numbers to the left of the editor.
     */
    @Option
    public Boolean lineNumbers;
    
    /**
     * Explicitly set the line separator for the editor. By default (value null), the document
     * will be split on CRLFs as well as lone CRs and LFs, and a single LF will be used as line
     * separator in all output (such as getValue). When a specific string is given, lines will
     * only be split on that string, and output will, by default, use that same separator.
     */
    @Option
    public String lineSeparator;

    /**
     * When enabled, doing copy or cut when there is no selection will copy or cut the whole lines
     * that have cursors on them.
     * <p>
     * Default: true
     */
    @Option
    public Boolean lineWiseCopyCut;

    /**
     * Whether CodeMirror should scroll (false) or wrap (true) for long lines.
     * <p>
     * Default: false
     */
    @Option
    public Boolean lineWrapping;

    /**
     * When highlighting long lines, in order to stay responsive, the editor will give up and simply
     * style the rest of the line as plain text when it reaches a certain position. You can set this
     * to MaxInt to turn off this behavior.
     * <p>
     * Default: 10000
     */
    @Option
    public Integer maxHighlightLength;

    /**
     * The mode to use. When not given, this will default to the first mode that was loaded. It is a
     * string, which either simply names the mode or is a MIME type associated with the mode.
     */
    @Option
    protected String mode;
    
    /**
     * When pasting something from an external source (not from the editor itself), if the number of
     * lines matches the number of selection, CodeMirror will by default insert one line per
     * selection. You can set this to false to disable that behavior.
     * <p>
     * Default: true
     */
    @Option
    public Boolean pasteLinesPerSelection;
    
    /**
     * Can be used to make content appear in the editor when it is empty and not focused. It gives
     * the editor a CodeMirror-empty CSS class whenever it doesn't contain any text.
     */
    @Option
    public String placeholder;
    
    /**
     * Indicates how quickly (in milliseconds) CodeMirror should poll its input textarea for changes
     * (when focused). Most input is captured by events, but some things, like IME input on some
     * browsers, don't generate events that allow CodeMirror to properly detect it. Thus, it polls.
     * <p>
     * Default: 100.
     */
    @Option
    public Integer pollInterval;
    
    /**
     * This disables editing of the editor content by the user. If the special value "nocursor" is
     * given (instead of simply true), focusing of the editor is also disallowed.
     * <p>
     * Default: false
     */
    @Option
    public Boolean readonly;
    
    /**
     * Controls whether, when the context menu is opened with a click outside of the current
     * selection, the cursor is moved to the point of the click.
     * <p>
     * Default: true
     */
    @Option
    public Boolean resetSelectionOnContextMenu;
    
    /**
     * Determines whether horizontal cursor movement through right-to-left (Arabic, Hebrew) text is
     * visual (pressing the left arrow moves the cursor left) or logical (pressing the left arrow
     * moves to the next lower index in the string, which is visually right in right-to-left text).
     * Default: false on Windows, and true on other platforms.
     */
    @Option
    public Boolean rtlMoveVisually;

    /**
     * Chooses a scrollbar implementation. The default is "native", showing native scrollbars. The
     * core library also provides the "null" style, which completely hides the scrollbars. Addons
     * can implement additional scrollbar models.
     * <p>
     * Default: "native"
     */
    @Option
    public String scrollbarStyle;

    /**
     * Whether the cursor should be drawn when a selection is active.
     * <p>
     * Default: false
     */
    @Option
    public Boolean showCursorWhenSelecting;

    /**
     * Whether to use the context-sensitive indentation that the mode provides (or just indent the
     * same as the line before).
     * <p>
     * Default: true
     */
    @Option
    public Boolean smartIndent;
    
    /**
     * A function that, given a special character identified by the specialChars option, produces a
     * DOM node that is used to represent the character.
     * <p>
     * Default: A red dot (•) is shown, with a title tooltip to indicate the character code.
     */
    @Option
    public JavaScript specialCharPlaceholder;
    
    /**
     * A regular expression used to determine which characters should be replaced by a special
     * placeholder. Mostly useful for non-printing special characters.
     * <p>
     * Default: [\u0000-\u001f\u007f-\u009f\u00ad\u061c\u200b-\u200f\u2028\u2029\ufeff]
     */
    @Option
    public Pattern specialChars;
    
    /**
     * The width of a tab character.
     * <p>
     * Default: 4
     */
    @Option
    public Integer tabSize;

    /**
     * The maximum number of undo levels that the editor stores. Note that this includes selection
     * change events.
     * <p>
     * Default: 200
     */
    @Option
    public Integer undoDepth;

    /**
     * Specifies the amount of lines that are rendered above and below the part of the document
     * that's currently scrolled into view. This affects the amount of updates needed when
     * scrolling, and the amount of work that such an update does. You should usually leave it at
     * its default, 10. Can be set to MaxInt to make sure the whole document is always rendered, and
     * thus the browser's text search works on it. This will have bad effects on performance of big
     * documents.
     * <p>
     * Default: 10
     */
    @Option
    public Integer viewportMargin;

    /**
     * Highlighting is done by a pseudo background-thread that will work for workTime milliseconds,
     * and then use timeout to sleep for workDelay milliseconds. You can change these options to
     * make the highlighting more or less aggressive.
     * <p>
     * Default: 300
     */
    @Option
    public Integer workDelay;

    /**
     * Highlighting is done by a pseudo background-thread that will work for workTime milliseconds,
     * and then use timeout to sleep for workDelay milliseconds. You can change these options to
     * make the highlighting more or less aggressive.
     * <p>
     * Default: 200
     */
    @Option
    public Integer workTime;

    /**
     * Create options instance with specified mode.
     *
     * @param mode Desired mode.
     */
    public CodeMirrorOptions(String mode) {
        this.mode = mode;
    }
    
    /**
     * Binds a key combination to a registered command.
     *
     * @param key Key combination mnemonic.
     * @param command Name of registered command.
     * @return The previous key binding (command or JavaScript object), or null if none.
     */
    public Object addKeyBinding(String key, String command) {
        return doAddKeyBinding(key, command);
    }

    /**
     * Binds a key combination to a JavaScript function.
     *
     * @param key Key combination mnemonic.
     * @param javascript JavaScript code to be invoked.
     * @return The previous key binding (command or JavaScript object), or null if none.
     */
    public Object addKeyBinding(String key, JavaScript javascript) {
        return doAddKeyBinding(key, javascript);
    }

    private Object doAddKeyBinding(String key, Object binding) {
        return binding == null ? extraKeys.remove(key) : extraKeys.put(key, binding);
    }
    
    /**
     * Returns the current key binding.
     *
     * @param key Key combination mnemonic.
     * @return The existing key binding (command or JavaScript object), or null if none.
     */
    public Object getKeyBinding(String key) {
        return extraKeys.get(key);
    }

    /**
     * Removes the current key binding, if any.
     *
     * @param key Key combination mnemonic.
     * @return The previous key binding (command or JavaScript object), or null if none.
     */
    public Object removeKeyBinding(String key) {
        return extraKeys.remove(key);
    }
}
