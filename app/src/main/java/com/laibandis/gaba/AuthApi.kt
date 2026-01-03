package com.laibandis.gaba

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

fun requestAuthCode(
    phone: String,
    phoneCode: String = "+7",
    countryIso: String = "KZ"
) {
    val jsonBody = JSONObject().apply {
        put("phone", phone)
        put("phoneCode", phoneCode)
        put("countryIso", countryIso)
        put("mode", "request")
    }

    val client = AppNetworkClient.getInstance().okHttp

    val requestBody = jsonBody.toString()
        .toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url("https://cas-gw-cf.euce1.gabaapp.com/api/authorization")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {

        override fun onResponse(call: Call, response: Response) {
            response.use {
                val body = it.body?.string()
                if (it.isSuccessful) {
                    Log.d("AuthCode", "Response: $body")
                } else {
                    Log.e("AuthCode", "Server error ${it.code}: $body")
                }
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            Log.e("AuthCode", "Network error", e)
        }
    })
}
