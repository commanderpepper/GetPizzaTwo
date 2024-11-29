package commanderpepper.getpizza.model.feature.favorites

import commanderpepper.getpizza.model.util.SimpleLocation

data class FavoritesItem(val id: String, val name: String, val address: String?, val distance: String?, val location: SimpleLocation, val searchTerm: String)