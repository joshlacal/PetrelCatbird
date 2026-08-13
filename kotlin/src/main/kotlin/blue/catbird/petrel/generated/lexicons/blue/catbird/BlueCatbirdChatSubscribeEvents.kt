// Lexicon: 1, ID: blue.catbird.chat.subscribeEvents
// The sole non-DPoP endpoint: browser WebSocket upgrade authentication uses only the one-use short-lived getSubscriptionTicket token. cursor must byte-equal the cursor bound into the DID/device/JKT/authGeneration/path-bound ticket, which is consumed atomically before upgrade; mismatch, reuse, or expiry rejects. Streams durable entitlement-filtered envelopes plus uncursored best-effort typing variants. For durable envelopes previousCursor is the immediately preceding visible audience cursor and the first continues the ticket cursor. Typing events never alter this chain.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatSubscribeEventsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.subscribeEvents"
}

@Serializable
    data class BlueCatbirdChatSubscribeEventsParameters(
        @SerialName("ticket")
        val ticket: String,        @SerialName("cursor")
        val cursor: String    )

    @Serializable
    class BlueCatbirdChatSubscribeEventsMessage

sealed class BlueCatbirdChatSubscribeEventsError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatSubscribeEventsError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatSubscribeEventsError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatSubscribeEventsError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatSubscribeEventsError("DeviceRevoked", "")
        object InvalidTicket: BlueCatbirdChatSubscribeEventsError("InvalidTicket", "")
    }

/**
 * The sole non-DPoP endpoint: browser WebSocket upgrade authentication uses only the one-use short-lived getSubscriptionTicket token. cursor must byte-equal the cursor bound into the DID/device/JKT/authGeneration/path-bound ticket, which is consumed atomically before upgrade; mismatch, reuse, or expiry rejects. Streams durable entitlement-filtered envelopes plus uncursored best-effort typing variants. For durable envelopes previousCursor is the immediately preceding visible audience cursor and the first continues the ticket cursor. Typing events never alter this chain.
 *
 * Endpoint: blue.catbird.chat.subscribeEvents
 *
 * The returned [kotlinx.coroutines.flow.Flow] completes (or throws) when the
 * underlying WebSocket disconnects. Reconnect / cursor-resume is the caller's
 * responsibility — wrap in `retryWhen { ... }` with backoff as needed.
 */
fun BlueCatbirdChatNamespace.subscribeEvents(
parameters: BlueCatbirdChatSubscribeEventsParameters? = null,
hostOverride: String? = null,
    websocketClient: io.ktor.client.HttpClient? = null,
): kotlinx.coroutines.flow.Flow<BlueCatbirdChatSubscribeEventsMessage> = kotlinx.coroutines.flow.flow {
    val endpoint = "blue.catbird.chat.subscribeEvents"
    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?collections=a&collections=b`).
    val queryItems = parameters?.toQueryItems().orEmpty()

    client.openSubscription(endpoint, queryItems, hostOverride, websocketClient) { frame ->
        // Message schema is not a union; the generated `BlueCatbirdChatSubscribeEventsMessage` type
        // carries no variant information, so we emit a single best-effort decode
        // of every binary frame's payload. Subscriptions without union schemas
        // are rare in practice — see docs/plans/2026-04-19-kotlin-subscription-codegen.md.
        when (frame) {
            is blue.catbird.petrel.runtime.subscription.CborFrame.Message -> {
                // Non-union subscription: surface the raw payload to consumers
                // via kotlinx.serialization.json.JsonObject under a placeholder.
                emit(BlueCatbirdChatSubscribeEventsMessage())
            }
            is blue.catbird.petrel.runtime.subscription.CborFrame.Error -> {
                throw io.ktor.utils.io.errors.IOException(
                    "Subscription error frame: ${frame.name}: ${frame.message ?: "(no detail)"}"
                )
            }
        }
    }
}
