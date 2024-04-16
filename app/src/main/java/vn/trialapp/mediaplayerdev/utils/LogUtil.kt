package vn.trialapp.mediaplayerdev.utils

class LogUtil {

    companion object {
        private var tag = ""
        private var methodName = ""

        fun initTitle(stackTraceElement: StackTraceElement) {
            tag = "[${stackTraceElement.fileName.substringBefore(".")}]"
            methodName = stackTraceElement.methodName + "()"
        }

        fun e(message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.e(tag, "ForFun | $methodName | $message")
        }

        fun d(message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.d(tag, "ForFun | $methodName | $message")
        }

        fun i(message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.i(tag, "ForFun | $methodName | $message")
        }

        fun w(message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.w(tag, "ForFun | $methodName | $message")
        }

        fun v(message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.v(tag, "ForFun | $methodName | $message")
        }

        fun traceIn() {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.i(tag, "ForFun | $methodName | TRACE_IN")
        }

        fun traceOut() {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            initTitle(stackTraceElement)
            android.util.Log.i(tag, "ForFun | $methodName | TRACE_OUT")
        }
    }
}