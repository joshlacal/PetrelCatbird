// Lexicon: 1, ID: place.stream.muxl.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMuxlDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.muxl.defs"
}

    /**
     * A track within a multiplexed stream
     */
    @Serializable
    data class PlaceStreamMuxlDefsTrack(
/** Track identifier */        @SerialName("id")
        val id: Int,/** Codec used for this track */        @SerialName("codec")
        val codec: String,/** Width in pixels (video tracks) */        @SerialName("width")
        val width: Int? = null,/** Height in pixels (video tracks) */        @SerialName("height")
        val height: Int? = null,/** Sample rate (audio tracks) */        @SerialName("rate")
        val rate: Int? = null,/** Number of audio channels */        @SerialName("channels")
        val channels: Int? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamMuxlDefsTrack"
        }
    }
