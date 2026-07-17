import Foundation
import Petrel
import PetrelCatbird
import XCTest

final class NamespaceOwnershipTests: XCTestCase {
    func testManifestDeclaresBlueAsCoreOwnedNamespaceRoot() throws {
        let repositoryRoot = URL(fileURLWithPath: #filePath)
            .deletingLastPathComponent()
            .deletingLastPathComponent()
            .deletingLastPathComponent()
        let manifestURL = repositoryRoot
            .appendingPathComponent("manifests/petrel-catbird.json")
        let data = try Data(contentsOf: manifestURL)
        let manifest = try XCTUnwrap(
            JSONSerialization.jsonObject(with: data) as? [String: Any]
        )
        let package = try XCTUnwrap(manifest["package"] as? [String: Any])
        let coreNamespaceRoots = try XCTUnwrap(
            package["core_namespace_roots"] as? [String]
        )

        XCTAssertEqual(coreNamespaceRoots, ["blue"])
    }

    func testOverlayExtendsCoreBlueInsteadOfRedeclaringIt() {
        let _: ATProtoClient.Blue.Catbird.Type = ATProtoClient.Blue.Catbird.self
    }
}
