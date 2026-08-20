// Lexicon: 1, ID: blue.catbird.chat.closeConversation
// Terminal signed close. Either exact direct participant, including a pending invitee, may close unilaterally; a group may close only when its sole remaining logical participant is an active admin. The atomic CAS has no successor, closes every application interval, releases invitation counts and request-bound reservations, supersedes pending work, and returns the immutable terminal tombstone and entry.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatCloseConversationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.closeConversation"
}

@Serializable
    data class BlueCatbirdChatCloseConversationInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedConversationClose    )

    @Serializable
    data class BlueCatbirdChatCloseConversationOutput(
        @SerialName("result")
        val result: BlueCatbirdChatDefsConversationCloseResult    )

sealed class BlueCatbirdChatCloseConversationError(val name: String, val description: String?) {
        object ConversationCloseNotAllowed: BlueCatbirdChatCloseConversationError("ConversationCloseNotAllowed", "")
        object ConversationNotFound: BlueCatbirdChatCloseConversationError("ConversationNotFound", "")
        object CoordinateOverflow: BlueCatbirdChatCloseConversationError("CoordinateOverflow", "")
        object CutoverRequired: BlueCatbirdChatCloseConversationError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatCloseConversationError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatCloseConversationError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatCloseConversationError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatCloseConversationError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatCloseConversationError("InvalidSignature", "")
        object NotParticipant: BlueCatbirdChatCloseConversationError("NotParticipant", "")
        object StaleCoordinates: BlueCatbirdChatCloseConversationError("StaleCoordinates", "")
        object AccountSessionExpired: BlueCatbirdChatCloseConversationError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatCloseConversationError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatCloseConversationError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatCloseConversationError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatCloseConversationError("RateLimited", "")
    }

/**
 * Terminal signed close. Either exact direct participant, including a pending invitee, may close unilaterally; a group may close only when its sole remaining logical participant is an active admin. The atomic CAS has no successor, closes every application interval, releases invitation counts and request-bound reservations, supersedes pending work, and returns the immutable terminal tombstone and entry.
 *
 * Endpoint: blue.catbird.chat.closeConversation
 */
suspend fun BlueCatbirdChatNamespace.closeConversation(
input: BlueCatbirdChatCloseConversationInput): ATProtoResponse<BlueCatbirdChatCloseConversationOutput> {
    val endpoint = "blue.catbird.chat.closeConversation"

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
