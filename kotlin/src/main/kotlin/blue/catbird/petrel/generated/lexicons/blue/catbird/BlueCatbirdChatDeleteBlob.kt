// Lexicon: 1, ID: blue.catbird.chat.deleteBlob
// Delete only the signer-owned completed upload that is still unbound, without requiring current conversation coordinates so removed uploaders can release quota. Bound blobs are immutable history. Upload tickets expire after five minutes; completed-unbound uploads expire after one hour; blob IDs and tombstones are never reused.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatDeleteBlobDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.deleteBlob"
}

@Serializable
    data class BlueCatbirdChatDeleteBlobInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedBlobDeletion    )

    @Serializable
    data class BlueCatbirdChatDeleteBlobOutput(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("deletedAt")
        val deletedAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatDeleteBlobError(val name: String, val description: String?) {
        object BlobBound: BlueCatbirdChatDeleteBlobError("BlobBound", "")
        object BlobNotFound: BlueCatbirdChatDeleteBlobError("BlobNotFound", "")
        object CutoverRequired: BlueCatbirdChatDeleteBlobError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatDeleteBlobError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatDeleteBlobError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatDeleteBlobError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatDeleteBlobError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatDeleteBlobError("InvalidSignature", "")
        object NotAuthorized: BlueCatbirdChatDeleteBlobError("NotAuthorized", "")
        object AccountSessionExpired: BlueCatbirdChatDeleteBlobError("AccountSessionExpired", "")
        object DeviceBindingMismatch: BlueCatbirdChatDeleteBlobError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatDeleteBlobError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatDeleteBlobError("RateLimited", "")
    }

/**
 * Delete only the signer-owned completed upload that is still unbound, without requiring current conversation coordinates so removed uploaders can release quota. Bound blobs are immutable history. Upload tickets expire after five minutes; completed-unbound uploads expire after one hour; blob IDs and tombstones are never reused.
 *
 * Endpoint: blue.catbird.chat.deleteBlob
 */
suspend fun BlueCatbirdChatNamespace.deleteBlob(
input: BlueCatbirdChatDeleteBlobInput): ATProtoResponse<BlueCatbirdChatDeleteBlobOutput> {
    val endpoint = "blue.catbird.chat.deleteBlob"

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
