package com.example.tmdbmovies.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbmovies.R
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.databinding.FragmentDetailsMovieBinding
import com.squareup.picasso.Picasso

class DetailsMovieFragment() : Fragment() {

    private lateinit var binding: FragmentDetailsMovieBinding

    private val viewModel: DetailsMovieFragmentViewModel by lazy {
        ViewModelProvider(this).get(DetailsMovieFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.movie.value = arguments?.getParcelable<Result>(BUNDLE_EXTRA)
        return binding.root

    }

    companion object {

        const val BUNDLE_EXTRA = "film"

        fun newInstance(movie: Bundle): DetailsMovieFragment {
            val fragment = DetailsMovieFragment()
            fragment.arguments = movie
            return fragment
        }
    }
}

class DetailsMovieFragmentViewModel() : ViewModel() {
    val movie = MutableLiveData<Result>()
}
