package org.whudb.database;

/**
 * Describes a custom SQL function.
 *
 * @hide
 */
public final class SQLiteCustomFunction {
    public final String name;
    public final int numArgs;
    public final SQLiteDatabase.CustomFunction callback;

    /**
     * Create custom function.
     *
     * @param name The name of the sqlite3 function.
     * @param numArgs The number of arguments for the function, or -1 to
     * support any number of arguments.
     * @param callback The callback to invoke when the function is executed.
     */
    public SQLiteCustomFunction(String name, int numArgs,
            SQLiteDatabase.CustomFunction callback) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null.");
        }

        this.name = name;
        this.numArgs = numArgs;
        this.callback = callback;
    }

    // Called from native.
    @SuppressWarnings("unused")
    private String dispatchCallback(String[] args) {
        return callback.callback(args);
    }
}
