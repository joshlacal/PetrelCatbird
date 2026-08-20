// Lexicon: 1, ID: blue.catbird.chat.getDevices
// Return the complete active addressable-device set for one to five strictly ordered, duplicate-free bare DIDs. At most 20 active devices exist per DID, so the bounded response is never truncated. Output devices are strictly ordered by (userDid exact UTF-8 bytes, deviceId raw UUID bytes). actorDeviceId identifies the authenticated caller's active device and is validated against the service-authenticated DID.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetDevicesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getDevices"
}

@Serializable
    data class BlueCatbirdChatGetDevicesParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,        @SerialName("userDids")
        val userDids: List<DID>    )

    @Serializable
    data class BlueCatbirdChatGetDevicesOutput(
        @SerialName("devices")
        val devices: List<BlueCatbirdChatDefsAddressableDevice>    )

sealed class BlueCatbirdChatGetDevicesError(val name: String, val description: String?) {
        object CutoverRequired: BlueCatbirdChatGetDevicesError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetDevicesError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetDevicesError("DeviceRevoked", "")
        object InvalidRequest: BlueCatbirdChatGetDevicesError("InvalidRequest", "")
        object AccountSessionExpired: BlueCatbirdChatGetDevicesError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatGetDevicesError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatGetDevicesError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatGetDevicesError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatGetDevicesError("RateLimited", "")
    }

/**
 * Return the complete active addressable-device set for one to five strictly ordered, duplicate-free bare DIDs. At most 20 active devices exist per DID, so the bounded response is never truncated. Output devices are strictly ordered by (userDid exact UTF-8 bytes, deviceId raw UUID bytes). actorDeviceId identifies the authenticated caller's active device and is validated against the service-authenticated DID.
 *
 * Endpoint: blue.catbird.chat.getDevices
 */
suspend fun BlueCatbirdChatNamespace.getDevices(
parameters: BlueCatbirdChatGetDevicesParameters): ATProtoResponse<BlueCatbirdChatGetDevicesOutput> {
    val endpoint = "blue.catbird.chat.getDevices"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
