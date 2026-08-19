package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.Bytes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TransitionAttestationContractTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun conversationCoordinatesEncodeAndDecode() {
        val bytes = Bytes(ByteArray(32) { 1 })
        val coords = BlueCatbirdChatDefsConversationCoordinates(
            conversationId = "00000000-0000-4000-8000-000000000001",
            generation = 1,
            stateVersion = 1,
            groupId = bytes,
            epoch = 1,
            groupContextHash = bytes,
            confirmationTag = bytes,
            lifecycle = BlueCatbirdChatDefsDefsLifecycle.value_active,
        )
        val encoded = json.encodeToString(coords)
        val decoded = json.decodeFromString<BlueCatbirdChatDefsConversationCoordinates>(encoded)
        assertEquals(coords.conversationId, decoded.conversationId)
        assertEquals(coords.epoch, decoded.epoch)
        assertEquals(coords.lifecycle, decoded.lifecycle)
    }
}
