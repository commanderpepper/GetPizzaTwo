package commanderpepper.getpizza.model.remote.foursquare

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val pluralName: String,
    val shortName: String,
    val icon: Icon,
    val primary: Boolean,
    val venuePage: VenuePage
)

@Serializable
data class Icon(
    val prefix: String,
    val suffix: String
)

@Serializable
data class LabeledLatLngs(
    val label: String,
    val lat: Float,
    val lng: Float
)

@Serializable
data class Location(
    val address: String? = null,
    val crossStreet: String? = null,
    val lat: Float,
    val lng: Float,
    val labeledLatLngs: List<LabeledLatLngs>? = emptyList(),
    val distance: Int,
    val postalCode: String? = null,
    val cc: String,
    val city: String? = null,
    val state: String? = null,
    val country: String,
    val formattedAddress: List<String>
)

@Serializable
data class Meta(
    val code: Int,
    val requestId: String
)

@Serializable
data class Response(
    val venues: List<Venue>,
    val categories: List<Category>? = null
)

@Serializable
data class SearchResponse(
    val meta: Meta,
    val response: Response
)

@Serializable
data class Venue(
    val id: String,
    val name: String,
    val location: Location
)

@Serializable
data class VenuePage(
    val id: String
)