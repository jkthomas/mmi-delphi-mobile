package com.example.mmi_delphi_mobile.service.account

import com.example.mmi_delphi_mobile.utilities.store.JsonWebTokenStore
import khttp.post
import khttp.put
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject



class AccountService {

    fun createAccount(username: String, password: String): Boolean {
        val response: Response = post("http://10.0.3.2:4200/account/signup", data = mapOf("username" to username, "password" to password))
        return response.statusCode == 200 //If statusCode == 200 -> User created account successfully
    }

    fun loginAccount(username: String, password: String): Boolean {
        val response: Response = post("http://10.0.3.2:4200/account/login", data = mapOf("username" to username, "password" to password))
        val responseJson: JSONObject = response.jsonObject
        return if(response.statusCode == 200) {
            JsonWebTokenStore.setJsonWebToken(responseJson["token"].toString())
            true
        } else {
            false
        }
    }

    fun updateAccount(username: String, password: String, newPassword: String){
        // TODO: Implement
        val response: Response = put("http://10.0.3.2:4200/account/update",
            headers = mapOf("x-access-token" to JsonWebTokenStore.getJsonWebToken()),
            data = mapOf("username" to username, "password" to password, "new_password" to newPassword))
        val responseJson: JSONObject = response.jsonObject
    }
}