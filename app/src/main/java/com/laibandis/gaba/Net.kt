package com.laibandis.gaba

import okhttp3.*

object Net {

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val req = chain.request().newBuilder()
                .header("User-Agent", "okhttp/5.3.0")
                .header("X-Platform", "android")
                .header("X-App-Id", "com.laibandis.gaba")
                .build()
            chain.proceed(req)
        }
        .build()
}
