package com.cronocode.moviecatalog.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Genre
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter(
    private val genres : List<Genre>
): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    class GenreViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bindGenre(genre : Genre){
            itemView.genre_title.text = genre.name
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        return (
                GenreViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
                )
                )
    }



    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindGenre(genres[position])
    }

}