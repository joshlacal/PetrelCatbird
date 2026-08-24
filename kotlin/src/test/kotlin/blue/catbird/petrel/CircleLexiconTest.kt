package blue.catbird.petrel

import blue.catbird.petrel.generated.BlueCatbirdCircleActivateSpaceOutput
import blue.catbird.petrel.generated.BlueCatbirdCircleDefsCircleSummary
import blue.catbird.petrel.generated.BlueCatbirdCircleDefsDefsAccessState
import blue.catbird.petrel.generated.BlueCatbirdCircleDefsOperation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class CircleLexiconTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun circleSummaryDecodesSpaceIdentity() {
        val data = """{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","name":"Family","owner":"did:plc:owner","accessState":"active"}"""
        val summary = json.decodeFromString<BlueCatbirdCircleDefsCircleSummary>(data)
        assertEquals("Family", summary.name)
        assertEquals("at://did:plc:owner/space/blue.catbird.circle/3abc", summary.uri.toString())
    }

    @Test
    fun circleOperationDecodesCanonicalUUID() {
        val uuid = "12345678-1234-4234-8234-123456789abc"
        val data = """{"id":"$uuid","status":"complete","space":"at://did:plc:owner/space/blue.catbird.circle/3abc"}"""
        val op = json.decodeFromString<BlueCatbirdCircleDefsOperation>(data)
        assertEquals(36, op.id.length)
        assertEquals(uuid, op.id)
    }

    @Test
    fun activateSpaceDecodesClosedEnum() {
        val data = """{"accessState":"active"}"""
        val output = json.decodeFromString<BlueCatbirdCircleActivateSpaceOutput>(data)
        assertEquals(BlueCatbirdCircleDefsDefsAccessState.value_active, output.accessState)
    }
}
