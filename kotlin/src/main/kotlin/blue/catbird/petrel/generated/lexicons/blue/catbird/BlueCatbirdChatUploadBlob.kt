// Lexicon: 1, ID: blue.catbird.chat.uploadBlob
// Consume one opaque upload ticket with exact raw ciphertext bytes. Exact hash/size and ticket owner are enforced; same bytes replay succeeds and any changed bytes conflict. The upload remains immutable and unbound until an accepted message or metadata transition binds it. Its uploadedBlobBinding result reports the prepared attachment or metadata purpose but is upload-result transport only and cannot inhabit either signed projection.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatUploadBlobDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.uploadBlob"
}

@Serializable
    data class BlueCatbirdChatUploadBlobParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,        @SerialName("uploadTicket")
        val uploadTicket: String    )

@Serializable
    data class BlueCatbirdChatUploadBlobInput(
        @SerialName("data")
        val `data`: ByteArray    )

    @Serializable
    data class BlueCatbirdChatUploadBlobOutput(
        @SerialName("binding")
        val binding: BlueCatbirdChatDefsUploadedBlobBinding,        @SerialName("uploadedAt")
        val uploadedAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatUploadBlobError(val name: String, val description: String?) {
        object BlobConflict: BlueCatbirdChatUploadBlobError("BlobConflict", "")
        object BlobHashMismatch: BlueCatbirdChatUploadBlobError("BlobHashMismatch", "")
        object BlobSizeMismatch: BlueCatbirdChatUploadBlobError("BlobSizeMismatch", "")
        object CutoverRequired: BlueCatbirdChatUploadBlobError("CutoverRequired", "")
        object InvalidRequest: BlueCatbirdChatUploadBlobError("InvalidRequest", "")
        object UploadTicketExpired: BlueCatbirdChatUploadBlobError("UploadTicketExpired", "")
        object UploadTicketNotFound: BlueCatbirdChatUploadBlobError("UploadTicketNotFound", "")
        object AccountSessionExpired: BlueCatbirdChatUploadBlobError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatUploadBlobError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatUploadBlobError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatUploadBlobError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatUploadBlobError("RateLimited", "")
    }

/**
 * Consume one opaque upload ticket with exact raw ciphertext bytes. Exact hash/size and ticket owner are enforced; same bytes replay succeeds and any changed bytes conflict. The upload remains immutable and unbound until an accepted message or metadata transition binds it. Its uploadedBlobBinding result reports the prepared attachment or metadata purpose but is upload-result transport only and cannot inhabit either signed projection.
 *
 * Endpoint: blue.catbird.chat.uploadBlob
 */
suspend fun BlueCatbirdChatNamespace.uploadBlob(
input: BlueCatbirdChatUploadBlobInput,
    params: BlueCatbirdChatUploadBlobParameters): ATProtoResponse<BlueCatbirdChatUploadBlobOutput> {
    val endpoint = "blue.catbird.chat.uploadBlob"

    // Binary data
    val body = input.data
    val contentType = "application/octet-stream"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = params.toQueryItems()

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
