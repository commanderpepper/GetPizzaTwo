package commanderpepper.getpizza.map

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.DefaultMapProperties
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.util.SimpleLocation
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaMapScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: PizzaMapScreenViewModel = koinViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = modifier){
        PizzaMapScreen(modifier = Modifier.fillMaxSize(), uiState = uiState.value)
    }
}

@Composable
fun PizzaMapScreen(modifier: Modifier, uiState: PizzaMapScreenUIState){
    when(uiState){
        PizzaMapScreenUIState.Error -> {

        }
        PizzaMapScreenUIState.Loading -> {
            @Composable
            fun Loading(modifier: Modifier = Modifier){
                CircularProgressIndicator(
                    modifier = modifier.fillMaxSize()
                )
            }
        }
        is PizzaMapScreenUIState.Success -> {
            PizzaMapScreen(modifier = modifier, pizzaMarkers = uiState.pizzaMarkers, location = uiState.simpleLocation)
        }
    }
}

@Composable
fun PizzaMapScreen(modifier: Modifier, pizzaMarkers: List<PizzaMarkerUIState>, location : SimpleLocation) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), 12f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = DefaultMapProperties.copy(isMyLocationEnabled = true)
    ) {
        pizzaMarkers.forEach { pizzaMarker ->
            MarkerComposable(
                tag = pizzaMarker.id,
                title = pizzaMarker.name,
                state = rememberMarkerState(position = LatLng(pizzaMarker.lat, pizzaMarker.lng)),
                onClick = { marker ->
                    Log.i("Humza", marker.tag.toString() ?: "the tag is null")
                    false
                }
            ) {
                Image(
                    painter = painterResource(if (pizzaMarker.isFavorite) R.drawable.ic_pizza_favorite else R.drawable.ic_pizza_non_favorite),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun PizzaMapScreenPreview(){
    PizzaMapScreen(modifier = Modifier.fillMaxSize(), pizzaMarkers = emptyList<PizzaMarkerUIState>(), SimpleLocation(40.77,-73.97))
}