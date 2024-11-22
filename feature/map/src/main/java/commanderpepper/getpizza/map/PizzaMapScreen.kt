package commanderpepper.getpizza.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    val pizzaMarkerUIState : MutableState<PizzaMarkerUIState?> = remember { mutableStateOf(null) }
    val showPizzaMarkerInfo : MutableState<Boolean> = remember { mutableStateOf(false) }

    LaunchedEffect(cameraPositionState.position) {
        if(cameraPositionState.isMoving.not()){
            Timber.tag("Humza").d("The camera position is ${cameraPositionState.position.target}")
            val lat = cameraPositionState.position.target.latitude
            val lng = cameraPositionState.position.target.longitude
            onCameraPositionChange(SimpleLocation(lat, lng))
        }
    }

    Box {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = DefaultMapProperties.copy(isMyLocationEnabled = true),
            onMapClick = { _ ->
                showPizzaMarkerInfo.value = false
            }
        ) {
            pizzaMarkers.forEach { pizzaMarker ->
                val state = rememberMarkerState(key = pizzaMarker.id, position = LatLng(pizzaMarker.lat, pizzaMarker.lng))
                MarkerComposable(
                    keys = arrayOf(pizzaMarker.id),
                    tag = pizzaMarker.id,
                    title = pizzaMarker.name,
                    state = state,
                    onClick = { marker ->
                        Timber.tag("Humza").i(marker.title ?: "the tag is null")
                        showPizzaMarkerInfo.value = true
                        pizzaMarkerUIState.value = pizzaMarker
                        true
                    }
                ) {
                    Image(
                        painter = painterResource(if (pizzaMarker.isFavorite) R.drawable.ic_pizza_favorite else R.drawable.ic_pizza_non_favorite),
                        contentDescription = ""
                    )
                }
            }
        }
        if(showPizzaMarkerInfo.value && pizzaMarkerUIState.value != null){
            PizzaMarkerInfo(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                onMapClick = { lat, lng ->

                },
                onFavoriteClick = { id ->

                },
                onSearchClick = { term ->

                },
                pizzaMarkerUIState = pizzaMarkerUIState.value!!
            )
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