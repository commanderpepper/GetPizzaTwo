package commanderpepper.getpizza.local.room.di

import androidx.room.Room
import commanderpepper.getpizza.local.repo.PizzaRepo
import commanderpepper.getpizza.local.room.PizzaDAO
import commanderpepper.getpizza.local.room.PizzaDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(androidApplication(), PizzaDatabase::class.java, "getPizzaTwo-db").build()
    }
    single<PizzaDAO> {
        val db = get<PizzaDatabase>()
        db.pizzaDao()
    }
    factory {
        PizzaRepo(get(), get(), get(), get())
    }
}