// Lexicon: 1, ID: blue.catbird.chat.getLeafRecoveryInbox
// Returns the flat closed exact-device recovery inbox from the retained inventorySession created by getConversations: the unchanged target-device-signed leafRecoveryView and the four concrete non-authorizing recoveryWorkView-family variants sourced only from retained expired or rejected Welcomes. Every union ref is a concrete object. inventorySessionId is always required; pageCursor is an opaque recovery-domain cursor bound to the same device, session, audience, fence, and expiry. Every response echoes the exact session event cursor and expiry; nextPageCursor is absent after the final page.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetLeafRecoveryInboxDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getLeafRecoveryInbox"
}

@Serializable
    data class BlueCatbirdChatGetLeafRecoveryInboxParameters(
        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("pageCursor")
        val pageCursor: String? = null,        @SerialName("limit")
        val limit: Int    )

    @Serializable
    data class BlueCatbirdChatGetLeafRecoveryInboxOutput(
        @SerialName("items")
        val items: List<BlueCatbirdChatDefsLeafRecoveryInboxItem>,        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("snapshotEventCursor")
        val snapshotEventCursor: String,        @SerialName("nextPageCursor")
        val nextPageCursor: String? = null,        @SerialName("hasMore")
        val hasMore: Boolean,        @SerialName("snapshotExpiresAt")
        val snapshotExpiresAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatGetLeafRecoveryInboxError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatGetLeafRecoveryInboxError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatGetLeafRecoveryInboxError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetLeafRecoveryInboxError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetLeafRecoveryInboxError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatGetLeafRecoveryInboxError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatGetLeafRecoveryInboxError("InvalidRequest", "")
        object InventorySessionExpired: BlueCatbirdChatGetLeafRecoveryInboxError("InventorySessionExpired", "")
        object InventorySessionMismatch: BlueCatbirdChatGetLeafRecoveryInboxError("InventorySessionMismatch", "")
    }

/**
 * Returns the flat closed exact-device recovery inbox from the retained inventorySession created by getConversations: the unchanged target-device-signed leafRecoveryView and the four concrete non-authorizing recoveryWorkView-family variants sourced only from retained expired or rejected Welcomes. Every union ref is a concrete object. inventorySessionId is always required; pageCursor is an opaque recovery-domain cursor bound to the same device, session, audience, fence, and expiry. Every response echoes the exact session event cursor and expiry; nextPageCursor is absent after the final page.
 *
 * Endpoint: blue.catbird.chat.getLeafRecoveryInbox
 */
suspend fun BlueCatbirdChatNamespace.getLeafRecoveryInbox(
parameters: BlueCatbirdChatGetLeafRecoveryInboxParameters): ATProtoResponse<BlueCatbirdChatGetLeafRecoveryInboxOutput> {
    val endpoint = "blue.catbird.chat.getLeafRecoveryInbox"

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
