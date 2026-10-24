package blue.catbird.petrel.generated

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class SpaceDeletionContractTest {
    @Test
    fun deletedGenerationHasDistinctTypedErrorMetadata() {
        val meks = BlueCatbirdLabyrinthFindMeksError.SpaceDeleted
        val attachment = BlueCatbirdLabyrinthGetAttachmentBlobError.SpaceDeleted
        assertEquals("SpaceDeleted", meks.name)
        assertEquals(meks.name, attachment.name)
        assertNotNull(meks.description)
        assertEquals(meks.description, attachment.description)
        assertNotEquals(BlueCatbirdLabyrinthGetAttachmentBlobError.NotFound.name, attachment.name)
    }
}
