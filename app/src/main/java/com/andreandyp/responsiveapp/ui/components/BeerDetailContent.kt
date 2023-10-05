package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.AllThemesPreviews
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews

@Composable
fun BeerDetailContent(
    beer: Beer,
    modifier: Modifier = Modifier,
) {
    var loading by remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(
            Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(all = dimensionResource(id = R.dimen.text_margin)),
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = beer.imageUrl,
            contentDescription = beer.name,
            placeholder = rememberVectorPainter(image = Icons.Filled.Downloading),
            error = rememberVectorPainter(image = Icons.Filled.CloudOff),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.size(128.dp),
            colorFilter = if (loading) {
                ColorFilter.tint(MaterialTheme.colors.onBackground)
            } else {
                null
            },
            onSuccess = {
                loading = false
            },
        )
        Text(
            text = beer.name,
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = beer.tagline,
            style = MaterialTheme.typography.h5,
        )
        Text(
            text = beer.description,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.text_margin),
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            Text(
                text = stringResource(id = R.string.first_brewed_date_label),
                style = MaterialTheme.typography.subtitle2,
            )
            Text(
                text = beer.firstBrewed,
                style = MaterialTheme.typography.body2,
            )
        }
        Text(
            text = stringResource(id = R.string.food_pairings_label),
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = beer.foodPairing,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@AllThemesPreviews
@Composable
private fun BeerDetailContentPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerDetailContent(ComposePreviews.previewBeer)
        }
    }
}
