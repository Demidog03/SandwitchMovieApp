//package com.cronocode.moviecatalog.activities
//
//import android.os.Bundle
//import android.util.AndroidException
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.cronocode.moviecatalog.R
//import com.cronocode.moviecatalog.models.RegisterRequest
//import com.cronocode.moviecatalog.models.User
//import com.cronocode.moviecatalog.models.UserResponse
//import com.cronocode.moviecatalog.services.MovieApiInterface
//import com.cronocode.moviecatalog.services.MovieApiService
//import com.cronocode.moviecatalog.services.RetrofitClient
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.schedulers.Schedulers
//import kotlinx.android.synthetic.main.activity_registration.*
//import okhttp3.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//
//class registration : AppCompatActivity() {
//    lateinit var iMyService: MovieApiInterface
//    lateinit var registerRequest: RegisterRequest
//    internal val compositeDisposable = CompositeDisposable()
//    override fun onStop() {
//        super.onStop()
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)
//
//        //Init Api
//        val retrofit: Retrofit = RetrofitClient.getInstance()
//        iMyService = retrofit.create(MovieApiInterface::class.java)
//
//        //event
//        val registerBtn = findViewById<Button>(R.id.registerBtn)
//        val registerEmailTV = findViewById<EditText>(R.id.registerEmail)
//        registerBtn.setOnClickListener(View.OnClickListener {
//            val registerEmail = registerEmailTV.getText().toString()
//            val registerPassword = registerPassword.getText().toString()
//
//            registerRequest.userEmail = registerEmail
//            registerRequest.userPassword = registerPassword
//            registerUser(registerRequest)
//        })
//
//    }
//
//    private fun registerUser(registerRequest: RegisterRequest) {
//        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
//        val userResponseCall: Call<UserResponse> = apiService.registerUser(registerRequest)
//        userResponseCall.enqueue(object: Callback<UserResponse>{
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                if(response.isSuccessful){
//                    println("SAVED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                }
//                else{
//                    println("FAILED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                println("FAILED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//            }
//
//        })
//    }
////    private fun register(email: String, password: String) {
////        val requestBody: RequestBody = MultipartBody.Builder()
////            .setType(MultipartBody.FORM)
////            .addFormDataPart("email", email)
////            .addFormDataPart("password", password)
////            .build()
////        val request = Request.Builder()
////            .url("https://localhost:5000/api/auth/register")
////            .post(requestBody)
////            .build()
////        val client = OkHttpClient()
////        try {
////            val response: Response = client.newCall(request).execute()
////            if (!response.isSuccessful) throw IOException("Unexpected code " + response)
////            val responseHeaders: Headers = response.headers()
////            for (i in 0 until responseHeaders.size()) {
////                println(responseHeaders.name(i).toString() + ": " + responseHeaders.value(i))
////            }
////            System.out.println(response.body()!!.string());
////            println("Hello")
////            Log.e("Error", "Error")
////        }
////        finally {
////            System.out.println("ok");
////        }
////    }
//}