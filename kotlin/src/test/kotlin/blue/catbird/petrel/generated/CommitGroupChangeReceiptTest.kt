package blue.catbird.petrel.generated

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertNotNull
import kotlin.test.assertFails

class CommitGroupChangeReceiptTest {
    private val json = Json { ignoreUnknownKeys = true }
    private val legacy = """{"success":true,"newEpoch":7}"""
    private val full = """{"success":true,"newEpoch":8,"receipt":{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}"""

    @Test
    fun legacyOutputDecodesWithoutReceipt() {
        val output = json.decodeFromString<BlueCatbirdMlsChatCommitGroupChangeOutput>(legacy)
        assertNull(output.receipt)
    }

    @Test
    fun fullReceiptDecodesAndRoundTripsLosslessly() {
        val output = json.decodeFromString<BlueCatbirdMlsChatCommitGroupChangeOutput>(full)
        val receipt = assertNotNull(output.receipt)
        assertEquals("convo-1", receipt.convoId)
        assertEquals(8, receipt.epoch)
        assertEquals(3, receipt.sequencerTerm)
        assertContentEquals(byteArrayOf(1, 2, 3), receipt.commitHash.data)
        assertEquals("did:web:sequencer.example", receipt.sequencerDid.toString())
        assertEquals(1_710_000_000, receipt.issuedAt)
        assertContentEquals(byteArrayOf(4, 5, 6), receipt.signature.data)

        val roundTrip = json.decodeFromString<BlueCatbirdMlsChatCommitGroupChangeOutput>(
            json.encodeToString(output)
        )
        assertEquals(3, roundTrip.receipt?.sequencerTerm)
        assertContentEquals(byteArrayOf(1, 2, 3), roundTrip.receipt?.commitHash?.data)
        assertContentEquals(byteArrayOf(4, 5, 6), roundTrip.receipt?.signature?.data)
    }

    @Test
    fun malformedPresentReceiptsAreRejected() {
        val malformed = listOf(
            """{"success":true,"receipt":{"convoId":"convo-1","epoch":8,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}""",
            """{"success":true,"receipt":{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"not-a-did","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}""",
            """{"success":true,"receipt":{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"${'$'}bytes":"%%%"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}""",
            """{"success":true,"receipt":{"convoId":"convo-1","epoch":"eight","sequencerTerm":3,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}""",
        )
        malformed.forEach { fixture ->
            assertFails {
                json.decodeFromString<BlueCatbirdMlsChatCommitGroupChangeOutput>(fixture)
            }
        }
    }
}
