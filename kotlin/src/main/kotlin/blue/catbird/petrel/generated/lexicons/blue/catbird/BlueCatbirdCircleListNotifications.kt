// Lexicon: 1, ID: blue.catbird.circle.listNotifications
// List notifications for the caller across Circle Spaces
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleListNotificationsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.listNotifications"
}

@Serializable
    data class BlueCatbirdCircleListNotificationsParameters(
        @SerialName("limit")
        val limit: Int? = null,        @SerialName("cursor")
        val cursor: String? = null    )

    @Serializable
    data class BlueCatbirdCircleListNotificationsOutput(
        @SerialName("notifications")
        val notifications: List<BlueCatbirdCircleDefsNotification>,        @SerialName("cursor")
        val cursor: String? = null    )

sealed class BlueCatbirdCircleListNotificationsError(val name: String, val description: String?) {
        object AuthRequired: BlueCatbirdCircleListNotificationsError("AuthRequired", "")
        object AccessRemoved: BlueCatbirdCircleListNotificationsError("AccessRemoved", "")
        object UnsupportedPDS: BlueCatbirdCircleListNotificationsError("UnsupportedPDS", "")
        object ProtocolRevisionMismatch: BlueCatbirdCircleListNotificationsError("ProtocolRevisionMismatch", "")
        object UpstreamUnavailable: BlueCatbirdCircleListNotificationsError("UpstreamUnavailable", "")
    }

/**
 * List notifications for the caller across Circle Spaces
 *
 * Endpoint: blue.catbird.circle.listNotifications
 */
suspend fun BlueCatbirdCircleNamespace.listNotifications(
parameters: BlueCatbirdCircleListNotificationsParameters): ATProtoResponse<BlueCatbirdCircleListNotificationsOutput> {
    val endpoint = "blue.catbird.circle.listNotifications"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
