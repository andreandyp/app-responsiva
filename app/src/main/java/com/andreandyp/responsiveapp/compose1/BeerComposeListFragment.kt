package com.andreandyp.responsiveapp.compose1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.database.BeerDatabase
import com.andreandyp.responsiveapp.repository.BeerRepository
import com.andreandyp.responsiveapp.ui.screens.BeerComposeListFragmentScreen
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.viewmodels.BeerListViewModel

class BeerComposeListFragment : Fragment() {
    private val beerListViewModel by viewModels<BeerListViewModel> {
        val repository = BeerRepository(BeerDatabase.getDatabase(requireContext()))
        BeerListViewModel.BeerListViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val isTablet = resources.getBoolean(R.bool.is_tablet)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                ResponsiveAppTheme {
                    Surface {
                        BeerComposeListFragmentScreen(
                            isTablet = isTablet,
                            viewModel = beerListViewModel,
                            onNavigatedToBeer = {
                                val directions =
                                    BeerComposeListFragmentDirections.seeBeerDetails(it)
                                findNavController().navigate(directions)
                            },
                            onError = ::onError,
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = activity as AppCompatActivity
        parentActivity.supportActionBar?.title = getString(R.string.fragment_compose_title)
    }

    private fun onError() {
        val noInternetMessage =
            getString(R.string.no_internet_connection_error)
        val retryMessage = getString(R.string.get_cache_action)
        showSnackbar(noInternetMessage, retryMessage) {
            beerListViewModel.getBeers(false)
        }
    }

    private fun showSnackbar(
        text: String,
        actionText: String?,
        action: ((view: View) -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(requireView(), text, 3000)
        snackbar.setAction(actionText, action)
        snackbar.show()
    }
}
