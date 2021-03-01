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
            MainScope().launch {
                if (message.isNotEmpty()) {
                    findViewById<TextView>(R.id.message).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.message).text = message
                    delay(5000)
                    findViewById<TextView>(R.id.message).visibility = View.GONE
                }
            }

        }

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.incrementScore()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}