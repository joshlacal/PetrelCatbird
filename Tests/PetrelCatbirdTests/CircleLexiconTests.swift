import Foundation
import Petrel
import PetrelCatbird
import Testing

@Suite("Circle Lexicon Tests")
struct CircleLexiconTests {
    @Test func circleSummaryDecodesSpaceIdentity() throws {
        let data = #"{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","name":"Family","owner":"did:plc:owner","accessState":"active"}"#.data(using: .utf8)!
        let summary = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: data)
        #expect(summary.name == "Family")
        #expect(summary.uri.description == "at://did:plc:owner/space/blue.catbird.circle/3abc")
    }
}
