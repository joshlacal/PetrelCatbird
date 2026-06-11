// Lexicon: 1, ID: place.stream.server.getServerTime
// Get the current server time for client clock synchronization
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamServerGetServerTimeDefs {
    const val TYPE_IDENTIFIER = "place.stream.server.getServerTime"
}

@Serializable
    class PlaceStreamServerGetServerTimeParameters

    @Serializable
    data class PlaceStreamServerGetServerTimeOutput(
// Current server time in RFC3339 format        @SerialName("serverTime")
        val serverTime: ATProtocolDate    )

/**
 * Get the current server time for client clock synchronization
 *
 * Endpoint: place.stream.server.getServerTime
 */
suspend fun PlaceStreamServerNamespace.getServerTime(
parameters: PlaceStreamServerGetServerTimeParameters): ATProtoResponse<PlaceStreamServerGetServerTimeOutput> {
    val endpoint = "place.stream.server.getServerTime"

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
