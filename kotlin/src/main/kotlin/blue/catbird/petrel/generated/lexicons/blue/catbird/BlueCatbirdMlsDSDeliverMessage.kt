// Lexicon: 1, ID: blue.catbird.mlsDS.deliverMessage
// Accept an inbound MLS application message from a remote sequencer DS for a local recipient. Deliver a federated MLS application message to a local recipient on the destination DS.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSDeliverMessageDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.deliverMessage"
}

@Serializable
    data class BlueCatbirdMlsDSDeliverMessageInput(
        @SerialName("header")
        val header: BlueCatbirdMlsDSDefsEnvelopeHeaderV1,// Canonical DAG-CBOR bytes of the exact applicationEntry        @SerialName("entryBytes")
        val entryBytes: Bytes,// Exact JSON bytes of the signed message request        @SerialName("signedRequestBytes")
        val signedRequestBytes: Bytes,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("entryLocator")
        val entryLocator: BlueCatbirdMlsDSDefsEntryLocatorV1    )

    @Serializable
    data class BlueCatbirdMlsDSDeliverMessageOutput(
// Whether the message was delivered and persisted        @SerialName("accepted")
        val accepted: Boolean,        @SerialName("receipt")
        val receipt: BlueCatbirdMlsDSDefsFederationReceiptV1    )

sealed class BlueCatbirdMlsDSDeliverMessageError(val name: String, val description: String?) {
        object MailboxNotProvisioned: BlueCatbirdMlsDSDeliverMessageError("MailboxNotProvisioned", "")
        object SequenceConflict: BlueCatbirdMlsDSDeliverMessageError("SequenceConflict", "")
        object DeliveryConflict: BlueCatbirdMlsDSDeliverMessageError("DeliveryConflict", "")
        object NotSequencer: BlueCatbirdMlsDSDeliverMessageError("NotSequencer", "")
        object TermStale: BlueCatbirdMlsDSDeliverMessageError("TermStale", "")
        object InvalidEnvelope: BlueCatbirdMlsDSDeliverMessageError("InvalidEnvelope", "")
        object UnauthorizedRecipient: BlueCatbirdMlsDSDeliverMessageError("UnauthorizedRecipient", "")
    }

/**
 * Accept an inbound MLS application message from a remote sequencer DS for a local recipient. Deliver a federated MLS application message to a local recipient on the destination DS.
 *
 * Endpoint: blue.catbird.mlsDS.deliverMessage
 */
suspend fun BlueCatbirdMlsDSNamespace.deliverMessage(
input: BlueCatbirdMlsDSDeliverMessageInput): ATProtoResponse<BlueCatbirdMlsDSDeliverMessageOutput> {
    val endpoint = "blue.catbird.mlsDS.deliverMessage"

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
