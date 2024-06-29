
package org.whudb.database;

import java.io.Closeable;

public abstract class SQLiteClosable implements Closeable {
    private int mReferenceCount = 1;


    protected abstract void onAllReferencesReleased();


    public void acquireReference() {
        synchronized(this) {
            if (mReferenceCount <= 0) {
                throw new IllegalStateException(
                        "attempt to re-open an already-closed object: " + this);
            }
            mReferenceCount++;
        }
    }


    public void releaseReference() {
        boolean refCountIsZero;
        synchronized(this) {
            refCountIsZero = --mReferenceCount == 0;
        }
        if (refCountIsZero) {
            onAllReferencesReleased();
        }
    }


    public void close() {
        releaseReference();
    }
}
