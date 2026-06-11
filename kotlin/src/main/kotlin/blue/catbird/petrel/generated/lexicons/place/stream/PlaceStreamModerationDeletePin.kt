// Lexicon: 1, ID: place.stream.moderation.deletePin
// Unpin a pinned chat message on behalf of a streamer. Requires 'message.pin' permission. Deletes the place.stream.chat.pinnedRecord from the streamer's repo.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamModerationDeletePinDefs {
    const val TYPE_IDENTIFIER = "place.stream.moderation.deletePin"
}

@Serializable
    data class PlaceStreamModerationDeletePinInput(
// The DID of the streamer.        @SerialName("streamer")
        val streamer: DID,// The AT-URI of the pinned record to delete.        @SerialName("pinUri")
        val pinUri: ATProtocolURI    )

    @Serializable
    class PlaceStreamModerationDeletePinOutput

sealed class PlaceStreamModerationDeletePinError(val name: String, val description: String?) {
        object Unauthorized: PlaceStreamModerationDeletePinError("Unauthorized", "The request lacks valid authentication credentials.")
        object Forbidden: PlaceStreamModerationDeletePinError("Forbidden", "The caller does not have permission to unpin messages for this streamer.")
        object SessionNotFound: PlaceStreamModerationDeletePinError("SessionNotFound", "The streamer's OAuth session could not be found or is invalid.")
    }

/**
 * Unpin a pinned chat message on behalf of a streamer. Requires 'message.pin' permission. Deletes the place.stream.chat.pinnedRecord from the streamer's repo.
 *
 * Endpoint: place.stream.moderation.deletePin
 */
suspend fun PlaceStreamModerationNamespace.deletePin(
input: PlaceStreamModerationDeletePinInput): ATProtoResponse<PlaceStreamModerationDeletePinOutput> {
    val endpoint = "place.stream.moderation.deletePin"

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
