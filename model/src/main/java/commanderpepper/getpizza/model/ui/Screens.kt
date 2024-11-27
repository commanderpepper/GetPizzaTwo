package commanderpepper.getpizza.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class MapDestination(val latitude: Double, val longitude: Double, val userLocationEnabled: Boolean = false)

@Serializable
data object FavoritesDestination

@Serializable
data object PermissionsDestination