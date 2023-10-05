package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.AllThemesPreviews
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews

@Composable
fun BeerList(
    listState: LazyListState,
    selectedBeer: Beer?,
    beers: List<Beer>,
    onClickBeer: (Beer) -> Unit,
    onScrolledToEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(listState.canScrollForward) {
        if (listState.canScrollBackward && listState.canScrollForward.not()) {
            onScrolledToEnd()
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        items(beers) {
            BeerItem(beer = it, isSelected = it == selectedBeer, onClickBeer = onClickBeer)
        }
        item {
            if (beers.isNotEmpty()) {
                ScreenMessage(
                    message = stringResource(id = R.string.loading_more_beers),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@AllThemesPreviews
@Composable
private fun BeerListPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerList(
                listState = LazyListState(),
                selectedBeer = null,
                beers = List(3) { ComposePreviews.previewBeer },
                onClickBeer = {},
                onScrolledToEnd = {},
            )
        }
    }
}

@AllThemesPreviews
@Composable
private fun BeerListSelectedPreview() {
    val beerSelected = ComposePreviews.previewBeer.copy(id = 1)
    ResponsiveAppTheme {
        Surface {
            BeerList(
                listState = LazyListState(),
                selectedBeer = beerSelected,
                beers = List(3) { ComposePreviews.previewBeer } + beerSelected,
                onClickBeer = {},
                onScrolledToEnd = {},
            )
        }
    }
}
