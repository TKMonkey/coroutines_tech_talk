package co.me.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import co.me.coroutines.coroutines.unitnostate.CoroutineUnitNoState
import co.me.coroutines.coroutines.unitnostate.CoroutineUnitNoStateUnderTheHood
import co.me.coroutines.coroutines.MyContinuation
import co.me.coroutines.coroutines.nounitwithstate.CoroutineNoUnitWithState
import co.me.coroutines.coroutines.nounitwithstate.CoroutineNoUnitWithStateUnderTheHood
import co.me.coroutines.coroutines.unitwithstate.CoroutineUnitWithState
import co.me.coroutines.coroutines.unitwithstate.CoroutineUnitWithStateUnderTheHood
import co.me.coroutines.ui.theme.CoroutinesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sleepThread()

//        launchCoroutineUnitNoState()
//        launchCoroutineUnitNoStateUnderTheHood()
//
//        launchCoroutinesUnitWithState()
//        launchCoroutinesUnitWithStateUnderTheHood()
//
//        launchCoroutineNoUnitWithState()
        launchCoroutineNoUnitWithStateUnderTheHood()


        setContent {
            CoroutinesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun sleepThread() {
        Thread.sleep(5000)
    }

    private fun launchCoroutineNoUnitWithState() {
        lifecycleScope.launch {
            CoroutineNoUnitWithState().printUser("aToken")
        }
    }

    private fun launchCoroutineNoUnitWithStateUnderTheHood() {
        lifecycleScope.launch {
            CoroutineNoUnitWithStateUnderTheHood().printUser("aToken", object: MyContinuation<Unit>(){
                override fun resumeWith(result: Result<Unit>) {
                    Log.d("MyCoroutine", "Result in the activity for CoroutineNoUnitWithStateUnderTheHood: $result")
                }
            })
        }
    }

    private fun launchCoroutinesUnitWithStateUnderTheHood() {
        lifecycleScope.launch {
            CoroutineUnitWithStateUnderTheHood().myFunctionReturningUnit(object: MyContinuation<Unit>(){
                override fun resumeWith(result: Result<Unit>) {
                    Log.d("MyCoroutine", "Result in the activity for CoroutineUnitWithStateUnderTheHood: $result")
                }
            })
        }
    }

    private fun launchCoroutinesUnitWithState() {
        lifecycleScope.launch {
            CoroutineUnitWithState().myFunction()
        }
    }

    private fun launchCoroutineUnitNoStateUnderTheHood() {
        CoroutineUnitNoStateUnderTheHood().myFunctionReturningUnit(object: MyContinuation<Unit>(){
            override fun resumeWith(result: Result<Unit>) {
                Log.d("MyCoroutine", "Result in the activity for CoroutineUnitNoStateUnderTheHood: $result")
            }
        })

        Log.d("MyCoroutine", "Continues after CoroutineUnitNoStateUnderTheHood")
    }

    private fun launchCoroutineUnitNoState() {
        lifecycleScope.launch {
            CoroutineUnitNoState().myFunction()
        }

        Log.d("MyCoroutine", "Continues after CoroutineUnitNoState")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinesTheme {
        Greeting("Android")
    }
}