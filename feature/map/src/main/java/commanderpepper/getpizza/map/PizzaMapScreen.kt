package commanderpepper.getpizza.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaMapScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: PizzaMapScreenViewModel = koinViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    PizzaMapScreen(modifier = modifier, uiState = uiState.value)
}

@Composable
fun PizzaMapScreen(modifier: Modifier, uiState: PizzaMapScreenUIState){
    when(uiState){
        PizzaMapScreenUIState.Error -> {

        }
        PizzaMapScreenUIState.Loading -> {

        }
        is PizzaMapScreenUIState.Success -> {
            PizzaMapScreen(modifier = modifier, pizzaMarkers = uiState.pizzaMarkers)
        }
    }
}

@Composable
fun PizzaMapScreen(modifier: Modifier, pizzaMarkers: List<PizzaMarkerUIState>) {
    val newYork = LatLng(40.71, -74.00)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(newYork, 10f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        pizzaMarkers.forEach { pizzaMarker ->
            MarkerComposable(
                state = rememberMarkerState(position = LatLng(pizzaMarker.lat, pizzaMarker.lng))
            ) {
                PizzaMarkerUI(pizzaMarker)
            }
        }
    }
}

@Preview
@Composable
fun PizzaMapScreenPreview(){
    PizzaMapScreen(modifier = Modifier.fillMaxSize(), pizzaMarkers = emptyList<PizzaMarkerUIState>())
}