// Lexicon: 1, ID: blue.catbird.chat.prepareBlobUpload
// Reserve opaque blob quota for the signing DID/device and return one bounded one-time upload ticket. The signed projection binds blob UUID, exact active coordinate, ciphertext SHA-256/size/purpose, owner, and idempotency. After upload the blob is completed and unbound; it may be bound only by that same owner device under exact descriptor/hash/size/purpose equality. Unbound uploads expire and release quota.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatPrepareBlobUploadDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.prepareBlobUpload"
}

@Serializable
    data class BlueCatbirdChatPrepareBlobUploadInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedBlobUploadPreparation    )

    @Serializable
    data class BlueCatbirdChatPrepareBlobUploadOutput(
        @SerialName("upload")
        val upload: BlueCatbirdChatDefsBlobUploadView    )

sealed class BlueCatbirdChatPrepareBlobUploadError(val name: String, val description: String?) {
        object BlobAlreadyExists: BlueCatbirdChatPrepareBlobUploadError("BlobAlreadyExists", "")
        object BlobQuotaExceeded: BlueCatbirdChatPrepareBlobUploadError("BlobQuotaExceeded", "")
        object ConversationNotFound: BlueCatbirdChatPrepareBlobUploadError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatPrepareBlobUploadError("CutoverRequired", "")
        object DeviceNotLeaf: BlueCatbirdChatPrepareBlobUploadError("DeviceNotLeaf", "")
        object DeviceNotRegistered: BlueCatbirdChatPrepareBlobUploadError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatPrepareBlobUploadError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatPrepareBlobUploadError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatPrepareBlobUploadError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatPrepareBlobUploadError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatPrepareBlobUploadError("InvalidSignature", "")
        object NotAuthorized: BlueCatbirdChatPrepareBlobUploadError("NotAuthorized", "")
        object StaleCoordinates: BlueCatbirdChatPrepareBlobUploadError("StaleCoordinates", "")
    }

/**
 * Reserve opaque blob quota for the signing DID/device and return one bounded one-time upload ticket. The signed projection binds blob UUID, exact active coordinate, ciphertext SHA-256/size/purpose, owner, and idempotency. After upload the blob is completed and unbound; it may be bound only by that same owner device under exact descriptor/hash/size/purpose equality. Unbound uploads expire and release quota.
 *
 * Endpoint: blue.catbird.chat.prepareBlobUpload
 */
suspend fun BlueCatbirdChatNamespace.prepareBlobUpload(
input: BlueCatbirdChatPrepareBlobUploadInput): ATProtoResponse<BlueCatbirdChatPrepareBlobUploadOutput> {
    val endpoint = "blue.catbird.chat.prepareBlobUpload"

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
