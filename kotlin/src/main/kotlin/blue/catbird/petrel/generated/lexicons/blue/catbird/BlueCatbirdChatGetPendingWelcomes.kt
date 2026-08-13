// Lexicon: 1, ID: blue.catbird.chat.getPendingWelcomes
// Returns device-addressed pending Welcomes from the retained inventorySession created by getConversations. inventorySessionId is always required; pageCursor is an opaque Welcome-domain cursor bound to the same device, session, audience, fence, and expiry. Every response echoes the exact session event cursor and expiry; nextPageCursor is absent after the final page.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetPendingWelcomesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getPendingWelcomes"
}

@Serializable
    data class BlueCatbirdChatGetPendingWelcomesParameters(
        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("pageCursor")
        val pageCursor: String? = null,        @SerialName("limit")
        val limit: Int    )

    @Serializable
    data class BlueCatbirdChatGetPendingWelcomesOutput(
        @SerialName("items")
        val items: List<BlueCatbirdChatDefsWelcomeView>,        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("snapshotEventCursor")
        val snapshotEventCursor: String,        @SerialName("nextPageCursor")
        val nextPageCursor: String? = null,        @SerialName("hasMore")
        val hasMore: Boolean,        @SerialName("snapshotExpiresAt")
        val snapshotExpiresAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatGetPendingWelcomesError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatGetPendingWelcomesError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatGetPendingWelcomesError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetPendingWelcomesError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetPendingWelcomesError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatGetPendingWelcomesError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatGetPendingWelcomesError("InvalidRequest", "")
        object InventorySessionExpired: BlueCatbirdChatGetPendingWelcomesError("InventorySessionExpired", "")
        object InventorySessionMismatch: BlueCatbirdChatGetPendingWelcomesError("InventorySessionMismatch", "")
    }

/**
 * Returns device-addressed pending Welcomes from the retained inventorySession created by getConversations. inventorySessionId is always required; pageCursor is an opaque Welcome-domain cursor bound to the same device, session, audience, fence, and expiry. Every response echoes the exact session event cursor and expiry; nextPageCursor is absent after the final page.
 *
 * Endpoint: blue.catbird.chat.getPendingWelcomes
 */
suspend fun BlueCatbirdChatNamespace.getPendingWelcomes(
parameters: BlueCatbirdChatGetPendingWelcomesParameters): ATProtoResponse<BlueCatbirdChatGetPendingWelcomesOutput> {
    val endpoint = "blue.catbird.chat.getPendingWelcomes"

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
