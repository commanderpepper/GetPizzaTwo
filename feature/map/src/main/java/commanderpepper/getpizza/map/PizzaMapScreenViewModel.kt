package commanderpepper.getpizza.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.androidutil.LocationProvider
import commanderpepper.getpizza.local.repo.PizzaRepo
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.ui.MapDestination
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation
import commanderpepper.getpizza.util.usecase.PizzaMarkerUIStateUseCaseToPizzaUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaMarkerUIStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PizzaMapScreenViewModel(
    private val repo: PizzaRepo,
    private val pizzaUseCaseToPizzaMarkerUIStateUseCase: PizzaUseCaseToPizzaMarkerUIStateUseCase,
    private val pizzaMarkerUIStateUseCaseToPizzaUseCase: PizzaMarkerUIStateUseCaseToPizzaUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val favorites = repo.getFavoriteLocations()
    private val locations = MutableStateFlow<List<PizzaUseCase>>(emptyList())
    private val userLocation = savedStateHandle.toRoute<MapDestination>().let { SimpleLocation(latitude = it.latitude, longitude = it.longitude) }

    val uiState = favorites.combine(locations){ favs, locations ->
        if(locations.isEmpty()){
            PizzaMapScreenUIState.Error
        }
        else {
            PizzaMapScreenUIState.Success(
                pizzaMarkers = locations.map { pizzaUseCaseToPizzaMarkerUIStateUseCase(it) },
                pizzaFavoriteMarkers = favs.map { pizzaUseCaseToPizzaMarkerUIStateUseCase(it) },
                simpleLocation = userLocation
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = PizzaMapScreenUIState.Loading
    )

    init {
        viewModelScope.launch {
            updateLocation(userLocation)
        }
    }

    fun updateLocation(simpleLocation: SimpleLocation){
        viewModelScope.launch {
            val markers = repo.getLocations(latitude = simpleLocation.latitude, longitude = simpleLocation.longitude)
            locations.value = markers
        }
    }

    fun onFavoriteClick(pizzaMarkerUIState: PizzaMarkerUIState){
        viewModelScope.launch {
            repo.addFavorite(pizzaMarkerUIStateUseCaseToPizzaUseCase(pizzaMarkerUIState.copy(isFavorite = pizzaMarkerUIState.isFavorite.not())))
        }
    }
}