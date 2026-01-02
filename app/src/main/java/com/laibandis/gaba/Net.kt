package com.laibandis.gaba

import okhttp3.*

object Net {
    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val r = chain.request().newBuilder()
            .addHeader("User-Agent", "okhttp/5.3.0")
            .addHeader("X-Platform", "android")
            .addHeader("X-App-Id", "com.laibandis.gaba")
            .build()
        chain.proceed(r)
    }.build()
}
