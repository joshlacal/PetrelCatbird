// Lexicon: 1, ID: blue.catbird.mlsChat.getKeyPackages
// Retrieve key packages for one or more DIDs (same as v1, in v2 namespace for consistency) Retrieve key packages for one or more DIDs to add them to conversations. Returns one key package per device per DID.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatGetKeyPackagesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.getKeyPackages"
}

@Serializable
    data class BlueCatbirdMlsChatGetKeyPackagesParameters(
// DIDs to fetch key packages for        @SerialName("dids")
        val dids: List<DID>,// Filter by cipher suite        @SerialName("cipherSuite")
        val cipherSuite: String? = null    )

    @Serializable
    data class BlueCatbirdMlsChatGetKeyPackagesOutput(
// Available key packages for the requested DIDs        @SerialName("keyPackages")
        val keyPackages: List<BlueCatbirdMlsChatDefsKeyPackageRef>,// DIDs for which no key packages were found        @SerialName("missing")
        val missing: List<DID>? = null    )

sealed class BlueCatbirdMlsChatGetKeyPackagesError(val name: String, val description: String?) {
        object TooManyDids: BlueCatbirdMlsChatGetKeyPackagesError("TooManyDids", "Too many DIDs requested (max 100)")
        object InvalidDid: BlueCatbirdMlsChatGetKeyPackagesError("InvalidDid", "One or more DIDs are invalid")
    }

/**
 * Retrieve key packages for one or more DIDs (same as v1, in v2 namespace for consistency) Retrieve key packages for one or more DIDs to add them to conversations. Returns one key package per device per DID.
 *
 * Endpoint: blue.catbird.mlsChat.getKeyPackages
 */
suspend fun BlueCatbirdMlsChatNamespace.getKeyPackages(
parameters: BlueCatbirdMlsChatGetKeyPackagesParameters): ATProtoResponse<BlueCatbirdMlsChatGetKeyPackagesOutput> {
    val endpoint = "blue.catbird.mlsChat.getKeyPackages"

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
