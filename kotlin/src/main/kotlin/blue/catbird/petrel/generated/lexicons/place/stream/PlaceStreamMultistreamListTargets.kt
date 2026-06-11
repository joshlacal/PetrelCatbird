// Lexicon: 1, ID: place.stream.multistream.listTargets
// List a range of targets for rebroadcasting a Streamplace stream.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMultistreamListTargetsDefs {
    const val TYPE_IDENTIFIER = "place.stream.multistream.listTargets"
}

    @Serializable
    data class PlaceStreamMultistreamListTargetsRecord(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("cid")
        val cid: CID,        @SerialName("value")
        val value: JsonElement    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamMultistreamListTargetsRecord"
        }
    }

@Serializable
    data class PlaceStreamMultistreamListTargetsParameters(
// The number of targets to return.        @SerialName("limit")
        val limit: Int? = null,        @SerialName("cursor")
        val cursor: String? = null    )

    @Serializable
    data class PlaceStreamMultistreamListTargetsOutput(
        @SerialName("targets")
        val targets: List<PlaceStreamMultistreamDefsTargetView>,        @SerialName("cursor")
        val cursor: String? = null    )

/**
 * List a range of targets for rebroadcasting a Streamplace stream.
 *
 * Endpoint: place.stream.multistream.listTargets
 */
suspend fun PlaceStreamMultistreamNamespace.listTargets(
parameters: PlaceStreamMultistreamListTargetsParameters): ATProtoResponse<PlaceStreamMultistreamListTargetsOutput> {
    val endpoint = "place.stream.multistream.listTargets"

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
