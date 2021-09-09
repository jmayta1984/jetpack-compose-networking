package pe.edu.upc.restaurants.data.remote

import pe.edu.upc.restaurants.data.entities.Restaurant
import retrofit2.Call
import retrofit2.http.GET

interface RestaurantInterface {

    @GET("restaurants")
    fun fetchRestaurants(): Call<List<Restaurant>>
}