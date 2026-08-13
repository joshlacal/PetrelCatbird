// Lexicon: 1, ID: blue.catbird.chat.cancelLeafRecovery
// Idempotently cancels an open leaf-recovery request owned by the signing device and releases its unconsumed KeyPackage reservation. No conversation coordinate is required because cancellation does not mutate MLS state.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatCancelLeafRecoveryDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.cancelLeafRecovery"
}

@Serializable
    data class BlueCatbirdChatCancelLeafRecoveryInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeafRecoveryCancellation    )

    @Serializable
    data class BlueCatbirdChatCancelLeafRecoveryOutput(
        @SerialName("recovery")
        val recovery: BlueCatbirdChatDefsLeafRecoveryView    )

sealed class BlueCatbirdChatCancelLeafRecoveryError(val name: String, val description: String?) {
        object CancellationConflict: BlueCatbirdChatCancelLeafRecoveryError("CancellationConflict", "")
        object CutoverRequired: BlueCatbirdChatCancelLeafRecoveryError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatCancelLeafRecoveryError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatCancelLeafRecoveryError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatCancelLeafRecoveryError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatCancelLeafRecoveryError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatCancelLeafRecoveryError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatCancelLeafRecoveryError("InvalidSignature", "")
        object LeafRecoveryNotFound: BlueCatbirdChatCancelLeafRecoveryError("LeafRecoveryNotFound", "")
        object NotAuthorized: BlueCatbirdChatCancelLeafRecoveryError("NotAuthorized", "")
    }

/**
 * Idempotently cancels an open leaf-recovery request owned by the signing device and releases its unconsumed KeyPackage reservation. No conversation coordinate is required because cancellation does not mutate MLS state.
 *
 * Endpoint: blue.catbird.chat.cancelLeafRecovery
 */
suspend fun BlueCatbirdChatNamespace.cancelLeafRecovery(
input: BlueCatbirdChatCancelLeafRecoveryInput): ATProtoResponse<BlueCatbirdChatCancelLeafRecoveryOutput> {
    val endpoint = "blue.catbird.chat.cancelLeafRecovery"

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
