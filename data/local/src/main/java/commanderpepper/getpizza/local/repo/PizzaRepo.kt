package commanderpepper.getpizza.local.repo

import commanderpepper.getpizza.local.BuildConfig
import commanderpepper.getpizza.local.room.PizzaDAO
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.remote.retrofit.FourSquareService
import commanderpepper.getpizza.util.usecase.PizzaFavAndVenueToPizzaUseCase

class PizzaRepo(
    private val pizzaDAO: PizzaDAO,
    private val remoteService: FourSquareService,
    private val pizzaFavAndVenueToPizzaUseCase: PizzaFavAndVenueToPizzaUseCase) {

    suspend fun getLocations(latitude: Double, longitude: Double): List<PizzaUseCase> {
        val latLng = "$latitude,$longitude"
        val venues = remoteService.searchForPizzas(
            latLng = latLng,
            clientSecret = BuildConfig.FOURSQUARE_CLIENT_SECRET,
            clientId = BuildConfig.FOURSQUARE_CLIENT_ID
        ).response.venues
        val favs = pizzaDAO.getPizzaFavs()
        return venues.map { venue -> pizzaFavAndVenueToPizzaUseCase(venue) } + favs.map { pizzaFavAndVenueToPizzaUseCase.invoke(it) }.toSet()
    }
}