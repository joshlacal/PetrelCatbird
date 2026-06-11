// Lexicon: 1, ID: blue.catbird.mlsChat.getConvoSettings
// Get conversation settings and policy Retrieve current settings and policy for a conversation.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdMlsChatGetConvoSettingsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.mlsChat.getConvoSettings"
}

@Serializable
    data class BlueCatbirdMlsChatGetConvoSettingsParameters(
// Conversation identifier        @SerialName("convoId")
        val convoId: String    )

    @Serializable
    data class BlueCatbirdMlsChatGetConvoSettingsOutput(
// Conversation identifier        @SerialName("convoId")
        val convoId: String,// Conversation policy object (structure varies by conversation type)        @SerialName("policy")
        val policy: JsonElement    )

sealed class BlueCatbirdMlsChatGetConvoSettingsError(val name: String, val description: String?) {
        object ConvoNotFound: BlueCatbirdMlsChatGetConvoSettingsError("ConvoNotFound", "Conversation not found")
        object NotMember: BlueCatbirdMlsChatGetConvoSettingsError("NotMember", "Caller is not a member of this conversation")
    }

/**
 * Get conversation settings and policy Retrieve current settings and policy for a conversation.
 *
 * Endpoint: blue.catbird.mlsChat.getConvoSettings
 */
suspend fun BlueCatbirdMlsChatNamespace.getConvoSettings(
parameters: BlueCatbirdMlsChatGetConvoSettingsParameters): ATProtoResponse<BlueCatbirdMlsChatGetConvoSettingsOutput> {
    val endpoint = "blue.catbird.mlsChat.getConvoSettings"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
