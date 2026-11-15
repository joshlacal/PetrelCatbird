package blue.catbird.petrel.generated

import blue.catbird.petrel.core.types.ATProtocolDate
import blue.catbird.petrel.core.types.Bytes
import blue.catbird.petrel.core.types.DID
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

class BlobUploadPreparationContractTest {
    private val json = Json

    private fun body(
        mediaType: String = "image/png",
        plaintextSize: Long = 16L,
    ): BlueCatbirdChatDefsBlobUploadPreparationBody {
        val bytes = Bytes(ByteArray(32) { 7 })
        return BlueCatbirdChatDefsBlobUploadPreparationBody(
            signatureDomain = "CATBIRD-CHAT-BLOB-PREPARE\u0000",
            blobId = "00000000-0000-4000-8000-000000000002",
            conversationId = "00000000-0000-4000-8000-000000000001",
            actorDid = DID("plc", "aaaaaaaaaaaaaaaaaaaaaaaa"),
            actorDeviceId = "device-1",
            keyId = "key-1",
            authGeneration = 1,
            prior = BlueCatbirdChatDefsConversationCoordinates(
                conversationId = "00000000-0000-4000-8000-000000000001",
                generation = 3_000_000_000L,
                stateVersion = 2,
                groupId = bytes,
                epoch = 3_000_000_001L,
                groupContextHash = bytes,
                confirmationTag = bytes,
                lifecycle = BlueCatbirdChatDefsDefsLifecycle.value_active,
            ),
            ciphertextSha256 = bytes,
            ciphertextSize = plaintextSize + 16L,
            mediaType = mediaType,
            plaintextSize = plaintextSize,
            purpose = BlueCatbirdChatDefsDefsBlobPurpose.value_attachment,
            idempotencyKey = "00000000-0000-4000-8000-000000000003",
            signedAt = ATProtocolDate("2024-03-09T16:00:00Z"),
        )
    }

    @Test
    fun mediaMetadataRoundTripsAndParticipatesInEquality() {
        val body = body()
        val encoded = json.encodeToString(body)
        val decoded = json.decodeFromString<BlueCatbirdChatDefsBlobUploadPreparationBody>(encoded)

        assertEquals("image/png", decoded.mediaType)
        assertEquals(16L, decoded.plaintextSize)
        assertEquals(3_000_000_000L, decoded.prior.generation)
        assertEquals(3_000_000_001L, decoded.prior.epoch)
        assertEquals(body, decoded)
        assertNotEquals(body, body(mediaType = "image/jpeg"))
        assertNotEquals(body, body(plaintextSize = 17L))

        val objectValue = json.parseToJsonElement(encoded).jsonObject
        assertEquals("image/png", objectValue.getValue("mediaType").jsonPrimitive.content)
        assertEquals(16L, objectValue.getValue("plaintextSize").jsonPrimitive.content.toLong())
    }

    @Test
    fun deviceRevocationRecordRoundTrips() {
        val record = BlueCatbirdChatDeviceRevocation(
            targetDeviceId = "00000000-0000-4000-8000-000000000010",
            actorDeviceId = "00000000-0000-4000-8000-000000000011",
            signerPublicKey = Bytes(ByteArray(32) { 1 }),
            signature = Bytes(ByteArray(64) { 2 }),
            createdAt = ATProtocolDate("2026-11-15T00:00:00Z"),
        )
        val encoded = json.encodeToString(record)
        val decoded = json.decodeFromString<BlueCatbirdChatDeviceRevocation>(encoded)
        assertEquals(record, decoded)
    }

    @Test
    fun mediaMetadataIsRequiredWhenDecoding() {
        val encoded = json.encodeToString(body())
        val objectValue = json.parseToJsonElement(encoded).jsonObject
        assertFails {
            json.decodeFromString<BlueCatbirdChatDefsBlobUploadPreparationBody>(
                Json.encodeToString(objectValue - "mediaType")
            )
        }
        assertFails {
            json.decodeFromString<BlueCatbirdChatDefsBlobUploadPreparationBody>(
                Json.encodeToString(objectValue - "plaintextSize")
            )
        }
    }
}
