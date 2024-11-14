package commanderpepper.getpizza.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.androidutil.LocationProvider
import commanderpepper.getpizza.local.repo.PizzaRepo
import commanderpepper.getpizza.model.ui.MapDestination
import commanderpepper.getpizza.model.util.SimpleLocation
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaMarkerUIStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PizzaMapScreenViewModel(
    private val repo: PizzaRepo,
    private val pizzaUseCaseToPizzaMarkerUIStateUseCase: PizzaUseCaseToPizzaMarkerUIStateUseCase,
    private val locationProvider: LocationProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<PizzaMapScreenUIState> =
        MutableStateFlow(PizzaMapScreenUIState.Loading)
    val uiState: StateFlow<PizzaMapScreenUIState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = PizzaMapScreenUIState.Loading
    )

    init {
        viewModelScope.launch {
            val mapDestination = savedStateHandle.toRoute<MapDestination>()
            val location = SimpleLocation(latitude = mapDestination.latitude, longitude = mapDestination.longitude)
            val markers = repo.getLocations(latitude = location.latitude, longitude = location.longitude).map { pizzaUseCaseToPizzaMarkerUIStateUseCase(it).copy() }
            _uiState.value = if(markers.isEmpty()) PizzaMapScreenUIState.Error else PizzaMapScreenUIState.Success(markers, location)
        }
    }
}