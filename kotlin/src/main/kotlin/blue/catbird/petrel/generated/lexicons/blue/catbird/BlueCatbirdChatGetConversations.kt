// Lexicon: 1, ID: blue.catbird.chat.getConversations
// The first call creates one opaque retained inventorySession with a single event fence and materialized audience snapshots for active conversation states, device-removal tombstones, terminal close tombstones, pending Welcomes, and recovery inbox. Every page returns the same inventorySessionId, snapshotEventCursor, and expiry. pageCursor is domain-specific, audience/device/session-bound, and never an event cursor; nextPageCursor is absent after the final page. Mixed, expired, or device-mismatched sessions are rejected.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetConversationsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getConversations"
}

@Serializable
    data class BlueCatbirdChatGetConversationsParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,        @SerialName("pageCursor")
        val pageCursor: String? = null,        @SerialName("limit")
        val limit: Int    )

    @Serializable
    data class BlueCatbirdChatGetConversationsOutput(
        @SerialName("items")
        val items: List<BlueCatbirdChatDefsConversationInventoryItem>,        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("snapshotEventCursor")
        val snapshotEventCursor: String,        @SerialName("nextPageCursor")
        val nextPageCursor: String? = null,        @SerialName("hasMore")
        val hasMore: Boolean,        @SerialName("snapshotExpiresAt")
        val snapshotExpiresAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatGetConversationsError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatGetConversationsError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatGetConversationsError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetConversationsError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetConversationsError("DeviceRevoked", "")
        object InvalidRequest: BlueCatbirdChatGetConversationsError("InvalidRequest", "")
        object AccountSessionExpired: BlueCatbirdChatGetConversationsError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatGetConversationsError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatGetConversationsError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatGetConversationsError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatGetConversationsError("RateLimited", "")
    }

/**
 * The first call creates one opaque retained inventorySession with a single event fence and materialized audience snapshots for active conversation states, device-removal tombstones, terminal close tombstones, pending Welcomes, and recovery inbox. Every page returns the same inventorySessionId, snapshotEventCursor, and expiry. pageCursor is domain-specific, audience/device/session-bound, and never an event cursor; nextPageCursor is absent after the final page. Mixed, expired, or device-mismatched sessions are rejected.
 *
 * Endpoint: blue.catbird.chat.getConversations
 */
suspend fun BlueCatbirdChatNamespace.getConversations(
parameters: BlueCatbirdChatGetConversationsParameters): ATProtoResponse<BlueCatbirdChatGetConversationsOutput> {
    val endpoint = "blue.catbird.chat.getConversations"

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
