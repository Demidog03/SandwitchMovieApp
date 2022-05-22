package com.cronocode.moviecatalog.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    private val context: Context,
    private val movies : List<Movie>,
    private val type : String,
    val listener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie : Movie, listener: (Movie) -> Unit){
            itemView.movie_title.text = movie.title
            itemView.movie_rating.text = movie.rating
            itemView.movie_release_date.text = movie.release
            Glide.with(itemView).load(IMAGE_BASE + movie.poster).into(itemView.movie_poster)
            itemView.setOnClickListener{listener(movie)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return when (type) {
            "horizontal" -> {
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

                )
            }
            "vertical" -> {
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_list_tem, parent, false)

                )
            }
            "upcoming" -> {
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item_upcoming, parent, false)

                )
            }
            else -> {
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

                )
            }
        }

    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position], listener)
    }
}