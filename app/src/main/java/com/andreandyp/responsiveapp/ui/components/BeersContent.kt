package com.andreandyp.responsiveapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.navigation.BeerDestinations
import com.andreandyp.responsiveapp.ui.state.BeerListState

@Composable
fun BeersContent(
    state: BeerListState,
    navController: NavHostController,
    onClickBeer: (Beer?) -> Unit,
    onScrolledToEnd: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = state) {
        if (state is BeerListState.Success && state.selectedBeer != null) {
            navController.navigate(BeerDestinations.BEER_DETAIL_EMPTY) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = BeerDestinations.LIST,
        modifier = modifier,
    ) {
        composable(BeerDestinations.LIST) {
            BeerListContent(
                state = state,
                onClickBeer = onClickBeer,
                onScrolledToEnd = onScrolledToEnd,
                onRefresh = onRefresh,
            )
        }
        composable(BeerDestinations.BEER_DETAIL_EMPTY) {
            if (state is BeerListState.Success) {
                state.selectedBeer?.let {
                    BeerDetailContent(beer = it)
                }
            }
        }
    }
}
