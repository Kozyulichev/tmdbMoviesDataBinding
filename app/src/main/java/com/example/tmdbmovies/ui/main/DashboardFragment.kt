package com.example.tmdbmovies.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.R
import com.example.tmdbmovies.data.*
import com.example.tmdbmovies.databinding.FragmentDashboardBinding
import com.example.tmdbmovies.databinding.FragmentNotificationsBinding
import com.example.tmdbmovies.databinding.MainFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private val lambdaNewViewInternet = { film: Genres ->
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(CategoryFilmFragment.BUNDLE_EXTRA_CATEGORY, film)
            manager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_activity_main,
                    CategoryFilmFragment.newInstance(bundle)
                )
                .addToBackStack("null")
                .commitAllowingStateLoss()
        }
    }

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by lazy {
        ViewModelProvider(this).get(DashboardViewModel::class.java)
    }
    lateinit var recyclerView: RecyclerView
    var recyclerAdapter = CategoryAdapter(lambdaNewViewInternet)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = dashboardViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.getLiveData().observe(viewLifecycleOwner, { initRecyclerView(it) })
    }

    private fun initRecyclerView(category: Category) {
        recyclerView = view?.findViewById(R.id.recycler_category)!!
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerAdapter.setFilms(category)
        recyclerView.adapter = recyclerAdapter
    }
}

class DashboardViewModel(private val liveData: MutableLiveData<Category> = MutableLiveData()) :
    ViewModel() {
    fun getLiveData() = liveData
    private val scope = CoroutineScope(Dispatchers.IO)
    private val repository = RepositoryImpl()

    init {
        loadCategory()
    }

    private fun loadCategory() {
        scope.launch {
            val categoryData = repository.getCategory("ru-RU")
            liveData.postValue(categoryData)
        }
    }
}
