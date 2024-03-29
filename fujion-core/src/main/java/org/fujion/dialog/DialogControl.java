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
package org.fujion.dialog;

import org.fujion.common.StrUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogControl<T> {

    public enum ChoiceFormat {
        BUTTONS, LIST
    }

    public interface IPromptCallback<T> {

        void onComplete(DialogResponse<T> response);

    }

    /**
     * Used to persist previous responses.
     */
    public interface IDialogResponseProvider {

        int get(String responseId);

        void set(
                String responseId,
                int index);

    }

    /**
     * The default provider for persisting last responses uses a map.
     */
    public static IDialogResponseProvider dialogResponseProvider = new IDialogResponseProvider() {

        private final Map<String, Integer> responses = new HashMap<>();

        @Override
        public int get(String responseId) {
            return responses.getOrDefault(responseId, -1);
        }

        @Override
        public void set(
                String responseId,
                int index) {
            if (index >= 0) {
                responses.put(responseId, index);
            } else {
                responses.remove(responseId);
            }
        }
    };

    private final String message;

    private final String title;

    private final String iconClass;

    private final String textClass;

    private final String panelClass;

    private final List<DialogResponse<T>> responses;

    private final String saveResponseId;

    private final IPromptCallback<T> callback;

    private final ChoiceFormat format = ChoiceFormat.BUTTONS;

    /**
     * Parameters for the dialog.
     *
     * @param message          Text message
     * @param title            Title of dialog
     * @param styles           Style classes for icon, message text, and panel (pipe-delimited)
     * @param responses        Button captions separated by vertical bars
     * @param excludeResponses Only applies if saveResponseId is specified. This is a list of
     *                         responses that will not be saved and is specified in the same format as the
     *                         buttonCaptions parameter.
     * @param defaultResponse  Caption of button to have initial focus
     * @param saveResponseId   Uniquely identifies this response for purposes of saving and retrieving
     *                         the last response. If not specified (null or empty), the response is not saved.
     *                         Otherwise, if a saved response exists, it is returned without displaying the
     *                         dialog. If a saved response does not exist, the user is prompted in the normal
     *                         manner with the addition of a checkbox on the dialog asking if the response is to
     *                         be saved. If this box is checked, the user's response is then saved as a user
     *                         preference.
     * @param callback         Callback to receive response.
     * @return DialogParameters instance.
     */
    public static DialogControl<String> create(
            String message,
            String title,
            String styles,
            String responses,
            String excludeResponses,
            String defaultResponse,
            String saveResponseId,
            IPromptCallback<String> callback) {
        return new DialogControl<>(message, title, styles, toList(responses), toList(excludeResponses), defaultResponse,
                saveResponseId, callback);
    }

    private static <T> List<T> toList(T[] array) {
        return array == null ? null : Arrays.asList(array);
    }

    private static List<String> toList(String values) {
        return values == null ? null : toList(values.split("\\|"));
    }

    /**
     * Control parameters for the dialog.
     *
     * @param message          Text message
     * @param title            Title of dialog
     * @param styles           Style classes for icon, message text, and panel (pipe-delimited)
     * @param responses        Responses for the dialog.
     * @param excludeResponses Only applies if saveResponseId is specified. This is a list of
     *                         responses that will not be saved.
     * @param defaultResponse  Default response for the dialog.
     * @param saveResponseId   Uniquely identifies this response for purposes of saving and retrieving
     *                         the last response. If not specified (null or empty), the response is not saved.
     *                         Otherwise, if a saved response exists, it is returned without displaying the
     *                         dialog. If a saved response does not exist, the user is prompted in the normal
     *                         manner with the addition of a checkbox on the dialog asking if the response is to
     *                         be saved. If this box is checked, the user's response is then saved as a user
     *                         preference.
     * @param callback         Callback to receive response.
     */
    public DialogControl(
            String message,
            String title,
            String styles,
            T[] responses,
            T[] excludeResponses,
            T defaultResponse,
            String saveResponseId,
            IPromptCallback<T> callback) {
        this(message, title, styles, toList(responses), toList(excludeResponses), defaultResponse, saveResponseId, callback);
    }

    /**
     * Control parameters for the dialog.
     *
     * @param message          Text message
     * @param title            Title of dialog
     * @param styles           Style classes for icon, message text, and panel (pipe-delimited)
     * @param responses        Responses for the dialog.
     * @param excludeResponses Only applies if saveResponseId is specified. This is a list of
     *                         responses that will not be saved.
     * @param defaultResponse  Default response for the dialog.
     * @param saveResponseId   Uniquely identifies this response for purposes of saving and retrieving
     *                         the last response. If not specified (null or empty), the response is not saved.
     *                         Otherwise, if a saved response exists, it is returned without displaying the
     *                         dialog. If a saved response does not exist, the user is prompted in the normal
     *                         manner with the addition of a checkbox on the dialog asking if the response is to
     *                         be saved. If this box is checked, the user's response is then saved as a user
     *                         preference.
     * @param callback         Callback to receive response.
     */
    public DialogControl(
            String message,
            String title,
            String styles,
            List<T> responses,
            List<T> excludeResponses,
            T defaultResponse,
            String saveResponseId,
            IPromptCallback<T> callback) {
        this(message, title, styles, DialogResponse.toResponseList(responses, excludeResponses, defaultResponse),
                saveResponseId, callback);
    }

    /**
     * Control parameters for the dialog.
     *
     * @param message        Text message
     * @param title          Title of dialog
     * @param styles         Style classes for icon, message text, and panel (pipe-delimited)
     * @param responses      Responses for the dialog.
     * @param saveResponseId Uniquely identifies this response for purposes of saving and retrieving
     *                       the last response. If not specified (null or empty), the response is not saved.
     *                       Otherwise, if a saved response exists, it is returned without displaying the
     *                       dialog. If a saved response does not exist, the user is prompted in the normal
     *                       manner with the addition of a checkbox on the dialog asking if the response is to
     *                       be saved. If this box is checked, the user's response is then saved as a user
     *                       preference.
     * @param callback       Callback to receive response.
     */
    public DialogControl(
            String message,
            String title,
            String styles,
            List<DialogResponse<T>> responses,
            String saveResponseId,
            IPromptCallback<T> callback) {
        this.message = StrUtil.formatMessage(message);
        this.title = StrUtil.formatMessage(title);
        this.saveResponseId = saveResponseId;
        this.callback = callback;
        this.responses = responses;
        String[] sclass = styles == null ? null : StrUtil.split(styles, "|", 3, false);
        iconClass = sclass == null ? null : sclass[0];
        textClass = sclass == null ? null : sclass[1];
        panelClass = sclass == null || sclass[2] == null ? "alert-primary" : sclass[2];
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getIconClass() {
        return iconClass;
    }

    public String getTextClass() {
        return textClass;
    }

    public String getPanelClass() {
        return panelClass;
    }

    public ChoiceFormat getFormat() {
        return format;
    }

    public List<DialogResponse<T>> getResponses() {
        return responses;
    }

    public String getSaveResponseId() {
        return saveResponseId;
    }

    public IPromptCallback<T> getCallback() {
        return callback;
    }

    /**
     * Returns the last saved response for this dialog.
     *
     * @return The response, or null if none found.
     */
    public DialogResponse<T> getLastResponse() {
        int i = saveResponseId == null ? -1 : dialogResponseProvider.get(saveResponseId);
        DialogResponse<T> response = i < 0 || i >= responses.size() ? null : responses.get(i);
        return response == null || response.isExcluded() ? null : response;
    }

    /**
     * Saves the last response under the named responseId.
     *
     * @param response The response to save. A null value will delete any saved response.
     */
    public void saveLastResponse(DialogResponse<T> response) {
        if (saveResponseId != null && (response == null || !response.isExcluded())) {
            int index = response == null ? -1 : responses.indexOf(response);
            dialogResponseProvider.set(saveResponseId, index);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void callback(DialogResponse response) {
        if (callback != null) {
            callback.onComplete(response);
        }
    }

}
