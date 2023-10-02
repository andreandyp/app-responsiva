package com.andreandyp.responsiveapp.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.andreandyp.responsiveapp.database.BeerDatabase
import com.andreandyp.responsiveapp.database.BeersDao
import com.andreandyp.responsiveapp.database.entities.BeerEntity
import com.andreandyp.responsiveapp.database.entities.asEntity
import com.andreandyp.responsiveapp.network.API
import com.andreandyp.responsiveapp.network.BeerNetwork
import com.andreandyp.responsiveapp.network.Result
import com.andreandyp.responsiveapp.repository.models.Beer
import com.andreandyp.responsiveapp.repository.models.asDomain

class BeerRepository(
    database: BeerDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {
    private val beersDao: BeersDao = database.beersDao()

    override suspend fun getBeersFromInternet(page: String, perPage: String): List<BeerNetwork> =
        withContext(dispatcher) {
            API.beers.getBeers(page, perPage)
        }

    override suspend fun getBeersFromDatabase(): List<BeerEntity> = withContext(dispatcher) {
        beersDao.getBeers()
    }

    override suspend fun getBeers(page: String, forceUpdate: Boolean): Result<List<Beer>> {
        return if (forceUpdate) {
            try {
                val beersNetwork = getBeersFromInternet(page, "20")
                saveBeersFromInternet(beersNetwork)
                val beers = beersNetwork.map { it.asDomain() }
                Result.Success(beers)
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            val beersEntities = getBeersFromDatabase()
            if (beersEntities.isNotEmpty()) {
                val beers = beersEntities.map { it.asDomain() }
                Result.Success(beers)
            } else {
                Result.Success(emptyList())
            }
        }
    }

    override suspend fun saveBeersFromInternet(beers: List<BeerNetwork>) = withContext(dispatcher) {
        val beersEntities = beers.map { it.asEntity() }.toTypedArray()
        beersDao.saveBeers(*beersEntities)
    }
}