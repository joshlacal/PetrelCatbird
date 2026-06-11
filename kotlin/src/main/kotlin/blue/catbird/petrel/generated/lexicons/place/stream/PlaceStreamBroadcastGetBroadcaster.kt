// Lexicon: 1, ID: place.stream.broadcast.getBroadcaster
// Get information about a Streamplace broadcaster.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamBroadcastGetBroadcasterDefs {
    const val TYPE_IDENTIFIER = "place.stream.broadcast.getBroadcaster"
}

@Serializable
    class PlaceStreamBroadcastGetBroadcasterParameters

    @Serializable
    data class PlaceStreamBroadcastGetBroadcasterOutput(
// DID of the Streamplace broadcaster to which this server belongs        @SerialName("broadcaster")
        val broadcaster: DID,// DID of this particular Streamplace server        @SerialName("server")
        val server: DID? = null,// Array of DIDs authorized as admins        @SerialName("admins")
        val admins: List<DID>? = null    )

/**
 * Get information about a Streamplace broadcaster.
 *
 * Endpoint: place.stream.broadcast.getBroadcaster
 */
suspend fun PlaceStreamBroadcastNamespace.getBroadcaster(
parameters: PlaceStreamBroadcastGetBroadcasterParameters): ATProtoResponse<PlaceStreamBroadcastGetBroadcasterOutput> {
    val endpoint = "place.stream.broadcast.getBroadcaster"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
