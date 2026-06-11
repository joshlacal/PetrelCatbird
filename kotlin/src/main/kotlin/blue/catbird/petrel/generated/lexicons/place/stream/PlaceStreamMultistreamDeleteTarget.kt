// Lexicon: 1, ID: place.stream.multistream.deleteTarget
// Delete a target for rebroadcasting a Streamplace stream.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMultistreamDeleteTargetDefs {
    const val TYPE_IDENTIFIER = "place.stream.multistream.deleteTarget"
}

@Serializable
    data class PlaceStreamMultistreamDeleteTargetInput(
// The Record Key of the target to delete.        @SerialName("rkey")
        val rkey: String    )

    @Serializable
    class PlaceStreamMultistreamDeleteTargetOutput

/**
 * Delete a target for rebroadcasting a Streamplace stream.
 *
 * Endpoint: place.stream.multistream.deleteTarget
 */
suspend fun PlaceStreamMultistreamNamespace.deleteTarget(
input: PlaceStreamMultistreamDeleteTargetInput): ATProtoResponse<PlaceStreamMultistreamDeleteTargetOutput> {
    val endpoint = "place.stream.multistream.deleteTarget"

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
