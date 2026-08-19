import Foundation
import Petrel
import PetrelCatbird
import XCTest

final class TransitionAttestationContractTests: XCTestCase {
    func testSignedTransitionUnionVariantsEncodeAndDecode() throws {
        let decoder = JSONDecoder()
        let encoder = JSONEncoder()

        let bytes = Bytes(data: Data(repeating: 1, count: 32))
        let coords = BlueCatbirdChatDefs.ConversationCoordinates(
            conversationId: "00000000-0000-4000-8000-000000000001",
            generation: 1,
            stateVersion: 1,
            groupId: bytes,
            epoch: 1,
            groupContextHash: bytes,
            confirmationTag: bytes,
            lifecycle: .value_active
        )
        let encoded = try encoder.encode(coords)
        let decoded = try decoder.decode(BlueCatbirdChatDefs.ConversationCoordinates.self, from: encoded)
        XCTAssertEqual(decoded.conversationId, coords.conversationId)
        XCTAssertEqual(decoded.epoch, coords.epoch)
        XCTAssertEqual(decoded.lifecycle, .value_active)
    }

    func testSubmitTransitionOutputDecodesAndRoundTrips() throws {
        let json = """
        {
            "coordinates": {
                "conversationId": "00000000-0000-4000-8000-000000000001",
                "generation": 1,
                "stateVersion": 1,
                "groupId": {"$bytes": "AQID"},
                "epoch": 2,
                "groupContextHash": {"$bytes": "BAUG"},
                "confirmationTag": {"$bytes": "BwgJ"},
                "lifecycle": "active"
            },
            "entry": {
                "$type": "blue.catbird.chat.defs#customEntry",
                "seq": 2
            },
            "welcomes": []
        }
        """.data(using: .utf8)!

        let output = try JSONDecoder().decode(BlueCatbirdChatSubmitTransition.Output.self, from: json)
        XCTAssertEqual(output.coordinates.epoch, 2)
        XCTAssertEqual(output.coordinates.conversationId, "00000000-0000-4000-8000-000000000001")
        XCTAssertEqual(output.welcomes.count, 0)

        let reencoded = try JSONEncoder().encode(output)
        let roundTrip = try JSONDecoder().decode(BlueCatbirdChatSubmitTransition.Output.self, from: reencoded)
        XCTAssertEqual(roundTrip.coordinates.conversationId, output.coordinates.conversationId)
        XCTAssertEqual(roundTrip.coordinates.epoch, output.coordinates.epoch)
    }

    func testDeviceEnrollmentAndRebindErrorsRemainAvailable() {
        XCTAssertEqual(
            BlueCatbirdChatEnrollDevice.Error.authenticationGenerationConflict.errorName,
            "AuthenticationGenerationConflict"
        )
        XCTAssertEqual(
            BlueCatbirdChatEnrollDevice.Error.cutoverRequired.errorName,
            "CutoverRequired"
        )
        XCTAssertEqual(
            BlueCatbirdChatEnrollDevice.Error.invalidSignature.errorName,
            "InvalidSignature"
        )
        XCTAssertEqual(
            BlueCatbirdChatRebindDeviceAuthentication.Error.deviceNotRegistered.errorName,
            "DeviceNotRegistered"
        )
        XCTAssertEqual(
            BlueCatbirdChatRebindDeviceAuthentication.Error.invalidDPoP.errorName,
            "InvalidDPoP"
        )
    }
}
