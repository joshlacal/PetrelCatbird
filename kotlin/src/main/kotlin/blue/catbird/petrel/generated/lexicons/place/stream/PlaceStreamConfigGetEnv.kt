// Lexicon: 1, ID: place.stream.config.getEnv
// Get client-facing environment configuration from the server.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamConfigGetEnvDefs {
    const val TYPE_IDENTIFIER = "place.stream.config.getEnv"
}

@Serializable
    class PlaceStreamConfigGetEnvParameters

    @Serializable
    data class PlaceStreamConfigGetEnvOutput(
// URL of the Cloudflare playback router worker        @SerialName("playbackWorkerUrl")
        val playbackWorkerUrl: String? = null    )

/**
 * Get client-facing environment configuration from the server.
 *
 * Endpoint: place.stream.config.getEnv
 */
suspend fun PlaceStreamConfigNamespace.getEnv(
parameters: PlaceStreamConfigGetEnvParameters): ATProtoResponse<PlaceStreamConfigGetEnvOutput> {
    val endpoint = "place.stream.config.getEnv"

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
