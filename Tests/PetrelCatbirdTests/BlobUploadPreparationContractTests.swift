import Foundation
import Petrel
import PetrelCatbird
import XCTest

final class BlobUploadPreparationContractTests: XCTestCase {
    private func makeBody(mediaType: String = "image/png", plaintextSize: Int = 16) throws
        -> BlueCatbirdChatDefs.BlobUploadPreparationBody
    {
        let bytes = Bytes(data: Data(repeating: 7, count: 32))
        let prior = BlueCatbirdChatDefs.ConversationCoordinates(
            conversationId: "00000000-0000-4000-8000-000000000001",
            generation: 1,
            stateVersion: 2,
            groupId: bytes,
            epoch: 3,
            groupContextHash: bytes,
            confirmationTag: bytes,
            lifecycle: .value_active
        )
        return try BlueCatbirdChatDefs.BlobUploadPreparationBody(
            signatureDomain: "CATBIRD-CHAT-BLOB-PREPARE\0",
            blobId: "00000000-0000-4000-8000-000000000002",
            conversationId: prior.conversationId,
            actorDid: DID(didString: "did:plc:aaaaaaaaaaaaaaaaaaaaaaaa"),
            actorDeviceId: "device-1",
            keyId: "key-1",
            authGeneration: 1,
            prior: prior,
            ciphertextSha256: bytes,
            ciphertextSize: plaintextSize + 16,
            mediaType: mediaType,
            plaintextSize: plaintextSize,
            purpose: .value_attachment,
            idempotencyKey: "00000000-0000-4000-8000-000000000003",
            signedAt: ATProtocolDate(date: Date(timeIntervalSince1970: 1_710_000_000))
        )
    }

    func testMediaMetadataRoundTripsThroughJSONAndCBOR() throws {
        let body = try makeBody()
        let encoded = try JSONEncoder().encode(body)
        let decoded = try JSONDecoder().decode(
            BlueCatbirdChatDefs.BlobUploadPreparationBody.self,
            from: encoded
        )

        XCTAssertEqual(decoded.mediaType, "image/png")
        XCTAssertEqual(decoded.plaintextSize, 16)
        XCTAssertEqual(decoded, body)

        let object = try XCTUnwrap(JSONSerialization.jsonObject(with: encoded) as? [String: Any])
        XCTAssertEqual(object["mediaType"] as? String, "image/png")
        XCTAssertEqual(object["plaintextSize"] as? Int, 16)

        let cbor = try XCTUnwrap(body.toCBORValue() as? OrderedCBORMap)
        let fields = Dictionary(uniqueKeysWithValues: cbor.entries.map { ($0.key, $0.value) })
        XCTAssertEqual(fields["mediaType"] as? String, "image/png")
        XCTAssertEqual(fields["plaintextSize"] as? Int, 16)
    }

    func testMediaMetadataParticipatesInEqualityAndIsRequiredWhenDecoding() throws {
        XCTAssertNotEqual(try makeBody(), try makeBody(mediaType: "image/jpeg"))
        XCTAssertNotEqual(try makeBody(), try makeBody(plaintextSize: 17))

        let encoded = try JSONEncoder().encode(makeBody())
        var object = try XCTUnwrap(JSONSerialization.jsonObject(with: encoded) as? [String: Any])
        object.removeValue(forKey: "mediaType")
        XCTAssertThrowsError(
            try JSONDecoder().decode(
                BlueCatbirdChatDefs.BlobUploadPreparationBody.self,
                from: JSONSerialization.data(withJSONObject: object)
            )
        )
        object["mediaType"] = "image/png"
        object.removeValue(forKey: "plaintextSize")
        XCTAssertThrowsError(
            try JSONDecoder().decode(
                BlueCatbirdChatDefs.BlobUploadPreparationBody.self,
                from: JSONSerialization.data(withJSONObject: object)
            )
        )
    }
}
