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
package org.fujion.taglib;

/**
 * Represents a function definition block from a tag library. Currently, ignores the method signature
 * except for extracting the method name. Instead, we rely on the EL parser to find the method
 * signature that matches the parameter list.
 */
public class TagLibraryFunction {

    private final String className;

    private final String methodSignature;

    private final String methodName;

    TagLibraryFunction(String className, String methodSignature) {
        this.className = className.trim();
        this.methodSignature = methodSignature;
        this.methodName = extractMethodName(methodSignature);
    }

    private String extractMethodName(String methodSignature) {
        int i = methodSignature.indexOf(" ");
        int j = methodSignature.indexOf("(", i);
        return methodSignature.substring(i + 1, j < 0 ? methodSignature.length() : j).trim();
    }

    /**
     * Returns the fully qualified name of the class.
     *
     * @return The fully qualified name of the class.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the method name.
     *
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return className + "." + methodName;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TagLibraryFunction fcn) {
            return fcn.className.equals(className) && fcn.methodSignature.equals(methodSignature);
        }

        return false;
    }

}
