import Foundation
import Petrel
import PetrelCatbird
import Testing

@Suite("Circle Lexicon Tests")
struct CircleLexiconTests {
    /// TIDs are 13 characters; a short literal like "3abc" is not a valid TID.
    private static let circleId = "3l7revaaaaaaa"

    @Test func circleSummaryCarriesSpaceIdentityAndCircleId() throws {
        let data = """
        {"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc",\
        "circleId":"\(Self.circleId)","name":"Family","owner":"did:plc:owner","memberCount":3}
        """.data(using: .utf8)!
        let summary = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: data)
        #expect(summary.name == "Family")
        #expect(summary.uri.description == "at://did:plc:owner/space/blue.catbird.circle/3abc")
        #expect(summary.circleId.description == Self.circleId)
        #expect(summary.memberCount == 3)
    }

    /// `memberCount` and `muted` are optional; absence must decode as nil rather
    /// than defaulting to a value that would misreport Circle size or mute state.
    @Test func circleSummaryOmitsOptionalFieldsWhenAbsent() throws {
        let data = """
        {"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc",\
        "circleId":"\(Self.circleId)","name":"Family","owner":"did:plc:owner"}
        """.data(using: .utf8)!
        let summary = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: data)
        #expect(summary.memberCount == nil)
        #expect(summary.muted == nil)
    }

    /// `circleId` is required, so a summary lacking it must not decode. This is what
    /// lets a client follow a Circle across a Space migration.
    @Test func circleSummaryRequiresCircleId() throws {
        let data = """
        {"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc","name":"Family","owner":"did:plc:owner"}
        """.data(using: .utf8)!
        #expect(throws: (any Swift.Error).self) {
            _ = try JSONDecoder().decode(BlueCatbirdCircleDefs.CircleSummary.self, from: data)
        }
    }

    @Test func metadataCarriesCircleIdAndMigrationPointers() throws {
        let data = """
        {"$type":"blue.catbird.circle.metadata","circleId":"\(Self.circleId)","name":"Family",\
        "createdAt":"2026-08-26T00:00:00Z",\
        "migratedFrom":"at://did:plc:owner/space/blue.catbird.circle/3old"}
        """.data(using: .utf8)!
        let record = try JSONDecoder().decode(BlueCatbirdCircleMetadata.self, from: data)
        #expect(record.circleId.description == Self.circleId)
        #expect(record.name == "Family")
        #expect(record.migratedFrom?.description == "at://did:plc:owner/space/blue.catbird.circle/3old")
        #expect(record.migratedTo == nil)
    }

    /// Activation returns a refreshed summary rather than an access-state enum, so a
    /// client needs no second round trip after creating a Circle.
    @Test func activateCircleReturnsRefreshedSummary() throws {
        let data = """
        {"circle":{"uri":"at://did:plc:owner/space/blue.catbird.circle/3abc",\
        "circleId":"\(Self.circleId)","name":"Family","owner":"did:plc:owner","memberCount":2}}
        """.data(using: .utf8)!
        let output = try JSONDecoder().decode(BlueCatbirdCircleActivateCircle.Output.self, from: data)
        #expect(output.circle.name == "Family")
        #expect(output.circle.circleId.description == Self.circleId)
        #expect(output.circle.memberCount == 2)
    }
}
