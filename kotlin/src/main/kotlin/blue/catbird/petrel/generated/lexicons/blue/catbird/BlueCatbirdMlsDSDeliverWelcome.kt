// Lexicon: 1, ID: blue.catbird.mlsDS.deliverWelcome
// Accept and verify an inbound MLS Welcome message from a remote sequencer DS for a preprovisioned local recipient mailbox. Deliver a federated MLS Welcome message to complete local preprovisioned participant addition on the destination DS.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSDeliverWelcomeDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.deliverWelcome"
}

@Serializable
    data class BlueCatbirdMlsDSDeliverWelcomeInput(
        @SerialName("header")
        val header: BlueCatbirdMlsDSDefsEnvelopeHeaderV1,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("keyPackageRef")
        val keyPackageRef: Bytes,// Serialized MLS Welcome message bytes        @SerialName("welcomeBytes")
        val welcomeBytes: Bytes,        @SerialName("welcomeSha256")
        val welcomeSha256: Bytes,// Canonical DAG-CBOR bytes of the exact control entry        @SerialName("entryBytes")
        val entryBytes: Bytes,// Exact JSON bytes of the signed transition request        @SerialName("signedRequestBytes")
        val signedRequestBytes: Bytes,        @SerialName("entryLocator")
        val entryLocator: BlueCatbirdMlsDSDefsEntryLocatorV1,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("publicSnapshotSha256")
        val publicSnapshotSha256: Bytes,        @SerialName("treeSummarySha256")
        val treeSummarySha256: Bytes    )

    @Serializable
    data class BlueCatbirdMlsDSDeliverWelcomeOutput(
// Whether the welcome was accepted and verified against preprovisioned mailbox state        @SerialName("accepted")
        val accepted: Boolean,        @SerialName("receipt")
        val receipt: BlueCatbirdMlsDSDefsFederationReceiptV1    )

sealed class BlueCatbirdMlsDSDeliverWelcomeError(val name: String, val description: String?) {
        object MailboxNotProvisioned: BlueCatbirdMlsDSDeliverWelcomeError("MailboxNotProvisioned", "")
        object DeliveryConflict: BlueCatbirdMlsDSDeliverWelcomeError("DeliveryConflict", "")
        object NotSequencer: BlueCatbirdMlsDSDeliverWelcomeError("NotSequencer", "")
        object TermStale: BlueCatbirdMlsDSDeliverWelcomeError("TermStale", "")
        object InvalidEnvelope: BlueCatbirdMlsDSDeliverWelcomeError("InvalidEnvelope", "")
        object UnauthorizedRecipient: BlueCatbirdMlsDSDeliverWelcomeError("UnauthorizedRecipient", "")
    }

/**
 * Accept and verify an inbound MLS Welcome message from a remote sequencer DS for a preprovisioned local recipient mailbox. Deliver a federated MLS Welcome message to complete local preprovisioned participant addition on the destination DS.
 *
 * Endpoint: blue.catbird.mlsDS.deliverWelcome
 */
suspend fun BlueCatbirdMlsDSNamespace.deliverWelcome(
input: BlueCatbirdMlsDSDeliverWelcomeInput): ATProtoResponse<BlueCatbirdMlsDSDeliverWelcomeOutput> {
    val endpoint = "blue.catbird.mlsDS.deliverWelcome"

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
