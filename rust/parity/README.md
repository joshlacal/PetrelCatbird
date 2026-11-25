# B2 Jacquard 0.13 ↔ Petrel Parity Corpus & Harness

This standalone, dev-only crate holds the B2 parity fixture corpus (`fixtures/`),
canonical Jacquard 0.13 goldens (`goldens/`), and the round-trip diff harness
comparing `petrel-catbird` + `petrel-api` against Jacquard 0.13.

It lives outside `Petrel/` and outside `petrel-catbird/` so no private
`blue.catbird.*` fixture or Jacquard artifact ever leaks into the public
`Petrel` repository or the shipped `petrel-catbird` library crate.

## Provenance

- **Jacquard pin**: `catbird-atproto` `cb307aee7c12c380f970df72e317fbe05b2325f2`
  (Jacquard `0.13.0` rev `3287244a99262a361bd32873a871982da0a76c40`,
  `serde_ipld_dagcbor = 0.6.4`, features `streaming,namespace-atproto-space,namespace-site-standard`).
- **Extracted inline/literal fixtures** (84 cases):
  - `catbird-atproto/tests/account_erasure_completion_contract.rs` (3)
  - `catbird-atproto/tests/blob_upload_contract.rs` (7: 1 valid with integer-array bytes, 2 missing-field rejects, 4 `validate()` rejects)
  - `catbird-atproto/tests/blue_catbird_chat_surface.rs` (6)
  - `catbird-atproto/tests/blue_catbird_circle_surface.rs` (1)
  - `catbird-atproto/tests/com_atproto_space_surface.rs` (27)
  - `catbird-atproto/tests/conversation_space_contract.rs` (13)
  - `catbird-atproto/tests/get_convo_events_contract.rs` (17: clean, legacy, 14 known `entryKind` + 1 `Other`)
  - `catbird-atproto/tests/jacquard_0121_regressions.rs` (1: open-union unknown `$type` passthrough)
  - `catbird-atproto/tests/labyrinth_relay_contract.rs` (1: raw `application/json` wire passthrough)
  - `catbird-atproto/tests/named_bytes_contract.rs` (2: JSON + DAG-CBOR)
  - `catbird-atproto/tests/site_standard_surface.rs` (1)
  - `catbird-atproto/src/lib.rs::bytes_test` (1: `SignedBlobDeletion`)
  - `catbird-atproto/tests/fixtures/push-registration.json#/body` (1: `PushRegistrationBody`)
- **Captured payloads**:
  - Public AppView (`Petrel/Tests/PetrelTests/Vectors/bsky-recorded/{profile,labeler-services}.json`, `28b2624ee`) — stored in `fixtures/` + `goldens/`.
  - Optional local-only private captures — read strictly from `/tmp/petrel-catbird-b2-private/` when present locally, never committed or tracked in `PetrelCatbird`.

## Jacquard 0.13 Golden Results (86 committed public/synthetic cases + 2 local-only)

- `69` `roundtrip -> ok` (JSON value + deterministic DAG-CBOR round-trip)
- `12` `reject -> deser_err` (expected missing-field / legacy-schema rejections)
- `4` `validate_reject -> validate_err` (`BlobUploadPreparationBody` bounds)
- `1` `raw_passthrough -> ok` (`blue.catbird.labyrinth.labyrinthRelay`)
- `2` optional local-only `reject -> deser_err` probes under `/tmp/petrel-catbird-b2-private/` (historical/bare-base64 captures rejected by strict `$bytes` decoders)

## Documented Intentional Wire Differences (Petrel vs Jacquard)

1. **`$bytes` JSON base64 padding (Decision #6)**: Jacquard emits padded `BASE64_STANDARD` (`=`); Petrel emits **unpadded** base64 (reference PDS form) and decodes both. The parity harness asserts exact decoded bytes and exact **DAG-CBOR bytes**, while explicitly checking unpadded vs padded `$bytes` strings on JSON paths rather than normalizing away the difference.
2. **Error enum absent optional `message`**: Jacquard serializes `SubmitTransitionError::SignedOperationExpired(None)` with `"message": null`. Petrel's `DeclaredXrpcError` maps wire error names cleanly via `XrpcErrorBody`.
3. **Integer-array bytes tolerance (`blob_upload_prep_valid_array_bytes`)**: Jacquard's `serde_bytes_helper` accepts `[0, 0, ...]` byte arrays on deserialize and re-encodes as `{"$bytes": "..."}`. Petrel enforces the strict ATProto Data Model (`$bytes` / CBOR bytes) and rejects integer arrays with `DecodeError::TypeMismatch { expected: "bytes", found: "array" }`. Rewriting `ciphertextSha256` to `{"$bytes": ...}` roundtrips JSON + DAG-CBOR byte-identically to Jacquard.
4. **Raw HTTP body pass-through (`labyrinthRelay`)**: `labyrinthRelay` carries schemaless `application/json` bytes verbatim (`petrel_core::Bytes`), with no JSON re-encoding or DAG-CBOR round-trip.
5. **`format: "cid"` in DAG-CBOR (`SpaceBatchView.cid`, `StrongRef.cid` in `recorded_bsky_app`)**: Per ATProto Lexicon spec, `{"type": "string", "format": "cid"}` is a string (`petrel_core::CidString`), so Petrel encodes it as a CBOR text string (major type 3), whereas `{"type": "cid-link"}` (`petrel_core::CidLink`) encodes as CBOR tag 42. Jacquard 0.13 erroneously encodes `format: "cid"` as CBOR tag 42. The parity harness proves every other CBOR field and map key is byte-identical once `.cid` is normalized from `Value::String` to `Value::Link`.
6. **Single-member closed unions (`SignedBlobDeletion.body`)**: Jacquard collapses 1-member closed unions (`"type": "union", "refs": ["#blobDeletionBody"], "closed": true`) into a bare struct with no `$type` field (which is why `catbird-atproto`'s `bytes_test` omits `$type`). Petrel Swift, Kotlin, and Rust all emit a `$type`-tagged union requiring `"$type": "blue.catbird.chat.defs#blobDeletionBody"`. With `$type` present, `SignedBlobDeletion` roundtrips JSON + DAG-CBOR cleanly. (Critical Phase-4 migration note: consumers migrating off Jacquard must ensure 1-member union payloads include `$type`.)
7. **Defaulted non-required object booleans (`com.atproto.memberspace.policy`)**: Jacquard materializes `"default": false` properties as non-optional `bool` fields and always serializes them (`publicWrite`, `publishMembers`, `publishPolicy`). Petrel models non-`required` object fields as `Option<bool>` (omitting `None` on serialize). Setting those fields to `Some(false)` produces byte-identical JSON and DAG-CBOR.
8. **Space URIs in the `at:space/` form (November 24 Spaces alpha)**: Jacquard 0.13 predates `at:space/{did}/{type}/{key}[/{author}/{collection}/{rkey}]` and writes space URIs as `at://{did}/space/…`. Petrel parses `space-ref` strictly in the new form, as `SpaceRef.parse` upstream does, and decodes a legacy `at-uri` without rewriting it. Every space URI in `fixtures/` and `goldens/` was rewritten mechanically to the `at:space/` form, JSON and DAG-CBOR alike: the CBOR rewrite replaces only the text-string items, so map order and every other byte are still Jacquard's.


