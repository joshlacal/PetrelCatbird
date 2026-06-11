// Lexicon: 1, ID: place.stream.metadata.contentWarnings
// Content warnings for a stream.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMetadataContentWarningsDefs {
    const val TYPE_IDENTIFIER = "place.stream.metadata.contentWarnings"
}

@Serializable
data class PlaceStreamMetadataContentWarnings(
    @SerialName("warnings")
    val warnings: List<String>? = null)
