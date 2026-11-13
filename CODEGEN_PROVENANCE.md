# Private contract generation

This package extends Petrel with Catbird's private API contracts. The October 23
reconciliation preserves the newer chat security and recovery definitions while
adding immutable conversation Space placement, authenticated history references,
current membership lookup, and permanent generation deletion. Schemas are the
source of truth; generated Swift and Kotlin are never patched by hand.

The corresponding Petrel core at head
`fb3281346e5a26db68c638b16af7ed6656fe615a` (PR
[`#73`](https://github.com/joshlacal/Petrel/pull/73)) uses the exact November 12
upstream Spaces alpha `d4def6e9f521896ed8222f576d54923d00b5fbeb`
(published as `0.0.0-spaces-alpha-20261112204144`). Its published generator
revision `7d47f90684a73b9b420378f4925a686a6cb4cd17` vendors all 39
`com.atproto.space`, `memberspace`, `managedspace` and `personalspace` schemas
from that release. The upstream archive has SHA-256
`fd0b719f54227f93406770dd6e985147869853cb2a97320b6f072d999fb663d4`.
No overlay schema references a core definition, and all overlay references
resolve against that corpus. This overlay's 189 JSON files have aggregate
SHA-256 `3297a00b8a1ce0194a0e17f5375e1f4519d889079c79c1cc01712e37c90565fd`.
The 191 generated Swift files have aggregate SHA-256
`3853e92ea38fe56fb7587e180086f777c03a361afefb820a9ce0b14c93da1916`;
the 190 generated Kotlin files have
`b9945318ae4be196f04298905d51ba695889c26d3b85a9b952447bdc270caf4b`.
Each aggregate hashes sorted lines containing a file's SHA-256, two spaces,
its path relative to the corresponding root, and LF. `Package.swift` pins
Petrel revision `fb3281346e5a26db68c638b16af7ed6656fe615a` from PR #73,
so the generated projection and Swift tests use the same Petrel 2.0-line
runtime APIs.

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
