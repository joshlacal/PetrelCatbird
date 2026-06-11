// Lexicon: 1, ID: place.stream.live.stopLivestream
// Stop your current livestream, updating your current place.stream.livestream record and ceasing the flow of video.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamLiveStopLivestreamDefs {
    const val TYPE_IDENTIFIER = "place.stream.live.stopLivestream"
}

@Serializable
    class PlaceStreamLiveStopLivestreamInput

    @Serializable
    data class PlaceStreamLiveStopLivestreamOutput(
// The URI of the stopped livestream record.        @SerialName("uri")
        val uri: URI,// The new CID of the stopped livestream record.        @SerialName("cid")
        val cid: CID    )

/**
 * Stop your current livestream, updating your current place.stream.livestream record and ceasing the flow of video.
 *
 * Endpoint: place.stream.live.stopLivestream
 */
suspend fun PlaceStreamLiveNamespace.stopLivestream(
input: PlaceStreamLiveStopLivestreamInput): ATProtoResponse<PlaceStreamLiveStopLivestreamOutput> {
    val endpoint = "place.stream.live.stopLivestream"

    // JSON serialization
    val body = Json.encodeToString(input)
    val contentType = "application/json"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "POST",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf(
            "Content-Type" to contentType,
            "Accept" to "application/json"
        ),
        body = body
    )
}
