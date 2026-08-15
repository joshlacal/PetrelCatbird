// Lexicon: 1, ID: blue.catbird.chat.defs
// Closed clean-cutover protocol types. Runtime validators reject duplicate keys, null, unknown fields, unknown union tags, noncanonical identifiers/timestamps/order, and all size/depth/count overflow before DTO deserialization. For each request the server captures one trusted instant T. First execution of every signed mutation requires canonical signedAt in [T-300 seconds,T+60 seconds] and otherwise returns InvalidRequest; only an exact already-completed idempotent replay with the identical stored transcript digest and separate 64-byte signature may bypass signedAt age, and it still requires fresh valid DPoP/JTI. The canonical request digest is raw 32-byte SHA-256 of the exact domain-prefixed canonical Ed25519 signing transcript, never raw JSON, generated DTO bytes, or a signature-containing wrapper. Idempotency identity is endpoint NSID plus authenticated principal plus signed idempotencyKey, except sendMessage uses messageId; exact digest and exact signature must both match or the request conflicts. Durable pending-work expiry derives from server receivedAt, never signedAt; typing TTL and every server timestamp derive from T. Nest is the trusted token exchanger/gateway and sends only issuer-signed clean JWTs of at most 120 seconds using Authorization DPoP. Exact required claims are iss, sub, aud, lxm, iat, exp, jti, cnf.jkt, device_id, and chat_instance. Token/grant jti, device_id, and chat_instance are canonical lowercase UUIDv4 strings; token/grant jti is consumed once in the configured issuer token namespace. Proof jti is canonical base64url without padding decoding to 12-32 bytes and replay uniqueness is exactly (JKT,decoded jti bytes) in a separate namespace; htu and htm remain validated audit claims and never widen that key. Token cnf.jkt, proof RFC7638 JKT, and signed-body or stored JKT must match, except rebind bootstrap authenticates signed newDpopJkt and the immutable stored Ed25519 key before CAS. First enrollment additionally requires a one-use fresh-auth grant from an enrollment-purpose-bound OAuth authorization_code flow. Nest creates fresh evidence only after successful callback/code exchange and issuer, subject, scope, and DPoP validation; restore, refresh, cookie exchange, or an existing session alone never creates evidence. Callback completion opens one encrypted enrollment capability through auth_time + 300 seconds, and auth_time is Nest callback-completion time, not upstream auth_time. Capability states are unpinned, pinned/pending, and terminal-success. Before pinning, Nest performs strict canonical decode, bounds, and capability/body binding checks and verifies the body's Ed25519 signature under its supplied immutable signing key. A malformed, out-of-bounds, binding-invalid, or signature-invalid attempt neither pins nor burns the capability. The first body that passes all of those checks transitions unpinned to pinned/pending and atomically pins its exact canonical request digest, signature, DID, device ID, DPoP JKT, key ID, signing-key digest, and enrollment-transcript digest. While pinned/pending and Nest has not durably recorded downstream success, including ambiguous response loss after delivery-service commit, the same exact body may mint another downstream grant; each attempt retains original auth_time but gets fresh token/proof JTIs and auth_txn, and delivery-service exact idempotent replay returns its stored result. auth_txn is a server-generated per-grant canonical lowercase UUIDv4 distinct from provider state and client input. Changed body cannot reuse the capability. Once Nest durably records success it stores the terminal result/binding, transitions to terminal-success, and closes the capability; exact client retry is then answered from that Nest-stored terminal result without a new downstream grant. Expiry before terminal success requires a new purpose-bound code flow. prompt=login and an ephemeral browser are best-effort only; neither is a server security predicate, fresh authorization-code completion does not attest credential entry or user presence, and the protocol does not claim user reauthentication. Exact additional grant claims are key_id, signing_key_sha256, enrollment_transcript_sha256, auth_time, and auth_txn. Enrollment grant exp = min(iat + 120, auth_time + 300) using checked NumericDate arithmetic; ordinary tokens require exp <= iat + 120. The delivery service independently accepts the Nest issuer attestation only when 0 <= T-auth_time <= 300 seconds. DPoP htu uses only a configured trusted external base, never Host, Forwarded, or X-Forwarded headers: scheme is https, host is lowercase ASCII/IDNA A-label form with no userinfo or trailing dot, the base has no path/query/fragment, port 443 is omitted, only an explicitly allowed nondefault port is retained, and htu is that base plus exact /xrpc/{NSID} with the request query and fragment excluded.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object BlueCatbirdChatDefsDefs {
    const val TYPE_IDENTIFIER = "blue.catbird.chat.defs"
}

@Serializable(with = BlueCatbirdChatDefsParticipantChangeSerializer::class)
sealed interface BlueCatbirdChatDefsParticipantChange {
    @Serializable
    data class AddParticipant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsAddParticipant) : BlueCatbirdChatDefsParticipantChange

    @Serializable
    data class RemoveParticipant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveParticipant) : BlueCatbirdChatDefsParticipantChange

    @Serializable
    data class ChangeParticipantRole(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsChangeParticipantRole) : BlueCatbirdChatDefsParticipantChange

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsParticipantChange
}

object BlueCatbirdChatDefsParticipantChangeSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsParticipantChange> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsParticipantChange")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsParticipantChange) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsParticipantChange.AddParticipant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAddParticipant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#addParticipant")
                })
            }
            is BlueCatbirdChatDefsParticipantChange.RemoveParticipant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveParticipant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#removeParticipant")
                })
            }
            is BlueCatbirdChatDefsParticipantChange.ChangeParticipantRole -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsChangeParticipantRole.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#changeParticipantRole")
                })
            }
            is BlueCatbirdChatDefsParticipantChange.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsParticipantChange {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#addParticipant" -> BlueCatbirdChatDefsParticipantChange.AddParticipant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAddParticipant.serializer(), element)
            )
            "blue.catbird.chat.defs#removeParticipant" -> BlueCatbirdChatDefsParticipantChange.RemoveParticipant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveParticipant.serializer(), element)
            )
            "blue.catbird.chat.defs#changeParticipantRole" -> BlueCatbirdChatDefsParticipantChange.ChangeParticipantRole(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsChangeParticipantRole.serializer(), element)
            )
            else -> BlueCatbirdChatDefsParticipantChange.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsLeafChangeSerializer::class)
sealed interface BlueCatbirdChatDefsLeafChange {
    @Serializable
    data class AddLeafByRecovery(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsAddLeafByRecovery) : BlueCatbirdChatDefsLeafChange

    @Serializable
    data class RemoveLeaf(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveLeaf) : BlueCatbirdChatDefsLeafChange

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsLeafChange
}

object BlueCatbirdChatDefsLeafChangeSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsLeafChange> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsLeafChange")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsLeafChange) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsLeafChange.AddLeafByRecovery -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAddLeafByRecovery.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#addLeafByRecovery")
                })
            }
            is BlueCatbirdChatDefsLeafChange.RemoveLeaf -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveLeaf.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#removeLeaf")
                })
            }
            is BlueCatbirdChatDefsLeafChange.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsLeafChange {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#addLeafByRecovery" -> BlueCatbirdChatDefsLeafChange.AddLeafByRecovery(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAddLeafByRecovery.serializer(), element)
            )
            "blue.catbird.chat.defs#removeLeaf" -> BlueCatbirdChatDefsLeafChange.RemoveLeaf(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRemoveLeaf.serializer(), element)
            )
            else -> BlueCatbirdChatDefsLeafChange.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsApplicationEmbedSerializer::class)
sealed interface BlueCatbirdChatDefsApplicationEmbed {
    @Serializable
    data class EncryptedImageEmbedVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedImageEmbedVariant) : BlueCatbirdChatDefsApplicationEmbed

    @Serializable
    data class EncryptedAudioEmbedVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedAudioEmbedVariant) : BlueCatbirdChatDefsApplicationEmbed

    @Serializable
    data class AtprotoRecordEmbedVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsAtprotoRecordEmbedVariant) : BlueCatbirdChatDefsApplicationEmbed

    @Serializable
    data class ExternalLinkEmbedVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsExternalLinkEmbedVariant) : BlueCatbirdChatDefsApplicationEmbed

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsApplicationEmbed
}

object BlueCatbirdChatDefsApplicationEmbedSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsApplicationEmbed> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsApplicationEmbed")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsApplicationEmbed) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsApplicationEmbed.EncryptedImageEmbedVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedImageEmbedVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#encryptedImageEmbedVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationEmbed.EncryptedAudioEmbedVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedAudioEmbedVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#encryptedAudioEmbedVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationEmbed.AtprotoRecordEmbedVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAtprotoRecordEmbedVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#atprotoRecordEmbedVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationEmbed.ExternalLinkEmbedVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsExternalLinkEmbedVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#externalLinkEmbedVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationEmbed.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsApplicationEmbed {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#encryptedImageEmbedVariant" -> BlueCatbirdChatDefsApplicationEmbed.EncryptedImageEmbedVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedImageEmbedVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#encryptedAudioEmbedVariant" -> BlueCatbirdChatDefsApplicationEmbed.EncryptedAudioEmbedVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEncryptedAudioEmbedVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#atprotoRecordEmbedVariant" -> BlueCatbirdChatDefsApplicationEmbed.AtprotoRecordEmbedVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAtprotoRecordEmbedVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#externalLinkEmbedVariant" -> BlueCatbirdChatDefsApplicationEmbed.ExternalLinkEmbedVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsExternalLinkEmbedVariant.serializer(), element)
            )
            else -> BlueCatbirdChatDefsApplicationEmbed.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsApplicationFrameBodySerializer::class)
sealed interface BlueCatbirdChatDefsApplicationFrameBody {
    @Serializable
    data class MessageFrameVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageFrameVariant) : BlueCatbirdChatDefsApplicationFrameBody

    @Serializable
    data class ReactionFrameVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsReactionFrameVariant) : BlueCatbirdChatDefsApplicationFrameBody

    @Serializable
    data class EditFrameVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsEditFrameVariant) : BlueCatbirdChatDefsApplicationFrameBody

    @Serializable
    data class TombstoneFrameVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsTombstoneFrameVariant) : BlueCatbirdChatDefsApplicationFrameBody

    @Serializable
    data class ReadStateFrameVariant(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsReadStateFrameVariant) : BlueCatbirdChatDefsApplicationFrameBody

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsApplicationFrameBody
}

object BlueCatbirdChatDefsApplicationFrameBodySerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsApplicationFrameBody> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsApplicationFrameBody")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsApplicationFrameBody) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsApplicationFrameBody.MessageFrameVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageFrameVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#messageFrameVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationFrameBody.ReactionFrameVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsReactionFrameVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#reactionFrameVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationFrameBody.EditFrameVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEditFrameVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#editFrameVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationFrameBody.TombstoneFrameVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTombstoneFrameVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#tombstoneFrameVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationFrameBody.ReadStateFrameVariant -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsReadStateFrameVariant.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#readStateFrameVariant")
                })
            }
            is BlueCatbirdChatDefsApplicationFrameBody.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsApplicationFrameBody {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#messageFrameVariant" -> BlueCatbirdChatDefsApplicationFrameBody.MessageFrameVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageFrameVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#reactionFrameVariant" -> BlueCatbirdChatDefsApplicationFrameBody.ReactionFrameVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsReactionFrameVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#editFrameVariant" -> BlueCatbirdChatDefsApplicationFrameBody.EditFrameVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEditFrameVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#tombstoneFrameVariant" -> BlueCatbirdChatDefsApplicationFrameBody.TombstoneFrameVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTombstoneFrameVariant.serializer(), element)
            )
            "blue.catbird.chat.defs#readStateFrameVariant" -> BlueCatbirdChatDefsApplicationFrameBody.ReadStateFrameVariant(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsReadStateFrameVariant.serializer(), element)
            )
            else -> BlueCatbirdChatDefsApplicationFrameBody.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsRecoveryWorkViewSerializer::class)
sealed interface BlueCatbirdChatDefsRecoveryWorkView {
    @Serializable
    data class RecoveryWorkPendingView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView) : BlueCatbirdChatDefsRecoveryWorkView

    @Serializable
    data class RecoveryWorkCompletedByTransitionView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView) : BlueCatbirdChatDefsRecoveryWorkView

    @Serializable
    data class RecoveryWorkSupersededByTransitionView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView) : BlueCatbirdChatDefsRecoveryWorkView

    @Serializable
    data class RecoveryWorkSupersededByRevocationView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView) : BlueCatbirdChatDefsRecoveryWorkView

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsRecoveryWorkView
}

object BlueCatbirdChatDefsRecoveryWorkViewSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsRecoveryWorkView> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsRecoveryWorkView")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsRecoveryWorkView) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkPendingView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkPendingView")
                })
            }
            is BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkCompletedByTransitionView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkCompletedByTransitionView")
                })
            }
            is BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkSupersededByTransitionView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkSupersededByTransitionView")
                })
            }
            is BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkSupersededByRevocationView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkSupersededByRevocationView")
                })
            }
            is BlueCatbirdChatDefsRecoveryWorkView.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsRecoveryWorkView {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#recoveryWorkPendingView" -> BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkPendingView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkCompletedByTransitionView" -> BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkCompletedByTransitionView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkSupersededByTransitionView" -> BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkSupersededByTransitionView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkSupersededByRevocationView" -> BlueCatbirdChatDefsRecoveryWorkView.RecoveryWorkSupersededByRevocationView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView.serializer(), element)
            )
            else -> BlueCatbirdChatDefsRecoveryWorkView.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsLeafRecoveryInboxItemSerializer::class)
sealed interface BlueCatbirdChatDefsLeafRecoveryInboxItem {
    @Serializable
    data class LeafRecoveryView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryView) : BlueCatbirdChatDefsLeafRecoveryInboxItem

    @Serializable
    data class RecoveryWorkPendingView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView) : BlueCatbirdChatDefsLeafRecoveryInboxItem

    @Serializable
    data class RecoveryWorkCompletedByTransitionView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView) : BlueCatbirdChatDefsLeafRecoveryInboxItem

    @Serializable
    data class RecoveryWorkSupersededByTransitionView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView) : BlueCatbirdChatDefsLeafRecoveryInboxItem

    @Serializable
    data class RecoveryWorkSupersededByRevocationView(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView) : BlueCatbirdChatDefsLeafRecoveryInboxItem

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsLeafRecoveryInboxItem
}

object BlueCatbirdChatDefsLeafRecoveryInboxItemSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsLeafRecoveryInboxItem> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsLeafRecoveryInboxItem")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsLeafRecoveryInboxItem) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.LeafRecoveryView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryView")
                })
            }
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkPendingView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkPendingView")
                })
            }
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkCompletedByTransitionView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkCompletedByTransitionView")
                })
            }
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkSupersededByTransitionView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkSupersededByTransitionView")
                })
            }
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkSupersededByRevocationView -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#recoveryWorkSupersededByRevocationView")
                })
            }
            is BlueCatbirdChatDefsLeafRecoveryInboxItem.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsLeafRecoveryInboxItem {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leafRecoveryView" -> BlueCatbirdChatDefsLeafRecoveryInboxItem.LeafRecoveryView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkPendingView" -> BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkPendingView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkPendingView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkCompletedByTransitionView" -> BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkCompletedByTransitionView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkSupersededByTransitionView" -> BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkSupersededByTransitionView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView.serializer(), element)
            )
            "blue.catbird.chat.defs#recoveryWorkSupersededByRevocationView" -> BlueCatbirdChatDefsLeafRecoveryInboxItem.RecoveryWorkSupersededByRevocationView(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView.serializer(), element)
            )
            else -> BlueCatbirdChatDefsLeafRecoveryInboxItem.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsLeaveOperationResultSerializer::class)
sealed interface BlueCatbirdChatDefsLeaveOperationResult {
    @Serializable
    data class DurableLeaveRequestResult(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsDurableLeaveRequestResult) : BlueCatbirdChatDefsLeaveOperationResult

    @Serializable
    data class ZeroLeafLeaveResult(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveResult) : BlueCatbirdChatDefsLeaveOperationResult

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsLeaveOperationResult
}

object BlueCatbirdChatDefsLeaveOperationResultSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsLeaveOperationResult> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsLeaveOperationResult")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsLeaveOperationResult) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsLeaveOperationResult.DurableLeaveRequestResult -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDurableLeaveRequestResult.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#durableLeaveRequestResult")
                })
            }
            is BlueCatbirdChatDefsLeaveOperationResult.ZeroLeafLeaveResult -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveResult.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#zeroLeafLeaveResult")
                })
            }
            is BlueCatbirdChatDefsLeaveOperationResult.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsLeaveOperationResult {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#durableLeaveRequestResult" -> BlueCatbirdChatDefsLeaveOperationResult.DurableLeaveRequestResult(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDurableLeaveRequestResult.serializer(), element)
            )
            "blue.catbird.chat.defs#zeroLeafLeaveResult" -> BlueCatbirdChatDefsLeaveOperationResult.ZeroLeafLeaveResult(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveResult.serializer(), element)
            )
            else -> BlueCatbirdChatDefsLeaveOperationResult.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsConversationCreationResultSerializer::class)
sealed interface BlueCatbirdChatDefsConversationCreationResult {
    @Serializable
    data class ConversationCreatedResult(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCreatedResult) : BlueCatbirdChatDefsConversationCreationResult

    @Serializable
    data class ExistingDirectConversationResult(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsExistingDirectConversationResult) : BlueCatbirdChatDefsConversationCreationResult

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsConversationCreationResult
}

object BlueCatbirdChatDefsConversationCreationResultSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsConversationCreationResult> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsConversationCreationResult")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsConversationCreationResult) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsConversationCreationResult.ConversationCreatedResult -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCreatedResult.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationCreatedResult")
                })
            }
            is BlueCatbirdChatDefsConversationCreationResult.ExistingDirectConversationResult -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsExistingDirectConversationResult.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#existingDirectConversationResult")
                })
            }
            is BlueCatbirdChatDefsConversationCreationResult.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsConversationCreationResult {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#conversationCreatedResult" -> BlueCatbirdChatDefsConversationCreationResult.ConversationCreatedResult(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCreatedResult.serializer(), element)
            )
            "blue.catbird.chat.defs#existingDirectConversationResult" -> BlueCatbirdChatDefsConversationCreationResult.ExistingDirectConversationResult(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsExistingDirectConversationResult.serializer(), element)
            )
            else -> BlueCatbirdChatDefsConversationCreationResult.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion {
    @Serializable
    data class DeviceEnrollmentBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceEnrollmentBody) : BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion
}

object BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion.DeviceEnrollmentBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceEnrollmentBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#deviceEnrollmentBody")
                })
            }
            is BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#deviceEnrollmentBody" -> BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion.DeviceEnrollmentBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceEnrollmentBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion {
    @Serializable
    data class KeyPackageReplenishmentBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsKeyPackageReplenishmentBody) : BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion
}

object BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion.KeyPackageReplenishmentBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsKeyPackageReplenishmentBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#keyPackageReplenishmentBody")
                })
            }
            is BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#keyPackageReplenishmentBody" -> BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion.KeyPackageReplenishmentBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsKeyPackageReplenishmentBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion {
    @Serializable
    data class DeviceAuthenticationRebindBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceAuthenticationRebindBody) : BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion
}

object BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion.DeviceAuthenticationRebindBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceAuthenticationRebindBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#deviceAuthenticationRebindBody")
                })
            }
            is BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#deviceAuthenticationRebindBody" -> BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion.DeviceAuthenticationRebindBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceAuthenticationRebindBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedDeviceRevocationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion {
    @Serializable
    data class DeviceRevocationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceRevocationBody) : BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion
}

object BlueCatbirdChatDefsSignedDeviceRevocationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion.DeviceRevocationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceRevocationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#deviceRevocationBody")
                })
            }
            is BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#deviceRevocationBody" -> BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion.DeviceRevocationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsDeviceRevocationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion {
    @Serializable
    data class BlobUploadPreparationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobUploadPreparationBody) : BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion
}

object BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion.BlobUploadPreparationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobUploadPreparationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#blobUploadPreparationBody")
                })
            }
            is BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#blobUploadPreparationBody" -> BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion.BlobUploadPreparationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobUploadPreparationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedBlobDeletionBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedBlobDeletionBodyUnion {
    @Serializable
    data class BlobDeletionBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobDeletionBody) : BlueCatbirdChatDefsSignedBlobDeletionBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedBlobDeletionBodyUnion
}

object BlueCatbirdChatDefsSignedBlobDeletionBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedBlobDeletionBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedBlobDeletionBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedBlobDeletionBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedBlobDeletionBodyUnion.BlobDeletionBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobDeletionBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#blobDeletionBody")
                })
            }
            is BlueCatbirdChatDefsSignedBlobDeletionBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedBlobDeletionBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#blobDeletionBody" -> BlueCatbirdChatDefsSignedBlobDeletionBodyUnion.BlobDeletionBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsBlobDeletionBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedBlobDeletionBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedCreationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedCreationBodyUnion {
    @Serializable
    data class CreationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationBody) : BlueCatbirdChatDefsSignedCreationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedCreationBodyUnion
}

object BlueCatbirdChatDefsSignedCreationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedCreationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedCreationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedCreationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedCreationBodyUnion.CreationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#creationBody")
                })
            }
            is BlueCatbirdChatDefsSignedCreationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedCreationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#creationBody" -> BlueCatbirdChatDefsSignedCreationBodyUnion.CreationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedCreationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedCommitTransitionBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedCommitTransitionBodyUnion {
    @Serializable
    data class CommitTransitionBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitTransitionBody) : BlueCatbirdChatDefsSignedCommitTransitionBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedCommitTransitionBodyUnion
}

object BlueCatbirdChatDefsSignedCommitTransitionBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedCommitTransitionBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedCommitTransitionBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedCommitTransitionBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedCommitTransitionBodyUnion.CommitTransitionBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitTransitionBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#commitTransitionBody")
                })
            }
            is BlueCatbirdChatDefsSignedCommitTransitionBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedCommitTransitionBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#commitTransitionBody" -> BlueCatbirdChatDefsSignedCommitTransitionBodyUnion.CommitTransitionBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitTransitionBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedCommitTransitionBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedPolicyTransitionBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion {
    @Serializable
    data class PolicyTransitionBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyTransitionBody) : BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion
}

object BlueCatbirdChatDefsSignedPolicyTransitionBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion.PolicyTransitionBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyTransitionBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#policyTransitionBody")
                })
            }
            is BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#policyTransitionBody" -> BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion.PolicyTransitionBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyTransitionBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion {
    @Serializable
    data class ParticipantAcceptanceBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceBody) : BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion
}

object BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion.ParticipantAcceptanceBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#participantAcceptanceBody")
                })
            }
            is BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#participantAcceptanceBody" -> BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion.ParticipantAcceptanceBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedApplicationSendBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedApplicationSendBodyUnion {
    @Serializable
    data class ApplicationSendBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationSendBody) : BlueCatbirdChatDefsSignedApplicationSendBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedApplicationSendBodyUnion
}

object BlueCatbirdChatDefsSignedApplicationSendBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedApplicationSendBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedApplicationSendBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedApplicationSendBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedApplicationSendBodyUnion.ApplicationSendBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationSendBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#applicationSendBody")
                })
            }
            is BlueCatbirdChatDefsSignedApplicationSendBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedApplicationSendBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#applicationSendBody" -> BlueCatbirdChatDefsSignedApplicationSendBodyUnion.ApplicationSendBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationSendBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedApplicationSendBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedTypingBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedTypingBodyUnion {
    @Serializable
    data class TypingBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingBody) : BlueCatbirdChatDefsSignedTypingBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedTypingBodyUnion
}

object BlueCatbirdChatDefsSignedTypingBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedTypingBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedTypingBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedTypingBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedTypingBodyUnion.TypingBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#typingBody")
                })
            }
            is BlueCatbirdChatDefsSignedTypingBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedTypingBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#typingBody" -> BlueCatbirdChatDefsSignedTypingBodyUnion.TypingBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedTypingBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedMetadataTransitionBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion {
    @Serializable
    data class MetadataTransitionBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataTransitionBody) : BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion
}

object BlueCatbirdChatDefsSignedMetadataTransitionBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion.MetadataTransitionBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataTransitionBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#metadataTransitionBody")
                })
            }
            is BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#metadataTransitionBody" -> BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion.MetadataTransitionBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataTransitionBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedResetRequestBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedResetRequestBodyUnion {
    @Serializable
    data class ResetRequestBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestBody) : BlueCatbirdChatDefsSignedResetRequestBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedResetRequestBodyUnion
}

object BlueCatbirdChatDefsSignedResetRequestBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedResetRequestBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedResetRequestBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedResetRequestBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedResetRequestBodyUnion.ResetRequestBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#resetRequestBody")
                })
            }
            is BlueCatbirdChatDefsSignedResetRequestBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedResetRequestBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#resetRequestBody" -> BlueCatbirdChatDefsSignedResetRequestBodyUnion.ResetRequestBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedResetRequestBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedResetActivationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedResetActivationBodyUnion {
    @Serializable
    data class ResetActivationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationBody) : BlueCatbirdChatDefsSignedResetActivationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedResetActivationBodyUnion
}

object BlueCatbirdChatDefsSignedResetActivationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedResetActivationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedResetActivationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedResetActivationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedResetActivationBodyUnion.ResetActivationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#resetActivationBody")
                })
            }
            is BlueCatbirdChatDefsSignedResetActivationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedResetActivationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#resetActivationBody" -> BlueCatbirdChatDefsSignedResetActivationBodyUnion.ResetActivationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedResetActivationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion {
    @Serializable
    data class LeafRecoveryRequestBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryRequestBody) : BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion
}

object BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion.LeafRecoveryRequestBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryRequestBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryRequestBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leafRecoveryRequestBody" -> BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion.LeafRecoveryRequestBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryRequestBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion {
    @Serializable
    data class LeafRecoveryCancellationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryCancellationBody) : BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion
}

object BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion.LeafRecoveryCancellationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryCancellationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryCancellationBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leafRecoveryCancellationBody" -> BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion.LeafRecoveryCancellationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryCancellationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion {
    @Serializable
    data class LeafRecoveryFulfillmentBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentBody) : BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion
}

object BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion.LeafRecoveryFulfillmentBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryFulfillmentBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leafRecoveryFulfillmentBody" -> BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion.LeafRecoveryFulfillmentBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedConversationCloseBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedConversationCloseBodyUnion {
    @Serializable
    data class ConversationCloseBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseBody) : BlueCatbirdChatDefsSignedConversationCloseBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedConversationCloseBodyUnion
}

object BlueCatbirdChatDefsSignedConversationCloseBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedConversationCloseBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedConversationCloseBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedConversationCloseBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedConversationCloseBodyUnion.ConversationCloseBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationCloseBody")
                })
            }
            is BlueCatbirdChatDefsSignedConversationCloseBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedConversationCloseBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#conversationCloseBody" -> BlueCatbirdChatDefsSignedConversationCloseBodyUnion.ConversationCloseBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedConversationCloseBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeaveRequestBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeaveRequestBodyUnion {
    @Serializable
    data class LeaveRequestBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestBody) : BlueCatbirdChatDefsSignedLeaveRequestBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeaveRequestBodyUnion
}

object BlueCatbirdChatDefsSignedLeaveRequestBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeaveRequestBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeaveRequestBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeaveRequestBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeaveRequestBodyUnion.LeaveRequestBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveRequestBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeaveRequestBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeaveRequestBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leaveRequestBody" -> BlueCatbirdChatDefsSignedLeaveRequestBodyUnion.LeaveRequestBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeaveRequestBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion {
    @Serializable
    data class ZeroLeafLeaveBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveBody) : BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion
}

object BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion.ZeroLeafLeaveBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#zeroLeafLeaveBody")
                })
            }
            is BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#zeroLeafLeaveBody" -> BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion.ZeroLeafLeaveBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeaveOperationSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeaveOperation {
    @Serializable
    data class SignedLeaveRequest(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveRequest) : BlueCatbirdChatDefsSignedLeaveOperation

    @Serializable
    data class SignedZeroLeafLeave(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedZeroLeafLeave) : BlueCatbirdChatDefsSignedLeaveOperation

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeaveOperation
}

object BlueCatbirdChatDefsSignedLeaveOperationSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeaveOperation> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeaveOperation")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeaveOperation) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeaveOperation.SignedLeaveRequest -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveRequest.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedLeaveRequest")
                })
            }
            is BlueCatbirdChatDefsSignedLeaveOperation.SignedZeroLeafLeave -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedZeroLeafLeave.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedZeroLeafLeave")
                })
            }
            is BlueCatbirdChatDefsSignedLeaveOperation.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeaveOperation {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#signedLeaveRequest" -> BlueCatbirdChatDefsSignedLeaveOperation.SignedLeaveRequest(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveRequest.serializer(), element)
            )
            "blue.catbird.chat.defs#signedZeroLeafLeave" -> BlueCatbirdChatDefsSignedLeaveOperation.SignedZeroLeafLeave(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedZeroLeafLeave.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeaveOperation.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeaveCancellationBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion {
    @Serializable
    data class LeaveCancellationBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationBody) : BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion
}

object BlueCatbirdChatDefsSignedLeaveCancellationBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion.LeaveCancellationBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveCancellationBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leaveCancellationBody" -> BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion.LeaveCancellationBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion {
    @Serializable
    data class LeaveCommitFulfillmentBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentBody) : BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion
}

object BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion.LeaveCommitFulfillmentBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveCommitFulfillmentBody")
                })
            }
            is BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#leaveCommitFulfillmentBody" -> BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion.LeaveCommitFulfillmentBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion {
    @Serializable
    data class WelcomeAcknowledgementBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAcknowledgementBody) : BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion
}

object BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion.WelcomeAcknowledgementBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAcknowledgementBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#welcomeAcknowledgementBody")
                })
            }
            is BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#welcomeAcknowledgementBody" -> BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion.WelcomeAcknowledgementBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAcknowledgementBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion {
    @Serializable
    data class WelcomeRejectionBody(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeRejectionBody) : BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion
}

object BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion.WelcomeRejectionBody -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeRejectionBody.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#welcomeRejectionBody")
                })
            }
            is BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#welcomeRejectionBody" -> BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion.WelcomeRejectionBody(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeRejectionBody.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSignedTransitionSerializer::class)
sealed interface BlueCatbirdChatDefsSignedTransition {
    @Serializable
    data class SignedCommitTransition(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedCommitTransition) : BlueCatbirdChatDefsSignedTransition

    @Serializable
    data class SignedPolicyTransition(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedPolicyTransition) : BlueCatbirdChatDefsSignedTransition

    @Serializable
    data class SignedLeafRecoveryFulfillment(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeafRecoveryFulfillment) : BlueCatbirdChatDefsSignedTransition

    @Serializable
    data class SignedMetadataTransition(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedMetadataTransition) : BlueCatbirdChatDefsSignedTransition

    @Serializable
    data class SignedLeaveCommitFulfillment(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveCommitFulfillment) : BlueCatbirdChatDefsSignedTransition

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSignedTransition
}

object BlueCatbirdChatDefsSignedTransitionSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSignedTransition> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSignedTransition")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSignedTransition) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSignedTransition.SignedCommitTransition -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedCommitTransition.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedCommitTransition")
                })
            }
            is BlueCatbirdChatDefsSignedTransition.SignedPolicyTransition -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedPolicyTransition.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedPolicyTransition")
                })
            }
            is BlueCatbirdChatDefsSignedTransition.SignedLeafRecoveryFulfillment -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeafRecoveryFulfillment.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedLeafRecoveryFulfillment")
                })
            }
            is BlueCatbirdChatDefsSignedTransition.SignedMetadataTransition -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedMetadataTransition.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedMetadataTransition")
                })
            }
            is BlueCatbirdChatDefsSignedTransition.SignedLeaveCommitFulfillment -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveCommitFulfillment.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#signedLeaveCommitFulfillment")
                })
            }
            is BlueCatbirdChatDefsSignedTransition.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSignedTransition {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#signedCommitTransition" -> BlueCatbirdChatDefsSignedTransition.SignedCommitTransition(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedCommitTransition.serializer(), element)
            )
            "blue.catbird.chat.defs#signedPolicyTransition" -> BlueCatbirdChatDefsSignedTransition.SignedPolicyTransition(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedPolicyTransition.serializer(), element)
            )
            "blue.catbird.chat.defs#signedLeafRecoveryFulfillment" -> BlueCatbirdChatDefsSignedTransition.SignedLeafRecoveryFulfillment(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeafRecoveryFulfillment.serializer(), element)
            )
            "blue.catbird.chat.defs#signedMetadataTransition" -> BlueCatbirdChatDefsSignedTransition.SignedMetadataTransition(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedMetadataTransition.serializer(), element)
            )
            "blue.catbird.chat.defs#signedLeaveCommitFulfillment" -> BlueCatbirdChatDefsSignedTransition.SignedLeaveCommitFulfillment(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsSignedLeaveCommitFulfillment.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSignedTransition.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsConversationInventoryItemSerializer::class)
sealed interface BlueCatbirdChatDefsConversationInventoryItem {
    @Serializable
    data class ConversationInventoryState(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationInventoryState) : BlueCatbirdChatDefsConversationInventoryItem

    @Serializable
    data class ConversationRemovalTombstone(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationRemovalTombstone) : BlueCatbirdChatDefsConversationInventoryItem

    @Serializable
    data class ConversationCloseTombstone(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseTombstone) : BlueCatbirdChatDefsConversationInventoryItem

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsConversationInventoryItem
}

object BlueCatbirdChatDefsConversationInventoryItemSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsConversationInventoryItem> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsConversationInventoryItem")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsConversationInventoryItem) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsConversationInventoryItem.ConversationInventoryState -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationInventoryState.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationInventoryState")
                })
            }
            is BlueCatbirdChatDefsConversationInventoryItem.ConversationRemovalTombstone -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationRemovalTombstone.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationRemovalTombstone")
                })
            }
            is BlueCatbirdChatDefsConversationInventoryItem.ConversationCloseTombstone -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseTombstone.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationCloseTombstone")
                })
            }
            is BlueCatbirdChatDefsConversationInventoryItem.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsConversationInventoryItem {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#conversationInventoryState" -> BlueCatbirdChatDefsConversationInventoryItem.ConversationInventoryState(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationInventoryState.serializer(), element)
            )
            "blue.catbird.chat.defs#conversationRemovalTombstone" -> BlueCatbirdChatDefsConversationInventoryItem.ConversationRemovalTombstone(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationRemovalTombstone.serializer(), element)
            )
            "blue.catbird.chat.defs#conversationCloseTombstone" -> BlueCatbirdChatDefsConversationInventoryItem.ConversationCloseTombstone(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseTombstone.serializer(), element)
            )
            else -> BlueCatbirdChatDefsConversationInventoryItem.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsConversationEntrySerializer::class)
sealed interface BlueCatbirdChatDefsConversationEntry {
    @Serializable
    data class ApplicationEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class CommitEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class PolicyEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class MetadataEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class CreationEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class ParticipantAcceptanceEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class ConversationCloseEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class ResetRequestEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class ResetActivationEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class LeafRecoveryFulfillmentEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class LeaveRequestEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class ZeroLeafLeaveEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class LeaveCancellationEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class LeaveCommitFulfillmentEntry(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentEntry) : BlueCatbirdChatDefsConversationEntry

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsConversationEntry
}

object BlueCatbirdChatDefsConversationEntrySerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsConversationEntry> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsConversationEntry")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsConversationEntry) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsConversationEntry.ApplicationEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#applicationEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.CommitEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#commitEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.PolicyEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#policyEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.MetadataEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#metadataEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.CreationEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#creationEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.ParticipantAcceptanceEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#participantAcceptanceEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.ConversationCloseEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationCloseEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.ResetRequestEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#resetRequestEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.ResetActivationEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#resetActivationEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.LeafRecoveryFulfillmentEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryFulfillmentEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.LeaveRequestEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveRequestEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.ZeroLeafLeaveEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#zeroLeafLeaveEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.LeaveCancellationEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveCancellationEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.LeaveCommitFulfillmentEntry -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentEntry.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveCommitFulfillmentEntry")
                })
            }
            is BlueCatbirdChatDefsConversationEntry.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsConversationEntry {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#applicationEntry" -> BlueCatbirdChatDefsConversationEntry.ApplicationEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsApplicationEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#commitEntry" -> BlueCatbirdChatDefsConversationEntry.CommitEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCommitEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#policyEntry" -> BlueCatbirdChatDefsConversationEntry.PolicyEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsPolicyEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#metadataEntry" -> BlueCatbirdChatDefsConversationEntry.MetadataEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMetadataEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#creationEntry" -> BlueCatbirdChatDefsConversationEntry.CreationEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsCreationEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#participantAcceptanceEntry" -> BlueCatbirdChatDefsConversationEntry.ParticipantAcceptanceEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsParticipantAcceptanceEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#conversationCloseEntry" -> BlueCatbirdChatDefsConversationEntry.ConversationCloseEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationCloseEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#resetRequestEntry" -> BlueCatbirdChatDefsConversationEntry.ResetRequestEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#resetActivationEntry" -> BlueCatbirdChatDefsConversationEntry.ResetActivationEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetActivationEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#leafRecoveryFulfillmentEntry" -> BlueCatbirdChatDefsConversationEntry.LeafRecoveryFulfillmentEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryFulfillmentEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#leaveRequestEntry" -> BlueCatbirdChatDefsConversationEntry.LeaveRequestEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#zeroLeafLeaveEntry" -> BlueCatbirdChatDefsConversationEntry.ZeroLeafLeaveEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsZeroLeafLeaveEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#leaveCancellationEntry" -> BlueCatbirdChatDefsConversationEntry.LeaveCancellationEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCancellationEntry.serializer(), element)
            )
            "blue.catbird.chat.defs#leaveCommitFulfillmentEntry" -> BlueCatbirdChatDefsConversationEntry.LeaveCommitFulfillmentEntry(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveCommitFulfillmentEntry.serializer(), element)
            )
            else -> BlueCatbirdChatDefsConversationEntry.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsProtocolEventPayloadSerializer::class)
sealed interface BlueCatbirdChatDefsProtocolEventPayload {
    @Serializable
    data class ConversationChangedEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationChangedEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class ConversationClosedEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationClosedEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class MessageAvailableEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageAvailableEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class WelcomeAvailableEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAvailableEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class WelcomeDispositionEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeDispositionEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class ResetRequestedEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestedEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class LeafRecoveryEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class LeaveRequestEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class AccessEndedEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsAccessEndedEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class WatermarkEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsWatermarkEvent) : BlueCatbirdChatDefsProtocolEventPayload

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsProtocolEventPayload
}

object BlueCatbirdChatDefsProtocolEventPayloadSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsProtocolEventPayload> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsProtocolEventPayload")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsProtocolEventPayload) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsProtocolEventPayload.ConversationChangedEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationChangedEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationChangedEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.ConversationClosedEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationClosedEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#conversationClosedEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.MessageAvailableEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageAvailableEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#messageAvailableEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.WelcomeAvailableEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAvailableEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#welcomeAvailableEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.WelcomeDispositionEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeDispositionEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#welcomeDispositionEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.ResetRequestedEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestedEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#resetRequestedEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.LeafRecoveryEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leafRecoveryEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.LeaveRequestEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#leaveRequestEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.AccessEndedEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAccessEndedEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#accessEndedEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.WatermarkEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWatermarkEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#watermarkEvent")
                })
            }
            is BlueCatbirdChatDefsProtocolEventPayload.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsProtocolEventPayload {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#conversationChangedEvent" -> BlueCatbirdChatDefsProtocolEventPayload.ConversationChangedEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationChangedEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#conversationClosedEvent" -> BlueCatbirdChatDefsProtocolEventPayload.ConversationClosedEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsConversationClosedEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#messageAvailableEvent" -> BlueCatbirdChatDefsProtocolEventPayload.MessageAvailableEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsMessageAvailableEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#welcomeAvailableEvent" -> BlueCatbirdChatDefsProtocolEventPayload.WelcomeAvailableEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeAvailableEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#welcomeDispositionEvent" -> BlueCatbirdChatDefsProtocolEventPayload.WelcomeDispositionEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWelcomeDispositionEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#resetRequestedEvent" -> BlueCatbirdChatDefsProtocolEventPayload.ResetRequestedEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsResetRequestedEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#leafRecoveryEvent" -> BlueCatbirdChatDefsProtocolEventPayload.LeafRecoveryEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeafRecoveryEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#leaveRequestEvent" -> BlueCatbirdChatDefsProtocolEventPayload.LeaveRequestEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsLeaveRequestEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#accessEndedEvent" -> BlueCatbirdChatDefsProtocolEventPayload.AccessEndedEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsAccessEndedEvent.serializer(), element)
            )
            "blue.catbird.chat.defs#watermarkEvent" -> BlueCatbirdChatDefsProtocolEventPayload.WatermarkEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsWatermarkEvent.serializer(), element)
            )
            else -> BlueCatbirdChatDefsProtocolEventPayload.Unexpected(element)
        }
    }
}

@Serializable(with = BlueCatbirdChatDefsSubscriptionMessageSerializer::class)
sealed interface BlueCatbirdChatDefsSubscriptionMessage {
    @Serializable
    data class EventEnvelope(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsEventEnvelope) : BlueCatbirdChatDefsSubscriptionMessage

    @Serializable
    data class TypingEvent(val value: blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingEvent) : BlueCatbirdChatDefsSubscriptionMessage

    @Serializable
    data class Unexpected(val value: JsonElement) : BlueCatbirdChatDefsSubscriptionMessage
}

object BlueCatbirdChatDefsSubscriptionMessageSerializer : kotlinx.serialization.KSerializer<BlueCatbirdChatDefsSubscriptionMessage> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("BlueCatbirdChatDefsSubscriptionMessage")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BlueCatbirdChatDefsSubscriptionMessage) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is BlueCatbirdChatDefsSubscriptionMessage.EventEnvelope -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEventEnvelope.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#eventEnvelope")
                })
            }
            is BlueCatbirdChatDefsSubscriptionMessage.TypingEvent -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingEvent.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("blue.catbird.chat.defs#typingEvent")
                })
            }
            is BlueCatbirdChatDefsSubscriptionMessage.Unexpected -> value.value
            // Synthetic variants (e.g. <Union>Error / <Union>Unexpected added by
            // subscription codegen) are runtime-only sentinels; JSON round-trip
            // serialises them as an empty object tagged with the variant class
            // name. Consumers should filter these before JSON serialisation.
            else -> kotlinx.serialization.json.buildJsonObject {
                put("\$type", kotlinx.serialization.json.JsonPrimitive(value::class.simpleName ?: "Unknown"))
            }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): BlueCatbirdChatDefsSubscriptionMessage {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "blue.catbird.chat.defs#eventEnvelope" -> BlueCatbirdChatDefsSubscriptionMessage.EventEnvelope(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsEventEnvelope.serializer(), element)
            )
            "blue.catbird.chat.defs#typingEvent" -> BlueCatbirdChatDefsSubscriptionMessage.TypingEvent(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.BlueCatbirdChatDefsTypingEvent.serializer(), element)
            )
            else -> BlueCatbirdChatDefsSubscriptionMessage.Unexpected(element)
        }
    }
}

@Serializable
enum class BlueCatbirdChatDefsDefsProtocolVersion {
    @SerialName("1")
    value_1}

@Serializable
enum class BlueCatbirdChatDefsDefsCipherSuite {
    @SerialName("MLS_256_XWING_CHACHA20POLY1305_SHA256_Ed25519")
    value_MLS_u5f_256_u5f_XWING_u5f_CHACHA20POLY1305_u5f_SHA256_u5f_Ed25519}

@Serializable
enum class BlueCatbirdChatDefsDefsLifecycle {
    @SerialName("active")
    value_active,
    @SerialName("superseded")
    value_superseded}

@Serializable
enum class BlueCatbirdChatDefsDefsConversationKind {
    @SerialName("direct")
    value_direct,
    @SerialName("group")
    value_group}

@Serializable
enum class BlueCatbirdChatDefsDefsMemberRole {
    @SerialName("admin")
    value_admin,
    @SerialName("member")
    value_member}

@Serializable
enum class BlueCatbirdChatDefsDefsParticipantStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("active")
    value_active}

@Serializable
enum class BlueCatbirdChatDefsDefsIncomingConsentPolicy {
    @SerialName("all")
    value_all,
    @SerialName("none")
    value_none,
    @SerialName("following")
    value_following}

@Serializable
enum class BlueCatbirdChatDefsDefsDeviceStatus {
    @SerialName("active")
    value_active,
    @SerialName("revoked")
    value_revoked}

@Serializable
enum class BlueCatbirdChatDefsDefsPackageStatus {
    @SerialName("available")
    value_available,
    @SerialName("reserved")
    value_reserved,
    @SerialName("consumed")
    value_consumed,
    @SerialName("expired")
    value_expired,
    @SerialName("revoked")
    value_revoked}

@Serializable
enum class BlueCatbirdChatDefsDefsReservationStatus {
    @SerialName("active")
    value_active,
    @SerialName("consumed")
    value_consumed,
    @SerialName("expired")
    value_expired,
    @SerialName("released")
    value_released}

@Serializable
enum class BlueCatbirdChatDefsDefsWelcomeStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("acknowledged")
    value_acknowledged,
    @SerialName("rejected")
    value_rejected,
    @SerialName("expired")
    value_expired,
    @SerialName("superseded")
    value_superseded}

@Serializable
enum class BlueCatbirdChatDefsDefsWelcomeRejectionReason {
    @SerialName("noMatchingKeyPackage")
    value_noMatchingKeyPackage,
    @SerialName("invalidWelcome")
    value_invalidWelcome,
    @SerialName("unsupportedCipherSuite")
    value_unsupportedCipherSuite,
    @SerialName("coordinateMismatch")
    value_coordinateMismatch,
    @SerialName("localStateConflict")
    value_localStateConflict}

@Serializable
enum class BlueCatbirdChatDefsDefsLeafRecoveryStatus {
    @SerialName("open")
    value_open,
    @SerialName("fulfilled")
    value_fulfilled,
    @SerialName("cancelled")
    value_cancelled,
    @SerialName("expired")
    value_expired,
    @SerialName("superseded")
    value_superseded}

@Serializable
enum class BlueCatbirdChatDefsDefsRecoveryWorkSourceKind {
    @SerialName("welcomeExpired")
    value_welcomeExpired,
    @SerialName("welcomeRejected")
    value_welcomeRejected}

@Serializable
enum class BlueCatbirdChatDefsDefsRecoveryWorkStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("completed")
    value_completed,
    @SerialName("superseded")
    value_superseded}

@Serializable
enum class BlueCatbirdChatDefsDefsLeaveRequestStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("fulfilled")
    value_fulfilled,
    @SerialName("cancelled")
    value_cancelled,
    @SerialName("expired")
    value_expired,
    @SerialName("stale")
    value_stale}

@Serializable
enum class BlueCatbirdChatDefsDefsLeafRecoveryKind {
    @SerialName("add")
    value_add,
    @SerialName("replace")
    value_replace}

@Serializable
enum class BlueCatbirdChatDefsDefsLeafOrigin {
    @SerialName("genesis")
    value_genesis,
    @SerialName("keyPackage")
    value_keyPackage}

@Serializable
enum class BlueCatbirdChatDefsDefsResetReason {
    @SerialName("localStateLost")
    value_localStateLost,
    @SerialName("poisonedState")
    value_poisonedState,
    @SerialName("epochDivergence")
    value_epochDivergence,
    @SerialName("manualRecovery")
    value_manualRecovery}

@Serializable
enum class BlueCatbirdChatDefsDefsBlobPurpose {
    @SerialName("attachment")
    value_attachment,
    @SerialName("metadata")
    value_metadata}

@Serializable
enum class BlueCatbirdChatDefsEncryptedImageEmbedMimeType {
    @SerialName("image/heic")
    value_image_u2f_heic,
    @SerialName("image/jpeg")
    value_image_u2f_jpeg,
    @SerialName("image/png")
    value_image_u2f_png,
    @SerialName("image/webp")
    value_image_u2f_webp,
    @SerialName("image/gif")
    value_image_u2f_gif}

@Serializable
enum class BlueCatbirdChatDefsEncryptedAudioEmbedMimeType {
    @SerialName("audio/aac")
    value_audio_u2f_aac,
    @SerialName("audio/mp4")
    value_audio_u2f_mp4,
    @SerialName("audio/ogg")
    value_audio_u2f_ogg,
    @SerialName("audio/opus")
    value_audio_u2f_opus}

@Serializable
enum class BlueCatbirdChatDefsReactionFrameBodyOperation {
    @SerialName("add")
    value_add,
    @SerialName("remove")
    value_remove}

@Serializable
enum class BlueCatbirdChatDefsMetadataAvatarEmbedMimeType {
    @SerialName("image/heic")
    value_image_u2f_heic,
    @SerialName("image/jpeg")
    value_image_u2f_jpeg,
    @SerialName("image/png")
    value_image_u2f_png,
    @SerialName("image/webp")
    value_image_u2f_webp}

@Serializable
enum class BlueCatbirdChatDefsResetRequestViewStatus {
    @SerialName("pending")
    value_pending,
    @SerialName("stale")
    value_stale,
    @SerialName("consumed")
    value_consumed,
    @SerialName("expired")
    value_expired,
    @SerialName("revoked")
    value_revoked}

    /**
     * Exact UTC grammar YYYY-MM-DDTHH:MM:SS.sssZ; offsets, lowercase, leap seconds, and variable fractions are invalid.
     */
    typealias BlueCatbirdChatDefsCanonicalDatetime = ATProtocolDate

    /**
     * Canonical lowercase hyphenated UUIDv4 with RFC 4122 variant.
     */
    typealias BlueCatbirdChatDefsDeviceId = String

    /**
     * Canonical lowercase hyphenated UUIDv4 for every client-generated conversation, transition, reset, recovery, Welcome, message, blob, and idempotency identifier.
     */
    typealias BlueCatbirdChatDefsOperationId = String

    /**
     * Production ATProto bare DID only: did:plc followed by exactly 24 lowercase base32 [a-z2-7] characters, or hostname-level did:web followed by a normalized lowercase handle-shaped ASCII hostname of 1-253 bytes with at least two dot-separated labels, each 1-63 bytes, alphanumeric endpoints, and a nonnumeric-leading TLD. Production rejects TLDs alt, arpa, example, internal, invalid, local, localhost, onion, and test. did:web path DIDs, ports, percent escapes, IP literals, single-label names including localhost, trailing dots, queries, fragments, Unicode hostnames, and other methods are forbidden. The exact semantic length is 12-261 bytes; actorDid#deviceId BasicCredential identity is 49-298 bytes. Equality with token DID, BasicCredential identity, signer, roster, and ordering uses exact UTF-8 bytes.
     */
    typealias BlueCatbirdChatDefsBareDid = DID

    /**
     * Base64url without padding SHA-256 thumbprint of the exact raw 32-byte Ed25519 public key.
     */
    typealias BlueCatbirdChatDefsKeyId = String

    @Serializable
    data class BlueCatbirdChatDefsConversationCoordinates(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("generation")
        val generation: Int,        @SerialName("stateVersion")
        val stateVersion: Int,        @SerialName("groupId")
        val groupId: Bytes,        @SerialName("epoch")
        val epoch: Int,        @SerialName("groupContextHash")
        val groupContextHash: Bytes,        @SerialName("confirmationTag")
        val confirmationTag: Bytes,        @SerialName("lifecycle")
        val lifecycle: BlueCatbirdChatDefsDefsLifecycle    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCoordinates"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsApplicationFrameContext(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("stateVersion")
        val stateVersion: Int,        @SerialName("groupId")
        val groupId: Bytes,        @SerialName("epoch")
        val epoch: Int,        @SerialName("groupContextHash")
        val groupContextHash: Bytes,        @SerialName("confirmationTag")
        val confirmationTag: Bytes,        @SerialName("lifecycle")
        val lifecycle: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationFrameContext"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMetadataCryptoContext(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("groupId")
        val groupId: Bytes,        @SerialName("epoch")
        val epoch: Int,        @SerialName("groupContextHash")
        val groupContextHash: Bytes,        @SerialName("confirmationTag")
        val confirmationTag: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataCryptoContext"
        }
    }

    /**
     * Internal canonical DAG-CBOR projection. UUID transport text is converted to exact 16 bytes and must equal the separately signed outer conversationCoordinates field-for-field.
     */
    @Serializable
    data class BlueCatbirdChatDefsMlsAadPriorContext(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("stateVersion")
        val stateVersion: Int,        @SerialName("groupId")
        val groupId: Bytes,        @SerialName("epoch")
        val epoch: Int,        @SerialName("groupContextHash")
        val groupContextHash: Bytes,        @SerialName("confirmationTag")
        val confirmationTag: Bytes,        @SerialName("lifecycle")
        val lifecycle: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMlsAadPriorContext"
        }
    }

    typealias BlueCatbirdChatDefsArtifactHash = Bytes

    /**
     * Exactly one MLS 1.0 MLSMessage wire-format 5 wrapper. KeyPackageRef is RefHash over the exact inner KeyPackage TLS bytes, never the wrapper or a plain SHA-256.
     */
    @Serializable
    data class BlueCatbirdChatDefsKeyPackageArtifact(
        @SerialName("framing")
        val framing: String,        @SerialName("contentType")
        val contentType: String,        @SerialName("bytes")
        val bytes: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("keyPackageRef")
        val keyPackageRef: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsKeyPackageArtifact"
        }
    }

    /**
     * Exactly one MLS 1.0 MLSMessage public Commit. The server exact-parses, canonical-reencodes, and structurally validates suite-specific XWing update-path ciphertext, but structural validity does not prove that any recipient can decrypt deliberately bogus path ciphertext.
     */
    @Serializable
    data class BlueCatbirdChatDefsPublicCommit(
        @SerialName("framing")
        val framing: String,        @SerialName("contentType")
        val contentType: String,        @SerialName("bytes")
        val bytes: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsPublicCommit"
        }
    }

    /**
     * Exactly one MLS 1.0 MLSMessage wire-format 4 wrapper. OpenMLS 0.8.1 GroupInfo extensions are frozen to exactly ratchet_tree followed by external_pub; every other GroupInfo extension is rejected. external_pub presence does not authorize external commits, which protocol policy forbids.
     */
    @Serializable
    data class BlueCatbirdChatDefsGroupInfoArtifact(
        @SerialName("framing")
        val framing: String,        @SerialName("contentType")
        val contentType: String,        @SerialName("bytes")
        val bytes: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsGroupInfoArtifact"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsPrivateApplicationMessage(
        @SerialName("framing")
        val framing: String,        @SerialName("contentType")
        val contentType: String,        @SerialName("bytes")
        val bytes: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsPrivateApplicationMessage"
        }
    }

    /**
     * Protocol v1 exact capability profile: suite 0x004D, BasicCredential, by-value Add/Remove and ordinary sender Update path. KeyPackage, LeafNode, and GroupContext extensions are empty; OpenMLS 0.8.1 GroupInfo has exactly [ratchet_tree, external_pub]. External commits remain forbidden by protocol policy.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceCapability(
        @SerialName("protocolVersion")
        val protocolVersion: BlueCatbirdChatDefsDefsProtocolVersion,        @SerialName("mlsVersion")
        val mlsVersion: String,        @SerialName("cipherSuite")
        val cipherSuite: BlueCatbirdChatDefsDefsCipherSuite,        @SerialName("credentialType")
        val credentialType: String,        @SerialName("addByValue")
        val addByValue: String,        @SerialName("updatePath")
        val updatePath: String,        @SerialName("removeByValue")
        val removeByValue: String,        @SerialName("ratchetTreeGroupInfo")
        val ratchetTreeGroupInfo: String,        @SerialName("externalPubGroupInfo")
        val externalPubGroupInfo: String,        @SerialName("applicationFrameProfile")
        val applicationFrameProfile: String,        @SerialName("controlProfile")
        val controlProfile: String,        @SerialName("attachmentProfile")
        val attachmentProfile: String,        @SerialName("metadataProfile")
        val metadataProfile: String,        @SerialName("typingProfile")
        val typingProfile: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceCapability"
        }
    }

    /**
     * availablePackageCount plus reservedPackageCount is at most 1000 live nonterminal KeyPackages for this device.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceView(
        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("signaturePublicKey")
        val signaturePublicKey: Bytes,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("dpopJkt")
        val dpopJkt: String,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsDeviceStatus,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("updatedAt")
        val updatedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("availablePackageCount")
        val availablePackageCount: Int,        @SerialName("reservedPackageCount")
        val reservedPackageCount: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceView"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsOwnDeviceView(
        @SerialName("device")
        val device: BlueCatbirdChatDefsDeviceView    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsOwnDeviceView"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsAddressableDevice(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("capability")
        val capability: BlueCatbirdChatDefsDeviceCapability,        @SerialName("availablePackageCount")
        val availablePackageCount: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAddressableDevice"
        }
    }

    /**
     * Exact immutable provenance of the creation or admin policy transition that inserted this pending participant. The transition ID and inviter device must equal that signed transition; acceptance repeats this object byte-for-byte.
     */
    @Serializable
    data class BlueCatbirdChatDefsInvitationProvenance(
        @SerialName("invitationTransitionId")
        val invitationTransitionId: BlueCatbirdChatDefsOperationId,        @SerialName("invitedByDid")
        val invitedByDid: BlueCatbirdChatDefsBareDid,        @SerialName("invitedByDeviceId")
        val invitedByDeviceId: BlueCatbirdChatDefsDeviceId    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsInvitationProvenance"
        }
    }

    /**
     * The creator is active/admin and has no invitationProvenance. A group invitation is pending/member/zero-leaf. The one direct invitee is pending/admin/zero-leaf, but pending status grants no authority; acceptance changes only status to active and preserves role and provenance. Every pending participant requires immutable invitationProvenance and can never be an MLS leaf.
     */
    @Serializable
    data class BlueCatbirdChatDefsParticipant(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("role")
        val role: BlueCatbirdChatDefsDefsMemberRole,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsParticipantStatus,        @SerialName("invitationProvenance")
        val invitationProvenance: BlueCatbirdChatDefsInvitationProvenance? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsParticipant"
        }
    }

    /**
     * Pending participants have leafCount zero and immutable invitationProvenance. A group pending participant is member; the sole direct pending invitee is admin but has no authority until active. The active creator lacks provenance; accepted participants retain it for audit and exact replay.
     */
    @Serializable
    data class BlueCatbirdChatDefsParticipantView(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("role")
        val role: BlueCatbirdChatDefsDefsMemberRole,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsParticipantStatus,        @SerialName("invitationProvenance")
        val invitationProvenance: BlueCatbirdChatDefsInvitationProvenance? = null,        @SerialName("leafCount")
        val leafCount: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsParticipantView"
        }
    }

    /**
     * genesis is valid only for the actor/activator's epoch-zero leaf and forbids joinKeyPackageRef. Its LeafNode lifetime is validated at the same captured Unix second T as a KeyPackage: not_before < T < not_after, at least 600 seconds remain, and total lifetime is at most 2595600 seconds. keyPackage requires joinKeyPackageRef from the consumed Add package. Epoch zero never invents or consumes a KeyPackageRef.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceLeaf(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("leafOrigin")
        val leafOrigin: BlueCatbirdChatDefsDefsLeafOrigin,        @SerialName("joinKeyPackageRef")
        val joinKeyPackageRef: Bytes? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceLeaf"
        }
    }

    /**
     * joinKeyPackageRef is absent for genesis and required for keyPackage origin.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceLeafView(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("leafOrigin")
        val leafOrigin: BlueCatbirdChatDefsDefsLeafOrigin,        @SerialName("joinKeyPackageRef")
        val joinKeyPackageRef: Bytes? = null,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("deviceStatus")
        val deviceStatus: BlueCatbirdChatDefsDefsDeviceStatus    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceLeafView"
        }
    }

    /**
     * Group-only admin invitation. It creates only pending/member/zero-leaf state; provenance must name this exact policy transition and signing inviter. It can never share a transition with an Add leaf. Direct conversations forbid addParticipant after creation.
     */
    @Serializable
    data class BlueCatbirdChatDefsAddParticipant(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("role")
        val role: String,        @SerialName("status")
        val status: String,        @SerialName("invitationProvenance")
        val invitationProvenance: BlueCatbirdChatDefsInvitationProvenance    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAddParticipant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsRemoveParticipant(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRemoveParticipant"
        }
    }

    /**
     * Group-only role change for an active participant. Pending group participants remain members; direct conversations forbid every role mutation.
     */
    @Serializable
    data class BlueCatbirdChatDefsChangeParticipantRole(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("role")
        val role: BlueCatbirdChatDefsDefsMemberRole    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsChangeParticipantRole"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsAddLeafByRecovery(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("keyPackageRef")
        val keyPackageRef: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAddLeafByRecovery"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsRemoveLeaf(
        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRemoveLeaf"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsRecoveryWelcomeProvenance(
        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("keyPackageRef")
        val keyPackageRef: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRecoveryWelcomeProvenance"
        }
    }

    /**
     * Provenance exactly equals the addLeafByRecovery recoveryRequestId and its target-device-signed request-bound KeyPackageRef. A mismatched target, request, or package is invalid.
     */
    @Serializable
    data class BlueCatbirdChatDefsWelcomeDelivery(
        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("provenance")
        val provenance: BlueCatbirdChatDefsRecoveryWelcomeProvenance    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeDelivery"
        }
    }

    /**
     * Version 1 has exactly one delivery for the one target-device-signed recovery request fulfilled by this Add Commit. The row maps that added leaf to its exact recovery provenance.
     */
    @Serializable
    data class BlueCatbirdChatDefsWelcomeBundle(
        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("framing")
        val framing: String,        @SerialName("contentType")
        val contentType: String,        @SerialName("opaqueWelcome")
        val opaqueWelcome: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("deliveries")
        val deliveries: List<BlueCatbirdChatDefsWelcomeDelivery>    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeBundle"
        }
    }

    /**
     * One immutable recipient row is retained for every Welcome delivery. expiresAt is the canonical UTC datetime representation of the exact consumed Add KeyPackage not_after Unix second and therefore has .000Z fractional seconds. At that instant a still-pending row atomically becomes expired and creates recovery work; its encrypted Welcome bytes, provenance, historical coordinate, transition seq, recipient, and terminal disposition are never deleted or rewritten. Acknowledge/reject/expiry/supersession use one compare-and-set terminal race. Exact delayed idempotent replay returns the recorded disposition, while changed bytes conflict and a delayed acknowledge/reject cannot overturn a terminal result.
     */
    @Serializable
    data class BlueCatbirdChatDefsWelcomeView(
        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionSeq")
        val transitionSeq: Int,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsWelcomeStatus,        @SerialName("opaqueWelcome")
        val opaqueWelcome: Bytes,        @SerialName("sha256")
        val sha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("provenance")
        val provenance: BlueCatbirdChatDefsRecoveryWelcomeProvenance,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeView"
        }
    }

    /**
     * Exact deterministic DAG-CBOR projection prefixed by CATBIRD-CHAT-MLS-AAD-COMMIT\0. All UUIDs are raw 16-byte strings and prior is the active bytes-based context exactly equivalent to the outer signed transport coordinate; it never includes the enclosing MLSMessage hash.
     */
    @Serializable
    data class BlueCatbirdChatDefsCommitAad(
        @SerialName("protocolVersion")
        val protocolVersion: BlueCatbirdChatDefsDefsProtocolVersion,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsMlsAadPriorContext    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCommitAad"
        }
    }

    /**
     * Exact deterministic DAG-CBOR projection prefixed by CATBIRD-CHAT-MLS-AAD-MESSAGE\0. All UUIDs are raw 16-byte strings and prior is the active bytes-based context exactly equivalent to the outer signed transport coordinate; it never includes the enclosing MLSMessage hash.
     */
    @Serializable
    data class BlueCatbirdChatDefsApplicationAad(
        @SerialName("protocolVersion")
        val protocolVersion: BlueCatbirdChatDefsDefsProtocolVersion,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("messageId")
        val messageId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsMlsAadPriorContext    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationAad"
        }
    }

    /**
     * participantChanges are strictly increasing and duplicate-free by affected userDid exact UTF-8 bytes; leafChanges are strictly increasing and duplicate-free by (userDid exact UTF-8 bytes, deviceId raw UUID bytes, operation rank). Operation rank is removeLeaf before addLeafByRecovery for the same (userDid, deviceId). addParticipant is policy-only and can never coexist with an Add leaf; every Add DID must already be active in prior state. Direct conversations require participantChanges empty forever. Every Add is exactly one addLeafByRecovery refining an open target-device-signed request; leafRecoveryRequestId and a one-delivery welcomeBundle are required and match that Add, request-bound KeyPackageRef, and recovery Welcome provenance exactly. No admin or current leaf can reserve or Add another device without that target device's signed request. Removes never have deliveries. Under the conversation mutation lock the server rechecks block policy for every unordered pair in the resulting roster immediately before the final Add. Inputs are rejected rather than sorted.
     */
    @Serializable
    data class BlueCatbirdChatDefsTransitionManifest(
        @SerialName("participantChanges")
        val participantChanges: List<BlueCatbirdChatDefsParticipantChange>,        @SerialName("leafChanges")
        val leafChanges: List<BlueCatbirdChatDefsLeafChange>,        @SerialName("welcomeBundle")
        val welcomeBundle: BlueCatbirdChatDefsWelcomeBundle? = null,        @SerialName("leafRecoveryRequestId")
        val leafRecoveryRequestId: BlueCatbirdChatDefsOperationId? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsTransitionManifest"
        }
    }

    /**
     * participants are strictly increasing and duplicate-free by userDid exact UTF-8 bytes. The signing creator appears exactly once as active/admin with no invitation provenance; actorLeaf matches that creator and is the only MLS leaf at epoch zero. For group, every other participant is pending/member/zero-leaf. For direct, the roster is exactly the creator plus one pending/admin/zero-leaf invitee; pending status grants that invitee no authority. Every pending row has provenance naming this creation transition and signer. Pending invitations are the only consent request and carry no server-readable message. Inputs are rejected rather than sorted.
     */
    @Serializable
    data class BlueCatbirdChatDefsCreationManifest(
        @SerialName("participants")
        val participants: List<BlueCatbirdChatDefsParticipant>,        @SerialName("actorLeaf")
        val actorLeaf: BlueCatbirdChatDefsDeviceLeaf    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCreationManifest"
        }
    }

    /**
     * participants are strictly increasing and duplicate-free by userDid exact UTF-8 bytes and preserve immutable conversationKind plus every prior status, role, and invitation provenance exactly. The signing activator is an active admin, actorLeaf matches it, and it is the only successor MLS leaf at epoch zero. Every other participant has zero successor leaves; only active participants may later be re-added by ordinary recovery/membership Commits. A direct roster remains its exact two admin-role participants. Inputs are rejected rather than sorted.
     */
    @Serializable
    data class BlueCatbirdChatDefsResetActivationManifest(
        @SerialName("participants")
        val participants: List<BlueCatbirdChatDefsParticipant>,        @SerialName("actorLeaf")
        val actorLeaf: BlueCatbirdChatDefsDeviceLeaf    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetActivationManifest"
        }
    }

    /**
     * Dedicated recovery reservation bound to the requester's signed recoveryRequestId, recoveryKind, identity, and boundCoordinate. For requestLeafRecovery, boundCoordinate equals the signed prior. For acceptConversation, it equals the exact post-acceptance next coordinate, never prior. add requires requesterDeviceId to be absent from the bound MLS tree. replace requires requesterDeviceId to identify its exact existing bound leaf; fulfillment must remove and re-add that same (requesterDid, requesterDeviceId), consuming this package. It has no future transitionId or aliased/invented reservation ID; the future fulfiller independently signs transitionId plus recoveryRequestId.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryReservation(
        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("boundCoordinate")
        val boundCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("requesterDid")
        val requesterDid: BlueCatbirdChatDefsBareDid,        @SerialName("requesterDeviceId")
        val requesterDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("requesterKeyId")
        val requesterKeyId: BlueCatbirdChatDefsKeyId,        @SerialName("requesterAuthGeneration")
        val requesterAuthGeneration: Int,        @SerialName("keyPackageRef")
        val keyPackageRef: Bytes,        @SerialName("cipherSuite")
        val cipherSuite: BlueCatbirdChatDefsDefsCipherSuite,        @SerialName("purpose")
        val purpose: String,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsReservationStatus,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("keyPackage")
        val keyPackage: BlueCatbirdChatDefsKeyPackageArtifact    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryReservation"
        }
    }

    /**
     * Upload completion reflects the exact prepared attachment or metadata purpose. Signed application and metadata projections must use their narrower purpose-constant binding types rather than this transport result type.
     */
    @Serializable
    data class BlueCatbirdChatDefsUploadedBlobBinding(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: BlueCatbirdChatDefsDefsBlobPurpose    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsUploadedBlobBinding"
        }
    }

    /**
     * Closed signed-application binding. The purpose is exactly attachment; a metadata-purpose upload cannot inhabit applicationSendBody.
     */
    @Serializable
    data class BlueCatbirdChatDefsApplicationAttachmentBinding(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationAttachmentBinding"
        }
    }

    /**
     * Closed metadata-snapshot avatar binding. The purpose is exactly metadata; an attachment-purpose upload cannot inhabit metadataSnapshot.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataAvatarBinding(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAvatarBinding"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsBlobUploadView(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("uploadTicket")
        val uploadTicket: String,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: BlueCatbirdChatDefsDefsBlobPurpose,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsBlobUploadView"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsBlobUsageView(
        @SerialName("usedBytes")
        val usedBytes: Int,        @SerialName("reservedBytes")
        val reservedBytes: Int,        @SerialName("quotaBytes")
        val quotaBytes: Int,        @SerialName("blobCount")
        val blobCount: Int,        @SerialName("liveUnboundCount")
        val liveUnboundCount: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsBlobUsageView"
        }
    }

    /**
     * First enrollment requires a one-use at-most-120-second Nest fresh-auth grant from an enrollment-purpose-bound OAuth authorization_code flow. Nest creates evidence only after successful callback/code exchange and issuer, subject, scope, and DPoP validation; restore, refresh, cookie exchange, or an existing session alone never creates evidence. Callback completion opens one encrypted capability through auth_time + 300 seconds, with auth_time equal to Nest callback-completion time and not upstream auth_time. Capability states are unpinned, pinned/pending, and terminal-success. Before pinning, Nest performs strict canonical decode, bounds, and capability/body binding checks and verifies the body's Ed25519 signature under its supplied immutable signing key. A malformed, out-of-bounds, binding-invalid, or signature-invalid attempt neither pins nor burns the capability. The first body that passes all of those checks transitions unpinned to pinned/pending and atomically pins the exact canonical request digest, separate signature, DID, device ID, DPoP JKT, key ID, signing-key digest, and enrollment-transcript digest. While pinned/pending and Nest has not durably recorded downstream success, including ambiguous response loss after delivery-service commit, the same exact body may mint another downstream grant. Each such attempt retains original auth_time but gets fresh token/proof JTIs and a server-generated per-grant canonical lowercase UUIDv4 auth_txn distinct from provider state and client input; delivery-service exact idempotent replay returns its stored result. Changed body cannot reuse the capability. Once Nest durably records success it stores the terminal result/binding, transitions to terminal-success, and closes the capability; exact client retry is then answered from that Nest-stored terminal result without a new downstream grant. Expiry before terminal success requires a new code flow. Besides common claims iss, sub, aud, lxm, iat, exp, jti, cnf.jkt, device_id, and chat_instance, exact additional claims are key_id, signing_key_sha256, enrollment_transcript_sha256, auth_time, and auth_txn. Enrollment grant exp = min(iat + 120, auth_time + 300) using checked NumericDate arithmetic; ordinary tokens require exp <= iat + 120. The delivery service independently accepts the Nest issuer attestation only when 0 <= T-auth_time <= 300 seconds. prompt=login and an ephemeral browser are best-effort only; neither is a security predicate, fresh authorization-code completion does not attest credential entry or user presence, and the protocol does not claim user reauthentication. Subject, endpoint, device ID, key ID, SHA-256(signaturePublicKey), dpopJkt through cnf.jkt, and raw SHA-256 of this exact canonical signing transcript must match; grant cnf.jkt, proof RFC7638 JKT, and body dpopJkt are byte-equal before device lookup. A generic bearer/session token is invalid. keyPackages are strictly increasing and duplicate-free by computed raw KeyPackageRef. Every package lifetime is checked against one captured Unix second T: not_before < T < not_after, not_after - T >= 600, and not_after - not_before <= 2595600, with checked arithmetic and underflow rejection. The whole batch is rejected rather than sorted.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceEnrollmentBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("deviceName")
        val deviceName: String,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("signaturePublicKey")
        val signaturePublicKey: Bytes,        @SerialName("dpopJkt")
        val dpopJkt: String,        @SerialName("expectedAuthGeneration")
        val expectedAuthGeneration: Int,        @SerialName("capability")
        val capability: BlueCatbirdChatDefsDeviceCapability,        @SerialName("keyPackages")
        val keyPackages: List<BlueCatbirdChatDefsKeyPackageArtifact>,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceEnrollmentBody"
        }
    }

    /**
     * keyPackages are strictly increasing and duplicate-free by computed raw KeyPackageRef. Every package lifetime is checked against one captured Unix second T: not_before < T < not_after, not_after - T >= 600, and not_after - not_before <= 2595600, with checked arithmetic and underflow rejection. The whole batch is rejected rather than sorted.
     */
    @Serializable
    data class BlueCatbirdChatDefsKeyPackageReplenishmentBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("dpopJkt")
        val dpopJkt: String,        @SerialName("signaturePublicKey")
        val signaturePublicKey: Bytes,        @SerialName("keyPackages")
        val keyPackages: List<BlueCatbirdChatDefsKeyPackageArtifact>,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsKeyPackageReplenishmentBody"
        }
    }

    /**
     * Rebind is an explicit bootstrap exception for loss of the old DPoP key. First execution uses a valid Nest token/proof bound to newDpopJkt, looks up exact (actorDid,actorDeviceId), CASes signed currentDpopJkt plus expectedAuthGeneration, verifies this body with the immutable stored Ed25519 key, then installs proofJKT = newDpopJkt and increments generation. If the Ed25519 key is lost, enroll a new device and revoke the old. Exact completed replay requires the stored transcript digest/signature and fresh token/proof under only the recorded new JKT.
     */
    @Serializable
    data class BlueCatbirdChatDefsDeviceAuthenticationRebindBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("expectedAuthGeneration")
        val expectedAuthGeneration: Int,        @SerialName("currentDpopJkt")
        val currentDpopJkt: String,        @SerialName("newDpopJkt")
        val newDpopJkt: String,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceAuthenticationRebindBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsDeviceRevocationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("targetDeviceId")
        val targetDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("targetAuthGeneration")
        val targetAuthGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDeviceRevocationBody"
        }
    }

    /**
     * True-absence CAS creation from client-staged actor-only epoch-zero state. next is exactly generation 0, stateVersion 0, epoch 0, lifecycle active, with the stable conversationId, fresh random groupId, GroupContext-derived hash, and exact client-produced confirmation tag whose GroupInfo/public-state/signed-coordinate/snapshot bytes agree; the public-only service does not derive or verify the secret confirmation MAC. conversationKind is immutable. The creator is active/admin and the sole genesis leaf; every other creation-roster participant is pending and zero-leaf. Direct is exactly creator plus one pending admin-role invitee and uses allowIncoming; group pending rows are members and use allowGroupInvites when present, otherwise allowIncoming. The service checks consent, exact invitation limits, and block policy for every unordered roster pair under the creation lock. At most one nonterminal direct exists for the unordered DID pair; a concurrent/direct duplicate CAS returns the existing identity as a typed outcome. There is no preparation lease, KeyPackage reservation, bootstrap Commit, or Welcome.
     */
    @Serializable
    data class BlueCatbirdChatDefsCreationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("absence")
        val absence: Boolean,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("manifest")
        val manifest: BlueCatbirdChatDefsCreationManifest,        @SerialName("genesisGroupInfo")
        val genesisGroupInfo: BlueCatbirdChatDefsGroupInfoArtifact,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCreationBody"
        }
    }

    /**
     * Ordinary generic signedCommitTransition requires zero Add proposals and zero membership changes; only signedLeafRecoveryFulfillment may contain exactly one request-bound Add. It requires the same conversationId, generation, active lifecycle, and groupId as prior; next.stateVersion = prior.stateVersion + 1 and next.epoch = prior.epoch + 1. The next GroupContext hash is derived from validated merged public state and differs from prior. The exact 32-byte confirmation tag also differs and must byte-equal the parsed authenticated Commit, merged OpenMLS public state, signed next, authoritative coordinate, and persisted snapshot. The public-only service does not cryptographically derive or verify that secret confirmation MAC; recipients do after processing. Checked increment overflow rejects the entire transition.
     */
    @Serializable
    data class BlueCatbirdChatDefsCommitTransitionBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("aad")
        val aad: BlueCatbirdChatDefsCommitAad,        @SerialName("manifest")
        val manifest: BlueCatbirdChatDefsTransitionManifest,        @SerialName("commit")
        val commit: BlueCatbirdChatDefsPublicCommit,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCommitTransitionBody"
        }
    }

    /**
     * Group-only active-admin policy transition; direct conversations reject every policy transition and preserve their exact two-role roster until unilateral close. participantChanges are strictly increasing and duplicate-free by affected userDid exact UTF-8 bytes. addParticipant creates pending/member/zero-leaf only, carries provenance naming this transition/actor, and is never combined with any Add leaf. Invitation creation rechecks consent, exact pending-invitation limits, and block policy for every unordered pair in the resulting roster under lock. next equals prior in conversationId, generation, groupId, epoch, GroupContext hash, confirmation tag, and active lifecycle; only stateVersion increments by exactly one. Checked increment overflow rejects the entire transition.
     */
    @Serializable
    data class BlueCatbirdChatDefsPolicyTransitionBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("participantChanges")
        val participantChanges: List<BlueCatbirdChatDefsParticipantChange>,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsPolicyTransitionBody"
        }
    }

    /**
     * A pending participant's active registered device accepts the exact immutable invitation. The actor DID equals that pending row, provenance matches byte-for-byte, and next changes only stateVersion = prior + 1 while status becomes active and role is preserved: member for group, admin for direct. Under the same conversation lock, the service rechecks consent and block policy for every unordered roster pair and atomically selects the actor device's KeyPackage into an add-kind leaf-recovery reservation bound to recoveryRequestId and the exact post-acceptance next coordinate, never prior. This uses the unique-open constraint for exact (conversationId,generation,actorDid,actorDeviceId); binding next prevents the acceptance transaction's prior-coordinate supersession rule from immediately superseding its own request. Failure changes neither policy state nor package state; a different current leaf later fulfills the recovery Commit/Welcome.
     */
    @Serializable
    data class BlueCatbirdChatDefsParticipantAcceptanceBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("invitationProvenance")
        val invitationProvenance: BlueCatbirdChatDefsInvitationProvenance,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsParticipantAcceptanceBody"
        }
    }

    /**
     * messageId is the sole idempotency identity, unique within conversationId. Its canonical request digest is raw SHA-256 of the exact domain-prefixed canonical Ed25519 transcript and its 64-byte signature is stored separately. Exact completed replay requires both digest and signature byte-equal; either mismatch conflicts. Raw JSON or generated DTO bytes are never hashed. blobBindings contains zero or one exact applicationAttachmentBinding with purpose attachment; metadataAvatarBinding and generic uploadedBlobBinding are forbidden. A stale-coordinate attempt retires that messageId under the same digest/signature rule, so retry requires a newly encrypted frame and new messageId.
     */
    @Serializable
    data class BlueCatbirdChatDefsApplicationSendBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("messageId")
        val messageId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("aad")
        val aad: BlueCatbirdChatDefsApplicationAad,        @SerialName("applicationMessage")
        val applicationMessage: BlueCatbirdChatDefsPrivateApplicationMessage,        @SerialName("blobBindings")
        val blobBindings: List<BlueCatbirdChatDefsApplicationAttachmentBinding>,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationSendBody"
        }
    }

    /**
     * Exact 16 UUID bytes corresponding to the canonical outer UUIDv4 text.
     */
    typealias BlueCatbirdChatDefsIdentifierBytes = Bytes

    /**
     * Both fields identify one exact earlier original message-body entry in the same conversation and inside the reducer's application interval. A reaction, edit, tombstone, readState, or other mutation is never a target. Reducers never fetch outside the interval. An unavailable reply becomes a stub; unavailable reaction/edit/tombstone becomes an UnavailableTarget no-op/rejection while seq still advances.
     */
    @Serializable
    data class BlueCatbirdChatDefsMessageTarget(
        @SerialName("targetSeq")
        val targetSeq: Int,        @SerialName("targetMessageId")
        val targetMessageId: BlueCatbirdChatDefsIdentifierBytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMessageTarget"
        }
    }

    /**
     * Runtime requires ciphertextSize == plaintextSize + 16 using checked safe-integer arithmetic; mismatch or overflow rejects. Ciphertext bytes include the appended 16-byte AES-GCM tag and exclude the nonce.
     */
    @Serializable
    data class BlueCatbirdChatDefsEncryptedImageEmbed(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("purpose")
        val purpose: String,        @SerialName("algorithm")
        val algorithm: String,        @SerialName("key")
        val key: Bytes,        @SerialName("nonce")
        val nonce: Bytes,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("plaintextSize")
        val plaintextSize: Int,        @SerialName("mimeType")
        val mimeType: BlueCatbirdChatDefsEncryptedImageEmbedMimeType,        @SerialName("width")
        val width: Int,        @SerialName("height")
        val height: Int,        @SerialName("altText")
        val altText: String? = null,        @SerialName("blurhash")
        val blurhash: String? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEncryptedImageEmbed"
        }
    }

    /**
     * Runtime requires ciphertextSize == plaintextSize + 16 using checked safe-integer arithmetic; mismatch or overflow rejects. Ciphertext bytes include the appended 16-byte AES-GCM tag and exclude the nonce.
     */
    @Serializable
    data class BlueCatbirdChatDefsEncryptedAudioEmbed(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("purpose")
        val purpose: String,        @SerialName("algorithm")
        val algorithm: String,        @SerialName("key")
        val key: Bytes,        @SerialName("nonce")
        val nonce: Bytes,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("plaintextSize")
        val plaintextSize: Int,        @SerialName("mimeType")
        val mimeType: BlueCatbirdChatDefsEncryptedAudioEmbedMimeType,        @SerialName("durationMillis")
        val durationMillis: Int,        @SerialName("waveform")
        val waveform: Bytes,        @SerialName("transcript")
        val transcript: String? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEncryptedAudioEmbed"
        }
    }

    /**
     * uri must be the deliberately unescaped restricted canonical AT URI of at most 1097 ASCII bytes: exact lowercase at:// scheme; no percent sign or escape anywhere; authority is an exact supported did:plc, a hostname-level did:web whose normalized lowercase hostname satisfies the same production handle syntax, or a normalized lowercase production handle; exact collection plus record key; lowercase collection domain-authority segments while its terminal name and the record key remain case-sensitive; and no query, fragment, trailing slash, duplicate slash, record key equal to . or .., or extra path segment. The exact maximum is 5-byte at:// plus 261-byte did:web authority plus slash plus 317-byte NSID plus slash plus 512-byte record key. Every hostname authority has at least two labels and rejects path/port/IP/single-label/localhost forms plus TLDs alt, arpa, example, internal, invalid, local, localhost, onion, and test; handle.invalid is not an authority. Parser acceptance or round-trip alone is not canonicality proof. cid must be canonical round-trip CID text: parse succeeds and reserialization byte-equals the exact 1-256 UTF-8-byte input.
     */
    @Serializable
    data class BlueCatbirdChatDefsAtprotoRecordEmbed(
        @SerialName("uri")
        val uri: ATProtocolURI,        @SerialName("cid")
        val cid: CID    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAtprotoRecordEmbed"
        }
    }

    /**
     * Runtime requires an absolute HTTPS URI with a nonempty host, no userinfo, no backslash, no whitespace or control character, a valid port when present, and valid percent encoding.
     */
    @Serializable
    data class BlueCatbirdChatDefsExternalLinkEmbed(
        @SerialName("uri")
        val uri: URI,        @SerialName("title")
        val title: String? = null,        @SerialName("description")
        val description: String? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsExternalLinkEmbed"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsEncryptedImageEmbedVariant(
        @SerialName("encryptedImage")
        val encryptedImage: BlueCatbirdChatDefsEncryptedImageEmbed    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEncryptedImageEmbedVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsEncryptedAudioEmbedVariant(
        @SerialName("encryptedAudio")
        val encryptedAudio: BlueCatbirdChatDefsEncryptedAudioEmbed    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEncryptedAudioEmbedVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsAtprotoRecordEmbedVariant(
        @SerialName("atprotoRecord")
        val atprotoRecord: BlueCatbirdChatDefsAtprotoRecordEmbed    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAtprotoRecordEmbedVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsExternalLinkEmbedVariant(
        @SerialName("externalLink")
        val externalLink: BlueCatbirdChatDefsExternalLinkEmbed    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsExternalLinkEmbedVariant"
        }
    }

    /**
     * Runtime requires nonempty text or embed; no empty sentinel is accepted.
     */
    @Serializable
    data class BlueCatbirdChatDefsMessageFrameBody(
        @SerialName("text")
        val text: String? = null,        @SerialName("replyTo")
        val replyTo: BlueCatbirdChatDefsMessageTarget? = null,        @SerialName("embed")
        val embed: BlueCatbirdChatDefsApplicationEmbed? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMessageFrameBody"
        }
    }

    /**
     * The reaction string must use Unicode 17.0.0 NFC, be control-free, and contain exactly one Unicode 17.0.0 UAX #29 extended grapheme cluster; no Unicode emoji-property requirement is imposed. Per verified DID plus emoji, greatest conversation seq wins. A target tombstone suppresses all reactions in the rendered state.
     */
    @Serializable
    data class BlueCatbirdChatDefsReactionFrameBody(
        @SerialName("target")
        val target: BlueCatbirdChatDefsMessageTarget,        @SerialName("emoji")
        val emoji: String,        @SerialName("operation")
        val operation: BlueCatbirdChatDefsReactionFrameBodyOperation    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsReactionFrameBody"
        }
    }

    /**
     * Only the verified DID that authored the original message may edit it. Greatest valid conversation seq wins. Once a valid tombstone exists, every later edit is a terminal no-op/rejection; tombstone dominates regardless of arrival order.
     */
    @Serializable
    data class BlueCatbirdChatDefsEditFrameBody(
        @SerialName("target")
        val target: BlueCatbirdChatDefsMessageTarget,        @SerialName("replacementText")
        val replacementText: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEditFrameBody"
        }
    }

    /**
     * Only the verified DID that authored the original message may tombstone it. A valid tombstone permanently dominates edits, reactions, and message rendering regardless of later mutation arrival.
     */
    @Serializable
    data class BlueCatbirdChatDefsTombstoneFrameBody(
        @SerialName("target")
        val target: BlueCatbirdChatDefsMessageTarget    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsTombstoneFrameBody"
        }
    }

    /**
     * The pair must identify the same accessible original message-body entry in this conversation. Per verified DID, a read frontier may advance to a greater throughSeq or exact-replay the same pair; regression, same seq with a different ID, inaccessible targets, and mutation targets are terminal no-ops/rejections.
     */
    @Serializable
    data class BlueCatbirdChatDefsReadStateFrameBody(
        @SerialName("throughSeq")
        val throughSeq: Int,        @SerialName("throughMessageId")
        val throughMessageId: BlueCatbirdChatDefsIdentifierBytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsReadStateFrameBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMessageFrameVariant(
        @SerialName("message")
        val message: BlueCatbirdChatDefsMessageFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMessageFrameVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsReactionFrameVariant(
        @SerialName("reaction")
        val reaction: BlueCatbirdChatDefsReactionFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsReactionFrameVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsEditFrameVariant(
        @SerialName("edit")
        val edit: BlueCatbirdChatDefsEditFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEditFrameVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsTombstoneFrameVariant(
        @SerialName("tombstone")
        val tombstone: BlueCatbirdChatDefsTombstoneFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsTombstoneFrameVariant"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsReadStateFrameVariant(
        @SerialName("readState")
        val readState: BlueCatbirdChatDefsReadStateFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsReadStateFrameVariant"
        }
    }

    /**
     * Encrypted plaintext is one deterministic RFC 8949 4.2.3 DAG-CBOR map, at most 65536 bytes, exact-reencoded by Rust with no JSON/unknown fallback, duplicate/unknown/null/tag/float/indefinite/trailing form. Inner context/messageId/blob binding exactly match MLS AAD and signed outer entry.
     */
    @Serializable
    data class BlueCatbirdChatDefsApplicationFrame(
        @SerialName("protocol")
        val protocol: String,        @SerialName("version")
        val version: Int,        @SerialName("messageId")
        val messageId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("context")
        val context: BlueCatbirdChatDefsApplicationFrameContext,        @SerialName("body")
        val body: BlueCatbirdChatDefsApplicationFrameBody    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationFrame"
        }
    }

    /**
     * AES-256-GCM AAD is CATBIRD-CHAT-BLOB\0 followed by exact deterministic DAG-CBOR. The 16-byte tag is appended to ciphertext; there is no plaintext hash. A message attachment is bound once to messageId/index and cannot be transplanted or reused.
     */
    @Serializable
    data class BlueCatbirdChatDefsBlobAad(
        @SerialName("protocol")
        val protocol: String,        @SerialName("context")
        val context: BlueCatbirdChatDefsApplicationFrameContext,        @SerialName("messageId")
        val messageId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("attachmentIndex")
        val attachmentIndex: Int,        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("purpose")
        val purpose: String,        @SerialName("mediaType")
        val mediaType: String,        @SerialName("plaintextSize")
        val plaintextSize: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsBlobAad"
        }
    }

    /**
     * Runtime requires ciphertextSize == plaintextSize + 16 using checked safe-integer arithmetic; mismatch or overflow rejects, including for metadata avatars. Ciphertext bytes include the appended 16-byte AES-GCM tag and exclude the nonce. The origin fields are immutable and identify the metadata version whose AAD first bound these blob bytes. A later title-only metadata version may reuse the descriptor and existing binding without reencrypting the avatar.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataAvatarEmbed(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("purpose")
        val purpose: String,        @SerialName("algorithm")
        val algorithm: String,        @SerialName("key")
        val key: Bytes,        @SerialName("nonce")
        val nonce: Bytes,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("plaintextSize")
        val plaintextSize: Int,        @SerialName("mimeType")
        val mimeType: BlueCatbirdChatDefsMetadataAvatarEmbedMimeType,        @SerialName("width")
        val width: Int,        @SerialName("height")
        val height: Int,        @SerialName("altText")
        val altText: String? = null,        @SerialName("blurhash")
        val blurhash: String? = null,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("originalMetadataVersion")
        val originalMetadataVersion: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAvatarEmbed"
        }
    }

    /**
     * AES-256-GCM AAD is CATBIRD-CHAT-METADATA-AVATAR-BLOB\0 followed by exact deterministic DAG-CBOR. The server validates the immutable original metadata origin fields when a later snapshot reuses an already-bound avatar.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataAvatarBlobAad(
        @SerialName("protocol")
        val protocol: String,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("originalMetadataVersion")
        val originalMetadataVersion: Int,        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("purpose")
        val purpose: String,        @SerialName("mediaType")
        val mediaType: String,        @SerialName("plaintextSize")
        val plaintextSize: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAvatarBlobAad"
        }
    }

    /**
     * Exact canonical DAG-CBOR exporter context. Derive exactly 32 bytes with label blue.catbird.chat.metadata.v1. metadataVersion is included, so only snapshots with the same exact context are guaranteed to share the exporter-derived key; different metadata versions in one MLS epoch may derive different keys. groupId, confirmationTag, stateVersion, originTransitionId, ciphertext size, and avatar binding are forbidden from this projection.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataExporterContext(
        @SerialName("protocol")
        val protocol: String,        @SerialName("version")
        val version: Int,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("generation")
        val generation: Int,        @SerialName("epoch")
        val epoch: Int,        @SerialName("groupContextHash")
        val groupContextHash: BlueCatbirdChatDefsArtifactHash,        @SerialName("metadataVersion")
        val metadataVersion: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataExporterContext"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMetadataAadAvatarBinding(
        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAadAvatarBinding"
        }
    }

    /**
     * AES-256-GCM AAD is exactly CATBIRD-CHAT-METADATA\0 followed by canonical DAG-CBOR of this map. coordinate remains nested; absent avatarBinding is omitted.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataAad(
        @SerialName("protocol")
        val protocol: String,        @SerialName("version")
        val version: Int,        @SerialName("coordinate")
        val coordinate: BlueCatbirdChatDefsMetadataCryptoContext,        @SerialName("metadataVersion")
        val metadataVersion: Int,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("avatarBinding")
        val avatarBinding: BlueCatbirdChatDefsMetadataAadAvatarBinding? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAad"
        }
    }

    /**
     * Immutable server-validated historical proof for the encrypted content author. It remains available with every current metadata snapshot after later device revocation or participant removal, and must match the decrypted author/key/origin fields exactly.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataAuthorProof(
        @SerialName("authorDid")
        val authorDid: BlueCatbirdChatDefsBareDid,        @SerialName("authorDeviceId")
        val authorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("authorKeyId")
        val authorKeyId: BlueCatbirdChatDefsKeyId,        @SerialName("signaturePublicKey")
        val signaturePublicKey: Bytes,        @SerialName("authGenerationAtOrigin")
        val authGenerationAtOrigin: Int,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsOperationId,        @SerialName("originSeq")
        val originSeq: Int,        @SerialName("roleAtOrigin")
        val roleAtOrigin: String,        @SerialName("deviceStatusAtOrigin")
        val deviceStatusAtOrigin: String    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataAuthorProof"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMetadataContentProjection(
        @SerialName("protocol")
        val protocol: String,        @SerialName("version")
        val version: Int,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("metadataVersion")
        val metadataVersion: Int,        @SerialName("authorDid")
        val authorDid: BlueCatbirdChatDefsBareDid,        @SerialName("authorDeviceId")
        val authorDeviceId: BlueCatbirdChatDefsIdentifierBytes,        @SerialName("authorKeyId")
        val authorKeyId: BlueCatbirdChatDefsKeyId,        @SerialName("title")
        val title: String,        @SerialName("description")
        val description: String? = null,        @SerialName("avatar")
        val avatar: BlueCatbirdChatDefsMetadataAvatarEmbed? = null    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataContentProjection"
        }
    }

    /**
     * contentSignature is strict Ed25519 over CATBIRD-CHAT-METADATA-CONTENT\0 plus canonical DAG-CBOR of content. Historical immutable device keys and authorization at origin are verified after decryption.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataPlaintext(
        @SerialName("content")
        val content: BlueCatbirdChatDefsMetadataContentProjection,        @SerialName("contentSignature")
        val contentSignature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataPlaintext"
        }
    }

    /**
     * Exporter label blue.catbird.chat.metadata.v1 with exact metadataExporterContext derives 32 bytes. metadataVersion participates in that context, so snapshots at different metadata versions in one epoch may use different keys. Every non-idempotent snapshot nevertheless uses a fresh unpredictable 96-bit CSPRNG nonce, and the server enforces uniqueness across the deliberately broader exact (conversationId,generation,epoch,nonce) tuple as defense-in-depth that subsumes nonce uniqueness whenever the exact metadataExporterContext and key are the same. Only exact idempotent replay may repeat a nonce; every other collision returns MetadataNonceReuse atomically. AES-256-GCM AAD uses exact metadataAad. Hash covers ciphertext plus appended tag, excluding nonce. avatarBinding, when present, is the exact metadataAvatarBinding with purpose metadata; applicationAttachmentBinding and generic uploadedBlobBinding are forbidden. Creation uses metadataVersion 1 and its create transition as origin. A metadata update increments version and uses its own transition as origin. Epoch-changing commits reencrypt byte-identical signed content/version/origin; reset either reencrypts that same content or creates an empty activator-signed prior-version-plus-one snapshot. policy-only transitions leave the snapshot unchanged.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataSnapshot(
        @SerialName("coordinate")
        val coordinate: BlueCatbirdChatDefsMetadataCryptoContext,        @SerialName("originTransitionId")
        val originTransitionId: BlueCatbirdChatDefsOperationId,        @SerialName("metadataVersion")
        val metadataVersion: Int,        @SerialName("nonce")
        val nonce: Bytes,        @SerialName("ciphertext")
        val ciphertext: Bytes,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("avatarBinding")
        val avatarBinding: BlueCatbirdChatDefsMetadataAvatarBinding? = null,        @SerialName("authorProof")
        val authorProof: BlueCatbirdChatDefsMetadataAuthorProof    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataSnapshot"
        }
    }

    /**
     * Admin-only metadata transition. next equals prior in conversationId, generation, groupId, epoch, GroupContext hash, confirmation tag, and active lifecycle; only stateVersion increments by exactly one. metadataSnapshot.metadataVersion is exactly the prior metadataVersion plus one. Checked coordinate or metadata-version overflow rejects the entire transition.
     */
    @Serializable
    data class BlueCatbirdChatDefsMetadataTransitionBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataTransitionBody"
        }
    }

    /**
     * typingId is the sole idempotency identity. Publishing is current-leaf-only, rate-limited and coalesced; the eight-second TTL and every server timestamp derive from the request's single trusted server instant T. The result is best-effort, uncursored, and never durable.
     */
    @Serializable
    data class BlueCatbirdChatDefsTypingBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("typingId")
        val typingId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("isTyping")
        val isTyping: Boolean,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsTypingBody"
        }
    }

    /**
     * Best-effort eight-second coalesced ephemeral event with no event cursor, previousCursor, or durable conversation entry.
     */
    @Serializable
    data class BlueCatbirdChatDefsTypingEvent(
        @SerialName("typingId")
        val typingId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("isTyping")
        val isTyping: Boolean,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsTypingEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsBlobUploadPreparationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("ciphertextSha256")
        val ciphertextSha256: BlueCatbirdChatDefsArtifactHash,        @SerialName("ciphertextSize")
        val ciphertextSize: Int,        @SerialName("purpose")
        val purpose: BlueCatbirdChatDefsDefsBlobPurpose,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsBlobUploadPreparationBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsBlobDeletionBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("blobId")
        val blobId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsBlobDeletionBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsResetRequestBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("resetRequestId")
        val resetRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("reason")
        val reason: BlueCatbirdChatDefsDefsResetReason,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetRequestBody"
        }
    }

    /**
     * CASes prior to retired with stateVersion = prior.stateVersion + 1 and lifecycle superseded while conversationId, generation, groupId, epoch, GroupContext hash, and confirmation tag remain unchanged. successor keeps conversationId, immutable conversationKind, exact participant statuses/roles/provenance, sets generation = prior.generation + 1, stateVersion 0, epoch 0, lifecycle active, and uses a fresh random groupId, successor GroupContext-derived hash, and exact client-produced confirmation tag whose GroupInfo/public-state/signed-coordinate/snapshot bytes agree; the public-only service does not derive or verify the secret confirmation MAC. The registered signing activator must be an active roster admin but need not be an old-generation leaf; it becomes the sole successor genesis leaf. In direct conversations both participants retain role admin, but a pending invitee remains unauthorized and cannot activate. Checked stateVersion or generation overflow rejects the entire activation. There is no reset KeyPackage reservation, bootstrap Commit, or Welcome.
     */
    @Serializable
    data class BlueCatbirdChatDefsResetActivationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("resetRequestId")
        val resetRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("retired")
        val retired: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("successor")
        val successor: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("manifest")
        val manifest: BlueCatbirdChatDefsResetActivationManifest,        @SerialName("genesisGroupInfo")
        val genesisGroupInfo: BlueCatbirdChatDefsGroupInfoArtifact,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetActivationBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsResetRequestView(
        @SerialName("resetRequestId")
        val resetRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("requesterDid")
        val requesterDid: BlueCatbirdChatDefsBareDid,        @SerialName("requesterDeviceId")
        val requesterDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("reason")
        val reason: BlueCatbirdChatDefsDefsResetReason,        @SerialName("status")
        val status: BlueCatbirdChatDefsResetRequestViewStatus,        @SerialName("requestedAt")
        val requestedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetRequestView"
        }
    }

    /**
     * The requester must be a registered device of an active current participant; pending participants must first use acceptConversation. At most one request/reservation is open for exact (conversationId,generation,actorDid,actorDeviceId): exact replay is idempotent and a changed concurrent request rejects LeafRecoveryAlreadyOpen. Every coordinate-changing transaction supersedes and releases every open request at its prior coordinate. Relationship block policy is rechecked under lock before reserving. add requires actorDeviceId to be absent from the prior MLS tree. replace requires actorDeviceId to identify its exact existing prior leaf; fulfillment removes and re-adds that same (actorDid, actorDeviceId) using the request-bound package. A poisoned honest client can sign replace outside MLS with a fresh package; poison is not server-visible and structural Commit validation cannot prove decryptability.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryRequestBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("recoveryKind")
        val recoveryKind: BlueCatbirdChatDefsDefsLeafRecoveryKind,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryRequestBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryCancellationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryCancellationBody"
        }
    }

    /**
     * A different current leaf fulfills the request with an ordinary Commit coordinate increment. The requester DID is active in prior state. add contains exactly one matching addLeafByRecovery. replace contains exactly one removeLeaf and one addLeafByRecovery for the same requester's (userDid, deviceId), with no other leaf effect for that tuple. Under the mutation lock, block policy is rechecked for every unordered pair in the resulting roster immediately before the final Add. Honest poison containment selects a healthy different-DID fulfiller, but the server enforces only different-current-leaf because poison has no server-visible marker. Replacement opens a new application interval and never backfills history.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryFulfillmentBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("aad")
        val aad: BlueCatbirdChatDefsCommitAad,        @SerialName("manifest")
        val manifest: BlueCatbirdChatDefsTransitionManifest,        @SerialName("commit")
        val commit: BlueCatbirdChatDefsPublicCommit,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryFulfillmentBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryView(
        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("requesterDid")
        val requesterDid: BlueCatbirdChatDefsBareDid,        @SerialName("requesterDeviceId")
        val requesterDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("recoveryKind")
        val recoveryKind: BlueCatbirdChatDefsDefsLeafRecoveryKind,        @SerialName("boundCoordinate")
        val boundCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("reservation")
        val reservation: BlueCatbirdChatDefsLeafRecoveryReservation,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsLeafRecoveryStatus,        @SerialName("requestedAt")
        val requestedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryView"
        }
    }

    /**
     * Open advisory with no terminalTransitionId, terminalRevocationId, or terminalAt field.
     */
    @Serializable
    data class BlueCatbirdChatDefsRecoveryWorkPendingView(
        @SerialName("recoveryWorkId")
        val recoveryWorkId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("sourceKind")
        val sourceKind: BlueCatbirdChatDefsDefsRecoveryWorkSourceKind,        @SerialName("sourceId")
        val sourceId: BlueCatbirdChatDefsOperationId,        @SerialName("sourceCoordinate")
        val sourceCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: String,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRecoveryWorkPendingView"
        }
    }

    /**
     * Completed advisory bound to exactly the accepted leaf-recovery fulfillment transition and terminal time.
     */
    @Serializable
    data class BlueCatbirdChatDefsRecoveryWorkCompletedByTransitionView(
        @SerialName("recoveryWorkId")
        val recoveryWorkId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("sourceKind")
        val sourceKind: BlueCatbirdChatDefsDefsRecoveryWorkSourceKind,        @SerialName("sourceId")
        val sourceId: BlueCatbirdChatDefsOperationId,        @SerialName("sourceCoordinate")
        val sourceCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: String,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("terminalTransitionId")
        val terminalTransitionId: BlueCatbirdChatDefsOperationId,        @SerialName("terminalAt")
        val terminalAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRecoveryWorkCompletedByTransitionView"
        }
    }

    /**
     * Superseded advisory bound to exactly the coordinate-changing transition that invalidated it and the terminal time.
     */
    @Serializable
    data class BlueCatbirdChatDefsRecoveryWorkSupersededByTransitionView(
        @SerialName("recoveryWorkId")
        val recoveryWorkId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("sourceKind")
        val sourceKind: BlueCatbirdChatDefsDefsRecoveryWorkSourceKind,        @SerialName("sourceId")
        val sourceId: BlueCatbirdChatDefsOperationId,        @SerialName("sourceCoordinate")
        val sourceCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: String,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("terminalTransitionId")
        val terminalTransitionId: BlueCatbirdChatDefsOperationId,        @SerialName("terminalAt")
        val terminalAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRecoveryWorkSupersededByTransitionView"
        }
    }

    /**
     * Superseded advisory whose terminalRevocationId resolves to immutable signed revocation evidence whose target DID byte-equals recipientDid and whose target device ID byte-equals recipientDeviceId; a same-DID sibling-device revocation rejects. terminalAt is that revocation's exact trusted acceptance time.
     */
    @Serializable
    data class BlueCatbirdChatDefsRecoveryWorkSupersededByRevocationView(
        @SerialName("recoveryWorkId")
        val recoveryWorkId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("recipientDid")
        val recipientDid: BlueCatbirdChatDefsBareDid,        @SerialName("recipientDeviceId")
        val recipientDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("sourceKind")
        val sourceKind: BlueCatbirdChatDefsDefsRecoveryWorkSourceKind,        @SerialName("sourceId")
        val sourceId: BlueCatbirdChatDefsOperationId,        @SerialName("sourceCoordinate")
        val sourceCoordinate: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: String,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("terminalRevocationId")
        val terminalRevocationId: BlueCatbirdChatDefsOperationId,        @SerialName("terminalAt")
        val terminalAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsRecoveryWorkSupersededByRevocationView"
        }
    }

    /**
     * Terminal close CAS. retired preserves conversationId, generation, groupId, epoch, GroupContext hash, and confirmation tag, sets stateVersion = prior + 1 and lifecycle superseded, and has no successor. For direct, either exact logical participant may sign with an active registered device, including the pending invitee. For group, only the sole remaining logical participant may sign and must be active admin. The transaction closes every device application interval, releases live invitation counts and request-bound package reservations, supersedes pending Welcomes and reset/recovery/leave work, and emits one terminal tombstone, entry, and event.
     */
    @Serializable
    data class BlueCatbirdChatDefsConversationCloseBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("retired")
        val retired: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCloseBody"
        }
    }

    /**
     * Group-only durable consent used when the active requester currently has one or more MLS leaves. An active registered requester device signs removal of that participant and every current leaf. Requester must not be the last active admin; handoff occurs first. It expires after 24 hours and any coordinate change makes it stale. Group zero-leaf pending or active participants use zeroLeafLeaveBody. Either direct participant instead uses directCloseBody regardless of status or leaf count.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeaveRequestBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("leaveRequestId")
        val leaveRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveRequestBody"
        }
    }

    /**
     * Group-only immediate self-removal. A pending or active participant with zero leaves removes its own exact participant row without admin fulfillment. next changes only stateVersion = prior + 1 and the operation emits no Welcome. A last active admin still must hand off first. Removing a pending invitation or active zero-leaf participant releases all associated pending-invitation counts and any open unconsumed recovery reservation atomically. Direct participants use directCloseBody instead.
     */
    @Serializable
    data class BlueCatbirdChatDefsZeroLeafLeaveBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsZeroLeafLeaveBody"
        }
    }

    /**
     * conversationId must byte-equal the cancellation entry row conversationId. Before append or fingerprinting, resolve the exact retained authenticated leaveRequestEntry and durable request named by leaveRequestId. The referenced body leaveRequestId must byte-equal this leaveRequestId; leaveRequestId is the exact globally resolved signed request key; the referenced row and body conversationId must byte-equal this conversationId; and actorDid must byte-equal the referenced requester actorDid. The retained reference entry is distinct and has seq strictly less than cancellation seq. Same-seq, later, and self-reference reject. actorDeviceId may differ from the requesting device only when it is another currently active registered device of that same DID and its own key, authentication generation, canonical transcript, and Ed25519 signature all verify. Missing, wrong-type, wrong-ID, cross-conversation, and wrong-requester references reject. The retained reference remains independently verifiable after cancellation.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeaveCancellationBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("leaveRequestId")
        val leaveRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveCancellationBody"
        }
    }

    /**
     * Group-only fulfillment. For an active requester with one or more leaves, a current leaf of a different DID fulfills the exact unexpired request with an ordinary Commit. The manifest contains exactly one removeParticipant for requesterDid and removeLeaf for every and only current requester leaf, with no Add, reservation, or Welcome. The requester's signature supplies consent, but removal still rejects if requester is the last active admin.
     */
    @Serializable
    data class BlueCatbirdChatDefsLeaveCommitFulfillmentBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("leaveRequestId")
        val leaveRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionId")
        val transitionId: BlueCatbirdChatDefsOperationId,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("next")
        val next: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("aad")
        val aad: BlueCatbirdChatDefsCommitAad,        @SerialName("manifest")
        val manifest: BlueCatbirdChatDefsTransitionManifest,        @SerialName("commit")
        val commit: BlueCatbirdChatDefsPublicCommit,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveCommitFulfillmentBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeaveRequestView(
        @SerialName("leaveRequestId")
        val leaveRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("requesterDid")
        val requesterDid: BlueCatbirdChatDefsBareDid,        @SerialName("requesterDeviceId")
        val requesterDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("prior")
        val prior: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsLeaveRequestStatus,        @SerialName("requestedAt")
        val requestedAt: BlueCatbirdChatDefsCanonicalDatetime,        @SerialName("expiresAt")
        val expiresAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveRequestView"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsDurableLeaveRequestResult(
        @SerialName("leaveRequest")
        val leaveRequest: BlueCatbirdChatDefsLeaveRequestView,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsLeaveRequestEntry    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsDurableLeaveRequestResult"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsZeroLeafLeaveResult(
        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsZeroLeafLeaveEntry    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsZeroLeafLeaveResult"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationCreatedResult(
        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsCreationEntry    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCreatedResult"
        }
    }

    /**
     * Typed successful CAS-loser outcome for the one already-nonterminal direct conversation of the same unordered DID pair. No proposed conversation state, entry, metadata nonce, or invitation is committed.
     */
    @Serializable
    data class BlueCatbirdChatDefsExistingDirectConversationResult(
        @SerialName("conversationKind")
        val conversationKind: String,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsExistingDirectConversationResult"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsWelcomeAcknowledgementBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionSeq")
        val transitionSeq: Int,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeAcknowledgementBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsWelcomeRejectionBody(
        @SerialName("signatureDomain")
        val signatureDomain: String,        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("transitionSeq")
        val transitionSeq: Int,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("reason")
        val reason: BlueCatbirdChatDefsDefsWelcomeRejectionReason,        @SerialName("actorDid")
        val actorDid: BlueCatbirdChatDefsBareDid,        @SerialName("actorDeviceId")
        val actorDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("keyId")
        val keyId: BlueCatbirdChatDefsKeyId,        @SerialName("authGeneration")
        val authGeneration: Int,        @SerialName("idempotencyKey")
        val idempotencyKey: BlueCatbirdChatDefsOperationId,        @SerialName("signedAt")
        val signedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeRejectionBody"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedDeviceEnrollment(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedDeviceEnrollmentBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedDeviceEnrollment"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedKeyPackageReplenishment(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedKeyPackageReplenishmentBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedKeyPackageReplenishment"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedDeviceAuthenticationRebind(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedDeviceAuthenticationRebindBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedDeviceAuthenticationRebind"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedDeviceRevocation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedDeviceRevocationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedDeviceRevocation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedBlobUploadPreparation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedBlobUploadPreparationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedBlobUploadPreparation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedBlobDeletion(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedBlobDeletionBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedBlobDeletion"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedCreation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedCreationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedCreation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedCommitTransition(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedCommitTransitionBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedCommitTransition"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedPolicyTransition(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedPolicyTransitionBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedPolicyTransition"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedParticipantAcceptance(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedParticipantAcceptanceBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedParticipantAcceptance"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedApplicationSend(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedApplicationSendBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedApplicationSend"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedTyping(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedTypingBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedTyping"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedMetadataTransition(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedMetadataTransitionBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedMetadataTransition"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedResetRequest(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedResetRequestBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedResetRequest"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedResetActivation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedResetActivationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedResetActivation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeafRecoveryRequest(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeafRecoveryRequestBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeafRecoveryRequest"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeafRecoveryCancellation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeafRecoveryCancellationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeafRecoveryCancellation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeafRecoveryFulfillment(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeafRecoveryFulfillmentBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeafRecoveryFulfillment"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedConversationClose(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedConversationCloseBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedConversationClose"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeaveRequest(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeaveRequestBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeaveRequest"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedZeroLeafLeave(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedZeroLeafLeaveBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedZeroLeafLeave"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeaveCancellation(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeaveCancellationBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeaveCancellation"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedLeaveCommitFulfillment(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedLeaveCommitFulfillmentBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedLeaveCommitFulfillment"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedWelcomeAcknowledgement(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedWelcomeAcknowledgementBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedWelcomeAcknowledgement"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsSignedWelcomeRejection(
        @SerialName("body")
        val body: BlueCatbirdChatDefsSignedWelcomeRejectionBodyUnion,        @SerialName("signature")
        val signature: Bytes    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsSignedWelcomeRejection"
        }
    }

    /**
     * conversationKind is immutable across every transition and reset and is never inferred from participant count. Every active post-state has 1-100 unique logical participants, at least one active admin, and 1-100 MLS leaves. Group pending participants are members; the sole direct pending invitee is admin-role but unauthorized until active. Every pending participant has zero leaves and every MLS leaf belongs to an active participant. Direct has exactly two fixed admin-role participants until close and at most one nonterminal conversation per unordered DID pair; group remains group at every participant count. A DID has at most 20 leaves. There is exactly one leaf per (userDid, deviceId), BasicCredential identity, and public-tree leaf index; every leaf binds the exact registered immutable device/key and may open only while both are active. Later revocation immediately removes authentication and delivery eligibility but may leave that dead cryptographic leaf MLS-active until a signed Remove, reset, or close. snapshotSeq is the greatest committed conversation seq included in the transactionally observed state snapshot; it is not a caller entry cursor or proof of application visibility. Output participants are strictly ordered by userDid exact UTF-8 bytes; leaves are strictly ordered by (userDid exact UTF-8 bytes, deviceId raw UUID bytes).
     */
    @Serializable
    data class BlueCatbirdChatDefsConversationState(
        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("coordinates")
        val coordinates: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("cipherSuite")
        val cipherSuite: BlueCatbirdChatDefsDefsCipherSuite,        @SerialName("participants")
        val participants: List<BlueCatbirdChatDefsParticipantView>,        @SerialName("leaves")
        val leaves: List<BlueCatbirdChatDefsDeviceLeafView>,        @SerialName("metadataSnapshot")
        val metadataSnapshot: BlueCatbirdChatDefsMetadataSnapshot,        @SerialName("snapshotSeq")
        val snapshotSeq: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationState"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationInventoryState(
        @SerialName("state")
        val state: BlueCatbirdChatDefsConversationState    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationInventoryState"
        }
    }

    /**
     * A tombstone is for one exact authenticated (userDid,deviceId) leaf interval. membershipIntervalId equals the opening creation, reset activation, or Add transition ID for this concrete device interval. terminalSeq is the inclusive seq of that device's Remove or reset; another leaf of the same DID never extends it.
     */
    @Serializable
    data class BlueCatbirdChatDefsConversationRemovalTombstone(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("membershipIntervalId")
        val membershipIntervalId: BlueCatbirdChatDefsOperationId,        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("terminalSeq")
        val terminalSeq: Int,        @SerialName("removedAt")
        val removedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationRemovalTombstone"
        }
    }

    /**
     * Immutable terminal conversation record. retired is superseded and has no successor; terminalSeq inclusively closes every concrete device application interval.
     */
    @Serializable
    data class BlueCatbirdChatDefsConversationCloseTombstone(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("retired")
        val retired: BlueCatbirdChatDefsConversationCoordinates,        @SerialName("closedByDid")
        val closedByDid: BlueCatbirdChatDefsBareDid,        @SerialName("closedByDeviceId")
        val closedByDeviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("terminalSeq")
        val terminalSeq: Int,        @SerialName("closedAt")
        val closedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCloseTombstone"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationCloseResult(
        @SerialName("tombstone")
        val tombstone: BlueCatbirdChatDefsConversationCloseTombstone,        @SerialName("entry")
        val entry: BlueCatbirdChatDefsConversationCloseEntry    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCloseResult"
        }
    }

    /**
     * conversationId must byte-equal signedRequest.body.prior.conversationId before append or fingerprinting. Clients strictly validate the signed request and verify its 64-byte Ed25519 signature over the exact CATBIRD-CHAT-MESSAGE\0 canonical signing transcript before decryption, attribution, display, or effects. The immutable outer-entry fingerprint is raw SHA-256 of UTF8(CATBIRD-CHAT-APPLICATION-ENTRY-FINGERPRINT\0) followed by canonical DAG-CBOR of exactly {entryId: UUID bytes16, conversationId: UUID bytes16, seq: safe integer, requestDigest: bytes32, signature: bytes64, receivedAt: canonical text}. requestDigest is raw SHA-256 of the exact send signing transcript, so it commits every signed actor, device, coordinate, message, artifact, and blob-binding field; signature and server row identity/time are committed separately. Raw JSON, generated DTO bytes, plaintext-only digests, and unsigned outer surrogates are forbidden. Application visibility is per concrete authenticated (userDid,deviceId) MLS leaf. The genesis creator device begins at creation seq; an ordinary Add begins that added device at its Welcome-producing transition; that exact device's Remove or reset closes its interval inclusively. Reset begins only the activator device's successor interval at the same reset seq. A sibling registered device, logical roster member, or zero-leaf device inherits no ciphertext. Re-addition opens a new interval and never backfills the gap.
     */
    @Serializable
    data class BlueCatbirdChatDefsApplicationEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedApplicationSend,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsApplicationEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsCommitEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedCommitTransition,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCommitEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsPolicyEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedPolicyTransition,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsPolicyEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMetadataEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedMetadataTransition,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMetadataEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsCreationEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedCreation,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsCreationEntry"
        }
    }

    /**
     * One atomic policy entry records pending-to-active acceptance and the request-bound add recovery reservation created for the accepting device.
     */
    @Serializable
    data class BlueCatbirdChatDefsParticipantAcceptanceEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedParticipantAcceptance,        @SerialName("recovery")
        val recovery: BlueCatbirdChatDefsLeafRecoveryView,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsParticipantAcceptanceEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationCloseEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedConversationClose,        @SerialName("tombstone")
        val tombstone: BlueCatbirdChatDefsConversationCloseTombstone,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationCloseEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsResetRequestEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedResetRequest,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetRequestEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsResetActivationEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedResetActivation,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetActivationEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryFulfillmentEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeafRecoveryFulfillment,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryFulfillmentEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeaveRequestEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeaveRequest,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveRequestEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsZeroLeafLeaveEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedZeroLeafLeave,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsZeroLeafLeaveEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeaveCancellationEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeaveCancellation,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveCancellationEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeaveCommitFulfillmentEntry(
        @SerialName("entryId")
        val entryId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int,        @SerialName("signedRequest")
        val signedRequest: BlueCatbirdChatDefsSignedLeaveCommitFulfillment,        @SerialName("receivedAt")
        val receivedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveCommitFulfillmentEntry"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationChangedEvent(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationChangedEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsConversationClosedEvent(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationKind")
        val conversationKind: BlueCatbirdChatDefsDefsConversationKind,        @SerialName("terminalSeq")
        val terminalSeq: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsConversationClosedEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsMessageAvailableEvent(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("seq")
        val seq: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsMessageAvailableEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsWelcomeAvailableEvent(
        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeAvailableEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsWelcomeDispositionEvent(
        @SerialName("welcomeId")
        val welcomeId: BlueCatbirdChatDefsOperationId,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsWelcomeStatus    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWelcomeDispositionEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsResetRequestedEvent(
        @SerialName("resetRequestId")
        val resetRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsResetRequestedEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeafRecoveryEvent(
        @SerialName("recoveryRequestId")
        val recoveryRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsLeafRecoveryStatus    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeafRecoveryEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsLeaveRequestEvent(
        @SerialName("leaveRequestId")
        val leaveRequestId: BlueCatbirdChatDefsOperationId,        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("status")
        val status: BlueCatbirdChatDefsDefsLeaveRequestStatus    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsLeaveRequestEvent"
        }
    }

    /**
     * Frozen-audience notice closing one exact device-leaf interval inclusively; another device of the same DID does not extend it.
     */
    @Serializable
    data class BlueCatbirdChatDefsAccessEndedEvent(
        @SerialName("conversationId")
        val conversationId: BlueCatbirdChatDefsOperationId,        @SerialName("membershipIntervalId")
        val membershipIntervalId: BlueCatbirdChatDefsOperationId,        @SerialName("userDid")
        val userDid: BlueCatbirdChatDefsBareDid,        @SerialName("deviceId")
        val deviceId: BlueCatbirdChatDefsDeviceId,        @SerialName("terminalSeq")
        val terminalSeq: Int    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsAccessEndedEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsWatermarkEvent(
        @SerialName("issuedAt")
        val issuedAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsWatermarkEvent"
        }
    }

    @Serializable
    data class BlueCatbirdChatDefsEventEnvelope(
        @SerialName("previousCursor")
        val previousCursor: String,        @SerialName("cursor")
        val cursor: String,        @SerialName("payload")
        val payload: BlueCatbirdChatDefsProtocolEventPayload,        @SerialName("createdAt")
        val createdAt: BlueCatbirdChatDefsCanonicalDatetime    ) {
        companion object {
            const val TYPE_IDENTIFIER = "#blueCatbirdChatDefsEventEnvelope"
        }
    }
