package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme
import com.andreandyp.responsiveapp.ui.utils.AllThemesPreviews
import com.andreandyp.responsiveapp.ui.utils.ComposePreviews

@Composable
fun BeerItem(
    beer: Beer,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClickBeer: (Beer) -> Unit,
) {
    var loading by remember { mutableStateOf(true) }
    val elevation = if (isSelected) 8.dp else 0.dp
    Card(
        modifier = modifier.then(
            Modifier
                .clickable { onClickBeer(beer) }
                .fillMaxWidth()),
        elevation = elevation,
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = beer.imageUrl,
                contentDescription = beer.name,
                placeholder = rememberVectorPainter(image = Icons.Filled.Downloading),
                error = rememberVectorPainter(image = Icons.Filled.CloudOff),
                contentScale = ContentScale.FillHeight,
                colorFilter = if (loading) {
                    ColorFilter.tint(MaterialTheme.colors.onBackground)
                } else {
                    null
                },
                modifier = Modifier
                    .size(72.dp)
                    .padding(all = dimensionResource(id = R.dimen.image_margin)),
                onSuccess = {
                    loading = false
                },
            )
            Column(modifier = Modifier.padding(all = dimensionResource(id = R.dimen.text_margin))) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    text = beer.tagline,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@AllThemesPreviews
@Composable
private fun BeerItemPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerItem(ComposePreviews.previewBeer, false) {}
        }
    }
}

@AllThemesPreviews
@Composable
private fun BeerItemSelectedPreview() {
    ResponsiveAppTheme {
        Surface {
            BeerItem(ComposePreviews.previewBeer, true) {}
        }
    }
}
