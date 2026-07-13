// Lexicon: 1, ID: blue.catbird.mlsChat.completeDeviceAuthBinding
// Complete proof-of-possession binding for an MLS device registration. Atomically consumes a binding challenge after verifying its signature with the registered MLS device identity key and requiring the same authenticated DPoP key used to begin it.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatCompleteDeviceAuthBindingDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.completeDeviceAuthBinding"
}

@Serializable
    data class BlueCatbirdMlsChatCompleteDeviceAuthBindingInput(
// Opaque identifier returned by beginDeviceAuthBinding.        @SerialName("challengeId")
        val challengeId: String,// Ed25519 signature over the exact canonical challenge bytes.        @SerialName("signature")
        val signature: Bytes    )

    @Serializable
    data class BlueCatbirdMlsChatCompleteDeviceAuthBindingOutput(
// Bound server-issued device registration UUID.        @SerialName("deviceId")
        val deviceId: String,        @SerialName("boundAt")
        val boundAt: ATProtocolDate,        @SerialName("bindingVersion")
        val bindingVersion: Int    )

sealed class BlueCatbirdMlsChatCompleteDeviceAuthBindingError(val name: String, val description: String?) {
        object DpopRequired: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("DpopRequired", "")
        object ChallengeNotFound: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("ChallengeNotFound", "")
        object ChallengeExpired: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("ChallengeExpired", "")
        object ChallengeAlreadyUsed: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("ChallengeAlreadyUsed", "")
        object BindingMismatch: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("BindingMismatch", "")
        object InvalidSignature: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("InvalidSignature", "")
        object DeviceNotFound: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("DeviceNotFound", "")
        object Unauthorized: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("Unauthorized", "")
        object BindingUnavailable: BlueCatbirdMlsChatCompleteDeviceAuthBindingError("BindingUnavailable", "")
    }

/**
 * Complete proof-of-possession binding for an MLS device registration. Atomically consumes a binding challenge after verifying its signature with the registered MLS device identity key and requiring the same authenticated DPoP key used to begin it.
 *
 * Endpoint: blue.catbird.mlsChat.completeDeviceAuthBinding
 */
suspend fun BlueCatbirdMlsChatNamespace.completeDeviceAuthBinding(
input: BlueCatbirdMlsChatCompleteDeviceAuthBindingInput): ATProtoResponse<BlueCatbirdMlsChatCompleteDeviceAuthBindingOutput> {
    val endpoint = "blue.catbird.mlsChat.completeDeviceAuthBinding"

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
