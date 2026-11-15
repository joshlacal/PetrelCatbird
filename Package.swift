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
        siblingRoot.appendingPathComponent("wave1-cohesive/Petrel", isDirectory: true),
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
    // Published Petrel revision (complete 64-bit Kotlin Long coverage on top of 2912970c).
    petrelDependency = .package(
        url: "https://github.com/joshlacal/Petrel.git",
        revision: "87acd38094470f0629134dc98d602334d3e0f01f"
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
