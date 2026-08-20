// Lexicon: 1, ID: blue.catbird.chat.enrollDevice
// Enroll one client-generated MLS device through a standard ATProto service-authenticated AppView request. The canonical Ed25519-signed body binds the authenticated actor DID, device UUID, immutable signing key, generation-zero absence claim, and initial key packages. Existing login is sufficient when its OAuth scope permits this RPC.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatEnrollDeviceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.enrollDevice"
}

@Serializable
    data class BlueCatbirdChatEnrollDeviceInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedDeviceEnrollment    )

    @Serializable
    data class BlueCatbirdChatEnrollDeviceOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatEnrollDeviceError(val name: String, val description: String?) {
        object AuthenticationGenerationConflict: BlueCatbirdChatEnrollDeviceError("AuthenticationGenerationConflict", "")
        object CutoverRequired: BlueCatbirdChatEnrollDeviceError("CutoverRequired", "")
        object DeviceAlreadyExists: BlueCatbirdChatEnrollDeviceError("DeviceAlreadyExists", "")
        object DeviceLimitReached: BlueCatbirdChatEnrollDeviceError("DeviceLimitReached", "")
        object DeviceTombstoned: BlueCatbirdChatEnrollDeviceError("DeviceTombstoned", "")
        object IdempotencyConflict: BlueCatbirdChatEnrollDeviceError("IdempotencyConflict", "")
        object InvalidKeyPackage: BlueCatbirdChatEnrollDeviceError("InvalidKeyPackage", "")
        object InvalidRequest: BlueCatbirdChatEnrollDeviceError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatEnrollDeviceError("InvalidSignature", "")
        object KeyPackageInventoryLimitReached: BlueCatbirdChatEnrollDeviceError("KeyPackageInventoryLimitReached", "")
        object NotAuthorized: BlueCatbirdChatEnrollDeviceError("NotAuthorized", "")
        object AccountSessionExpired: BlueCatbirdChatEnrollDeviceError("AccountSessionExpired", "")
        object DeviceBindingMismatch: BlueCatbirdChatEnrollDeviceError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatEnrollDeviceError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatEnrollDeviceError("RateLimited", "")
    }

/**
 * Enroll one client-generated MLS device through a standard ATProto service-authenticated AppView request. The canonical Ed25519-signed body binds the authenticated actor DID, device UUID, immutable signing key, generation-zero absence claim, and initial key packages. Existing login is sufficient when its OAuth scope permits this RPC.
 *
 * Endpoint: blue.catbird.chat.enrollDevice
 */
suspend fun BlueCatbirdChatNamespace.enrollDevice(
input: BlueCatbirdChatEnrollDeviceInput): ATProtoResponse<BlueCatbirdChatEnrollDeviceOutput> {
    val endpoint = "blue.catbird.chat.enrollDevice"

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
