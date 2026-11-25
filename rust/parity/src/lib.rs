//! Dev-only B2 parity harness comparing `petrel-catbird` + `petrel-api` against
//! Jacquard 0.13 golden outputs (`fixtures/` and `goldens/`).

use std::path::{Path, PathBuf};

use petrel_core::{
    DeclaredXrpcError, DecodeError, FromValue, ToValue, XrpcOutput, xrpc::XrpcErrorBody,
};

#[derive(Debug, Clone, serde::Deserialize)]
pub struct FixtureMeta {
    pub nsid: String,
    pub case: String,
    pub payload_file: String,
    pub kind: String,
    pub schema_ref: String,
    pub jacquard_rust_type: String,
    pub expect: String,
    pub cbor: bool,
    pub source_commit: String,
    pub source_test: String,
    #[serde(default)]
    pub note: Option<String>,
}

#[derive(Debug, Clone, serde::Deserialize)]
pub struct GoldenMeta {
    pub status: String,
    pub expect: String,
    #[serde(default)]
    pub validate_error: Option<String>,
    #[serde(default)]
    pub cbor_hex: Option<String>,
    #[serde(default)]
    pub cbor_roundtrip_equal: Option<bool>,
    #[serde(default)]
    pub raw_hex: Option<String>,
    #[serde(default)]
    pub error: Option<String>,
}

pub fn parity_root() -> PathBuf {
    Path::new(env!("CARGO_MANIFEST_DIR")).to_path_buf()
}

pub fn load_fixture_text(nsid: &str, case: &str) -> String {
    let path = parity_root()
        .join("fixtures")
        .join(nsid)
        .join(format!("{case}.json"));
    std::fs::read_to_string(&path)
        .unwrap_or_else(|e| panic!("failed to read fixture {}: {e}", path.display()))
}

pub fn load_fixture_bytes(nsid: &str, case: &str) -> Vec<u8> {
    let path = parity_root()
        .join("fixtures")
        .join(nsid)
        .join(format!("{case}.bin"));
    std::fs::read(&path)
        .unwrap_or_else(|e| panic!("failed to read raw fixture {}: {e}", path.display()))
}

pub fn load_golden_json(nsid: &str, case: &str) -> serde_json::Value {
    let path = parity_root()
        .join("goldens")
        .join(nsid)
        .join(format!("{case}.json"));
    let text = std::fs::read_to_string(&path)
        .unwrap_or_else(|e| panic!("failed to read golden {}: {e}", path.display()));
    serde_json::from_str(&text)
        .unwrap_or_else(|e| panic!("failed to parse golden {}: {e}", path.display()))
}

pub fn load_golden_cbor(nsid: &str, case: &str) -> Vec<u8> {
    let path = parity_root()
        .join("goldens")
        .join(nsid)
        .join(format!("{case}.cbor"));
    std::fs::read(&path)
        .unwrap_or_else(|e| panic!("failed to read golden cbor {}: {e}", path.display()))
}

pub fn load_golden_bin(nsid: &str, case: &str) -> Vec<u8> {
    let path = parity_root()
        .join("goldens")
        .join(nsid)
        .join(format!("{case}.bin"));
    std::fs::read(&path)
        .unwrap_or_else(|e| panic!("failed to read golden bin {}: {e}", path.display()))
}

pub fn load_golden_meta(nsid: &str, case: &str) -> GoldenMeta {
    let path = parity_root()
        .join("goldens")
        .join(nsid)
        .join(format!("{case}.meta.json"));
    let text = std::fs::read_to_string(&path)
        .unwrap_or_else(|e| panic!("failed to read golden meta {}: {e}", path.display()));
    serde_json::from_str(&text)
        .unwrap_or_else(|e| panic!("failed to parse golden meta {}: {e}", path.display()))
}

/// Normalize `$bytes` strings from padded standard base64 (Jacquard) to
/// unpadded standard base64 (Petrel Decision #6) ONLY when explicitly checking
/// a known `$bytes` padding difference; asserts Petrel emitted unpadded `$bytes`.
pub fn assert_json_eq_allowing_unpadded_bytes(
    petrel_val: &serde_json::Value,
    jacquard_val: &serde_json::Value,
    path: &str,
) {
    match (petrel_val, jacquard_val) {
        (serde_json::Value::Object(pm), serde_json::Value::Object(jm)) => {
            if pm.len() == 1
                && jm.len() == 1
                && pm.contains_key("$bytes")
                && jm.contains_key("$bytes")
            {
                let ps = pm["$bytes"].as_str().expect("petrel $bytes str");
                let js = jm["$bytes"].as_str().expect("jacquard $bytes str");
                assert!(
                    !ps.ends_with('='),
                    "{path}.$bytes: Petrel Decision #6 must emit unpadded base64, got {ps:?}"
                );
                assert_eq!(
                    ps,
                    js.trim_end_matches('='),
                    "{path}.$bytes mismatch vs trimmed Jacquard {js:?}"
                );
                return;
            }
            assert_eq!(
                pm.keys().collect::<Vec<_>>(),
                jm.keys().collect::<Vec<_>>(),
                "{path}: object keys mismatch"
            );
            for (k, pv) in pm {
                assert_json_eq_allowing_unpadded_bytes(pv, &jm[k], &format!("{path}.{k}"));
            }
        }
        (serde_json::Value::Array(pa), serde_json::Value::Array(ja)) => {
            assert_eq!(pa.len(), ja.len(), "{path}: array len mismatch");
            for (i, (pv, jv)) in pa.iter().zip(ja.iter()).enumerate() {
                assert_json_eq_allowing_unpadded_bytes(pv, jv, &format!("{path}[{i}]"));
            }
        }
        _ => assert_eq!(petrel_val, jacquard_val, "{path}: value mismatch"),
    }
}

pub fn roundtrip_petrel<T>(nsid: &str, case: &str, check_cbor: bool)
where
    T: serde::Serialize
        + serde::de::DeserializeOwned
        + ToValue
        + FromValue
        + PartialEq
        + std::fmt::Debug,
{
    let raw = load_fixture_text(nsid, case);
    let golden_json = load_golden_json(nsid, case);
    let meta = load_golden_meta(nsid, case);
    assert_eq!(meta.status, "ok", "{nsid}/{case} golden was not ok");

    // 1. JSON decode + re-encode via petrel_core::from_json / to_json
    let parsed: T = petrel_core::from_json(raw.as_bytes())
        .unwrap_or_else(|e| panic!("{nsid}/{case}: petrel from_json failed: {e}"));
    let reencoded_bytes = petrel_core::to_json(&parsed)
        .unwrap_or_else(|e| panic!("{nsid}/{case}: petrel to_json failed: {e}"));
    let reencoded_val: serde_json::Value = serde_json::from_slice(&reencoded_bytes).unwrap();
    assert_json_eq_allowing_unpadded_bytes(&reencoded_val, &golden_json, &format!("{nsid}/{case}"));

    // Also confirm serde::Serialize / serde::Deserialize bridge parity
    let serde_parsed: T = serde_json::from_str(&raw)
        .unwrap_or_else(|e| panic!("{nsid}/{case}: serde_json::from_str failed: {e}"));
    assert_eq!(
        parsed, serde_parsed,
        "{nsid}/{case}: from_json vs serde_json eq"
    );

    if check_cbor {
        let golden_cbor = load_golden_cbor(nsid, case);
        let cbor = petrel_core::to_dag_cbor(&parsed)
            .unwrap_or_else(|e| panic!("{nsid}/{case}: to_dag_cbor failed: {e}"));
        assert_eq!(
            cbor, golden_cbor,
            "{nsid}/{case}: DAG-CBOR bytes differ from Jacquard golden"
        );
        let decoded: T = petrel_core::from_dag_cbor(&golden_cbor)
            .unwrap_or_else(|e| panic!("{nsid}/{case}: from_dag_cbor(golden) failed: {e}"));
        assert_eq!(parsed, decoded, "{nsid}/{case}: CBOR roundtrip value eq");
    }
}

pub fn assert_rejects_petrel<T>(nsid: &str, case: &str)
where
    T: serde::de::DeserializeOwned + FromValue + std::fmt::Debug,
{
    let raw = load_fixture_text(nsid, case);
    let meta = load_golden_meta(nsid, case);
    assert_eq!(
        meta.status, "deser_err",
        "{nsid}/{case} golden expected deser_err"
    );
    let res: Result<T, DecodeError> = petrel_core::from_json(raw.as_bytes());
    assert!(
        res.is_err(),
        "{nsid}/{case}: expected Petrel decode failure, got {:?}",
        res
    );
}

pub fn assert_error_wire<E>(nsid: &str, case: &str, expected_declared: bool)
where
    E: DeclaredXrpcError + PartialEq + std::fmt::Debug,
{
    let raw = load_fixture_text(nsid, case);
    let golden = load_golden_json(nsid, case);
    let body: XrpcErrorBody = petrel_core::from_json(raw.as_bytes())
        .unwrap_or_else(|e| panic!("{nsid}/{case}: XrpcErrorBody decode failed: {e}"));
    let err = E::from_wire_name(&body.error);
    assert_eq!(
        err.is_declared(),
        expected_declared,
        "{nsid}/{case}: is_declared mismatch for {:?}",
        err
    );
    assert_eq!(err.wire_name(), golden["error"].as_str().unwrap());
    let golden_msg = golden.get("message").and_then(|v| v.as_str());
    assert_eq!(body.message.as_deref(), golden_msg);
}

pub fn assert_raw_output_passthrough<O>(nsid: &str, case: &str) -> O
where
    O: XrpcOutput + std::fmt::Debug,
{
    let raw = load_fixture_bytes(nsid, case);
    let golden = load_golden_bin(nsid, case);
    assert_eq!(
        raw, golden,
        "{nsid}/{case}: fixture/golden raw bytes differ"
    );
    O::from_body(&raw)
        .unwrap_or_else(|e| panic!("{nsid}/{case}: XrpcOutput::from_body failed: {e}"))
}
