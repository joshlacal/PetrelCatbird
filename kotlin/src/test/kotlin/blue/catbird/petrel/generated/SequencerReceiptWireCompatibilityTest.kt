package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.Bytes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SequencerReceiptWireCompatibilityTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val sampleReceiptJson = """
    {
        "conversationId": "00000000-0000-4000-8000-000000000001",
        "generation": 1,
        "groupId": {"${'$'}bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA="},
        "epoch": 10,
        "entryId": "00000000-0000-4000-8000-000000000002",
        "seq": 42,
        "senderDid": "did:plc:ewvi7nxzyoun6zhxrhs64oiz",
        "senderDeviceId": "device-01",
        "archiveCommitment": {"${'$'}bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA="},
        "issuedAtMs": 1729000000000,
        "sequencerDid": "did:plc:sequencer0000000000001",
        "kid": "k1",
        "signature": {"${'$'}bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyAhIiMkJSYnKCkqKywtLi8wMTIzNDU2Nzg5Ojs8PT4/QA=="}
    }
    """.trimIndent()

    @Test
    fun sequencerReceiptDirectlyDecodesWith64BitIssuedAtMs() {
        val receipt = json.decodeFromString<BlueCatbirdChatDefsSequencerReceipt>(sampleReceiptJson)
        assertEquals("00000000-0000-4000-8000-000000000001", receipt.conversationId)
        assertEquals(1L, receipt.generation)
        assertEquals(10L, receipt.epoch)
        assertEquals(42L, receipt.seq)
        assertEquals(1729000000000L, receipt.issuedAtMs)
        assertEquals("did:plc:ewvi7nxzyoun6zhxrhs64oiz", receipt.senderDid.toString())
        assertEquals("device-01", receipt.senderDeviceId)
        assertEquals("did:plc:sequencer0000000000001", receipt.sequencerDid.toString())
        assertEquals("k1", receipt.kid)

        // Roundtrip serialization
        val encoded = json.encodeToString(receipt)
        val roundTrip = json.decodeFromString<BlueCatbirdChatDefsSequencerReceipt>(encoded)
        assertEquals(1729000000000L, roundTrip.issuedAtMs)
        assertEquals(42L, roundTrip.seq)
    }

    @Test
    fun applicationEntryWithSequencerReceiptDecodesCleanly() {
        val appEntryJson = """
        {
            "${'$'}type": "blue.catbird.chat.defs#applicationEntry",
            "entryId": "00000000-0000-4000-8000-000000000002",
            "conversationId": "00000000-0000-4000-8000-000000000001",
            "seq": 42,
            "receivedAt": "2026-10-15T12:00:00.000Z",
            "sequencerReceipt": $sampleReceiptJson
        }
        """.trimIndent()

        // Direct deserialization
        val entry = json.decodeFromString<BlueCatbirdChatDefsApplicationEntry>(appEntryJson)
        assertEquals(42L, entry.seq)
        assertNotNull(entry.sequencerReceipt)
        assertEquals(1729000000000L, entry.sequencerReceipt?.issuedAtMs)

        // Polymorphic union deserialization
        val unionEntry = json.decodeFromString<BlueCatbirdChatDefsConversationEntry>(appEntryJson)
        val appUnion = assertIs<BlueCatbirdChatDefsConversationEntry.ApplicationEntry>(unionEntry)
        assertEquals(42L, appUnion.value.seq)
        assertEquals(1729000000000L, appUnion.value.sequencerReceipt?.issuedAtMs)
    }

    @Test
    fun commitEntryWithSequencerReceiptDecodesCleanly() {
        val commitEntryJson = """
        {
            "${'$'}type": "blue.catbird.chat.defs#commitEntry",
            "entryId": "00000000-0000-4000-8000-000000000003",
            "conversationId": "00000000-0000-4000-8000-000000000001",
            "seq": 43,
            "signedRequest": {
                "body": {
                    "${'$'}type": "test.custom.transition",
                    "dummy": "payload"
                },
                "signature": {"${'$'}bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyAhIiMkJSYnKCkqKywtLi8wMTIzNDU2Nzg5Ojs8PT4/QA=="}
            },
            "receivedAt": "2026-10-15T12:00:00.000Z",
            "sequencerReceipt": $sampleReceiptJson
        }
        """.trimIndent()

        val entry = json.decodeFromString<BlueCatbirdChatDefsCommitEntry>(commitEntryJson)
        assertEquals(43L, entry.seq)
        assertNotNull(entry.sequencerReceipt)
        assertEquals(1729000000000L, entry.sequencerReceipt?.issuedAtMs)

        // Polymorphic union deserialization
        val unionEntry = json.decodeFromString<BlueCatbirdChatDefsConversationEntry>(commitEntryJson)
        val commitUnion = assertIs<BlueCatbirdChatDefsConversationEntry.CommitEntry>(unionEntry)
        assertEquals(43L, commitUnion.value.seq)
        assertEquals(1729000000000L, commitUnion.value.sequencerReceipt?.issuedAtMs)
    }

    @Test
    fun historicalEntryWithoutSequencerReceiptDecodesWithNullReceipt() {
        val historicalJson = """
        {
            "${'$'}type": "blue.catbird.chat.defs#applicationEntry",
            "entryId": "00000000-0000-4000-8000-000000000004",
            "conversationId": "00000000-0000-4000-8000-000000000001",
            "seq": 10,
            "receivedAt": "2026-08-01T00:00:00.000Z"
        }
        """.trimIndent()

        val entry = json.decodeFromString<BlueCatbirdChatDefsApplicationEntry>(historicalJson)
        assertNull(entry.sequencerReceipt)

        val unionEntry = json.decodeFromString<BlueCatbirdChatDefsConversationEntry>(historicalJson)
        val appUnion = assertIs<BlueCatbirdChatDefsConversationEntry.ApplicationEntry>(unionEntry)
        assertNull(appUnion.value.sequencerReceipt)
    }

    @Test
    fun explicitNullSequencerReceiptDecodesCleanly() {
        val nullReceiptJson = """
        {
            "${'$'}type": "blue.catbird.chat.defs#applicationEntry",
            "entryId": "00000000-0000-4000-8000-000000000005",
            "conversationId": "00000000-0000-4000-8000-000000000001",
            "seq": 11,
            "receivedAt": "2026-08-01T00:00:00.000Z",
            "sequencerReceipt": null
        }
        """.trimIndent()

        val entry = json.decodeFromString<BlueCatbirdChatDefsApplicationEntry>(nullReceiptJson)
        assertNull(entry.sequencerReceipt)

        val unionEntry = json.decodeFromString<BlueCatbirdChatDefsConversationEntry>(nullReceiptJson)
        val appUnion = assertIs<BlueCatbirdChatDefsConversationEntry.ApplicationEntry>(unionEntry)
        assertNull(appUnion.value.sequencerReceipt)
    }
}
