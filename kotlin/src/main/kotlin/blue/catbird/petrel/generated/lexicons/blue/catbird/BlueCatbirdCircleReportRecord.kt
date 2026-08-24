// Lexicon: 1, ID: blue.catbird.circle.reportRecord
// Report a record in a Circle Space
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleReportRecordDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.reportRecord"
}

@Serializable
enum class BlueCatbirdCircleReportRecordInputReason {
    @SerialName("spam")
    value_spam,
    @SerialName("abuse")
    value_abuse,
    @SerialName("other")
    value_other}

@Serializable
    data class BlueCatbirdCircleReportRecordInput(
        @SerialName("space")
        val space: SpaceRef,        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("reason")
        val reason: BlueCatbirdCircleReportRecordInputReason,        @SerialName("details")
        val details: String? = null    )

    @Serializable
    data class BlueCatbirdCircleReportRecordOutput(
        @SerialName("id")
        val id: String    )

sealed class BlueCatbirdCircleReportRecordError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleReportRecordError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleReportRecordError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleReportRecordError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleReportRecordError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleReportRecordError("UpstreamUnavailable", "")
    }

/**
 * Report a record in a Circle Space
 *
 * Endpoint: blue.catbird.circle.reportRecord
 */
suspend fun BlueCatbirdCircleNamespace.reportRecord(
input: BlueCatbirdCircleReportRecordInput): ATProtoResponse<BlueCatbirdCircleReportRecordOutput> {
    val endpoint = "blue.catbird.circle.reportRecord"

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
