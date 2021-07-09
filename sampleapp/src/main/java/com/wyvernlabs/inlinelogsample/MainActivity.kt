package com.wyvernlabs.inlinelogsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wyvernlabs.inlinelog.Log
import com.wyvernlabs.inlinelog.Logger
import com.wyvernlabs.inlinelog.Severity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.severity = Severity.Info
        Log.d(TAG) { "This log is never printed because of the severity level" }
        // ... and the work is only done if the log is printed.

        Log.i(TAG) { "This log appears when using the debug variant of inlinelog" }
        // ... and if using the release variant the log doesn't appear in the bytecode.

        findViewById<Button>(R.id.button_make_log).setOnClickListener {
            Log.i(TAG) { "Made a log: ${expensiveWork()}" }
        }

        findViewById<Button>(R.id.button_log_to_screen).setOnClickListener {
            Log.logger = ScreenLogger(findViewById(R.id.textView))
        }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName

        private fun expensiveWork(): String {
            Thread.sleep(1000)
            return UUID.randomUUID().toString()
        }

        class ScreenLogger(val textView: TextView) : Logger {
            override fun log(type: Severity, tag: String?, message: String, e: Throwable?) {
                textView.text = "${textView.text}\n ${System.currentTimeMillis()} $type $tag $message $e"
            }
        }
    }
}