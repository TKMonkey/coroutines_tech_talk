package co.me.coroutines.coroutines.unitnostate

import android.util.Log
import co.me.coroutines.coroutines.COROUTINE_SUSPENDED
import co.me.coroutines.coroutines.MyContinuation
import co.me.coroutines.coroutines.myDelay

class CoroutineUnitNoStateUnderTheHood {

    fun myFunctionReturningUnit(continuation: MyContinuation<Unit>): Any{
        val wrappedContinuation =
            if(continuation is MyFunctionContinuation) continuation
            else MyFunctionContinuation(continuation)

        if(wrappedContinuation.label == 0) {
            Log.d("MyCoroutines1UnderTheHood", "Before CoroutineUnitNoStateUnderTheHood")
            wrappedContinuation.label = 1
            if(myDelay(3000, wrappedContinuation) == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
        }

        if(wrappedContinuation.label == 1) {
            Log.d("MyCoroutines1UnderTheHood", "After CoroutineUnitNoStateUnderTheHood")
            return Unit
        }

        error("Impossible")
    }


    inner class MyFunctionContinuation(private val completion: MyContinuation<Unit>): MyContinuation<Unit>() {

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