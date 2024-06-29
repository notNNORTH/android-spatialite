package org.whudb.database;

import android.database.Cursor;

/**
 * A driver for SQLiteCursors that is used to create them and gets notified
 * by the cursors it creates on significant events in their lifetimes.
 */
public interface SQLiteCursorDriver {
    /**
     * Executes the query returning a Cursor over the result set.
     * 
     * @param factory The CursorFactory to use when creating the Cursors, or
     *         null if standard SQLiteCursors should be returned.
     * @return a Cursor over the result set
     */
    Cursor query(SQLiteDatabase.CursorFactory factory, Object[] bindArgs);

    /**
     * Called by a SQLiteCursor when it is released.
     */
    void cursorDeactivated();

    /**
     * Called by a SQLiteCursor when it is requeried.
     */
    void cursorRequeried(Cursor cursor);

    /**
     * Called by a SQLiteCursor when it it closed to destroy this object as well.
     */
    void cursorClosed();

    /**
     * Set new bind arguments. These will take effect in cursorRequeried().
     * @param bindArgs the new arguments
     */
    void setBindArguments(String[] bindArgs);
}
