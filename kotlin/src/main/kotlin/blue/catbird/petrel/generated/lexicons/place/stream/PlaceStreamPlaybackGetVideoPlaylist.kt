// Lexicon: 1, ID: place.stream.playback.getVideoPlaylist
// Get the HLS playlist for a video
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamPlaybackGetVideoPlaylistDefs {
    const val TYPE_IDENTIFIER = "place.stream.playback.getVideoPlaylist"
}

@Serializable
    data class PlaceStreamPlaybackGetVideoPlaylistParameters(
// The AT URI of the video record        @SerialName("uri")
        val uri: ATProtocolURI,// Track number to retrieve        @SerialName("track")
        val track: Int? = null    )

    @Serializable
    data class PlaceStreamPlaybackGetVideoPlaylistOutput(
        @SerialName("data")
        val `data`: ByteArray    )

/**
 * Get the HLS playlist for a video
 *
 * Endpoint: place.stream.playback.getVideoPlaylist
 */
suspend fun PlaceStreamPlaybackNamespace.getVideoPlaylist(
parameters: PlaceStreamPlaybackGetVideoPlaylistParameters): ATProtoResponse<PlaceStreamPlaybackGetVideoPlaylistOutput> {
    val endpoint = "place.stream.playback.getVideoPlaylist"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/vnd.apple.mpegurl"),
        body = null
    )
}
