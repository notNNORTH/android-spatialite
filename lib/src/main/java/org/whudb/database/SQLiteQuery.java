package org.whudb.database;

import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.whudb.CursorWindow;

import androidx.core.os.CancellationSignal;
import androidx.core.os.OperationCanceledException;

/**
 * Represents a query that reads the resulting rows into a {@link SQLiteQuery}.
 * This class is used by {@link SQLiteCursor} and isn't useful itself.
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class SQLiteQuery extends SQLiteProgram {
    private static final String TAG = "SQLiteQuery";

    private final CancellationSignal mCancellationSignal;

    SQLiteQuery(SQLiteDatabase db, String query, Object[] bindArgs,
                CancellationSignal cancellationSignal) {
        super(db, query, bindArgs, cancellationSignal);
        mCancellationSignal = cancellationSignal;
    }

    /**
     * Reads rows into a buffer.
     *
     * @param window The window to fill into
     * @param startPos The start position for filling the window.
     * @param requiredPos The position of a row that MUST be in the window.
     * If it won't fit, then the query should discard part of what it filled.
     * @param countAllRows True to count all rows that the query would
     * return regardless of whether they fit in the window.
     * @return Number of rows that were enumerated.  Might not be all rows
     * unless countAllRows is true.
     *
     * @throws SQLiteException if an error occurs.
     * @throws OperationCanceledException if the operation was canceled.
     */
    int fillWindow(CursorWindow window, int startPos, int requiredPos, boolean countAllRows) {
        acquireReference();
        try {
            window.acquireReference();
            try {
                return getSession().executeForCursorWindow(getSql(), getBindArgs(),
                        window, startPos, requiredPos, countAllRows, getConnectionFlags(),
                        mCancellationSignal);
            } catch (SQLiteDatabaseCorruptException ex) {
                onCorruption();
                throw ex;
            } catch (SQLiteException ex) {
                Log.e(TAG, "exception: " + ex.getMessage() + "; query: " + getSql());
                throw ex;
            } finally {
                window.releaseReference();
            }
        } finally {
            releaseReference();
        }
    }

    @Override
    public String toString() {
        return "SQLiteQuery: " + getSql();
    }
}