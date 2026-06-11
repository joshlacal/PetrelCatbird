// Lexicon: 1, ID: place.stream.ingest.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamIngestDefsDefs {
    const val TYPE_IDENTIFIER = "place.stream.ingest.defs"
}

    /**
     * An ingest URL for a Streamplace station.
     */
    @Serializable
    data class PlaceStreamIngestDefsIngest(
/** The type of ingest endpoint, currently 'rtmp' and 'whip' are supported. */        @SerialName("type")
        val type: String,/** The URL of the ingest endpoint. */        @SerialName("url")
        val url: URI    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamIngestDefsIngest"
        }
    }
