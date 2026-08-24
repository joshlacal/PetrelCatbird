// Lexicon: 1, ID: blue.catbird.circle.createCircle
// Create a new Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleCreateCircleDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.createCircle"
}

@Serializable
    data class BlueCatbirdCircleCreateCircleInput(
        @SerialName("name")
        val name: String,        @SerialName("memberDids")
        val memberDids: List<DID>    )

    typealias BlueCatbirdCircleCreateCircleOutput = BlueCatbirdCircleDefsOperation

sealed class BlueCatbirdCircleCreateCircleError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleCreateCircleError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleCreateCircleError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleCreateCircleError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleCreateCircleError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleCreateCircleError("UpstreamUnavailable", "")
    }

/**
 * Create a new Circle Space
 *
 * Endpoint: blue.catbird.circle.createCircle
 */
suspend fun BlueCatbirdCircleNamespace.createCircle(
input: BlueCatbirdCircleCreateCircleInput): ATProtoResponse<BlueCatbirdCircleCreateCircleOutput> {
    val endpoint = "blue.catbird.circle.createCircle"

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
