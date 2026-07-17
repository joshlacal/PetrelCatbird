// Lexicon: 1, ID: place.stream.segment
// Media file representing a segment of a livestream
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamSegmentDefs {
    const val TYPE_IDENTIFIER = "place.stream.segment"
}

@Serializable
enum class PlaceStreamSegmentAudioCodec {
    @SerialName("opus")
    value_opus}

@Serializable
enum class PlaceStreamSegmentVideoCodec {
    @SerialName("h264")
    value_h264}

    @Serializable
    data class PlaceStreamSegmentAudio(
        @SerialName("codec")
        val codec: PlaceStreamSegmentAudioCodec,        @SerialName("rate")
        val rate: Int,        @SerialName("channels")
        val channels: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamSegmentAudio"
        }
    }

    @Serializable
    data class PlaceStreamSegmentVideo(
        @SerialName("codec")
        val codec: PlaceStreamSegmentVideoCodec,        @SerialName("width")
        val width: Int,        @SerialName("height")
        val height: Int,        @SerialName("framerate")
        val framerate: PlaceStreamSegmentFramerate? = null,        @SerialName("bframes")
        val bframes: Boolean? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamSegmentVideo"
        }
    }

    @Serializable
    data class PlaceStreamSegmentFramerate(
        @SerialName("num")
        val num: Int,        @SerialName("den")
        val den: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamSegmentFramerate"
        }
    }

    @Serializable
    data class PlaceStreamSegmentSegmentView(
        @SerialName("cid")
        val cid: CID,        @SerialName("record")
        val record: JsonElement    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamSegmentSegmentView"
        }
    }

    /**
     * Media file representing a segment of a livestream
     */
    @Serializable
    data class PlaceStreamSegment(
/** Unique identifier for the segment */        @SerialName("id")
        val id: String,/** The DID of the signing key used for this segment */        @SerialName("signingKey")
        val signingKey: String,/** When this segment started */        @SerialName("startTime")
        val startTime: ATProtocolDate,/** The duration of the segment in nanoseconds */        @SerialName("duration")
        val duration: Int? = null,        @SerialName("creator")
        val creator: DID,        @SerialName("video")
        val video: List<PlaceStreamSegmentVideo>? = null,        @SerialName("audio")
        val audio: List<PlaceStreamSegmentAudio>? = null,/** The size of the segment in bytes */        @SerialName("size")
        val size: Int? = null,        @SerialName("contentWarnings")
        val contentWarnings: PlaceStreamMetadataContentWarnings? = null,        @SerialName("contentRights")
        val contentRights: PlaceStreamMetadataContentRights? = null,        @SerialName("distributionPolicy")
        val distributionPolicy: PlaceStreamMetadataDistributionPolicy? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
