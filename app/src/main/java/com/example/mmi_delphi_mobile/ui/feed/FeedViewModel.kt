package com.example.mmi_delphi_mobile.ui.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.mmi_delphi_mobile.data.PollRepository
import org.json.JSONObject

class FeedViewModel(private val pollRepository: PollRepository) : ViewModel() {

    fun getPollData(): JSONObject{
        return pollRepository.getPoll()
    }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Unused placeholder"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}