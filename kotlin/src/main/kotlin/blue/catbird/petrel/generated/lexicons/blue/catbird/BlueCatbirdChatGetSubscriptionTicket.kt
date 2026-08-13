// Lexicon: 1, ID: blue.catbird.chat.getSubscriptionTicket
// After conversations, pending Welcomes, and recovery inbox have all reached hasMore=false for one inventorySession, a fresh DPoP-authenticated call atomically marks that barrier complete and mints a one-use short-lived ticket. The ticket binds effective DID, device, JKT, authGeneration, inventorySessionId, exact event cursor, and subscription path. The first durable envelope's previousCursor equals that cursor.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetSubscriptionTicketDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getSubscriptionTicket"
}

@Serializable
    data class BlueCatbirdChatGetSubscriptionTicketInput(
        @SerialName("inventorySessionId")
        val inventorySessionId: String,        @SerialName("eventCursor")
        val eventCursor: String    )

    @Serializable
    data class BlueCatbirdChatGetSubscriptionTicketOutput(
        @SerialName("ticket")
        val ticket: String,        @SerialName("endpoint")
        val endpoint: URI,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    )

sealed class BlueCatbirdChatGetSubscriptionTicketError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatGetSubscriptionTicketError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatGetSubscriptionTicketError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetSubscriptionTicketError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetSubscriptionTicketError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatGetSubscriptionTicketError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatGetSubscriptionTicketError("InvalidRequest", "")
        object InventoryIncomplete: BlueCatbirdChatGetSubscriptionTicketError("InventoryIncomplete", "")
        object InventorySessionExpired: BlueCatbirdChatGetSubscriptionTicketError("InventorySessionExpired", "")
        object InventorySessionMismatch: BlueCatbirdChatGetSubscriptionTicketError("InventorySessionMismatch", "")
    }

/**
 * After conversations, pending Welcomes, and recovery inbox have all reached hasMore=false for one inventorySession, a fresh DPoP-authenticated call atomically marks that barrier complete and mints a one-use short-lived ticket. The ticket binds effective DID, device, JKT, authGeneration, inventorySessionId, exact event cursor, and subscription path. The first durable envelope's previousCursor equals that cursor.
 *
 * Endpoint: blue.catbird.chat.getSubscriptionTicket
 */
suspend fun BlueCatbirdChatNamespace.getSubscriptionTicket(
input: BlueCatbirdChatGetSubscriptionTicketInput): ATProtoResponse<BlueCatbirdChatGetSubscriptionTicketOutput> {
    val endpoint = "blue.catbird.chat.getSubscriptionTicket"

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
