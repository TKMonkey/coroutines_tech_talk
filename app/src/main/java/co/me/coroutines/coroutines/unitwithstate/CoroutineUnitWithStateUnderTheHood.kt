package co.me.coroutines.coroutines.unitwithstate

import android.util.Log
import co.me.coroutines.coroutines.COROUTINE_SUSPENDED
import co.me.coroutines.coroutines.MyContinuation
import co.me.coroutines.coroutines.myDelay

class CoroutineUnitWithStateUnderTheHood {

    fun myFunctionReturningUnit(continuation: MyContinuation<Unit>): Any{
        val wrappedContinuation =
            if(continuation is MyFunctionContinuation) continuation
            else MyFunctionContinuation(continuation)

        var counter = wrappedContinuation.counter

        if(wrappedContinuation.label == 0) {
            Log.d("MyCoroutineUnitNoStateUnderTheHood", "Before CoroutineUnitWithStateUnderTheHood")
            counter = 0
            wrappedContinuation.counter = counter
            wrappedContinuation.label = 1
            if(myDelay(3000, wrappedContinuation) == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
        }

        if(wrappedContinuation.label == 1) {
            counter++
            Log.d("MyCoroutineUnitNoStateUnderTheHood", "counter: $counter CoroutineUnitWithStateUnderTheHood")
            Log.d("MyCoroutineUnitNoStateUnderTheHood", "After CoroutineUnitWithStateUnderTheHood")
            return Unit
        }

        error("Impossible")
    }


    inner class MyFunctionContinuation(private val completion: MyContinuation<Unit>): MyContinuation<Unit>() {

        var counter: Int = 0

        var label = 0
        var result: Result<Any>? = null

        override fun resumeWith(result: Result<Unit>) {
            this.result = result
            val res = try{
                val r = myFunctionReturningUnit(this)

                if(r == COROUTINE_SUSPENDED) return

                Result.success(r as Unit)
            }catch (e: Throwable){
                Result.failure(e)
            }

            completion.resumeWith(res)
        }
    }
}