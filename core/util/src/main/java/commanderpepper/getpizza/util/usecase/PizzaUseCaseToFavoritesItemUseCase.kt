package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.feature.favorites.FavoritesItem
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation
import kotlin.math.abs
import kotlin.math.round

class PizzaUseCaseToFavoritesItemUseCase {
    operator fun invoke(pizzaUseCase: PizzaUseCase, userLocation: SimpleLocation?): FavoritesItem {
        return FavoritesItem(
            id = pizzaUseCase.id,
            name = pizzaUseCase.name,
            address = pizzaUseCase.address,
            distance = userLocation?.let { location -> "${round(abs(location.latitude - pizzaUseCase.lat) * 69f)} miles" },
            searchTerm = pizzaUseCase.address?.let { pizzaUseCase.name + " " + pizzaUseCase.address } ?: pizzaUseCase.name,
            location = SimpleLocation(latitude = pizzaUseCase.lat, longitude = pizzaUseCase.lng)
        )
    }
}