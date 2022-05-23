package com.cronocode.moviecatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.services.MovieApiInterface
import com.cronocode.moviecatalog.services.MovieApiService
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_search.*
import kotlinx.android.synthetic.main.movie_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movie_search_activity: AppCompatActivity() {
    lateinit var movieAdapter: MovieAdapter
    lateinit var movies: List<Movie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_search)
        searchList.layoutManager = LinearLayoutManager(this)
        searchList.setHasFixedSize(true)
        val intent = Intent(this, Detail::class.java)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                getMovieDataBySearch("$p0"){movies:List<Movie> ->
                    searchList.adapter = MovieAdapter( movies, "vertical"){
                        intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                        startActivity(intent)
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                getMovieDataBySearch("$p0"){movies:List<Movie> ->
                    searchList.adapter = MovieAdapter( movies, "vertical"){
                        intent.putExtra(MainActivity.INTENT_PARCELABLE, it)
                        startActivity(intent)
                    }
                }
                return true
            }

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

    }


    private fun getMovieDataBySearch(query: String?, callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.searchMovieApi("$query", "da0213edba5ce29d325c43cfec6aeab5").enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }
}