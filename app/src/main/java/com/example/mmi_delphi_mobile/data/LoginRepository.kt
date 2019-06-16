package com.example.mmi_delphi_mobile.data

import com.example.mmi_delphi_mobile.service.account.AccountService

class LoginRepository {
    private val accountService = AccountService()

    fun login(username: String, password: String): Boolean {
        return accountService.loginAccount(username, password)
    }

    fun register(username: String, password: String): Boolean {
        return accountService.createAccount(username, password)
    }
}
