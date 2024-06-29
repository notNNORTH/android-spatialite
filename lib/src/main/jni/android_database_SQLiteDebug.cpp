
#define LOG_TAG "SQLiteDebug"

#include <jni.h>
#include "JNIHelp.h"
#include "ALog-priv.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sqlite3.h>

namespace android {

static struct {
    jfieldID memoryUsed;
    jfieldID pageCacheOverflow;
    jfieldID largestMemAlloc;
} gSQLiteDebugPagerStatsClassInfo;

static void nativeGetPagerStats(JNIEnv *env, jobject clazz, jobject statsObj)
{
    int memoryUsed;
    int pageCacheOverflow;
    int largestMemAlloc;
    int unused;

    sqlite3_status(SQLITE_STATUS_MEMORY_USED, &memoryUsed, &unused, 0);
    sqlite3_status(SQLITE_STATUS_MALLOC_SIZE, &unused, &largestMemAlloc, 0);
    sqlite3_status(SQLITE_STATUS_PAGECACHE_OVERFLOW, &pageCacheOverflow, &unused, 0);
    env->SetIntField(statsObj, gSQLiteDebugPagerStatsClassInfo.memoryUsed, memoryUsed);
    env->SetIntField(statsObj, gSQLiteDebugPagerStatsClassInfo.pageCacheOverflow,
            pageCacheOverflow);
    env->SetIntField(statsObj, gSQLiteDebugPagerStatsClassInfo.largestMemAlloc, largestMemAlloc);
}

/*
 * JNI registration.
 */

static JNINativeMethod gMethods[] =
{
    { "nativeGetPagerStats", "(Lorg/whudb/database/SQLiteDebug$PagerStats;)V",
            (void*) nativeGetPagerStats },
};

int register_android_database_SQLiteDebug(JNIEnv *env)
{
    jclass clazz;
    FIND_CLASS(clazz, "org/whudb/database/SQLiteDebug$PagerStats");

    GET_FIELD_ID(gSQLiteDebugPagerStatsClassInfo.memoryUsed, clazz,
            "memoryUsed", "I");
    GET_FIELD_ID(gSQLiteDebugPagerStatsClassInfo.largestMemAlloc, clazz,
            "largestMemAlloc", "I");
    GET_FIELD_ID(gSQLiteDebugPagerStatsClassInfo.pageCacheOverflow, clazz,
            "pageCacheOverflow", "I");

    return jniRegisterNativeMethods(env, "org/whudb/database/SQLiteDebug",
            gMethods, NELEM(gMethods));
}

} // namespace android
