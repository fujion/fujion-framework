package org.fujion.thread;

/**
 * Implemented by classes that perform operations that can be cancelled.
 */
public interface ICancellable {

    /**
     * True if the operation has been cancelled.
     */
    boolean isCancelled();

    /**
     * Cancels a background operation.
     */
    boolean cancel(boolean mayInterrupt);

    default boolean cancel() {
        return cancel(true);
    }
}
