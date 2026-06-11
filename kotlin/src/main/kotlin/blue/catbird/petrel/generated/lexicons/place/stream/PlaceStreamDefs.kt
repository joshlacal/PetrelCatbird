// Lexicon: 1, ID: place.stream.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.defs"
}

    @Serializable
    data class PlaceStreamDefsBlockView(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("cid")
        val cid: CID,        @SerialName("blocker")
        val blocker: AppBskyActorDefsProfileViewBasic,        @SerialName("record")
        val record: AppBskyGraphBlock,        @SerialName("indexedAt")
        val indexedAt: ATProtocolDate    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamDefsBlockView"
        }
    }

    @Serializable
    data class PlaceStreamDefsRenditions(
        @SerialName("renditions")
        val renditions: List<PlaceStreamDefsRendition>    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamDefsRenditions"
        }
    }

    @Serializable
    data class PlaceStreamDefsRendition(
        @SerialName("name")
        val name: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamDefsRendition"
        }
    }
