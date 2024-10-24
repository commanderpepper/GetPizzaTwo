package commanderpepper.getpizza.model.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizzafav")
data class PizzaFav(
    @PrimaryKey
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String = "",
    val name: String = "",
    var favorite: Int = 0
)