package org.whudb;

import org.whudb.database.SQLiteDatabase;

/**
 * An interface to let apps define an action to take when database corruption is detected.
 */
public interface DatabaseErrorHandler {

    /**
     * The method invoked when database corruption is detected.
     * @param dbObj the {@link SQLiteDatabase} object representing the database on which corruption
     * is detected.
     */
    void onCorruption(SQLiteDatabase dbObj);
}
