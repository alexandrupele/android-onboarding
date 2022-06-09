package rdx.works.wallet.core

import android.util.Log

class Logger(private val tag: String) {

    fun debug(message: String) = Log.d(tag, message)

    fun error(message: String) = Log.e(tag, message)

    fun error(message: String, throwable: Throwable) = Log.e(tag, message, throwable)
}
