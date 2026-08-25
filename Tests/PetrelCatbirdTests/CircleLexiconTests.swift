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

    @Test func circleSummaryDecodesMembersWhenPresentAndOmitsWhenAbsent() throws {
        let withMembersData = #"{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","name":"Family","owner":"did:plc:owner","accessState":"active","members":["did:plc:alice","did:plc:bob"]}"#.data(using: .utf8)!
        let summaryWithMembers = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: withMembersData)
        #expect(summaryWithMembers.members?.count == 2)
        #expect(summaryWithMembers.members?[0].didString() == "did:plc:alice")

        let withoutMembersData = #"{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","name":"Family","owner":"did:plc:owner","accessState":"active"}"#.data(using: .utf8)!
        let summaryWithoutMembers = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: withoutMembersData)
        #expect(summaryWithoutMembers.members == nil)
    }

    @Test func getOperationAndRetryOperationDecode() throws {
        let uuid = "12345678-1234-4234-8234-123456789abc"
        let getOpData = """
        {"id":"\(uuid)","status":"pending","space":"at://did:plc:owner/space/blue.catbird.circle/3abc"}
        """.data(using: .utf8)!
        let getOpOutput = try JSONDecoder().decode(BlueCatbirdCircleGetOperation.Output.self, from: getOpData)
        #expect(getOpOutput.id == uuid)
        #expect(getOpOutput.status == .value_pending)

        let retryOpData = """
        {"id":"\(uuid)","status":"failed","error":"UpstreamUnavailable"}
        """.data(using: .utf8)!
        let retryOpOutput = try JSONDecoder().decode(BlueCatbirdCircleRetryOperation.Output.self, from: retryOpData)
        #expect(retryOpOutput.id == uuid)
        #expect(retryOpOutput.status == .value_failed)
        #expect(retryOpOutput.error == "UpstreamUnavailable")
    }

    @Test func activateSpaceDecodesClosedEnum() throws {
        let data = #"{"accessState":"active"}"#.data(using: .utf8)!
        let output = try JSONDecoder().decode(BlueCatbirdCircleActivateSpace.Output.self, from: data)
        #expect(output.accessState == .value_active)
    }
}
