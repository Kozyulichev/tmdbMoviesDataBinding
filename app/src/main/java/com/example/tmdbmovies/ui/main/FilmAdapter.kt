package com.example.tmdbmovies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.data.Movie
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

const val URL_IMAGE = "https://image.tmdb.org/t/p/w1280_and_h720_bestv2"

class FilmAdapter(private var omItemViewClickListener: (Result) -> Unit) :
    RecyclerView.Adapter<FilmAdapter.Holder>() {

    private var films: List<Result> = listOf()

    fun setFilms(data: Movie) {
        films = data.results
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding?.movie = films[position]
        holder.bind(films[position])

    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var binding: ItemMovieBinding? = DataBindingUtil.bind(itemView!!)

        fun bind(film: Result) {
            itemView.setOnClickListener { omItemViewClickListener.invoke(binding?.movie!!) }
        }
    }
}

