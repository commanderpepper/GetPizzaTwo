package commanderpepper.getpizza.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidutil.LocationProvider
import commanderpepper.getpizza.local.repo.PizzaRepo
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToFavoritesItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesScreenViewModel(private val repo: PizzaRepo, private val locationProvider: LocationProvider, private val pizzaUseCaseToFavoritesItemUseCase: PizzaUseCaseToFavoritesItemUseCase): ViewModel() {
    val favoritesScreenState = repo.getFavoriteLocations().map { list ->
        if(list.isEmpty()){
            FavoritesScreenState.NoneFound
        }
        else {
            val userLocation = locationProvider.providerUserLocation()
            FavoritesScreenState.Success(list.map { pizzaUseCaseToFavoritesItemUseCase(it, userLocation) })
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000L), initialValue = FavoritesScreenState.Loading)
}