import Foundation
import PetrelCatbird
import Testing

@Suite("Direct request transport contract")
struct DirectRequestContractTests {
    @Test func bothWireVersionsRemainDecodable() throws {
        for version in ["1", "2"] {
            let value = try JSONDecoder().decode(BlueCatbirdChatDefs.DefsProtocolVersion.self, from: Data("\"\(version)\"".utf8))
            #expect(value.rawValue == version)
        }
    }

    @Test func unknownOutcomeRemainsDistinctFromAReceipt() throws {
        let raw = Data(#"{"$type":"blue.catbird.chat.defs#directRequestUnknownOutcome","requestId":"12345678-1234-4234-8234-123456789abc"}"#.utf8)
        let value = try JSONDecoder().decode(BlueCatbirdChatDefs.DirectRequestOutcome.self, from: raw)
        guard case let .blueCatbirdChatDefsDirectRequestUnknownOutcome(outcome) = value else {
            Issue.record("Unknown must not be decoded as a publication or terminal receipt")
            return
        }
        #expect(outcome.requestId == "12345678-1234-4234-8234-123456789abc")
    }
}
