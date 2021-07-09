package com.wyvernlabs.inlinelog

@Suppress("UNUSED_PARAMETER")
inline fun log(severity: Severity, tag: String?, message: () -> String, e: () -> Throwable? = { null }) {
    // Intentionally a no-op. Because this is inline, the lambda definitions at the call sites will
    // be compiled out, so the logs will not appear in the compiled bytecode.
}