// Lexicon: 1, ID: blue.catbird.mlsChat.deleteBlob
// Delete an encrypted blob (owner only) Soft-delete an encrypted blob. Only the blob owner can delete it. The MLS message still contains the embed reference, but blob download will return 404 (same UX as expired blobs). Allows users to free quota space before the 90-day TTL.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatDeleteBlobDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.deleteBlob"
}

@Serializable
    data class BlueCatbirdMlsChatDeleteBlobInput(
// The blob identifier to delete        @SerialName("blobId")
        val blobId: String    )

sealed class BlueCatbirdMlsChatDeleteBlobError(val name: String, val description: String?) {
        object BlobNotFound: BlueCatbirdMlsChatDeleteBlobError("BlobNotFound", "Blob does not exist or is not owned by the authenticated user")
    }

/**
 * Delete an encrypted blob (owner only) Soft-delete an encrypted blob. Only the blob owner can delete it. The MLS message still contains the embed reference, but blob download will return 404 (same UX as expired blobs). Allows users to free quota space before the 90-day TTL.
 *
 * Endpoint: blue.catbird.mlsChat.deleteBlob
 */
suspend fun BlueCatbirdMlsChatNamespace.deleteBlob(
input: BlueCatbirdMlsChatDeleteBlobInput): ATProtoResponse<Unit> {
    val endpoint = "blue.catbird.mlsChat.deleteBlob"

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
            "Accept" to "None"
        ),
        body = body
    )
}
