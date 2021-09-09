package pe.edu.upc.restaurants.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val API_BASE_URL = "https://upc-pre-202101-si672-final.herokuapp.com/"
    var restaurantInterface: RestaurantInterface? = null

    fun build(): RestaurantInterface? {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var retrofit: Retrofit = builder.build()
        restaurantInterface = retrofit.create(RestaurantInterface::class.java)
        return restaurantInterface as RestaurantInterface
    }
}