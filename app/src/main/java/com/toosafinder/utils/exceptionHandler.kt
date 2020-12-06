package com.toosafinder.utils

import android.util.Log
import kotlinx.coroutines.*

/**
 * Запускает корутину и логирует ошибки, если они приходят
 */
fun CoroutineScope.launchWithErrorLogging(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(coroutineExceptionLogger, start, block)

private val coroutineExceptionLogger = CoroutineExceptionHandler { _, exception ->
    Log.e("Coroutine", "caught unhandled exception while execution async task", exception)
    throw IllegalArgumentException(exception)
}
