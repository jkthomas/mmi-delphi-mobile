package com.example.mmi_delphi_mobile.service.account

import khttp.post
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject



class AccountService {

    fun loginAccount(username: String, password: String){
        val response: Response = post("http://10.0.3.2:4200/account/login", data = mapOf("username" to username, "password" to password))
        val responseJson: JSONObject = response.jsonObject
        val jsonWebToken: String = responseJson["token"].toString()
        // TODO: Implement saving
    }
}