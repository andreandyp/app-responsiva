package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews

@Composable
fun BeersExpandedContent(
    state: BeerListState,
    onClickBeer: (Beer) -> Unit,
    onScrolledToEnd: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        BeerListContent(
            state = state,
            onClickBeer = onClickBeer,
            onScrolledToEnd = onScrolledToEnd,
            onRefresh = onRefresh,
            modifier = modifier.then(Modifier.weight(1f)),
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )
        BeerDetailExpandedContent(state, modifier = Modifier.weight(3f))
    }
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
                onClickBeer = {},
                onScrolledToEnd = {},
                onRefresh = {},
            )
        }
    }
}
