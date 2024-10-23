package commanderpepper.getpizza.remote.retrofit

import commanderpepper.getpizza.di.remoteModule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.FileInputStream
import java.util.Properties
import kotlin.test.assertTrue

class FourSquareServiceTest: KoinTest {
    private val fourSquareService: FourSquareService by inject()
    private val latLng = "40.77,-73.97"
    private val categoryId = "4bf58dd8d48988d1ca941735"

    private var clientId = ""
    private var clientSecret = ""

    @Before
    fun setUp(){
        startKoin {
            modules(remoteModule)
        }

        val props = Properties()
        val ins = FileInputStream("api.properties")
        props.load(ins)

        clientId = props.getProperty("FOURSQUARE_CLIENT_ID")
        clientSecret = props.getProperty("FOURSQUARE_CLIENT_SECRET")
        ins.close()
    }

    @Test
    fun getFoursquareData() = runTest {
        val data = fourSquareService.searchForPizzas(
            latLng = latLng,
            clientId = clientId,
            clientSecret = clientSecret,
            categoryId = categoryId
        )
        val venues = data.response.venues
        assertTrue {
            venues.isNotEmpty()
        }
    }
}