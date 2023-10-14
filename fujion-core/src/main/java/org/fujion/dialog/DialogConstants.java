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

import org.fujion.common.LocalizedMessage;
import org.fujion.core.CoreUtil;

public class DialogConstants {

    static final String STYLE_FIXED_FONT = "|text-monospace";

    static final String LABEL_ID_OK = "@org.fujion.dialog.btn.ok.label";

    static final String LABEL_ID_CANCEL = "@org.fujion.dialog.btn.cancel.label";

    static final String LABEL_IDS_OK_CANCEL = LABEL_ID_OK + "|" + LABEL_ID_CANCEL;

    static final LocalizedMessage MSG_CONFIRM_TITLE = new LocalizedMessage("org.fujion.dialog.prompt.confirm.title");

    static final LocalizedMessage MSG_INFO_TITLE = new LocalizedMessage("org.fujion.dialog.prompt.info.title");

    static final LocalizedMessage MSG_WARNING_TITLE = new LocalizedMessage("org.fujion.dialog.prompt.warning.title");

    static final LocalizedMessage MSG_ERROR_TITLE = new LocalizedMessage("org.fujion.dialog.prompt.error.title");

    static final String STYLES_INFO = "fa-info-circle alert-info";

    static final String STYLES_WARNING = "fa-exclamation-triangle alert-warning";

    static final String STYLES_ERROR = "fa-exclamation-circle alert-danger";

    static final String STYLES_QUESTION = "fa-question-circle alert-success";

    static final String RESOURCE_PREFIX = CoreUtil.getResourceClassPath(DialogConstants.class);

    private DialogConstants() {
    }

}
