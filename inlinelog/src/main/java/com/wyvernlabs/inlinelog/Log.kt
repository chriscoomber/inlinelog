package com.wyvernlabs.inlinelog

import com.wyvernlabs.inlinelog.Severity.*

/**
 * Stand-in replacement for [android.util.Log].
 *
 * If you compile the release version of this library, all logs become no-ops, and any lambdas that
 * are used as parameters will be compiled out. This uses the fact that the log functions are
 * inlined, so the lambda's code is placed inside the log function's code which gets copied to the
 * call site. Since the log function's code doesn't use the lambda, the lambda is simply absent in
 * the compiled bytecode. I.e., in the following statement the bytecode will not contain any of this
 * log:
 *
 * ```
 * Log.i(TAG) { "The machine went $ping" }
 * ```
 *
 * Therefore, the configurable parameters [logger] and [severity] only have an effect for the debug
 * variant of this library.
 *
 * A similar effect could be achieved without this library by using the following in your release
 * variant's proguard file:
 *
 * ```
 * -assumenosideeffects class android.util.Log {
 *     public static *** v(...);
 *     public static *** d(...);
 *     public static *** i(...);
 *     public static *** w(...);
 *     public static *** e(...);
 *     public static *** wtf(...);
 * }
 * ```
 *
 * This checks to see if the return value for these functions is used (it won't be) and if not it
 * removes them.
 *
 * However, this library feels slightly cleaner than relying on proguard rules, though you could
 * argue that instead we're relying on how the compiler treats inline. Either way, we're "compiling
 * out" the logs in release code, which is better than simply not processing the message lambdas.
 */
object Log {
    /**
     * Configurable logging implementation, in case you want to write your own logger which prints
     * to file, or something like that.
     */
    @Volatile
    var logger: Logger = DefaultLogger

    /**
     * Configurable severity. All logs will a lower severity will be ignored.
     */
    @Volatile
    var severity: Severity = Debug

    inline fun v(tag: String?, message: () -> String, e: () -> Throwable?) = log(Verbose, tag, message, e)
    inline fun v(tag: String?, message: () -> String, e: Throwable?) = log(Verbose, tag, message, { e })
    inline fun v(tag: String?, message: () -> String) = log(Verbose, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.v(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun v(tag: String?, message: String, e: () -> Throwable?) = log(Verbose, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.v(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun v(tag: String?, message: String, e: Throwable?) = log(Verbose, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.v(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun v(tag: String?, message: String) = log(Verbose, tag, { message }, { null })

    inline fun d(tag: String?, message: () -> String, e: () -> Throwable?) = log(Debug, tag, message, e)
    inline fun d(tag: String?, message: () -> String, e: Throwable?) = log(Debug, tag, message, { e })
    inline fun d(tag: String?, message: () -> String) = log(Debug, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.d(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun d(tag: String?, message: String, e: () -> Throwable?) = log(Debug, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.d(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun d(tag: String?, message: String, e: Throwable?) = log(Debug, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.d(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun d(tag: String?, message: String) = log(Debug, tag, { message }, { null })

    inline fun i(tag: String?, message: () -> String, e: () -> Throwable?) = log(Info, tag, message, e)
    inline fun i(tag: String?, message: () -> String, e: Throwable?) = log(Info, tag, message, { e })
    inline fun i(tag: String?, message: () -> String) = log(Info, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.i(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun i(tag: String?, message: String, e: () -> Throwable?) = log(Info, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.i(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun i(tag: String?, message: String, e: Throwable?) = log(Info, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.i(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun i(tag: String?, message: String) = log(Info, tag, { message }, { null })

    inline fun w(tag: String?, message: () -> String, e: () -> Throwable?) = log(Warn, tag, message, e)
    inline fun w(tag: String?, message: () -> String, e: Throwable?) = log(Warn, tag, message, { e })
    inline fun w(tag: String?, message: () -> String) = log(Warn, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.w(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun w(tag: String?, message: String, e: () -> Throwable?) = log(Warn, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.w(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun w(tag: String?, message: String, e: Throwable?) = log(Warn, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.w(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun w(tag: String?, message: String) = log(Warn, tag, { message }, { null })

    inline fun e(tag: String?, message: () -> String, e: () -> Throwable?) = log(Error, tag, message, e)
    inline fun e(tag: String?, message: () -> String, e: Throwable?) = log(Error, tag, message, { e })
    inline fun e(tag: String?, message: () -> String) = log(Error, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.e(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun e(tag: String?, message: String, e: () -> Throwable?) = log(Error, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.e(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun e(tag: String?, message: String, e: Throwable?) = log(Error, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.e(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun e(tag: String?, message: String) = log(Error, tag, { message }, { null })

    inline fun wtf(tag: String?, message: () -> String, e: () -> Throwable?) = log(Wtf, tag, message, e)
    inline fun wtf(tag: String?, message: () -> String, e: Throwable?) = log(Wtf, tag, message, { e })
    inline fun wtf(tag: String?, message: () -> String) = log(Wtf, tag, message, { null })
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.wtf(tag, { message }, e)", "com.wyvernlabs.inlinelog.Log"))
    inline fun wtf(tag: String?, message: String, e: () -> Throwable?) = log(Wtf, tag, { message }, e)
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.wtf(tag, { message }, { e })", "com.wyvernlabs.inlinelog.Log"))
    inline fun wtf(tag: String?, message: String, e: Throwable?) = log(Wtf, tag, { message }, { e })
    @Suppress("NOTHING_TO_INLINE")
    @Deprecated("Use lazy message evaluation so less work is done if the log is not printed",
        ReplaceWith("Log.wtf(tag) { message }", "com.wyvernlabs.inlinelog.Log"))
    inline fun wtf(tag: String?, message: String) = log(Wtf, tag, { message }, { null })
}

enum class Severity {
    Verbose, Debug, Info, Warn, Error, Wtf
}

interface Logger {
    fun log(type: Severity, tag: String?, message: String, e: Throwable? = null)
}

object DefaultLogger : Logger {
    override fun log(type: Severity, tag: String?, message: String, e: Throwable?) {
        if (e != null) {
            when (type) {
                Verbose -> android.util.Log.v(tag, message, e)
                Debug -> android.util.Log.d(tag, message, e)
                Info -> android.util.Log.i(tag, message, e)
                Warn -> android.util.Log.w(tag, message, e)
                Error -> android.util.Log.e(tag, message, e)
                Wtf -> android.util.Log.wtf(tag, message, e)
            }
        } else {
            when (type) {
                Verbose -> android.util.Log.v(tag, message)
                Debug -> android.util.Log.d(tag, message)
                Info -> android.util.Log.i(tag, message)
                Warn -> android.util.Log.w(tag, message)
                Error -> android.util.Log.e(tag, message)
                Wtf -> android.util.Log.wtf(tag, message)
            }
        }
    }
}
