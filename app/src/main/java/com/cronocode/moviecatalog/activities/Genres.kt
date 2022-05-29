package com.cronocode.moviecatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.activities.genres.*
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_genres.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Genres : AppCompatActivity() {
    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        /**Horror**/
        rv_genre_action.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_action.setHasFixedSize(true)
        getMovieDataByGenres("27") { movies : List<Movie> ->
            rv_genre_action.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Adventure**/
        rv_genre_adventure.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_adventure.setHasFixedSize(true)
        getMovieDataByGenres("12") { movies : List<Movie> ->
            rv_genre_adventure.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Animation**/
        rv_genre_animation.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_animation.setHasFixedSize(true)
        getMovieDataByGenres("16") { movies : List<Movie> ->
            rv_genre_animation.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Comedy**/
        rv_genre_comedy.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_comedy.setHasFixedSize(true)
        getMovieDataByGenres("35") { movies : List<Movie> ->
            rv_genre_comedy.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Crime**/
        rv_genre_crime.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_crime.setHasFixedSize(true)
        getMovieDataByGenres("80") { movies : List<Movie> ->
            rv_genre_crime.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Documentary**/
        rv_genre_documentary.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_documentary.setHasFixedSize(true)
        getMovieDataByGenres("99") { movies : List<Movie> ->
            rv_genre_documentary.adapter = MovieAdapter(movies, "horizontal"){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        /**Drama**/
        rv_genre_drama.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false)
        rv_genre_drama.setHasFixedSize(true)
        getMovieDataByGenres("18") { movies : List<Movie> ->
            rv_genre_drama.adapter = MovieAdapter(movies, "horizontal"){
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

        //GenrePages
        seeMoreHorror.setOnClickListener{
            startActivity(Intent(this, genre_list_horror::class.java))
        }
        seeMoreAdventure.setOnClickListener{
            startActivity(Intent(this, genre_list_adventure::class.java))
        }
        seeMoreAnimation.setOnClickListener{
            startActivity(Intent(this, genre_list_animation::class.java))
        }
        seeMoreComedy.setOnClickListener{
            startActivity(Intent(this, genre_list_comedy::class.java))
        }
        seeMoreCrime.setOnClickListener{
            startActivity(Intent(this, genre_list_crime::class.java))
        }
        seeMoreDocumentary.setOnClickListener{
            startActivity(Intent(this, genre_list_documentary::class.java))
        }
        seeMoreDrama.setOnClickListener{
            startActivity(Intent(this, genre_list_drama::class.java))
        }

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