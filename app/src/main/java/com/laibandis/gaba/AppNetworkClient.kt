package com.laibandis.gaba

import okhttp3.OkHttpClient

object AppNetworkClient {

    val okHttp: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()

    fun getInstance(): AppNetworkClient = this
}
