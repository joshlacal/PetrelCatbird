// Lexicon: 1, ID: blue.catbird.mlsChat.removeDevice
// Remove a registered MLS device and clean up associated resources (key packages, conversation memberships, pending welcome messages).
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatRemoveDeviceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.removeDevice"
}

@Serializable
    data class BlueCatbirdMlsChatRemoveDeviceInput(
// The device ID to remove.        @SerialName("deviceId")
        val deviceId: String    )

    @Serializable
    data class BlueCatbirdMlsChatRemoveDeviceOutput(
// Whether the device was successfully deleted.        @SerialName("deleted")
        val deleted: Boolean,// Number of key packages that were removed.        @SerialName("keyPackagesDeleted")
        val keyPackagesDeleted: Int? = null,// Number of conversations the device was removed from.        @SerialName("conversationsLeft")
        val conversationsLeft: Int? = null    )

sealed class BlueCatbirdMlsChatRemoveDeviceError(val name: String, val description: String?) {
        object DeviceNotFound: BlueCatbirdMlsChatRemoveDeviceError("DeviceNotFound", "")
    }

/**
 * Remove a registered MLS device and clean up associated resources (key packages, conversation memberships, pending welcome messages).
 *
 * Endpoint: blue.catbird.mlsChat.removeDevice
 */
suspend fun BlueCatbirdMlsChatNamespace.removeDevice(
input: BlueCatbirdMlsChatRemoveDeviceInput): ATProtoResponse<BlueCatbirdMlsChatRemoveDeviceOutput> {
    val endpoint = "blue.catbird.mlsChat.removeDevice"

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
