package com.example.mmi_delphi_mobile.ui.feed

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.mmi_delphi_mobile.data.PollRepository

class FeedViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(
                pollRepository = PollRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
