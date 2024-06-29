
#ifndef _ANDROID_DATABASE_SQLITE_COMMON_H
#define _ANDROID_DATABASE_SQLITE_COMMON_H

#include <jni.h>
#include <JNIHelp.h>

#include <sqlite3.h>

// Special log tags defined in SQLiteDebug.java.
#define SQLITE_LOG_TAG "SQLiteLog"
#define SQLITE_TRACE_TAG "SQLiteStatements"
#define SQLITE_PROFILE_TAG "SQLiteTime"

namespace android {

/* throw a SQLiteException with a message appropriate for the error in handle */
void throw_sqlite3_exception(JNIEnv* env, sqlite3* handle);

/* throw a SQLiteException with the given message */
void throw_sqlite3_exception(JNIEnv* env, const char* message);

/* throw a SQLiteException with a message appropriate for the error in handle
   concatenated with the given message
 */
void throw_sqlite3_exception(JNIEnv* env, sqlite3* handle, const char* message);

/* throw a SQLiteException for a given error code */
void throw_sqlite3_exception_errcode(JNIEnv* env, int errcode, const char* message);

void throw_sqlite3_exception(JNIEnv* env, int errcode,
        const char* sqlite3Message, const char* message);

}

#endif // _ANDROID_DATABASE_SQLITE_COMMON_H
