// Lexicon: 1, ID: place.stream.live.getSegments
// Get a list of livestream segments for a user
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamLiveGetSegmentsDefs {
    const val TYPE_IDENTIFIER = "place.stream.live.getSegments"
}

@Serializable
    data class PlaceStreamLiveGetSegmentsParameters(
// The DID of the potentially-following user        @SerialName("userDID")
        val userDID: DID,        @SerialName("limit")
        val limit: Int? = null,        @SerialName("before")
        val before: ATProtocolDate? = null    )

    @Serializable
    data class PlaceStreamLiveGetSegmentsOutput(
        @SerialName("segments")
        val segments: List<PlaceStreamSegmentSegmentView>? = null    )

/**
 * Get a list of livestream segments for a user
 *
 * Endpoint: place.stream.live.getSegments
 */
suspend fun PlaceStreamLiveNamespace.getSegments(
parameters: PlaceStreamLiveGetSegmentsParameters): ATProtoResponse<PlaceStreamLiveGetSegmentsOutput> {
    val endpoint = "place.stream.live.getSegments"

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
