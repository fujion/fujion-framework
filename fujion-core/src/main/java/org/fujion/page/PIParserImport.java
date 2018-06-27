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
package org.fujion.page;

import org.fujion.common.MiscUtil;
import org.w3c.dom.ProcessingInstruction;

/**
 * Parser for processing instructions that directly import a FSP.
 */
public class PIParserImport extends PIParserBase {
    
    public PIParserImport() {
        super("import");
    }
    
    @Override
    public void parse(ProcessingInstruction pi, PageElement element) {
        try {
            PageSource source = new PageSource(getAttribute(pi, "src", true));
            PageParser.getInstance().parse(source, element);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
}
