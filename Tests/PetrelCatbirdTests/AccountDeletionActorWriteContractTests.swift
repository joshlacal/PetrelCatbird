import Foundation
import Petrel
import PetrelCatbird
import XCTest

/// The account-deleted entry carries the deleting device's signed request and a
/// repository proof of that device's key as optional, declared fields.
final class AccountDeletionActorWriteContractTests: XCTestCase {
    private static let writeBody = Data(
        #"{"operationId":"7b6f1e0a-2c3d-4e5f-8a9b-0c1d2e3f4a5b","actorDeviceId":"0f1e2d3c-4b5a-4968-8776-655443322110","endpointNsid":"blue.catbird.labyrinth.deleteAccountData","payloadSha256":"RBNvo1WzZ4oRRq0W9-hknpT7T8If536DEMBg9hyq_4o","signedAt":"2026-11-16T12:00:00.000Z"}"#.utf8
    )
    private static let deviceSignature = Data((0x40 ..< 0x80).map { UInt8($0) })
    private static let keyProof = Data([0x3a, 0xa2, 0x65, 0x72, 0x6f, 0x6f, 0x74, 0x73])

    private static func entryJSON(includeActorWrite: Bool) -> Data {
        var object: [String: Any] = [
            "entryId": "00112233-4455-4677-8899-aabbccddeeff",
            "conversationId": "10112233-4455-4677-8899-aabbccddeeff",
            "seq": 42,
            "deletedDid": "did:plc:abcdefghijklmnopqrstuvwx",
            "sequencerDid": "did:web:mlschat.catbird.blue",
            "sequencerAuthority": [
                "sequencerDid": "did:web:mlschat.catbird.blue",
                "keyId": "did:web:mlschat.catbird.blue#mls-receipt-1",
                "signature": ["$bytes": Data(repeating: 0x11, count: 64).base64EncodedString()],
            ],
            "receivedAt": "2026-11-16T12:00:01.000Z",
        ]
        if includeActorWrite {
            object["actorWrite"] = [
                "writeBody": ["$bytes": writeBody.base64EncodedString()],
                "signature": ["$bytes": deviceSignature.base64EncodedString()],
            ]
            object["keyProof"] = ["$bytes": keyProof.base64EncodedString()]
        }
        return try! JSONSerialization.data(withJSONObject: object, options: [.sortedKeys])
    }

    func testEntryWithActorWriteAndKeyProofDecodesThemAsDeclaredFields() throws {
        let entry = try JSONDecoder().decode(
            BlueCatbirdChatDefs.AccountDeletedEntry.self,
            from: Self.entryJSON(includeActorWrite: true)
        )

        let actorWrite = try XCTUnwrap(entry.actorWrite)
        XCTAssertEqual(actorWrite.writeBody.data, Self.writeBody)
        XCTAssertEqual(actorWrite.signature.data, Self.deviceSignature)
        XCTAssertEqual(entry.keyProof?.data, Self.keyProof)
        XCTAssertTrue(entry.extraFields.isEmpty, "actorWrite and keyProof must be declared, not carried as unknown fields")

        let roundTrip = try JSONDecoder().decode(
            BlueCatbirdChatDefs.AccountDeletedEntry.self,
            from: JSONEncoder().encode(entry)
        )
        XCTAssertEqual(roundTrip.actorWrite?.writeBody.data, Self.writeBody)
        XCTAssertEqual(roundTrip.actorWrite?.signature.data, Self.deviceSignature)
        XCTAssertEqual(roundTrip.keyProof?.data, Self.keyProof)
    }

    func testEntryWithoutActorWriteStillDecodes() throws {
        let entry = try JSONDecoder().decode(
            BlueCatbirdChatDefs.AccountDeletedEntry.self,
            from: Self.entryJSON(includeActorWrite: false)
        )

        XCTAssertNil(entry.actorWrite)
        XCTAssertNil(entry.keyProof)
        XCTAssertEqual(entry.seq, 42)
        XCTAssertTrue(entry.extraFields.isEmpty)
    }
}
