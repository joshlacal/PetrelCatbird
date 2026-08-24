// Lexicon: 1, ID: blue.catbird.circle.activateSpace
// Activate or renew access to a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleActivateSpaceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.activateSpace"
}

@Serializable
    data class BlueCatbirdCircleActivateSpaceInput(
        @SerialName("space")
        val space: SpaceRef    )

    @Serializable
    data class BlueCatbirdCircleActivateSpaceOutput(
        @SerialName("accessState")
        val accessState: BlueCatbirdCircleDefsAccessState,        @SerialName("expiresAt")
        val expiresAt: ATProtocolDate? = null    )

sealed class BlueCatbirdCircleActivateSpaceError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleActivateSpaceError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleActivateSpaceError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleActivateSpaceError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleActivateSpaceError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleActivateSpaceError("UpstreamUnavailable", "")
    }

/**
 * Activate or renew access to a Circle Space
 *
 * Endpoint: blue.catbird.circle.activateSpace
 */
suspend fun BlueCatbirdCircleNamespace.activateSpace(
input: BlueCatbirdCircleActivateSpaceInput): ATProtoResponse<BlueCatbirdCircleActivateSpaceOutput> {
    val endpoint = "blue.catbird.circle.activateSpace"

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
