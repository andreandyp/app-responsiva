package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.DisplayFeature
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

@Composable
fun BeersExpandedContent(
    state: BeerListState,
    displayFeatures: List<DisplayFeature>,
    onClickBeer: (Beer) -> Unit,
    onScrolledToEnd: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TwoPane(
        first = {
            BeerListContent(
                state = state,
                onClickBeer = onClickBeer,
                onScrolledToEnd = onScrolledToEnd,
                onRefresh = onRefresh,
            )
        },
        second = { BeerDetailExpandedContent(state, modifier = Modifier.fillMaxWidth()) },
        displayFeatures = displayFeatures,
        strategy = HorizontalTwoPaneStrategy(splitFraction = 1f / 3f),
        foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
        modifier = modifier,
    )
}

@Preview(widthDp = 1280)
@Composable
private fun BeersExpandedContentPreview() {
    val beerSelected = ComposePreviews.previewBeer.copy(id = 1)
    val beers = List(3) { ComposePreviews.previewBeer }

    ResponsiveAppTheme {
        Surface {
            BeersExpandedContent(
                state = BeerListState.Success(
                    selectedBeer = beerSelected,
                    beers = beers + beerSelected + beers,
                ),
                displayFeatures = emptyList(),
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}
