// Lexicon: 1, ID: blue.catbird.chat.acceptConversation
// Accepts one exact pending invitation from the target participant's active registered device. Under the conversation mutation lock it verifies immutable invitation provenance, consent and every-pair block policy, changes only pending to active at prior stateVersion plus one, and atomically creates one add-kind target-device-signed recovery request with a request-bound KeyPackage reservation. A different current leaf later fulfills the one-device Add Commit and Welcome.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatAcceptConversationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.acceptConversation"
}

@Serializable
    data class BlueCatbirdChatAcceptConversationInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedParticipantAcceptance    )

    @Serializable
    data class BlueCatbirdChatAcceptConversationOutput(
        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsParticipantAcceptanceEntry,        @SerialName("recovery")
        val recovery: BlueCatbirdChatDefsLeafRecoveryView    )

sealed class BlueCatbirdChatAcceptConversationError(val name: String, val description: String?) {
        object BlockedRelationship: BlueCatbirdChatAcceptConversationError("BlockedRelationship", "")
        object ConversationNotFound: BlueCatbirdChatAcceptConversationError("ConversationNotFound", "")
        object CoordinateOverflow: BlueCatbirdChatAcceptConversationError("CoordinateOverflow", "")
        object CutoverRequired: BlueCatbirdChatAcceptConversationError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatAcceptConversationError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatAcceptConversationError("DeviceRevoked", "")
        object GroupInvitesDisabled: BlueCatbirdChatAcceptConversationError("GroupInvitesDisabled", "")
        object IdempotencyConflict: BlueCatbirdChatAcceptConversationError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatAcceptConversationError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatAcceptConversationError("InvalidSignature", "")
        object InvitationNotFound: BlueCatbirdChatAcceptConversationError("InvitationNotFound", "")
        object InvitationNotPending: BlueCatbirdChatAcceptConversationError("InvitationNotPending", "")
        object InvitationProvenanceMismatch: BlueCatbirdChatAcceptConversationError("InvitationProvenanceMismatch", "")
        object KeyPackageUnavailable: BlueCatbirdChatAcceptConversationError("KeyPackageUnavailable", "")
        object MessagesDisabled: BlueCatbirdChatAcceptConversationError("MessagesDisabled", "")
        object NotFollowedByRecipient: BlueCatbirdChatAcceptConversationError("NotFollowedByRecipient", "")
        object NotParticipant: BlueCatbirdChatAcceptConversationError("NotParticipant", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatAcceptConversationError("RelationshipPolicyUnavailable", "")
        object StaleCoordinates: BlueCatbirdChatAcceptConversationError("StaleCoordinates", "")
        object AccountSessionExpired: BlueCatbirdChatAcceptConversationError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatAcceptConversationError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatAcceptConversationError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatAcceptConversationError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatAcceptConversationError("RateLimited", "")
    }

/**
 * Accepts one exact pending invitation from the target participant's active registered device. Under the conversation mutation lock it verifies immutable invitation provenance, consent and every-pair block policy, changes only pending to active at prior stateVersion plus one, and atomically creates one add-kind target-device-signed recovery request with a request-bound KeyPackage reservation. A different current leaf later fulfills the one-device Add Commit and Welcome.
 *
 * Endpoint: blue.catbird.chat.acceptConversation
 */
suspend fun BlueCatbirdChatNamespace.acceptConversation(
input: BlueCatbirdChatAcceptConversationInput): ATProtoResponse<BlueCatbirdChatAcceptConversationOutput> {
    val endpoint = "blue.catbird.chat.acceptConversation"

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
