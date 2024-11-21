package commanderpepper.getpizza.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.DefaultMapProperties
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.util.SimpleLocation
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun PizzaMapScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: PizzaMapScreenViewModel = koinViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = modifier){
        PizzaMapScreen(modifier = Modifier.fillMaxSize(), uiState = uiState.value) { sl ->
            viewModel.updateLocation(sl)
        }
    }
}

@Composable
fun PizzaMapScreen(modifier: Modifier, uiState: PizzaMapScreenUIState, onCameraPositionChange: (SimpleLocation) -> Unit){
    when(uiState){
        PizzaMapScreenUIState.Error -> {

        }
        PizzaMapScreenUIState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Loading()
            }
        }
        is PizzaMapScreenUIState.Success -> {
            PizzaMapScreen(modifier = modifier, pizzaMarkers = uiState.pizzaMarkers, location = uiState.simpleLocation, onCameraPositionChange)
        }
    }
}

@Composable
fun PizzaMapScreen(modifier: Modifier, pizzaMarkers: List<PizzaMarkerUIState>, location : SimpleLocation, onCameraPositionChange: (SimpleLocation) -> Unit) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), 12f)
    }
    LaunchedEffect(cameraPositionState.position) {
        if(cameraPositionState.isMoving.not()){
            Timber.tag("Humza").d("The camera position is ${cameraPositionState.position.target}")
            val lat = cameraPositionState.position.target.latitude
            val lng = cameraPositionState.position.target.longitude
//            onCameraPositionChange(SimpleLocation(lat, lng))
        }
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
                    Timber.tag("Humza").i(marker.title ?: "the tag is null")
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
    PizzaMapScreen(modifier = Modifier.fillMaxSize(), pizzaMarkers = emptyList<PizzaMarkerUIState>(), SimpleLocation(40.77,-73.97)){}
}

@Composable
fun Loading(modifier: Modifier = Modifier){
    CircularProgressIndicator(modifier)
}