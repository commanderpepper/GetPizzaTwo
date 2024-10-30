package commanderpepper.getpizza.map

import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState

sealed class PizzaMapScreenUIState {
    data object Loading: PizzaMapScreenUIState()
    data object Error: PizzaMapScreenUIState()
    data class Success(val pizzaMarkers: List<PizzaMarkerUIState>): PizzaMapScreenUIState()
}