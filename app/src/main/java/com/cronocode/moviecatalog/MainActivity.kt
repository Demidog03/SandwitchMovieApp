package com.cronocode.moviecatalog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.movie_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        /**
         * For movie list page
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_movies_list.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list.setHasFixedSize(true)
        getMovieData { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies, false)
        }

        /////////////

        rv_movies_list2.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list2.setHasFixedSize(true)
        getMovieData2 { movies : List<Movie> ->
            rv_movies_list2.adapter = MovieAdapter(movies, false)
        }

        seeMorePopular.setOnClickListener{
            val intent = Intent(this, movie_list_popular::class.java)
            startActivity(intent)
        }
        seeMoreTopRated.setOnClickListener{
            val intent = Intent(this, movie_list_top_rated::class.java)
            startActivity(intent)
        }
//        rv_movies_list_popular.layoutManager =  LinearLayoutManager(this)
//        rv_movies_list_popular.setHasFixedSize(true)
//        getMovieData { movies : List<Movie> ->
//            rv_movies_list_popular.adapter = MovieAdapter(movies)
//        }


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
    private fun getMovieData2(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList2().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }

//    private fun setupSearchView(){
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query, movi
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}