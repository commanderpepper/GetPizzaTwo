package commanderpepper.getpizza.util.usecase

import commanderpepper.getpizza.model.util.SimpleLocation
import kotlin.math.abs

class CompareSimpleLocationsUseCase {
    operator fun invoke(locationOne: SimpleLocation, locationTwo: SimpleLocation, distance: Double): Boolean {
        val lat1 = locationOne.latitude
        val lng1 = locationOne.longitude
        val lat2 = locationTwo.latitude
        val lng2 = locationTwo.longitude

        val latDif = abs(lat1 - lat2)
        val lngDif = abs(lng1 - lng2)

        return latDif <= distance || lngDif <= distance
    }
}