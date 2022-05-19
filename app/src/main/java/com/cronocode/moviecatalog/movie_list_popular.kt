package com.cronocode.moviecatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_list_popular.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movie_list_popular : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list_popular)
        rv_movies_list_popular.layoutManager = LinearLayoutManager(this)
        rv_movies_list_popular.setHasFixedSize(true)
        getMovieData { movies : List<Movie> ->
            rv_movies_list_popular.adapter = MovieAdapter(movies, true)
        }
    }
    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }
}