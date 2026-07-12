import Foundation
import PetrelCatbird
import XCTest

final class CommitGroupChangeReceiptTests: XCTestCase {
    private let legacy = #"{"success":true,"newEpoch":7}"#.data(using: .utf8)!
    private let full = #"{"success":true,"newEpoch":8,"receipt":{"convoId":"convo-1","epoch":8,"sequencerTerm":3,"commitHash":{"$bytes":"AQID"},"sequencerDid":"did:web:sequencer.example","issuedAt":1710000000,"signature":{"$bytes":"BAUG"}}}"#.data(using: .utf8)!

    func testLegacyOutputDecodesWithoutReceipt() throws {
        let output = try JSONDecoder().decode(BlueCatbirdMlsChatCommitGroupChange.Output.self, from: legacy)
        XCTAssertNil(output.receipt)
    }

    func testFullReceiptDecodesAndRoundTripsLosslessly() throws {
        let output = try JSONDecoder().decode(BlueCatbirdMlsChatCommitGroupChange.Output.self, from: full)
        let receipt = try XCTUnwrap(output.receipt)
        XCTAssertEqual(receipt.convoId, "convo-1")
        XCTAssertEqual(receipt.epoch, 8)
        XCTAssertEqual(receipt.sequencerTerm, 3)
        XCTAssertEqual(receipt.commitHash.data, Data([1, 2, 3]))
        XCTAssertEqual(receipt.sequencerDid.description, "did:web:sequencer.example")
        XCTAssertEqual(receipt.issuedAt, 1_710_000_000)
        XCTAssertEqual(receipt.signature.data, Data([4, 5, 6]))

        let roundTrip = try JSONDecoder().decode(
            BlueCatbirdMlsChatCommitGroupChange.Output.self,
            from: JSONEncoder().encode(output)
        )
        XCTAssertEqual(roundTrip.receipt?.sequencerTerm, 3)
        XCTAssertEqual(roundTrip.receipt?.commitHash.data, Data([1, 2, 3]))
        XCTAssertEqual(roundTrip.receipt?.signature.data, Data([4, 5, 6]))
    }
}
