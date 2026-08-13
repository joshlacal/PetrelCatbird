// Lexicon: 1, ID: blue.catbird.chat.cancelLeave
// Cancels an exact pending leave request before fulfillment. Exact replay returns the recorded cancellation; changed bytes or a terminal request state conflict.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatCancelLeaveDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.cancelLeave"
}

@Serializable
    data class BlueCatbirdChatCancelLeaveInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeaveCancellation    )

    @Serializable
    data class BlueCatbirdChatCancelLeaveOutput(
        @SerialName("leaveRequest")
        val leaveRequest: BlueCatbirdChatDefsLeaveRequestView,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsLeaveCancellationEntry    )

sealed class BlueCatbirdChatCancelLeaveError(val name: String, val description: String?) {
        object CancellationConflict: BlueCatbirdChatCancelLeaveError("CancellationConflict", "")
        object CutoverRequired: BlueCatbirdChatCancelLeaveError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatCancelLeaveError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatCancelLeaveError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatCancelLeaveError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatCancelLeaveError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatCancelLeaveError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatCancelLeaveError("InvalidSignature", "")
        object LeaveRequestNotFound: BlueCatbirdChatCancelLeaveError("LeaveRequestNotFound", "")
        object NotAuthorized: BlueCatbirdChatCancelLeaveError("NotAuthorized", "")
    }

/**
 * Cancels an exact pending leave request before fulfillment. Exact replay returns the recorded cancellation; changed bytes or a terminal request state conflict.
 *
 * Endpoint: blue.catbird.chat.cancelLeave
 */
suspend fun BlueCatbirdChatNamespace.cancelLeave(
input: BlueCatbirdChatCancelLeaveInput): ATProtoResponse<BlueCatbirdChatCancelLeaveOutput> {
    val endpoint = "blue.catbird.chat.cancelLeave"

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
