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
