package com.andreandyp.responsiveapp.ui.screens

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.ui.layouts.BeersLayout
import com.andreandyp.responsiveapp.ui.navigation.BeerDestinations
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.viewmodels.BeerListViewModel

@Composable
fun BeersScreen(
    isExpanded: Boolean,
    viewModel: BeerListViewModel
) {
    val state by viewModel.state.observeAsState(initial = BeerListState.Initial)
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is BeerListState.NetworkError) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.no_internet_connection_error),
                actionLabel = context.getString(R.string.get_cache_action),
                duration = SnackbarDuration.Indefinite,
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.getBeers(forceUpdate = false)
            }
        }
    }
    LaunchedEffect(key1 = currentDestination) {
        if (currentDestination?.route == BeerDestinations.LIST) {
            viewModel.onSelectedBeer(null)
        }
    }

    BeersLayout(
        state = state,
        isExpanded = isExpanded,
        snackbarHostState = snackbarHostState,
        navController = navController,
        onClickBeer = viewModel::onSelectedBeer,
        onScrolledToEnd = viewModel::loadMoreBeers,
        onRefresh = viewModel::getBeers,
    )
}
