package commanderpepper.getpizza.model.ui

import commanderpepper.getpizza.model.util.SimpleLocation
import kotlinx.serialization.Serializable

@Serializable
data class MapDestination(val latitude: Double, val longitude: Double)

@Serializable
data object FavoritesDestination

@Serializable
data object PermissionsDestination