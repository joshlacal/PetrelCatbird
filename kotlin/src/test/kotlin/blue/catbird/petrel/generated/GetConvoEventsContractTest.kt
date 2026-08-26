package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.ATProtocolDate
import blue.catbird.petrel.core.types.Bytes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

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
            "acceptedPayloadSha256": {"${"$"}bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="},
            "signedRequest": {"${"$"}bytes": "AQ=="},
            "outerFingerprint": {"${"$"}bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="}
        }
        """.trimIndent()

        val entry = json.decodeFromString<BlueCatbirdMlsDSGetConvoEventsConvoEventEntry>(cleanJSON)
        assertEquals(1, entry.seq)
        assertEquals(0, entry.epoch)
        assertEquals("msg-001", entry.msgId)
        assertEquals("app", entry.messageType)
        assertEquals("00112233-4455-4677-8899-aabbccddeeff", entry.entryId)
        assertEquals("blue.catbird.chat.defs#creationEntry", entry.entryKind)
        assertEquals(32, entry.acceptedPayloadSha256?.data?.size)
        assertTrue(byteArrayOf(0x01).contentEquals(entry.signedRequest?.data ?: ByteArray(0)))
        assertEquals(32, entry.outerFingerprint?.data?.size)

        val reencoded = json.encodeToString(entry)
        val roundTrip = json.decodeFromString<BlueCatbirdMlsDSGetConvoEventsConvoEventEntry>(reencoded)
        assertEquals(entry.entryId, roundTrip.entryId)
        assertEquals(entry.entryKind, roundTrip.entryKind)
        assertTrue(entry.acceptedPayloadSha256?.data?.contentEquals(roundTrip.acceptedPayloadSha256?.data ?: ByteArray(0)) == true)
        assertTrue(entry.signedRequest?.data?.contentEquals(roundTrip.signedRequest?.data ?: ByteArray(0)) == true)
        assertTrue(entry.outerFingerprint?.data?.contentEquals(roundTrip.outerFingerprint?.data ?: ByteArray(0)) == true)
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
