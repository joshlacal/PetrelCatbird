// Lexicon: 1, ID: place.stream.moderation.permission
// Record granting moderation permissions to a user for this streamer's content.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamModerationPermissionDefs {
    const val TYPE_IDENTIFIER = "place.stream.moderation.permission"
}

@Serializable
enum class PlaceStreamModerationPermissionPermissions {
    @SerialName("ban")
    value_ban,
    @SerialName("hide")
    value_hide,
    @SerialName("livestream.manage")
    value_livestream_u2e_manage,
    @SerialName("message.pin")
    value_message_u2e_pin}

    /**
     * Record granting moderation permissions to a user for this streamer's content.
     */
    @Serializable
    data class PlaceStreamModerationPermission(
/** The DID of the user granted moderator permissions. */        @SerialName("moderator")
        val moderator: DID,/** Array of permissions granted to this moderator. 'ban' covers blocks/bans (with optional expiration), 'hide' covers message gates, 'livestream.manage' allows updating livestream metadata. */        @SerialName("permissions")
        val permissions: List<PlaceStreamModerationPermissionPermissions>,/** Client-declared timestamp when this moderator was added. */        @SerialName("createdAt")
        val createdAt: ATProtocolDate,/** Optional expiration time for this delegation. If set, the delegation is invalid after this time. */        @SerialName("expirationTime")
        val expirationTime: ATProtocolDate? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = ""
        }
    }
