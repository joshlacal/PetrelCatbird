// Lexicon: 1, ID: place.stream.multistream.createTarget
// Create a new target for rebroadcasting a Streamplace stream.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMultistreamCreateTargetDefs {
    const val TYPE_IDENTIFIER = "place.stream.multistream.createTarget"
}

@Serializable
    data class PlaceStreamMultistreamCreateTargetInput(
        @SerialName("multistreamTarget")
        val multistreamTarget: PlaceStreamMultistreamTarget    )

    typealias PlaceStreamMultistreamCreateTargetOutput = PlaceStreamMultistreamDefsTargetView

sealed class PlaceStreamMultistreamCreateTargetError(val name: String, val description: String?) {
        object InvalidTargetUrl: PlaceStreamMultistreamCreateTargetError("InvalidTargetUrl", "The provided target URL is invalid or unreachable.")
    }

/**
 * Create a new target for rebroadcasting a Streamplace stream.
 *
 * Endpoint: place.stream.multistream.createTarget
 */
suspend fun PlaceStreamMultistreamNamespace.createTarget(
input: PlaceStreamMultistreamCreateTargetInput): ATProtoResponse<PlaceStreamMultistreamCreateTargetOutput> {
    val endpoint = "place.stream.multistream.createTarget"

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
