package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.shared.BeerListSwipeRefresh
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews

@Composable
fun BeerListContent(
    state: BeerListState,
    onClickBeer: (Beer) -> Unit,
    onScrolledToEnd: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRefreshing =
        state is BeerListState.Initial || (state as? BeerListState.Success)?.loadingMoreBeers == true
    val listState = rememberLazyListState()

    BeerListSwipeRefresh(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {
        when (state) {
            BeerListState.Initial -> {
                BeerListScreenMessage(message = stringResource(id = R.string.loading))
            }

            BeerListState.NetworkError -> {
                BeerListScreenMessage(message = stringResource(id = R.string.no_internet_data))
            }

            is BeerListState.Success -> {
                if (state.beers.isEmpty()) {
                    BeerListScreenMessage(message = stringResource(id = R.string.empty_db))
                } else {
                    BeerList(
                        listState = listState,
                        selectedBeer = state.selectedBeer,
                        beers = state.beers,
                        onClickBeer = onClickBeer,
                        onScrolledToEnd = onScrolledToEnd,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BeerListContentInitialPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerListContent(
                state = BeerListState.Initial,
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@Preview
@Composable
private fun BeerListContentNetworkErrorPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerListContent(
                state = BeerListState.NetworkError,
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@Preview
@Composable
private fun BeerListContentSuccessPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerListContent(
                state = BeerListState.Success(beers = List(6) { ComposePreviews.previewBeer }),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}

@Preview
@Composable
private fun BeerListContentSuccessEmptyPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerListContent(
                state = BeerListState.Success(beers = emptyList()),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}
