// Lexicon: 1, ID: blue.catbird.chat.subscribeEvents
// The direct WebSocket upgrade authenticates only with the one-use short-lived getSubscriptionTicket token. cursor must byte-equal the cursor bound into the DID/device/authGeneration/path-bound ticket, which is consumed atomically before upgrade; mismatch, reuse, or expiry rejects. Streams durable entitlement-filtered envelopes plus uncursored best-effort typing variants. For durable envelopes previousCursor is the immediately preceding visible audience cursor and the first continues the ticket cursor. Typing events never alter this chain.
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

typealias BlueCatbirdChatSubscribeEventsMessage = BlueCatbirdChatDefsSubscriptionMessage

sealed class BlueCatbirdChatSubscribeEventsError(val name: String, val description: String?) {
        object CursorExpired: BlueCatbirdChatSubscribeEventsError("CursorExpired", "")
        object CutoverRequired: BlueCatbirdChatSubscribeEventsError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatSubscribeEventsError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatSubscribeEventsError("DeviceRevoked", "")
        object InvalidTicket: BlueCatbirdChatSubscribeEventsError("InvalidTicket", "")
        object AccountSessionExpired: BlueCatbirdChatSubscribeEventsError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatSubscribeEventsError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatSubscribeEventsError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatSubscribeEventsError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatSubscribeEventsError("RateLimited", "")
    }

/**
 * Synthetic variants augmenting the generated BlueCatbirdChatDefsSubscriptionMessage sealed interface.
 *
 * `Error` surfaces ATProto `op == -1` server error frames; `Unexpected` wraps
 * frames whose header tag did not match any known variant (e.g. new event types
 * added server-side before client regen). Kept as extensions so the lexicon-
 * driven sealed interface stays mechanically faithful to the schema.
 */
data class BlueCatbirdChatDefsSubscriptionMessageError(val name: String, val message: String?) : BlueCatbirdChatDefsSubscriptionMessage
data class BlueCatbirdChatDefsSubscriptionMessageUnexpected(val type: String, val payload: kotlinx.serialization.json.JsonObject) : BlueCatbirdChatDefsSubscriptionMessage

/**
 * The direct WebSocket upgrade authenticates only with the one-use short-lived getSubscriptionTicket token. cursor must byte-equal the cursor bound into the DID/device/authGeneration/path-bound ticket, which is consumed atomically before upgrade; mismatch, reuse, or expiry rejects. Streams durable entitlement-filtered envelopes plus uncursored best-effort typing variants. For durable envelopes previousCursor is the immediately preceding visible audience cursor and the first continues the ticket cursor. Typing events never alter this chain.
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
        val decoded: BlueCatbirdChatSubscribeEventsMessage = when (frame) {
            is blue.catbird.petrel.runtime.subscription.CborFrame.Error ->
                BlueCatbirdChatDefsSubscriptionMessageError(frame.name, frame.message)
            is blue.catbird.petrel.runtime.subscription.CborFrame.Message -> {
                val json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                try {
                    when (frame.header.t) {
                        "#eventEnvelope" -> BlueCatbirdChatDefsSubscriptionMessage.EventEnvelope(
                            json.decodeFromJsonElement(
                                blue.catbird.petrel.generated.BlueCatbirdChatDefsEventEnvelope.serializer(),
                                frame.payload
                            )
                        )
                        "#typingEvent" -> BlueCatbirdChatDefsSubscriptionMessage.TypingEvent(
                            json.decodeFromJsonElement(
                                blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingEvent.serializer(),
                                frame.payload
                            )
                        )
                        else -> BlueCatbirdChatDefsSubscriptionMessageUnexpected(frame.header.t, frame.payload)
                    }
                } catch (e: Throwable) {
                    BlueCatbirdChatDefsSubscriptionMessageUnexpected(frame.header.t, frame.payload)
                }
            }
        }
        emit(decoded)
    }
}
