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
            "acceptedPayloadSha256": {"$bytes": "AQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyA="},
            "signedRequest": {"$bytes": "AQ=="},
            "outerFingerprint": {"$bytes": "oaKjpKWmp6ipqqusra6vsLGys7S1tre4ubq7vL2+v8A="}
        }
        """.data(using: .utf8)!

        let entry = try JSONDecoder().decode(
            BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry.self,
            from: cleanJSON
        )
        let expectedAcceptedSha = Data(1...32)
        let expectedSignedRequest = Data([0x01])
        let expectedFingerprint = Data((0xa1...0xc0).map { UInt8($0) })

        XCTAssertEqual(entry.seq, 1)
        XCTAssertEqual(entry.epoch, 0)
        XCTAssertEqual(entry.msgId, "msg-001")
        XCTAssertEqual(entry.messageType, "app")
        XCTAssertEqual(entry.entryId, "00112233-4455-4677-8899-aabbccddeeff")
        XCTAssertEqual(entry.entryKind, "blue.catbird.chat.defs#creationEntry")
        XCTAssertEqual(entry.acceptedPayloadSha256?.data, expectedAcceptedSha)
        XCTAssertEqual(entry.signedRequest?.data, expectedSignedRequest)
        XCTAssertEqual(entry.outerFingerprint?.data, expectedFingerprint)

        let reencoded = try JSONEncoder().encode(entry)
        let roundTrip = try JSONDecoder().decode(
            BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry.self,
            from: reencoded
        )
        XCTAssertEqual(roundTrip.entryId, entry.entryId)
        XCTAssertEqual(roundTrip.entryKind, entry.entryKind)
        XCTAssertEqual(roundTrip.acceptedPayloadSha256?.data, expectedAcceptedSha)
        XCTAssertEqual(roundTrip.signedRequest?.data, expectedSignedRequest)
        XCTAssertEqual(roundTrip.outerFingerprint?.data, expectedFingerprint)
    }

    func testConvoEventEntryPublicInitializerSetsAllProperties() throws {
        let createdAt = try XCTUnwrap(ATProtocolDate(iso8601String: "2026-08-21T00:00:00.000Z"))
        let ciphertext = Bytes(data: Data([0x01, 0x02, 0x03]))
        let acceptedSha = Bytes(data: Data(1...32))
        let signedReq = Bytes(data: Data([0x05, 0x06]))
        let fingerprint = Bytes(data: Data((0xa1...0xc0).map { UInt8($0) }))

        let entry = BlueCatbirdMlsDSGetConvoEvents.ConvoEventEntry(
            seq: 42,
            epoch: 7,
            msgId: "msg-42",
            messageType: "custom",
            ciphertext: ciphertext,
            paddedSize: 128,
            createdAt: createdAt,
            entryId: "entry-uuid-1234",
            entryKind: "blue.catbird.chat.defs#applicationEntry",
            acceptedPayloadSha256: acceptedSha,
            signedRequest: signedReq,
            outerFingerprint: fingerprint
        )

        XCTAssertEqual(entry.seq, 42)
        XCTAssertEqual(entry.epoch, 7)
        XCTAssertEqual(entry.msgId, "msg-42")
        XCTAssertEqual(entry.messageType, "custom")
        XCTAssertEqual(entry.ciphertext.data, ciphertext.data)
        XCTAssertEqual(entry.paddedSize, 128)
        XCTAssertEqual(entry.createdAt, createdAt)
        XCTAssertEqual(entry.entryId, "entry-uuid-1234")
        XCTAssertEqual(entry.entryKind, "blue.catbird.chat.defs#applicationEntry")
        XCTAssertEqual(entry.acceptedPayloadSha256?.data, acceptedSha.data)
        XCTAssertEqual(entry.signedRequest?.data, signedReq.data)
        XCTAssertEqual(entry.outerFingerprint?.data, fingerprint.data)
    }

    func testCanonicalLexiconSchemaSourceContract() throws {
        let repositoryRoot = URL(fileURLWithPath: #filePath)
            .deletingLastPathComponent()
            .deletingLastPathComponent()
            .deletingLastPathComponent()
        let lexiconURL = repositoryRoot
            .appendingPathComponent("lexicons/blue/catbird/mlsDS/blue.catbird.mlsDS.getConvoEvents.json")
        let data = try Data(contentsOf: lexiconURL)
        let root = try XCTUnwrap(
            JSONSerialization.jsonObject(with: data) as? [String: Any]
        )
        let defs = try XCTUnwrap(root["defs"] as? [String: Any])
        let convoEventEntry = try XCTUnwrap(defs["convoEventEntry"] as? [String: Any])
        let properties = try XCTUnwrap(convoEventEntry["properties"] as? [String: Any])

        let entryIdProp = try XCTUnwrap(properties["entryId"] as? [String: Any])
        XCTAssertEqual(entryIdProp["type"] as? String, "string")

        let entryKindProp = try XCTUnwrap(properties["entryKind"] as? [String: Any])
        XCTAssertEqual(entryKindProp["type"] as? String, "string")
        let knownValues = try XCTUnwrap(entryKindProp["knownValues"] as? [String])
        let expectedKnownValues = [
            "blue.catbird.chat.defs#applicationEntry",
            "blue.catbird.chat.defs#commitEntry",
            "blue.catbird.chat.defs#policyEntry",
            "blue.catbird.chat.defs#metadataEntry",
            "blue.catbird.chat.defs#creationEntry",
            "blue.catbird.chat.defs#participantAcceptanceEntry",
            "blue.catbird.chat.defs#conversationCloseEntry",
            "blue.catbird.chat.defs#resetRequestEntry",
            "blue.catbird.chat.defs#resetActivationEntry",
            "blue.catbird.chat.defs#leafRecoveryFulfillmentEntry",
            "blue.catbird.chat.defs#leaveRequestEntry",
            "blue.catbird.chat.defs#zeroLeafLeaveEntry",
            "blue.catbird.chat.defs#leaveCancellationEntry",
            "blue.catbird.chat.defs#leaveCommitFulfillmentEntry"
        ]
        XCTAssertEqual(knownValues, expectedKnownValues)

        let acceptedShaProp = try XCTUnwrap(properties["acceptedPayloadSha256"] as? [String: Any])
        XCTAssertEqual(acceptedShaProp["type"] as? String, "bytes")
        XCTAssertEqual(acceptedShaProp["minLength"] as? Int, 32)
        XCTAssertEqual(acceptedShaProp["maxLength"] as? Int, 32)

        let signedRequestProp = try XCTUnwrap(properties["signedRequest"] as? [String: Any])
        XCTAssertEqual(signedRequestProp["type"] as? String, "bytes")
        XCTAssertEqual(signedRequestProp["minLength"] as? Int, 1)
        XCTAssertEqual(signedRequestProp["maxLength"] as? Int, 1048576)

        let outerFingerprintProp = try XCTUnwrap(properties["outerFingerprint"] as? [String: Any])
        XCTAssertEqual(outerFingerprintProp["type"] as? String, "bytes")
        XCTAssertEqual(outerFingerprintProp["minLength"] as? Int, 32)
        XCTAssertEqual(outerFingerprintProp["maxLength"] as? Int, 32)

        let required = convoEventEntry["required"] as? [String] ?? []
        for field in ["entryId", "entryKind", "acceptedPayloadSha256", "signedRequest", "outerFingerprint"] {
            XCTAssertFalse(required.contains(field), "\(field) must be optional (not in required)")
        }
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
