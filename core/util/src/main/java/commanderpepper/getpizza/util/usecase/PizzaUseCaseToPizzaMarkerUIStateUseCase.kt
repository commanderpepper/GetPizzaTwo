package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation

class PizzaUseCaseToPizzaMarkerUIStateUseCase(private val compareSimpleLocationsUseCase: CompareSimpleLocationsUseCase) {
    operator fun invoke(pizzaUseCase: PizzaUseCase, userLocation: SimpleLocation): PizzaMarkerUIState {
        return PizzaMarkerUIState(
            id = pizzaUseCase.id,
            name = pizzaUseCase.name,
            lat = pizzaUseCase.lat,
            lng = pizzaUseCase.lng,
            address = pizzaUseCase.address,
            isFavorite = pizzaUseCase.isFavorite,
            isVisible = pizzaUseCase.isFavorite || compareSimpleLocationsUseCase(SimpleLocation(latitude = pizzaUseCase.lat, longitude = pizzaUseCase.lng), userLocation, 0.018181818)
        )
    }
}