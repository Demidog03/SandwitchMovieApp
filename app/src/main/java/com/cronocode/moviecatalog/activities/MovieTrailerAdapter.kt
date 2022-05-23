package com.cronocode.moviecatalog.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.MovieVideosResults
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.movie_video_links.view.*

class MovieTrailerAdapter(
    private val context: Context,
    private val movieVideos : List<MovieVideosResults>
): RecyclerView.Adapter<MovieTrailerAdapter.MovieVideoViewHolder>(){
    class MovieVideoViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val URL_BASE = "https://www.youtube.com/watch?v="
        fun bindMovieVideos(videosResults: MovieVideosResults){
            itemView.movie_video_item_link.text = URL_BASE + videosResults.key

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder {
        return MovieVideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_video_links, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return movieVideos.size
    }

    override fun onBindViewHolder(holder: MovieVideoViewHolder, position: Int) {
        holder.bindMovieVideos(movieVideos[position])
    }

}