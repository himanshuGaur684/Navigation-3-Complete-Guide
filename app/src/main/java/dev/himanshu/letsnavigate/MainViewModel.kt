package dev.himanshu.letsnavigate

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var tag = ""

    fun updateTag(tag: String) {
        this.tag = tag
    }


    override fun onCleared() {
        Log.d("TAGGGGGGGGGGG", "onCleared: ${tag}")
        super.onCleared()
    }

}