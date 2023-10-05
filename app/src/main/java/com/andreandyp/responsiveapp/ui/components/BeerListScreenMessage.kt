package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme

@Composable
fun BeerListScreenMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScreenMessage(
            message = message,
            modifier = modifier.then(Modifier.padding(dimensionResource(id = R.dimen.loading_padding)))
        )
    }
}

@Preview
@Composable
private fun BeerListScreenMessagePreview() {
    ResponsiveAppTheme {
        Surface {
            BeerListScreenMessage(
                message = stringResource(id = R.string.empty_db),
            )
        }
    }
}
