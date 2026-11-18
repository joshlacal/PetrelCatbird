// swift-tools-version: 6.0
import Foundation
import PackageDescription

let environment = ProcessInfo.processInfo.environment
let packageRoot = URL(fileURLWithPath: #filePath).deletingLastPathComponent()
let siblingRoot = packageRoot.deletingLastPathComponent()

func localPetrelPathIfAvailable() -> String? {
    guard environment["PETREL_LOCAL_INTEGRATION"] != "0" else {
        return nil
    }
    let candidates = [
        siblingRoot.appendingPathComponent("Petrel", isDirectory: true),
    ]
    for raw in candidates {
        let candidate = raw.standardizedFileURL
        let manifest = candidate.appendingPathComponent("Package.swift")
        var isDirectory: ObjCBool = false
        if FileManager.default.fileExists(atPath: candidate.path, isDirectory: &isDirectory),
           isDirectory.boolValue,
           FileManager.default.fileExists(atPath: manifest.path)
        {
            return candidate.path
        }
    }
    return nil
}

let petrelDependency: Package.Dependency
if let localPetrel = localPetrelPathIfAvailable() {
    petrelDependency = .package(path: localPetrel)
} else {
    // Match the Petrel core used to qualify this overlay in the candidate workspace.
    petrelDependency = .package(
        url: "https://github.com/joshlacal/Petrel.git",
        revision: "74b89dfb58fca288ee2003177fe1b287dbaf7ee8"
    )
}

let package = Package(
    name: "PetrelCatbird",
    platforms: [
        .iOS(.v18),
        .macOS(.v15),
    ],
    products: [
        .library(name: "PetrelCatbird", targets: ["PetrelCatbird"]),
    ],
    dependencies: [
        petrelDependency,
    ],
    targets: [
        .target(
            name: "PetrelCatbird",
            dependencies: [
                .product(name: "Petrel", package: "Petrel"),
            ],
            path: "Sources/PetrelCatbird"
        ),
        .testTarget(
            name: "PetrelCatbirdTests",
            dependencies: ["PetrelCatbird"]
        ),
    ]
)
