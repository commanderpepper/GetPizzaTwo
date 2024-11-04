package commanderpepper.getpizza.util.di

import commanderpepper.getpizza.util.usecase.PizzaFavAndVenueToPizzaUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaMarkerUIStateUseCase
import org.koin.dsl.module

val utilModule = module {
    single { PizzaFavAndVenueToPizzaUseCase() }
    single { PizzaUseCaseToPizzaMarkerUIStateUseCase() }
}