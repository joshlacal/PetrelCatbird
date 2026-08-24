// Lexicon: 1, ID: blue.catbird.chat.updatePushToken
// Register, update, or clear the APNs device token for the caller's active device.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatUpdatePushTokenDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.updatePushToken"
}

@Serializable
    data class BlueCatbirdChatUpdatePushTokenInput(
// APNs device token (e.g. hex-encoded). Omit to unregister/clear.        @SerialName("token")
        val token: String? = null    )

    @Serializable
    data class BlueCatbirdChatUpdatePushTokenOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatUpdatePushTokenError(val name: String, val description: String?) {
        object AccountSessionExpired: BlueCatbirdChatUpdatePushTokenError("AccountSessionExpired", "")
        object CutoverRequired: BlueCatbirdChatUpdatePushTokenError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatUpdatePushTokenError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatUpdatePushTokenError("DeviceRevoked", "")
        object InvalidRequest: BlueCatbirdChatUpdatePushTokenError("InvalidRequest", "")
        object NotAuthorized: BlueCatbirdChatUpdatePushTokenError("NotAuthorized", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatUpdatePushTokenError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatUpdatePushTokenError("RateLimited", "")
    }

/**
 * Register, update, or clear the APNs device token for the caller's active device.
 *
 * Endpoint: blue.catbird.chat.updatePushToken
 */
suspend fun BlueCatbirdChatNamespace.updatePushToken(
input: BlueCatbirdChatUpdatePushTokenInput): ATProtoResponse<BlueCatbirdChatUpdatePushTokenOutput> {
    val endpoint = "blue.catbird.chat.updatePushToken"

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
