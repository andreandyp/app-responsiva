package com.andreandyp.responsiveapp.ui.state

import com.andreandyp.responsiveapp.repository.models.Beer

sealed class BeerListState {
    object Initial : BeerListState()
    data class Success(
        val selectedBeer: Beer? = null,
        val beers: List<Beer>,
        val loadingMoreBeers: Boolean = false,
    ) : BeerListState()

    object NetworkError : BeerListState()
}
