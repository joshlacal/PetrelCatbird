// Lexicon: 1, ID: blue.catbird.mlsChat.reportSpam
// Report an account as spam in an MLS conversation.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatReportSpamDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.reportSpam"
}

@Serializable
    data class BlueCatbirdMlsChatReportSpamInput(
// The conversation ID        @SerialName("convoId")
        val convoId: String,// DID of the account being reported        @SerialName("reportedDid")
        val reportedDid: DID,// Optional reason for the report        @SerialName("reason")
        val reason: String? = null    )

    @Serializable
    data class BlueCatbirdMlsChatReportSpamOutput(
        @SerialName("id")
        val id: String,        @SerialName("createdAt")
        val createdAt: ATProtocolDate    )

sealed class BlueCatbirdMlsChatReportSpamError(val name: String, val description: String?) {
        object ConversationNotFound: BlueCatbirdMlsChatReportSpamError("ConversationNotFound", "")
        object NotAMember: BlueCatbirdMlsChatReportSpamError("NotAMember", "")
        object AlreadyReported: BlueCatbirdMlsChatReportSpamError("AlreadyReported", "")
    }

/**
 * Report an account as spam in an MLS conversation.
 *
 * Endpoint: blue.catbird.mlsChat.reportSpam
 */
suspend fun BlueCatbirdMlsChatNamespace.reportSpam(
input: BlueCatbirdMlsChatReportSpamInput): ATProtoResponse<BlueCatbirdMlsChatReportSpamOutput> {
    val endpoint = "blue.catbird.mlsChat.reportSpam"

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
