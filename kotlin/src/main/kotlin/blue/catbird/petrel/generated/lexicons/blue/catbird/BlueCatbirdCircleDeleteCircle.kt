// Lexicon: 1, ID: blue.catbird.circle.deleteCircle
// Delete a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleDeleteCircleDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.deleteCircle"
}

@Serializable
    data class BlueCatbirdCircleDeleteCircleInput(
        @SerialName("space")
        val space: SpaceRef    )

    typealias BlueCatbirdCircleDeleteCircleOutput = BlueCatbirdCircleDefsOperation

sealed class BlueCatbirdCircleDeleteCircleError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleDeleteCircleError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleDeleteCircleError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleDeleteCircleError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleDeleteCircleError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleDeleteCircleError("UpstreamUnavailable", "")
    }

/**
 * Delete a Circle Space
 *
 * Endpoint: blue.catbird.circle.deleteCircle
 */
suspend fun BlueCatbirdCircleNamespace.deleteCircle(
input: BlueCatbirdCircleDeleteCircleInput): ATProtoResponse<BlueCatbirdCircleDeleteCircleOutput> {
    val endpoint = "blue.catbird.circle.deleteCircle"

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
