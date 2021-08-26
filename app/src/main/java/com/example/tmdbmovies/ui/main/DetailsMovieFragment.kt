package com.example.tmdbmovies.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.R
import com.example.tmdbmovies.data.Actors
import com.example.tmdbmovies.data.Movie
import com.example.tmdbmovies.data.RepositoryImpl
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.databinding.FragmentDetailsMovieBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsMovieFragment() : Fragment() {

    private lateinit var binding: FragmentDetailsMovieBinding

    private val viewModel: DetailsMovieFragmentViewModel by lazy {
        ViewModelProvider(this).get(DetailsMovieFragmentViewModel::class.java)
    }
    lateinit var recyclerView: RecyclerView
    var recyclerAdapter = ActorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.movie.value = arguments?.getParcelable<Result>(BUNDLE_EXTRA)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { initActorRecycler(it) })
    }

    @SuppressLint("WrongConstant")
    private fun initActorRecycler(actors: Actors) {
        recyclerView = view?.findViewById(R.id.recycler_actors)!!
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        recyclerView.layoutManager = layoutManager
        recyclerAdapter.setFilms(actors)
        recyclerView.adapter = recyclerAdapter
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

class DetailsMovieFragmentViewModel(private val liveData: MutableLiveData<Actors> = MutableLiveData()) :
    ViewModel() {
    val movie = MutableLiveData<Result>()
    private val scope = CoroutineScope(Dispatchers.Main)
    fun getLiveData() = liveData
    private val repository = RepositoryImpl()

    init {
        loadActors()
    }

    private fun loadActors() {
        scope.launch {
            val id = movie.value?.id
            val actorsData = repository.getActors(id, "ru-RU")
            liveData.postValue(actorsData)
        }
    }
}
