package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.local.room.PizzaFav
import commanderpepper.getpizza.model.usecase.PizzaUseCase

class PizzaUseCaseToPizzaFavUseCase {
    operator fun invoke(pizzaUseCase: PizzaUseCase): PizzaFav{
        return PizzaFav(
            id = pizzaUseCase.id,
            name = pizzaUseCase.name,
            lng = pizzaUseCase.lng,
            lat = pizzaUseCase.lat,
            address = pizzaUseCase.address ?: "",
            favorite = if(pizzaUseCase.isFavorite) 1 else 0
        )
    }
}