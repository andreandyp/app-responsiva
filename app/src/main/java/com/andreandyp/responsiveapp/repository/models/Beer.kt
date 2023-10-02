package com.andreandyp.responsiveapp.repository.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.andreandyp.responsiveapp.database.entities.BeerEntity
import com.andreandyp.responsiveapp.network.BeerNetwork

@Parcelize
data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val firstBrewed: String,
    val imageUrl: String,
    val foodPairing: String
) : Parcelable

fun BeerNetwork.asDomain() = Beer(
    id = this.id,
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    firstBrewed = this.firstBrewed,
    imageUrl = this.imageUrl,
    foodPairing = this.foodPairing.joinToString(",").replace(",", "\n")
)

fun BeerEntity.asDomain() = Beer(
    id = this.id,
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    firstBrewed = this.firstBrewed,
    imageUrl = this.imageUrl,
    foodPairing = this.foodPairing.replace(",", "\n")
)