package com.andreandyp.responsiveapp.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.repository.models.Beer

@Composable
fun BeersTopBar(
    selectedBeer: Beer?,
    showBackIcon: Boolean,
    onClickNavIcon: () -> Unit,
) {
    val textTitle = selectedBeer?.name ?: stringResource(id = R.string.new_compose_title)
    if (showBackIcon) {
        TopAppBar(
            windowInsets = WindowInsets.statusBars,
            title = { Text(text = textTitle) },
            navigationIcon = {
                IconButton(onClick = onClickNavIcon) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            },
        )
    } else {
        TopAppBar(windowInsets = WindowInsets.statusBars, title = { Text(text = textTitle) })
    }
}
