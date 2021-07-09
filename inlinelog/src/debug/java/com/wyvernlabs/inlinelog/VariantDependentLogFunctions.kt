package com.wyvernlabs.inlinelog

inline fun log(severity: Severity, tag: String?, message: () -> String, e: () -> Throwable? = { null }) {
    if (Log.severity <= severity) {
        Log.logger.log(severity, tag, message(), e())
    }
}