package com.example.tmdbmovies.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.R
import com.example.tmdbmovies.data.Movie
import com.example.tmdbmovies.data.RepositoryImpl
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.databinding.MainFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val lambdaNewViewInternet = { film: Result ->
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsMovieFragment.BUNDLE_EXTRA, film)
            manager.beginTransaction()
                .replace(R.id.container, DetailsMovieFragment.newInstance(bundle))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
    lateinit var recyclerView: RecyclerView
    var recyclerAdapter = FilmAdapter(lambdaNewViewInternet)

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { initRecyclerView(it) })
    }

    private fun initRecyclerView(movie: Movie) {
        recyclerView = view?.findViewById(R.id.recycler)!!
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerAdapter.setFilms(movie)
        recyclerView.adapter = recyclerAdapter
    }
}


class MainViewModel(private val liveData: MutableLiveData<Movie> = MutableLiveData()) :
    ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    val movie = MutableLiveData<Movie>()
    private val repository = RepositoryImpl()
    val movieIsLoading = MutableLiveData(false)

    fun getLiveData() = liveData


    init {
        loadMovie()
    }

    private fun loadMovie() {
        scope.launch {
            movieIsLoading.postValue(true)
            val movieData = repository.getMovie("ru-RU")
            liveData.postValue(movieData)
            movie.postValue(movieData)
            movieIsLoading.postValue(false)
        }
    }

}

