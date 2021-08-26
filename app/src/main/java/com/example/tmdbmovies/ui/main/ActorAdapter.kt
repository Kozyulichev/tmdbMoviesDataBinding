package com.example.tmdbmovies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.data.Actors
import com.example.tmdbmovies.data.Cast
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.databinding.ItemActorBinding
import com.example.tmdbmovies.databinding.ItemMovieBinding

class ActorAdapter() :
    RecyclerView.Adapter<ActorAdapter.Holder>() {

    private var actors: List<Cast> = listOf()

    fun setFilms(data: Actors) {
        actors = data.cast
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemActorBinding.inflate(inflater, parent, false)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: ActorAdapter.Holder, position: Int) {
        holder.binding?.actor = actors[position]

    }

    override fun getItemCount(): Int {
        return actors.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var binding: ItemActorBinding? = DataBindingUtil.bind(itemView!!)

    }

}