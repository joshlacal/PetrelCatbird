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
`838a01b33cc249e42a19ecc028fe5a5d34abab56789d497bf95df7a4c73905bd`.
The aggregate hashes path-sorted lines of each file's SHA-256, two spaces, its
path relative to `lexicons`, and LF. `Package.swift` pins a published Petrel
revision so package resolution does not depend on the neighboring checkout.

## Wave 1 Spaces Nov-12 + Petrel 2.0 + Encrypted Circles convergence (2026-11-13)

This projection pins `Package.swift` to published `Petrel` `2912970ccda2579da71c3f7673a8da733b1ac83b`
(Petrel 2.0 + November 12, 2026 ATProto Spaces `d4def6e9` / `471`-file core lexicon corpus,
aggregate SHA-256 `8bf4f0e4ada9249cfbdcd2175d334af8da3b0f047b90d5f3073c7e2b086246d7`),
merges the encrypted-Circle schemas (`blue.catbird.circle.encrypted`, `blue.catbird.circle.key`,
`blue.catbird.circle.post`, `blue.catbird.circle.getKeys`, and encrypted feed/capability fields),
renames `name` to `title` on all four Space declarations (`blue.catbird.circle`,
`blue.catbird.circle.encrypted`, `blue.catbird.labyrinth.mailbox`, and
`blue.catbird.labyrinth.conversation`), adds `blue.catbird.labyrinth.authorRedaction` and
`blue.catbird.labyrinth.defs#originalSenderArchiveAttestation`, reconciles
`blue.catbird.chat.authFull` (`81e4bb8fbe28c3325488a376a4642253156493f94a086a5e832ba84c43a641e3`)
to the exact 35 live `blue.catbird.chat.*` RPC endpoints plus create-only repository permissions on
`blue.catbird.chat.device` and `blue.catbird.labyrinth.mailboxKeys`, declares `SignedOperationExpired`
on `blue.catbird.chat.createConversation` (`57b9564be2fc2039481a9cf68d4e680c3ad6f7ca014e289be2f58e4de16b085a`),
and adds optional November 12
`authorizeSpaceSync` / `listWriters` / RFC 9421 `acceptSignature` parameters to
`blue.catbird.chat.relaySpaceCredential` and `blue.catbird.chat.relaySpaceRead`.

The canonical `blue.catbird.chat.defs.json` remains SHA-256
`b0fe4af17f657647a8e1ceedb7a34765a0c72d514420ce3b153d6ecd11d240ff`.
The 208-file overlay aggregate is
`9a92f47aad03ec507338619f3bfcef2ccf827fbd3cf76facd66b0fab2be88a60`.
The Kotlin generator applies `887b522a18949dda322ef9c3f0119ab52b95c2eb`
(`generator/kotlin_type_converter.py` SHA-256
`574296a73ede89d3fc3dc35db015101649f730361aeeb13da05386181119ec4c`) so all
64-bit (`maximum > 2147483647`), `*Ms`, and `authGeneration` fields emit Kotlin `Long`.
The generated projections (formatted with SwiftFormat **0.61.1**) contain
210 Swift files (aggregate `1a684ad826739ed061c2fc889e9b14293d02648ecbb279b15a65a0e584364222`),
209 Kotlin files (aggregate `787f97b6897bc238478f4fa70ff2f8916f00c870dbadd7a54fe2666602fa9c15`),
and 707 Rust files in `catbird-atproto` (aggregate
`783f72db31c17a9883c68b9f334aa8aa4392f098abe5a790f7113dccfb3d4392`).

## Task 5 receipt projection (2026-11-11)

This projection removes the unsupported optional `sequencerReceipt` from
`blue.catbird.chat.defs#accountDeletedEntry`, whose authenticated evidence remains
the required `sequencerAuthority`, and adds the separately stored receipt bytes to
`blue.catbird.mlsDS.getConvoEvents#convoEventEntry` for lossless reconciliation.
The checked-in Kotlin output remains reproducible by applying Petrel generator change
`887b522a18949dda322ef9c3f0119ab52b95c2eb` ("map 64-bit and millisecond
timestamp integers to Kotlin Long") as file changes to the canonical Petrel
working copy based on `f381cb844d33d76ad7ca948698480d381712304b`. The resulting
`generator/kotlin_type_converter.py` is SHA-256
`574296a73ede89d3fc3dc35db015101649f730361aeeb13da05386181119ec4c`;
no other Petrel generator file changed.

The canonical `blue.catbird.chat.defs.json` is SHA-256
`b0fe4af17f657647a8e1ceedb7a34765a0c72d514420ce3b153d6ecd11d240ff`.
The 203-file overlay aggregate is
`57a924bfa5384e5a6d1440b87a8d61d5ebf501de5f01d7f195db5f1b6bd68e58`.
The generated projections contain 205 Swift files (aggregate
`df5a77cfc45e102b8a0a20f5e1c22dafc8641d9660a5bd28b3a9217dbfcbec9d`),
204 Kotlin files (aggregate
`a619737463e32a67323d7dcccce8374363612d733ea5f5a26d3b2d4b34f7ab03`),
and 671 Rust files (aggregate
`7951c1e02876f4f0ddb4e5309ef1262812d1fc2547be411e416299b0e08b5fca`).
The Kotlin projection retains `Long` for every integer wider than 32 bits,
millisecond field, and `authGeneration`.

The R4 leaf-recovery projection (optional `leafRecoveryView.signedRequest`, the
requester device's exact signed `requestLeafRecovery`) was regenerated with
Petrel `f381cb844d33d76ad7ca948698480d381712304b` (tree
`6768ba98ed5076fe23141983a1f9b8011dbc7e85`). Petrel's 441-file lexicon
corpus remains aggregate SHA-256
`a5c5fbc0a9c38ceb74192fa0aea31d93f73e381b3ddb02e55d73fef7f94e975d`;
`blue.catbird.chat.defs.json` is SHA-256
`cf931d0fda7b68082f7f0ac9105dd62122692ee35afbf0ddf7eb2d134340f848`.
The pinned generator and SwiftFormat 0.61.1 changed only
`BlueCatbirdChatDefs.swift` and `BlueCatbirdChatDefs.kt` (the `signedRequest`
field); every other generated Swift and Kotlin file is byte for byte unchanged.
The previous seed 2323 projection (signed `applicationSendBody.sendCounter`)
had `blue.catbird.chat.defs.json` SHA-256
`1c21d1b04555c800a0491e997b74e3185520b0634362e68a2a27e6b8babdb075`.

## Reproduce

Checkpoint the affected repositories with jj before generation. Use Petrel's
hash-locked Python 3.12 generator environment and SwiftFormat **0.61.1**:

```sh
cd ../Petrel
.build/release-generator-venv/bin/python run.py --manifest ../PetrelCatbird/manifests/petrel-catbird.json
cd ../PetrelCatbird
MINT_PATH=../Petrel/.build/release-tools/mint-packages \
MINT_LINK_PATH=../Petrel/.build/release-tools/mint-links \
  ../Petrel/.build/release-tools/mint run --executable swiftformat \
  nicklockwood/SwiftFormat@0.61.1 Sources/PetrelCatbird/Generated
swift test --build-system native --scratch-path .build/native-spaces --no-parallel
cd kotlin
./gradlew test --no-daemon -Pkotlin.incremental=false -Pkotlin.compiler.execution.strategy=in-process '-Dorg.gradle.jvmargs=-Xmx3g -XX:MaxMetaspaceSize=1g'
cd ../../mls-ds
cargo build --locked --manifest-path Cargo.toml -p mls-codegen --bin gen-lexicons
cd ../catbird-atproto
python3 Scripts/regenerate.py generate --generator ../mls-ds/target/debug/gen-lexicons
python3 Scripts/regenerate.py check --generator ../mls-ds/target/debug/gen-lexicons
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
