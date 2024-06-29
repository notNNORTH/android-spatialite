
package org.whudb.database;

/**
 * Describes a SQLite extension entry point.
 */
public final class SQLiteCustomExtension {

    public final String path;
    public final String entryPoint;

    /**
     * Creates a SQLite extension description.
     *
     * @param path       path to the loadable extension shared library
     *                   e.g. "/data/data/(package)/lib/libSqliteICU.so"
     * @param entryPoint extension entry point (optional)
     *                   e.g. "sqlite3_icu_init"
     */
    public SQLiteCustomExtension(String path, String entryPoint) {
        if (path == null) {
            throw new IllegalArgumentException("null path");
        }
        this.path = path;
        this.entryPoint = entryPoint;
    }
}
