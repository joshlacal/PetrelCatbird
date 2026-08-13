// Lexicon: 1, ID: blue.catbird.chat.sendMessage
// Stores one signed MLS PrivateMessage Application wrapper from a current leaf after exact coordinate/AAD validation. A complete at-most-60-second relationship projection between the actor DID and every current active participant DID is required; any actor-involving direct/list block denies and incomplete or stale policy fails closed. Direct additionally requires both exact participants active and each represented by at least one current MLS leaf, rejecting pre-consent and acceptance/recovery/reset gaps; group traffic may continue for remaining leaves. An application attachment binding, if present, is exactly applicationAttachmentBinding with purpose attachment; it must be completed-unbound and owned by this exact signer, and metadataAvatarBinding or generic uploadedBlobBinding rejects. The service remains blind to plaintext. (conversationId,messageId) is unique, and a stale attempt terminally retires that ID. The returned applicationEntry carries the exact signedRequest rather than duplicated unsigned actor, device, coordinate, message, artifact, or blob fields; entry conversationId must equal signedRequest.body.prior.conversationId. Clients verify the canonical CATBIRD-CHAT-MESSAGE\0 Ed25519 transcript before decrypting or attributing. Its immutable fingerprint is SHA-256 of UTF8(CATBIRD-CHAT-APPLICATION-ENTRY-FINGERPRINT\0) followed by canonical DAG-CBOR of exact entryId bytes16, conversationId bytes16, seq, requestDigest bytes32, signature bytes64, and receivedAt canonical text; plaintext-only or unsigned substitutes are forbidden.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatSendMessageDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.sendMessage"
}

@Serializable
    data class BlueCatbirdChatSendMessageInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedApplicationSend    )

    @Serializable
    data class BlueCatbirdChatSendMessageOutput(
        @SerialName("entry")
        val entry: BlueCatbirdChatDefsApplicationEntry    )

sealed class BlueCatbirdChatSendMessageError(val name: String, val description: String?) {
        object BlobBindingConflict: BlueCatbirdChatSendMessageError("BlobBindingConflict", "")
        object BlobNotFound: BlueCatbirdChatSendMessageError("BlobNotFound", "")
        object BlockedRelationship: BlueCatbirdChatSendMessageError("BlockedRelationship", "")
        object ConversationNotAccepted: BlueCatbirdChatSendMessageError("ConversationNotAccepted", "")
        object ConversationNotFound: BlueCatbirdChatSendMessageError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatSendMessageError("CutoverRequired", "")
        object DeviceNotLeaf: BlueCatbirdChatSendMessageError("DeviceNotLeaf", "")
        object DeviceNotRegistered: BlueCatbirdChatSendMessageError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatSendMessageError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatSendMessageError("IdempotencyConflict", "")
        object InvalidApplicationMessage: BlueCatbirdChatSendMessageError("InvalidApplicationMessage", "")
        object InvalidDPoP: BlueCatbirdChatSendMessageError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatSendMessageError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatSendMessageError("InvalidSignature", "")
        object NotMember: BlueCatbirdChatSendMessageError("NotMember", "")
        object RecipientNotReady: BlueCatbirdChatSendMessageError("RecipientNotReady", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatSendMessageError("RelationshipPolicyUnavailable", "")
        object StaleCoordinates: BlueCatbirdChatSendMessageError("StaleCoordinates", "")
        object UnsupportedMlsProfile: BlueCatbirdChatSendMessageError("UnsupportedMlsProfile", "")
    }

/**
 * Stores one signed MLS PrivateMessage Application wrapper from a current leaf after exact coordinate/AAD validation. A complete at-most-60-second relationship projection between the actor DID and every current active participant DID is required; any actor-involving direct/list block denies and incomplete or stale policy fails closed. Direct additionally requires both exact participants active and each represented by at least one current MLS leaf, rejecting pre-consent and acceptance/recovery/reset gaps; group traffic may continue for remaining leaves. An application attachment binding, if present, is exactly applicationAttachmentBinding with purpose attachment; it must be completed-unbound and owned by this exact signer, and metadataAvatarBinding or generic uploadedBlobBinding rejects. The service remains blind to plaintext. (conversationId,messageId) is unique, and a stale attempt terminally retires that ID. The returned applicationEntry carries the exact signedRequest rather than duplicated unsigned actor, device, coordinate, message, artifact, or blob fields; entry conversationId must equal signedRequest.body.prior.conversationId. Clients verify the canonical CATBIRD-CHAT-MESSAGE\0 Ed25519 transcript before decrypting or attributing. Its immutable fingerprint is SHA-256 of UTF8(CATBIRD-CHAT-APPLICATION-ENTRY-FINGERPRINT\0) followed by canonical DAG-CBOR of exact entryId bytes16, conversationId bytes16, seq, requestDigest bytes32, signature bytes64, and receivedAt canonical text; plaintext-only or unsigned substitutes are forbidden.
 *
 * Endpoint: blue.catbird.chat.sendMessage
 */
suspend fun BlueCatbirdChatNamespace.sendMessage(
input: BlueCatbirdChatSendMessageInput): ATProtoResponse<BlueCatbirdChatSendMessageOutput> {
    val endpoint = "blue.catbird.chat.sendMessage"

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
