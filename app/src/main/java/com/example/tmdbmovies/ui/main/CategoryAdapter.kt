package com.example.tmdbmovies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.data.Category
import com.example.tmdbmovies.data.Genres
import com.example.tmdbmovies.databinding.ItemCategoryBinding

class CategoryAdapter(private var omItemViewClickListener: (Genres) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var films: List<Genres> = listOf()

    fun setFilms(data: Category) {
        films = data.genres
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding?.catgory = films[position]
        holder.bind(films[position])

    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var binding: ItemCategoryBinding? = DataBindingUtil.bind(itemView!!)

        fun bind(genres: Genres) {
            itemView.setOnClickListener { omItemViewClickListener.invoke(binding?.catgory!!) }
        }

    }
}