package pe.edu.upc.restaurants.data.entities

import com.google.gson.annotations.SerializedName

class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val district: String,
    val poster: String,
    val rating: Float
)