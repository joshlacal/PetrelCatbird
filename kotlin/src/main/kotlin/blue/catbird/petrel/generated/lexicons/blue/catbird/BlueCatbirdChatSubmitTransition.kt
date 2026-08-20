// Lexicon: 1, ID: blue.catbird.chat.submitTransition
// Submits exactly one closed signed transition: ordinary public Commit, group-only policy change, leaf-recovery fulfillment, metadata update, or group leave fulfillment. Generic signedCommitTransition has zero Add proposals and zero membership changes; only signedLeafRecoveryFulfillment may contain exactly one target-device-signed request-bound Add, fulfilled by a different current leaf with one Welcome. No admin/current leaf can reserve and Add another device. Direct participant and role mutations are forbidden. Group invitation changes recheck consent, pending limits, and every-pair block policy; recovery selection and final Add consume a fresh complete exact-scope policy projection under lock. Self-exit fulfillment is never denied by relationship state. Epoch-changing forms carry a fresh-nonce final-epoch metadata snapshot. The server validates and merges the public Commit in disposable OpenMLS storage before atomically replacing its digest-bound deterministic PublicGroup snapshot. External commits, standalone proposals, post-GroupInfo artifacts, and committer self-removal are forbidden.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatSubmitTransitionDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.submitTransition"
}

@Serializable
    data class BlueCatbirdChatSubmitTransitionInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedTransition    )

    @Serializable
    data class BlueCatbirdChatSubmitTransitionOutput(
        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsConversationEntry,        @SerialName("welcomes")
        val welcomes: List<BlueCatbirdChatDefsWelcomeView>    )

sealed class BlueCatbirdChatSubmitTransitionError(val name: String, val description: String?) {
        object AdminRequired: BlueCatbirdChatSubmitTransitionError("AdminRequired", "")
        object BlockedRelationship: BlueCatbirdChatSubmitTransitionError("BlockedRelationship", "")
        object CommitterSelfRemovalForbidden: BlueCatbirdChatSubmitTransitionError("CommitterSelfRemovalForbidden", "")
        object ConversationLeafLimitReached: BlueCatbirdChatSubmitTransitionError("ConversationLeafLimitReached", "")
        object ConversationNotFound: BlueCatbirdChatSubmitTransitionError("ConversationNotFound", "")
        object CoordinateOverflow: BlueCatbirdChatSubmitTransitionError("CoordinateOverflow", "")
        object CutoverRequired: BlueCatbirdChatSubmitTransitionError("CutoverRequired", "")
        object DeviceNotLeaf: BlueCatbirdChatSubmitTransitionError("DeviceNotLeaf", "")
        object DeviceNotRegistered: BlueCatbirdChatSubmitTransitionError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatSubmitTransitionError("DeviceRevoked", "")
        object DirectParticipantMutationForbidden: BlueCatbirdChatSubmitTransitionError("DirectParticipantMutationForbidden", "")
        object DuplicateDeviceLeaf: BlueCatbirdChatSubmitTransitionError("DuplicateDeviceLeaf", "")
        object ExternalCommitForbidden: BlueCatbirdChatSubmitTransitionError("ExternalCommitForbidden", "")
        object GroupInvitesDisabled: BlueCatbirdChatSubmitTransitionError("GroupInvitesDisabled", "")
        object IdempotencyConflict: BlueCatbirdChatSubmitTransitionError("IdempotencyConflict", "")
        object InvalidCommit: BlueCatbirdChatSubmitTransitionError("InvalidCommit", "")
        object InvalidLeaveManifest: BlueCatbirdChatSubmitTransitionError("InvalidLeaveManifest", "")
        object InvalidMetadataSnapshot: BlueCatbirdChatSubmitTransitionError("InvalidMetadataSnapshot", "")
        object InvalidRequest: BlueCatbirdChatSubmitTransitionError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatSubmitTransitionError("InvalidSignature", "")
        object InvalidWelcomeMapping: BlueCatbirdChatSubmitTransitionError("InvalidWelcomeMapping", "")
        object InvitationLimitReached: BlueCatbirdChatSubmitTransitionError("InvitationLimitReached", "")
        object LastAdminRequired: BlueCatbirdChatSubmitTransitionError("LastAdminRequired", "")
        object LeafRecoveryExpired: BlueCatbirdChatSubmitTransitionError("LeafRecoveryExpired", "")
        object LeafRecoveryNotFound: BlueCatbirdChatSubmitTransitionError("LeafRecoveryNotFound", "")
        object LeafRecoverySuperseded: BlueCatbirdChatSubmitTransitionError("LeafRecoverySuperseded", "")
        object LeaveRequestExpired: BlueCatbirdChatSubmitTransitionError("LeaveRequestExpired", "")
        object LeaveRequestNotFound: BlueCatbirdChatSubmitTransitionError("LeaveRequestNotFound", "")
        object LeaveRequestStale: BlueCatbirdChatSubmitTransitionError("LeaveRequestStale", "")
        object MetadataNonceReuse: BlueCatbirdChatSubmitTransitionError("MetadataNonceReuse", "")
        object MetadataVersionOverflow: BlueCatbirdChatSubmitTransitionError("MetadataVersionOverflow", "")
        object MissingMetadataSnapshot: BlueCatbirdChatSubmitTransitionError("MissingMetadataSnapshot", "")
        object NotAuthorized: BlueCatbirdChatSubmitTransitionError("NotAuthorized", "")
        object NotFollowedByRecipient: BlueCatbirdChatSubmitTransitionError("NotFollowedByRecipient", "")
        object NotMember: BlueCatbirdChatSubmitTransitionError("NotMember", "")
        object ParticipantLeafLimitReached: BlueCatbirdChatSubmitTransitionError("ParticipantLeafLimitReached", "")
        object ParticipantLimitReached: BlueCatbirdChatSubmitTransitionError("ParticipantLimitReached", "")
        object RelationshipPolicyUnavailable: BlueCatbirdChatSubmitTransitionError("RelationshipPolicyUnavailable", "")
        object StaleCoordinates: BlueCatbirdChatSubmitTransitionError("StaleCoordinates", "")
        object StandaloneProposalForbidden: BlueCatbirdChatSubmitTransitionError("StandaloneProposalForbidden", "")
        object UnsupportedMlsProfile: BlueCatbirdChatSubmitTransitionError("UnsupportedMlsProfile", "")
        object AccountSessionExpired: BlueCatbirdChatSubmitTransitionError("AccountSessionExpired", "")
        object DeviceBindingMismatch: BlueCatbirdChatSubmitTransitionError("DeviceBindingMismatch", "")
        object ProtocolUpgradeRequired: BlueCatbirdChatSubmitTransitionError("ProtocolUpgradeRequired", "")
        object RateLimited: BlueCatbirdChatSubmitTransitionError("RateLimited", "")
    }

/**
 * Submits exactly one closed signed transition: ordinary public Commit, group-only policy change, leaf-recovery fulfillment, metadata update, or group leave fulfillment. Generic signedCommitTransition has zero Add proposals and zero membership changes; only signedLeafRecoveryFulfillment may contain exactly one target-device-signed request-bound Add, fulfilled by a different current leaf with one Welcome. No admin/current leaf can reserve and Add another device. Direct participant and role mutations are forbidden. Group invitation changes recheck consent, pending limits, and every-pair block policy; recovery selection and final Add consume a fresh complete exact-scope policy projection under lock. Self-exit fulfillment is never denied by relationship state. Epoch-changing forms carry a fresh-nonce final-epoch metadata snapshot. The server validates and merges the public Commit in disposable OpenMLS storage before atomically replacing its digest-bound deterministic PublicGroup snapshot. External commits, standalone proposals, post-GroupInfo artifacts, and committer self-removal are forbidden.
 *
 * Endpoint: blue.catbird.chat.submitTransition
 */
suspend fun BlueCatbirdChatNamespace.submitTransition(
input: BlueCatbirdChatSubmitTransitionInput): ATProtoResponse<BlueCatbirdChatSubmitTransitionOutput> {
    val endpoint = "blue.catbird.chat.submitTransition"

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
