package com.example.tmdbmovies.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.R
import com.example.tmdbmovies.data.*
import com.example.tmdbmovies.databinding.FragmentCategoryFilmBinding
import com.example.tmdbmovies.databinding.FragmentDetailsMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoryFilmFragment : Fragment() {

    private val lambdaNewViewInternet = { film: Result ->
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsMovieFragment.BUNDLE_EXTRA, film)
            manager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_activity_main,
                    DetailsMovieFragment.newInstance(bundle)
                )
                .addToBackStack("null")
                .commitAllowingStateLoss()
        }
    }

    private lateinit var binding: FragmentCategoryFilmBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(CategoryFilmViewModel::class.java)
    }
    lateinit var recyclerView: RecyclerView
    var recyclerAdapter = FilmAdapter(lambdaNewViewInternet)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryFilmBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.category.value =
            arguments?.getParcelable<Genres>(CategoryFilmFragment.BUNDLE_EXTRA_CATEGORY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { initRecyclerView(it) })

    }

    private fun initRecyclerView(movie: Movie) {
        recyclerView = view?.findViewById(R.id.recycler_category_film)!!
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerAdapter.setFilms(movie)
        recyclerView.adapter = recyclerAdapter
    }

    companion object {

        const val BUNDLE_EXTRA_CATEGORY = "category"

        fun newInstance(category: Bundle): CategoryFilmFragment {
            val fragment = CategoryFilmFragment()
            fragment.arguments = category
            return fragment
        }
    }
}

class CategoryFilmViewModel(private val liveData: MutableLiveData<Movie> = MutableLiveData()) :
    ViewModel() {
    val category = MutableLiveData<Genres>()
    val scope = CoroutineScope(Dispatchers.IO)
    fun getLiveData() = liveData
    private val repository = RepositoryImpl()

    init {
        loadMovieByCategory()
    }

    fun loadMovieByCategory() {
        scope.launch {
            val id = category.value?.id
            val movie = repository.getMovieByCategory("ru-RU", id!!)
            liveData.postValue(movie)
        }
    }

}