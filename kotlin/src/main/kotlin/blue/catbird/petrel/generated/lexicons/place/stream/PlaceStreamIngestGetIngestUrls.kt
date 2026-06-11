// Lexicon: 1, ID: place.stream.ingest.getIngestUrls
// Get ingest URLs for a Streamplace station.
package blue.catbird.petrel.generated

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import blue.catbird.petrel.core.types.*
import blue.catbird.petrel.core.*
import blue.catbird.petrel.client.*
import blue.catbird.petrel.network.*
import blue.catbird.petrel.runtime.subscription.openSubscription
import kotlinx.coroutines.flow.*

object PlaceStreamIngestGetIngestUrlsDefs {
    const val TYPE_IDENTIFIER = "place.stream.ingest.getIngestUrls"
}

@Serializable(with = PlaceStreamIngestGetIngestUrlsOutputIngestsUnionSerializer::class)
sealed interface PlaceStreamIngestGetIngestUrlsOutputIngestsUnion {
    @Serializable
    data class Ingest(val value: blue.catbird.petrel.generated.PlaceStreamIngestDefsIngest) : PlaceStreamIngestGetIngestUrlsOutputIngestsUnion

    @Serializable
    data class Unexpected(val value: JsonElement) : PlaceStreamIngestGetIngestUrlsOutputIngestsUnion
}

object PlaceStreamIngestGetIngestUrlsOutputIngestsUnionSerializer : kotlinx.serialization.KSerializer<PlaceStreamIngestGetIngestUrlsOutputIngestsUnion> {
    override val descriptor: kotlinx.serialization.descriptors.SerialDescriptor =
        kotlinx.serialization.descriptors.buildClassSerialDescriptor("PlaceStreamIngestGetIngestUrlsOutputIngestsUnion")

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: PlaceStreamIngestGetIngestUrlsOutputIngestsUnion) {
        val jsonEncoder = encoder as kotlinx.serialization.json.JsonEncoder
        val element = when (value) {
            is PlaceStreamIngestGetIngestUrlsOutputIngestsUnion.Ingest -> {
                val obj = jsonEncoder.json.encodeToJsonElement(blue.catbird.petrel.generated.PlaceStreamIngestDefsIngest.serializer(), value.value)
                kotlinx.serialization.json.JsonObject(obj.jsonObject.toMutableMap().also {
                    it["\$type"] = kotlinx.serialization.json.JsonPrimitive("place.stream.ingest.defs#ingest")
                })
            }
            is PlaceStreamIngestGetIngestUrlsOutputIngestsUnion.Unexpected -> value.value
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

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): PlaceStreamIngestGetIngestUrlsOutputIngestsUnion {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject
        val type = jsonObject["\$type"]?.jsonPrimitive?.contentOrNull

        return when (type) {
            "place.stream.ingest.defs#ingest" -> PlaceStreamIngestGetIngestUrlsOutputIngestsUnion.Ingest(
                jsonDecoder.json.decodeFromJsonElement(blue.catbird.petrel.generated.PlaceStreamIngestDefsIngest.serializer(), element)
            )
            else -> PlaceStreamIngestGetIngestUrlsOutputIngestsUnion.Unexpected(element)
        }
    }
}

@Serializable
    class PlaceStreamIngestGetIngestUrlsParameters

    @Serializable
    data class PlaceStreamIngestGetIngestUrlsOutput(
        @SerialName("ingests")
        val ingests: List<PlaceStreamIngestGetIngestUrlsOutputIngestsUnion>    )

/**
 * Get ingest URLs for a Streamplace station.
 *
 * Endpoint: place.stream.ingest.getIngestUrls
 */
suspend fun PlaceStreamIngestNamespace.getIngestUrls(
parameters: PlaceStreamIngestGetIngestUrlsParameters): ATProtoResponse<PlaceStreamIngestGetIngestUrlsOutput> {
    val endpoint = "place.stream.ingest.getIngestUrls"

    // List<Pair<String, String>> preserves repeated keys, which ATProto
    // array-valued query params rely on (e.g. `?actors=a&actors=b`).
    val queryItems = parameters.toQueryItems()

    return client.networkService.performRequest(
        method = "GET",
        endpoint = endpoint,
        queryItems = queryItems,
        headers = mapOf("Accept" to "application/json"),
        body = null
    )
}
