package com.andreandyp.responsiveapp.repository

import com.andreandyp.responsiveapp.database.entities.BeerEntity
import com.andreandyp.responsiveapp.network.BeerNetwork
import com.andreandyp.responsiveapp.network.Result

interface Repository {
    suspend fun getBeersFromInternet(page: String, perPage: String): List<BeerNetwork>
    suspend fun getBeersFromDatabase(): List<BeerEntity>
    suspend fun getBeers(page: String, forceUpdate: Boolean): Result<Any>
    suspend fun saveBeersFromInternet(beers: List<BeerNetwork>)
}