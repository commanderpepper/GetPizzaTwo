package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.feature.favorites.FavoritesItem
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation

class PizzaUseCaseToFavoritesItemUseCase {
    operator fun invoke(pizzaUseCase: PizzaUseCase, userLocation: SimpleLocation?): FavoritesItem {
        return FavoritesItem(
            id = pizzaUseCase.id,
            name = pizzaUseCase.name,
            address = pizzaUseCase.address,
            distance = null,
            searchTerm = pizzaUseCase.address?.let { pizzaUseCase.name + " " + pizzaUseCase.address } ?: pizzaUseCase.name,
            location = SimpleLocation(latitude = pizzaUseCase.lat, longitude = pizzaUseCase.lng)
        )
    }
}