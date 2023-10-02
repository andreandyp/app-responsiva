package com.andreandyp.responsiveapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.andreandyp.responsiveapp.database.BeerDatabase
import com.andreandyp.responsiveapp.databinding.FragmentBeerListBinding
import com.andreandyp.responsiveapp.network.Result
import com.andreandyp.responsiveapp.repository.BeerRepository
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.viewmodels.BeerListViewModel

class BeerListFragment : Fragment() {
    private lateinit var beerListAdapter: BeerListAdapter
    private lateinit var beerListBinding: FragmentBeerListBinding
    private lateinit var emptyListTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private var twoPane: Boolean = false
    private val beerListViewModel by viewModels<BeerListViewModel> {
        val repository = BeerRepository(BeerDatabase.getDatabase(requireContext()))
        BeerListViewModel.BeerListViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        beerListBinding = FragmentBeerListBinding.inflate(layoutInflater, container, false)
        return beerListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = activity as AppCompatActivity
        parentActivity.supportActionBar?.title = getString(R.string.title_beer_list)

        if (view.findViewById<NestedScrollView>(R.id.beer_detail_container) != null) {
            twoPane = true
        }

        beerListBinding.layoutSwipe.setOnRefreshListener {
            beerListViewModel.getBeers()
        }

        emptyListTextView = view.findViewById(R.id.tv_empty_list)
        recyclerView = view.findViewById(R.id.beer_list)

        setupAdapter(findNavController(), beerListViewModel.beers.value!!)
        setupRecyclerView()
        setupLiveDataObservers()
    }

    private fun setupAdapter(navController: NavController, beers: List<Beer>) {
        beerListAdapter = BeerListAdapter(
            navController,
            requireActivity() as AppCompatActivity,
            twoPane,
            beers
        )
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = beerListAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                loadMoreBeers(recyclerView)
            }
        })
    }

    private fun loadMoreBeers(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val items = layoutManager.itemCount

        if (items == lastPosition + 1) {
            beerListViewModel.loadMoreBeers()
        }
    }

    private fun setupLiveDataObservers() {
        beerListViewModel.beers.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                emptyListTextView.visibility = View.VISIBLE
                emptyListTextView.text = getString(R.string.empty_db)
                recyclerView.visibility = View.GONE
            } else {
                emptyListTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            beerListAdapter.beers = it
            beerListAdapter.notifyDataSetChanged()
        })

        beerListViewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    beerListBinding.layoutSwipe.isRefreshing = true
                    emptyListTextView.text = getString(R.string.loading)
                }
                is Result.Error -> {
                    val noInternetMessage = getString(R.string.no_internet_connection_error)
                    val retryMessage = getString(R.string.get_cache_action)
                    showSnackbar(noInternetMessage, retryMessage) {
                        beerListViewModel.getBeers(false)
                    }
                    emptyListTextView.text = getString(R.string.empty_db)

                    beerListBinding.layoutSwipe.isRefreshing = false
                }
                else -> {
                    beerListBinding.layoutSwipe.isRefreshing = false
                }
            }
        })
    }

    private fun showSnackbar(
        text: String,
        actionText: String?,
        action: ((view: View) -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(beerListBinding.root, text, 3000)
        snackbar.setAction(actionText, action)
        snackbar.show()
    }
}