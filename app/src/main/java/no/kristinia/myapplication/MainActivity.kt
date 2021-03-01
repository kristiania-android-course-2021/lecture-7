package no.kristinia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val coroutineScope = MainScope()

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.score.observe(this) { score ->
            findViewById<TextView>(R.id.text).text = score.toString()
        }

        viewModel.message.observe(this) { message ->
            // Non blocking, DENNE KJØRER PÅ EN ANNEN THREAD
            MainScope().launch(Dispatchers.IO) {
                for(i in 1..100000000) println(i)
            }
            //Blocking. DENNE KJØRER PÅ MAIN THREAD
            MainScope().launch(Dispatchers.IO) {
                val mydata = mySuspendFun()
                println("")
            }

        }

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.incrementScore()
        }

    }

    suspend fun mySuspendFun(): Int{
        delay(5000)
        return 1
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}