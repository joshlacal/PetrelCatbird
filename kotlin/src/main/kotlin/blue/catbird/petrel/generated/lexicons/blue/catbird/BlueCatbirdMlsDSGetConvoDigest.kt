// Lexicon: 1, ID: blue.catbird.mlsDS.getConvoDigest
// Get a cryptographic digest of all messages in a conversation for reconciliation. Compute and return a SHA-256 digest over all messages in a conversation, along with sequencer state.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSGetConvoDigestDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.getConvoDigest"
}

@Serializable
    data class BlueCatbirdMlsDSGetConvoDigestParameters(
// Conversation ID        @SerialName("convoId")
        val convoId: String    )

    @Serializable
    data class BlueCatbirdMlsDSGetConvoDigestOutput(
// Conversation ID        @SerialName("convoId")
        val convoId: String,// DID of the current sequencer DS        @SerialName("sequencerDsDid")
        val sequencerDsDid: String,// Current sequencer term        @SerialName("sequencerTerm")
        val sequencerTerm: Int,// Current MLS epoch        @SerialName("epoch")
        val epoch: Int,// Highest sequence number        @SerialName("lastSeq")
        val lastSeq: Int,// Total number of events/messages        @SerialName("eventCount")
        val eventCount: Int,// Hex-encoded SHA-256 digest over all messages        @SerialName("digestSha256")
        val digestSha256: String,// Timestamp when the digest was generated        @SerialName("generatedAt")
        val generatedAt: ATProtocolDate    )

sealed class BlueCatbirdMlsDSGetConvoDigestError(val name: String, val description: String?) {
        object ConversationNotFound: BlueCatbirdMlsDSGetConvoDigestError("ConversationNotFound", "")
        object NotAuthorized: BlueCatbirdMlsDSGetConvoDigestError("NotAuthorized", "")
    }

/**
 * Get a cryptographic digest of all messages in a conversation for reconciliation. Compute and return a SHA-256 digest over all messages in a conversation, along with sequencer state.
 *
 * Endpoint: blue.catbird.mlsDS.getConvoDigest
 */
suspend fun BlueCatbirdMlsDSNamespace.getConvoDigest(
parameters: BlueCatbirdMlsDSGetConvoDigestParameters): ATProtoResponse<BlueCatbirdMlsDSGetConvoDigestOutput> {
    val endpoint = "blue.catbird.mlsDS.getConvoDigest"

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
