package pe.edu.upc.restaurants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import pe.edu.upc.restaurants.data.entities.Restaurant
import pe.edu.upc.restaurants.data.remote.ApiClient
import pe.edu.upc.restaurants.ui.theme.RestaurantsTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    var restaurants by mutableStateOf(listOf<Restaurant>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRestaurants()
        setContent {
            RestaurantsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RestaurantList(restaurants)
                }
            }
        }
    }

    private fun loadRestaurants() {
        val restaurantInterface = ApiClient.build()
        val fetchRestaurants = restaurantInterface?.fetchRestaurants()

        fetchRestaurants?.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                restaurants = response.body()!!
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {

            }

        }
        )
    }
}

@Composable
fun RestaurantList(restaurants: List<Restaurant>) {
    LazyColumn {
        items(restaurants) { restaurant ->
            RestaurantRow(restaurant)
        }
    }
}

@Composable
fun RestaurantRow(restaurant: Restaurant) {
    Row {
        Image(
            painter = rememberImagePainter(
                data = restaurant.poster
            ),
            contentDescription = "Restaurant photo",
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = restaurant.name, fontWeight = FontWeight.Bold)
            Text(text = restaurant.district)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantsTheme {
    }
}