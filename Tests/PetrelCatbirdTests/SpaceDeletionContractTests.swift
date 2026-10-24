import Foundation
import Petrel
import PetrelCatbird
import Testing

@Suite("Terminal conversation Space errors")
struct SpaceDeletionContractTests {
    @Test
    func deletedGenerationRemainsDistinctFromMissingData() throws {
        let body = Data(#"{"error":"SpaceDeleted","message":"generation permanently deleted"}"#.utf8)
        let meks = try #require(ATProtoErrorParser.parse(
            data: body,
            statusCode: 410,
            errorType: BlueCatbirdLabyrinthFindMeks.Error.self
        ))
        #expect(meks.error == .spaceDeleted)
        #expect(meks.statusCode == 410)
        #expect(meks.message == "generation permanently deleted")

        let attachment = try #require(ATProtoErrorParser.parse(
            data: body,
            statusCode: 410,
            errorType: BlueCatbirdLabyrinthGetAttachmentBlob.Error.self
        ))
        #expect(attachment.error == .spaceDeleted)
        #expect(attachment.error != .notFound)
        #expect(attachment.message == meks.message)
    }
}
