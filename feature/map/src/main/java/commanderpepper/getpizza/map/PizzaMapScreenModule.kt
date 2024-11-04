package commanderpepper.getpizza.map

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pizzaMapScreenModule = module {
    viewModel { PizzaMapScreenViewModel(get(), get()) }
}