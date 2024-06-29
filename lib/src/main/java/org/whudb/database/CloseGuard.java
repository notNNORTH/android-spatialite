
package org.whudb.database;

import android.util.Log;

@SuppressWarnings("unused")
public final class CloseGuard {

    private static final CloseGuard NOOP = new CloseGuard();


    private static volatile boolean ENABLED = true;


    private static volatile Reporter REPORTER = new DefaultReporter();


    public static CloseGuard get() {
        if (!ENABLED) {
            return NOOP;
        }
        return new CloseGuard();
    }


    public static void setEnabled(boolean enabled) {
        ENABLED = enabled;
    }


    public static void setReporter(Reporter reporter) {
        if (reporter == null) {
            throw new NullPointerException("reporter == null");
        }
        REPORTER = reporter;
    }


    public static Reporter getReporter() {
        return REPORTER;
    }

    private CloseGuard() {}


    public void open(String closer) {
        // always perform the check for valid API usage...
        if (closer == null) {
            throw new NullPointerException("closer == null");
        }
        // ...but avoid allocating an allocationSite if disabled
        if (this == NOOP || !ENABLED) {
            return;
        }
        String message = "Explicit termination method '" + closer + "' not called";
        allocationSite = new Throwable(message);
    }

    private Throwable allocationSite;


    public void close() {
        allocationSite = null;
    }


    public void warnIfOpen() {
        if (allocationSite == null || !ENABLED) {
            return;
        }

        String message =
                ("A resource was acquired at attached stack trace but never released. "
                 + "See java.io.Closeable for information on avoiding resource leaks.");

        REPORTER.report(message, allocationSite);
    }


    public interface Reporter {
        void report(String message, Throwable allocationSite);
    }


    private static final class DefaultReporter implements Reporter {
        @Override public void report (String message, Throwable allocationSite) {
            Log.w("SQLite", message, allocationSite);
        }
    }
}
