// Lexicon: 1, ID: blue.catbird.mlsChat.beginDeviceAuthBinding
// Begin proof-of-possession binding between an authenticated session and an owned MLS device registration. Returns a short-lived single-use canonical challenge. The caller signs the exact challenge bytes with the registered MLS device identity key and completes the binding under the same DPoP key.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatBeginDeviceAuthBindingDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.beginDeviceAuthBinding"
}

@Serializable
    data class BlueCatbirdMlsChatBeginDeviceAuthBindingInput(
// Server-issued device registration UUID.        @SerialName("deviceId")
        val deviceId: String    )

    @Serializable
    data class BlueCatbirdMlsChatBeginDeviceAuthBindingOutput(
// Opaque single-use challenge identifier.        @SerialName("challengeId")
        val challengeId: String,// Canonical versioned challenge bytes to sign without transformation.        @SerialName("challenge")
        val challenge: Bytes,        @SerialName("expiresAt")
        val expiresAt: ATProtocolDate,        @SerialName("bindingVersion")
        val bindingVersion: Int    )

sealed class BlueCatbirdMlsChatBeginDeviceAuthBindingError(val name: String, val description: String?) {
        object DpopRequired: BlueCatbirdMlsChatBeginDeviceAuthBindingError("DpopRequired", "")
        object DeviceNotFound: BlueCatbirdMlsChatBeginDeviceAuthBindingError("DeviceNotFound", "")
        object Unauthorized: BlueCatbirdMlsChatBeginDeviceAuthBindingError("Unauthorized", "")
        object InvalidDeviceId: BlueCatbirdMlsChatBeginDeviceAuthBindingError("InvalidDeviceId", "")
        object BindingUnavailable: BlueCatbirdMlsChatBeginDeviceAuthBindingError("BindingUnavailable", "")
    }

/**
 * Begin proof-of-possession binding between an authenticated session and an owned MLS device registration. Returns a short-lived single-use canonical challenge. The caller signs the exact challenge bytes with the registered MLS device identity key and completes the binding under the same DPoP key.
 *
 * Endpoint: blue.catbird.mlsChat.beginDeviceAuthBinding
 */
suspend fun BlueCatbirdMlsChatNamespace.beginDeviceAuthBinding(
input: BlueCatbirdMlsChatBeginDeviceAuthBindingInput): ATProtoResponse<BlueCatbirdMlsChatBeginDeviceAuthBindingOutput> {
    val endpoint = "blue.catbird.mlsChat.beginDeviceAuthBinding"

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
