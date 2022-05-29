package com.cronocode.moviecatalog.activities.genres

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.activities.*
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_movie_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class genre_list_drama: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        genreTitle.text = "Drama"
        rv_movies_list_popular.layoutManager = LinearLayoutManager(this)
        rv_movies_list_popular.setHasFixedSize(true)
        getMovieDataByGenres("18") { movies : List<Movie> ->
            rv_movies_list_popular.adapter = MovieAdapter(movies, "vertical"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
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
    }
    private fun getMovieDataByGenres( genre: String, callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieListByGenres("da0213edba5ce29d325c43cfec6aeab5", "$genre").enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }
}