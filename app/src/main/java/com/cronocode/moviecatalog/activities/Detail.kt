package com.cronocode.moviecatalog.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.*
import com.cronocode.moviecatalog.services.AuthService
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import com.cronocode.moviecatalog.services.RetrofitClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.movie_video_links.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail : AppCompatActivity() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private lateinit var  authService: AuthService
    private var `object` = JSONObject()
    private lateinit var retrofitService: MovieApiInterface
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent: Intent = intent
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val retrofit = RetrofitClient.getInstance()
        authService = retrofit.create(AuthService::class.java)

        val movie = intent.getParcelableExtra<Movie>(MainActivity.INTENT_PARCELABLE)

        val movieSrc = findViewById<ImageView>(R.id.movie_poster)
        val movieTitle = findViewById<TextView>(R.id.movie_title)
        val movieDesc = findViewById<TextView>(R.id.movie_desc)
        val movieRating = findViewById<TextView>(R.id.movie_rating)
        val movieDate = findViewById<TextView>(R.id.movie_release_date)
        val mainPicture = findViewById<ImageView>(R.id.mainPicture)

        Glide.with(this).load(IMAGE_BASE + movie!!.backdropUrl).into(movieSrc)
        Glide.with(this).load(IMAGE_BASE + movie!!.poster).into(mainPicture)
        movieTitle.text = movie.title
        movieDesc.text = movie.overview
        movieRating.text = movie.rating
        movieDate.text = movie.release
//        Trailers
        movie_video_link.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        movie_video_link.setHasFixedSize(true)
        getMovieVideo("${movie.id}") { movieVideos : List<MovieVideosResults> ->
            movie_video_link.adapter = MovieTrailerAdapter(this, movieVideos)
        }

//        Company
        movie_companies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_companies.setHasFixedSize(true)
        getCompanyByMovieId("${movie.id}"){ companies: List<Company>->
            movie_companies.adapter = CompanyAdapter( companies)
        }

//        Genre
        movie_genres.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_genres.setHasFixedSize(true)
        getGenreByMovieId("${movie.id}"){ genres: List<Genre>->
            movie_genres.adapter = GenreAdapter( genres)
        }


//        getGenres { genres : List<Genre> ->
//            movieGen.adapter = MovieTrailerAdapter(this, movieVideos)
//        }
        movieSrc.setOnClickListener(View.OnClickListener {
            val url: String = movie_video_item_link.getText().toString()
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)
        })
        //Sharedpref init
        pref = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val jsonTokenString = pref.getString("jsonTokenString", "")


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
        btnFavourites.setOnClickListener{
            if(jsonTokenString!=""){
                val userId: String
                val mJSONUser = JSONObject(jsonTokenString)
                userId = mJSONUser.get("userId").toString()
                val requestBody: MutableMap<String, String> = HashMap()
                requestBody["userId"] = userId
                requestBody["movieId"] = movie.id.toString()
                val call: Call<Any?>? = authService.addToFavoriteMovies(requestBody)
                call?.enqueue(object : Callback<Any?> {

                    override fun onResponse(call: Call<Any?>?, response: Response<Any?>) {
                        try {
                            `object` = JSONObject(Gson().toJson(response.body()))
                            Log.e("TAG", "onResponse: $`object`")

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext, "Movie added to your favourites!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any?>?, t: Throwable?) {}
                })
            }
        }

//        val movieVideoCall: Call<MovieVideos> = retrofitService.getMovieVideosById(movie.id!!, "da0213edba5ce29d325c43cfec6aeab5")
//        movieVideoCall.enqueue(object: Callback<MovieVideos>{
//            override fun onResponse(call: Call<MovieVideos>, response: Response<MovieVideos>) {
//                val movieVideos: MovieVideos = response.body()!!
//                if(movieVideos!=null){
//                    val MovieVideosResultsList: List<MovieVideosResults> = movieVideos.results
//                    if(MovieVideosResultsList!=null && MovieVideosResultsList.size>0){
//                        val movieVideosAdapter: MovieVideosAdapter = MovieVideosAdapter(this, MovieVideosResultsList)
//                        movie_video_RV.adapter = movieVideosAdapter
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MovieVideos>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }
    private fun getMovieVideo(movieId: String, callback: (List<MovieVideosResults>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieVideosById("$movieId", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<MovieVideos> {
            override fun onFailure(call: Call<MovieVideos>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieVideos>, response: Response<MovieVideos>) {
                return callback(response.body()!!.results)
            }

        })
    }
    private fun getCompanyByMovieId(movieId: String, callback: (List<Company>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getCompanyOrGenreByMovieId("$movieId", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                return callback(response.body()!!.companies)
            }

        })
    }
    private fun getGenreByMovieId(movieId: String, callback: (List<Genre>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getCompanyOrGenreByMovieId("$movieId", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                return callback(response.body()!!.genres)
            }

        })
    }

//    private fun getGenres(callback: (List<String>) -> Unit){
//        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
//        apiService.getGenres( "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<Movie> {
//            override fun onFailure(call: Call<Movie>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
//                return callback(response.body()!!.genreIds)
//            }
//
//        })
//    }


}