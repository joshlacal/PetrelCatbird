// Lexicon: 1, ID: blue.catbird.chat.getConversationState
// Returns public state and visible pending reset/leave work under separate control-plane entitlement. state.snapshotSeq is the greatest committed conversation seq included in this transactionally observed state; it is not a getEntries afterSeq or an application-visibility grant. A logical participant with zero leaves may receive bounded inventory/reset/recovery/leave visibility needed to join or leave, but roster status and sibling-device membership grant no application ciphertext. A former concrete device leaf is bounded by its own inclusive terminalSeq and cannot inherit another device's interval.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetConversationStateDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getConversationState"
}

@Serializable
    data class BlueCatbirdChatGetConversationStateParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 conversation ID; strict runtime validation rejects every other spelling/version/variant.        @SerialName("conversationId")
        val conversationId: String    )

    @Serializable
    data class BlueCatbirdChatGetConversationStateOutput(
        @SerialName("state")
        val state: BlueCatbirdChatDefsConversationState,        @SerialName("pendingResetRequests")
        val pendingResetRequests: List<BlueCatbirdChatDefsResetRequestView>,        @SerialName("pendingLeaveRequests")
        val pendingLeaveRequests: List<BlueCatbirdChatDefsLeaveRequestView>    )

sealed class BlueCatbirdChatGetConversationStateError(val name: String, val description: String?) {
        object AccessOutsideMembershipInterval: BlueCatbirdChatGetConversationStateError("AccessOutsideMembershipInterval", "")
        object ConversationNotFound: BlueCatbirdChatGetConversationStateError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatGetConversationStateError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetConversationStateError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetConversationStateError("DeviceRevoked", "")
        object InvalidRequest: BlueCatbirdChatGetConversationStateError("InvalidRequest", "")
        object NotEntitled: BlueCatbirdChatGetConversationStateError("NotEntitled", "")
        object AccountSessionExpired: BlueCatbirdChatGetConversationStateError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatGetConversationStateError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatGetConversationStateError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatGetConversationStateError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatGetConversationStateError("RateLimited", "")
    }

/**
 * Returns public state and visible pending reset/leave work under separate control-plane entitlement. state.snapshotSeq is the greatest committed conversation seq included in this transactionally observed state; it is not a getEntries afterSeq or an application-visibility grant. A logical participant with zero leaves may receive bounded inventory/reset/recovery/leave visibility needed to join or leave, but roster status and sibling-device membership grant no application ciphertext. A former concrete device leaf is bounded by its own inclusive terminalSeq and cannot inherit another device's interval.
 *
 * Endpoint: blue.catbird.chat.getConversationState
 */
suspend fun BlueCatbirdChatNamespace.getConversationState(
parameters: BlueCatbirdChatGetConversationStateParameters): ATProtoResponse<BlueCatbirdChatGetConversationStateOutput> {
    val endpoint = "blue.catbird.chat.getConversationState"

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
