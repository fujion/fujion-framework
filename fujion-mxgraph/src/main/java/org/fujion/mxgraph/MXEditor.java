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
package org.fujion.mxgraph;

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertySetter;

/**
 * Fujion wrapper for mxEditor component.
 */
@Component(tag = "mxeditor", widgetModule = "fujion-mxgraph", widgetClass = "MXEditor", parentTag = "*", content = ContentHandling.AS_ATTRIBUTE, description = "Fujion wrapper for mxEditor component.")
public class MXEditor extends MXGraph {
    
    /**
     * Creates a toolbar item. Registered actions include:
     * <ul>
     * <li><b>save</b> - Saves the graph using post.</li>
     * <li><b>print</b> - Shows the graph in a new print preview window.</li>
     * <li><b>show</b> - Shows the graph in a new window.</li>
     * <li><b>exportImage</b> - Shows the graph as a bitmap image using
     * <code>getUrlImage</code>.</li>
     * <li><b>refresh</b> - Refreshes the graph's display.</li>
     * <li><b>cut</b> - Copies the current selection into the clipboard and removes it from the
     * graph.</li>
     * <li><b>copy</b> - Copies the current selection into the clipboard.</li>
     * <li><b>paste</b> - Pastes the clipboard into the graph.</li>
     * <li><b>delete</b> - Removes the current selection from the graph.</li>
     * <li><b>group</b> - Puts the current selection into a new group.</li>
     * <li><b>ungroup</b> - Removes the selected groups and selects the children.</li>
     * <li><b>undo</b> - Undoes the last change on the graph model.</li>
     * <li><b>redo</b> - Redoes the last change on the graph model.</li>
     * <li><b>zoom</b> - Sets the zoom via a dialog.</li>
     * <li><b>zoomIn</b> - Zooms into the graph.</li>
     * <li><b>zoomOut</b> - Zooms out of the graph.</li>
     * <li><b>actualSize</b> - Resets the scale and translation on the graph.</li>
     * <li><b>fit</b> - Changes the scale so that the graph fits into the window.</li>
     * <li><b>showProperties</b> - Shows the properties dialog.</li>
     * <li><b>selectAll</b> - Selects all cells.</li>
     * <li><b>selectNone</b> - Clears the selection.</li>
     * <li><b>selectVertices</b> - Selects all vertices.</li>
     * <li><b>selectEdges</b> Selects all edges.</li>
     * <li><b>edit</b> - Starts editing the current selection cell.</li>
     * <li><b>enterGroup</b> - Drills down into the current selection cell.</li>
     * <li><b>exitGroup</b> - Moves up in the drilling hierachy.</li>
     * <li><b>home</b> - Moves to the topmost parent in the drilling hierarchy.</li>
     * <li><b>selectPrevious</b> - Selects the previous cell.</li>
     * <li><b>selectNext</b> - Selects the next cell.</li>
     * <li><b>selectParent</b> - Selects the parent of the selection cell.</li>
     * <li><b>selectChild</b> - Selects the first child of the selection cell.</li>
     * <li><b>collapse</b> - Collapses the currently selected cells.</li>
     * <li><b>expand</b> - Expands the currently selected cells.</li>
     * <li><b>bold</b> - Toggle bold text style.</li>
     * <li><b>italic</b> - Toggle italic text style.</li>
     * <li><b>underline</b> - Toggle underline text style.</li>
     * <li><b>alignCellsLeft</b> - Aligns the selection cells at the left.</li>
     * <li><b>alignCellsCenter</b> - Aligns the selection cells in the center.</li>
     * <li><b>alignCellsRight</b> - Aligns the selection cells at the right.</li>
     * <li><b>alignCellsTop</b> - Aligns the selection cells at the top.</li>
     * <li><b>alignCellsMiddle</b> - Aligns the selection cells in the middle.</li>
     * <li><b>alignCellsBottom</b> - Aligns the selection cells at the bottom.</li>
     * <li><b>alignFontLeft</b> - Sets the horizontal text alignment to left.</li>
     * <li><b>alignFontCenter</b> - Sets the horizontal text alignment to center.</li>
     * <li><b>alignFontRight</b> - Sets the horizontal text alignment to right.</li>
     * <li><b>alignFontTop</b> - Sets the vertical text alignment to top.</li>
     * <li><b>alignFontMiddle</b> - Sets the vertical text alignment to middle.</li>
     * <li><b>alignFontBottom</b> - Sets the vertical text alignment to bottom.</li>
     * <li><b>toggleTasks</b> - Shows or hides the tasks window.</li>
     * <li><b>toggleHelp</b> - Shows or hides the help window.</li>
     * <li><b>toggleOutline</b> - Shows or hides the outline window.</li>
     * <li><b>toggleConsole</b> - Shows or hides the console window.</li>
     * </ul>
     *
     * @param label The toolbar label. If "-" and action is null, creates a separator.
     * @param action The associated action (see above).
     * @param image Optional image.
     */
    public void addToolbarItem(String label, String action, String image) {
        this.invoke("addToolbarItem", label, action, image);
    }
    
    /**
     * Add an editor action.
     * 
     * @param action The action name.
     * @param code JavaScript code to execute.
     * @return The JavaScript code.
     */
    public JavaScript addAction(String action, String code) {
        return addAction(action, new JavaScript(code));
    }

    /**
     * Add an editor action.
     * 
     * @param action The action name.
     * @param code JavaScript code to execute.
     * @return The JavaScript code.
     */
    public JavaScript addAction(String action, JavaScript code) {
        this.invoke("addAction", action, code);
        return code;
    }

    /**
     * Clears the undo history.
     */
    public void resetHistory() {
        this.invoke("resetHistory");
    }

    /**
     * Executes a registered action.
     *
     * @param action A registered action.
     */
    public void execute(String action) {
        execute(action, null);
    }
    
    /**
     * Executes a registered action.
     *
     * @param action A registered action.
     * @param cell Optional target of the action.
     */
    public void execute(String action, MXCell<?> cell) {
        this.invoke("execute", action, cell);
    }

    @PropertySetter(value = "status", description = "Message for display on status bar.")
    public void setStatus(String message) {
        this.sync("status", message);
    }
}
