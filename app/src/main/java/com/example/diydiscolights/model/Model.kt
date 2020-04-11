package com.example.diydiscolights.model

import com.example.diydiscolights.model.web.State
import com.example.diydiscolights.model.web.WebController
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Model(ipAddress: String, val username: String) {

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ipAddress)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val service: WebController = retrofit.create(WebController::class.java)

    private var flashing = false

    suspend fun startFlashing() {
        flashing = true
        flash()
    }

    suspend fun stopFlashing() {
        flashing = false
    }

    suspend fun switchOn(lightId: Int) {
        service.switch(username, lightId, State(on = true, bri = 254))
    }

    suspend fun switchOff(lightId: Int) {
        service.switch(username, lightId, State(on = false, bri = 254))
    }

    suspend fun flash() {
        while (flashing) {
            switchOn(1)
            delay(1000)
            switchOff(1)
            delay(1000)
        }
    }
}