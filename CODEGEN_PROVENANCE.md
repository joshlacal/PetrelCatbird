# Private contract generation

This package extends Petrel with Catbird's private API contracts. The October 23
reconciliation preserves the newer chat security and recovery definitions while
adding immutable conversation Space placement, authenticated history references,
current membership lookup, and permanent generation deletion. Schemas are the
source of truth; generated Swift and Kotlin are never patched by hand.

The corresponding Petrel core uses the exact October 29 upstream Spaces alpha
`5d88b414a98de1ada22c94c78fa0667a7028163c` (published as
`0.0.0-spaces-alpha-20261029171913`). Petrel verifies all 39
`com.atproto.space`, `memberspace`, `managedspace` and `personalspace` schema
bytes against its recorded upstream archive. No overlay schema references a
core definition that release changed, so regenerating this overlay against it
reproduced the generated Swift and Kotlin byte for byte. This overlay's
189 JSON files have aggregate SHA-256
`a627cf74bcf876c954f79cbc4b3cab6618a1eed2401385655310575bf63e853f`.
The aggregate hashes sorted lines of each file's SHA-256, two spaces, its path
relative to `lexicons`, and LF. `Package.swift` pins a published Petrel revision
so package resolution does not depend on the neighboring checkout.

## Reproduce

Checkpoint the affected repositories with jj before generation. Use Petrel's
hash-locked Python 3.12 generator environment and SwiftFormat **0.61.1**:

```sh
cd ../Petrel
.build/release-generator-venv/bin/python run.py --manifest ../PetrelCatbird/manifests/petrel-catbird.json
cd ../PetrelCatbird
swiftformat Sources/PetrelCatbird/Generated
swift test --build-system native --scratch-path .build/native-spaces --no-parallel
cd kotlin
./gradlew test --no-daemon -Pkotlin.incremental=false -Pkotlin.compiler.execution.strategy=in-process '-Dorg.gradle.jvmargs=-Xmx3g -XX:MaxMetaspaceSize=1g'
```

The checked-in `.swiftformat` selects indentation, linebreak, blank-line and
trailing-space hygiene for generated code. It preserves explicit generated
implementations. Broader simplification/argument-rewriting rules in this pinned
formatter take minutes on the complete private chat definition; the bounded
pass finishes in seconds and is part of the reproducible projection.

The full generation and formatter roundtrip reproduced all **381** generated
files with identical inventory and bytes. The shared Rust projection lives in
`catbird-atproto`; its maintained generation wrapper handles named-byte codecs
and raw relay output without editing generated declarations. Wire contract tests
and builds establish local serialization and compilation only. They do not
establish membership enforcement, migration, app rendering, or live deployment.

The terminal-deletion contract also declares `SpaceDeleted` for `findMeks` and
`getAttachmentBlob`. A deleted conversation storage generation returns no data;
callers must not rebind or fall back to another placement. The overlay merges
current main `3dc8b613abbab900f650f7e1a4e42884cc42b0e1` while retaining all 48
main-only source changes plus the newer chat security definitions.
