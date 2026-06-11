// Lexicon: 1, ID: place.stream.chat.pinnedRecord
// Record pinning a chat message for prominent display.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamChatPinnedRecordDefs {
    const val TYPE_IDENTIFIER = "place.stream.chat.pinnedRecord"
}

    /**
     * Record pinning a chat message for prominent display.
     */
    @Serializable
    data class PlaceStreamChatPinnedRecord(
/** AT-URI of the pinned chat message. */        @SerialName("pinnedMessage")
        val pinnedMessage: ATProtocolURI,/** DID of the user who pinned the message. */        @SerialName("pinnedBy")
        val pinnedBy: DID? = null,/** When this pin was created. */        @SerialName("createdAt")
        val createdAt: ATProtocolDate,/** Optional expiration time. If set, the pin is considered inactive after this time. */        @SerialName("expiresAt")
        val expiresAt: ATProtocolDate? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
