package commanderpepper.getpizza.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState

@Composable
fun PizzaMarkerInfo(
    modifier: Modifier,
    pizzaMarkerUIState: PizzaMarkerUIState,
    onMapClick: (Double, Double) -> Unit,
    onSearchClick: (String) -> Unit,
    onFavoriteClick: (PizzaMarkerUIState) -> Unit) {
    Column(modifier = modifier.fillMaxWidth().background(color = Color.White).padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Text(pizzaMarkerUIState.name)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { onMapClick(pizzaMarkerUIState.lat, pizzaMarkerUIState.lng) }) {
                Image(painter = painterResource(R.drawable.ic_map), contentDescription = "Go to maps")
            }
            IconButton(onClick = {
                val searchTerm = pizzaMarkerUIState.address?.let { address -> pizzaMarkerUIState.name + " " + address  } ?: pizzaMarkerUIState.name
                onSearchClick(searchTerm) }) {
                Image(painter = painterResource(R.drawable.ic_search), contentDescription = "Search")
            }
            IconButton(onClick = { onFavoriteClick(pizzaMarkerUIState) }) {
                Image(painter = painterResource(R.drawable.ic_favorite), contentDescription = "Favorite")
            }
        }
        if(pizzaMarkerUIState.address != null && pizzaMarkerUIState.address?.isNotEmpty() == true){
            Text(pizzaMarkerUIState.address!!)
        }
    }
}

@Preview
@Composable
fun PizzaMarkerInfoPreview(){
    Box(modifier = Modifier.fillMaxSize()) {
        PizzaMarkerInfo(modifier = Modifier.align(alignment = Alignment.BottomCenter), PizzaMarkerUIStateSample, { _, _ ->}, {}, {})
    }
}

private val PizzaMarkerUIStateSample = PizzaMarkerUIState(
    id = "1",
    name = "This is a sample",
    "1234 Address",
    lat = 1.0,
    lng = 2.0,
    isFavorite = false
)
