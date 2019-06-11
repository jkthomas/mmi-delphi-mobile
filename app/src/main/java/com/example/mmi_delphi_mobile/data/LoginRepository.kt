package com.example.mmi_delphi_mobile.data

import android.widget.Toast
import com.example.mmi_delphi_mobile.data.model.LoggedInUser
import com.example.mmi_delphi_mobile.service.account.AccountService
import java.lang.Exception
import java.net.ConnectException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    private val accountService = AccountService()

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Boolean {
        // TODO: Implement full login/signin
//        val result = dataSource.login(username, password)
//
//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
        val result = accountService.loginAccount(username, password)

        return result
    }

    fun register(username: String, password: String): Boolean {
        val result = accountService.createAccount(username, password)

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
