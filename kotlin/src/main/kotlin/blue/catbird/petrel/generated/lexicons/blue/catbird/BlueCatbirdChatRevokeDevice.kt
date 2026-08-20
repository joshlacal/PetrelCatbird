// Lexicon: 1, ID: blue.catbird.chat.revokeDevice
// Signed lost-device or self revocation using authoritative own-device inventory CAS fields. Revocation releases packages, reservations, and recovery requests but does not forge MLS leaf removal or logical participant removal. Exact completed response-loss replay requires the same authenticated DID, canonical request digest, and signature; it returns the stored response without reexecuting the revocation.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRevokeDeviceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.revokeDevice"
}

@Serializable
    data class BlueCatbirdChatRevokeDeviceInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedDeviceRevocation    )

    @Serializable
    data class BlueCatbirdChatRevokeDeviceOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatRevokeDeviceError(val name: String, val description: String?) {
        object AuthenticationGenerationConflict: BlueCatbirdChatRevokeDeviceError("AuthenticationGenerationConflict", "")
        object CutoverRequired: BlueCatbirdChatRevokeDeviceError("CutoverRequired", "")
        object DeviceNotFound: BlueCatbirdChatRevokeDeviceError("DeviceNotFound", "")
        object DeviceNotRegistered: BlueCatbirdChatRevokeDeviceError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRevokeDeviceError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatRevokeDeviceError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatRevokeDeviceError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRevokeDeviceError("InvalidSignature", "")
        object NotAuthorized: BlueCatbirdChatRevokeDeviceError("NotAuthorized", "")
        object AccountSessionExpired: BlueCatbirdChatRevokeDeviceError("AccountSessionExpired", "")
        object DeviceBindingMismatch: BlueCatbirdChatRevokeDeviceError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatRevokeDeviceError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatRevokeDeviceError("RateLimited", "")
    }

/**
 * Signed lost-device or self revocation using authoritative own-device inventory CAS fields. Revocation releases packages, reservations, and recovery requests but does not forge MLS leaf removal or logical participant removal. Exact completed response-loss replay requires the same authenticated DID, canonical request digest, and signature; it returns the stored response without reexecuting the revocation.
 *
 * Endpoint: blue.catbird.chat.revokeDevice
 */
suspend fun BlueCatbirdChatNamespace.revokeDevice(
input: BlueCatbirdChatRevokeDeviceInput): ATProtoResponse<BlueCatbirdChatRevokeDeviceOutput> {
    val endpoint = "blue.catbird.chat.revokeDevice"

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
