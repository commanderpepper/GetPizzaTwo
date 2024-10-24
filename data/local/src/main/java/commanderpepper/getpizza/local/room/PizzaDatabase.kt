package commanderpepper.getpizza.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import commanderpepper.getpizza.model.local.room.PizzaFav

@Database(entities = [PizzaFav::class], version = 1)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun pizzaDao(): PizzaDAO
}