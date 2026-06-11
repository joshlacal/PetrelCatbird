// Generated namespace extensions for an overlay package.

package blue.catbird.petrel.generated

import blue.catbird.petrel.client.*

val ATProtoClient.blue: BlueNamespace get() = BlueNamespace(this)
val ATProtoClient.place: PlaceNamespace get() = PlaceNamespace(this)

class BlueNamespace(val client: ATProtoClient) {
    val catbird: BlueCatbirdNamespace get() = BlueCatbirdNamespace(client)
}

class BlueCatbirdNamespace(val client: ATProtoClient) {
    val bskychat: BlueCatbirdBskychatNamespace get() = BlueCatbirdBskychatNamespace(client)
    val mlsChat: BlueCatbirdMlsChatNamespace get() = BlueCatbirdMlsChatNamespace(client)
    val mlsDS: BlueCatbirdMlsDSNamespace get() = BlueCatbirdMlsDSNamespace(client)
}

class BlueCatbirdBskychatNamespace(val client: ATProtoClient) {
}

class BlueCatbirdMlsChatNamespace(val client: ATProtoClient) {
}

class BlueCatbirdMlsDSNamespace(val client: ATProtoClient) {
}

class PlaceNamespace(val client: ATProtoClient) {
    val stream: PlaceStreamNamespace get() = PlaceStreamNamespace(client)
}

class PlaceStreamNamespace(val client: ATProtoClient) {
    val defs: PlaceStreamDefsNamespace get() = PlaceStreamDefsNamespace(client)
    val key: PlaceStreamKeyNamespace get() = PlaceStreamKeyNamespace(client)
    val livestream: PlaceStreamLivestreamNamespace get() = PlaceStreamLivestreamNamespace(client)
    val segment: PlaceStreamSegmentNamespace get() = PlaceStreamSegmentNamespace(client)
    val video: PlaceStreamVideoNamespace get() = PlaceStreamVideoNamespace(client)
    val badge: PlaceStreamBadgeNamespace get() = PlaceStreamBadgeNamespace(client)
    val branding: PlaceStreamBrandingNamespace get() = PlaceStreamBrandingNamespace(client)
    val broadcast: PlaceStreamBroadcastNamespace get() = PlaceStreamBroadcastNamespace(client)
    val chat: PlaceStreamChatNamespace get() = PlaceStreamChatNamespace(client)
    val config: PlaceStreamConfigNamespace get() = PlaceStreamConfigNamespace(client)
    val graph: PlaceStreamGraphNamespace get() = PlaceStreamGraphNamespace(client)
    val ingest: PlaceStreamIngestNamespace get() = PlaceStreamIngestNamespace(client)
    val live: PlaceStreamLiveNamespace get() = PlaceStreamLiveNamespace(client)
    val metadata: PlaceStreamMetadataNamespace get() = PlaceStreamMetadataNamespace(client)
    val moderation: PlaceStreamModerationNamespace get() = PlaceStreamModerationNamespace(client)
    val multistream: PlaceStreamMultistreamNamespace get() = PlaceStreamMultistreamNamespace(client)
    val muxl: PlaceStreamMuxlNamespace get() = PlaceStreamMuxlNamespace(client)
    val playback: PlaceStreamPlaybackNamespace get() = PlaceStreamPlaybackNamespace(client)
    val richtext: PlaceStreamRichtextNamespace get() = PlaceStreamRichtextNamespace(client)
    val server: PlaceStreamServerNamespace get() = PlaceStreamServerNamespace(client)
}

class PlaceStreamDefsNamespace(val client: ATProtoClient) {
}

class PlaceStreamKeyNamespace(val client: ATProtoClient) {
}

class PlaceStreamLivestreamNamespace(val client: ATProtoClient) {
}

class PlaceStreamSegmentNamespace(val client: ATProtoClient) {
}

class PlaceStreamVideoNamespace(val client: ATProtoClient) {
}

class PlaceStreamBadgeNamespace(val client: ATProtoClient) {
}

class PlaceStreamBrandingNamespace(val client: ATProtoClient) {
}

class PlaceStreamBroadcastNamespace(val client: ATProtoClient) {
}

class PlaceStreamChatNamespace(val client: ATProtoClient) {
}

class PlaceStreamConfigNamespace(val client: ATProtoClient) {
}

class PlaceStreamGraphNamespace(val client: ATProtoClient) {
}

class PlaceStreamIngestNamespace(val client: ATProtoClient) {
}

class PlaceStreamLiveNamespace(val client: ATProtoClient) {
}

class PlaceStreamMetadataNamespace(val client: ATProtoClient) {
}

class PlaceStreamModerationNamespace(val client: ATProtoClient) {
}

class PlaceStreamMultistreamNamespace(val client: ATProtoClient) {
}

class PlaceStreamMuxlNamespace(val client: ATProtoClient) {
}

class PlaceStreamPlaybackNamespace(val client: ATProtoClient) {
}

class PlaceStreamRichtextNamespace(val client: ATProtoClient) {
}

class PlaceStreamServerNamespace(val client: ATProtoClient) {
}
