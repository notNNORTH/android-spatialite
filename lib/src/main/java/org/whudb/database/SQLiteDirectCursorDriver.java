package org.whudb.database;

import android.database.Cursor;
import androidx.core.os.CancellationSignal;

/**
 * A cursor driver that uses the given query directly.
 * 
 * @hide
 */
public final class SQLiteDirectCursorDriver implements SQLiteCursorDriver {
    private final SQLiteDatabase mDatabase;
    private final String mEditTable; 
    private final String mSql;
    private final CancellationSignal mCancellationSignal;
    private SQLiteQuery mQuery;

    public SQLiteDirectCursorDriver(SQLiteDatabase db, String sql, String editTable,
            CancellationSignal cancellationSignal) {
        mDatabase = db;
        mEditTable = editTable;
        mSql = sql;
        mCancellationSignal = cancellationSignal;
    }

    public Cursor query(SQLiteDatabase.CursorFactory factory, Object[] selectionArgs) {
        SQLiteQuery query = new SQLiteQuery(mDatabase, mSql, selectionArgs, mCancellationSignal);
        final Cursor cursor;
        try {
            if (factory == null) {
                cursor = new SQLiteCursor(this, mEditTable, query);
            } else {
                cursor = factory.newCursor(mDatabase, this, mEditTable, query);
            }
        } catch (RuntimeException ex) {
            query.close();
            throw ex;
        }

        mQuery = query;
        return cursor;
    }

    @Override
    public void cursorClosed() {
        // Do nothing
    }

    @Override
    public void setBindArguments(String[] bindArgs) {
        mQuery.bindAllArgsAsStrings(bindArgs);
    }

    @Override
    public void cursorDeactivated() {
        // Do nothing
    }

    @Override
    public void cursorRequeried(Cursor cursor) {
        // Do nothing
    }

    @Override
    public String toString() {
        return "SQLiteDirectCursorDriver: " + mSql;
    }
}
