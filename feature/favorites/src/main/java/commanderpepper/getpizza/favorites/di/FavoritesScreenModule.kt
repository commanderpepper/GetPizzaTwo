package commanderpepper.getpizza.favorites.di

import commanderpepper.getpizza.favorites.FavoritesScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesScreenModule = module {
    viewModel { FavoritesScreenViewModel(get(), get()) }
}