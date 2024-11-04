package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.usecase.PizzaUseCase

class PizzaUseCaseToPizzaMarkerUIStateUseCase {
    operator fun invoke(pizzaUseCase: PizzaUseCase): PizzaMarkerUIState {
        return PizzaMarkerUIState(
            id = pizzaUseCase.id,
            name = pizzaUseCase.name,
            lat = pizzaUseCase.lat,
            lng = pizzaUseCase.lng,
            address = pizzaUseCase.address
        )
    }
}