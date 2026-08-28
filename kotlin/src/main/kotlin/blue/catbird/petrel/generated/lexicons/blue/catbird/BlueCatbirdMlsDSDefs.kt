// Lexicon: 1, ID: blue.catbird.mlsDS.defs
// Closed MLS v2 Clean Delivery Service federation definitions.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsDSDefsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsDS.defs"
}

@Serializable
enum class BlueCatbirdMlsDSDefsDefsProtocolVersion {
    @SerialName("1")
    value_1}

@Serializable
enum class BlueCatbirdMlsDSDefsDefsFederationEndpoint {
    @SerialName("blue.catbird.mlsDS.deliverWelcome")
    value_blue_u2e_catbird_u2e_mlsDS_u2e_deliverWelcome,
    @SerialName("blue.catbird.mlsDS.deliverMessage")
    value_blue_u2e_catbird_u2e_mlsDS_u2e_deliverMessage,
    @SerialName("blue.catbird.mlsDS.submitCommit")
    value_blue_u2e_catbird_u2e_mlsDS_u2e_submitCommit}

    @Serializable
    data class BlueCatbirdMlsDSDefsEnvelopeHeaderV1(
        @SerialName("protocolVersion")
        val protocolVersion: BlueCatbirdMlsDSDefsDefsProtocolVersion,        @SerialName("deliveryId")
        val deliveryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("senderDsDid")
        val senderDsDid: BlueCatbirdChatDefsBareDid,        @SerialName("receiverDsDid")
        val receiverDsDid: BlueCatbirdChatDefsBareDid,        @SerialName("sequencerDid")
        val sequencerDid: BlueCatbirdChatDefsBareDid,        @SerialName("sequencerTerm")
        val sequencerTerm: Int,/** The mailbox's canonical admission instant for this signed operation. The sequencer must use this exact value as the entry receivedAt so both nodes canonicalize identical entry bytes. */        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("payloadSha256")
        val payloadSha256: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsDSDefsEnvelopeHeaderV1"
        }
    }

    @Serializable
    data class BlueCatbirdMlsDSDefsEntryLocatorV1(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("acceptedPayloadSha256")
        val acceptedPayloadSha256: Bytes,        @SerialName("outerEntryFingerprint")
        val outerEntryFingerprint: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsDSDefsEntryLocatorV1"
        }
    }

    @Serializable
    data class BlueCatbirdMlsDSDefsFederationReceiptV1(
        @SerialName("protocolVersion")
        val protocolVersion: BlueCatbirdMlsDSDefsDefsProtocolVersion,        @SerialName("endpoint")
        val endpoint: BlueCatbirdMlsDSDefsDefsFederationEndpoint,        @SerialName("deliveryId")
        val deliveryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("senderDsDid")
        val senderDsDid: BlueCatbirdChatDefsBareDid,        @SerialName("receiverDsDid")
        val receiverDsDid: BlueCatbirdChatDefsBareDid,        @SerialName("sequencerDid")
        val sequencerDid: BlueCatbirdChatDefsBareDid,        @SerialName("sequencerTerm")
        val sequencerTerm: Int,        @SerialName("envelopeSha256")
        val envelopeSha256: Bytes,        @SerialName("resultSha256")
        val resultSha256: Bytes,        @SerialName("sourceLocator")
        val sourceLocator: BlueCatbirdMlsDSDefsEntryLocatorV1,        @SerialName("completedAt")
        val completedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsDSDefsFederationReceiptV1"
        }
    }
