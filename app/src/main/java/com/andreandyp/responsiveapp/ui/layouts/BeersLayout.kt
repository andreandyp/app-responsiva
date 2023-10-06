package com.andreandyp.responsiveapp.ui.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.components.BeersContent
import com.andreandyp.responsiveapp.ui.components.BeersExpandedContent
import com.andreandyp.responsiveapp.ui.components.BeersTopBar
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews
import com.andreandyp.responsiveapp.ui.utils.PixelPhonePreview
import com.andreandyp.responsiveapp.ui.utils.PixelTabletPreview

@Composable
fun BeersLayout(
    state: BeerListState,
    isExpanded: Boolean,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    onClickBeer: (Beer?) -> Unit,
    onScrolledToEnd: () -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            val beer = (state as? BeerListState.Success)?.selectedBeer
            BeersTopBar(
                selectedBeer = beer,
                showBackIcon = !isExpanded && (state as? BeerListState.Success)?.selectedBeer != null,
                onClickNavIcon = { navController.navigateUp() },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        if (isExpanded) {
            BeersExpandedContent(
                state = state,
                onClickBeer = onClickBeer,
                onScrolledToEnd = onScrolledToEnd,
                onRefresh = onRefresh,
                modifier = Modifier.padding(paddingValues),
            )
        } else {
            BeersContent(
                state = state,
                navController = navController,
                onClickBeer = onClickBeer,
                onScrolledToEnd = onScrolledToEnd,
                onRefresh = onRefresh,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@PixelPhonePreview
@Composable
private fun BeersLayoutPhoneNoBeerPreview() {
    val beers = List(6) { ComposePreviews.previewBeer }

    ResponsiveAppTheme {
        Surface {
            BeersLayout(
                state = BeerListState.Success(beers = beers),
                isExpanded = false,
                snackbarHostState = SnackbarHostState(),
                navController = rememberNavController(),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@PixelTabletPreview
@Composable
private fun BeersLayoutTabletNoBeerPreview() {
    val beers = List(6) { ComposePreviews.previewBeer }

    ResponsiveAppTheme {
        Surface {
            BeersLayout(
                state = BeerListState.Success(beers = beers),
                isExpanded = true,
                snackbarHostState = SnackbarHostState(),
                navController = rememberNavController(),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@PixelPhonePreview
@Composable
private fun BeersLayoutPhoneBeerPreview() {
    val beerSelected = ComposePreviews.previewBeer.copy(id = 1)
    val beers = List(3) { ComposePreviews.previewBeer }

    ResponsiveAppTheme {
        Surface {
            BeersLayout(
                state = BeerListState.Success(beerSelected, beers + beerSelected + beers),
                isExpanded = false,
                snackbarHostState = SnackbarHostState(),
                navController = rememberNavController(),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@PixelTabletPreview
@Composable
private fun BeersLayoutTabletBeerPreview() {
    val beerSelected = ComposePreviews.previewBeer.copy(id = 1)
    val beers = List(3) { ComposePreviews.previewBeer }

    ResponsiveAppTheme {
        Surface {
            BeersLayout(
                state = BeerListState.Success(beerSelected, beers + beerSelected + beers),
                isExpanded = true,
                snackbarHostState = SnackbarHostState(),
                navController = rememberNavController(),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}
