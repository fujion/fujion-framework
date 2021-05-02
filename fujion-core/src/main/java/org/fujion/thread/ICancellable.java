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
package org.fujion.thread;

/**
 * Implemented by classes that perform operations that can be cancelled.
 */
public interface ICancellable {

    /**
     * @return True if the operation has been cancelled.
     */
    boolean isCancelled();

    /**
     * Cancels a background operation.
     *
     * @param mayInterrupt If true, interrupt execution immediately if possible.
     * @return True if the operation was successfully cancelled.
     */
    boolean cancel(boolean mayInterrupt);

    /**
     * Cancels a background operation, interrupting execution immediately if possible.
     *
     * @return True if the operation was successfully cancelled.
     */
    default boolean cancel() {
        return cancel(true);
    }
}
