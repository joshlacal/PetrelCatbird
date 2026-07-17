package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.ATProtocolDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TransitionAttestationContractTest {
    private val json = Json { ignoreUnknownKeys = true }
    private val wireActions = listOf(
        "addMembers",
        "removeMembers",
        "swapMembers",
        "commit",
        "updateMetadata",
    )
    private val receipt = """{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}"""

    @Test
    fun transitionActionIsClosedAndRoundTripsExactWireValues() {
        wireActions.forEach { wireValue ->
            val encoded = json.encodeToString(wireValue)
            val action = json.decodeFromString<BlueCatbirdMlsChatBeginTransitionAttestationInputAction>(encoded)
            assertEquals(encoded, json.encodeToString(action))
        }

        assertFails {
            json.decodeFromString<BlueCatbirdMlsChatBeginTransitionAttestationInputAction>("\"unknown\"")
        }
    }

    @Test
    fun finalizeRequiresACompleteReceipt() {
        val fixtures = listOf(
            """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"}}""",
            """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"},"receipt":{"convoId":"convo-1","epoch":8,"commitHash":{"${'$'}bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"${'$'}bytes":"BAUG"}}}""",
        )

        fixtures.forEach { fixture ->
            assertFails {
                json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(fixture)
            }
        }
    }

    @Test
    fun strictReceiptReferencesRejectUnknownNestedKeysEvenWhenDecoderIgnoresThem() {
        val receiptWithUnknownKey = receipt.dropLast(1) + ",\"unexpected\":true}"
        val finalize = """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"},"receipt":$receiptWithUnknownKey}"""
        val commit = """{"success":true,"newEpoch":8,"receipt":$receiptWithUnknownKey}"""

        assertFails { json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(finalize) }
        assertFails { json.decodeFromString<BlueCatbirdMlsChatCommitGroupChangeOutput>(commit) }
    }

    @Test
    fun strictReceiptReferenceValidatesPresentDiscriminator() {
        val canonicalType = "blue.catbird.mlsChat.commitGroupChange#sequencerReceipt"
        val typedReceipt = receipt.dropLast(1) + ",\"${'$'}type\":\"$canonicalType\"}"
        val valid = """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"},"receipt":$typedReceipt}"""
        json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(valid)

        val wrongReceipt = receipt.dropLast(1) + ",\"${'$'}type\":\"blue.catbird.mlsChat.commitGroupChange#other\"}"
        val wrong = """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"},"receipt":$wrongReceipt}"""
        assertFails { json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(wrong) }
    }

    @Test
    fun finalizeReceiptRoundTripsLosslessly() {
        val fixture = """{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"${'$'}bytes":"AQID"},"receipt":$receipt}"""
        val input = json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(fixture)
        val roundTrip = json.decodeFromString<BlueCatbirdMlsChatFinalizeGroupChangeInput>(
            json.encodeToString(input),
        )

        assertEquals("convo-1", roundTrip.receipt.convoId)
        assertEquals(8, roundTrip.receipt.epoch)
        assertEquals(3, roundTrip.receipt.sequencerTerm)
        assertContentEquals(byteArrayOf(1, 2, 3), roundTrip.receipt.commitHash.data)
        assertContentEquals(byteArrayOf(4, 5, 6), roundTrip.receipt.signature.data)
    }

    @Test
    fun existingDeviceAuthPublicTypesRemainAvailable() {
        val begin = BlueCatbirdMlsChatBeginDeviceAuthBindingInput(deviceId = "device-1")
        assertEquals("device-1", begin.deviceId)

        val complete = BlueCatbirdMlsChatCompleteDeviceAuthBindingOutput(
            deviceId = "device-1",
            boundAt = ATProtocolDate("2024-03-09T16:00:00Z"),
            bindingVersion = 1,
        )
        assertEquals("device-1", complete.deviceId)
        assertEquals(1, complete.bindingVersion)
    }
}
