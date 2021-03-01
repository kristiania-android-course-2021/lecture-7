package no.kristinia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val coroutineScope = MainScope()

    val viewModel: MainViewModel by viewModels()

    val score = MutableLiveData<Int>()
    val scoreText = score.map { "score: $it" }
    val message = score.map { score ->
        when {
            score == 10 -> "Gratulerer med 10"
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreText.observe(this) { score ->
            findViewById<TextView>(R.id.text).text = score.toString()
        }

        message.observe(this) { message ->
            findViewById<TextView>(R.id.message).text = message
        }

        coroutineScope.launch {
            delay(1000)
        }

        GlobalScope.launch {

        }

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
            println("Something went wrong ${exception.message}")
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            coroutineScope.launch(exceptionHandler + Dispatchers.IO) {
                for (i in 1..10) {
                    delay(1000)
                    score.postValue(i)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}