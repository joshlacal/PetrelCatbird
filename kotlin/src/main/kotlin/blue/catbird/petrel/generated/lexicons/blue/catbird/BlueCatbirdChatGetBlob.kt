// Lexicon: 1, ID: blue.catbird.chat.getBlob
// Download immutable opaque ciphertext under purpose-specific authorization. An attachment is readable only when its binding seq lies inside the caller's exact application interval; pre-join history and re-add gaps are denied even if the caller is currently a participant. A metadata avatar is readable by any current logical participant from the current metadata snapshot, including a title-only snapshot that reuses an older avatar binding. A completed unbound upload is visible only to its verified owner.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetBlobDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getBlob"
}

@Serializable
    data class BlueCatbirdChatGetBlobParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,        @SerialName("blobId")
        val blobId: String    )

    @Serializable
    data class BlueCatbirdChatGetBlobOutput(
        @SerialName("data")
        val `data`: ByteArray    )

sealed class BlueCatbirdChatGetBlobError(val name: String, val description: String?) {
        object BlobNotFound: BlueCatbirdChatGetBlobError("BlobNotFound", "")
        object CutoverRequired: BlueCatbirdChatGetBlobError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetBlobError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetBlobError("DeviceRevoked", "")
        object NotAuthorized: BlueCatbirdChatGetBlobError("NotAuthorized", "")
        object AccountSessionExpired: BlueCatbirdChatGetBlobError("AccountSessionExpired", "")
        object DeviceBindingMismatch: BlueCatbirdChatGetBlobError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatGetBlobError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatGetBlobError("RateLimited", "")
    }

/**
 * Download immutable opaque ciphertext under purpose-specific authorization. An attachment is readable only when its binding seq lies inside the caller's exact application interval; pre-join history and re-add gaps are denied even if the caller is currently a participant. A metadata avatar is readable by any current logical participant from the current metadata snapshot, including a title-only snapshot that reuses an older avatar binding. A completed unbound upload is visible only to its verified owner.
 *
 * Endpoint: blue.catbird.chat.getBlob
 */
suspend fun BlueCatbirdChatNamespace.getBlob(
parameters: BlueCatbirdChatGetBlobParameters): ATProtoResponse<BlueCatbirdChatGetBlobOutput> {
    val endpoint = "blue.catbird.chat.getBlob"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/octet-stream"),
        body = null
    )
}
