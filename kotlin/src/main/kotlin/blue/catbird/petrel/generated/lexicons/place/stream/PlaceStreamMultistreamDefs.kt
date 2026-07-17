// Lexicon: 1, ID: place.stream.multistream.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMultistreamDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.multistream.defs"
}

@Serializable
enum class PlaceStreamMultistreamDefsEventStatus {
    @SerialName("inactive")
    value_inactive,
    @SerialName("pending")
    value_pending,
    @SerialName("active")
    value_active,
    @SerialName("error")
    value_error}

    @Serializable
    data class PlaceStreamMultistreamDefsTargetView(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("cid")
        val cid: CID,        @SerialName("record")
        val record: JsonElement,        @SerialName("latestEvent")
        val latestEvent: PlaceStreamMultistreamDefsEvent? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamMultistreamDefsTargetView"
        }
    }

    @Serializable
    data class PlaceStreamMultistreamDefsEvent(
        @SerialName("message")
        val message: String,        @SerialName("status")
        val status: PlaceStreamMultistreamDefsEventStatus,        @SerialName("createdAt")
        val createdAt: ATProtocolDate    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamMultistreamDefsEvent"
        }
    }
