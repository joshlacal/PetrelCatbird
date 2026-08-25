// Lexicon: 1, ID: blue.catbird.circle.defs

package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdCircleDefsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.circle.defs"
}

@Serializable
enum class BlueCatbirdCircleDefsDefsAccessState {
    @SerialName("active")
    value_active,
    @SerialName("expired")
    value_expired,
    @SerialName("removed")
    value_removed,
    @SerialName("unsupported")
    value_unsupported}

@Serializable
enum class BlueCatbirdCircleDefsOperationStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("complete")
    value_complete,
    @SerialName("failed")
    value_failed}

@Serializable
enum class BlueCatbirdCircleDefsNotificationReason {
    @SerialName("reply")
    value_reply,
    @SerialName("like")
    value_like,
    @SerialName("invite")
    value_invite}

    @Serializable
    data class BlueCatbirdCircleDefsCircleSummary(
        @SerialName("uri")
        val uri: SpaceRef,        @SerialName("name")
        val name: String,        @SerialName("owner")
        val owner: DID,        @SerialName("accessState")
        val accessState: BlueCatbirdCircleDefsDefsAccessState,        @SerialName("muted")
        val muted: Boolean? = null,        @SerialName("members")
        val members: List<DID>? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdCircleDefsCircleSummary"
        }
    }

    @Serializable
    data class BlueCatbirdCircleDefsOperation(
/** Canonical lowercase hyphenated RFC 4122 UUID. */        @SerialName("id")
        val id: String,        @SerialName("status")
        val status: BlueCatbirdCircleDefsOperationStatus,        @SerialName("space")
        val space: SpaceRef? = null,        @SerialName("error")
        val error: String? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdCircleDefsOperation"
        }
    }

    @Serializable
    data class BlueCatbirdCircleDefsFeedItem(
        @SerialName("post")
        val post: AppBskyFeedDefsFeedViewPost,        @SerialName("circle")
        val circle: BlueCatbirdCircleDefsCircleSummary    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdCircleDefsFeedItem"
        }
    }

    @Serializable
    data class BlueCatbirdCircleDefsNotification(
/** Canonical lowercase hyphenated RFC 4122 UUID. */        @SerialName("id")
        val id: String,        @SerialName("reason")
        val reason: BlueCatbirdCircleDefsNotificationReason,        @SerialName("actor")
        val actor: AppBskyActorDefsProfileViewBasic,        @SerialName("subject")
        val subject: ATProtocolURI? = null,        @SerialName("indexedAt")
        val indexedAt: ATProtocolDate,        @SerialName("circle")
        val circle: BlueCatbirdCircleDefsCircleSummary    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdCircleDefsNotification"
        }
    }
