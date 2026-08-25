// Lexicon: 1, ID: blue.catbird.circle.retryOperation
// Retry an existing Circle operation
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleRetryOperationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.retryOperation"
}

@Serializable
    data class BlueCatbirdCircleRetryOperationInput(
// Canonical lowercase hyphenated RFC 4122 UUID.        @SerialName("id")
        val id: String    )

    typealias BlueCatbirdCircleRetryOperationOutput = BlueCatbirdCircleDefsOperation

sealed class BlueCatbirdCircleRetryOperationError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleRetryOperationError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleRetryOperationError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleRetryOperationError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleRetryOperationError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleRetryOperationError("UpstreamUnavailable", "")
    }

/**
 * Retry an existing Circle operation
 *
 * Endpoint: blue.catbird.circle.retryOperation
 */
suspend fun BlueCatbirdCircleNamespace.retryOperation(
input: BlueCatbirdCircleRetryOperationInput): ATProtoResponse<BlueCatbirdCircleRetryOperationOutput> {
    val endpoint = "blue.catbird.circle.retryOperation"

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
