package com.example.mmi_delphi_mobile.data

import com.example.mmi_delphi_mobile.service.poll.PollService
import org.json.JSONObject

class PollRepository {
    private val pollService = PollService()

    fun getPoll(): JSONObject {
        return pollService.getServerValues()
    }
}