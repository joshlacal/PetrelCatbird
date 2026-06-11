// Lexicon: 1, ID: place.stream.chat.profile
// Record containing customizations for a user's chat profile.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamChatProfileDefs {
    const val TYPE_IDENTIFIER = "place.stream.chat.profile"
}

    /**
     * Customizations for the color of a user's name in chat
     */
    @Serializable
    data class PlaceStreamChatProfileColor(
        @SerialName("red")
        val red: Int,        @SerialName("green")
        val green: Int,        @SerialName("blue")
        val blue: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#placeStreamChatProfileColor"
        }
    }

    /**
     * Record containing customizations for a user's chat profile.
     */
    @Serializable
    data class PlaceStreamChatProfile(
        @SerialName("color")
        val color: PlaceStreamChatProfileColor? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
