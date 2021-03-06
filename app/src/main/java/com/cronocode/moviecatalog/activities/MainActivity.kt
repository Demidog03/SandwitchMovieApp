package com.cronocode.moviecatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        /**
         * For movie list page
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ///////////
        rv_movies_list.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list.setHasFixedSize(true)
        getMovieData("upcoming") { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies, "upcoming"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }



//        /////////////

        rv_movies_list2.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list2.setHasFixedSize(true)
        getMovieData("top_rated") { movies : List<Movie> ->
            rv_movies_list2.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        ///////////

        rv_movies_list3.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list3.setHasFixedSize(true)
        getMovieData("now_playing") { movies : List<Movie> ->
            rv_movies_list3.adapter = MovieAdapter( movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }
        rv_movies_list4.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_movies_list4.setHasFixedSize(true)
        getMovieData("popular") { movies : List<Movie> ->
            rv_movies_list4.adapter = MovieAdapter( movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }


        /**OnClickListeners */
        seeMoreHorror.setOnClickListener{
            startActivity(Intent(this, movie_list_upcoming::class.java))
        }
        seeMoreAnimation.setOnClickListener{
            startActivity(Intent(this, movie_list_top_rated::class.java))
        }
        seeMoreComedy.setOnClickListener{
            startActivity(Intent(this, movie_list_now_playing::class.java))
        }
        seeMoreAdventure.setOnClickListener{
            startActivity(Intent(this, movie_list_popular::class.java))
        }
        profileBtn.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(this, Profile::class.java)
            startActivity(newIntent)
        })
        searchBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, movie_search_activity::class.java))
        })
        homeBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        })
        btnGoToGenres.setOnClickListener{
            startActivity(Intent(this, Genres::class.java))
        }

    }

    private fun getMovieData( type: String, callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList("$type", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object : Callback<MovieResponse> {
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