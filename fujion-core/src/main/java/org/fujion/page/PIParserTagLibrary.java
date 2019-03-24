/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.taglib.TagLibrary;
import org.fujion.taglib.TagLibraryRegistry;
import org.springframework.util.Assert;
import org.w3c.dom.ProcessingInstruction;

/**
 * Parser for tag library processing instructions.
 */
public class PIParserTagLibrary extends PIParserBase {
    
    public PIParserTagLibrary() {
        super("taglib");
    }
    
    @Override
    public void parse(ProcessingInstruction pi, PageElement element) {
        String uri = getAttribute(pi, "uri", true);
        String prefix = getAttribute(pi, "prefix", true);
        TagLibrary tagLibrary = TagLibraryRegistry.getInstance().get(uri);
        Assert.notNull(tagLibrary, () -> "Tag library not found: " + uri);
        element.addTagLibrary(prefix, tagLibrary);
    }
    
}
