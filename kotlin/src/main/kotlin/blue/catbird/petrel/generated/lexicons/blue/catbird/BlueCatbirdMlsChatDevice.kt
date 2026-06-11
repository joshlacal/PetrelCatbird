// Lexicon: 1, ID: blue.catbird.mlsChat.device
// Per-device MLS signature key published to the user's repo. Used to verify that key packages served by the delivery service belong to an authorized device.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatDeviceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.device"
}

    /**
     * Per-device MLS signature key published to the user's repo. Used to verify that key packages served by the delivery service belong to an authorized device.
     */
    @Serializable
    data class BlueCatbirdMlsChatDevice(
/** The MLS credential signature public key for this device */        @SerialName("mlsSignaturePublicKey")
        val mlsSignaturePublicKey: Bytes,/** Signature algorithm: 'ed25519' or 'p256' */        @SerialName("algorithm")
        val algorithm: String,        @SerialName("createdAt")
        val createdAt: ATProtocolDate    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
