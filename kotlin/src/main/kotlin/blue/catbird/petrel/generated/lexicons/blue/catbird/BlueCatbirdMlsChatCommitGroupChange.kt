// Lexicon: 1, ID: blue.catbird.mlsChat.commitGroupChange
// Commit MLS group membership changes (consolidates addMembers + processExternalCommit + rejoin + readdition + getPendingDeviceAdditions + claimPendingDeviceAddition + completePendingDeviceAddition) Perform MLS group membership operations. The 'action' field determines the operation type. This consolidates all membership-changing operations into a single endpoint.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatCommitGroupChangeDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.commitGroupChange"
}

@OptIn(ExperimentalSerializationApi::class)
private object BlueCatbirdMlsChatCommitGroupChangeOutputReceiptStrictReferenceSerializer : KSerializer<BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt?> {
    private val delegate = BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt.serializer()
    private const val expectedTypeIdentifier = "blue.catbird.mlsChat.commitGroupChange#sequencerReceipt"
    override val descriptor = delegate.descriptor

    override fun serialize(
        encoder: kotlinx.serialization.encoding.Encoder,
        value: BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt?,
    ) {
        if (value == null) {
            val jsonEncoder = encoder as? JsonEncoder
                ?: throw SerializationException("Strict reference requires JSON encoding")
            jsonEncoder.encodeJsonElement(JsonNull)
        } else {
            delegate.serialize(encoder, value)
        }
    }

    override fun deserialize(
        decoder: kotlinx.serialization.encoding.Decoder,
    ): BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("Strict reference requires JSON decoding")
        val element = jsonDecoder.decodeJsonElement()
        if (element is JsonNull) return null
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

    /**
     * Signed receipt binding an accepted commit to its conversation, epoch, sequencer term, and sequencer identity.
     */
    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt(
        /** Stable conversation identifier. */
        @SerialName("convoId")
        val convoId: String,
        /** Epoch assigned to the accepted commit. */
        @SerialName("epoch")
        val epoch: Int,
        /** Active sequencer leadership term included in the signed receipt bytes. */
        @SerialName("sequencerTerm")
        val sequencerTerm: Int,
        /** Cryptographic hash of the accepted MLS commit bytes. */
        @SerialName("commitHash")
        val commitHash: Bytes,
        /** DID of the sequencer that signed the receipt. */
        @SerialName("sequencerDid")
        val sequencerDid: DID,
        /** Unix timestamp at which the receipt was issued. */
        @SerialName("issuedAt")
        val issuedAt: Int,
        /** Signature over the canonical CATBIRD-RECEIPT-V1 payload. */
        @SerialName("signature")
        val signature: Bytes
    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatCommitGroupChangeSequencerReceipt"
        }
    }

    /**
     * Structured 429 response body for External-Commit rate-limit rejections. Wire shape locked with clients so retryAfterSeconds is parseable without scraping the message string. Sent for both per-conversation (30s) and per-(device, group) (60s) limits, distinguished by `scope`.
     */
    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeRateLimitedBody(
/** Always 'RateLimited'. */        @SerialName("error")
        val error: String,/** Human-readable explanation. */        @SerialName("message")
        val message: String,/** Seconds the client should wait before retrying. Mirrors the HTTP Retry-After header. */        @SerialName("retryAfterSeconds")
        val retryAfterSeconds: Int,/** 'convo' = per-conversation 30s limit. 'device-convo' = per-(device, group) 60s cooldown. */        @SerialName("scope")
        val scope: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatCommitGroupChangeRateLimitedBody"
        }
    }

    /**
     * Structured 423 response body for GroupFrozen epoch-storm circuit-breaker rejections. Sent when the conversation is temporarily frozen and epoch-advancing operations should back off.
     */
    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeGroupFrozenBody(
/** Always 'GroupFrozen'. */        @SerialName("error")
        val error: String,/** Human-readable explanation. */        @SerialName("message")
        val message: String,/** Seconds the client should wait before retrying. Mirrors the HTTP Retry-After header. */        @SerialName("retryAfterSeconds")
        val retryAfterSeconds: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatCommitGroupChangeGroupFrozenBody"
        }
    }

    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeKeyPackageHashEntry(
/** DID of the member */        @SerialName("did")
        val did: DID,/** Hex-encoded SHA-256 hash of the key package used */        @SerialName("hash")
        val hash: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatCommitGroupChangeKeyPackageHashEntry"
        }
    }

    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangePendingDeviceAddition(
/** Unique identifier for this pending addition */        @SerialName("id")
        val id: String,/** Conversation identifier */        @SerialName("convoId")
        val convoId: String,/** Base user DID (without device suffix) */        @SerialName("userDid")
        val userDid: DID,/** Device identifier */        @SerialName("deviceId")
        val deviceId: String,/** Human-readable device name */        @SerialName("deviceName")
        val deviceName: String? = null,/** Full device credential DID (format: did:plc:user#device-uuid) */        @SerialName("deviceCredentialDid")
        val deviceCredentialDid: String,/** Current status of the pending addition */        @SerialName("status")
        val status: String,/** DID of the member who claimed this addition (if in_progress) */        @SerialName("claimedBy")
        val claimedBy: DID? = null,/** When this pending addition was created */        @SerialName("createdAt")
        val createdAt: ATProtocolDate    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatCommitGroupChangePendingDeviceAddition"
        }
    }

@Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeInput(
// Conversation identifier        @SerialName("convoId")
        val convoId: String,// Membership action to perform        @SerialName("action")
        val action: String,// DIDs of members to add (required for 'addMembers')        @SerialName("memberDids")
        val memberDids: List<DID>? = null,// MLS Commit message (used by addMembers, processExternalCommit, rejoin)        @SerialName("commit")
        val commit: Bytes? = null,// MLS Welcome message (used by addMembers, completePendingDeviceAddition)        @SerialName("welcome")
        val welcome: Bytes? = null,// GroupInfo to update after commit (used by processExternalCommit, addMembers)        @SerialName("groupInfo")
        val groupInfo: Bytes? = null,// Key package hash mappings for new members (used by addMembers, completePendingDeviceAddition)        @SerialName("keyPackageHashes")
        val keyPackageHashes: List<BlueCatbirdMlsChatCommitGroupChangeKeyPackageHashEntry>? = null,// Device ID for pending device addition operations (used by claimPendingDeviceAddition)        @SerialName("deviceId")
        val deviceId: String? = null,// ID of the pending addition to claim or complete        @SerialName("pendingAdditionId")
        val pendingAdditionId: String? = null,// Client-generated UUID for idempotent retries        @SerialName("idempotencyKey")
        val idempotencyKey: String? = null,// MLS confirmation tag from the client's post-commit group state.        @SerialName("confirmationTag")
        val confirmationTag: Bytes? = null,// Hex-encoded epoch_authenticator (RFC 9420 §8.7) for the post-commit epoch. Optional. When present on an epoch-advancing action (addMembers, processExternalCommit, rejoin, commit, updateMetadata), the server records it in the epoch_authenticators table and uses it to validate future reportRecoveryFailure votes for quorum auto-reset (see ADR-002).        @SerialName("epochAuthenticator")
        val epochAuthenticator: String? = null,// Optional TransitionAttestationV1 challenge UUID. Must be present if and only if transitionSignature is present.        @SerialName("transitionChallengeId")
        val transitionChallengeId: String? = null,// Ed25519 signature over the exact challenge bytes. Must be present if and only if transitionChallengeId is present.        @SerialName("transitionSignature")
        val transitionSignature: Bytes? = null    )

    @Serializable
    data class BlueCatbirdMlsChatCommitGroupChangeOutput(
// Whether the operation succeeded        @SerialName("success")
        val success: Boolean,// New epoch number after the change (for addMembers, processExternalCommit, rejoin)        @SerialName("newEpoch")
        val newEpoch: Int? = null,// Timestamp of rejoin (for processExternalCommit, rejoin)        @SerialName("rejoinedAt")
        val rejoinedAt: ATProtocolDate? = null,// List of pending device additions (for getPendingDeviceAdditions)        @SerialName("pendingAdditions")
        val pendingAdditions: List<BlueCatbirdMlsChatCommitGroupChangePendingDeviceAddition>? = null,// The claimed pending addition (for claimPendingDeviceAddition)        @SerialName("claimedAddition")
        val claimedAddition: BlueCatbirdMlsChatCommitGroupChangePendingDeviceAddition? = null,// confirmation tag of the new canonical tree state.        @SerialName("confirmationTag")
        val confirmationTag: Bytes? = null,// Signed sequencer receipt for an accepted epoch-advancing commit. Absent for legacy servers and non-advancing actions.
                @Serializable(with = BlueCatbirdMlsChatCommitGroupChangeOutputReceiptStrictReferenceSerializer::class)
        @SerialName("receipt")
        val receipt: BlueCatbirdMlsChatCommitGroupChangeSequencerReceipt? = null    )

sealed class BlueCatbirdMlsChatCommitGroupChangeError(val name: String, val description: String?) {
        object ConvoNotFound: BlueCatbirdMlsChatCommitGroupChangeError("ConvoNotFound", "Conversation not found")
        object NotMember: BlueCatbirdMlsChatCommitGroupChangeError("NotMember", "Caller is not a member of the conversation")
        object InvalidAction: BlueCatbirdMlsChatCommitGroupChangeError("InvalidAction", "Unknown action value")
        object KeyPackageNotFound: BlueCatbirdMlsChatCommitGroupChangeError("KeyPackageNotFound", "Key package not found for one or more members")
        object AlreadyMember: BlueCatbirdMlsChatCommitGroupChangeError("AlreadyMember", "One or more DIDs are already members")
        object TooManyMembers: BlueCatbirdMlsChatCommitGroupChangeError("TooManyMembers", "Would exceed maximum member count")
        object BlockedByMember: BlueCatbirdMlsChatCommitGroupChangeError("BlockedByMember", "Cannot add user who has blocked or been blocked by an existing member")
        object InvalidCommit: BlueCatbirdMlsChatCommitGroupChangeError("InvalidCommit", "The provided MLS Commit message is invalid")
        object InvalidGroupInfo: BlueCatbirdMlsChatCommitGroupChangeError("InvalidGroupInfo", "The provided GroupInfo is invalid")
        object PendingAdditionNotFound: BlueCatbirdMlsChatCommitGroupChangeError("PendingAdditionNotFound", "The specified pending addition does not exist")
        object PendingAdditionAlreadyClaimed: BlueCatbirdMlsChatCommitGroupChangeError("PendingAdditionAlreadyClaimed", "The pending addition was already claimed by another member")
        object Unauthorized: BlueCatbirdMlsChatCommitGroupChangeError("Unauthorized", "Insufficient privileges for this operation")
        object NoKeyPackagesPublished: BlueCatbirdMlsChatCommitGroupChangeError("NoKeyPackagesPublished", "Layer 1 robustness gate: the calling device has zero published key packages and is therefore not eligible to issue an External Commit. The device must call publishKeyPackages and successfully publish at least one available, non-expired key package before retrying. Returned as HTTP 412 Precondition Failed.")
        object GroupFrozen: BlueCatbirdMlsChatCommitGroupChangeError("GroupFrozen", "Layer 1 robustness circuit breaker: the conversation has been temporarily frozen because the server detected an epoch-storm pattern (too many epoch advances within a short window). All epoch-advancing commits are rejected until the freeze auto-thaws. Returned as HTTP 423 Locked with body shaped per #groupFrozenBody (retryAfterSeconds).")
        object RateLimited: BlueCatbirdMlsChatCommitGroupChangeError("RateLimited", "Rate limit exceeded. Two cases: (a) per-conversation 30s External-Commit limit (existing). (b) per-(device, group) 60s External-Commit cooldown (Layer 1 §1.2). Both return HTTP 429 with body shaped per #rateLimitedBody (retryAfterSeconds + scope discriminator).")
    }

/**
 * Commit MLS group membership changes (consolidates addMembers + processExternalCommit + rejoin + readdition + getPendingDeviceAdditions + claimPendingDeviceAddition + completePendingDeviceAddition) Perform MLS group membership operations. The 'action' field determines the operation type. This consolidates all membership-changing operations into a single endpoint.
 *
 * Endpoint: blue.catbird.mlsChat.commitGroupChange
 */
suspend fun BlueCatbirdMlsChatNamespace.commitGroupChange(
input: BlueCatbirdMlsChatCommitGroupChangeInput): ATProtoResponse<BlueCatbirdMlsChatCommitGroupChangeOutput> {
    val endpoint = "blue.catbird.mlsChat.commitGroupChange"

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
