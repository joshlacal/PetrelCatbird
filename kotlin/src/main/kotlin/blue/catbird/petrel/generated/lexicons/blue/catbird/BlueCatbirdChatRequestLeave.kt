// Lexicon: 1, ID: blue.catbird.chat.requestLeave
// Group-only self-leave. A pending or active zero-leaf participant signs immediate stateVersion-plus-one self-removal. An active participant with leaves signs durable 24-hour consent for a different-DID current leaf to remove every requester leaf and the participant by Commit. Neither path can remove the last active admin. Relationship blocks or policy unavailability never deny self-exit. Direct participants use closeConversation instead.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRequestLeaveDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.requestLeave"
}

@Serializable
    data class BlueCatbirdChatRequestLeaveInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeaveOperation    )

    @Serializable
    data class BlueCatbirdChatRequestLeaveOutput(
        @SerialName("result")
        val result: BlueCatbirdChatDefsLeaveOperationResult    )

sealed class BlueCatbirdChatRequestLeaveError(val name: String, val description: String?) {
        object ConversationNotFound: BlueCatbirdChatRequestLeaveError("ConversationNotFound", "")
        object CoordinateOverflow: BlueCatbirdChatRequestLeaveError("CoordinateOverflow", "")
        object CutoverRequired: BlueCatbirdChatRequestLeaveError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatRequestLeaveError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRequestLeaveError("DeviceRevoked", "")
        object DirectParticipantMutationForbidden: BlueCatbirdChatRequestLeaveError("DirectParticipantMutationForbidden", "")
        object IdempotencyConflict: BlueCatbirdChatRequestLeaveError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatRequestLeaveError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatRequestLeaveError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRequestLeaveError("InvalidSignature", "")
        object LastAdminRequired: BlueCatbirdChatRequestLeaveError("LastAdminRequired", "")
        object LeaveAlreadyPending: BlueCatbirdChatRequestLeaveError("LeaveAlreadyPending", "")
        object NotMember: BlueCatbirdChatRequestLeaveError("NotMember", "")
        object StaleCoordinates: BlueCatbirdChatRequestLeaveError("StaleCoordinates", "")
    }

/**
 * Group-only self-leave. A pending or active zero-leaf participant signs immediate stateVersion-plus-one self-removal. An active participant with leaves signs durable 24-hour consent for a different-DID current leaf to remove every requester leaf and the participant by Commit. Neither path can remove the last active admin. Relationship blocks or policy unavailability never deny self-exit. Direct participants use closeConversation instead.
 *
 * Endpoint: blue.catbird.chat.requestLeave
 */
suspend fun BlueCatbirdChatNamespace.requestLeave(
input: BlueCatbirdChatRequestLeaveInput): ATProtoResponse<BlueCatbirdChatRequestLeaveOutput> {
    val endpoint = "blue.catbird.chat.requestLeave"

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
