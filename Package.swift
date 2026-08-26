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
            revision: "6998893de74175f7ad6b9616e8215d5d14d5fd10"
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
