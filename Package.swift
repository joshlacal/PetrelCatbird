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
        // Published Petrel, pinned by revision. A sibling `path:` dependency
        // builds against whichever line the neighbouring checkout is on, which
        // no manifest records and no other machine reproduces.
        .package(
            url: "https://github.com/joshlacal/Petrel.git",
            revision: "64c7574ebfb5e46b260aa1dd808802635ca38851"
        ),
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
