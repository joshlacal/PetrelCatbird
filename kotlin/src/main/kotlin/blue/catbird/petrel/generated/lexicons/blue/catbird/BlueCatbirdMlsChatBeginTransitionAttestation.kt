// Lexicon: 1, ID: blue.catbird.mlsChat.beginTransitionAttestation
// Prepare a single-use, device-signed attestation challenge for a private MLS transition. Verify the proposed private MLS transition against one resolved conversation and authorized-device snapshot, then return the exact canonical challenge bytes to sign.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatBeginTransitionAttestationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.beginTransitionAttestation"
}

@Serializable
enum class BlueCatbirdMlsChatBeginTransitionAttestationInputAction {
    @SerialName("addMembers")
    value_addMembers,
    @SerialName("removeMembers")
    value_removeMembers,
    @SerialName("swapMembers")
    value_swapMembers,
    @SerialName("commit")
    value_commit,
    @SerialName("updateMetadata")
    value_updateMetadata}

    /**
     * Canonical device-level MLS roster delta. Entries are sorted by device credential DID bytes and their action-specific tiebreaker; duplicate credentials are invalid. The combined number of additions and removals must not exceed 50.
     */
    @Serializable
    data class BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceDelta(
        @SerialName("adds")
        val adds: List<BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceAdd>,        @SerialName("removes")
        val removes: List<BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceRemove>    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceDelta"
        }
    }

    /**
     * One device addition, sorted by credential DID bytes then KeyPackage hash bytes.
     */
    @Serializable
    data class BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceAdd(
/** Canonical ASCII base DID with exactly one nonempty device fragment. */        @SerialName("deviceCredentialDid")
        val deviceCredentialDid: String,/** SHA-256 of the exact KeyPackage bytes. */        @SerialName("keyPackageHash")
        val keyPackageHash: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceAdd"
        }
    }

    /**
     * One device removal, sorted by credential DID bytes then prior leaf index.
     */
    @Serializable
    data class BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceRemove(
/** Canonical ASCII base DID with exactly one nonempty device fragment. */        @SerialName("deviceCredentialDid")
        val deviceCredentialDid: String,/** Leaf index in the verified source GroupInfo. */        @SerialName("priorLeafIndex")
        val priorLeafIndex: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceRemove"
        }
    }

@Serializable
    data class BlueCatbirdMlsChatBeginTransitionAttestationInput(
// Stable printable-ASCII conversation identifier.        @SerialName("convoId")
        val convoId: String,// Closed TransitionAttestationV1 action vocabulary.        @SerialName("action")
        val action: BlueCatbirdMlsChatBeginTransitionAttestationInputAction,// Exact nonempty encrypted MLS commit bytes.        @SerialName("commit")
        val commit: Bytes,// Exact source or pre-merge GroupInfo bytes.        @SerialName("sourceGroupInfo")
        val sourceGroupInfo: Bytes,// Exact target or post-merge GroupInfo bytes.        @SerialName("targetGroupInfo")
        val targetGroupInfo: Bytes,// Target confirmation tag. Its length must match the conversation ciphersuite hash length.        @SerialName("targetConfirmationTag")
        val targetConfirmationTag: Bytes,// Client-generated RFC 4122 UUID in canonical hyphenated form.        @SerialName("idempotencyKey")
        val idempotencyKey: String,// Exact device-level roster delta derived from the verified source and target GroupInfos.        @SerialName("deviceDelta")
        val deviceDelta: BlueCatbirdMlsChatBeginTransitionAttestationTransitionDeviceDelta,// Exact MLS Welcome bytes. Required for addMembers and swapMembers; prohibited for all other actions.        @SerialName("welcome")
        val welcome: Bytes? = null,// Optional target epoch authenticator. When present, its length must match the conversation ciphersuite hash length.        @SerialName("epochAuthenticator")
        val epochAuthenticator: Bytes? = null    )

    @Serializable
    data class BlueCatbirdMlsChatBeginTransitionAttestationOutput(
// Server-generated RFC 4122 UUID in canonical hyphenated form.        @SerialName("challengeId")
        val challengeId: String,// Exact canonical TransitionAttestationV1 bytes. Sign verbatim without reconstruction or transformation.        @SerialName("challenge")
        val challenge: Bytes,// Challenge expiry. The lifetime is exactly 120 seconds from issuance.        @SerialName("expiresAt")
        val expiresAt: ATProtocolDate,// Transition attestation wire version.        @SerialName("version")
        val version: Int    )

/**
 * Prepare a single-use, device-signed attestation challenge for a private MLS transition. Verify the proposed private MLS transition against one resolved conversation and authorized-device snapshot, then return the exact canonical challenge bytes to sign.
 *
 * Endpoint: blue.catbird.mlsChat.beginTransitionAttestation
 */
suspend fun BlueCatbirdMlsChatNamespace.beginTransitionAttestation(
input: BlueCatbirdMlsChatBeginTransitionAttestationInput): ATProtoResponse<BlueCatbirdMlsChatBeginTransitionAttestationOutput> {
    val endpoint = "blue.catbird.mlsChat.beginTransitionAttestation"

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
