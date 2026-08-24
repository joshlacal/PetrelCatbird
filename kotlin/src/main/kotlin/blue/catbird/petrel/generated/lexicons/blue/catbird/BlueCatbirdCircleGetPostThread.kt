// Lexicon: 1, ID: blue.catbird.circle.getPostThread
// Get a thread of posts within a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleGetPostThreadDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.getPostThread"
}

@Serializable
    data class BlueCatbirdCircleGetPostThreadParameters(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("space")
        val space: SpaceRef,        @SerialName("depth")
        val depth: Int? = null,        @SerialName("parentHeight")
        val parentHeight: Int? = null    )

    @Serializable
    data class BlueCatbirdCircleGetPostThreadOutput(
        @SerialName("thread")
        val thread: AppBskyFeedDefsThreadViewPost,        @SerialName("circle")
        val circle: BlueCatbirdCircleDefsCircleSummary    )

sealed class BlueCatbirdCircleGetPostThreadError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleGetPostThreadError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleGetPostThreadError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleGetPostThreadError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleGetPostThreadError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleGetPostThreadError("UpstreamUnavailable", "")
    }

/**
 * Get a thread of posts within a Circle Space
 *
 * Endpoint: blue.catbird.circle.getPostThread
 */
suspend fun BlueCatbirdCircleNamespace.getPostThread(
parameters: BlueCatbirdCircleGetPostThreadParameters): ATProtoResponse<BlueCatbirdCircleGetPostThreadOutput> {
    val endpoint = "blue.catbird.circle.getPostThread"

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
