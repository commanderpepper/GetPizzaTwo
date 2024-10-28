package commanderpepper.getpizza.model.feature.map

data class PizzaMarkerUIState(
    val id: String,
    val name: String,
    val address: String?,
    val lat: Double,
    val lng: Double,
    val isFavorite: Boolean = false
)