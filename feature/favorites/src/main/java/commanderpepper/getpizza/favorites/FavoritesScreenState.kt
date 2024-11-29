package commanderpepper.getpizza.favorites

import commanderpepper.getpizza.model.feature.favorites.FavoritesItem

sealed class FavoritesScreenState {
    data object Loading: FavoritesScreenState()
    data object NoneFound: FavoritesScreenState()
    data class Success(val favorites: List<FavoritesItem>): FavoritesScreenState()
}