// Lexicon: 1, ID: blue.catbird.chat.replenishKeyPackages
// Publish a whole-or-nothing batch for the exact active device binding. Stored immutable key, submitted key/keyId, auth generation, signed JKT, token claims, and DPoP proof are rechecked under the mutation lock; package-only replenishment does not change auth generation.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatReplenishKeyPackagesDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.replenishKeyPackages"
}

@Serializable
    data class BlueCatbirdChatReplenishKeyPackagesInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedKeyPackageReplenishment    )

    @Serializable
    data class BlueCatbirdChatReplenishKeyPackagesOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatReplenishKeyPackagesError(val name: String, val description: String?) {
        object AuthenticationGenerationConflict: BlueCatbirdChatReplenishKeyPackagesError("AuthenticationGenerationConflict", "")
        object CutoverRequired: BlueCatbirdChatReplenishKeyPackagesError("CutoverRequired", "")
        object DeviceNotRegistered: BlueCatbirdChatReplenishKeyPackagesError("DeviceNotRegistered", "")
        object DeviceRevoked: BlueCatbirdChatReplenishKeyPackagesError("DeviceRevoked", "")
        object IdempotencyConflict: BlueCatbirdChatReplenishKeyPackagesError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatReplenishKeyPackagesError("InvalidDPoP", "")
        object InvalidKeyPackage: BlueCatbirdChatReplenishKeyPackagesError("InvalidKeyPackage", "")
        object InvalidRequest: BlueCatbirdChatReplenishKeyPackagesError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatReplenishKeyPackagesError("InvalidSignature", "")
        object KeyPackageInventoryLimitReached: BlueCatbirdChatReplenishKeyPackagesError("KeyPackageInventoryLimitReached", "")
        object NotAuthorized: BlueCatbirdChatReplenishKeyPackagesError("NotAuthorized", "")
    }

/**
 * Publish a whole-or-nothing batch for the exact active device binding. Stored immutable key, submitted key/keyId, auth generation, signed JKT, token claims, and DPoP proof are rechecked under the mutation lock; package-only replenishment does not change auth generation.
 *
 * Endpoint: blue.catbird.chat.replenishKeyPackages
 */
suspend fun BlueCatbirdChatNamespace.replenishKeyPackages(
input: BlueCatbirdChatReplenishKeyPackagesInput): ATProtoResponse<BlueCatbirdChatReplenishKeyPackagesOutput> {
    val endpoint = "blue.catbird.chat.replenishKeyPackages"

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
