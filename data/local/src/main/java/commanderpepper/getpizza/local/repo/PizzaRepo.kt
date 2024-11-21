package commanderpepper.getpizza.local.repo

import commanderpepper.getpizza.local.BuildConfig
import commanderpepper.getpizza.local.room.PizzaDAO
import commanderpepper.getpizza.model.usecase.PizzaUseCase
import commanderpepper.getpizza.model.util.SimpleLocation
import commanderpepper.getpizza.remote.retrofit.FourSquareService
import commanderpepper.getpizza.util.usecase.PizzaFavAndVenueToPizzaUseCase
import kotlin.math.abs

class PizzaRepo(
    private val pizzaDAO: PizzaDAO,
    private val remoteService: FourSquareService,
    private val pizzaFavAndVenueToPizzaUseCase: PizzaFavAndVenueToPizzaUseCase) {

    private val locations : MutableSet<PizzaUseCase> = mutableSetOf()
    private val threeMileDistanceThreshold = 0.054545454

    suspend fun getLocations(latitude: Double, longitude: Double): List<PizzaUseCase> {
        val latLng = "$latitude,$longitude"
        val venues = remoteService.searchForPizzas(
            latLng = latLng,
            clientSecret = BuildConfig.FOURSQUARE_CLIENT_SECRET,
            clientId = BuildConfig.FOURSQUARE_CLIENT_ID
        ).response.venues.map { venue -> pizzaFavAndVenueToPizzaUseCase(venue) }
        locations.addAll(venues)

        val favs = pizzaDAO.getPizzaFavs()
        val closeLocations = locations.filter { compareLatLng(SimpleLocation(latitude = latitude, longitude = longitude), SimpleLocation(latitude = it.lat, longitude = it.lng)) }
        return closeLocations + favs.map { pizzaFavAndVenueToPizzaUseCase.invoke(it) }.toSet()
    }

    private fun compareLatLng(
        locationOne: SimpleLocation,
        locationTwo: SimpleLocation,
        distance: Double = threeMileDistanceThreshold
    ): Boolean {

        val lat1 = locationOne.latitude
        val lng1 = locationOne.longitude
        val lat2 = locationTwo.latitude
        val lng2 = locationTwo.longitude

        val latDif = abs(lat1 - lat2)
        val lngDif = abs(lng1 - lng2)

        return latDif <= distance || lngDif <= distance
    }
}