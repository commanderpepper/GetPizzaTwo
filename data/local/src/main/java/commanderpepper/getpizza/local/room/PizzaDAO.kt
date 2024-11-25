package commanderpepper.getpizza.local.room

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import commanderpepper.getpizza.model.local.room.PizzaFav
import kotlinx.coroutines.flow.Flow

@Dao
interface PizzaDAO  {

    @Query("SELECT * from pizzafav WHERE favorite == 1")
    suspend fun getPizzaFavs(): List<PizzaFav>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPizzaFav(pizzaFav: PizzaFav): Long

    @Delete
    suspend fun deletePizzaFav(pizzaFav: PizzaFav)

    @Query("SELECT * from pizzafav WHERE favorite == 1")
    fun getPizzaFavsFlow(): Flow<List<PizzaFav>>
}