// Lexicon: 1, ID: blue.catbird.chat.getOwnDevices
// Authoritative fenced inventory of the authenticated bare DID's active and revoked/package-ineligible devices. Every page is evaluated as of one retained fence and uses a distinct principal-bound cursor domain.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetOwnDevicesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getOwnDevices"
}

@Serializable
    data class BlueCatbirdChatGetOwnDevicesParameters(
        @SerialName("pageCursor")
        val pageCursor: String? = null,        @SerialName("limit")
        val limit: Int? = null    )

    @Serializable
    data class BlueCatbirdChatGetOwnDevicesOutput(
        @SerialName("items")
        val items: List<BlueCatbirdChatDefsOwnDeviceView>,        @SerialName("nextPageCursor")
        val nextPageCursor: String? = null,        @SerialName("hasMore")
        val hasMore: Boolean,        @SerialName("snapshotExpiresAt")
        val snapshotExpiresAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatGetOwnDevicesError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatGetOwnDevicesError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatGetOwnDevicesError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetOwnDevicesError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetOwnDevicesError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatGetOwnDevicesError("InvalidDPoP", "")
    }

/**
 * Authoritative fenced inventory of the authenticated bare DID's active and revoked/package-ineligible devices. Every page is evaluated as of one retained fence and uses a distinct principal-bound cursor domain.
 *
 * Endpoint: blue.catbird.chat.getOwnDevices
 */
suspend fun BlueCatbirdChatNamespace.getOwnDevices(
parameters: BlueCatbirdChatGetOwnDevicesParameters): ATProtoResponse<BlueCatbirdChatGetOwnDevicesOutput> {
    val endpoint = "blue.catbird.chat.getOwnDevices"

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
