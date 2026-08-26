import Foundation
import Petrel
import PetrelCatbird
import XCTest

final class GetConvoEventsContractTests: XCTestCase {
    func testCleanConvoEventEntryDecodesWithAllCanonicalFields() throws {
        let cleanJSON = """
        {
            "seq": 1,
            "epoch": 0,
            "msgId": "msg-001",
            "messageType": "app",
            "ciphertext": {"$bytes": "AQID"},
            "paddedSize": 3,
            "createdAt": "2026-08-21T00:00:00.000Z",
            "entryId": "00112233-4455-4677-8899-aabbccddeeff",
            "entryKind": "blue.catbird.chat.defs#creationEntry",
            "acceptedPayloadSha256": {"$bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="},
            "signedRequest": {"$bytes": "AQ=="},
            "outerFingerprint": {"$bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="}
        }
        """.data(using: .utf8)!

        let entry = try JSONDecoder().decode(
            BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry.self,
            from: cleanJSON
        )
        XCTAssertEqual(entry.seq, 1)
        XCTAssertEqual(entry.epoch, 0)
        XCTAssertEqual(entry.msgId, "msg-001")
        XCTAssertEqual(entry.messageType, "app")
        XCTAssertEqual(entry.entryId, "00112233-4455-4677-8899-aabbccddeeff")
        XCTAssertEqual(entry.entryKind, "blue.catbird.chat.defs#creationEntry")
        XCTAssertEqual(entry.acceptedPayloadSha256?.data.count, 32)
        XCTAssertEqual(entry.signedRequest?.data, Data([0x01]))
        XCTAssertEqual(entry.outerFingerprint?.data.count, 32)

        let reencoded = try JSONEncoder().encode(entry)
        let roundTrip = try JSONDecoder().decode(
            BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry.self,
            from: reencoded
        )
        XCTAssertEqual(roundTrip.entryId, entry.entryId)
        XCTAssertEqual(roundTrip.entryKind, entry.entryKind)
        XCTAssertEqual(roundTrip.acceptedPayloadSha256?.data, entry.acceptedPayloadSha256?.data)
        XCTAssertEqual(roundTrip.signedRequest?.data, entry.signedRequest?.data)
        XCTAssertEqual(roundTrip.outerFingerprint?.data, entry.outerFingerprint?.data)
    }

    func testLegacyConvoEventEntryDecodesWithNilCanonicalFields() throws {
        let legacyJSON = """
        {
            "seq": 1,
            "epoch": 0,
            "msgId": "msg-001",
            "messageType": "app",
            "ciphertext": {"$bytes": "AQID"},
            "paddedSize": 3,
            "createdAt": "2026-08-21T00:00:00.000Z"
        }
        """.data(using: .utf8)!

        let entry = try JSONDecoder().decode(
            BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry.self,
            from: legacyJSON
        )
        XCTAssertEqual(entry.seq, 1)
        XCTAssertEqual(entry.epoch, 0)
        XCTAssertNil(entry.entryId)
        XCTAssertNil(entry.entryKind)
        XCTAssertNil(entry.acceptedPayloadSha256)
        XCTAssertNil(entry.signedRequest)
        XCTAssertNil(entry.outerFingerprint)
    }
}
