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

            MainScope().launch {
                val messageView = findViewById<TextView>(R.id.message)
                val message = when {
                    score == 10 -> "Gratulerer du har nådd 10 poeng"
                    score == 1 -> "Takk for at du spiller!"
                    else -> ""
                }
                if(message.isNotEmpty()){
                    messageView.visibility = View.VISIBLE
                    messageView.text = message
                    delay(5000)
                    messageView.visibility = View.GONE
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