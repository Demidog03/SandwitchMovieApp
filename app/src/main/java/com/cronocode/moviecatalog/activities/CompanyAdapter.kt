package com.cronocode.moviecatalog.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.models.Company
import kotlinx.android.synthetic.main.company_item.view.*

class CompanyAdapter(
    private val companies : List<Company>
): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {
    class CompanyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/original/"
        fun bindCompany(company : Company){
            itemView.company_title.text = company.name
            Glide.with(itemView).load(IMAGE_BASE + company.logo).into(itemView.company_poster)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyViewHolder {
        return (
            CompanyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.company_item, parent, false)
            )
        )
    }



    override fun getItemCount(): Int = companies.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bindCompany(companies[position])
    }

}