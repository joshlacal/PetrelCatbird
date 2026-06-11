// Lexicon: 1, ID: place.stream.graph.getFollowingUser
// Get whether or not user A is following user B.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamGraphGetFollowingUserDefs {
    const val TYPE_IDENTIFIER = "place.stream.graph.getFollowingUser"
}

@Serializable
    data class PlaceStreamGraphGetFollowingUserParameters(
// The DID of the potentially-following user        @SerialName("userDID")
        val userDID: DID,// The DID of the user potentially being followed        @SerialName("subjectDID")
        val subjectDID: DID    )

    @Serializable
    data class PlaceStreamGraphGetFollowingUserOutput(
        @SerialName("follow")
        val follow: ComAtprotoRepoStrongRef? = null    )

/**
 * Get whether or not user A is following user B.
 *
 * Endpoint: place.stream.graph.getFollowingUser
 */
suspend fun PlaceStreamGraphNamespace.getFollowingUser(
parameters: PlaceStreamGraphGetFollowingUserParameters): ATProtoResponse<PlaceStreamGraphGetFollowingUserOutput> {
    val endpoint = "place.stream.graph.getFollowingUser"

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
