// Lexicon: 1, ID: blue.catbird.mlsDS.healthCheck
// Simple health/status endpoint for DS-to-DS discovery. Return the health status and federation capabilities of this delivery service. No authentication required.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSHealthCheckDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.healthCheck"
}

    @Serializable
    data class BlueCatbirdMlsDSHealthCheckOutput(
// Service DID of this delivery service        @SerialName("did")
        val did: String,// Server version        @SerialName("version")
        val version: String,// Server uptime in seconds        @SerialName("uptime")
        val uptime: Int,// List of supported federation capabilities        @SerialName("federationCapabilities")
        val federationCapabilities: List<String>    )

/**
 * Simple health/status endpoint for DS-to-DS discovery. Return the health status and federation capabilities of this delivery service. No authentication required.
 *
 * Endpoint: blue.catbird.mlsDS.healthCheck
 */
suspend fun BlueCatbirdMlsDSNamespace.healthCheck(
): ATProtoResponse<BlueCatbirdMlsDSHealthCheckOutput> {
    val endpoint = "blue.catbird.mlsDS.healthCheck"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
