package com.cronocode.moviecatalog.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.databinding.ActivityProfileBinding
import com.cronocode.moviecatalog.models.User
import com.cronocode.moviecatalog.services.AuthService
import com.cronocode.moviecatalog.services.RetrofitClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Profile : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private var `object` = JSONObject()
    private lateinit var  authService: AuthService
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: User
    private lateinit var username: String
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getInstance()
        authService = retrofit.create(AuthService::class.java)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser

        //SharedPref init
        pref = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val jsonTokenString = pref.getString("jsonTokenString", "")


        if(jsonTokenString!=""){
            val mJSONUser = JSONObject(jsonTokenString)
            username = mJSONUser.get("userId").toString()
            val requestBody: MutableMap<String, String> = HashMap()
            requestBody["userId"] = username
            val call: Call<Any?>? = authService.getUserById(requestBody)
            call?.enqueue(object : Callback<Any?> {

                override fun onResponse(call: Call<Any?>?, response: Response<Any?>) {
                    try {
                        `object` = JSONObject(Gson().toJson(response.body()))
                        Log.e("TAG", "onResponse: $`object`")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    if(response.isSuccessful){
                        binding.username.text = `object`.get("username").toString()
                    }
                }

                override fun onFailure(call: Call<Any?>?, t: Throwable?) {}
            })
        }

        else {
            database.reference.child("profiles")
                .child(currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!
                        username = user.name.toString()
                        binding.username.text = username
                        Glide.with(this@Profile)
                            .load(user.profile)
                            .into(binding.profilePic)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
//Button and OnClickers
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)
        val searchBtn = findViewById<ImageView>(R.id.searchBtn)
        val profileBtn = findViewById<ImageView>(R.id.profileBtn)
        homeBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        })
        searchBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, movie_search_activity::class.java))
        })
        profileBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Profile::class.java))
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        logout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, Authorization::class.java)
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

    }


}