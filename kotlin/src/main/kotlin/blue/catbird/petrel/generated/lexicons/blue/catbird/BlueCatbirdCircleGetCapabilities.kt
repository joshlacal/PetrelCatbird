// Lexicon: 1, ID: blue.catbird.circle.getCapabilities
// Get capability metadata for Catbird Circles support
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleGetCapabilitiesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.getCapabilities"
}

    @Serializable
    data class BlueCatbirdCircleGetCapabilitiesOutput(
        @SerialName("enabled")
        val enabled: Boolean,        @SerialName("protocolRevision")
        val protocolRevision: String,        @SerialName("supportsImages")
        val supportsImages: Boolean    )

sealed class BlueCatbirdCircleGetCapabilitiesError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleGetCapabilitiesError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleGetCapabilitiesError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleGetCapabilitiesError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleGetCapabilitiesError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleGetCapabilitiesError("UpstreamUnavailable", "")
    }

/**
 * Get capability metadata for Catbird Circles support
 *
 * Endpoint: blue.catbird.circle.getCapabilities
 */
suspend fun BlueCatbirdCircleNamespace.getCapabilities(
): ATProtoResponse<BlueCatbirdCircleGetCapabilitiesOutput> {
    val endpoint = "blue.catbird.circle.getCapabilities"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
