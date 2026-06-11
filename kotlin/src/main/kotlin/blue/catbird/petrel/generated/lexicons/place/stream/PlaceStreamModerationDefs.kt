// Lexicon: 1, ID: place.stream.moderation.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamModerationDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.moderation.defs"
}

    @Serializable
    data class PlaceStreamModerationDefsPermissionView(
/** AT-URI of the permission record */        @SerialName("uri")
        val uri: ATProtocolURI,/** Content identifier of the permission record */        @SerialName("cid")
        val cid: CID,/** The streamer who granted these permissions */        @SerialName("author")
        val author: AppBskyActorDefsProfileViewBasic,/** The permission record itself */        @SerialName("record")
        val record: JsonElement    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamModerationDefsPermissionView"
        }
    }
