// Lexicon: 1, ID: blue.catbird.mlsDS.getFederationMode
// Get the current federation mode (admin only). Return the effective, override, and environment federation mode settings.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSGetFederationModeDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.getFederationMode"
}

    @Serializable
    data class BlueCatbirdMlsDSGetFederationModeOutput(
// Currently effective federation mode        @SerialName("effectiveMode")
        val effectiveMode: String,// Runtime override mode (if set)        @SerialName("overrideMode")
        val overrideMode: String? = null,// Federation mode from environment configuration        @SerialName("envMode")
        val envMode: String    )

/**
 * Get the current federation mode (admin only). Return the effective, override, and environment federation mode settings.
 *
 * Endpoint: blue.catbird.mlsDS.getFederationMode
 */
suspend fun BlueCatbirdMlsDSNamespace.getFederationMode(
): ATProtoResponse<BlueCatbirdMlsDSGetFederationModeOutput> {
    val endpoint = "blue.catbird.mlsDS.getFederationMode"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
