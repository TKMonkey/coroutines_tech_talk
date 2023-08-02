package co.me.coroutines.coroutines

abstract class MyContinuation<T> {

        private var continuation: MyContinuation<T>? = null

        abstract fun resumeWith(result: Result<T>)
}