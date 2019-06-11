package com.example.mmi_delphi_mobile.utilities.store

object JsonWebTokenStore {
    var jwt: String = ""

    fun setJsonWebToken(jwt: String){
        this.jwt = jwt
    }

    fun getJsonWebToken(): String {
        return this.jwt
    }
}