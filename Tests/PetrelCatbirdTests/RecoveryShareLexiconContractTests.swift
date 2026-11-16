import Foundation
import Petrel
import PetrelCatbird
import XCTest

/// Contract for the five erasable recovery-share methods.
final class RecoveryShareLexiconContractTests: XCTestCase {
    private static let vdeviceId = Data((0x00 ..< 0x10).map { UInt8($0) })

    private static func bytes(_ data: Data) -> [String: String] {
        ["$bytes": data.base64EncodedString()]
    }

    private static func jsonObject(_ value: some Encodable) throws -> [String: Any] {
        let data = try JSONEncoder().encode(value)
        return try XCTUnwrap(JSONSerialization.jsonObject(with: data) as? [String: Any])
    }

    func testGetRecoveryShareCarriesTheFetchTokenAndEphemeralKeyInTheBody() throws {
        let input = BlueCatbirdLabyrinthGetRecoveryShare.Input(
            vdeviceId: Bytes(data: Self.vdeviceId),
            fetchToken: Bytes(data: Data(repeating: 0x21, count: 32)),
            recipientPublicKey: Bytes(data: Data(repeating: 0x22, count: 32))
        )
        let object = try Self.jsonObject(input)
        XCTAssertEqual(Set(object.keys), ["vdeviceId", "fetchToken", "recipientPublicKey"])
    }

    func testGetRecoveryShareOutputIsSealedNotPlain() throws {
        let json = try JSONSerialization.data(withJSONObject: [
            "vdeviceId": Self.bytes(Self.vdeviceId),
            "enc": Self.bytes(Data(repeating: 0x31, count: 32)),
            "sealedShare": Self.bytes(Data(repeating: 0x32, count: 48)),
        ])
        let output = try JSONDecoder().decode(BlueCatbirdLabyrinthGetRecoveryShare.Output.self, from: json)
        XCTAssertEqual(output.vdeviceId.data, Self.vdeviceId)
        XCTAssertEqual(output.enc.data, Data(repeating: 0x31, count: 32))
        XCTAssertEqual(output.sealedShare.data, Data(repeating: 0x32, count: 48))
        XCTAssertNil(try Self.jsonObject(output)["share"])
    }

    func testRecoveryShareMethodsExposeTypedErrors() {
        XCTAssertEqual(BlueCatbirdLabyrinthGetRecoveryShare.Error.recoveryShareUnavailable.rawValue, "RecoveryShareUnavailable")
        XCTAssertEqual(BlueCatbirdLabyrinthPutRecoveryShare.Error.recoveryShareConflict.rawValue, "RecoveryShareConflict")
        XCTAssertEqual(BlueCatbirdLabyrinthPutRecoveryShare.Error.recoveryShareLimitReached.rawValue, "RecoveryShareLimitReached")
        XCTAssertEqual(BlueCatbirdLabyrinthDeleteRecoveryShare.Error.recoveryShareNotFound.rawValue, "RecoveryShareNotFound")
    }

    func testPutRecoveryShareInputCarriesShareVerifierAndMechanism() throws {
        let input = BlueCatbirdLabyrinthPutRecoveryShare.Input(
            vdeviceId: Bytes(data: Self.vdeviceId),
            share: Bytes(data: Data(repeating: 0x41, count: 32)),
            fetchVerifier: Bytes(data: Data(repeating: 0x42, count: 32)),
            mechanism: "passkey"
        )
        let object = try Self.jsonObject(input)
        XCTAssertEqual(Set(object.keys), ["vdeviceId", "share", "fetchVerifier", "mechanism"])
        XCTAssertEqual(object["mechanism"] as? String, "passkey")
    }

    func testDeleteRecoveryShareOutputDecodesWithAndWithoutReceipt() throws {
        let withReceipt = try JSONSerialization.data(withJSONObject: [
            "vdeviceId": Self.bytes(Self.vdeviceId),
            "deletedAt": "2026-11-16T12:00:00.000Z",
            "receipt": [
                "keyId": "did:web:mlschat.catbird.blue#atproto",
                "signature": Self.bytes(Data(repeating: 0x51, count: 64)),
            ],
        ])
        let decoded = try JSONDecoder().decode(BlueCatbirdLabyrinthDeleteRecoveryShare.Output.self, from: withReceipt)
        XCTAssertEqual(decoded.receipt?.keyId, "did:web:mlschat.catbird.blue#atproto")
        XCTAssertEqual(decoded.receipt?.signature.data, Data(repeating: 0x51, count: 64))

        let withoutReceipt = try JSONSerialization.data(withJSONObject: [
            "vdeviceId": Self.bytes(Self.vdeviceId),
            "deletedAt": "2026-11-16T12:00:00.000Z",
        ])
        let bare = try JSONDecoder().decode(BlueCatbirdLabyrinthDeleteRecoveryShare.Output.self, from: withoutReceipt)
        XCTAssertNil(bare.receipt)
    }

    func testRecoveryShareListsDecode() throws {
        let shares = try JSONSerialization.data(withJSONObject: [
            "shares": [[
                "vdeviceId": Self.bytes(Self.vdeviceId),
                "mechanism": "code",
                "createdAt": "2026-11-16T12:00:00.000Z",
                "fetchCount": 3,
            ]],
        ])
        let list = try JSONDecoder().decode(BlueCatbirdLabyrinthListRecoveryShares.Output.self, from: shares)
        XCTAssertEqual(list.shares.count, 1)
        XCTAssertEqual(list.shares.first?.fetchCount, 3)

        let events = try JSONSerialization.data(withJSONObject: [
            "events": [[
                "id": "e1",
                "vdeviceId": Self.bytes(Self.vdeviceId),
                "kind": "fetch",
                "actorDeviceId": "0f1e2d3c-4b5a-4968-8776-655443322110",
                "at": "2026-11-16T12:00:00.000Z",
            ]],
            "cursor": "e1",
        ])
        let log = try JSONDecoder().decode(BlueCatbirdLabyrinthListRecoveryShareEvents.Output.self, from: events)
        XCTAssertEqual(log.events.count, 1)
        XCTAssertEqual(log.cursor, "e1")
        let event = try Self.jsonObject(XCTUnwrap(log.events.first))
        XCTAssertEqual(event["kind"] as? String, "fetch")
    }
}
