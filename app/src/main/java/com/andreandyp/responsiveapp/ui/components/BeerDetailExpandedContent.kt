package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews
import com.andreandyp.responsiveapp.ui.utils.PixelTabletPreview

@Composable
fun BeerDetailExpandedContent(state: BeerListState, modifier: Modifier = Modifier) {
    (state as? BeerListState.Success)?.selectedBeer?.let {
        BeerDetailContent(beer = it, modifier = modifier)
    } ?: run {
        val messageId = if (state is BeerListState.NetworkError) {
            R.string.no_internet_data
        } else {
            R.string.details_beer_placeholder
        }
        Box(
            modifier = modifier.then(Modifier.fillMaxHeight())
        ) {
            ScreenMessage(
                message = stringResource(id = messageId),
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@PixelTabletPreview
@Composable
private fun BeerDetailExpandedContentPlaceholderPreview() {
    val beers = List(6) { ComposePreviews.previewBeer }
    ResponsiveAppTheme {
        Surface {
            BeerDetailExpandedContent(BeerListState.Success(selectedBeer = null, beers = beers))
        }
    }
}

@PixelTabletPreview
@Composable
private fun BeerDetailExpandedContentSelectedPreview() {
    val selectedBeer = ComposePreviews.previewBeer.copy(id = 7)
    val beers = List(6) { ComposePreviews.previewBeer } + selectedBeer
    ResponsiveAppTheme {
        Surface {
            BeerDetailExpandedContent(
                BeerListState.Success(
                    selectedBeer = selectedBeer,
                    beers = beers
                )
            )
        }
    }
}
