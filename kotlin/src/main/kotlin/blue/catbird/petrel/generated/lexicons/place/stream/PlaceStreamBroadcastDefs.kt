// Lexicon: 1, ID: place.stream.broadcast.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamBroadcastDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.broadcast.defs"
}

    @Serializable
    data class PlaceStreamBroadcastDefsBroadcastOriginView(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("cid")
        val cid: CID,        @SerialName("author")
        val author: AppBskyActorDefsProfileViewBasic,        @SerialName("record")
        val record: JsonElement    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamBroadcastDefsBroadcastOriginView"
        }
    }
