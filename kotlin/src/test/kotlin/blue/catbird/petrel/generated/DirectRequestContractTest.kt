package blue.catbird.petrel.generated

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DirectRequestContractTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun bothWireVersionsRemainDecodable() {
        assertEquals(BlueCatbirdChatDefsDefsProtocolVersion.value_1,
            json.decodeFromString<BlueCatbirdChatDefsDefsProtocolVersion>("\"1\""))
        assertEquals(BlueCatbirdChatDefsDefsProtocolVersion.value_2,
            json.decodeFromString<BlueCatbirdChatDefsDefsProtocolVersion>("\"2\""))
    }

    @Test
    fun unknownOutcomeRemainsDistinctFromAReceipt() {
        val raw = """{"${'$'}type":"blue.catbird.chat.defs#directRequestUnknownOutcome","requestId":"12345678-1234-4234-8234-123456789abc"}"""
        val value = json.decodeFromString<BlueCatbirdChatDefsDirectRequestOutcome>(raw)
        val unknown = assertIs<BlueCatbirdChatDefsDirectRequestOutcome.DirectRequestUnknownOutcome>(value)
        assertEquals("12345678-1234-4234-8234-123456789abc", unknown.value.requestId)
    }
}
