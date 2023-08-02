package co.me.coroutines.coroutines

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val executor = Executors.newSingleThreadScheduledExecutor{
    Thread(it, "scheduler").apply { isDaemon = true }
}

fun myDelay(timeMillis: Long, continuation: MyContinuation<Unit>): Any{
    executor.schedule( {
        continuation.resumeWith(Result.success(Unit))
    }, timeMillis, TimeUnit.MILLISECONDS)

    return COROUTINE_SUSPENDED
}