package com.laibandis.gaba

import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.security.MessageDigest
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

 lateinit var phone:EditText
 lateinit var code:EditText
 lateinit var log:TextView
 var authId=""

 val CAS="https://cas-gw-cf.euce1.indriverapp.com"
 val APP_ID="com.laibandis.gaba"

 override fun onCreate(b:Bundle?){
  super.onCreate(b)
  setContentView(R.layout.activity_main)

  phone=findViewById(R.id.phone)
  code=findViewById(R.id.code)
  log=findViewById(R.id.log)

  findViewById<Button>(R.id.send).setOnClickListener{requestCode()}
  findViewById<Button>(R.id.check).setOnClickListener{checkCode()}
 }

 fun deviceId()=System.currentTimeMillis().toString()+"-"+Random.nextLong()
 fun md5(s:String)=MessageDigest.getInstance("MD5").digest(s.toByteArray())
  .joinToString(""){"%02x".format(it)}.substring(0,16)

 fun requestCode(){
  val dev=deviceId()
  val androidId=md5(dev)
  val j=JSONObject().apply{
   put("phone",phone.text.toString())
   put("locale","ru")
   put("platform","android")
   put("app_id",APP_ID)
   put("device_id",dev)
   put("android_id",androidId)
   put("fingerprint",JSONObject().apply{
    put("model","SM-N970F"); put("brand","samsung"); put("os",30); put("tz","+05:00")
   })
  }
  post("$CAS/api/authorization",j){
   authId=it.getJSONObject("response").getJSONArray("items").getJSONObject(0).getString("auth_id")
   log.text="Код отправлен\n$authId"
  }
 }

 fun checkCode(){
  val j=JSONObject().apply{
   put("auth_id",authId)
   put("code",code.text.toString())
   put("app_id",APP_ID)
   put("platform","android")
  }
  post("$CAS/api/v2/checkauthcode",j){ log.text=it.toString(2) }
 }

 fun post(url:String, body:JSONObject, cb:(JSONObject)->Unit){
  Thread{
   val r=Net.client.newCall(
    Request.Builder().url(url)
     .post(body.toString().toRequestBody("application/json".toMediaType()))
     .build()
   ).execute().body!!.string()
   runOnUiThread{cb(JSONObject(r))}
  }.start()
 }
}
