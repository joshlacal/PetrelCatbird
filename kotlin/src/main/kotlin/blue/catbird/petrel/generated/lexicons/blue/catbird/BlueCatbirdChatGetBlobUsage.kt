// Lexicon: 1, ID: blue.catbird.chat.getBlobUsage
// Return auth-derived aggregate opaque-blob usage and reserved quota for the verified bare DID; no caller-selected principal is accepted.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatGetBlobUsageDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.getBlobUsage"
}

@Serializable
    class BlueCatbirdChatGetBlobUsageParameters

    @Serializable
    data class BlueCatbirdChatGetBlobUsageOutput(
        @SerialName("usage")
        val usage: BlueCatbirdChatDefsBlobUsageView    )

sealed class BlueCatbirdChatGetBlobUsageError(val name: String, val description: String?) {
        object CutoverRequired: BlueCatbirdChatGetBlobUsageError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatGetBlobUsageError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatGetBlobUsageError("DeviceRevoked", "")
        object InvalidDPoP: BlueCatbirdChatGetBlobUsageError("InvalidDPoP", "")
    }

/**
 * Return auth-derived aggregate opaque-blob usage and reserved quota for the verified bare DID; no caller-selected principal is accepted.
 *
 * Endpoint: blue.catbird.chat.getBlobUsage
 */
suspend fun BlueCatbirdChatNamespace.getBlobUsage(
parameters: BlueCatbirdChatGetBlobUsageParameters): ATProtoResponse<BlueCatbirdChatGetBlobUsageOutput> {
    val endpoint = "blue.catbird.chat.getBlobUsage"

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
