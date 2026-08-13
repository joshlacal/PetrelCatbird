// Lexicon: 1, ID: blue.catbird.chat.rebindDeviceAuthentication
// Rebind only DPoP JKT using the immutable stored Ed25519 key. First execution deliberately accepts a valid Nest token/proof bound to signed newDpopJkt, looks up exact actorDid/deviceId, CASes signed currentDpopJkt plus generation, verifies the Ed25519 body, then installs proofJKT=newDpopJkt and increments once; the old DPoP key may be lost. Loss of both keys requires new-device enrollment then old-device revocation. Exact completed replay requires identical stored transcript digest/signature and fresh token/proof under only the recorded new JKT; it never reexecutes.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatRebindDeviceAuthenticationDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.rebindDeviceAuthentication"
}

@Serializable
    data class BlueCatbirdChatRebindDeviceAuthenticationInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedDeviceAuthenticationRebind    )

    @Serializable
    data class BlueCatbirdChatRebindDeviceAuthenticationOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatRebindDeviceAuthenticationError(val name: String, val description: String?) {
        object AuthenticationGenerationConflict: BlueCatbirdChatRebindDeviceAuthenticationError("AuthenticationGenerationConflict", "")
        object CutoverRequired: BlueCatbirdChatRebindDeviceAuthenticationError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatRebindDeviceAuthenticationError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatRebindDeviceAuthenticationError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatRebindDeviceAuthenticationError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatRebindDeviceAuthenticationError("InvalidDPoP", "")
        object InvalidRequest: BlueCatbirdChatRebindDeviceAuthenticationError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatRebindDeviceAuthenticationError("InvalidSignature", "")
        object NotAuthorized: BlueCatbirdChatRebindDeviceAuthenticationError("NotAuthorized", "")
    }

/**
 * Rebind only DPoP JKT using the immutable stored Ed25519 key. First execution deliberately accepts a valid Nest token/proof bound to signed newDpopJkt, looks up exact actorDid/deviceId, CASes signed currentDpopJkt plus generation, verifies the Ed25519 body, then installs proofJKT=newDpopJkt and increments once; the old DPoP key may be lost. Loss of both keys requires new-device enrollment then old-device revocation. Exact completed replay requires identical stored transcript digest/signature and fresh token/proof under only the recorded new JKT; it never reexecutes.
 *
 * Endpoint: blue.catbird.chat.rebindDeviceAuthentication
 */
suspend fun BlueCatbirdChatNamespace.rebindDeviceAuthentication(
input: BlueCatbirdChatRebindDeviceAuthenticationInput): ATProtoResponse<BlueCatbirdChatRebindDeviceAuthenticationOutput> {
    val endpoint = "blue.catbird.chat.rebindDeviceAuthentication"

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
