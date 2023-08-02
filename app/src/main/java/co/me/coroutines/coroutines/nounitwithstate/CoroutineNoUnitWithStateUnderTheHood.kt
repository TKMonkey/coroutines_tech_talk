package co.me.coroutines.coroutines.nounitwithstate

import android.util.Log
import co.me.coroutines.coroutines.COROUTINE_SUSPENDED
import co.me.coroutines.coroutines.MyContinuation
import co.me.coroutines.coroutines.myDelay

class CoroutineNoUnitWithStateUnderTheHood {

    fun printUser(token: String, continuation: MyContinuation<*>): Any{
        val wrappedContinuation: PrintUserContinuation =
            if(continuation is PrintUserContinuation) continuation
            else PrintUserContinuation(continuation as MyContinuation<Unit>, token)

        var result: Result<Any>? = wrappedContinuation.result /// Represents how this function was resumed
        var userId: String? = wrappedContinuation.userId
        val username: String

        if(wrappedContinuation.label == 0) {
            Log.d("MyCoroutineNoUnitWithStateUnderTheHood", "Before CoroutineNoUnitWithStateUnderTheHood")
            wrappedContinuation.label = 1
            val res = getUserId(token, wrappedContinuation)
            if(res == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
            result = Result.success(res)
        }

        if(wrappedContinuation.label == 1){
            userId = result?.getOrThrow() as String
            Log.d("MyCoroutineNoUnitWithStateUnderTheHood", "Got userId: $userId")
            wrappedContinuation.userId = userId
            wrappedContinuation.label = 2

            val res = getUsername(userId, token, wrappedContinuation)
            if(res == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
            result = Result.success(res)
        }

        if(wrappedContinuation.label == 2){
            username = result?.getOrThrow() as String
            Log.d("MyCoroutineNoUnitWithStateUnderTheHood", "Got username: $username")
            Log.d("MyCoroutineNoUnitWithStateUnderTheHood", "Full user is => uerName: $username with userId: $userId")
            Log.d("MyCoroutineNoUnitWithStateUnderTheHood", "After CoroutineNoUnitWithStateUnderTheHood")
            return Unit
        }

        error("Impossible")
    }


    inner class PrintUserContinuation(private val completion: MyContinuation<Unit>, val token: String): MyContinuation<String>() {

        var userId: String? = null

        var label = 0
        var result: Result<Any>? = null

        override fun resumeWith(result: Result<String>) {
            this.result = result /// Used as a result for calling my own function
            val res = try{ /// Used for calling the continuation I'm wrapping
                val r = printUser(token, this)

                if(r == COROUTINE_SUSPENDED) return

                Result.success(r as Unit)
            }catch (e: Throwable){
                Result.failure(e)
            }
            completion.resumeWith(res)
        }
    }

    fun getUserId(token: String, continuation: MyContinuation<*>): Any{
        val wrappedContinuation: GetUserIdContinuation =
            if(continuation is GetUserIdContinuation) continuation
            else GetUserIdContinuation(continuation as MyContinuation<String>, token)

        if(wrappedContinuation.label == 0) {
            wrappedContinuation.label = 1
            val res = myDelay(3000, wrappedContinuation)
            if(res == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
        }

        if(wrappedContinuation.label == 1){
            return "userId"
        }

        error("Impossible")
    }

    inner class GetUserIdContinuation(private val completion: MyContinuation<String>, val token: String): MyContinuation<Unit>(){

        var label = 0
        var result: Result<Any>? = null

        override fun resumeWith(result: Result<Unit>) {
            this.result = result

            val res = try{
                val r = getUserId(token, this)

                if(r == COROUTINE_SUSPENDED) return

                Result.success(r as String)
            }catch (e: Throwable){
                Result.failure(e)
            }

            completion.resumeWith(res)
        }

    }

    fun getUsername(token: String, userId: String, continuation: MyContinuation<*>): Any{
        val wrappedContinuation: GetUserNameContinuation =
            if(continuation is GetUserNameContinuation) continuation
            else GetUserNameContinuation(continuation as MyContinuation<String>, token, userId)

        if(wrappedContinuation.label == 0) {
            wrappedContinuation.label = 1
            val res = myDelay(3000, wrappedContinuation)
            if(res == COROUTINE_SUSPENDED) {
                return COROUTINE_SUSPENDED
            }
        }

        if(wrappedContinuation.label == 1){
            return "userName"
        }

        error("Impossible")
    }

    inner class GetUserNameContinuation(private val completion: MyContinuation<String>, val token: String, val userId: String): MyContinuation<Unit>(){

        var label = 0
        var result: Result<Any>? = null

        override fun resumeWith(result: Result<Unit>) {
            this.result = result

            val res = try{
                val r = getUsername(token, userId, this)

                if(r == COROUTINE_SUSPENDED) return

                Result.success(r as String)
            }catch (e: Throwable){
                Result.failure(e)
            }

            completion.resumeWith(res)
        }

    }
}


