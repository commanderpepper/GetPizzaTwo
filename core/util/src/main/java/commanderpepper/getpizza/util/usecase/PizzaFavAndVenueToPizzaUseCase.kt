package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.local.room.PizzaFav
import commanderpepper.getpizza.model.remote.foursquare.Venue
import commanderpepper.getpizza.model.usecase.PizzaUseCase

class PizzaFavAndVenueToPizzaUseCase {
    operator fun invoke(venue: Venue): PizzaUseCase {
        return PizzaUseCase(
            id = venue.id,
            name = venue.name,
            address = venue.location.address,
            lat = venue.location.lat.toDouble(),
            lng = venue.location.lng.toDouble(),
            isFavorite = false
        )
    }

    operator fun invoke(pizzaFav: PizzaFav): PizzaUseCase {
        return PizzaUseCase(
            id = pizzaFav.id,
            name = pizzaFav.name,
            address = pizzaFav.address ,
            lat = pizzaFav.lat,
            lng = pizzaFav.lng,
            isFavorite = pizzaFav.favorite == 1
        )
    }
}