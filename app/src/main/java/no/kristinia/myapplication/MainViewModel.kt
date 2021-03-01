package no.kristinia.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val score = MutableLiveData<Int>(0)

    fun incrementScore(){
        score.value = score.value!! +1
        //score.value?.inc()
    }

}