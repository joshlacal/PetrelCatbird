// Lexicon: 1, ID: blue.catbird.chat.getEntries
// Reads entries visible to the exact authenticated (DID,deviceId), strictly after global scan position afterSeq. Application intervals are per concrete MLS leaf: creation opens the genesis device at creation seq, Add opens only that device at its Welcome-producing transition seq, and that device's own Remove or reset closes it inclusively. Reset opens only the activator device at the same seq. Sibling, roster-only, and zero-leaf devices inherit no application ciphertext; re-Add opens a new interval without backfill. afterSeq need not lie in an interval. The server skips inaccessible gaps and never returns their entries. nextAfterSeq is afterSeq when entries is empty, otherwise the greatest returned seq; hasMore is true exactly when another caller-visible application or separately entitled control entry has seq greater than nextAfterSeq. AccessOutsideMembershipInterval means the caller has neither a concrete leaf interval nor separate control entitlement, not that afterSeq lies in a gap. Clients never fetch a gap to resolve targets.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetEntriesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getEntries"
}

@Serializable
    data class BlueCatbirdChatGetEntriesParameters(
// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 device ID; strict runtime validation rejects every other spelling, version, or variant.        @SerialName("actorDeviceId")
        val actorDeviceId: String,// Canonical lowercase hyphenated RFC 4122-variant UUIDv4 conversation ID; strict runtime validation rejects every other spelling/version/variant.        @SerialName("conversationId")
        val conversationId: String,        @SerialName("afterSeq")
        val afterSeq: Int,        @SerialName("limit")
        val limit: Int    )

    @Serializable
    data class BlueCatbirdChatGetEntriesOutput(
        @SerialName("entries")
        val entries: List<BlueCatbirdChatDefsConversationEntry>,        @SerialName("nextAfterSeq")
        val nextAfterSeq: Int,        @SerialName("hasMore")
        val hasMore: Boolean    )

sealed class BlueCatbirdChatGetEntriesError(val name: String, val description: String?) {
        object AccessOutsideMembershipInterval: BlueCatbirdChatGetEntriesError("AccessOutsideMembershipInterval", "")
        object ConversationNotFound: BlueCatbirdChatGetEntriesError("ConversationNotFound", "")
        object CutoverRequired: BlueCatbirdChatGetEntriesError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetEntriesError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetEntriesError("DeviceRevoked", "")
        object InvalidRequest: BlueCatbirdChatGetEntriesError("InvalidRequest", "")
        object NotEntitled: BlueCatbirdChatGetEntriesError("NotEntitled", "")
        object AccountSessionExpired: BlueCatbirdChatGetEntriesError("AccountSessionExpired", "")
        object NotAuthorized: BlueCatbirdChatGetEntriesError("NotAuthorized", "")
        object DeviceBindingMismatch: BlueCatbirdChatGetEntriesError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatGetEntriesError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatGetEntriesError("RateLimited", "")
    }

/**
 * Reads entries visible to the exact authenticated (DID,deviceId), strictly after global scan position afterSeq. Application intervals are per concrete MLS leaf: creation opens the genesis device at creation seq, Add opens only that device at its Welcome-producing transition seq, and that device's own Remove or reset closes it inclusively. Reset opens only the activator device at the same seq. Sibling, roster-only, and zero-leaf devices inherit no application ciphertext; re-Add opens a new interval without backfill. afterSeq need not lie in an interval. The server skips inaccessible gaps and never returns their entries. nextAfterSeq is afterSeq when entries is empty, otherwise the greatest returned seq; hasMore is true exactly when another caller-visible application or separately entitled control entry has seq greater than nextAfterSeq. AccessOutsideMembershipInterval means the caller has neither a concrete leaf interval nor separate control entitlement, not that afterSeq lies in a gap. Clients never fetch a gap to resolve targets.
 *
 * Endpoint: blue.catbird.chat.getEntries
 */
suspend fun BlueCatbirdChatNamespace.getEntries(
parameters: BlueCatbirdChatGetEntriesParameters): ATProtoResponse<BlueCatbirdChatGetEntriesOutput> {
    val endpoint = "blue.catbird.chat.getEntries"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
