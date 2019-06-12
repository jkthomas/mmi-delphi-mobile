package com.example.mmi_delphi_mobile.data

import com.example.mmi_delphi_mobile.service.account.AccountService

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository {
    private val accountService = AccountService()


    fun login(username: String, password: String): Boolean {
        return accountService.loginAccount(username, password)
    }

    fun register(username: String, password: String): Boolean {
        return accountService.createAccount(username, password)
    }
}
