// Lexicon: 1, ID: place.stream.live.teleport
// Record defining a 'teleport', that is active during a certain time.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamLiveTeleportDefs {
    const val TYPE_IDENTIFIER = "place.stream.live.teleport"
}

    /**
     * Record defining a 'teleport', that is active during a certain time.
     */
    @Serializable
    data class PlaceStreamLiveTeleport(
/** The DID of the streamer to teleport to. */        @SerialName("streamer")
        val streamer: DID,/** The time the teleport becomes active. */        @SerialName("startsAt")
        val startsAt: ATProtocolDate,/** The time limit in seconds for the teleport. If not set, the teleport is permanent. Must be at least 60 seconds, and no more than 32,400 seconds (9 hours). */        @SerialName("durationSeconds")
        val durationSeconds: Int? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
