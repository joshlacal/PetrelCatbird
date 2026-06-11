// Lexicon: 1, ID: place.stream.playback.whep
// Play a stream over WebRTC using WHEP.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamPlaybackWhepDefs {
    const val TYPE_IDENTIFIER = "place.stream.playback.whep"
}

@Serializable
    data class PlaceStreamPlaybackWhepParameters(
// The DID of the streamer to play.        @SerialName("streamer")
        val streamer: String,// The rendition of the stream to play.        @SerialName("rendition")
        val rendition: String    )

@Serializable
    data class PlaceStreamPlaybackWhepInput(
        @SerialName("data")
        val `data`: ByteArray    )

    @Serializable
    data class PlaceStreamPlaybackWhepOutput(
        @SerialName("data")
        val `data`: ByteArray    )

sealed class PlaceStreamPlaybackWhepError(val name: String, val description: String?) {
        object Unauthorized: PlaceStreamPlaybackWhepError("Unauthorized", "This user may not play this stream.")
    }

/**
 * Play a stream over WebRTC using WHEP.
 *
 * Endpoint: place.stream.playback.whep
 */
suspend fun PlaceStreamPlaybackNamespace.whep(
input: PlaceStreamPlaybackWhepInput,
    params: PlaceStreamPlaybackWhepParameters): ATProtoResponse<PlaceStreamPlaybackWhepOutput> {
    val endpoint = "place.stream.playback.whep"

    // Blob upload - input.data is ByteArray
    val body = input.data
    val contentType = "*/*"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = params.toQueryItems()

    return client.networkService.performRequest(
        method = "POST",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf(
            "Content-Type" to contentType,
            "Accept" to "*/*"
        ),
        body = body
    )
}
