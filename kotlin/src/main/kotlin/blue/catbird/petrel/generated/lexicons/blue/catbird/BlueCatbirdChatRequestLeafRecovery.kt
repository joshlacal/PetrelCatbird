// Lexicon: 1, ID: blue.catbird.chat.requestLeafRecovery
// An active participant's target device signs an idempotent, expiring add-or-replace recovery request and atomically reserves its own compatible KeyPackage. Under the mutation lock the server rechecks block policy for every unordered roster pair. This does not alter MLS state; a different current leaf must fulfill exactly this one request with one ordinary public Add Commit and Welcome.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRequestLeafRecoveryDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.requestLeafRecovery"
}

@Serializable
    data class BlueCatbirdChatRequestLeafRecoveryInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeafRecoveryRequest    )

    @Serializable
    data class BlueCatbirdChatRequestLeafRecoveryOutput(
        @SerialName("recovery")
        val recovery: BlueCatbirdChatDefsLeafRecoveryView    )

sealed class BlueCatbirdChatRequestLeafRecoveryError(val name: String, val description: String?) {
        object BlockedRelationship: BlueCatbirdChatRequestLeafRecoveryError("BlockedRelationship", "")
        object ConversationNotFound: BlueCatbirdChatRequestLeafRecoveryError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatRequestLeafRecoveryError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatRequestLeafRecoveryError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRequestLeafRecoveryError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatRequestLeafRecoveryError("IdempotencyConflict", "")
        object InvalidRequest: BlueCatbirdChatRequestLeafRecoveryError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRequestLeafRecoveryError("InvalidSignature", "")
        object KeyPackageUnavailable: BlueCatbirdChatRequestLeafRecoveryError("KeyPackageUnavailable", "")
        object LeafRecoveryAlreadyOpen: BlueCatbirdChatRequestLeafRecoveryError("LeafRecoveryAlreadyOpen", "")
        object NotParticipant: BlueCatbirdChatRequestLeafRecoveryError("NotParticipant", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatRequestLeafRecoveryError("RelationshipPolicyUnavailable", "")
        object StaleCoordinates: BlueCatbirdChatRequestLeafRecoveryError("StaleCoordinates", "")
        object AccountSessionExpired: BlueCatbirdChatRequestLeafRecoveryError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatRequestLeafRecoveryError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatRequestLeafRecoveryError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatRequestLeafRecoveryError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatRequestLeafRecoveryError("RateLimited", "")
    }

/**
 * An active participant's target device signs an idempotent, expiring add-or-replace recovery request and atomically reserves its own compatible KeyPackage. Under the mutation lock the server rechecks block policy for every unordered roster pair. This does not alter MLS state; a different current leaf must fulfill exactly this one request with one ordinary public Add Commit and Welcome.
 *
 * Endpoint: blue.catbird.chat.requestLeafRecovery
 */
suspend fun BlueCatbirdChatNamespace.requestLeafRecovery(
input: BlueCatbirdChatRequestLeafRecoveryInput): ATProtoResponse<BlueCatbirdChatRequestLeafRecoveryOutput> {
    val endpoint = "blue.catbird.chat.requestLeafRecovery"

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
