package com.andreandyp.responsiveapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreandyp.responsiveapp.database.entities.BeerEntity

@Dao
interface BeersDao {
    @Query("SELECT * FROM Beers")
    fun getBeers(): List<BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBeers(vararg beers: BeerEntity)
}