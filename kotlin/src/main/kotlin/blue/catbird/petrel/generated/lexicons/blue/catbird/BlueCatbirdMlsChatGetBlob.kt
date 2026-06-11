// Lexicon: 1, ID: blue.catbird.mlsChat.getBlob
// Download an encrypted blob by ID Fetch an encrypted blob from the blob store. Returns raw encrypted bytes (application/octet-stream). Only active members of the blob's conversation can download it.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatGetBlobDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.getBlob"
}

@Serializable
    data class BlueCatbirdMlsChatGetBlobParameters(
// The blob identifier to download        @SerialName("blobId")
        val blobId: String    )

    @Serializable
    data class BlueCatbirdMlsChatGetBlobOutput(
        @SerialName("data")
        val `data`: ByteArray    )

sealed class BlueCatbirdMlsChatGetBlobError(val name: String, val description: String?) {
        object BlobNotFound: BlueCatbirdMlsChatGetBlobError("BlobNotFound", "Blob does not exist or has expired/been deleted")
        object NotAMember: BlueCatbirdMlsChatGetBlobError("NotAMember", "Requester is not an active member of the blob's conversation")
    }

/**
 * Download an encrypted blob by ID Fetch an encrypted blob from the blob store. Returns raw encrypted bytes (application/octet-stream). Only active members of the blob's conversation can download it.
 *
 * Endpoint: blue.catbird.mlsChat.getBlob
 */
suspend fun BlueCatbirdMlsChatNamespace.getBlob(
parameters: BlueCatbirdMlsChatGetBlobParameters): ATProtoResponse<BlueCatbirdMlsChatGetBlobOutput> {
    val endpoint = "blue.catbird.mlsChat.getBlob"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "*/*"),
        body = null
    )
}
