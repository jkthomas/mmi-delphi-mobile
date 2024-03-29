package com.example.mmi_delphi_mobile.ui.login

import android.arch.lifecycle.ViewModel
import com.example.mmi_delphi_mobile.data.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    fun login(username: String, password: String): Boolean {
        return loginRepository.login(username, password)
    }

    fun register(username: String, password: String): Boolean {
        return loginRepository.register(username, password)
    }
}
