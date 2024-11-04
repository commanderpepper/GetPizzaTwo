package commanderpepper.getpizza.model.usecase

data class PizzaUseCase(
    val id: String,
    val name: String,
    val address: String? = null,
    val lat: Double,
    val lng: Double,
    val isFavorite: Boolean
)
