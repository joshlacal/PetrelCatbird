//! B2 Jacquard 0.13 <-> Petrel parity diff harness.
//!
//! Exercises all 88 extracted & captured fixture cases against `petrel-catbird`
//! and `petrel-api` and compares against the Jacquard 0.13 golden outputs.

use petrel_api::generated::{
    app::bsky::{
        actor::defs::ProfileViewDetailed, labeler::get_services::Output as GetServicesOutput,
    },
    com::atproto::{
        memberspace::{
            put_members::Input as PutMembersInput, remove_members::Input as RemoveMembersInput,
        },
        space::{
            authorize_space_sync::{
                Error as AuthorizeSpaceSyncError, Input as AuthorizeSpaceSyncInput,
            },
            confirm_registration::Input as ConfirmRegistrationInput,
            create_space::Input as CreateSpaceInput,
            defs::Commit,
            get_space::Output as GetSpaceOutput,
            list_spaces::Parameters as ListSpacesParams,
            list_writers::{Output as ListWritersOutput, Parameters as ListWritersParams},
            notify_repo_update::{Error as NotifyRepoUpdateError, Input as NotifyRepoUpdateInput},
            notify_space_update::Input as NotifySpaceUpdateInput,
        },
    },
    site::standard::{document::Document, theme::color::Rgb},
};
use petrel_catbird::generated::blue::catbird::{
    chat::{
        create_conversation::Input as CreateConversationInput,
        declaration::Declaration,
        defs::{
            ApplicationAttachmentBinding, BlobUploadPreparationBody, DirectRequestWelcomeBundle,
            ParticipantView, PushRegistrationBody, SignedBlobDeletion,
        },
        get_conversation_state::Output as GetConversationStateOutput,
        submit_transition::Error as SubmitTransitionError,
    },
    circle::defs::CircleSummary,
    labyrinth::{
        defs::SpaceBatchView,
        delete_account_data::Output as DeleteAccountDataOutput,
        delete_space_generation::{
            Input as DeleteSpaceGenerationInput, Output as DeleteSpaceGenerationOutput,
        },
        find_meks::Error as FindMeksError,
        get_attachment_blob::Error as GetAttachmentBlobError,
        get_space_membership::Output as GetSpaceMembershipOutput,
        get_thread_storage::Output as GetThreadStorageOutput,
        list_space_batches::Parameters as ListSpaceBatchesParams,
    },
    mls_ds::get_convo_events::ConvoEventEntry,
};
use petrel_catbird_parity::{
    assert_error_wire, assert_json_eq_allowing_unpadded_bytes, assert_raw_output_passthrough,
    assert_rejects_petrel, load_fixture_text, load_golden_cbor, load_golden_json, load_golden_meta,
    roundtrip_petrel,
};
use petrel_core::{ToQueryParams, Value};

#[test]
fn parity_account_erasure_completion() {
    for case in ["output_empty", "output_queued", "output_confirmed"] {
        roundtrip_petrel::<DeleteAccountDataOutput>(
            "blue.catbird.labyrinth.deleteAccountData",
            case,
            true,
        );
    }
}

#[test]
fn parity_blob_upload_contract() {
    // 1. Array-encoded bytes in `valid_body` (`[0u8; 32]`): Jacquard accepts bare integer
    // arrays via serde_bytes_helper and re-encodes as `{"$bytes": ...}`. Petrel enforces the
    // strict ATProto Data Model (`$bytes` or CBOR bytes), so array bytes fail with TypeMismatch.
    let raw = load_fixture_text(
        "blue.catbird.chat.defs",
        "blob_upload_prep_valid_array_bytes",
    );
    let err = petrel_core::from_json::<BlobUploadPreparationBody>(raw.as_bytes()).unwrap_err();
    let msg = format!("{err}");
    assert!(
        msg.contains("ciphertextSha256") && msg.contains("expected bytes, found array"),
        "expected strict bytes TypeMismatch for array bytes, got {err:?}"
    );

    // Canonical `$bytes` rewrite of the exact same payload must round-trip JSON + DAG-CBOR
    // identically to the Jacquard golden.
    let mut val: serde_json::Value = serde_json::from_str(&raw).unwrap();
    val["ciphertextSha256"] = serde_json::json!({
        "$bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
    });
    let canonical_raw = serde_json::to_string(&val).unwrap();
    let parsed: BlobUploadPreparationBody =
        petrel_core::from_json(canonical_raw.as_bytes()).expect("canonical $bytes decodes");
    let re_json =
        serde_json::from_slice::<serde_json::Value>(&petrel_core::to_json(&parsed).unwrap())
            .unwrap();
    let golden_json = load_golden_json(
        "blue.catbird.chat.defs",
        "blob_upload_prep_valid_array_bytes",
    );
    assert_json_eq_allowing_unpadded_bytes(
        &re_json,
        &golden_json,
        "blob_upload_prep_valid_canonical",
    );
    let golden_cbor = load_golden_cbor(
        "blue.catbird.chat.defs",
        "blob_upload_prep_valid_array_bytes",
    );
    assert_eq!(petrel_core::to_dag_cbor(&parsed).unwrap(), golden_cbor);

    // 2. Missing required fields reject in both Jacquard and Petrel.
    for case in [
        "blob_upload_prep_missing_mediaType",
        "blob_upload_prep_missing_plaintextSize",
    ] {
        assert_rejects_petrel::<BlobUploadPreparationBody>("blue.catbird.chat.defs", case);
    }

    // 3. Bounds-check fixtures (`validate()`): Petrel drops LexiconSchema runtime AST/validate()
    // per plan §SKIP; decode succeeds leniently just like Jacquard's `from_slice`, and the
    // `$bytes`-rewritten variant decodes identically.
    for case in [
        "blob_upload_prep_validate_fail_empty_media_type",
        "blob_upload_prep_validate_fail_overlong_media_type",
        "blob_upload_prep_validate_fail_zero_plaintext_size",
        "blob_upload_prep_validate_fail_overmax_plaintext_size",
    ] {
        let meta = load_golden_meta("blue.catbird.chat.defs", case);
        assert_eq!(meta.status, "validate_err");
        let case_raw = load_fixture_text("blue.catbird.chat.defs", case);
        let mut v: serde_json::Value = serde_json::from_str(&case_raw).unwrap();
        v["ciphertextSha256"] = serde_json::json!({
            "$bytes": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
        });
        let s = serde_json::to_string(&v).unwrap();
        let _: BlobUploadPreparationBody = petrel_core::from_json(s.as_bytes())
            .expect("validate_fail fixture decodes at wire layer");
    }
}

#[test]
fn parity_blue_catbird_chat_surface() {
    assert_error_wire::<SubmitTransitionError>(
        "blue.catbird.chat.submitTransition",
        "error_expired_with_msg",
        true,
    );
    assert_error_wire::<SubmitTransitionError>(
        "blue.catbird.chat.submitTransition",
        "error_expired_no_msg",
        true,
    );
    assert_error_wire::<SubmitTransitionError>(
        "blue.catbird.chat.submitTransition",
        "error_expired_null_msg",
        true,
    );

    roundtrip_petrel::<ParticipantView>(
        "blue.catbird.chat.defs",
        "participant_view_with_labyrinth_head",
        true,
    );
    roundtrip_petrel::<ParticipantView>(
        "blue.catbird.chat.defs",
        "participant_view_without_labyrinth_head",
        true,
    );

    // Inline knownValues `protocolVersion` stays `String` per Plan §Lane B.
    let pv_raw = load_fixture_text("blue.catbird.chat.declaration", "protocol_version_2");
    let pv: String = serde_json::from_str(&pv_raw).unwrap();
    assert_eq!(pv, "2");
    let decl = Declaration::new(
        "all".into(),
        "did:web:chat.catbird.blue".parse().unwrap(),
        pv,
        "2026-11-24T00:00:00Z".parse().unwrap(),
    );
    assert_eq!(decl.protocol_version, "2");
}

#[test]
fn parity_circle_surface() {
    roundtrip_petrel::<CircleSummary>("blue.catbird.circle.defs", "circle_summary_space_uri", true);
}

#[test]
fn parity_com_atproto_space_surface() {
    roundtrip_petrel::<Commit>("com.atproto.space.defs", "commit_oct22_valid", true);
    for missing in ["ver", "repo", "space", "rev", "data"] {
        assert_rejects_petrel::<Commit>(
            "com.atproto.space.defs",
            &format!("commit_missing_{missing}"),
        );
    }

    roundtrip_petrel::<AuthorizeSpaceSyncInput>(
        "com.atproto.space.authorizeSpaceSync",
        "input_minimal",
        true,
    );
    roundtrip_petrel::<AuthorizeSpaceSyncInput>(
        "com.atproto.space.authorizeSpaceSync",
        "input_registered",
        true,
    );
    for ename in [
        "SpaceNotFound",
        "AccountNotAuthorized",
        "AppNotAuthorized",
        "NotAuthorized",
        "RegistrationNotConfirmed",
    ] {
        assert_error_wire::<AuthorizeSpaceSyncError>(
            "com.atproto.space.authorizeSpaceSync",
            &format!("error_{ename}"),
            true,
        );
    }

    roundtrip_petrel::<NotifyRepoUpdateInput>(
        "com.atproto.space.notifyRepoUpdate",
        "input_valid",
        true,
    );
    assert_error_wire::<NotifyRepoUpdateError>(
        "com.atproto.space.notifyRepoUpdate",
        "error_future_rev",
        true,
    );
    assert_error_wire::<NotifyRepoUpdateError>(
        "com.atproto.space.notifyRepoUpdate",
        "error_space_not_found",
        true,
    );

    roundtrip_petrel::<ListWritersOutput>("com.atproto.space.listWriters", "output_mixed", true);
    roundtrip_petrel::<ListWritersOutput>("com.atproto.space.listWriters", "output_empty", true);

    // Query Params use ToQueryParams rather than JSON Deserialize.
    let mut lw_params = ListWritersParams::new(
        "at://did:plc:space/space/app.bsky.group/demo"
            .parse()
            .unwrap(),
    );
    lw_params.cursor = Some("not-a-tid".into());
    let mut pairs = Vec::new();
    lw_params.to_query_params(&mut pairs);
    assert!(pairs.contains(&("cursor", "not-a-tid".to_owned())));

    roundtrip_petrel::<PutMembersInput>("com.atproto.memberspace.putMembers", "input_valid", true);
    roundtrip_petrel::<RemoveMembersInput>(
        "com.atproto.memberspace.removeMembers",
        "input_valid",
        true,
    );
    roundtrip_petrel::<ConfirmRegistrationInput>(
        "com.atproto.space.confirmRegistration",
        "input_valid",
        true,
    );
    roundtrip_petrel::<NotifySpaceUpdateInput>(
        "com.atproto.space.notifySpaceUpdate",
        "input_valid",
        true,
    );

    let mut ls_params = ListSpacesParams::new();
    ls_params.space_type = Some("app.bsky.group".parse().unwrap());
    let mut ls_pairs = Vec::new();
    ls_params.to_query_params(&mut ls_pairs);
    assert_eq!(
        ls_pairs,
        [
            ("spaceType", "app.bsky.group".to_owned()),
            ("limit", "50".to_owned())
        ]
    );

    roundtrip_petrel::<CreateSpaceInput>(
        "com.atproto.space.createSpace",
        "input_personalspace",
        true,
    );
    // `memberspace.policy` and `managedspace.policy` declare `"default": false` on optional
    // booleans (`publicWrite`, `publishMembers`, `publishPolicy`). Jacquard serializes non-required
    // defaults as `false`, whereas Petrel models non-required object properties as `Option<bool>`
    // (`None` omitted on wire). Verify JSON decode/encode and exact CBOR parity when explicit.
    let ms_raw = load_fixture_text("com.atproto.space.createSpace", "input_memberspace");
    let ms: CreateSpaceInput = petrel_core::from_json(ms_raw.as_bytes()).unwrap();
    let ms_json: serde_json::Value =
        serde_json::from_slice(&petrel_core::to_json(&ms).unwrap()).unwrap();
    assert_eq!(ms_json["policy"]["publicRead"], serde_json::json!(true));
    assert_eq!(
        ms_json["policy"]["allowedApps"],
        serde_json::json!(["did:web:app.example#forum"])
    );
    let mut ms_explicit = ms;
    if let petrel_api::generated::com::atproto::space::create_space::InputPolicy::MemberspacePolicy(
        ref mut p,
    ) = ms_explicit.policy
    {
        p.public_write = Some(false);
        p.publish_members = Some(false);
        p.publish_policy = Some(false);
    }
    let ms_explicit_json: serde_json::Value =
        serde_json::from_slice(&petrel_core::to_json(&ms_explicit).unwrap()).unwrap();
    assert_eq!(
        ms_explicit_json,
        load_golden_json("com.atproto.space.createSpace", "input_memberspace")
    );
    assert_eq!(
        petrel_core::to_dag_cbor(&ms_explicit).unwrap(),
        load_golden_cbor("com.atproto.space.createSpace", "input_memberspace")
    );

    let mg_raw = load_fixture_text("com.atproto.space.createSpace", "input_managedspace");
    let mut mg: CreateSpaceInput = petrel_core::from_json(mg_raw.as_bytes()).unwrap();
    let mg_json: serde_json::Value =
        serde_json::from_slice(&petrel_core::to_json(&mg).unwrap()).unwrap();
    assert_eq!(
        mg_json["policy"]["managingApp"],
        serde_json::json!("did:web:app.example#forum")
    );
    if let petrel_api::generated::com::atproto::space::create_space::InputPolicy::ManagedspacePolicy(
        ref mut p,
    ) = mg.policy
    {
        p.publish_policy = Some(false);
    }
    assert_eq!(
        serde_json::from_slice::<serde_json::Value>(&petrel_core::to_json(&mg).unwrap()).unwrap(),
        load_golden_json("com.atproto.space.createSpace", "input_managedspace")
    );
    assert_eq!(
        petrel_core::to_dag_cbor(&mg).unwrap(),
        load_golden_cbor("com.atproto.space.createSpace", "input_managedspace")
    );

    assert_rejects_petrel::<CreateSpaceInput>(
        "com.atproto.space.createSpace",
        "input_legacy_type_reject",
    );

    let gs_raw = load_fixture_text("com.atproto.space.getSpace", "output_managedspace");
    let mut gs: GetSpaceOutput = petrel_core::from_json(gs_raw.as_bytes()).unwrap();
    if let petrel_api::generated::com::atproto::space::get_space::OutputPolicy::ManagedspacePolicy(
        ref mut p,
    ) = gs.policy
    {
        p.publish_policy = Some(false);
    }
    assert_eq!(
        serde_json::from_slice::<serde_json::Value>(&petrel_core::to_json(&gs).unwrap()).unwrap(),
        load_golden_json("com.atproto.space.getSpace", "output_managedspace")
    );
    assert_eq!(
        petrel_core::to_dag_cbor(&gs).unwrap(),
        load_golden_cbor("com.atproto.space.getSpace", "output_managedspace")
    );
}

#[test]
fn parity_conversation_space_contract() {
    roundtrip_petrel::<GetThreadStorageOutput>(
        "blue.catbird.labyrinth.getThreadStorage",
        "output_space",
        true,
    );
    roundtrip_petrel::<GetThreadStorageOutput>(
        "blue.catbird.labyrinth.getThreadStorage",
        "output_legacy_mlsds",
        true,
    );

    let lsb_params = ListSpaceBatchesParams::new(
        "conversation".into(),
        "a4c334f4-1581-4f8c-9767-e6e7e23e70e2".into(),
        "a4c334f4-1581-4f8c-9767-e6e7e23e70e2".into(),
    );
    let mut lsb_pairs = Vec::new();
    lsb_params.to_query_params(&mut lsb_pairs);
    assert!(lsb_pairs.contains(&("threadId", "conversation".to_owned())));
    assert!(lsb_pairs.contains(&(
        "generation",
        "a4c334f4-1581-4f8c-9767-e6e7e23e70e2".to_owned()
    )));
    assert!(lsb_pairs.contains(&(
        "actorDeviceId",
        "a4c334f4-1581-4f8c-9767-e6e7e23e70e2".to_owned()
    )));

    // SpaceBatchView has `"cid": {"type": "string", "format": "cid"}`.
    // Per ATProto Lexicon spec, format:"cid" is a CBOR text string (`CidString`),
    // whereas Jacquard 0.13 erroneously encodes it as CBOR tag 42 (`Value::Link`).
    // Verify JSON parity + CBOR roundtrip + exact Value diff (only `.cid` is String vs Link).
    roundtrip_petrel::<SpaceBatchView>(
        "blue.catbird.labyrinth.defs",
        "space_batch_view_valid",
        false,
    );
    let sbv_raw = load_fixture_text("blue.catbird.labyrinth.defs", "space_batch_view_valid");
    let sbv: SpaceBatchView = petrel_core::from_json(sbv_raw.as_bytes()).unwrap();
    let petrel_cbor = petrel_core::to_dag_cbor(&sbv).unwrap();
    assert_eq!(
        petrel_core::from_dag_cbor::<SpaceBatchView>(&petrel_cbor).unwrap(),
        sbv
    );
    let mut petrel_val = petrel_core::dagcbor::decode(&petrel_cbor).unwrap();
    let jacquard_val = petrel_core::dagcbor::decode(&load_golden_cbor(
        "blue.catbird.labyrinth.defs",
        "space_batch_view_valid",
    ))
    .unwrap();
    let Value::Object(petrel_map) = &mut petrel_val else {
        panic!("expected Value::Object");
    };
    let jacquard_map = jacquard_val.as_object().unwrap();
    assert!(matches!(petrel_map.get("cid"), Some(Value::String(_))));
    assert!(matches!(jacquard_map.get("cid"), Some(Value::Link(_))));
    assert_eq!(
        petrel_map.get("cid").unwrap().as_str().unwrap(),
        match jacquard_map.get("cid").unwrap() {
            Value::Link(c) => c.to_string(),
            _ => unreachable!(),
        }
    );
    petrel_map.insert("cid".into(), jacquard_map.get("cid").unwrap().clone());
    assert_eq!(petrel_val, jacquard_val);
    assert_rejects_petrel::<SpaceBatchView>(
        "blue.catbird.labyrinth.defs",
        "space_batch_view_missing_redactions",
    );
    roundtrip_petrel::<GetSpaceMembershipOutput>(
        "blue.catbird.labyrinth.getSpaceMembership",
        "output_valid",
        true,
    );
    roundtrip_petrel::<DeleteSpaceGenerationInput>(
        "blue.catbird.labyrinth.deleteSpaceGeneration",
        "input_valid",
        true,
    );
    assert_rejects_petrel::<DeleteSpaceGenerationInput>(
        "blue.catbird.labyrinth.deleteSpaceGeneration",
        "input_missing_generation",
    );
    roundtrip_petrel::<DeleteSpaceGenerationOutput>(
        "blue.catbird.labyrinth.deleteSpaceGeneration",
        "output_valid",
        true,
    );
    assert_error_wire::<FindMeksError>(
        "blue.catbird.labyrinth.findMeks",
        "error_space_deleted",
        true,
    );
    assert_error_wire::<GetAttachmentBlobError>(
        "blue.catbird.labyrinth.getAttachmentBlob",
        "error_space_deleted",
        true,
    );
}

#[test]
fn parity_get_convo_events_contract() {
    roundtrip_petrel::<ConvoEventEntry>(
        "blue.catbird.mlsDS.getConvoEvents",
        "convo_event_entry_clean",
        true,
    );
    roundtrip_petrel::<ConvoEventEntry>(
        "blue.catbird.mlsDS.getConvoEvents",
        "convo_event_entry_legacy",
        true,
    );
    for slug in [
        "applicationEntry",
        "commitEntry",
        "policyEntry",
        "metadataEntry",
        "creationEntry",
        "participantAcceptanceEntry",
        "conversationCloseEntry",
        "resetRequestEntry",
        "resetActivationEntry",
        "leafRecoveryFulfillmentEntry",
        "leaveRequestEntry",
        "zeroLeafLeaveEntry",
        "leaveCancellationEntry",
        "leaveCommitFulfillmentEntry",
        "futureExtensionEntry",
    ] {
        roundtrip_petrel::<ConvoEventEntry>(
            "blue.catbird.mlsDS.getConvoEvents",
            &format!("convo_event_entry_kind_{slug}"),
            true,
        );
    }
}

#[test]
fn parity_open_union_and_site_standard() {
    roundtrip_petrel::<Document>("site.standard.document", "open_union_unknown_content", true);
    roundtrip_petrel::<Rgb>("site.standard.theme.color", "rgb_valid", true);
}

#[test]
fn parity_labyrinth_relay_raw_passthrough() {
    let bytes: petrel_core::Bytes = assert_raw_output_passthrough(
        "blue.catbird.labyrinth.labyrinthRelay",
        "raw_json_passthrough",
    );
    assert_eq!(bytes.0.as_slice(), br#"{"threads":[],"cursor":"3l2b"}"#);
}

#[test]
fn parity_named_bytes_and_push_registration() {
    roundtrip_petrel::<ApplicationAttachmentBinding>(
        "blue.catbird.chat.defs",
        "application_attachment_binding",
        true,
    );
    roundtrip_petrel::<DirectRequestWelcomeBundle>(
        "blue.catbird.chat.defs",
        "direct_request_welcome_bundle",
        true,
    );
    // Jacquard 0.13 collapses 1-member closed unions (`signedBlobDeletion.body`) to a bare
    // struct with no `$type` tag, whereas Petrel (Swift, Kotlin, and Rust) faithfully emits
    // a `$type`-tagged union per the lexicon schema.
    let sbd_raw = load_fixture_text("blue.catbird.chat.defs", "signed_blob_deletion");
    let err = petrel_core::from_json::<SignedBlobDeletion>(sbd_raw.as_bytes()).unwrap_err();
    assert!(
        format!("{err}").contains("union value has no `$type`"),
        "expected missing $type on untagged 1-member union, got {err}"
    );
    let mut sbd_val: serde_json::Value = serde_json::from_str(&sbd_raw).unwrap();
    sbd_val["body"]["$type"] = serde_json::json!("blue.catbird.chat.defs#blobDeletionBody");
    let sbd_tagged = serde_json::to_string(&sbd_val).unwrap();
    let sbd: SignedBlobDeletion = petrel_core::from_json(sbd_tagged.as_bytes()).unwrap();
    let sbd_cbor = petrel_core::to_dag_cbor(&sbd).unwrap();
    assert_eq!(
        petrel_core::from_dag_cbor::<SignedBlobDeletion>(&sbd_cbor).unwrap(),
        sbd
    );
    let sbd_re_json: serde_json::Value =
        serde_json::from_slice(&petrel_core::to_json(&sbd).unwrap()).unwrap();
    let mut golden_sbd = load_golden_json("blue.catbird.chat.defs", "signed_blob_deletion");
    golden_sbd["body"]["$type"] = serde_json::json!("blue.catbird.chat.defs#blobDeletionBody");
    assert_json_eq_allowing_unpadded_bytes(&sbd_re_json, &golden_sbd, "signed_blob_deletion");
    roundtrip_petrel::<PushRegistrationBody>(
        "blue.catbird.chat.defs",
        "push_registration_body",
        true,
    );
}

#[test]
fn parity_captured_appview_and_local_private_captures() {
    // ProfileViewDetailed contains `pinnedPost: StrongRef { cid: format("cid"), uri }`.
    // Same ATProto spec vs Jacquard difference as SpaceBatchView: Petrel encodes `format:"cid"`
    // as CBOR text string (`CidString`), whereas Jacquard encodes it as CBOR tag 42 (`Link`).
    roundtrip_petrel::<ProfileViewDetailed>(
        "app.bsky.actor.getProfile",
        "recorded_bsky_app",
        false,
    );
    let prof_raw = load_fixture_text("app.bsky.actor.getProfile", "recorded_bsky_app");
    let prof: ProfileViewDetailed = petrel_core::from_json(prof_raw.as_bytes()).unwrap();
    let prof_cbor = petrel_core::to_dag_cbor(&prof).unwrap();
    assert_eq!(
        petrel_core::from_dag_cbor::<ProfileViewDetailed>(&prof_cbor).unwrap(),
        prof
    );
    let mut p_val = petrel_core::dagcbor::decode(&prof_cbor).unwrap();
    let j_val = petrel_core::dagcbor::decode(&load_golden_cbor(
        "app.bsky.actor.getProfile",
        "recorded_bsky_app",
    ))
    .unwrap();
    let Value::Object(p_obj) = &mut p_val else {
        panic!("expected profile object");
    };
    let Value::Object(p_pinned) = p_obj.get_mut("pinnedPost").unwrap() else {
        panic!("expected pinnedPost object");
    };
    let j_pinned = j_val
        .as_object()
        .unwrap()
        .get("pinnedPost")
        .unwrap()
        .as_object()
        .unwrap();
    assert!(matches!(p_pinned.get("cid"), Some(Value::String(_))));
    assert!(matches!(j_pinned.get("cid"), Some(Value::Link(_))));
    p_pinned.insert("cid".into(), j_pinned.get("cid").unwrap().clone());
    assert_eq!(p_val, j_val);

    // Same `format:"cid"` String vs Link difference on `views[0].cid` in `LabelerViewDetailed`.
    roundtrip_petrel::<GetServicesOutput>(
        "app.bsky.labeler.getServices",
        "recorded_moderation_bsky_app",
        false,
    );
    let lbl_raw = load_fixture_text(
        "app.bsky.labeler.getServices",
        "recorded_moderation_bsky_app",
    );
    let lbl: GetServicesOutput = petrel_core::from_json(lbl_raw.as_bytes()).unwrap();
    let lbl_cbor = petrel_core::to_dag_cbor(&lbl).unwrap();
    assert_eq!(
        petrel_core::from_dag_cbor::<GetServicesOutput>(&lbl_cbor).unwrap(),
        lbl
    );
    let mut lp_val = petrel_core::dagcbor::decode(&lbl_cbor).unwrap();
    let lj_val = petrel_core::dagcbor::decode(&load_golden_cbor(
        "app.bsky.labeler.getServices",
        "recorded_moderation_bsky_app",
    ))
    .unwrap();
    let Value::Object(lp_root) = &mut lp_val else {
        panic!("expected root object");
    };
    let Value::Array(lp_views) = lp_root.get_mut("views").unwrap() else {
        panic!("expected views array");
    };
    let Value::Object(lp_view0) = &mut lp_views[0] else {
        panic!("expected view[0] object");
    };
    let Value::Array(lj_views) = lj_val.as_object().unwrap().get("views").unwrap() else {
        panic!("expected golden views array");
    };
    let lj_view0 = lj_views[0].as_object().unwrap();
    assert!(matches!(lp_view0.get("cid"), Some(Value::String(_))));
    assert!(matches!(lj_view0.get("cid"), Some(Value::Link(_))));
    lp_view0.insert("cid".into(), lj_view0.get("cid").unwrap().clone());
    assert_eq!(lp_val, lj_val);

    // Private captured MLS payloads live strictly outside the public PetrelCatbird git tree
    // (under /tmp/petrel-catbird-b2-private when present locally).
    let priv_root = std::path::Path::new("/tmp/petrel-catbird-b2-private/fixtures");
    if priv_root.exists() {
        let c21 = std::fs::read(priv_root.join(
            "blue.catbird.chat.getConversationState/captured_c21_direct_request_accepted.json",
        ))
        .unwrap();
        assert!(petrel_core::from_json::<GetConversationStateOutput>(&c21).is_err());

        let hist = std::fs::read(priv_root.join(
            "blue.catbird.chat.createConversation/historical_466e60e0_legacy_wire_body.json",
        ))
        .unwrap();
        assert!(petrel_core::from_json::<CreateConversationInput>(&hist).is_err());
    }
}
