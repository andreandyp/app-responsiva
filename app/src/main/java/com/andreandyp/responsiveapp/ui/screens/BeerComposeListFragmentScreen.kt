package com.andreandyp.responsiveapp.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.andreandyp.responsiveapp.R
import com.andreandyp.responsiveapp.compose1.BeerComposeDetailFragment
import com.andreandyp.responsiveapp.databinding.FragmentBeerComposeDetailBinding
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.ui.components.BeerListContent
import com.andreandyp.responsiveapp.ui.components.ScreenMessage
import com.andreandyp.responsiveapp.ui.state.BeerListState
import com.andreandyp.responsiveapp.viewmodels.BeerListViewModel

@Composable
fun BeerComposeListFragmentScreen(
    isTablet: Boolean,
    viewModel: BeerListViewModel,
    onNavigatedToBeer: (Beer) -> Unit,
    onError: () -> Unit,
) {
    val state by viewModel.state.observeAsState(initial = BeerListState.Initial)

    LaunchedEffect(state) {
        if (state is BeerListState.NetworkError) {
            onError()
        }
    }

    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        BeerListContent(
            state = state,
            onClickBeer = {
                if (isTablet) {
                    viewModel.onSelectedBeer(it)
                } else {
                    onNavigatedToBeer(it)
                }
            },
            onScrolledToEnd = viewModel::loadMoreBeers,
            onRefresh = viewModel::getBeers,
            modifier = Modifier.weight(1f),
        )
        if (isTablet) {
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            (state as? BeerListState.Success)?.selectedBeer?.let {
                val activity = (LocalContext.current as AppCompatActivity)
                AndroidViewBinding(
                    factory = (FragmentBeerComposeDetailBinding::inflate),
                    update = {
                        val fragment = BeerComposeDetailFragment().apply {
                            arguments = Bundle().apply {
                                putParcelable("beer", it)
                            }
                        }

                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container_view, fragment, "DETAIL")
                            .commit()
                    },
                    modifier = Modifier.weight(3f),
                )
            } ?: run {
                val messageId = if (state is BeerListState.NetworkError) {
                    R.string.no_internet_data
                } else {
                    R.string.details_beer_placeholder
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f),
                ) {
                    ScreenMessage(
                        message = stringResource(id = messageId),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}
