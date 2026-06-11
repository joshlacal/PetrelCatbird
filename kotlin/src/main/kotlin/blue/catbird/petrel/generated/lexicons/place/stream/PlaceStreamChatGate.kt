// Lexicon: 1, ID: place.stream.chat.gate
// Record defining a single gated chat message.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamChatGateDefs {
    const val TYPE_IDENTIFIER = "place.stream.chat.gate"
}

    /**
     * Record defining a single gated chat message.
     */
    @Serializable
    data class PlaceStreamChatGate(
/** URI of the hidden chat message. */        @SerialName("hiddenMessage")
        val hiddenMessage: ATProtocolURI    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
