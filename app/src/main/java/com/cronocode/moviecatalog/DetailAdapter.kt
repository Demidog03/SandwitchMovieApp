package com.cronocode.moviecatalog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class DetailAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    val listener: (Movie) -> Unit
) :RecyclerView.Adapter<DetailAdapter.MovieDetailViewHolder>() {
    class MovieDetailViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        val movieSrc = view.findViewById<ImageView>(R.id.movie_poster)
        val movieTitle = view.findViewById<TextView>(R.id.title)
        fun bindView(movie: Movie, listener: (Movie) -> Unit){
            Glide.with(itemView).load(IMAGE_BASE + movie.poster).into(movieSrc)
            movieTitle.text = movie.title
            itemView.setOnClickListener{listener(movie)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MovieDetailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}