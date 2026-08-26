// Lexicon: 1, ID: blue.catbird.circle.getMedia
// Get media associated with a post in a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleGetMediaDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.getMedia"
}

@Serializable
    data class BlueCatbirdCircleGetMediaParameters(
        @SerialName("space")
        val space: SpaceRef,        @SerialName("did")
        val did: DID,        @SerialName("cid")
        val cid: CID    )

    @Serializable
    data class BlueCatbirdCircleGetMediaOutput(
        @SerialName("data")
        val `data`: ByteArray    )

sealed class BlueCatbirdCircleGetMediaError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleGetMediaError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleGetMediaError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleGetMediaError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleGetMediaError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleGetMediaError("UpstreamUnavailable", "")
    }

/**
 * Get media associated with a post in a Circle Space
 *
 * Endpoint: blue.catbird.circle.getMedia
 */
suspend fun BlueCatbirdCircleNamespace.getMedia(
parameters: BlueCatbirdCircleGetMediaParameters): ATProtoResponse<BlueCatbirdCircleGetMediaOutput> {
    val endpoint = "blue.catbird.circle.getMedia"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "*/*"),
        body = null
    )
}
