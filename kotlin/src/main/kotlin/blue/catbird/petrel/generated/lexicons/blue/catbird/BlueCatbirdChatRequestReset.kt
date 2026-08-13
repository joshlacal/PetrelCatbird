// Lexicon: 1, ID: blue.catbird.chat.requestReset
// Durably records an idempotent signed reset request at the exact prior coordinate from any active registered device of an active logical participant; the device need not be an MLS leaf. Pending participants may accept or close but cannot request reset. The request expires after exactly 24 hours and any coordinate change makes it stale. This does not itself mutate or retire the MLS group.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRequestResetDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.requestReset"
}

@Serializable
    data class BlueCatbirdChatRequestResetInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedResetRequest    )

    @Serializable
    data class BlueCatbirdChatRequestResetOutput(
        @SerialName("resetRequest")
        val resetRequest: BlueCatbirdChatDefsResetRequestView,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsResetRequestEntry    )

sealed class BlueCatbirdChatRequestResetError(val name: String, val description: String?) {
        object ConversationNotFound: BlueCatbirdChatRequestResetError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatRequestResetError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatRequestResetError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRequestResetError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatRequestResetError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatRequestResetError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatRequestResetError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRequestResetError("InvalidSignature", "")
        object NotMember: BlueCatbirdChatRequestResetError("NotMember", "")
        object ResetAlreadyPending: BlueCatbirdChatRequestResetError("ResetAlreadyPending", "")
        object StaleCoordinates: BlueCatbirdChatRequestResetError("StaleCoordinates", "")
    }

/**
 * Durably records an idempotent signed reset request at the exact prior coordinate from any active registered device of an active logical participant; the device need not be an MLS leaf. Pending participants may accept or close but cannot request reset. The request expires after exactly 24 hours and any coordinate change makes it stale. This does not itself mutate or retire the MLS group.
 *
 * Endpoint: blue.catbird.chat.requestReset
 */
suspend fun BlueCatbirdChatNamespace.requestReset(
input: BlueCatbirdChatRequestResetInput): ATProtoResponse<BlueCatbirdChatRequestResetOutput> {
    val endpoint = "blue.catbird.chat.requestReset"

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
