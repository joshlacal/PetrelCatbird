// Lexicon: 1, ID: blue.catbird.mlsChat.getBlobUsage
// Get blob storage usage for the authenticated user Returns the authenticated user's current blob storage usage, quota, and blob count.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatGetBlobUsageDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.getBlobUsage"
}

    @Serializable
    data class BlueCatbirdMlsChatGetBlobUsageOutput(
// Total bytes used by the user's active blobs        @SerialName("usedBytes")
        val usedBytes: Int,// Maximum allowed bytes (500MB)        @SerialName("quotaBytes")
        val quotaBytes: Int,// Number of active blobs        @SerialName("blobCount")
        val blobCount: Int    )

/**
 * Get blob storage usage for the authenticated user Returns the authenticated user's current blob storage usage, quota, and blob count.
 *
 * Endpoint: blue.catbird.mlsChat.getBlobUsage
 */
suspend fun BlueCatbirdMlsChatNamespace.getBlobUsage(
): ATProtoResponse<BlueCatbirdMlsChatGetBlobUsageOutput> {
    val endpoint = "blue.catbird.mlsChat.getBlobUsage"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
