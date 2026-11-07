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
203 JSON files have aggregate SHA-256
`c63561fc8365361d47c8ed9caba4b353885b737ccd3af8082fec2ca4f7d42c0e`.
The aggregate hashes path-sorted lines of each file's SHA-256, two spaces, its
path relative to `lexicons`, and LF. `Package.swift` pins a published Petrel
revision so package resolution does not depend on the neighboring checkout.

The seed 2323 projection (signed `applicationSendBody.sendCounter`, the
`sendMessage` `StaleSend` error) was regenerated with Petrel
`f381cb844d33d76ad7ca948698480d381712304b` (tree
`6768ba98ed5076fe23141983a1f9b8011dbc7e85`). Petrel's 441-file lexicon
corpus remains aggregate SHA-256
`a5c5fbc0a9c38ceb74192fa0aea31d93f73e381b3ddb02e55d73fef7f94e975d`;
`blue.catbird.chat.defs.json` is SHA-256
`1c21d1b04555c800a0491e997b74e3185520b0634362e68a2a27e6b8babdb075`.
Rerunning the pinned generator and SwiftFormat 0.61.1 reproduced all generated
Swift and Kotlin files byte for byte.

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

The full generation and formatter projection now contains all **409** generated
files (205 Swift and 204 Kotlin). The shared Rust projection lives in
`catbird-atproto`; its maintained generation wrapper handles named-byte codecs
and raw relay output without editing generated declarations. Wire contract tests
and builds establish local serialization and compilation only. They do not
establish membership enforcement, migration, app rendering, or live deployment.

The terminal-deletion contract also declares `SpaceDeleted` for `findMeks` and
`getAttachmentBlob`. A deleted conversation storage generation returns no data;
callers must not rebind or fall back to another placement. The overlay merges
current main `3dc8b613abbab900f650f7e1a4e42884cc42b0e1` while retaining all 48
main-only source changes plus the newer chat security definitions.
