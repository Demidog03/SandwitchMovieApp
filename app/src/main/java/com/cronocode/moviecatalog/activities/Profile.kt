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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.databinding.ActivityProfileBinding
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.models.User
import com.cronocode.moviecatalog.services.AuthService
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
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
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Profile : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private var `object` = JSONObject()
    private var `favos` = JSONArray()
    private lateinit var  authService: AuthService
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: User
    private lateinit var username: String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var favList: Call<List<JSONObject>?>

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
                        rv_favourites_list.visibility = View.GONE
                        dancingDog.visibility = View.VISIBLE
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
        if(jsonTokenString!="") {
            val mJSONUser = JSONObject(jsonTokenString)
            val userId = mJSONUser.get("userId").toString()
            val call: Call<Any?>? = authService.getFavourites(userId)
            call?.enqueue(object : Callback<Any?> {

                override fun onResponse(call: Call<Any?>?, response: Response<Any?>) {
                    try {
                        `favos` = JSONArray(Gson().toJson(response.body()))
                        Log.e("TAG", "onResponse: $`favos`")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    if (response.isSuccessful) {
                        val movieFav: Int = JSONArray(`favos`.toString()).length()
                        val movieList: MutableList<Movie> = ArrayList()
                        //Favourites
                        rv_favourites_list.layoutManager = LinearLayoutManager(applicationContext)
                        rv_favourites_list.setHasFixedSize(true)

                        for (i in 0 until movieFav) {
                            val movieFavEach = JSONObject(`favos`[i].toString())
                            val movieId = movieFavEach.get("movieId")
                            getGenreByMovieId("${movieId}") { movie: Movie ->
                                rv_favourites_list.adapter = MovieFavAdapter(movie, "vertical") {

                                }
                                movieList.add(movie)
                                Log.e("TAG", "Movie: ${movieList.size}")
                            }
                        }

                        rv_favourites_list.layoutManager = LinearLayoutManager(applicationContext)
                        rv_favourites_list.setHasFixedSize(true)
                        getMovieData {
                            rv_favourites_list.adapter = MovieAdapter(movieList, "vertical") {
                                val intent = Intent(applicationContext, Detail::class.java)
                                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                                startActivity(intent)
                            }
                        }


                    }
                }

                override fun onFailure(call: Call<Any?>?, t: Throwable?) {}
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
    private fun getGenreByMovieId(movieId: String, callback: (Movie) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getCompanyOrGenreByMovieId("$movieId", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                return callback(response.body()!!)
            }

        })
    }
    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList("upcoming", "bbf5a3000e95f1dddf266b5e187d4b21").enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }

}