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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;

/**
 * A tree cell component. This acts as a container for additional UI elements within a tree node.
 */
@Component(tag = "treecell", widgetModule = "fujion-treeview", widgetClass = "Treecell", parentTag = "treenode", childTag = @ChildTag("*"), description = "A simple container for a tree node.")
public class Treecell extends BaseUIComponent {

}