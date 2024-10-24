package commanderpepper.getpizza.local.room

import android.content.Context
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import commanderpepper.getpizza.model.local.room.PizzaFav
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class PizzaDatabaseTest{

    private lateinit var dao: PizzaDAO
    private lateinit var db: PizzaDatabase

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = inMemoryDatabaseBuilder(
            context, PizzaDatabase::class.java).build()
        dao = db.pizzaDao()
    }

    @After
    fun cleanUp() = runTest {
        dao.clearTableForTesting()
    }

    @Test
    fun writePizzaFavAndReadList() = runTest {
        val fav = PizzaFav(
            id = "1",
            lat = 12.0,
            lng = 90.5,
            address = "This is a test",
            name = "A test I say",
            favorite = 1
        )
        dao.addPizzaFav(fav)
        val favs = dao.getPizzaFavs()
        assertTrue { favs.isNotEmpty() }
    }

    @Test
    fun writePizzaFavDeleteFavAndReadEmptyList() = runTest {
        val fav = PizzaFav(
            id = "1",
            lat = 12.0,
            lng = 90.5,
            address = "This is a test",
            name = "A test I say",
            favorite = 1
        )
        dao.addPizzaFav(fav)
        val favs = dao.getPizzaFavs()
        assertTrue { favs.isNotEmpty() }
        dao.deletePizzaFav(fav)
        val noFavs = dao.getPizzaFavs()
        assertTrue { noFavs.isEmpty() }
    }
}