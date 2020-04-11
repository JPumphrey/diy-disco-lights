package com.example.diydiscolights.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.diydiscolights.model.web.State
import com.example.diydiscolights.model.web.WebController
import com.example.diydiscolights.ui.home.HomeViewModel
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlashController(
    ipAddress: String,
    private val username: String,
    private val setupState: SetupState,
    private val scope: CoroutineScope
) {

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
    private var interval = fromBpm(120)

    fun observe(
        homeViewModel: HomeViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        homeViewModel.flashing.observe(viewLifecycleOwner, Observer {
            if (it) {
                startFlashing()
            } else {
                stopFlashing()
            }
        })

        homeViewModel.bpm.observe(viewLifecycleOwner, Observer { interval = fromBpm(it) })
    }

    private fun startFlashing() {
        flashing = true
        scope.launch {
            flash()
        }
    }

    private fun stopFlashing() {
        flashing = false
    }

    private suspend fun switchOn(lightId: Int) {
        service.switch(username, lightId, State(on = true, bri = 254))
    }

    private suspend fun switchOff(lightId: Int) {
        service.switch(username, lightId, State(on = true, bri = 0))
    }

    private suspend fun flash() {
        for ((index, value) in setupState.lights.withIndex()) {
            delay(interval / setupState.lights.size * index)
            scope.launch(Dispatchers.IO) {
                flashOneLight(value)
            }
        }
    }

    private suspend fun flashOneLight(
        lightId: Int
    ) {
        while (flashing) {
            switchOn(lightId)
            delay(interval / 2)
            switchOff(lightId)
            delay(interval / 2)
        }
    }

    private fun fromBpm(bpm: Int) : Long {
        val millisPerMinute = 60 * 1000L
        val beatsPerFlash = 4
        return millisPerMinute * beatsPerFlash / bpm
    }
}