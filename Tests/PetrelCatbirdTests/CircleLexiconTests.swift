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

    @Test func circleOperationDecodesCanonicalUUID() throws {
        let uuid = "12345678-1234-4234-8234-123456789abc"
        let data = """
        {"id":"\(uuid)","status":"complete","space":"at://did:plc:owner/space/blue.catbird.circle/3abc"}
        """.data(using: .utf8)!
        let op = try JSONDecoder().decode(BlueCatbirdCircleDefs.Operation.self, from: data)
        #expect(op.id.count == 36)
        #expect(op.id == uuid)
        #expect(op.status == .value_complete)
    }

    @Test func activateSpaceDecodesClosedEnum() throws {
        let data = #"{"accessState":"active"}"#.data(using: .utf8)!
        let output = try JSONDecoder().decode(BlueCatbirdCircleActivateSpace.Output.self, from: data)
        #expect(output.accessState == .value_active)
    }
}
