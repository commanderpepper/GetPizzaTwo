package commanderpepper.getpizza.util.di

import commanderpepper.getpizza.util.usecase.PizzaFavAndVenueToPizzaUseCase
import commanderpepper.getpizza.util.usecase.PizzaMarkerUIStateUseCaseToPizzaUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToFavoritesItemUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaFavUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaMarkerUIStateUseCase
import org.koin.dsl.module

val utilModule = module {
    single { PizzaFavAndVenueToPizzaUseCase() }
    single { PizzaUseCaseToPizzaMarkerUIStateUseCase() }
    single { PizzaMarkerUIStateUseCaseToPizzaUseCase() }
    single { PizzaUseCaseToPizzaFavUseCase() }
    single { PizzaUseCaseToFavoritesItemUseCase() }
}