package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.usecase.PizzaUseCase

class PizzaMarkerUIStateUseCaseToPizzaUseCase {
    operator fun invoke(pizzaMarkerUIState: PizzaMarkerUIState): PizzaUseCase {
        return PizzaUseCase(
            id = pizzaMarkerUIState.id,
            name = pizzaMarkerUIState.name,
            lat = pizzaMarkerUIState.lat,
            lng = pizzaMarkerUIState.lng,
            address = pizzaMarkerUIState.address,
            isFavorite = pizzaMarkerUIState.isFavorite
        )
    }
}