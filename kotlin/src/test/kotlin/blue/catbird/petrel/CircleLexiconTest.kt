package blue.catbird.petrel

import blue.catbird.petrel.generated.BlueCatbirdCircleDefsCircleSummary
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
}
