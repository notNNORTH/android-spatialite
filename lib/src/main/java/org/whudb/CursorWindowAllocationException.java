package org.whudb;

/**
 * This exception is thrown when a CursorWindow couldn't be allocated,
 * most probably due to memory not being available.
 *
 * @hide
 */
public class CursorWindowAllocationException extends RuntimeException {
    public CursorWindowAllocationException(String description) {
        super(description);
    }
}
