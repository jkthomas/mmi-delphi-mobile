package com.example.mmi_delphi_mobile.service.poll

import com.example.mmi_delphi_mobile.utilities.store.JsonWebTokenStore
import khttp.post
import khttp.put
import khttp.responses.Response
import org.json.JSONObject

class PollService {

    fun getServerValues(){
        val response: Response = put("http://10.0.3.2:4200/poll/getValues",
            headers = mapOf("x-access-token" to JsonWebTokenStore.getJsonWebToken()))
        val responseJson: JSONObject = response.jsonObject
        // TODO: Provide feedback
    }
}