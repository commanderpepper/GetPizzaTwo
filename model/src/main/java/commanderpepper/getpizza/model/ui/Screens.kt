package commanderpepper.getpizza.model.ui

import commanderpepper.getpizza.model.util.SimpleLocation
import kotlinx.serialization.Serializable

@Serializable
data class MapDestination(val simpleLocation: SimpleLocation)

data object FavoritesDestination

data object PermissionsDestination