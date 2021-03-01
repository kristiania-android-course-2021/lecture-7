package no.kristinia.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MainViewModel: ViewModel() {

    val score = MutableLiveData<Int>(0)

    val message = score.map { score ->
        when(score){
            10 -> "Gratulerer du har nådd 10 poeng"
            1 -> "Takk for at du spiller"
            else -> ""
        }
    }

    val messageVisible = MutableLiveData<Boolean>(false)

    fun incrementScore(){
        score.value = score.value!! +1
        //score.value?.inc()
    }

}