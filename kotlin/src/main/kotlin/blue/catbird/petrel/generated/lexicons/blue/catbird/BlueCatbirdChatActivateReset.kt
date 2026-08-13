// Lexicon: 1, ID: blue.catbird.chat.activateReset
// An active registered device of an active admin DID authorizes an exact unexpired pending request; it need not be an old-generation leaf. Activation preserves immutable conversationKind plus every participant status/role/provenance, atomically supersedes the old generation, closes every old application interval at the reset seq, and installs the activator-only epoch-zero successor leaf. Every other participant has zero successor leaves: active participants recover through their own signed requests and pending participants remain pending. There is no reset reservation, bootstrap Commit, or Welcome. Metadata is either the same signed content/version/origin reencrypted with a fresh nonce or an activator-signed empty prior-version-plus-one snapshot.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatActivateResetDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.activateReset"
}

@Serializable
    data class BlueCatbirdChatActivateResetInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedResetActivation    )

    @Serializable
    data class BlueCatbirdChatActivateResetOutput(
        @SerialName("retiredCoordinates")
        val retiredCoordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("successorCoordinates")
        val successorCoordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsResetActivationEntry    )

sealed class BlueCatbirdChatActivateResetError(val name: String, val description: String?) {
        object AdminRequired: BlueCatbirdChatActivateResetError("AdminRequired", "")
        object ConversationNotFound: BlueCatbirdChatActivateResetError("ConversationNotFound", "")
        object CoordinateOverflow: BlueCatbirdChatActivateResetError("CoordinateOverflow", "")
        object CutoverRequired: BlueCatbirdChatActivateResetError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatActivateResetError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatActivateResetError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatActivateResetError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatActivateResetError("InvalidDPoP", "")
        object InvalidGenesisGroupInfo: BlueCatbirdChatActivateResetError("InvalidGenesisGroupInfo", "")
        object InvalidMetadataSnapshot: BlueCatbirdChatActivateResetError("InvalidMetadataSnapshot", "")
        object InvalidMlsArtifact: BlueCatbirdChatActivateResetError("InvalidMlsArtifact", "")
        object InvalidRequest: BlueCatbirdChatActivateResetError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatActivateResetError("InvalidSignature", "")
        object MetadataNonceReuse: BlueCatbirdChatActivateResetError("MetadataNonceReuse", "")
        object NotAuthorized: BlueCatbirdChatActivateResetError("NotAuthorized", "")
        object NotMember: BlueCatbirdChatActivateResetError("NotMember", "")
        object ResetRequestNotFound: BlueCatbirdChatActivateResetError("ResetRequestNotFound", "")
        object ResetRequestStale: BlueCatbirdChatActivateResetError("ResetRequestStale", "")
        object StaleCoordinates: BlueCatbirdChatActivateResetError("StaleCoordinates", "")
        object UnsupportedMlsProfile: BlueCatbirdChatActivateResetError("UnsupportedMlsProfile", "")
    }

/**
 * An active registered device of an active admin DID authorizes an exact unexpired pending request; it need not be an old-generation leaf. Activation preserves immutable conversationKind plus every participant status/role/provenance, atomically supersedes the old generation, closes every old application interval at the reset seq, and installs the activator-only epoch-zero successor leaf. Every other participant has zero successor leaves: active participants recover through their own signed requests and pending participants remain pending. There is no reset reservation, bootstrap Commit, or Welcome. Metadata is either the same signed content/version/origin reencrypted with a fresh nonce or an activator-signed empty prior-version-plus-one snapshot.
 *
 * Endpoint: blue.catbird.chat.activateReset
 */
suspend fun BlueCatbirdChatNamespace.activateReset(
input: BlueCatbirdChatActivateResetInput): ATProtoResponse<BlueCatbirdChatActivateResetOutput> {
    val endpoint = "blue.catbird.chat.activateReset"

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
