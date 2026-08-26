package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.ATProtocolDate
import blue.catbird.petrel.core.types.Bytes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetConvoEventsContractTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun cleanConvoEventEntryDecodesWithAllCanonicalFields() {
        val cleanJSON = """
        {
            "seq": 1,
            "epoch": 0,
            "msgId": "msg-001",
            "messageType": "app",
            "ciphertext": {"${"$"}bytes": "AQID"},
            "paddedSize": 3,
            "createdAt": "2026-08-21T00:00:00.000Z",
            "entryId": "00112233-4455-4677-8899-aabbccddeeff",
            "entryKind": "blue.catbird.chat.defs#creationEntry",
            "acceptedPayloadSha256": {"${"$"}bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA="},
            "signedRequest": {"${"$"}bytes": "AQ=="},
            "outerFingerprint": {"${"$"}bytes": "oaKjpKWmp6ipqqusra6vsLGys7S1tre4ubq7vL2+v8A="}
        }
        """.trimIndent()

        val expectedSha = ByteArray(32) { (it + 1).toByte() }
        val expectedSignedRequest = byteArrayOf(0x01)
        val expectedFingerprint = ByteArray(32) { (0xa1 + it).toByte() }

        val entry = json.decodeFromString<BlueCatbirdMlsDSGetConvoEventsConvoEventEntry>(cleanJSON)
        assertEquals(1, entry.seq)
        assertEquals(0, entry.epoch)
        assertEquals("msg-001", entry.msgId)
        assertEquals("app", entry.messageType)
        assertEquals("00112233-4455-4677-8899-aabbccddeeff", entry.entryId)
        assertEquals("blue.catbird.chat.defs#creationEntry", entry.entryKind)
        assertContentEquals(expectedSha, entry.acceptedPayloadSha256?.data)
        assertContentEquals(expectedSignedRequest, entry.signedRequest?.data)
        assertContentEquals(expectedFingerprint, entry.outerFingerprint?.data)

        val reencoded = json.encodeToString(entry)
        val roundTrip = json.decodeFromString<BlueCatbirdMlsDSGetConvoEventsConvoEventEntry>(reencoded)
        assertEquals(entry.entryId, roundTrip.entryId)
        assertEquals(entry.entryKind, roundTrip.entryKind)
        assertContentEquals(expectedSha, roundTrip.acceptedPayloadSha256?.data)
        assertContentEquals(expectedSignedRequest, roundTrip.signedRequest?.data)
        assertContentEquals(expectedFingerprint, roundTrip.outerFingerprint?.data)
    }

    @Test
    fun legacyConvoEventEntryDecodesWithNullCanonicalFields() {
        val legacyJSON = """
        {
            "seq": 1,
            "epoch": 0,
            "msgId": "msg-001",
            "messageType": "app",
            "ciphertext": {"${"$"}bytes": "AQID"},
            "paddedSize": 3,
            "createdAt": "2026-08-21T00:00:00.000Z"
        }
        """.trimIndent()

        val entry = json.decodeFromString<BlueCatbirdMlsDSGetConvoEventsConvoEventEntry>(legacyJSON)
        assertEquals(1, entry.seq)
        assertEquals(0, entry.epoch)
        assertNull(entry.entryId)
        assertNull(entry.entryKind)
        assertNull(entry.acceptedPayloadSha256)
        assertNull(entry.signedRequest)
        assertNull(entry.outerFingerprint)
    }
}
