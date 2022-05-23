package com.cronocode.moviecatalog.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.*
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_video_links.*
import kotlinx.android.synthetic.main.movie_video_links.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail : AppCompatActivity() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private lateinit var retrofitService: MovieApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent: Intent = intent
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

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
        movie_video_link.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        movie_video_link.setHasFixedSize(true)
        getMovieVideo("${movie.id}") { movieVideos : List<MovieVideosResults> ->
            movie_video_link.adapter = MovieTrailerAdapter(this, movieVideos)
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