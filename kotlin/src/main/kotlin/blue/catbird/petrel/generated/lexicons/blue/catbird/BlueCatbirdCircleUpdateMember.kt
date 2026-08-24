// Lexicon: 1, ID: blue.catbird.circle.updateMember
// Add or remove a member from a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleUpdateMemberDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.updateMember"
}

@Serializable
enum class BlueCatbirdCircleUpdateMemberInputAction {
    @SerialName("add")
    value_add,
    @SerialName("remove")
    value_remove}

@Serializable
    data class BlueCatbirdCircleUpdateMemberInput(
        @SerialName("space")
        val space: SpaceRef,        @SerialName("memberDid")
        val memberDid: DID,        @SerialName("action")
        val action: BlueCatbirdCircleUpdateMemberInputAction    )

    typealias BlueCatbirdCircleUpdateMemberOutput = BlueCatbirdCircleDefsOperation

sealed class BlueCatbirdCircleUpdateMemberError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleUpdateMemberError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleUpdateMemberError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleUpdateMemberError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleUpdateMemberError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleUpdateMemberError("UpstreamUnavailable", "")
    }

/**
 * Add or remove a member from a Circle Space
 *
 * Endpoint: blue.catbird.circle.updateMember
 */
suspend fun BlueCatbirdCircleNamespace.updateMember(
input: BlueCatbirdCircleUpdateMemberInput): ATProtoResponse<BlueCatbirdCircleUpdateMemberOutput> {
    val endpoint = "blue.catbird.circle.updateMember"

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
