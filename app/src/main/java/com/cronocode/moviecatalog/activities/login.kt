package com.cronocode.moviecatalog.activities;

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.services.AuthService
import com.cronocode.moviecatalog.services.RetrofitClient
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var `object` = JSONObject()
    lateinit var authService: AuthService
    internal var compositeDisposable = CompositeDisposable()
    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Init Api
        val retrofit = RetrofitClient.getInstance()
        authService = retrofit.create(AuthService::class.java)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        //Event
        btnSignIn.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEditText)
            val password = findViewById<EditText>(R.id.passwordEditText)
            val requestBody: MutableMap<String, String> = HashMap()

            requestBody["email"] = email.text.toString()
            requestBody["password"] = password.text.toString()
            val call: Call<Any?>? = authService.loginUser(requestBody)
            call?.enqueue(object : Callback<Any?> {

                override fun onResponse(call: Call<Any?>?, response: Response<Any?>) {
                    try {
                        `object` = JSONObject(Gson().toJson(response.body()))
                        Log.e("TAG", "onResponse: $`object`")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    if(response.isSuccessful){
                        //saving data in SharedPref
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("jsonTokenString", `object`.toString())
                        editor.apply()
                        //New ActivityStart
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("user", `object`.toString())
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Any?>?, t: Throwable?) {}
            })


        }
        signUpBtn1.setOnClickListener{
            startActivity(Intent(applicationContext, registration::class.java))
        }


    }

}