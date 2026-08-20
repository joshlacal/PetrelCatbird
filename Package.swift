// swift-tools-version: 6.0
import PackageDescription

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
        .package(path: "../Petrel"),
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
