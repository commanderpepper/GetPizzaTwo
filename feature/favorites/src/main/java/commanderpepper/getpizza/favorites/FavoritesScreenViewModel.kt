package commanderpepper.getpizza.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.getpizza.local.repo.PizzaRepo
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToFavoritesItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesScreenViewModel(private val repo: PizzaRepo, private val pizzaUseCaseToFavoritesItemUseCase: PizzaUseCaseToFavoritesItemUseCase): ViewModel() {
    val favoritesScreenState = repo.getFavoriteLocations().map { list ->
        if(list.isEmpty()){
            FavoritesScreenState.NoneFound
        }
        else {
            FavoritesScreenState.Success(list.map { pizzaUseCaseToFavoritesItemUseCase(it, null) })
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000L), initialValue = FavoritesScreenState.Loading)
}