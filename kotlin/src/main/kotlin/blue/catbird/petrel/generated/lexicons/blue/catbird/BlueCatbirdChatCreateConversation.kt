// Lexicon: 1, ID: blue.catbird.chat.createConversation
// True-absence-CAS creates immutable direct or group state from a client-staged creator-only epoch-zero group. Every noncreator is a pending zero-leaf consent request with no server-readable invitation body. Direct is exactly creator plus one pending admin-role invitee, is unique per unordered DID pair while nonterminal, and a concurrent loser receives the existing identity as a typed success outcome. Group invitees are pending members. Consent, invitation limits, and every-pair block policy are checked under lock. There is no preparation, general reservation, bootstrap Commit, or Welcome.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatCreateConversationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.createConversation"
}

@Serializable
    data class BlueCatbirdChatCreateConversationInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedCreation    )

    @Serializable
    data class BlueCatbirdChatCreateConversationOutput(
        @SerialName("result")
        val result: BlueCatbirdChatDefsConversationCreationResult    )

sealed class BlueCatbirdChatCreateConversationError(val name: String, val description: String?) {
        object BlockedRelationship: BlueCatbirdChatCreateConversationError("BlockedRelationship", "")
        object ConversationAlreadyExists: BlueCatbirdChatCreateConversationError("ConversationAlreadyExists", "")
        object CutoverRequired: BlueCatbirdChatCreateConversationError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatCreateConversationError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatCreateConversationError("DeviceRevoked", "")
        object GroupInvitesDisabled: BlueCatbirdChatCreateConversationError("GroupInvitesDisabled", "")
        object IdempotencyConflict: BlueCatbirdChatCreateConversationError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatCreateConversationError("InvalidDPoP", "")
        object InvalidGenesisGroupInfo: BlueCatbirdChatCreateConversationError("InvalidGenesisGroupInfo", "")
        object InvalidMetadataSnapshot: BlueCatbirdChatCreateConversationError("InvalidMetadataSnapshot", "")
        object InvalidMlsArtifact: BlueCatbirdChatCreateConversationError("InvalidMlsArtifact", "")
        object InvalidRequest: BlueCatbirdChatCreateConversationError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatCreateConversationError("InvalidSignature", "")
        object InvitationLimitReached: BlueCatbirdChatCreateConversationError("InvitationLimitReached", "")
        object MessagesDisabled: BlueCatbirdChatCreateConversationError("MessagesDisabled", "")
        object MetadataNonceReuse: BlueCatbirdChatCreateConversationError("MetadataNonceReuse", "")
        object NotAuthorized: BlueCatbirdChatCreateConversationError("NotAuthorized", "")
        object NotFollowedByRecipient: BlueCatbirdChatCreateConversationError("NotFollowedByRecipient", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatCreateConversationError("RelationshipPolicyUnavailable", "")
        object UnsupportedMlsProfile: BlueCatbirdChatCreateConversationError("UnsupportedMlsProfile", "")
    }

/**
 * True-absence-CAS creates immutable direct or group state from a client-staged creator-only epoch-zero group. Every noncreator is a pending zero-leaf consent request with no server-readable invitation body. Direct is exactly creator plus one pending admin-role invitee, is unique per unordered DID pair while nonterminal, and a concurrent loser receives the existing identity as a typed success outcome. Group invitees are pending members. Consent, invitation limits, and every-pair block policy are checked under lock. There is no preparation, general reservation, bootstrap Commit, or Welcome.
 *
 * Endpoint: blue.catbird.chat.createConversation
 */
suspend fun BlueCatbirdChatNamespace.createConversation(
input: BlueCatbirdChatCreateConversationInput): ATProtoResponse<BlueCatbirdChatCreateConversationOutput> {
    val endpoint = "blue.catbird.chat.createConversation"

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
