package org.whudb.database;

/**
 * Describes a SQLite statement.
 *
 * @hide
 */
public final class SQLiteStatementInfo {
    /**
     * The number of parameters that the statement has.
     */
    public int numParameters;

    /**
     * The names of all columns in the result set of the statement.
     */
    public String[] columnNames;

    /**
     * True if the statement is read-only.
     */
    public boolean readOnly;
}
