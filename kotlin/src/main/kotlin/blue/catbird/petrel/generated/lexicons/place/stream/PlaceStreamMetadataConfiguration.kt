// Lexicon: 1, ID: place.stream.metadata.configuration
// Default metadata record for livestream including content warnings, rights, and distribution policy
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamMetadataConfigurationDefs {
    const val TYPE_IDENTIFIER = "place.stream.metadata.configuration"
}

    /**
     * Default metadata record for livestream including content warnings, rights, and distribution policy
     */
    @Serializable
    data class PlaceStreamMetadataConfiguration(
        @SerialName("contentWarnings")
        val contentWarnings: PlaceStreamMetadataContentWarnings? = null,        @SerialName("contentRights")
        val contentRights: PlaceStreamMetadataContentRights? = null,        @SerialName("distributionPolicy")
        val distributionPolicy: PlaceStreamMetadataDistributionPolicy? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
