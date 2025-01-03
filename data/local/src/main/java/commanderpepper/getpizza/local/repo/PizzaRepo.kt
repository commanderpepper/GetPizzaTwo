package commanderpepper.getpizza.local.repo

import commanderpepper.getpizza.local.BuildConfig
import commanderpepper.getpizza.local.room.PizzaDAO
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation
import commanderpepper.getpizza.remote.retrofit.FourSquareService
import commanderpepper.getpizza.util.usecase.CompareSimpleLocationsUseCase
import commanderpepper.getpizza.util.usecase.PizzaFavAndVenueToPizzaUseCase
import commanderpepper.getpizza.util.usecase.PizzaUseCaseToPizzaFavUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PizzaRepo(
    private val pizzaDAO: PizzaDAO,
    private val remoteService: FourSquareService,
    private val pizzaFavAndVenueToPizzaUseCase: PizzaFavAndVenueToPizzaUseCase,
    private val pizzaUseCaseToPizzaFav: PizzaUseCaseToPizzaFavUseCase,
    private val compareSimpleLocationsUseCase: CompareSimpleLocationsUseCase
) {

    private val locations : MutableSet<PizzaUseCase> = mutableSetOf()
    private val threeMileDistanceThreshold = 0.054545454

    fun getFavoriteLocations(): Flow<List<PizzaUseCase>> {
        return pizzaDAO.getPizzaFavsFlow().map { list -> list.map { pizzaFavAndVenueToPizzaUseCase.invoke(it) } }
    }

    suspend fun addFavorite(pizzaUseCase: PizzaUseCase){
        pizzaDAO.addPizzaFav(pizzaUseCaseToPizzaFav(pizzaUseCase))
    }

    suspend fun getLocations(latitude: Double, longitude: Double): List<PizzaUseCase> {
        val latLng = "$latitude,$longitude"
        val venues = remoteService.searchForPizzas(
            latLng = latLng,
            clientSecret = BuildConfig.FOURSQUARE_CLIENT_SECRET,
            clientId = BuildConfig.FOURSQUARE_CLIENT_ID
        ).response.venues.map { venue -> pizzaFavAndVenueToPizzaUseCase(venue) }
        locations.addAll(venues)

        val closeLocations = locations.filter {
            compareSimpleLocationsUseCase(
                SimpleLocation(
                    latitude = latitude,
                    longitude = longitude
                ), SimpleLocation(latitude = it.lat, longitude = it.lng), threeMileDistanceThreshold
            )
        }
        return closeLocations
    }
}