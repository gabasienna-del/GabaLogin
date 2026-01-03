package com.laibandis.gaba

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // если какой-то SDK позволяет — тут подключается Net.client
        // Example: SomeSdk.setOkHttpClient(Net.client)
    }
}
