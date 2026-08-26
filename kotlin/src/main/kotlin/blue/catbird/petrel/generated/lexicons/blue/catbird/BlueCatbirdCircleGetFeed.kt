// Lexicon: 1, ID: blue.catbird.circle.getFeed
// Get feed items from one Circle Space or unified Circles feed
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleGetFeedDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.getFeed"
}

@Serializable
    data class BlueCatbirdCircleGetFeedParameters(
        @SerialName("space")
        val space: SpaceRef? = null,        @SerialName("limit")
        val limit: Int? = null,        @SerialName("cursor")
        val cursor: String? = null    )

    @Serializable
    data class BlueCatbirdCircleGetFeedOutput(
        @SerialName("feed")
        val feed: List<BlueCatbirdCircleDefsFeedItem>,        @SerialName("cursor")
        val cursor: String? = null    )

sealed class BlueCatbirdCircleGetFeedError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleGetFeedError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleGetFeedError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleGetFeedError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleGetFeedError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleGetFeedError("UpstreamUnavailable", "")
    }

/**
 * Get feed items from one Circle Space or unified Circles feed
 *
 * Endpoint: blue.catbird.circle.getFeed
 */
suspend fun BlueCatbirdCircleNamespace.getFeed(
parameters: BlueCatbirdCircleGetFeedParameters): ATProtoResponse<BlueCatbirdCircleGetFeedOutput> {
    val endpoint = "blue.catbird.circle.getFeed"

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
