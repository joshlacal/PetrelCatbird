// Lexicon: 1, ID: blue.catbird.chat.enrollDevice
// First enrollment only. Requires a one-use at-most-120-second Nest fresh-auth enrollment grant from an enrollment-purpose-bound OAuth authorization_code flow. Nest creates evidence only after successful callback/code exchange and issuer/subject/scope/DPoP validation; restore, refresh, cookie exchange, or an existing session alone never creates evidence. Callback completion opens one encrypted capability through auth_time + 300 seconds, with auth_time equal to Nest callback-completion time and not upstream auth_time. Capability states are unpinned, pinned/pending, and terminal-success. Before pinning, Nest performs strict canonical decode, bounds, and capability/body binding checks and verifies the body's Ed25519 signature under its supplied immutable signing key; malformed, out-of-bounds, binding-invalid, or signature-invalid attempts neither pin nor burn it. The first body that passes all checks transitions unpinned to pinned/pending and atomically pins exact canonical digest, separate signature, DID, device, DPoP JKT, key ID, signing-key digest, and enrollment-transcript digest. While pinned/pending and Nest has not durably recorded downstream success, including ambiguous response loss after delivery-service commit, the same exact body may mint another downstream grant; each such attempt retains original auth_time but gets fresh token/proof JTIs and a server-generated per-attempt canonical lowercase UUIDv4 auth_txn distinct from provider state and client input. Changed body cannot reuse the capability. Once Nest durably records success it stores the terminal result/binding, transitions to terminal-success, and closes the capability; exact client retry is then answered from that Nest-stored result without a new downstream grant. Expiry before terminal success requires a new code flow. Besides exact common claims iss, sub, aud, lxm, iat, exp, jti, cnf.jkt, device_id, and chat_instance, the grant carries key_id, signing_key_sha256, enrollment_transcript_sha256, auth_time, and auth_txn and sets exp = min(iat + 120, auth_time + 300) using checked NumericDate arithmetic; ordinary tokens require exp <= iat + 120. At trusted instant T, the delivery service independently requires 0 <= T-auth_time <= 300 seconds. prompt=login and an ephemeral browser are best-effort only; neither is a security predicate, fresh authorization-code completion does not attest credential entry or user presence, and no user reauthentication is claimed. Exact subject, endpoint, device, key ID, SHA-256 Ed25519 key, dpopJkt through cnf.jkt, and raw SHA-256 of the canonical enrollment signing transcript must match; grant cnf.jkt, proof RFC7638 JKT, and signed body dpopJkt match before lookup. A generic bearer/session token is forbidden. Requires row/tombstone absence, generation zero, and Ed25519 proof. While Nest remains pinned/pending, a delivery-service exact completed replay under a fresh grant, auth_txn, token/proof JTIs, and token/proof bound to the recorded JKT returns the stored result and never reexecutes. After terminal-success, Nest answers an exact client retry from its stored terminal result/binding and issues no downstream grant.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatEnrollDeviceDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.enrollDevice"
}

@Serializable
    data class BlueCatbirdChatEnrollDeviceInput(
        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedDeviceEnrollment    )

    @Serializable
    data class BlueCatbirdChatEnrollDeviceOutput(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    )

sealed class BlueCatbirdChatEnrollDeviceError(val name: String, val description: String?) {
        object AuthenticationGenerationConflict: BlueCatbirdChatEnrollDeviceError("AuthenticationGenerationConflict", "")
        object CutoverRequired: BlueCatbirdChatEnrollDeviceError("CutoverRequired", "")
        object DeviceAlreadyExists: BlueCatbirdChatEnrollDeviceError("DeviceAlreadyExists", "")
        object DeviceLimitReached: BlueCatbirdChatEnrollDeviceError("DeviceLimitReached", "")
        object DeviceTombstoned: BlueCatbirdChatEnrollDeviceError("DeviceTombstoned", "")
        object IdempotencyConflict: BlueCatbirdChatEnrollDeviceError("IdempotencyConflict", "")
        object InvalidDPoP: BlueCatbirdChatEnrollDeviceError("InvalidDPoP", "")
        object InvalidKeyPackage: BlueCatbirdChatEnrollDeviceError("InvalidKeyPackage", "")
        object InvalidRequest: BlueCatbirdChatEnrollDeviceError("InvalidRequest", "")
        object InvalidSignature: BlueCatbirdChatEnrollDeviceError("InvalidSignature", "")
        object KeyPackageInventoryLimitReached: BlueCatbirdChatEnrollDeviceError("KeyPackageInventoryLimitReached", "")
        object NotAuthorized: BlueCatbirdChatEnrollDeviceError("NotAuthorized", "")
    }

/**
 * First enrollment only. Requires a one-use at-most-120-second Nest fresh-auth enrollment grant from an enrollment-purpose-bound OAuth authorization_code flow. Nest creates evidence only after successful callback/code exchange and issuer/subject/scope/DPoP validation; restore, refresh, cookie exchange, or an existing session alone never creates evidence. Callback completion opens one encrypted capability through auth_time + 300 seconds, with auth_time equal to Nest callback-completion time and not upstream auth_time. Capability states are unpinned, pinned/pending, and terminal-success. Before pinning, Nest performs strict canonical decode, bounds, and capability/body binding checks and verifies the body's Ed25519 signature under its supplied immutable signing key; malformed, out-of-bounds, binding-invalid, or signature-invalid attempts neither pin nor burn it. The first body that passes all checks transitions unpinned to pinned/pending and atomically pins exact canonical digest, separate signature, DID, device, DPoP JKT, key ID, signing-key digest, and enrollment-transcript digest. While pinned/pending and Nest has not durably recorded downstream success, including ambiguous response loss after delivery-service commit, the same exact body may mint another downstream grant; each such attempt retains original auth_time but gets fresh token/proof JTIs and a server-generated per-attempt canonical lowercase UUIDv4 auth_txn distinct from provider state and client input. Changed body cannot reuse the capability. Once Nest durably records success it stores the terminal result/binding, transitions to terminal-success, and closes the capability; exact client retry is then answered from that Nest-stored result without a new downstream grant. Expiry before terminal success requires a new code flow. Besides exact common claims iss, sub, aud, lxm, iat, exp, jti, cnf.jkt, device_id, and chat_instance, the grant carries key_id, signing_key_sha256, enrollment_transcript_sha256, auth_time, and auth_txn and sets exp = min(iat + 120, auth_time + 300) using checked NumericDate arithmetic; ordinary tokens require exp <= iat + 120. At trusted instant T, the delivery service independently requires 0 <= T-auth_time <= 300 seconds. prompt=login and an ephemeral browser are best-effort only; neither is a security predicate, fresh authorization-code completion does not attest credential entry or user presence, and no user reauthentication is claimed. Exact subject, endpoint, device, key ID, SHA-256 Ed25519 key, dpopJkt through cnf.jkt, and raw SHA-256 of the canonical enrollment signing transcript must match; grant cnf.jkt, proof RFC7638 JKT, and signed body dpopJkt match before lookup. A generic bearer/session token is forbidden. Requires row/tombstone absence, generation zero, and Ed25519 proof. While Nest remains pinned/pending, a delivery-service exact completed replay under a fresh grant, auth_txn, token/proof JTIs, and token/proof bound to the recorded JKT returns the stored result and never reexecutes. After terminal-success, Nest answers an exact client retry from its stored terminal result/binding and issues no downstream grant.
 *
 * Endpoint: blue.catbird.chat.enrollDevice
 */
suspend fun BlueCatbirdChatNamespace.enrollDevice(
input: BlueCatbirdChatEnrollDeviceInput): ATProtoResponse<BlueCatbirdChatEnrollDeviceOutput> {
    val endpoint = "blue.catbird.chat.enrollDevice"

    // JSON serialization
    val body = Json.encodeToString(input)
    val contentType = "application/json"

    val queryItems: List<Pair<String, String>>? = null

    return client.networkService.performRequest(
        method = "POST",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf(
            "Content-Type" to contentType,
            "Accept" to "application/json"
        ),
        body = body
    )
}
