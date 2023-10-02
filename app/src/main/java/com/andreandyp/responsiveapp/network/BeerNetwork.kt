package com.andreandyp.responsiveapp.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BeerNetwork(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,

    @Json(name = "first_brewed")
    val firstBrewed: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "food_pairing")
    val foodPairing: List<String>
)