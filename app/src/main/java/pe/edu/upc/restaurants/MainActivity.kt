package pe.edu.upc.restaurants

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import pe.edu.upc.restaurants.data.models.Restaurant
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
                MyApp(restaurants)
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
                Log.d("MainActivity", t.toString())
            }

        })
    }
}

@Composable
fun MyApp(restaurants: List<Restaurant>) {
    Scaffold {
        RestaurantList(restaurants)
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
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            RestaurantImage(
                restaurant = restaurant
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Text(
                    text = restaurant.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = restaurant.district,
                    style = typography.caption
                )
            }
        }
    }
}

@Composable
fun RestaurantImage(restaurant: Restaurant) {
    Image(
        painter = rememberImagePainter(
            data = restaurant.poster,
        ),
        contentDescription = "Restaurant photo",
        modifier = Modifier
            .size(64.dp)
            .clip(shape = RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
    )
}
