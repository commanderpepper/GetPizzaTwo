package commanderpepper.getpizza.map

import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.util.SimpleLocation

sealed class PizzaMapScreenUIState {
    data object Loading: PizzaMapScreenUIState()
    data object Error: PizzaMapScreenUIState()
    data class Success(val pizzaMarkers: List<PizzaMarkerUIState>, val pizzaFavoriteMarkers: List<PizzaMarkerUIState>, val simpleLocation: SimpleLocation): PizzaMapScreenUIState()
}