// Lexicon: 1, ID: place.stream.server.deleteWebhook
// Delete an existing webhook.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamServerDeleteWebhookDefs {
    const val TYPE_IDENTIFIER = "place.stream.server.deleteWebhook"
}

@Serializable
    data class PlaceStreamServerDeleteWebhookInput(
// The ID of the webhook to delete.        @SerialName("id")
        val id: String    )

    @Serializable
    data class PlaceStreamServerDeleteWebhookOutput(
// Whether the webhook was successfully deleted.        @SerialName("success")
        val success: Boolean    )

sealed class PlaceStreamServerDeleteWebhookError(val name: String, val description: String?) {
        object WebhookNotFound: PlaceStreamServerDeleteWebhookError("WebhookNotFound", "The specified webhook was not found.")
        object Unauthorized: PlaceStreamServerDeleteWebhookError("Unauthorized", "The authenticated user does not have access to this webhook.")
    }

/**
 * Delete an existing webhook.
 *
 * Endpoint: place.stream.server.deleteWebhook
 */
suspend fun PlaceStreamServerNamespace.deleteWebhook(
input: PlaceStreamServerDeleteWebhookInput): ATProtoResponse<PlaceStreamServerDeleteWebhookOutput> {
    val endpoint = "place.stream.server.deleteWebhook"

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
