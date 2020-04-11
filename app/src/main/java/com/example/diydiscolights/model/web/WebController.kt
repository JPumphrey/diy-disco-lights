package com.example.diydiscolights.model.web

import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

data class State(val on: Boolean, val bri: Int)

interface WebController {
    @PUT("/api/{username}/lights/{lightId}/state")
    suspend fun switch(@Path("username") username: String, @Path("lightId") lightId: Int, @Body state: State)

}