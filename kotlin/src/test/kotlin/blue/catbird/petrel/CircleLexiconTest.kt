package blue.catbird.petrel

import blue.catbird.petrel.generated.BlueCatbirdCircleActivateCircleOutput
import blue.catbird.petrel.generated.BlueCatbirdCircleDefsCircleSummary
import blue.catbird.petrel.generated.BlueCatbirdCircleMetadata
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CircleLexiconTest {
    private val json = Json { ignoreUnknownKeys = true }

    /** TIDs are 13 characters; a short literal like "3abc" is not a valid TID. */
    private val circleId = "3l7revaaaaaaa"

    @Test
    fun circleSummaryCarriesSpaceIdentityAndCircleId() {
        val data = """{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","circleId":"$circleId","name":"Family","owner":"did:plc:owner","memberCount":3}"""
        val summary = json.decodeFromString<BlueCatbirdCircleDefsCircleSummary>(data)
        assertEquals("Family", summary.name)
        assertEquals("at://did:plc:owner/space/blue.catbird.circle/3abc", summary.uri.toString())
        assertEquals(circleId, summary.circleId)
        assertEquals(3, summary.memberCount)
    }

    /**
     * `memberCount` and `muted` are optional; absence must decode as null rather than
     * defaulting to a value that would misreport Circle size or mute state.
     */
    @Test
    fun circleSummaryOmitsOptionalFieldsWhenAbsent() {
        val data = """{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","circleId":"$circleId","name":"Family","owner":"did:plc:owner"}"""
        val summary = json.decodeFromString<BlueCatbirdCircleDefsCircleSummary>(data)
        assertNull(summary.memberCount)
        assertNull(summary.muted)
    }

    @Test
    fun metadataCarriesCircleIdAndMigrationPointers() {
        val data = """{"circleId":"$circleId","name":"Family","createdAt":"2026-08-26T00:00:00Z","migratedFrom":"at://did:plc:owner/space/blue.catbird.circle/3old"}"""
        val record = json.decodeFromString<BlueCatbirdCircleMetadata>(data)
        assertEquals(circleId, record.circleId)
        assertEquals("Family", record.name)
        assertEquals(
            "at://did:plc:owner/space/blue.catbird.circle/3old",
            record.migratedFrom?.toString()
        )
        assertNull(record.migratedTo)
    }

    /**
     * Activation returns a refreshed summary rather than an access-state enum, so a
     * client needs no second round trip after creating a Circle.
     */
    @Test
    fun activateCircleReturnsRefreshedSummary() {
        val data = """{"circle":{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","circleId":"$circleId","name":"Family","owner":"did:plc:owner","memberCount":2}}"""
        val output = json.decodeFromString<BlueCatbirdCircleActivateCircleOutput>(data)
        assertEquals("Family", output.circle.name)
        assertEquals(circleId, output.circle.circleId)
        assertEquals(2, output.circle.memberCount)
    }
}
