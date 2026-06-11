// Lexicon: 1, ID: place.stream.broadcast.syndication
// Record created by a Streamplace broadcaster to indicate that they will be replicating a livestream. NYI
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamBroadcastSyndicationDefs {
    const val TYPE_IDENTIFIER = "place.stream.broadcast.syndication"
}

    /**
     * Record created by a Streamplace broadcaster to indicate that they will be replicating a livestream. NYI
     */
    @Serializable
    data class PlaceStreamBroadcastSyndication(
/** DID of the Streamplace broadcaster that will be replicating the livestream */        @SerialName("broadcaster")
        val broadcaster: DID,/** DID of the streamer whose livestream is being replicated */        @SerialName("streamer")
        val streamer: DID,/** Client-declared timestamp when this syndication was created. */        @SerialName("createdAt")
        val createdAt: ATProtocolDate    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
