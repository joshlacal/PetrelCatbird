// Lexicon: 1, ID: blue.catbird.chat.publishTyping
// Publish signed clear ephemeral typing state from a current active MLS leaf. A complete at-most-60-second relationship projection between the actor DID and every current active participant DID is required; any actor-involving direct/list block denies and incomplete or stale policy fails closed. Direct additionally requires both exact participants active and each represented by at least one current MLS leaf; group traffic may continue for remaining leaves. The server derives an eight-second TTL, rate-limits and coalesces it, and emits only an uncursored best-effort typing variant with no database append or MLS secret-tree effect.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatPublishTypingDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.publishTyping"
}

@Serializable
    data class BlueCatbirdChatPublishTypingInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedTyping    )

    @Serializable
    data class BlueCatbirdChatPublishTypingOutput(
        @SerialName("typing")
        val typing: BlueCatbirdChatDefsTypingEvent    )

sealed class BlueCatbirdChatPublishTypingError(val name: String, val description: String?) {
        object BlockedRelationship: BlueCatbirdChatPublishTypingError("BlockedRelationship", "")
        object ConversationNotAccepted: BlueCatbirdChatPublishTypingError("ConversationNotAccepted", "")
        object ConversationNotFound: BlueCatbirdChatPublishTypingError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatPublishTypingError("CutoverRequired", "")
        object DeviceNotLeaf: BlueCatbirdChatPublishTypingError("DeviceNotLeaf", "")
        object DeviceNotRegistered: BlueCatbirdChatPublishTypingError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatPublishTypingError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatPublishTypingError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatPublishTypingError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatPublishTypingError("InvalidSignature", "")
        object RateLimited: BlueCatbirdChatPublishTypingError("RateLimited", "")
        object RecipientNotReady: BlueCatbirdChatPublishTypingError("RecipientNotReady", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatPublishTypingError("RelationshipPolicyUnavailable", "")
        object StaleCoordinates: BlueCatbirdChatPublishTypingError("StaleCoordinates", "")
        object AccountSessionExpired: BlueCatbirdChatPublishTypingError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatPublishTypingError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatPublishTypingError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatPublishTypingError("ProtocolUpgradeRequired", "")
    }

/**
 * Publish signed clear ephemeral typing state from a current active MLS leaf. A complete at-most-60-second relationship projection between the actor DID and every current active participant DID is required; any actor-involving direct/list block denies and incomplete or stale policy fails closed. Direct additionally requires both exact participants active and each represented by at least one current MLS leaf; group traffic may continue for remaining leaves. The server derives an eight-second TTL, rate-limits and coalesces it, and emits only an uncursored best-effort typing variant with no database append or MLS secret-tree effect.
 *
 * Endpoint: blue.catbird.chat.publishTyping
 */
suspend fun BlueCatbirdChatNamespace.publishTyping(
input: BlueCatbirdChatPublishTypingInput): ATProtoResponse<BlueCatbirdChatPublishTypingOutput> {
    val endpoint = "blue.catbird.chat.publishTyping"

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
