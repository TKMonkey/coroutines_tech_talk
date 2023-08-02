package co.me.coroutines.coroutines.nounitwithstate

import android.util.Log
import kotlinx.coroutines.delay

class CoroutineNoUnitWithState {

    suspend fun printUser(token: String){
        Log.d("MyCoroutineUnitNoState", "Before CoroutineNoUnitWithState")
        val userId = getUserId(token)
        Log.d("MyCoroutineUnitNoState", "Got userId: $userId")
        val userName: String = getUsername(userId, token)
        Log.d("MyCoroutineUnitNoState", "Got username: $userName")
        Log.d("MyCoroutineUnitNoState", "Full user is => uerName: $userName with userId: $userId")
        Log.d("MyCoroutineNoUnitWithState", "After CoroutineNoUnitWithState")
    }

    private suspend fun getUsername(userId: String, token: String): String {
        delay(3000)
        return "userName"
    }


    private suspend fun getUserId(token: String): String {
        delay(3000)
        return "userId"
    }
}
