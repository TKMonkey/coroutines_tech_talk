package co.me.coroutines.coroutines.unitnostate

import android.util.Log
import kotlinx.coroutines.delay

class CoroutineUnitNoState {

    suspend fun myFunction(){
        Log.d("MyCoroutineUnitNoState", "Before CoroutineUnitNoState")
        delay(3000)
        Log.d("MyCoroutineUnitNoState", "After CoroutineUnitNoState")
    }

}