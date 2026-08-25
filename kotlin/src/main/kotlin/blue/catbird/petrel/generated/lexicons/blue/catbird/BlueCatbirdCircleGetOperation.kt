// Lexicon: 1, ID: blue.catbird.circle.getOperation
// Get the status of a Circle operation
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleGetOperationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.getOperation"
}

@Serializable
    data class BlueCatbirdCircleGetOperationParameters(
// Canonical lowercase hyphenated RFC 4122 UUID.        @SerialName("id")
        val id: String    )

    typealias BlueCatbirdCircleGetOperationOutput = BlueCatbirdCircleDefsOperation

sealed class BlueCatbirdCircleGetOperationError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleGetOperationError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleGetOperationError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleGetOperationError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleGetOperationError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleGetOperationError("UpstreamUnavailable", "")
    }

/**
 * Get the status of a Circle operation
 *
 * Endpoint: blue.catbird.circle.getOperation
 */
suspend fun BlueCatbirdCircleNamespace.getOperation(
parameters: BlueCatbirdCircleGetOperationParameters): ATProtoResponse<BlueCatbirdCircleGetOperationOutput> {
    val endpoint = "blue.catbird.circle.getOperation"

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
