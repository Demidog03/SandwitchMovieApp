package com.cronocode.moviecatalog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Movie

class Detail : AppCompatActivity() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getParcelableExtra<Movie>(MainActivity.INTENT_PARCELABLE)

        val movieSrc = findViewById<ImageView>(R.id.movie_poster)
        val movieTitle = findViewById<TextView>(R.id.movie_title)
        val movieDesc = findViewById<TextView>(R.id.movie_desc)
        val movieRating = findViewById<TextView>(R.id.movie_rating)
        val movieDate = findViewById<TextView>(R.id.movie_release_date)

        Glide.with(this).load(IMAGE_BASE + movie!!.poster).into(movieSrc)
        movieTitle.text = movie.title
        movieDesc.text = movie.overview
        movieRating.text = movie.rating
        movieDate.text = movie.release

    }
}