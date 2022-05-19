package com.cronocode.moviecatalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.BoolRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    private val movies : List<Movie>, private val type : Boolean
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie : Movie){
            itemView.movie_title.text = movie.title
            itemView.movie_rating.text = movie.rating
            itemView.movie_release_date.text = movie.release
            Glide.with(itemView).load(IMAGE_BASE + movie.poster).into(itemView.movie_poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return if(type){
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_list_tem, parent, false)

            )
        } else{
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

            )
        }

    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }
}