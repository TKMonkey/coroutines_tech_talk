package co.me.coroutines.coroutines.unitwithstate

import android.util.Log
import kotlinx.coroutines.delay

class CoroutineUnitWithState {

    suspend fun myFunction(){
        Log.d("MyCoroutineUnitNoState", "Before CoroutineUnitWithState")
        var counter = 0
        delay(3000)
        counter++
        Log.d("MyCoroutineUnitNoState", "counter: $counter CoroutineUnitWithState")
        Log.d("MyCoroutineUnitNoState", "After CoroutineUnitWithState")
    }

}