// Lexicon: 1, ID: blue.catbird.mlsChat.finalizeGroupChange
// Finalize an accepted private MLS transition after the client durably authenticates its sequencer receipt. Install the exact verified target GroupInfo for an accepted TransitionAttestationV1 challenge.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatFinalizeGroupChangeDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.finalizeGroupChange"
}

@OptIn(ExperimentalSerializationApi::class)
private object BlueCatbirdMlsChatFinalizeGroupChangeInputReceiptStrictReferenceSerializer : KSerializer<BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt> {
    private val delegate = BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt.serializer()
    private const val expectedTypeIdentifier = "blue.catbird.mlsChat.commitGroupChange#sequencerReceipt"
    override val descriptor = delegate.descriptor

    override fun serialize(
        encoder: kotlinx.serialization.encoding.Encoder,
        value: BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt,
    ) {
        delegate.serialize(encoder, value)
    }

    override fun deserialize(
        decoder: kotlinx.serialization.encoding.Decoder,
    ): BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("Strict reference requires JSON decoding")
        val element = jsonDecoder.decodeJsonElement()
        val objectValue = element as? JsonObject
            ?: throw SerializationException("Strict reference must be a JSON object")
        val allowedKeys = setOf("commitHash", "convoId", "epoch", "issuedAt", "sequencerDid", "sequencerTerm", "signature", "\$type")
        val unknownKeys = objectValue.keys - allowedKeys
        if (unknownKeys.isNotEmpty()) {
            throw SerializationException(
                "Strict reference contains unknown keys: ${unknownKeys.sorted()}"
            )
        }
        objectValue["\$type"]?.let { discriminator ->
            val actualTypeIdentifier = (discriminator as? JsonPrimitive)
                ?.takeIf(JsonPrimitive::isString)
                ?.content
                ?: throw SerializationException(
                    "Strict reference discriminator must be a string"
                )
            if (actualTypeIdentifier != expectedTypeIdentifier) {
                throw SerializationException("Strict reference discriminator mismatch")
            }
        }
        return jsonDecoder.json.decodeFromJsonElement(delegate, element)
    }
}

@Serializable
    data class BlueCatbirdMlsChatFinalizeGroupChangeInput(
// Stable printable-ASCII conversation identifier.        @SerialName("convoId")
        val convoId: String,// Accepted transition challenge RFC 4122 UUID in canonical hyphenated form.        @SerialName("challengeId")
        val challengeId: String,// Exact target GroupInfo bytes previously verified and bound by the challenge.        @SerialName("targetGroupInfo")
        val targetGroupInfo: Bytes,// Strictly decoded ADR-017 receipt returned by the accepted commit. Unknown or missing receipt fields fail closed.
                @Serializable(with = BlueCatbirdMlsChatFinalizeGroupChangeInputReceiptStrictReferenceSerializer::class)
        @SerialName("receipt")
        val receipt: BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt    )

    @Serializable
    data class BlueCatbirdMlsChatFinalizeGroupChangeOutput(
// Whether the exact accepted target was finalized.        @SerialName("success")
        val success: Boolean,// Finalized MLS epoch.        @SerialName("epoch")
        val epoch: Int,// SHA-256 of the exact finalized GroupInfo bytes.        @SerialName("groupInfoHash")
        val groupInfoHash: Bytes,// Server timestamp at which the target GroupInfo became current.        @SerialName("finalizedAt")
        val finalizedAt: ATProtocolDate    )

/**
 * Finalize an accepted private MLS transition after the client durably authenticates its sequencer receipt. Install the exact verified target GroupInfo for an accepted TransitionAttestationV1 challenge.
 *
 * Endpoint: blue.catbird.mlsChat.finalizeGroupChange
 */
suspend fun BlueCatbirdMlsChatNamespace.finalizeGroupChange(
input: BlueCatbirdMlsChatFinalizeGroupChangeInput): ATProtoResponse<BlueCatbirdMlsChatFinalizeGroupChangeOutput> {
    val endpoint = "blue.catbird.mlsChat.finalizeGroupChange"

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
