package commanderpepper.getpizza.remote.retrofit

import commanderpepper.getpizza.model.remote.foursquare.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Date

interface FourSquareService {
    @GET("venues/search")
    suspend fun searchForPizzas(
        @Query("ll")
        latLng: String,
        @Query("categoryId")
        categoryId: String = "4bf58dd8d48988d1ca941735",
        @Query("intent")
        intent: String = "browse",
        @Query("radius")
        radius: Int = 3500,
        @Query("client_id")
        clientId: String,
        @Query("client_secret")
        clientSecret: String,
        @Query("limit")
        limit: Int = 50,
        @Query("v")
        v: String = SimpleDateFormat("yyyyMMdd").format(Date())
    ): SearchResponse
}