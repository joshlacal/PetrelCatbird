// Lexicon: 1, ID: blue.catbird.chat.rejectWelcome
// Idempotently records a signed terminal rejection of one historically addressed Welcome. Authorization is tied to the immutable recipient device and transition coordinate, not current coordinates or current membership.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRejectWelcomeDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.rejectWelcome"
}

@Serializable
    data class BlueCatbirdChatRejectWelcomeInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedWelcomeRejection    )

    @Serializable
    data class BlueCatbirdChatRejectWelcomeOutput(
        @SerialName("status")
        val status: String,        @SerialName("rejectedAt")
        val rejectedAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatRejectWelcomeError(val name: String, val description: String?) {
        object CutoverRequired: BlueCatbirdChatRejectWelcomeError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatRejectWelcomeError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRejectWelcomeError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatRejectWelcomeError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatRejectWelcomeError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRejectWelcomeError("InvalidSignature", "")
        object RejectionConflict: BlueCatbirdChatRejectWelcomeError("RejectionConflict", "")
        object WelcomeExpired: BlueCatbirdChatRejectWelcomeError("WelcomeExpired", "")
        object WelcomeNotFound: BlueCatbirdChatRejectWelcomeError("WelcomeNotFound", "")
        object WelcomeSuperseded: BlueCatbirdChatRejectWelcomeError("WelcomeSuperseded", "")
    }

/**
 * Idempotently records a signed terminal rejection of one historically addressed Welcome. Authorization is tied to the immutable recipient device and transition coordinate, not current coordinates or current membership.
 *
 * Endpoint: blue.catbird.chat.rejectWelcome
 */
suspend fun BlueCatbirdChatNamespace.rejectWelcome(
input: BlueCatbirdChatRejectWelcomeInput): ATProtoResponse<BlueCatbirdChatRejectWelcomeOutput> {
    val endpoint = "blue.catbird.chat.rejectWelcome"

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
