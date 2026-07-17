import Foundation
import Petrel
import PetrelCatbird
import XCTest

final class TransitionAttestationContractTests: XCTestCase {
    private let wireActions = [
        "addMembers",
        "removeMembers",
        "swapMembers",
        "commit",
        "updateMetadata",
    ]

    private let receipt = #"{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"$bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"$bytes":"BAUG"}}"#

    func testTransitionActionIsClosedAndRoundTripsExactWireValues() throws {
        let decoder = JSONDecoder()
        let encoder = JSONEncoder()

        for wireValue in wireActions {
            let data = Data(#""\#(wireValue)""#.utf8)
            let action = try decoder.decode(
                BlueCatbirdMlsChatBeginTransitionAttestation.InputAction.self,
                from: data
            )
            XCTAssertEqual(action.rawValue, wireValue)
            XCTAssertEqual(try action.toCBORValue() as? String, wireValue)
            XCTAssertEqual(try encoder.encode(action), data)
        }

        XCTAssertThrowsError(
            try decoder.decode(
                BlueCatbirdMlsChatBeginTransitionAttestation.InputAction.self,
                from: Data(#""unknown""#.utf8)
            )
        )
    }

    func testFinalizeRequiresACompleteReceipt() {
        let fixtures = [
            #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"}}"#,
            #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"},"receipt":{"convoId":"convo-1","epoch":8,"commitHash":{"$bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"$bytes":"BAUG"}}}"#,
        ]

        for fixture in fixtures {
            XCTAssertThrowsError(
                try JSONDecoder().decode(
                    BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
                    from: Data(fixture.utf8)
                )
            )
        }
    }

    func testStrictReceiptReferencesRejectUnknownNestedKeys() {
        let receiptWithUnknownKey = receipt.dropLast() + #", "unexpected":true}"#
        let finalize = #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"},"receipt":\#(receiptWithUnknownKey)}"#
        let commit = #"{"success":true,"newEpoch":8,"receipt":\#(receiptWithUnknownKey)}"#

        XCTAssertThrowsError(
            try JSONDecoder().decode(
                BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
                from: Data(finalize.utf8)
            )
        )
        XCTAssertThrowsError(
            try JSONDecoder().decode(
                BlueCatbirdMlsChatCommitGroupChange.Output.self,
                from: Data(commit.utf8)
            )
        )
    }

    func testStrictReceiptReferenceValidatesPresentDiscriminator() throws {
        let canonicalType = "blue.catbird.mlsChat.commitGroupChange#sequencerReceipt"
        let typedReceipt = receipt.dropLast() + #", "$type":"\#(canonicalType)"}"#
        let valid = #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"},"receipt":\#(typedReceipt)}"#
        XCTAssertNoThrow(
            try JSONDecoder().decode(
                BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
                from: Data(valid.utf8)
            )
        )

        let wrongReceipt = receipt.dropLast() + #", "$type":"blue.catbird.mlsChat.commitGroupChange#other"}"#
        let wrong = #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"},"receipt":\#(wrongReceipt)}"#
        XCTAssertThrowsError(
            try JSONDecoder().decode(
                BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
                from: Data(wrong.utf8)
            )
        )
    }

    func testFinalizeReceiptRoundTripsLosslessly() throws {
        let inputJSON = #"{"convoId":"convo-1","challengeId":"00000000-0000-4000-8000-000000000000","targetGroupInfo":{"$bytes":"AQID"},"receipt":\#(receipt)}"#
        let input = try JSONDecoder().decode(
            BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
            from: Data(inputJSON.utf8)
        )
        let roundTrip = try JSONDecoder().decode(
            BlueCatbirdMlsChatFinalizeGroupChange.Input.self,
            from: JSONEncoder().encode(input)
        )

        XCTAssertEqual(roundTrip.receipt.convoId, "convo-1")
        XCTAssertEqual(roundTrip.receipt.epoch, 8)
        XCTAssertEqual(roundTrip.receipt.sequencerTerm, 3)
        XCTAssertEqual(roundTrip.receipt.commitHash.data, Data([1, 2, 3]))
        XCTAssertEqual(roundTrip.receipt.signature.data, Data([4, 5, 6]))
    }

    func testExistingDeviceAuthPublicTypesRemainAvailable() {
        let begin = BlueCatbirdMlsChatBeginDeviceAuthBinding.Input(deviceId: "device-1")
        XCTAssertEqual(begin.deviceId, "device-1")
        XCTAssertEqual(
            BlueCatbirdMlsChatBeginDeviceAuthBinding.Error.dpopRequired.errorName,
            "DpopRequired"
        )

        let complete = BlueCatbirdMlsChatCompleteDeviceAuthBinding.Output(
            deviceId: "device-1",
            boundAt: ATProtocolDate(date: Date(timeIntervalSince1970: 1_710_000_000)),
            bindingVersion: 1
        )
        XCTAssertEqual(complete.deviceId, "device-1")
        XCTAssertEqual(complete.bindingVersion, 1)
        XCTAssertEqual(
            BlueCatbirdMlsChatCompleteDeviceAuthBinding.Error.bindingUnavailable.errorName,
            "BindingUnavailable"
        )
    }
}
